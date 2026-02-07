package net.riley.riley_mod.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.riley.riley_mod.RileyMod;
import org.jetbrains.annotations.NotNull;

public class AugmentationStationRecipe implements Recipe<Container> {

    public static final ResourceLocation TYPE_ID =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "augmentation_station");

    public enum Category {
        DOMAIN_EXPANSIONS,
        BODY_ENHANCEMENTS,
        ACTIVE_AUGMENTS // not a recipe category; handy if you want a UI tab later
    }
    private final int level;
    private final ResourceLocation id;
    private final Category category;
    private final ItemStack resultIcon; // purely display icon (since unlock is permanent)
    private final String description;
    private final NonNullList<Requirement> requirements;
    private final ResourceLocation augmentId; // unique id used for unlock/activate

    public AugmentationStationRecipe(ResourceLocation id,
                                     Category category,
                                     ResourceLocation augmentId,
                                     ItemStack resultIcon,
                                     String description,
                                     NonNullList<Requirement> requirements,
                                     int level) {
        this.id = id;
        this.category = category;
        this.augmentId = augmentId;
        this.resultIcon = resultIcon;
        this.description = description;
        this.requirements = requirements;
        this.level = Math.max(1, level);
    }

    @Override public @NotNull ResourceLocation getId() { return id; }
    public Category getCategory() { return category; }
    public ResourceLocation getAugmentId() { return augmentId; }
    public ItemStack getResultIcon() { return resultIcon.copy(); }
    public String getDescription() { return description; }
    public int getLevel() { return level; }

    public NonNullList<Requirement> getRequirements() { return requirements; }



    // Not used as a normal crafting recipe
    @Override public boolean matches(@NotNull Container c, @NotNull Level level) { return false; }
    @Override public @NotNull ItemStack assemble(@NotNull Container c, @NotNull net.minecraft.core.RegistryAccess ra) { return ItemStack.EMPTY; }
    @Override public boolean canCraftInDimensions(int w, int h) { return false; }
    @Override public @NotNull ItemStack getResultItem(@NotNull net.minecraft.core.RegistryAccess ra) { return ItemStack.EMPTY; }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return AugmentationStationRecipeSerializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return AugmentationStationRecipeType.INSTANCE;
    }
}