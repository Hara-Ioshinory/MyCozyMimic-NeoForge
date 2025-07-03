package com.trubefactory.crabificationfest.items;

import com.trubefactory.crabificationfest.CrabificationFest;
import com.trubefactory.crabificationfest.items.custom.*;
import com.trubefactory.crabificationfest.registers.ItemRegistrar;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CrabificationFest.MODID);
    private static final ItemRegistrar REGISTRAR = new ItemRegistrar(ITEMS);

    // Регистрация простых предметов
    public static final DeferredItem<Item> CHITIN_SHARDS = REGISTRAR.regDef("chitin_shards");
    public static final DeferredItem<Item> NEEDLES_BUNCH = REGISTRAR.regDef("needles_bunch");
    public static final DeferredItem<CrabRoe> CRAB_ROE = REGISTRAR.regDef("crab_roe", CrabRoe::new);
    public static final DeferredItem<Gum> GUM = REGISTRAR.regDef("gum", Gum::new);

    // Регистрация простой еды
    public static final DeferredItem<CrabMeat> RAW_CRAB_MEAT = REGISTRAR.regItem("raw_crab_meat", CrabMeat::new,p -> p
            .food(f -> f.nutrition(6).saturation(0.4F)
    ));
    public static final DeferredItem<CrabMeat> CONIFEROUS_ROLL = REGISTRAR.regItem("coniferous_roll", CrabMeat::new,p -> p
            .food(f -> f.nutrition(8).saturation(0.4F)
    ));
    public static final DeferredItem<CrabMeat> CRAB_RAMEN = REGISTRAR.regItem("crab_ramen", CrabMeat::new, p -> p
            .food(f -> f.nutrition(10).saturation(0.5F))
            .rarity(Rarity.UNCOMMON)
    );


    // Регистрация кастомной еды
    public static final DeferredItem<RawClaw> RAW_CLAW =
            REGISTRAR.regItem("raw_claw", RawClaw::new, p -> p
            .food(f -> f.nutrition(2).saturation(0.2f)
                    .effect(() -> new MobEffectInstance(MobEffects.POISON, 150), 0.4F))
    );
    public static final DeferredItem<Claw> FRIED_CLAW =
            REGISTRAR.regItem("fried_claw", Claw::new, p -> p
                    .food(f -> f.nutrition(6).saturation(0.6f))
                    .rarity(Rarity.UNCOMMON)
    );

    // Регистрация класса в шине событий
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}