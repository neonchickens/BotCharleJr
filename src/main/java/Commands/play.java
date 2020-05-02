package Commands;

import BotCharleJr.Audio;
import BotCharleJr.Command;
import BotCharleJr.Audio.AudioPlayerSendHandler;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.managers.AudioManager;

public class play extends Command {

	public play(Event event, String[] strCommand, boolean fromDirectCall) {
		super(event, strCommand, fromDirectCall);
	}
	
	@Override
	public void run() {
		Audio a = Audio.getGuildAudio(msgGuild.getId());

		for (VoiceChannel vc: msgGuild.getVoiceChannels()) {
			for (Member m: vc.getMembers()) {
				if (m.equals(msgMessage.getMember())) {
					VoiceChannel msgUserVC = vc;
					AudioManager manager = msgGuild.getAudioManager();
			        if (!msgUserVC.equals(manager.getConnectedChannel())) {
			        	AudioPlayerSendHandler x = new AudioPlayerSendHandler(a.player);
				        manager.setSendingHandler(x);
				        manager.openAudioConnection(msgUserVC);
				        System.out.println("Joined Channel.");
			        } else {
			        	System.out.println("Already Here!");
			        }
				}
			}
		}
		a.play(strCommand[1]);
	}
}