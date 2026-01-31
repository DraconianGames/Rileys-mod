package net.riley.riley_mod.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.riley.riley_mod.block.entity.SpecialSpawnerBlockEntity;

import java.util.function.Supplier;

public class SpecialSpawnerSettingsPacket {

    private final BlockPos pos;
    private final String mobId;
    private final int radius;
    private final int count;

    public SpecialSpawnerSettingsPacket(BlockPos pos, String mobId, int radius, int count) {
        this.pos = pos;
        this.mobId = mobId;
        this.radius = radius;
        this.count = count;
    }

    public SpecialSpawnerSettingsPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.mobId = buf.readUtf(128);
        this.radius = buf.readInt();
        this.count = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeUtf(mobId, 128);
        buf.writeInt(radius);
        buf.writeInt(count);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;

            BlockEntity be = player.level().getBlockEntity(pos);
            if (!(be instanceof SpecialSpawnerBlockEntity spawnerBe)) return;

            // Only allow creative players to configure
            if (!player.isCreative()) return;

            spawnerBe.applySettings(mobId, radius, count);

            // sync to clients
            player.level().sendBlockUpdated(pos, spawnerBe.getBlockState(), spawnerBe.getBlockState(), 3);
        });
        return true;
    }
}