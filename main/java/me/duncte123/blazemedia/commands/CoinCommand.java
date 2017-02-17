package me.duncte123.blazemedia.commands;

import java.io.IOException;
import java.util.Random;

import me.duncte123.blazemedia.Command;
import me.duncte123.blazemedia.utils.Config;
import me.duncte123.blazemedia.utils.StreamUtil;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CoinCommand implements Command {
	
	public final static String help = Config.prefix+"coin => flips a coin.";
	private final String coinUrl = "http://dshelmondgames.ml/img/coin/blazemediabot/";
	private final String[] imagesArr = {"heads.png", "tails.png"};

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		event.getTextChannel().sendTyping();
		event.getTextChannel().sendTyping();
		event.getTextChannel().sendMessage("*Flips a coin*").queue();
		event.getTextChannel().sendTyping();
		event.getTextChannel().sendTyping();
		Random rand = new Random();
		int res = rand.nextInt(2);
		//this.getClass().getResourceAsStream("my.conf");
		try {
			event.getTextChannel().sendFile(StreamUtil.stream2file(this.getClass().getResourceAsStream(imagesArr[res])), new MessageBuilder().append(coinUrl+imagesArr[res]).build()).queue();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
