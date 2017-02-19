package me.duncte123.blazemedia.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import me.duncte123.blazemedia.Command;
import me.duncte123.blazemedia.Main;
import me.duncte123.blazemedia.audio.GuildMusicManager;
import me.duncte123.blazemedia.utils.AudioUtils;
import me.duncte123.blazemedia.utils.Config;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class NowPlayingCommand implements Command {

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
		
		AudioTrack currentTrack = player.getPlayingTrack();
        if (currentTrack != null){
            String title = currentTrack.getInfo().title;
            String position = AudioUtils.getTimestamp(currentTrack.getPosition());
            String duration = AudioUtils.getTimestamp(currentTrack.getDuration());

            String nowplaying = String.format("**Playing:** %s\n**Time:** [%s / %s]",
                    title, position, duration);

            event.getChannel().sendMessage(nowplaying).queue();
        }else{
            event.getChannel().sendMessage("The player is not currently playing anything!").queue();
        }

	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return Config.prefix+"nowplaying => Prints information about the currently playing song (title, current time)";
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		// TODO Auto-generated method stub

	}

}
