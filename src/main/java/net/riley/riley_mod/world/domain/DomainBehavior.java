package net.riley.riley_mod.world.domain;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public interface DomainBehavior {
    ResourceLocation id();

    // Existing method (keep it)
    void onPulse(ServerLevel level, ServerPlayer owner, BlockPos center);

    // New: level-aware pulse (default forwards to old method)
    default void onPulse(ServerLevel level, ServerPlayer owner, BlockPos center, int augmentLevel) {
        onPulse(level, owner, center);
    }
}
//TODO Make max level 5. Max radius 50 blocks. Make 4 minute max effect. Stop increasing the cooldown.
//TODO Make say max after max level is bought.