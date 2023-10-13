package me.majeek.lunarphase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.server.ServerWorld;

import java.util.Arrays;
import java.util.List;

// SOURCES:
// https://minecraft.fandom.com/wiki/Moon
// https://pixelmonmod.com/wiki/Spawn_time
public class WorldUtil {
    public static final int DAY_TICK_AMOUNT = 24000;

    public static final int NIGHT_FIRST_TICK = 13450;
    public static final int NIGHT_LAST_TICK = 22550;

    public static int getAge(ServerWorld world) {
        return (int) (world.getDayTime() / WorldUtil.DAY_TICK_AMOUNT);
    }

    public static LunarPhase getMoonPhase(ServerWorld world) {
        return LunarPhase.fromOrder(WorldUtil.getAge(world) % 8);
    }

    public static int getTime(ServerWorld world) {
        return (int) (world.getDayTime() % WorldUtil.DAY_TICK_AMOUNT);
    }

    public static boolean isNightTime(ServerWorld world) {
        int time = WorldUtil.getTime(world);
        return time >= WorldUtil.NIGHT_FIRST_TICK && time <= WorldUtil.NIGHT_LAST_TICK;
    }

    public static int timeUntilLunarPhase(ServerWorld world, LunarPhase targetPhase) {
        LunarPhase currentPhase = WorldUtil.getMoonPhase(world);
        int currentTime = WorldUtil.getTime(world);
        List<LunarPhase> lunarPhases = Arrays.asList(LunarPhase.orderedValues());

        int modifier = currentTime < WorldUtil.NIGHT_FIRST_TICK ? 0 : lunarPhases.size();
        if(!currentPhase.equals(targetPhase))
            modifier = WorldUtil.distanceBetweenIndexes(lunarPhases.indexOf(currentPhase), lunarPhases.indexOf(targetPhase), lunarPhases.size());

        return (WorldUtil.NIGHT_FIRST_TICK - currentTime) + (WorldUtil.DAY_TICK_AMOUNT * modifier);
    }

    private static int distanceBetweenIndexes(int from, int to, int size) {
        return from < to ? to - from : to - from + size;
    }
}
