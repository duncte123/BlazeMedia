package me.duncte123.blazemedia.commands;

import me.duncte123.blazemedia.Command;
import me.duncte123.blazemedia.utils.Config;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class JoinCommand extends ListenerAdapter implements Command {
	
	public final static String help = Config.prefix+"join => makes the bot join the voice channel that you are in.";
	
	private String chanId = "";

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		
		if(event.getGuild().getAudioManager().isConnected()){
			event.getGuild().getAudioManager().closeAudioConnection();
		}
		
		boolean inChannel = false;
		
		for(VoiceChannel chan : event.getGuild().getVoiceChannels()){
			if(chan.getMembers().contains(event.getMember())){
				inChannel = true;
				chanId = chan.getId();
				break;
			}
		}

		if(!inChannel){
			event.getTextChannel().sendMessage("You are not in a voice channel").queue();
		}
		
		return inChannel;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		VoiceChannel vc = null;
		
		for(VoiceChannel chan : event.getGuild().getVoiceChannels()){
			if(chan.getId().equals(chanId)){
				vc = chan;
				break;
			}
		}
		
		try{
			//gm.mo
			vc.getGuild().getAudioManager().openAudioConnection(vc);
			event.getTextChannel().sendMessage("Joining `" + vc.getName() + "`.").queue();
		}catch(PermissionException e){
			if(e.getPermission() == Permission.VOICE_CONNECT){
				event.getTextChannel().sendMessage("I don't have permission to join `"+vc.getName()+"`").queue();
			}
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

	}
	
	

}
