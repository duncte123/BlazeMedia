package me.duncte123.blazemedia.commands;

import me.duncte123.blazemedia.Command;
import me.duncte123.blazemedia.utils.Config;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PingCommand implements Command {
	
	public final static String help = Config.prefix+"ping => PONG!";

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		event.getTextChannel().sendMessage("PONG!").queue();
		
	}

	@Override
	public String help() {
		return help;
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		return;
	}

	
}
