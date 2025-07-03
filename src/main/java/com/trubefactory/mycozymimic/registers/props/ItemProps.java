package com.trubefactory.mycozymimic.registers.props;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.Consumer;

public class ItemProps {
    private final Item.Properties properties;
    private FoodProps foodProps;

    private ItemProps() {
        this.properties = new Item.Properties();
    }

    public static ItemProps create() {
        return new ItemProps();
    }

    public ItemProps stacksTo(int count) {
        properties.stacksTo(count);
        return this;
    }

    public ItemProps rarity(Rarity rarity) {
        properties.rarity(rarity);
        return this;
    }

    public ItemProps fireResistant() {
        properties.fireResistant();
        return this;
    }

    public ItemProps food(Consumer<FoodProps> configurator) {
        this.foodProps = FoodProps.create(); // Создаём новый билдер
        configurator.accept(this.foodProps); // Конфигурируем
        return this; // ← Ключевое изменение: возвращаем this для цепочки
    }

    public Item.Properties build() {
        if (foodProps != null) {
            properties.food(foodProps.build());
        }
        return properties;
    }
}
