package Commands;

import BotCharleJr.Audio;
import BotCharleJr.Command;
import BotCharleJr.Settings;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.Event;

public class volume extends Command {

	public volume(Event event, String[] strCommand, boolean fromDirectCall) {
		super(event, strCommand, fromDirectCall);
	}

	@Override
	public void run() {
		Audio a = Audio.getGuildAudio(msgGuild.getId());
		if (strCommand.length == 1) {
			Message reply = new MessageBuilder().append("Current volume: " + a.getVolume()).build();
			msgMessageChannel.sendMessage(reply).complete();
		} else if (strCommand.length > 1) {
			int vol = Integer.parseInt(strCommand[1]);
			Settings.getGuildSettings(msgGuild.getId()).setSetting("volume", Integer.toString(vol));
			a.setVolume(vol);
		}
	}
}