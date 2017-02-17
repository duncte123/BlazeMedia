package me.duncte123.blazemedia.commands;

import me.duncte123.blazemedia.Command;
import me.duncte123.blazemedia.audio.GuildMusicManager;
import me.duncte123.blazemedia.audio.TrackScheduler;
import me.duncte123.blazemedia.utils.AudioUtils;
import me.duncte123.blazemedia.utils.Config;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SkipCommand implements Command {

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		AudioUtils au = new AudioUtils();
		
		Guild guild = event.getGuild();
		GuildMusicManager mng = au.getMusicManager(guild);
		TrackScheduler scheduler = mng.scheduler;
		
		scheduler.nextTrack();
        event.getTextChannel().sendMessage("The current track was skipped.").queue();

	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return Config.prefix+"skip => skips the current track";
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		// TODO Auto-generated method stub

	}

}
