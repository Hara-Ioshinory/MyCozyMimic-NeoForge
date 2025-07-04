package com.trubefactory.crabificationfest.items.custom;

import com.trubefactory.crabificationfest.effects.ModEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class Gum extends Item {
    public Gum(Properties properties) {super(properties);}

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 16;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!(entity instanceof Player player)) { return super.finishUsingItem(stack, level, entity); }
        if (!player.isCreative()) { stack.shrink(1); }

        MobEffectInstance crabification = player.getEffect(ModEffects.CRABIFICATION);
        if(crabification != null) {
            int duration = crabification.getDuration() - 2000;
            player.removeEffect(ModEffects.CRABIFICATION);
            if(duration > 0){
                player.addEffect(new MobEffectInstance(ModEffects.CRABIFICATION, duration, 0));
            }
            return stack;
        }

        MobEffectInstance crab_cadence = player.getEffect(ModEffects.CRAB_CADENCE);
        if(crab_cadence != null) {
            int duration = crab_cadence.getDuration();
            int amplifier = crab_cadence.getAmplifier() - 1;
            player.removeEffect(ModEffects.CRAB_CADENCE);
            if (!level.isClientSide()) {
                player.displayClientMessage(Component.translatable("crabificationfest.cadencelost"), true);
            }
            if (amplifier>=0){
                player.addEffect(new MobEffectInstance(ModEffects.CRAB_CADENCE, duration, amplifier));
                return stack;
            }
        }

        MobEffectInstance freshness = player.getEffect(ModEffects.FRESHNESS);
        if(freshness != null){
            player.removeEffect(ModEffects.FRESHNESS);
            int duration = freshness.getDuration() + 1200;
            if(duration > 4800){ duration = 4800; }
            player.addEffect(new MobEffectInstance(ModEffects.FRESHNESS, duration, 0));
        }
        else {
            player.addEffect(new MobEffectInstance(ModEffects.FRESHNESS, 1200, 0));
        }
        return stack;
    }
}
