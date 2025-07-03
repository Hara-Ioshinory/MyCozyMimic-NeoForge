package com.trubefactory.crabificationfest.items.custom;

import com.trubefactory.crabificationfest.items.ModItems;
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
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class Claw extends Item {
    public Claw(Properties properties) { super(properties); }

    // Список предметов доступных для взаимодействия
    private final List<Item> INTERACTION_ITEMS = List.of(
            Items.FLINT,
            Items.ARROW,
            Items.STICK,
            Items.SHEARS
    );
    private ItemStack secHandItem = null;
    private int interactionID = -1;

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand){
        initInteractData(player, usedHand);
        if (secHandItem.is(Items.SHEARS)) { player.startUsingItem(usedHand); }

        return super.use(level, player, usedHand);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return secHandItem.is(Items.SHEARS) ? 16 : super.getUseDuration(stack, entity);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity)
    {
        if (!(livingEntity instanceof Player player)) { return super.finishUsingItem(stack, level, livingEntity); }

        switch (interactionID) {
            case 0, 1, 2: {return eatClaw(secHandItem, stack, player, level, livingEntity);}
            case 3: {
                player.addItem(new ItemStack((ItemLike) ModItems.RAW_CRAB_MEAT));
                if (level.getRandom().nextFloat() < 0.4) {
                    player.addItem(new ItemStack((ItemLike) ModItems.CHITIN_SHARDS));
                }

                EquipmentSlot slot = player.getMainHandItem() == secHandItem
                        ? EquipmentSlot.MAINHAND
                        : EquipmentSlot.OFFHAND;
                secHandItem.hurtAndBreak(2, player, slot);

                stack.shrink(1);
                return stack;
            }
            default: {
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 1));
                player.hurt(player.damageSources().generic(), 1.0F);
                if (!level.isClientSide()) {
                    player.displayClientMessage(Component.literal("Нужно что-то острое..."), true);
                }
                return stack;
            }
        }
    }

    private ItemStack eatClaw(ItemStack offHand, ItemStack stack, Player player, Level level, LivingEntity livingEntity) {
        if (level.getRandom().nextFloat() < 0.4f && !level.isClientSide()) {
            offHand.shrink(1);
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ITEM_BREAK, SoundSource.PLAYERS,
                    0.8F, 0.8F + level.getRandom().nextFloat() * 0.4F);
        }

        ItemStack result = super.finishUsingItem(stack, level, livingEntity);
        return result.isEmpty() ? ItemStack.EMPTY : result;
    }

    private void initInteractData(Player player, InteractionHand useHand) {
        secHandItem = player.getItemInHand(useHand) == player.getMainHandItem()
                ? player.getOffhandItem()
                : player.getMainHandItem();
        interactionID = INTERACTION_ITEMS.indexOf(secHandItem.getItem());
    }
}