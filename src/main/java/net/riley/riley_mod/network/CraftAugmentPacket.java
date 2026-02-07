package net.riley.riley_mod.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record CraftAugmentPacket(BlockPos pos, ResourceLocation recipeId) {
    public static void encode(CraftAugmentPacket msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeResourceLocation(msg.recipeId);
    }

    public static CraftAugmentPacket decode(FriendlyByteBuf buf) {
        return new CraftAugmentPacket(buf.readBlockPos(), buf.readResourceLocation());
    }
}