package me.duncte123.blazemedia.commands;

import me.duncte123.blazemedia.Command;
import me.duncte123.blazemedia.utils.Config;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class LeaveCommand implements Command {
	
	public final static String help = Config.prefix+"leave => make the bot leave your channel.";

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		boolean botInChannel = false;
		
		if(event.getGuild().getAudioManager().isConnected()){
			botInChannel = true;
		}else{
			event.getTextChannel().sendMessage("I'm not in a channel atm").queue();
		}

		
		return botInChannel;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		event.getGuild().getAudioManager().setSendingHandler(null);
		event.getGuild().getAudioManager().closeAudioConnection();
		event.getTextChannel().sendMessage("Leaving your channel").queue();

	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return help;
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		// TODO Auto-generated method stub

	}

}
