package me.majeek.lunarphase;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("lunarphase")
public class Main {
    public Main() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRegisterCommand(RegisterCommandsEvent event) {
        new PhaseCommand(event.getDispatcher());
    }
}
