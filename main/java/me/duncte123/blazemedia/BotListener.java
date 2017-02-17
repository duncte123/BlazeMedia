package me.duncte123.blazemedia;

import java.util.List;

import me.duncte123.blazemedia.utils.Config;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.utils.SimpleLog;

public class BotListener extends ListenerAdapter {
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event){
		if(event.getMessage().getContent().equals("nope\nthat's not going to happen.")){
			return;
		}
		if(event.isFromType(ChannelType.PRIVATE) && event.getJDA().getSelfUser().getId() != event.getAuthor().getId()){
			event.getAuthor().getPrivateChannel().sendMessage("nope\nthat's not going to happen.");
			Main.log(SimpleLog.Level.WARNING, "User "+event.getMessage().getAuthor().getName()+"#"+event.getMessage().getAuthor().getDiscriminator()+", tried to do something in the pm-channel");
			return;
		}
		if(event.isFromType(ChannelType.PRIVATE)){
			return;
		}
		if(event.getMessage().getContent().startsWith(Config.prefix) && event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId()){
			Main.handleCommand(Main.parser.parse(event.getMessage().getContent(), event));
			Main.log("BlazeMediaCommand", SimpleLog.Level.INFO, "User "+event.getMessage().getAuthor().getName()+"#"+event.getMessage().getAuthor().getDiscriminator()+" ran command "+ event.getMessage().getContent().toLowerCase().split(" ")[0]);
			return;
		}
		Main.log("BlazeMediaMessage", SimpleLog.Level.INFO, "Message from user "+event.getMessage().getAuthor().getName()+"#"+event.getMessage().getAuthor().getDiscriminator()+": "+ event.getMessage().getContent());
	}
	
	@Override
	public void onReady(ReadyEvent event){
		Main.log(SimpleLog.Level.INFO, "Logged in as " + event.getJDA().getSelfUser().getName());
		//event.getJDA().getGuilds().get(0).getPublicChannel().sendMessage("BlazeMedia V" + Config.version +" is ready for action!!");
		
	}
	
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event){
		List<TextChannel> channels =  event.getGuild().getTextChannels();
		for(TextChannel tc: channels){
			if(tc.getId().equals("281097856943783936")){
				tc.sendMessage("welcome "+event.getMember().getAsMention()+", to the Blaze Media discord server!");
			}
		}
	}
	
	@Override
	public void onGuildMemberLeave(GuildMemberLeaveEvent event){
		List<TextChannel> channels =  event.getGuild().getTextChannels();
		for(TextChannel tc: channels){
			if(tc.getId().equals("281097856943783936")){
				tc.sendMessage(event.getMember().getAsMention()+"just left the Blaze Media discord server, bye "+event.getMember().getAsMention()+".");
			}
		}
	}
}
