package net.riley.riley_mod.entity.client; // use the same package as JournalScreen

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.riley.riley_mod.effect.RileyModEffects;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.item.RileyModItems;
import net.riley.riley_mod.util.JournalEntry;

import java.util.List;

public final class JournalEntries {
    private JournalEntries() {}

    public static List<JournalEntry> create() {
        return List.of(
                //Creaturse
          new JournalEntry("Bison","A new animal spawing only in the plains biome. Tame it like a horse.", JournalEntry.Category.CREATURES,RileyModEntities.BISON.get(), 22.0f, Items.CARROT,
                  java.util.List.of(),null),
        new JournalEntry("Sunless Crab", " They do like amethyst shards, and are neutral.", JournalEntry.Category.CREATURES, RileyModEntities.SUNLESS_CRAB.get(),22.0f, Items.AMETHYST_SHARD
                ,java.util.List.of(),null),
        new JournalEntry("Whale Hunter", "A massive predator deep sea predator. They are neutral. They don't like boats.", JournalEntry.Category.CREATURES, RileyModEntities.WHALE_HUNTER.get(),5.0f,null,
                java.util.List.of(),null),
        new JournalEntry("Rapter", "Loves cooked rabbit. They are neutral, but they are opportunistic. They are stronger than their cousin the Frost Hopper.", JournalEntry.Category.CREATURES, RileyModEntities.RAPTER.get(),15.0f,net.minecraft.world.item.Items.COOKED_RABBIT,
                java.util.List.of(new MobEffectInstance(RileyModEffects.BLEED.get(), 100, 0)),null),
        new JournalEntry("Night Terror","A Winged Nightmare that loves flying way too much. It's roar will make you go deaf temporary", JournalEntry.Category.CREATURES, RileyModEntities.NIGHT_TERROR.get(),7.0f, RileyModItems.CLAW.get(),
                java.util.List.of(new MobEffectInstance(RileyModEffects.DEAF.get(), 6000, 0)),null),
        new JournalEntry("Frost Hopper","Lives Obsidian Peaks in the abyss. Smaller than they rapter, they developed a poison that will freeze you in place, it comes out of the horn on their forehead. They hunt in packs.", JournalEntry.Category.CREATURES, RileyModEntities.FROST_HOPPER.get(),25.0f,net.minecraft.world.item.Items.COOKED_RABBIT,
                java.util.List.of(new MobEffectInstance(RileyModEffects.FREEZE.get(), 60, 0)),null),
        new JournalEntry("Tooth Fairy", "An extremely docile creature looking for protection. They are more useless than a bat. They do like bones for some reason.", JournalEntry.Category.CREATURES, RileyModEntities.TOOTHFAIRY.get(),25.0f, RileyModItems.TOOTH.get(),
                java.util.List.of(),null),
        new JournalEntry("Bone Fairy", "The larger, much deadlier evolutionary cousin of the Tooth Fairy. It can throw a pretty good punch. They do like skeleton skulls for some reason.", JournalEntry.Category.CREATURES, RileyModEntities.BONEFAIRY.get(), 5.0f, RileyModItems.TOOTH.get(),
                java.util.List.of(),null),
        new JournalEntry("Skeleton Fairy", "After countless fights, the Bone Fairie's claws wore down, force to only be used as feet. It grew too heavy to stand upright, and developed a nice set of fangs. It also developed more eyes for better precision.", JournalEntry.Category.CREATURES, RileyModEntities.SKELETONFAIRY.get(), 10.0f, RileyModItems.TOOTH.get(),
                java.util.List.of(),null),
        new JournalEntry("Skull Fairy","The final evolution of the tooth fairy. It is now fire proof.", JournalEntry.Category.CREATURES, RileyModEntities.SKULL_FAIRY.get(), 5.0f, RileyModItems.TOOTH.get(),
                java.util.List.of(),null),
        new JournalEntry("Mecha Rex", "Only found within a strange arena in the Fallow. Its missiles will track their target.", JournalEntry.Category.CREATURES, RileyModEntities.MECHAREX.get(), 5.0f, null,
                java.util.List.of(),null),
        new JournalEntry("Mecha Terror", "These are harder to fight than their boss, the Mecha Rex ", JournalEntry.Category.CREATURES, RileyModEntities.MECHA_TERROR.get(), 3.0f, null,
                java.util.List.of(),null),
        //Blocks
        new JournalEntry("Abyss Log", "Wood harvested from the trees of the abyss.", JournalEntry.Category.BLOCKS),
        new JournalEntry("Black Sand","Much like soul sand, this stuff will slow you down. It caught me by surprise while exploring the beach for the first time." , JournalEntry.Category.BLOCKS),
        new JournalEntry("Activated Funtium", "To get this, you mest first get a blast furnace. smelt funtium ore into funtium plate, combine 9 into one funtium block, then blast smelt it again into activated funtium.", JournalEntry.Category.BLOCKS),
        new JournalEntry("Special Spawner","When one gets close enough, the selected mob will spawn. If you got close enough to one without this happening, I probably forgot to set the mob for said block. You too can set the spawn in Creative mode, just right click on the block.", JournalEntry.Category.BLOCKS),
        //Items
        new JournalEntry("Eye", "To craft the eye, you need 4 obsidian, 1 activated funtium, and 4 glowstone dust. Activated funtium in the middle, glowstone dust in the corners, an the obsidian fills the rest.", JournalEntry.Category.ITEMS),
        new JournalEntry("Caged Fairy","A baby Tooth fairy is held within. Right click on any surface to release it. Make sure to tame it before it wanders off.", JournalEntry.Category.ITEMS),
        new JournalEntry("Funtium Ore", "A DENSE material containing mystical properties, you do need Netherite to mine it because it is so dense.", JournalEntry.Category.ITEMS),
        new JournalEntry("Mecha Rex Egg", "You will need to work hard to obtain such a powerful boss as a pet, just let it grow up first before attacking anything, it kinda looks ridiculous attacking things as a baby. SAVE it to your Journal. Your responsible for it now.", JournalEntry.Category.ITEMS),
        new JournalEntry("Dark Journal","An appendix containing mystical properties. The pets section allows you to revive and store your pet. One must store their pet in the journal before hand. Right click mounts to save them in the journal. Don't loose your journal, you will loose your pets.",JournalEntry.Category.ITEMS),
        new JournalEntry("Tome", "A rare item found in Wither Skeleton Rooms", JournalEntry.Category.ITEMS),
        //Misc
        new JournalEntry("The Abyss", "A dimension where messing with the wrong thing can be deadly. It has a range of terrifying creatures, unique flora, and for some reason, it's only night. Never let the creatures out unless they are tamed.", JournalEntry.Category.ABYSS),
        new JournalEntry("The Abyss Portal", "A portal built exactly like the Nether portal, but you need activated funtium as its frame, and the eye to light it", JournalEntry.Category.ABYSS),
        new JournalEntry("Fallow Dimension", "A place where no life can be found. Only the remnants of civilization remain as robots now rule the land. The whole area has been leveled by war and the detritus still falls from the old machines destroyed in the sky.", JournalEntry.Category.FALLOW),
        new JournalEntry("The Fallow Portal","A portal built exactly like the Nether portal, but you need fallow portal frame as its frame, and a tome to light it", JournalEntry.Category.FALLOW),
        //Structures
        new JournalEntry("The Arena", "It has good loot but only spawn in the abyss", JournalEntry.Category.STRUCTURES),
        new JournalEntry("The Avalon", "A place where the weary traveler can shelter out the storms. Spawns in the overworld. Designed by Avalon herself", JournalEntry.Category.STRUCTURES),
        new JournalEntry("Wither Skeleton","A room with wither skeleton spawner and good loot", JournalEntry.Category.STRUCTURES),
        new JournalEntry("Mecha Arena", "An arena found within the Fallow Dimension. You are safe on the outside, but within, the mecharex sleeps.", JournalEntry.Category.STRUCTURES)
   
        );
    }
}