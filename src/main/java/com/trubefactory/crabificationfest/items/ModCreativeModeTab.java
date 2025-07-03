package com.trubefactory.crabificationfest.items;

import com.trubefactory.crabificationfest.CrabificationFest;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CrabificationFest.MODID);

    public static final Supplier<CreativeModeTab> MY_COZY_MIMIC = CREATIVE_MODE_TAB.register("my_cozy_mimic_items",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.RAW_CLAW.get()))
                    .title(Component.translatable("crabificationfest.creativetab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.CRAB_ROE);
                        output.accept(ModItems.CHITIN_SHARDS);
                        output.accept(ModItems.NEEDLES_BUNCH);
                        output.accept(ModItems.GUM);
                        output.accept(ModItems.RAW_CRAB_MEAT);
                        output.accept(ModItems.CONIFEROUS_ROLL);
                        output.accept(ModItems.CRAB_RAMEN);
                        output.accept(ModItems.RAW_CLAW);
                        output.accept(ModItems.FRIED_CLAW);
                    })
                    .build()
    );

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
