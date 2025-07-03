package com.trubefactory.mycozymimic;

import com.trubefactory.mycozymimic.init.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(MyCozyMimic.MODID)
public class MyCozyMimic {
    public static final String MODID = "mycozymimic"; // Замените на ваш MODID

    public MyCozyMimic(IEventBus modEventBus) {
        ModItems.register(modEventBus);
    }
}
