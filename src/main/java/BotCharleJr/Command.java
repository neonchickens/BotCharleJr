package BotCharleJr;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Command implements Runnable {
	protected Event event;
	protected User msgUser;
	protected Member msgMember;
	protected Guild msgGuild;
	protected MessageChannel msgMessageChannel;
	protected VoiceChannel msgVoiceChannel;
	protected Message msgMessage;
	protected String[] strCommand;
	
	public Command(Event event, String[] strCommand, boolean fromDirectCall) {
		this.event = event;
		if (event instanceof MessageReceivedEvent) {
			MessageReceivedEvent eventMessage = (MessageReceivedEvent)event;
			this.msgUser = eventMessage.getAuthor();
			this.msgGuild = eventMessage.getGuild();
			this.msgMessageChannel = eventMessage.getChannel();
			this.msgMessage = eventMessage.getMessage();
		}
		if (event instanceof GuildVoiceJoinEvent) {
			GuildVoiceJoinEvent eventVoice = (GuildVoiceJoinEvent)event;
			this.msgGuild = eventVoice.getGuild();
			this.msgMember = eventVoice.getMember();
			this.msgVoiceChannel = eventVoice.getChannelJoined();
		}
		this.strCommand = strCommand;
	}
	
	protected String combineCommand() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strCommand.length; i++) {
			sb.append(strCommand[i]);
			if (i + 1 < strCommand.length) {
				sb.append(" ");
			}
		}
		return sb.toString();
	}
	
	protected void sendMessage(String content) {
		Message msgNew = new MessageBuilder().append(content).build();
		msgMessageChannel.sendMessage(msgNew).complete();
	}
	
	public static void help(MessageReceivedEvent event, String[] strCommand) {
		System.out.println("You shouldn't see this");
	}
}
