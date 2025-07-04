package com.trubefactory.crabificationfest.items.custom;

import com.trubefactory.crabificationfest.items.ModItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class RawClaw extends Claw {
    public RawClaw(Properties properties) {super(properties);}

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand){
        initInteractData(player, usedHand);
        if (secHandItem.is(Items.SHEARS)) { player.startUsingItem(usedHand); }

        return hasCrabification(player)
                ? super.use(level, player, usedHand)
                : InteractionResultHolder.fail(player.getItemInHand(usedHand));
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
            default: { return breakTeeth(player, level, stack); }
        }
    }
}