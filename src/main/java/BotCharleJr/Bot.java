package BotCharleJr;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot extends ListenerAdapter {

	private static Bot bot;
	private JDA jda;
	private ExecutorService cmdPool;
	
	private Bot() {
		cmdPool = Executors.newFixedThreadPool(2); 
	}
	
	public static Bot getInstance() {
		if (bot == null) {
			bot = new Bot();
			
			ArrayList<GatewayIntent> lstIntents = new ArrayList<GatewayIntent>();
			lstIntents.add(GatewayIntent.GUILD_MEMBERS);
			lstIntents.add(GatewayIntent.GUILD_PRESENCES);
			lstIntents.add(GatewayIntent.GUILD_EMOJIS);
			lstIntents.add(GatewayIntent.GUILD_VOICE_STATES);
			lstIntents.add(GatewayIntent.GUILD_PRESENCES);
			lstIntents.add(GatewayIntent.GUILD_MESSAGES);
			lstIntents.add(GatewayIntent.GUILD_MESSAGE_REACTIONS);
	        JDABuilder builder = JDABuilder.create(Secret.secret, (Collection<GatewayIntent>)lstIntents);
	        
	        builder.addEventListeners(bot);
	        
	        try {
				bot.jda = builder.build();
			} catch (LoginException e) {
				bot = null;
				System.out.println(e.getMessage());
			}
		}
		return bot;
	}
	
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
    	Message message = event.getMessage();
    	User user = event.getAuthor();
    	String content = message.getContentRaw();
    	Guild guild = event.getGuild();
    	
    	//Check to ensure message is for us
    	if (!user.isBot() && content.length() > 0) {
        	String[] command = message.getContentDisplay().substring(1).split(" ");
        	
    		if (content.charAt(0) == '!') {
    			execCommand(event, command, true);
    		}

    		Settings s = Settings.getGuildSettings(guild.getId());
    		String[] strAEM = s.getSetting("aem").split(";");
    		for (int i = 0; i < strAEM.length; i++) {
    			if (!strAEM[i].isEmpty()) {
        			command = strAEM[i].split(" ");
        			execCommand(event, command, false);
    			}
    		}
    	}
    }
    
    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
    	// TODO Auto-generated method stub
    	super.onGuildVoiceJoin(event);
    	
    	if (!event.getMember().getUser().isBot()) {
        	Guild guild = event.getGuild();
        	Settings s = Settings.getGuildSettings(guild.getId());
    		String[] strAEJ = s.getSetting("aej").split(";");
    		for (int i = 0; i < strAEJ.length; i++) {
    			if (!strAEJ[i].isEmpty()) {
    				String[] command = strAEJ[i].split(" ");
        			execCommand(event, command, false);
    			}
    		}
    	}
    }
    
    public void execHelpCommand(MessageReceivedEvent event, String[] command) {
    	//Use the first word in command to find java file to execute
    	try {
			//Call the command/file by name
    		//Class cls = Class.forName("Commands." + command[1]);
			//Method cmd = cls.getMethod("help", new Class[] {Event.class, String[].class});
			//cmd.invoke(null, new Object[] {event, command});
			
			Method m = Class.forName("Commands." + command[1]).getDeclaredMethod("help", MessageReceivedEvent.class, String[].class);
			Object o = m.invoke(null, event, command);
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not execute command '" + command[1] + "'.\n" + e.getMessage());
			e.printStackTrace();
		}
    }
    
    public void execCommand(Event event, String[] command, boolean fromDirectCall) {
    	//Use the first word in command to find java file to execute
    	try {
			//Call the command/file by name
			Constructor cmd = Class.forName("Commands." + command[0]).getConstructor(Event.class, String[].class, boolean.class);
			Command c = (Command)cmd.newInstance(event, command, fromDirectCall);
			cmdPool.execute(c);
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not execute command '" + command[0] + "'.\n" + e.getMessage());
			e.printStackTrace();
		}
    }
    
    public JDA getJDA() {
    	return jda;
    }
}
