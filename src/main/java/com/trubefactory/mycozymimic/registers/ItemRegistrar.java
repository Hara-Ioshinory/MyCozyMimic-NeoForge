package com.trubefactory.mycozymimic.registers;

import com.trubefactory.mycozymimic.registers.props.ItemProps;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ItemRegistrar {
    private final DeferredRegister.Items items;

    public ItemRegistrar(DeferredRegister.Items items) {
        this.items = items;
    }

    private <T extends Item> DeferredItem<T> regMaster(String name, Supplier<T> itemSupplier) {
        return items.register(name, itemSupplier);
    }

    public DeferredItem<Item> regItem(String name) {
        return regItem(name, props -> {});
    }

    public DeferredItem<Item> regItem(String name, Consumer<ItemProps> configurator) {
        ItemProps builder = ItemProps.create();
        configurator.accept(builder);
        return regMaster(name, () -> new Item(builder.build()));
    }

    public <T extends Item> DeferredItem<T> regItem(String name, Function<Item.Properties, T> itemFactory,
                                                               Consumer<ItemProps> configurator) {
        ItemProps builder = ItemProps.create();
        configurator.accept(builder);
        return regMaster(name, () -> itemFactory.apply(builder.build()));
    }
}