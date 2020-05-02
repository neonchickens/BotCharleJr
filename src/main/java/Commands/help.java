package Commands;

import java.io.File;

import BotCharleJr.Bot;
import BotCharleJr.Command;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class help extends Command {

	public help(Event event, String[] strCommand, boolean fromDirectCall) {
		super(event, strCommand, fromDirectCall);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {

		StringBuilder sb = new StringBuilder();
		if (strCommand.length == 1) {
			sb.append("Here is a list of commands! Type !help {command} for more info.\n");
			File folder = new File(new File("./src/main/java/Commands/").getAbsolutePath());
			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".java")) {
					sb.append(listOfFiles[i].getName().substring(0, listOfFiles[i].getName().lastIndexOf(".")) + "\n");
				}
			}
			sendMessage(sb.toString());
		} else if  (strCommand.length == 2) {
			Bot.getInstance().execHelpCommand((MessageReceivedEvent)event, strCommand);
		}
	}

}