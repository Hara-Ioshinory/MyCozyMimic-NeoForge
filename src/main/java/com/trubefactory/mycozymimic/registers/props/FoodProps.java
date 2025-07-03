package com.trubefactory.mycozymimic.registers.props;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

import java.util.function.Supplier;

public class FoodProps {
    private final FoodProperties.Builder builder;

    private FoodProps() {
        this.builder = new FoodProperties.Builder();
    }

    public static FoodProps create() {
        return new FoodProps();
    }

    public FoodProps nutrition(int nutrition) {
        builder.nutrition(nutrition);
        return this;
    }

    public FoodProps saturation(float saturation) {
        builder.saturationModifier(saturation);
        return this;
    }

    public FoodProps effect(Supplier<MobEffectInstance> effect, float probability) {
        builder.effect(effect, probability);
        return this;
    }

    public FoodProperties build() {
        return builder.build();
    }
}