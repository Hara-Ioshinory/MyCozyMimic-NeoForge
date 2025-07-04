package com.trubefactory.crabificationfest.items.custom;

import com.trubefactory.crabificationfest.effects.ModEffects;
import com.trubefactory.crabificationfest.items.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class CrabMeat extends Item {
    public CrabMeat(Properties properties) {super(properties);}

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        return hasCrabification(player)
                ? super.use(level, player, usedHand)
                : InteractionResultHolder.fail(player.getItemInHand(usedHand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (!(livingEntity instanceof Player player)) { return super.finishUsingItem(stack, level, livingEntity); }
        if (!player.isCreative() && stack.is(ModItems.CRAB_RAMEN)) {
            player.addItem(new ItemStack(Items.BOWL));
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }

    protected boolean hasCrabification(Player player){
        return !player.hasEffect(ModEffects.CRABIFICATION);
    }
}