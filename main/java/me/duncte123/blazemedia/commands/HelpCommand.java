package me.duncte123.blazemedia.commands;

import me.duncte123.blazemedia.Command;
import me.duncte123.blazemedia.Main;
import me.duncte123.blazemedia.utils.Config;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class HelpCommand implements Command {
	
	public final static String help = Config.prefix+"help => shows a list of all the commands.";
	
	private String helpMsg2;

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		
		
		helpMsg2 = "```\n";
		for(String cmd: Main.commands.keySet()){
			helpMsg2 += Main.commands.get(cmd).help();
			helpMsg2 += "\n";
		}
		helpMsg2 += "```";
		
		event.getTextChannel().sendMessage(event.getMember().getAsMention() +" check your DM's").queue();
		
		event.getAuthor().openPrivateChannel().queue( pc -> pc.sendMessage(helpMsg2).queue());
	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return help;
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return;
		
	}

}
