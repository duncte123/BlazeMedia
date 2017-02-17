package me.duncte123.blazemedia.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.bandcamp.BandcampAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.beam.BeamAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.http.HttpAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.local.LocalAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.soundcloud.SoundCloudAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.twitch.TwitchStreamAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.vimeo.VimeoAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import me.duncte123.blazemedia.audio.GuildMusicManager;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;

public class AudioUtils {
	
	public static final int DEFAULT_VOLUME = 100; //(0-150, where 100 is the default max volume)
	
	private final AudioPlayerManager playerManager;
	private final Map<String, GuildMusicManager> musicManagers;
	
	public AudioUtils(){
		java.util.logging.Logger.getLogger("org.apache.http.client.protocol.ResponseProcessCookies").setLevel(Level.OFF);
		
		this.playerManager = new DefaultAudioPlayerManager();
		playerManager.registerSourceManager(new YoutubeAudioSourceManager());
		playerManager.registerSourceManager(new SoundCloudAudioSourceManager());
		playerManager.registerSourceManager(new BandcampAudioSourceManager());
		playerManager.registerSourceManager(new VimeoAudioSourceManager());
		playerManager.registerSourceManager(new TwitchStreamAudioSourceManager());
		playerManager.registerSourceManager(new BeamAudioSourceManager());
		playerManager.registerSourceManager(new HttpAudioSourceManager());
		playerManager.registerSourceManager(new LocalAudioSourceManager());
		
		AudioSourceManagers.registerRemoteSources(playerManager);
		AudioSourceManagers.registerLocalSource(playerManager);
		
		musicManagers = new HashMap<String, GuildMusicManager>();
	}
	
	
	public void loadAndPlay(GuildMusicManager mng, final MessageChannel channel, final String trackUrl, final boolean addPlayList){
		playerManager.loadItemOrdered(mng, trackUrl, new AudioLoadResultHandler(){

			@Override
			public void trackLoaded(AudioTrack track) {
				String msg = "Adding to queue: " + track.getInfo().title;
				if(mng.player.getPlayingTrack() == null){
					msg += "\nand the Player has stated playing;";
				}
				
				mng.scheduler.queue(track);
				channel.sendMessage(msg).queue();
				
			}
			
			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				AudioTrack firstTrack = playlist.getSelectedTrack();
				List<AudioTrack> tracks = playlist.getTracks();
				
				if(firstTrack == null){
					firstTrack = playlist.getTracks().get(0);
				}
				
				if(addPlayList){
					channel.sendMessage("Adding **"+playlist.getTracks().size()+"** tracks to queue from playlist: "+playlist.getName()).queue();
					tracks.forEach(mng.scheduler::queue);
				}else{
					channel.sendMessage("Adding to queue "+ firstTrack.getInfo().title+" (first track of playlist "+playlist.getName()+")").queue();
					mng.scheduler.queue(firstTrack);
				}
				
			}

			@Override
			public void noMatches() {
				channel.sendMessage("Nothing found by "+trackUrl).queue();
			}

			@Override
			public void loadFailed(FriendlyException exeption) {
				channel.sendMessage("Could not play: "+exeption.getMessage()).queue();
				
			}

		});
	}
	
	
	
	public synchronized GuildMusicManager getMusicManager(Guild guild){
		String guildId = guild.getId();
		GuildMusicManager mng = musicManagers.get(guildId);
			if(mng == null){
				mng = new GuildMusicManager(playerManager);
				mng.player.setVolume(DEFAULT_VOLUME);
				musicManagers.put(guildId, mng);
			}
			
		guild.getAudioManager().setSendingHandler(mng.getSendHandler());
		
		return mng;
	}
	
	public static String getTimestamp(long miliseconds){
		int seconds = (int) (miliseconds / 1000) % 60;
		int minutes = (int) ((miliseconds / (1000 * 60)) % 60);
		int hours = (int) ((miliseconds / (1000 * 60 * 60)) % 24);
		
		if(hours > 0){
			return String.format("%02d:%02d:%02d", hours, minutes, seconds);
		}else{
			return String.format("%02d:%02d", minutes, seconds);
		}
	}

}
