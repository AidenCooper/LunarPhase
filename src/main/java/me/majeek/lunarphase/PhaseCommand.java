package me.majeek.lunarphase;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.*;
import net.minecraft.world.server.ServerWorld;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PhaseCommand {
    public PhaseCommand(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(net.minecraft.command.Commands.literal("phase").executes(context -> this.execute(context.getSource())));
    }

    public int execute(CommandSource source) {
        List<String> phaseMessages = new ArrayList<>();

        ServerWorld currentWorld = source.getWorld();
        LunarPhase currentPhase = WorldUtil.getMoonPhase(currentWorld);
        int currentTickTime = WorldUtil.getTime(currentWorld);

        String localTimeFormat = "HH:mm:ss";
        String localTimeStringFormat = TextFormatting.DARK_GRAY + "<<" + TextFormatting.DARK_PURPLE + localTimeFormat + TextFormatting.DARK_GRAY + ">>";

        Arrays.asList(LunarPhase.orderedValues()).forEach(phase -> {
            int ticksUntil = WorldUtil.timeUntilLunarPhase(currentWorld, phase);

            String nameString = phase.getName();

            String localTimeUntil = LocalTime.of(ticksUntil / 20 / 60 / 60, ticksUntil / 20 / 60 % 60, ticksUntil / 20 % 60).format(DateTimeFormatter.ofPattern(localTimeFormat));
            String localTimeString = localTimeStringFormat.replace(localTimeFormat, localTimeUntil);
            boolean includeLocalTime;

            if(currentPhase.equals(phase) && WorldUtil.isNightTime(currentWorld)) {
                nameString = TextFormatting.BOLD + nameString;
                nameString = TextFormatting.DARK_GREEN + nameString;

                includeLocalTime = false;
            } else if((currentTickTime > WorldUtil.NIGHT_LAST_TICK && currentPhase.getOrder() + 1 == phase.getOrder()) || (currentTickTime < WorldUtil.NIGHT_FIRST_TICK && currentPhase.equals(phase))) {
                nameString = TextFormatting.BOLD + nameString;
                nameString = TextFormatting.GOLD + nameString;

                includeLocalTime = true;
            } else {
                nameString = TextFormatting.GRAY + nameString;

                includeLocalTime = true;
            }

            if(includeLocalTime)
                phaseMessages.add(nameString + " " + localTimeString);
            else
                phaseMessages.add(nameString);
        });

        source.sendFeedback(new StringTextComponent(TextFormatting.DARK_GRAY + "" + TextFormatting.STRIKETHROUGH + StringUtils.repeat(" ", phaseMessages.stream().max(String::compareTo).get().length())), false);
        phaseMessages.forEach(message -> source.sendFeedback(new StringTextComponent(message), false));

        return 1;
    }
}