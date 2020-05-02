package Commands;

import BotCharleJr.Audio;
import BotCharleJr.Command;
import net.dv8tion.jda.api.events.Event;

public class skip extends Command {

	public skip(Event event, String[] strCommand, boolean fromDirectCall) {
		super(event, strCommand, fromDirectCall);
	}
	
	@Override
	public void run() {
		Audio a = Audio.getGuildAudio(msgGuild.getId());
		a.skip();
	}
}