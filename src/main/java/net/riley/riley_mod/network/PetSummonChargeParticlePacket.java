package net.riley.riley_mod.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PetSummonChargeParticlePacket {
    private final double x;
    private final double y;
    private final double z;

    public PetSummonChargeParticlePacket(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PetSummonChargeParticlePacket(FriendlyByteBuf buffer) {
        this.x = buffer.readDouble();
        this.y = buffer.readDouble();
        this.z = buffer.readDouble();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeDouble(this.x);
        buffer.writeDouble(this.y);
        buffer.writeDouble(this.z);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.level == null) return;

            minecraft.level.addParticle(
                    ParticleTypes.SOUL_FIRE_FLAME,
                    this.x + (minecraft.level.random.nextDouble() - 0.5D) * 0.08D,
                    this.y + (minecraft.level.random.nextDouble() - 0.5D) * 0.08D,
                    this.z + (minecraft.level.random.nextDouble() - 0.5D) * 0.08D,
                    0.0D,
                    0.015D,
                    0.0D
            );
        });

        return true;
    }
}