package net.riley.riley_mod.item.custom;


    import com.google.common.collect.ImmutableMap;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
    import net.minecraft.world.entity.Entity;
    import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
    import net.riley.riley_mod.item.RileyModArmorMaterials;

    import java.util.Map;
    public class RileyModArmorItem extends ArmorItem {
        private static final Map<ArmorMaterial, MobEffectInstance> MATERIAL_TO_EFFECT_MAP =
                (new ImmutableMap.Builder<ArmorMaterial, MobEffectInstance>())
                        .put(RileyModArmorMaterials.EYE, new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20000, 1,
                                false,false, true)).build();
        private static final Map<ArmorMaterial, MobEffectInstance> MATERIAL_TO_EFFECT_MAP_2 =
                (new ImmutableMap.Builder<ArmorMaterial, MobEffectInstance>())
                        .put(RileyModArmorMaterials.EYE, new MobEffectInstance(MobEffects.CONDUIT_POWER, 20000, 3,
                                false,false, true)).build();
        private static final Map<ArmorMaterial, MobEffectInstance> MATERIAL_TO_EFFECT_MAP_3 =
                (new ImmutableMap.Builder<ArmorMaterial, MobEffectInstance>())
                        .put(RileyModArmorMaterials.EYE, new MobEffectInstance(MobEffects.HEALTH_BOOST, 20000, 18,
                                false,false, true)).build();
        private static final Map<ArmorMaterial, MobEffectInstance> MATERIAL_TO_EFFECT_MAP_1 =
                (new ImmutableMap.Builder<ArmorMaterial, MobEffectInstance>())
                        .put(RileyModArmorMaterials.EYE, new MobEffectInstance(MobEffects.REGENERATION, 20000, 2,
                                false,false, true)).build();





        public RileyModArmorItem(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
            super(pMaterial, pType, pProperties);
        }

        @Override
        public void inventoryTick(ItemStack pStack, Level world, Entity entity, int pSlotId, boolean pIsSelected) {
            if(!world.isClientSide()){
                if(entity instanceof Player) {
                    Player player = (Player)entity;

                    if(hasFullSuitOfArmorOn(player)){
                        evaluateArmorEffects(player);
                    }
                }
            }
        }

        private void evaluateArmorEffects(Player player) {
            for (Map.Entry<ArmorMaterial, MobEffectInstance> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
                ArmorMaterial mapArmorMaterial = entry.getKey();
                MobEffectInstance mapStatusEffect = entry.getValue();

                if(hasCorrectArmorOn(mapArmorMaterial, player)) {
                    addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
                }
            }
            for (Map.Entry<ArmorMaterial, MobEffectInstance> entry : MATERIAL_TO_EFFECT_MAP_2.entrySet()) {
                ArmorMaterial mapArmorMaterial = entry.getKey();
                MobEffectInstance mapStatusEffect = entry.getValue();

                if(hasCorrectArmorOn(mapArmorMaterial, player)) {
                    addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
                }
            }
            for (Map.Entry<ArmorMaterial, MobEffectInstance> entry : MATERIAL_TO_EFFECT_MAP_3.entrySet()) {
                ArmorMaterial mapArmorMaterial = entry.getKey();
                MobEffectInstance mapStatusEffect = entry.getValue();

                if(hasCorrectArmorOn(mapArmorMaterial, player)) {
                    addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
                }
            }
            for (Map.Entry<ArmorMaterial, MobEffectInstance> entry : MATERIAL_TO_EFFECT_MAP_1.entrySet()) {
                ArmorMaterial mapArmorMaterial = entry.getKey();
                MobEffectInstance mapStatusEffect = entry.getValue();

                if(hasCorrectArmorOn(mapArmorMaterial, player)) {
                    addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
                }
            }

        }


        private void addStatusEffectForMaterial(Player player, ArmorMaterial mapArmorMaterial,
                                                MobEffectInstance mapStatusEffect) {
            boolean hasPlayerEffect = player.hasEffect(mapStatusEffect.getEffect());

            if(hasCorrectArmorOn(mapArmorMaterial, player) && !hasPlayerEffect) {
                player.addEffect(new MobEffectInstance(mapStatusEffect));
            }
        }

        private boolean hasFullSuitOfArmorOn(Player player) {
            ItemStack boots = player.getInventory().getArmor(0);
            ItemStack leggings = player.getInventory().getArmor(1);
            ItemStack breastplate = player.getInventory().getArmor(2);
            ItemStack helmet = player.getInventory().getArmor(3);

            return !helmet.isEmpty() && !breastplate.isEmpty()
                    && !leggings.isEmpty() && !boots.isEmpty();
        }

        private boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
            for (ItemStack armorStack : player.getInventory().armor) {
                if(!(armorStack.getItem() instanceof ArmorItem)) {
                    return false;
                }
            }

            ArmorItem boots = ((ArmorItem)player.getInventory().getArmor(0).getItem());
            ArmorItem leggings = ((ArmorItem)player.getInventory().getArmor(1).getItem());
            ArmorItem breastplate = ((ArmorItem)player.getInventory().getArmor(2).getItem());
            ArmorItem helmet = ((ArmorItem)player.getInventory().getArmor(3).getItem());

            return helmet.getMaterial() == material && breastplate.getMaterial() == material &&
                    leggings.getMaterial() == material && boots.getMaterial() == material;
        }
    }

