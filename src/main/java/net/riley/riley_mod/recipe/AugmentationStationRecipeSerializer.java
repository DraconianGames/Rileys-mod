package net.riley.riley_mod.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

public class AugmentationStationRecipeSerializer implements RecipeSerializer<AugmentationStationRecipe> {

    public static final AugmentationStationRecipeSerializer INSTANCE = new AugmentationStationRecipeSerializer();

    @Override
    public AugmentationStationRecipe fromJson(ResourceLocation id, JsonObject json) {
        String catStr = GsonHelper.getAsString(json, "category");
        AugmentationStationRecipe.Category category = switch (catStr.toLowerCase()) {
            case "domain_expansions" -> AugmentationStationRecipe.Category.DOMAIN_EXPANSIONS;
            case "body_enhancements" -> AugmentationStationRecipe.Category.BODY_ENHANCEMENTS;
            default -> throw new IllegalArgumentException("Unknown augmentation category: " + catStr);
        };

        ResourceLocation augmentId = new ResourceLocation(GsonHelper.getAsString(json, "augment_id"));
        String description = GsonHelper.getAsString(json, "description", "");

        // icon shown in UI
        JsonObject iconObj = GsonHelper.getAsJsonObject(json, "icon");
        ResourceLocation iconItemId = new ResourceLocation(GsonHelper.getAsString(iconObj, "item"));
        Item iconItem = BuiltInRegistries.ITEM.get(iconItemId);
        ItemStack icon = new ItemStack(iconItem);

        JsonArray reqs = GsonHelper.getAsJsonArray(json, "requirements");
        NonNullList<Requirement> requirements = NonNullList.create();

        for (int i = 0; i < reqs.size(); i++) {
            JsonObject r = reqs.get(i).getAsJsonObject();
            ResourceLocation itemId = new ResourceLocation(GsonHelper.getAsString(r, "item"));
            int count = GsonHelper.getAsInt(r, "count", 1);
            Item item = BuiltInRegistries.ITEM.get(itemId);
            requirements.add(new Requirement(item, count));
        }
        int level = GsonHelper.getAsInt(json, "level", 1);

        return new AugmentationStationRecipe(id, category, augmentId, icon, description, requirements, level);
    }

    @Override
    public @Nullable AugmentationStationRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
        AugmentationStationRecipe.Category category = buf.readEnum(AugmentationStationRecipe.Category.class);
        ResourceLocation augmentId = buf.readResourceLocation();
        String description = buf.readUtf(32767);
        ItemStack icon = buf.readItem();

        int size = buf.readVarInt();
        NonNullList<Requirement> requirements = NonNullList.create();
        for (int i = 0; i < size; i++) {
            Item item = buf.readById(BuiltInRegistries.ITEM);
            int count = buf.readVarInt();
            requirements.add(new Requirement(item, count));
        }
        int level = buf.readVarInt();
        return new AugmentationStationRecipe(id, category, augmentId, icon, description, requirements, level);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, AugmentationStationRecipe recipe) {
        buf.writeEnum(recipe.getCategory());
        buf.writeResourceLocation(recipe.getAugmentId());
        buf.writeUtf(recipe.getDescription());
        buf.writeItem(recipe.getResultIcon());
        buf.writeVarInt(recipe.getRequirements().size());
        for (Requirement r : recipe.getRequirements()) {
            buf.writeId(BuiltInRegistries.ITEM, r.item());
            buf.writeVarInt(r.count());
            buf.writeVarInt(recipe.getLevel());
        }
    }
}