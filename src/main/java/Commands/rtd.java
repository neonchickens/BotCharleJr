package Commands;

import BotCharleJr.Command;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class rtd extends Command {

	public rtd(Event event, String[] strCommand, boolean fromDirectCall) {
		super(event, strCommand, fromDirectCall);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {

		boolean defaultRoll = strCommand.length == 1;
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < strCommand.length || defaultRoll; i++) {
			String[] strDiceRoll = !defaultRoll ? strCommand[i].split(",") : new String[0];
			defaultRoll = false;

			try {
				int intSides = strDiceRoll.length >= 1 ? Integer.parseInt(strDiceRoll[0]) : 6;
				int intTimes = strDiceRoll.length >= 2 ? Integer.parseInt(strDiceRoll[1]) : 1;
				sb.append(String.format("Rolling a D%d %d times: ", intSides, intTimes));
				for (int t = 0; t < intTimes; t++) {
					sb.append(String.format("%.0f ", Math.floor(Math.random() * intSides + 1)));
				}
			} catch (NumberFormatException e) {
				sb.append("Could not recognize input. :(");
			}
			sb.append("\n");
		}

		sendMessage(sb.toString());
	}

	public static void help(MessageReceivedEvent event, String[] strCommand) {

		StringBuilder sb = new StringBuilder();
		sb.append("Welcome to Roll The Dice (rtd). Below is command usage.\n");
		sb.append("!rtd {sides=6},{times=1}\n");
		sb.append("\nIf you want to roll a D6: !rtd\n");
		sb.append("If you want to roll two D6: !rtd 6,2\n");
		sb.append("If you want to roll a D20: !rtd 20\n");
		sb.append("If you want to roll multiple die: !rtd 6,2 10,3 20\n");

		event.getTextChannel().sendMessage(sb.toString()).complete();
	}
}
