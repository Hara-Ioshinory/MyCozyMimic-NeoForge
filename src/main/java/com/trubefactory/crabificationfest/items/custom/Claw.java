package com.trubefactory.crabificationfest.items.custom;

import com.trubefactory.crabificationfest.effects.ModEffects;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class Claw extends CrabMeat {
    public Claw(Properties properties) {super(properties);}

    // Список предметов доступных для взаимодействия
    protected final List<Item> INTERACTION_ITEMS = List.of(
            Items.FLINT,
            Items.ARROW,
            Items.STICK,
            Items.SHEARS
    );

    protected ItemStack secHandItem = null;
    protected int interactionID = -1;

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand){
        initInteractData(player, usedHand);
        return hasCrabification(player)
                ? super.use(level, player, usedHand)
                : InteractionResultHolder.fail(player.getItemInHand(usedHand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity)
    {
        if (!(livingEntity instanceof Player player)) { return super.finishUsingItem(stack, level, livingEntity); }

        return switch (interactionID) {
            case 0, 1, 2 -> eatClaw(secHandItem, stack, player, level, livingEntity);
            case 3 -> eatClawWithShears(secHandItem, stack, player, level, livingEntity);
            default -> breakTeeth(player, level, stack);
        };
    }

    protected ItemStack breakTeeth(Player player, Level level, ItemStack stack){
        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 1));
        player.hurt(player.damageSources().generic(), 1.0F);
        if (!level.isClientSide()) {
            player.displayClientMessage(Component.translatable("crabificationfest.clawmessage"), true);
        }
        return stack;
    }

    private ItemStack eatClawWithShears(ItemStack offHand, ItemStack stack, Player player, Level level, LivingEntity livingEntity){
        giveCrabCadence(player, level);

        EquipmentSlot slot = player.getMainHandItem() == secHandItem
                ? EquipmentSlot.MAINHAND
                : EquipmentSlot.OFFHAND;
        secHandItem.hurtAndBreak(1, player, slot);
        ItemStack result = super.finishUsingItem(stack, level, livingEntity);
        return result.isEmpty() ? ItemStack.EMPTY : result;
    }

    protected ItemStack eatClaw(ItemStack offHand, ItemStack stack, Player player, Level level, LivingEntity livingEntity) {
        giveCrabCadence(player, level);

        if (level.getRandom().nextFloat() < 0.4f && !level.isClientSide()) {
            if (!player.isCreative()) { offHand.shrink(1); }
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ITEM_BREAK, SoundSource.PLAYERS,
                    0.8F, 0.8F + level.getRandom().nextFloat() * 0.4F);
        }

        ItemStack result = super.finishUsingItem(stack, level, livingEntity);
        return result.isEmpty() ? ItemStack.EMPTY : result;
    }

    protected void initInteractData(Player player, InteractionHand useHand) {
        secHandItem = player.getItemInHand(useHand) == player.getMainHandItem()
                ? player.getOffhandItem()
                : player.getMainHandItem();
        interactionID = INTERACTION_ITEMS.indexOf(secHandItem.getItem());
    }

    private void giveCrabCadence(Player player, Level level){
        player.removeEffect(ModEffects.FRESHNESS);
        MobEffectInstance effect = player.getEffect(ModEffects.CRAB_CADENCE);

        if (effect != null){
            int amplifier = effect.getAmplifier();
            switch (amplifier){
                case 0: {
                    player.addEffect(new MobEffectInstance(ModEffects.CRAB_CADENCE, 1200, 1));
                    break;
                }
                case 1: {
                    player.addEffect(new MobEffectInstance(ModEffects.CRAB_CADENCE, 2400, 2));
                    if(level.getRandom().nextFloat() < 0.4f){
                        player.addEffect(new MobEffectInstance(ModEffects.CRABIFICATION, 10000, 0));
                    }
                    break;
                }
                case 2: {
                    player.removeEffect(ModEffects.CRAB_CADENCE);
                    player.addEffect(new MobEffectInstance(ModEffects.CRAB_CADENCE, 2400, 2));
                    if(level.getRandom().nextFloat() < 0.6f){
                        player.addEffect(new MobEffectInstance(ModEffects.CRABIFICATION, 10000, 0));
                    }
                    break;
                }
            }
        }
        else { player.addEffect(new MobEffectInstance(ModEffects.CRAB_CADENCE, 800, 0)); }
    }
}