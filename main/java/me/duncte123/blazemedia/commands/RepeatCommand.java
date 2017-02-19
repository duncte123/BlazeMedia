package me.duncte123.blazemedia.commands;

import me.duncte123.blazemedia.Command;
import me.duncte123.blazemedia.Main;
import me.duncte123.blazemedia.audio.GuildMusicManager;
import me.duncte123.blazemedia.audio.TrackScheduler;
import me.duncte123.blazemedia.utils.AudioUtils;
import me.duncte123.blazemedia.utils.Config;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RepeatCommand implements Command {

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
		TrackScheduler scheduler = mng.scheduler;
		
		scheduler.setRepeating(!scheduler.isRepeating());
        event.getChannel().sendMessage("Player was set to: **" + (scheduler.isRepeating() ? "repeat" : "not repeat") + "**").queue();

	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return Config.prefix+"repeat => Makes the player repeat the currently playing song";
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		// TODO Auto-generated method stub

	}

}
