package net.riley.riley_mod.util;

import net.minecraft.world.entity.EntityType;
import javax.annotation.Nullable;

public record JournalEntry(
        String title,
        String content,
        Category category,
        @Nullable EntityType<?> entityType,
        net.minecraft.world.item.Item temptationItem,
        java.util.List<net.minecraft.world.effect.MobEffectInstance> hitEffects,
        @Nullable java.util.UUID entityUUID
) {
    public JournalEntry {
        if (hitEffects == null) {
            hitEffects = java.util.List.of();
        }
    }
    public enum Category {
        CREATURES, BLOCKS, ITEMS, ABYSS, FALLOW, STRUCTURES, PETS
    }


    public JournalEntry(String title, String content, Category category) {

        this(title, content, category, null,null,java.util.List.of(),null);

    }

}
