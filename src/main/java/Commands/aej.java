package Commands;

import java.util.Arrays;

import BotCharleJr.Command;
import BotCharleJr.Settings;
import net.dv8tion.jda.api.events.Event;

public class aej extends Command {

	public aej(Event event, String[] strCommand, boolean fromDirectCall) {
		super(event, strCommand, fromDirectCall);
	}

	@Override
	public void run() {
		strCommand = Arrays.copyOfRange(strCommand, 1, strCommand.length);
		Settings s = Settings.getGuildSettings(msgGuild.getId());
		String strNewAEJ = s.getSetting("aej") + ";" + combineCommand();
		Settings.getGuildSettings(msgGuild.getId()).setSetting("aej", strNewAEJ);
	}
}