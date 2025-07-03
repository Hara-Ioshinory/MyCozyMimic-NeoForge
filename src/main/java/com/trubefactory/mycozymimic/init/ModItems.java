package com.trubefactory.mycozymimic.init;

import com.trubefactory.mycozymimic.MyCozyMimic;
import com.trubefactory.mycozymimic.items.ClawItem;
import com.trubefactory.mycozymimic.registers.ItemRegistrar;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MyCozyMimic.MODID);
    private static final ItemRegistrar REGISTRAR = new ItemRegistrar(ITEMS);

    // Регистрация рессурсов
    public static final DeferredItem<Item> CHITIN_SHARDS = REGISTRAR.regItem("chitin_shards");
    public static final DeferredItem<Item> NEEDLES_BUNCH = REGISTRAR.regItem("needles_bunch");

    // Регистрация клешней
    public static final DeferredItem<ClawItem> RAW_CLAW =
            REGISTRAR.regItem("raw_claw", ClawItem::new, p -> p
            .food(f -> f.nutrition(2).saturation(0.2f))
    );
    public static final DeferredItem<ClawItem> FRIED_CLAW =
            REGISTRAR.regItem("fried_claw", ClawItem::new, p -> p
            .food(f -> f.nutrition(8).saturation(0.4f))
    );

    // Регистрация класса в шине событий
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}