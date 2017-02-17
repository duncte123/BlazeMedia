package me.duncte123.blazemedia.commands;

import me.duncte123.blazemedia.Command;
import me.duncte123.blazemedia.audio.GuildMusicManager;
import me.duncte123.blazemedia.utils.AudioUtils;
import me.duncte123.blazemedia.utils.Config;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PPlayCommand implements Command {
	
	public final static String help = Config.prefix+"pplay <Media link> => add a song to the queue.";

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		boolean inChan = false;
		boolean enoughArgs = false;
		
		if(event.getGuild().getAudioManager().isConnected()){
			inChan = true;
		}else{
			event.getTextChannel().sendMessage("I'm not in a voice channel, use `"+Config.prefix+"join` to make me join a channel").queue();
		}
		
		if(args.length > 0){
			enoughArgs = true;
		}else{
			event.getTextChannel().sendMessage("To few arguments, use `"+Config.prefix+"pplay <media link>`").queue();
		}
		
		return inChan && enoughArgs;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		AudioUtils au = new AudioUtils();
		
		Guild guild = event.getGuild();
		GuildMusicManager mng = au.getMusicManager(guild);

		au.loadAndPlay(mng, event.getTextChannel(), args[0], true);

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
