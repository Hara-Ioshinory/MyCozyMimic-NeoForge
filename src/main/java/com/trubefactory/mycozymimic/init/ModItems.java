package com.trubefactory.mycozymimic.init;

import com.trubefactory.mycozymimic.MyCozyMimic;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MyCozyMimic.MODID);

    //Регистрация ресурсов
    private static DeferredItem<Item> regItem(String name, Item.Properties props) {
        return ITEMS.register(name, () -> new Item(props));
    }
    public static final DeferredItem<Item> CHITIN_SHARDS = regItem("chitin_shards", new Item.Properties());
    public static final DeferredItem<Item> NEEDLES_BUNCH = regItem("needles_bunch", new Item.Properties());

    //Регистрация класса в шине событий
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
