package Commands;

import java.util.Arrays;
import BotCharleJr.Command;
import BotCharleJr.Settings;
import net.dv8tion.jda.api.events.Event;

public class aem extends Command {

	public aem(Event event, String[] strCommand, boolean fromDirectCall) {
		super(event, strCommand, fromDirectCall);
	}

	@Override
	public void run() {
		strCommand = Arrays.copyOfRange(strCommand, 1, strCommand.length);
		Settings s = Settings.getGuildSettings(msgGuild.getId());
		String strNewAEM = s.getSetting("aem") + ";" + combineCommand();
		Settings.getGuildSettings(msgGuild.getId()).getChannelSettings(msgMessageChannel.getId()).setSetting("aem", strNewAEM);
	}
}
