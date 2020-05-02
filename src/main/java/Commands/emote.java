package Commands;

import BotCharleJr.Command;
import net.dv8tion.jda.api.events.Event;

public class emote extends Command {

	public emote(Event event, String[] strCommand, boolean fromDirectCall) {
		super(event, strCommand, fromDirectCall);

	}
	
	@Override
	public void run() {
		for (int i = 1; i < strCommand.length; i++) {
			msgMessage.addReaction(msgGuild.getEmotesByName(strCommand[i].replaceAll(":", ""), false).get(0)).complete();
		}
	}

}