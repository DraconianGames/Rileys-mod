package net.riley.riley_mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.item.RileyModItems;

import java.util.List;
import java.util.function.Consumer;

public class RileyModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> ARMOR_PLATE_SMELTABLES = List.of(Items.IRON_INGOT);
    public RileyModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }


    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        oreSmelting(pWriter, ARMOR_PLATE_SMELTABLES, RecipeCategory.MISC,
                RileyModItems.ARMOR_PLATING.get(),
                0.7F, 200,
                "armor_plating");
        oreBlasting(pWriter, ARMOR_PLATE_SMELTABLES, RecipeCategory.MISC,
                RileyModItems.ARMOR_PLATING.get(),
                0.7F, 100,
                "armor_plating");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RileyModItems.MECHAREX_EGG.get())
                .pattern("EAE")
                .pattern("DBD")
                .pattern("ECE")
                .define('A', RileyModItems.MECHAREX_HEAD.get())
                .define('B', RileyModItems.MECHAREX_TORSO.get())
                .define('C', RileyModItems.MECHAREX_TAIL.get())
                .define('D', RileyModItems.MECHAREX_LEG.get())
                .define('E', RileyModItems.ARMOR_PLATING.get())
                .unlockedBy("has_item", has(RileyModItems.EYE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RileyModItems.MECHAREX_HEAD.get())
                .pattern("DDD")
                .pattern("DEA")
                .pattern("BBC")
                .define('A', RileyModItems.MECHA_REX_UNASSEMBLED_HEAD.get())
                .define('B', RileyModItems.LYDAR.get())
                .define('C', RileyModItems.MECHAREX_BRAIN.get())
                .define('D', RileyModItems.CANNON_SHEILD.get())
                .define('E', RileyModItems.MECHAREX_CANNON.get())
                .unlockedBy("has_item", has(RileyModItems.EYE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RileyModItems.MECHAREX_TORSO.get())
                .pattern("CCC")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', RileyModItems.ARMOR_PLATING.get())
                .define('B', RileyModItems.UNASSEMBLED_MECHAREX_TORSO.get())
                .define('C', RileyModItems.LIVING_MACHANICAL_NURONS.get())
                .unlockedBy("has_item", has(RileyModItems.EYE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RileyModItems.MECHAREX_TAIL.get())
                .pattern("CCB")
                .pattern("BDC")
                .pattern("BBB")
                .define('B', RileyModItems.SYNTHETIC_MUSCLE.get())
                .define('C', RileyModItems.LIVING_MACHANICAL_NURONS.get())
                .define('D', RileyModItems.UNASSEMBLED_MECHAREX_TAIL.get())
                .unlockedBy("has_item", has(RileyModItems.EYE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RileyModItems.MECHAREX_LEG.get())
                .pattern("BBC")
                .pattern("BAC")
                .pattern("BBB")
                .define('A', RileyModItems.UNASSEMBLED_MECHAREX_LEG.get())
                .define('B', RileyModItems.SYNTHETIC_MUSCLE.get())
                .define('C', RileyModItems.LIVING_MACHANICAL_NURONS.get())
                .unlockedBy("has_item", has(RileyModItems.EYE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RileyModItems.UNASSEMBLED_MECHAREX_TORSO.get())
                .pattern("AAA")
                .pattern("BCC")
                .pattern("AAA")
                .define('A', RileyModItems.ARMOR_PLATING.get())
                .define('B', RileyModItems.SYNTHETIC_MUSCLE.get())
                .define('C', RileyModItems.LIVING_MACHANICAL_NURONS.get())
                .unlockedBy("has_item", has(RileyModItems.EYE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RileyModItems.UNASSEMBLED_MECHAREX_TAIL.get())
                .pattern("AA ")
                .pattern(" AA")
                .pattern("  A")
                .define('A', RileyModItems.ARMOR_PLATING.get())
                .unlockedBy("has_item", has(RileyModItems.EYE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RileyModItems.UNASSEMBLED_MECHAREX_LEG.get())
                .pattern("AAA")
                .pattern(" AA")
                .pattern("AAA")
                .define('A', RileyModItems.ARMOR_PLATING.get())
                .unlockedBy("has_item", has(RileyModItems.EYE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RileyModItems.MECHA_REX_UNASSEMBLED_HEAD.get())
                .pattern("AAA")
                .pattern("A A")
                .pattern("AAA")
                .define('A', RileyModItems.ARMOR_PLATING.get())
                .unlockedBy("has_item", has(RileyModItems.EYE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RileyModItems.CANNON_SHEILD.get())
                .pattern("AAA")
                .pattern("A  ")
                .define('A', RileyModItems.ARMOR_PLATING.get())
                .unlockedBy("has_item", has(RileyModItems.EYE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RileyModItems.MECHAREX_CANNON.get())
                .pattern("A A")
                .pattern("A A")
                .pattern("BBB")
                .define('A', RileyModItems.ARMOR_PLATING.get())
                .define('B', Items.IRON_BLOCK)
                .unlockedBy("has_item", has(RileyModItems.EYE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RileyModItems.LYDAR.get())
                .pattern("BBB")
                .pattern("BAB")
                .pattern("BBB")
                .define('A', RileyModItems.LIVING_MACHANICAL_NURONS.get())
                .define('B', Items.RED_STAINED_GLASS_PANE)
                .unlockedBy("has_item", has(RileyModItems.EYE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RileyModItems.MECHAREX_BRAIN.get())
                .pattern("AAA")
                .pattern(" A ")
                .pattern(" A ")
                .define('A', RileyModItems.LIVING_MACHANICAL_NURONS.get())
                .unlockedBy("has_item", has(RileyModItems.EYE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RileyModItems.SYNTHETIC_MUSCLE.get())
                .pattern("AA ")
                .pattern(" AA")
                .pattern("AA ")
                .define('A', Items.SCULK)
                .unlockedBy("has_item", has(RileyModItems.EYE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RileyModItems.LIVING_MACHANICAL_NURONS.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("AC ")
                .define('B', Items.BEEF)
                .define('A', Items.GLASS_BOTTLE)
                .define('C', Items.CLOCK)
                .unlockedBy("has_item", has(RileyModItems.EYE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RileyModItems.MECHAREX_ENGINE.get())
                .pattern("CAC")
                .pattern("ABA")
                .pattern("CAC")
                .define('A', Items.SCULK_CATALYST)
                .define('B', Items.NETHER_STAR)
                .define('C', Items.END_ROD)
                .unlockedBy("has_item", has(RileyModItems.EYE.get()))
                .save(pWriter);

    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                            pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer,  RileyMod.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}