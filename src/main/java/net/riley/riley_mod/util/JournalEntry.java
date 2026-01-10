package net.riley.riley_mod.util;

import net.minecraft.world.entity.EntityType;
import javax.annotation.Nullable;

public record JournalEntry(String title, String content, Category category, @Nullable EntityType<?> entityType, float scale) {
    public enum Category {
        CREATURES, BLOCKS, ITEMS, ABYSS, STRUCTURES
    }


    public JournalEntry(String title, String content, Category category) {
        this(title, content, category, null, 18.0f);
    }

}
