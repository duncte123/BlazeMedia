package me.duncte123.blazemedia.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import me.duncte123.blazemedia.Command;
import me.duncte123.blazemedia.Main;
import me.duncte123.blazemedia.audio.GuildMusicManager;
import me.duncte123.blazemedia.utils.AudioUtils;
import me.duncte123.blazemedia.utils.Config;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PauseCommand implements Command {

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
		
		if (player.getPlayingTrack() == null){
            event.getTextChannel().sendMessage("Cannot pause or resume player because no track is loaded for playing.").queue();
            return;
        }

        player.setPaused(!player.isPaused());
        if (player.isPaused()){
            event.getTextChannel().sendMessage("The player has been paused.").queue();
        }else{
            event.getTextChannel().sendMessage("The player has resumed playing.").queue();
        }
	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return Config.prefix+"pause => pauses the current song";
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		// TODO Auto-generated method stub

	}

}
