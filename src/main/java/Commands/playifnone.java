package Commands;

import BotCharleJr.Audio;
import BotCharleJr.Command;
import BotCharleJr.Audio.AudioPlayerSendHandler;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.managers.AudioManager;

public class playifnone extends Command {

	public playifnone(Event event, String[] strCommand, boolean fromDirectCall) {
		super(event, strCommand, fromDirectCall);
	}
	
	@Override
	public void run() {
		Audio a = Audio.getGuildAudio(msgGuild.getId());
		if (a.player.getPlayingTrack() == null) {
			AudioManager manager = msgGuild.getAudioManager();
	        if (!msgVoiceChannel.equals(manager.getConnectedChannel())) {
	        	AudioPlayerSendHandler x = new AudioPlayerSendHandler(a.player);
		        manager.setSendingHandler(x);
		        manager.openAudioConnection(msgVoiceChannel);
	        }
			a.play(strCommand[1]);
		}
	}
}