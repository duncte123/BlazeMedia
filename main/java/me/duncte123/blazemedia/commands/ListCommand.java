package me.duncte123.blazemedia.commands;

import java.util.Queue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import me.duncte123.blazemedia.Command;
import me.duncte123.blazemedia.audio.GuildMusicManager;
import me.duncte123.blazemedia.audio.TrackScheduler;
import me.duncte123.blazemedia.utils.AudioUtils;
import me.duncte123.blazemedia.utils.Config;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ListCommand implements Command {

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

		Queue<AudioTrack> queue = scheduler.queue;
		synchronized (queue) {
			if (queue.isEmpty()) {
				event.getChannel().sendMessage("The queue is currently empty!").queue();
			} else {
				int trackCount = 0;
				long queueLength = 0;
				StringBuilder sb = new StringBuilder();
				sb.append("Current Queue: Entries: ").append(queue.size()).append("\n");
				for (AudioTrack track : queue) {
					queueLength += track.getDuration();
					if (trackCount < 10) {
						sb.append("`[").append(AudioUtils.getTimestamp(track.getDuration())).append("]` ");
						sb.append(track.getInfo().title).append("\n");
						trackCount++;
					}
				}
				sb.append("\n").append("Total Queue Time Length: ").append(AudioUtils.getTimestamp(queueLength));

				event.getChannel().sendMessage(sb.toString()).queue();
			}
		}	
	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return Config.prefix+"list => shows the current queue";
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		// TODO Auto-generated method stub

	}

}
