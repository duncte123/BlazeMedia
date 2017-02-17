package me.duncte123.blazemedia.commands;

import me.duncte123.blazemedia.Command;
import me.duncte123.blazemedia.utils.Config;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class BlazeCommand implements Command {

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		event.getTextChannel().sendMessage("🔥🔥🔥🔥🔥").queue();

	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return Config.prefix+"blaze => this command doesn't have any functions yet.";
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return;
	}

}
