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

public class StopCommand implements Command {

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		AudioUtils au = Main.au;
		
		Guild guild = event.getGuild();
		GuildMusicManager mng = au.getMusicManager(guild);
		AudioPlayer player = mng.player;
		TrackScheduler scheduler = mng.scheduler;
		
		scheduler.queue.clear();
		player.stopTrack();
		player.setPaused(false);
		event.getTextChannel().sendMessage("Playback has been completely stopped and the queue has been cleared").queue();

	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return Config.prefix+"stop => stops the music player.";
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return;
	}

}
