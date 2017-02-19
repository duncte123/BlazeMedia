package me.duncte123.blazemedia.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import me.duncte123.blazemedia.Command;
import me.duncte123.blazemedia.Main;
import me.duncte123.blazemedia.audio.GuildMusicManager;
import me.duncte123.blazemedia.audio.TrackScheduler;
import me.duncte123.blazemedia.utils.AudioUtils;
import me.duncte123.blazemedia.utils.Config;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PlayCommand implements Command {
	
	public final static String help = Config.prefix+"play <Media link> => make the bot play song.";

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		boolean inChan = false;
		
		if(event.getGuild().getAudioManager().isConnected()){
			inChan = true;
		}else{
			event.getTextChannel().sendMessage("I'm not in a voice channel, use `"+Config.prefix+"join` to make me join a channel").queue();
		}
		
		return inChan;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		AudioUtils au = Main.au;
		
		Guild guild = event.getGuild();
		GuildMusicManager mng = au.getMusicManager(guild);
		AudioPlayer player = mng.player;
		TrackScheduler scheduler = mng.scheduler;
		
		if(args.length == 0){
			if(player.isPaused()){
				player.setPaused(false);
				event.getTextChannel().sendMessage("Playback has been resumed.").queue();
			}else if(player.getPlayingTrack() != null){
				event.getTextChannel().sendMessage("Player is already playing!").queue();
			}else if(scheduler.queue.isEmpty()){
				event.getTextChannel().sendMessage("The current audio queue is empty! Add something to the queue first!").queue();
			}
		}else{
			au.loadAndPlay(mng, event.getTextChannel(), args[0], false);
		}

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
