package net.riley.riley_mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.item.RileyModItems;

import java.util.LinkedHashMap;

public class RileyModItemModelProvider extends ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public RileyModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, RileyMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(RileyModItems.EYE);
        simpleItem(RileyModItems.FUNTIUM_ORE);
        simpleItem(RileyModItems.FUNTIUM_MIX);
        simpleItem(RileyModItems.STARDUST);
        simpleItem(RileyModItems.FUNTIUM_PLATE);
        simpleItem(RileyModItems.ICE_CHUNK);
        simpleItem(RileyModItems.CLAW);
        simpleItem(RileyModItems.TOOTH);
        simpleItem(RileyModItems.CAGGED_FAIRY);
        simpleItem(RileyModItems.LIVING_MACHANICAL_NURONS);
        simpleItem(RileyModItems.LYDAR);
        simpleItem(RileyModItems.MECHA_REX_UNASSEMBLED_HEAD);
        simpleItem(RileyModItems.MECHAREX_BRAIN);
        simpleItem(RileyModItems.MECHAREX_EGG);
        simpleItem(RileyModItems.MECHAREX_ENGINE);
        simpleItem(RileyModItems.MECHAREX_HEAD);
        simpleItem(RileyModItems.MECHAREX_LEG);
        simpleItem(RileyModItems.MECHAREX_TORSO);
        simpleItem(RileyModItems.MECHAREX_TAIL);
        simpleItem(RileyModItems.SYNTHETIC_MUSCLE);
        simpleItem(RileyModItems.UNASSEMBLED_MECHAREX_TORSO);
        simpleItem(RileyModItems.UNASSEMBLED_MECHAREX_LEG);
        simpleItem(RileyModItems.UNASSEMBLED_MECHAREX_TAIL);
        simpleItem(RileyModItems.ARMOR_PLATING);
        simpleItem(RileyModItems.CANNON_SHEILD);
        simpleItem(RileyModItems.ARTIFICIAL_ORGAN);
        simpleItem(RileyModItems.TOME);

        saplingItem(RileyModBlocks.ABYSS_SAPLING);

        withExistingParent(RileyModItems.SUNLESS_CRAB_SPAWN_EGG.getId().getPath(),mcLoc("item/template_spawn_egg"));
        withExistingParent(RileyModItems.RAPTER_SPAWN_EGG.getId().getPath(),mcLoc("item/template_spawn_egg"));
        withExistingParent(RileyModItems.NIGHT_TERROR_SPAWN_EGG.getId().getPath(),mcLoc("item/template_spawn_egg"));
        withExistingParent(RileyModItems.WHALE_HUNTER_SPAWN_EGG.getId().getPath(),mcLoc("item/template_spawn_egg"));
        withExistingParent(RileyModItems.FROST_HOPPER_SPAWN_EGG.getId().getPath(),mcLoc("item/template_spawn_egg"));
        withExistingParent(RileyModItems.TOOTHFAIRY_SPAWN_EGG.getId().getPath(),mcLoc("item/template_spawn_egg"));
        withExistingParent(RileyModItems.BONEFAIRY_SPAWN_EGG.getId().getPath(),mcLoc("item/template_spawn_egg"));
        withExistingParent(RileyModItems.SKELETONFAIRY_SPAWN_EGG.getId().getPath(),mcLoc("item/template_spawn_egg"));
        withExistingParent(RileyModItems.SKULL_FAIRY_SPAWN_EGG.getId().getPath(),mcLoc("item/template_spawn_egg"));
        withExistingParent(RileyModItems.BISON_EGG.getId().getPath(),mcLoc("item/template_spawn_egg"));

        fenceItem(RileyModBlocks.ABYSS_WOOD_FENCE, RileyModBlocks.ABYSS_PLANKS);
        
        wallItem(RileyModBlocks.ABYSS_WOOD_WALL, RileyModBlocks.ABYSS_PLANKS);

        evenSimplerBlockItem(RileyModBlocks.ABYSS_WOOD_STAIRS);
        evenSimplerBlockItem(RileyModBlocks.ABYSS_WOOD_SLAB);
        evenSimplerBlockItem(RileyModBlocks.ABYSS_WOOD_FENCE_GATE);

        fenceItem(RileyModBlocks.STRUCTURE_BRICK_FENCE, RileyModBlocks.STRUCTURE_BRICK);

        wallItem(RileyModBlocks.STRUCTURE_BRICK_WALL, RileyModBlocks.STRUCTURE_BRICK);

        evenSimplerBlockItem(RileyModBlocks.STRUCTURE_BRICK_STAIRS);
        evenSimplerBlockItem(RileyModBlocks.STRUCTURE_BRICK_SLAB);
        evenSimplerBlockItem(RileyModBlocks.STRUCTURE_BRICK_FENCE_GATE);


        trimmedArmorItem(RileyModItems.FUNTIUM_HELMET);
        trimmedArmorItem(RileyModItems.FUNTIUM_CHESTPLATE);
        trimmedArmorItem(RileyModItems.FUNTIUM_LEGGINGS);
        trimmedArmorItem(RileyModItems.FUNTIUM_BOOTS);

        flatBlockItem(RileyModBlocks.ABYSS_PORTAL);
        evenSimplerBlockItem2(RileyModBlocks.WHALE_HUNTER_TROPHY);
        evenSimplerBlockItem2(RileyModBlocks.MORPH_STATION);
        evenSimplerBlockItem2(RileyModBlocks.ENCHANTER);
        evenSimplerBlockItem2(RileyModBlocks.AUGMENTATION_STATION);
    }
    private ItemModelBuilder flatBlockItem(RegistryObject<Block> block) {
        return withExistingParent(block.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(RileyMod.MODID, "block/" + block.getId().getPath()));
    }
    // Shoutout to El_Redstoniano for making this
    private void trimmedArmorItem(RegistryObject<Item> itemRegistryObject) {
        final String MOD_ID = RileyMod.MODID;

        if(itemRegistryObject.get() instanceof ArmorItem armorItem) {
            trimMaterials.entrySet().forEach(entry -> {

                ResourceKey<TrimMaterial> trimMaterial = entry.getKey();
                float trimValue = entry.getValue();

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = "item/" + armorItem;
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = new ResourceLocation(MOD_ID, armorItemPath);
                ResourceLocation trimResLoc = new ResourceLocation(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = new ResourceLocation(MOD_ID, currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc)
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemRegistryObject.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                new ResourceLocation(MOD_ID,
                                        "item/" + itemRegistryObject.getId().getPath()));
            });
        }
    }
    private ItemModelBuilder saplingItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(RileyMod.MODID,"block/" + item.getId().getPath()));
    }
    public void evenSimplerBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(RileyMod.MODID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }
    //top for datagen, bottom for existing models
    public void evenSimplerBlockItem2(RegistryObject<Block> block) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                new ResourceLocation(RileyMod.MODID, "block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }
    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  new ResourceLocation(RileyMod.MODID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }
    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  new ResourceLocation(RileyMod.MODID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(RileyMod.MODID,"item/" + item.getId().getPath()));
    }
}