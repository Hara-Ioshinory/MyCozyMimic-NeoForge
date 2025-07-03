package com.trubefactory.crabificationfest.effects;

import com.trubefactory.crabificationfest.CrabificationFest;
import com.trubefactory.crabificationfest.effects.custom.CrabCadence;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, CrabificationFest.MODID);

    public static final Holder<MobEffect> CRAB_CADENCE = MOB_EFFECTS.register("crab_cadence",
            () -> new CrabCadence(MobEffectCategory.BENEFICIAL, 0x8c3428)
    );

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}