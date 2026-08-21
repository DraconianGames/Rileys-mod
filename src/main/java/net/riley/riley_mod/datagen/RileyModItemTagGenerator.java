package net.riley.riley_mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.item.RileyModItems;
import net.riley.riley_mod.util.RileyModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class RileyModItemTagGenerator extends ItemTagsProvider {
    public RileyModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                                    CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, RileyMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(RileyModTags.Items.SOULS)
                .add(RileyModItems.BAT_SOUL.get())
                .add(RileyModItems.AXOLOTL_SOUL.get())
                .add(RileyModItems.BEE_SOUL.get())
                .add(RileyModItems.BLAZE_SOUL.get())
                .add(RileyModItems.CAT_SOUL.get())
                .add(RileyModItems.CAMEL_SOUL.get())
                .add(RileyModItems.CAVE_SPIDER_SOUL.get())
                .add(RileyModItems.CHICKEN_SOUL.get())
                .add(RileyModItems.COD_SOUL.get())
                .add(RileyModItems.COW_SOUL.get())
                .add(RileyModItems.CREEPER_SOUL.get())
                .add(RileyModItems.DOLPHIN_SOUL.get())
                .add(RileyModItems.DONKEY_SOUL.get())
                .add(RileyModItems.DROWNED_SOUL.get())
                .add(RileyModItems.ELDER_GUARDIAN_SOUL.get())
                .add(RileyModItems.ENDERMAN_SOUL.get())
                .add(RileyModItems.ENDERMITE_SOUL.get())
                .add(RileyModItems.EVOKER_SOUL.get())
                .add(RileyModItems.FOX_SOUL.get())
                .add(RileyModItems.FROG_SOUL.get())
                .add(RileyModItems.GHAST_SOUL.get())
                .add(RileyModItems.GLOW_SQUID_SOUL.get())
                .add(RileyModItems.GOAT_SOUL.get())
                .add(RileyModItems.GUARDIAN_SOUL.get())
                .add(RileyModItems.HOGLIN_SOUL.get())
                .add(RileyModItems.HORSE_SOUL.get())
                .add(RileyModItems.HUSK_SOUL.get())
                .add(RileyModItems.IRON_GOLEM_SOUL.get())
                .add(RileyModItems.LLAMA_SOUL.get())
                .add(RileyModItems.MAGMA_CUBE_SOUL.get())
                .add(RileyModItems.MOOSHROOM_SOUL.get())
                .add(RileyModItems.MULE_SOUL.get())
                .add(RileyModItems.OCELOT_SOUL.get())
                .add(RileyModItems.PANDA_SOUL.get())
                .add(RileyModItems.PARROT_SOUL.get())
                .add(RileyModItems.PHANTOM_SOUL.get())
                .add(RileyModItems.PIG_SOUL.get())
                .add(RileyModItems.PIGLIN_SOUL.get())
                .add(RileyModItems.PIGLIN_BRUTE_SOUL.get())
                .add(RileyModItems.PILLAGER_SOUL.get())
                .add(RileyModItems.POLAR_BEAR_SOUL.get())
                .add(RileyModItems.PUFFERFISH_SOUL.get())
                .add(RileyModItems.RABBIT_SOUL.get())
                .add(RileyModItems.RAVAGER_SOUL.get())
                .add(RileyModItems.SALMON_SOUL.get())
                .add(RileyModItems.SHEEP_SOUL.get())
                .add(RileyModItems.SHULKER_SOUL.get())
                .add(RileyModItems.SILVERFISH_SOUL.get())
                .add(RileyModItems.SKELETON_SOUL.get())
                .add(RileyModItems.SKELETON_HORSE_SOUL.get())
                .add(RileyModItems.SLIME_SOUL.get())
                .add(RileyModItems.SNIFFER_SOUL.get())
                .add(RileyModItems.SNOW_GOLEM_SOUL.get())
                .add(RileyModItems.SPIDER_SOUL.get())
                .add(RileyModItems.SQUID_SOUL.get())
                .add(RileyModItems.STRAY_SOUL.get())
                .add(RileyModItems.STRIDER_SOUL.get())
                .add(RileyModItems.TADPOLE_SOUL.get())
                .add(RileyModItems.TRADER_LLAMA_SOUL.get())
                .add(RileyModItems.TROPICAL_FISH_SOUL.get())
                .add(RileyModItems.TURTLE_SOUL.get())
                .add(RileyModItems.VEX_SOUL.get())
                .add(RileyModItems.VILLAGER_SOUL.get())
                .add(RileyModItems.VINDICATOR_SOUL.get())
                .add(RileyModItems.WANDERING_TRADER_SOUL.get())
                .add(RileyModItems.WARDEN_SOUL.get())
                .add(RileyModItems.WITCH_SOUL.get())
                .add(RileyModItems.WITHER_SOUL.get())
                .add(RileyModItems.WITHER_SKELETON_SOUL.get())
                .add(RileyModItems.WOLF_SOUL.get())
                .add(RileyModItems.ZOGLIN_SOUL.get())
                .add(RileyModItems.ZOMBIE_SOUL.get())
                .add(RileyModItems.ZOMBIE_HORSE_SOUL.get())
                .add(RileyModItems.ZOMBIE_VILLAGER_SOUL.get())
                .add(RileyModItems.ZOMBIFIED_PIGLIN_SOUL.get())
                .add(RileyModItems.ENDER_DRAGON_SOUL.get());
        this.tag(RileyModTags.Items.MEAT)
                .add(Items.BEEF,
                        Items.COOKED_BEEF,
                        Items.SALMON,
                        Items.COOKED_SALMON,
                        Items.PUFFERFISH,
                        Items.COD,
                        Items.COOKED_COD,
                        Items.CHICKEN,
                        Items.COOKED_CHICKEN,
                        Items.RABBIT,
                        Items.COOKED_RABBIT,
                        Items.PORKCHOP,
                        Items.COOKED_PORKCHOP);

        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(RileyModItems.FUNTIUM_HELMET.get(),
                        RileyModItems.FUNTIUM_CHESTPLATE.get(),
                        RileyModItems.FUNTIUM_LEGGINGS.get(),
                        RileyModItems.FUNTIUM_BOOTS.get());

        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(RileyModBlocks.ABYSS_LOG.get().asItem())
                .add(RileyModBlocks.ABYSS_WOOD.get().asItem())
                .add(RileyModBlocks.STRIPPED_ABYSS_LOG.get().asItem())
                .add(RileyModBlocks.STRIPPED_ABYSS_WOOD.get().asItem());

        this.tag(ItemTags.PLANKS)
                .add(RileyModBlocks.ABYSS_PLANKS.get().asItem());
    }
}