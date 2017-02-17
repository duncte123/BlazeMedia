package me.duncte123.blazemedia.commands;

import java.util.List;

import me.duncte123.blazemedia.Command;
import me.duncte123.blazemedia.Main;
import me.duncte123.blazemedia.utils.Config;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.utils.PermissionUtil;
import net.dv8tion.jda.core.utils.SimpleLog;

public class CleanupCommand implements Command {
	
	public final static String help = Config.prefix+"cleanup => performs a cleanup in the channel where the command is run. (MOD or higher ONLY!)";

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		if(event.getAuthor().isBot()){return false;}
		if(!PermissionUtil.checkPermission(event.getGuild(), event.getMember(), Permission.MESSAGE_MANAGE)){event.getTextChannel().sendMessage("You don't have permission to run this command!");return false;}
		return true;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		int deletedMsg = 0;
		MessageHistory mh = new MessageHistory(event.getChannel());
		List<Message> msgg =  mh.getRetrievedHistory().subList(0, 50);
		event.getTextChannel().deleteMessages(msgg);
		deletedMsg = 50;
		event.getTextChannel().sendMessage("Removed "+deletedMsg+" messages!").queue();
		Main.log(SimpleLog.Level.INFO, deletedMsg+" removed in channel "+event.getTextChannel().getName());
		
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
