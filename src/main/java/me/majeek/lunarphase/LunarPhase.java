package me.majeek.lunarphase;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;

public enum LunarPhase {
    FULL_MOON("Full Moon", 0),
    WANING_GIBBOUS("Waning Gibbous", 1),
    LAST_QUARTER("Last Quarter", 2),
    WANING_CRESCENT("Waning Crescent", 3),
    NEW_MOON("New Moon", 4),
    WAXING_CRESCENT("Waxing Crescent", 5),
    FIRST_QUARTER("First Quarter", 6),
    WAXING_GIBBOUS("Waxing Gibbous", 7);

    private final String name;
    private final int order;

    LunarPhase(final String name, final int order) {
        this.name = name;
        this.order = order;
    }

    public String getName() {
        return this.name;
    }

    public int getOrder() {
        return this.order;
    }

    @Nullable
    public static LunarPhase fromOrder(int order) {
        return LunarPhase.orderedValues()[order];
    }

    public static LunarPhase[] orderedValues() {
        return Arrays.stream(LunarPhase.values()).sorted(Comparator.comparingInt(LunarPhase::getOrder)).toArray(LunarPhase[]::new);
    }
}
