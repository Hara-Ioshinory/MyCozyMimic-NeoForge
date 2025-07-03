package com.trubefactory.crabificationfest.items;

import com.trubefactory.crabificationfest.CrabificationFest;
import com.trubefactory.crabificationfest.items.custom.Claw;
import com.trubefactory.crabificationfest.items.custom.Gum;
import com.trubefactory.crabificationfest.registers.ItemRegistrar;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CrabificationFest.MODID);
    private static final ItemRegistrar REGISTRAR = new ItemRegistrar(ITEMS);

    // Регистрация простых предметов
    public static final DeferredItem<Gum> GUM = REGISTRAR.regDef("gum", Gum::new);
    public static final DeferredItem<Item> CHITIN_SHARDS = REGISTRAR.regDef("chitin_shards");
    public static final DeferredItem<Item> NEEDLES_BUNCH = REGISTRAR.regDef("needles_bunch");

    // Регистрация простой еды
    public static final DeferredItem<Item> RAW_CRAB_MEAT = REGISTRAR.regItem("raw_crab_meat",
            p -> p.food(f -> f.nutrition(2).saturation(0.4F)
    ));

    // Регистрация кастомной еды
    public static final DeferredItem<Claw> RAW_CLAW =
            REGISTRAR.regItem("raw_claw", Claw::new, p -> p
            .food(f -> f.nutrition(2).saturation(0.2f))
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