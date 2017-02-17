package me.duncte123.blazemedia;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import me.duncte123.blazemedia.commands.BlazeCommand;
import me.duncte123.blazemedia.commands.CoinCommand;
import me.duncte123.blazemedia.commands.HelpCommand;
import me.duncte123.blazemedia.commands.JoinCommand;
import me.duncte123.blazemedia.commands.LeaveCommand;
import me.duncte123.blazemedia.commands.ListCommand;
import me.duncte123.blazemedia.commands.PPlayCommand;
import me.duncte123.blazemedia.commands.PauseCommand;
import me.duncte123.blazemedia.commands.PingCommand;
import me.duncte123.blazemedia.commands.PlayCommand;
import me.duncte123.blazemedia.commands.SkipCommand;
import me.duncte123.blazemedia.commands.StopCommand;
import me.duncte123.blazemedia.utils.CommandParser;
import me.duncte123.blazemedia.utils.Config;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.utils.SimpleLog;

public class Main {
	
	private static String logName = "BlazeMedia";

	private static JDA jda;
	public static final CommandParser parser = new CommandParser();
	public static HashMap<String, Command> commands = new HashMap<String, Command>();
	
	private static Logger logForFile = Logger.getLogger(logName);
	
	
	public static void main(String[] args){
		
		
		File theDir = new File("logs");
		
		if(!theDir.exists()){
			System.out.println("creating directory: "+theDir.getName());
			boolean res = false;
			
			try{
				theDir.mkdir();
				res = true;
			}
			catch(SecurityException e){
				e.printStackTrace();
			}
			if(res){
				System.out.println("DIR created");
			}
			
		}
		
		SimpleDateFormat format = new SimpleDateFormat("M-d_HHmss");
		String filepath = System.getProperty("user.dir")+File.separator+"logs"+File.separator+"log_"+format.format(Calendar.getInstance().getTime())+".log";
		
		
		FileHandler fh;
		
		try{
			fh = new FileHandler(filepath);
			logForFile.setUseParentHandlers(false);
			logForFile.addHandler(fh);
			fh.setFormatter(new Formatter() {
	            @Override
	            public String format(LogRecord record) {
	                SimpleDateFormat logTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	                Calendar cal = new GregorianCalendar();
	                cal.setTimeInMillis(record.getMillis());
	                return "["
	                		+record.getLevel()
	                		+"]["
	                        + logTime.format(cal.getTime())
	                        + "]["+logName+"]: "
	                        + record.getMessage() + "\n";
	            }
	        });
		}
		catch(SecurityException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logForFile.info("Logging to: "+filepath);
		
		
		// log in
		try{
			jda = new JDABuilder(AccountType.BOT)
					.setBulkDeleteSplittingEnabled(false)
					.setAudioEnabled(true)
					.addListener(new BotListener())
					.setToken(Config.token)
					.buildBlocking();
			jda.setAutoReconnect(true);
			jda.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
			jda.getPresence().setGame(Game.of("BlazeMedia V"+Config.version+"|"+Config.prefix+"help"));
			//jda.getPresence().setGame(Game.of("BlazeMedia V"+Config.version+"|Under Construction", "https://www.twitch.tv/duncte123"));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		// add the commands
		commands.put("help", new HelpCommand());
		commands.put("blaze", new BlazeCommand());
		commands.put("yt", new BlazeCommand());
		commands.put("ping", new PingCommand());
		commands.put("coin", new CoinCommand());
		//music commands
		commands.put("join", new JoinCommand());
		commands.put("leave", new LeaveCommand());
		commands.put("play", new PlayCommand());
		commands.put("stop", new StopCommand());
		commands.put("pplay", new PPlayCommand());
		commands.put("skip", new SkipCommand());
		commands.put("pause", new PauseCommand());
		commands.put("list", new ListCommand());
	}
	
	public static final void log(String name, SimpleLog.Level lvl, String message){
		logName = name;
		logForFile.log(toLevel(lvl), message);
		SimpleLog logger2 = SimpleLog.getLog(logName);
		logger2.log(lvl, message);
		
	}
	
	public static final void log(SimpleLog.Level lvl, String message){
		log("BlazeMedia", lvl, message);
	}
	
	public static void handleCommand(CommandParser.CommandContainer cmd){
		if(commands.containsKey(cmd.invoke)){
			boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);
			
			if(!safe){
				commands.get(cmd.invoke).executed(safe, cmd.event);
				return;
			}
			commands.get(cmd.invoke).action(cmd.args, cmd.event);
			commands.get(cmd.invoke).executed(safe, cmd.event);
		}
	}
	
	private static Level toLevel(SimpleLog.Level lvl){	
		return Level.parse(lvl.name());
	}
	
}
