package BotCharleJr;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;

public class Audio {

	// Static calls
	private static HashMap<String, Audio> hmGuildAudio;

	public static Audio getGuildAudio(String strGuildID) {
		if (hmGuildAudio == null) {
			hmGuildAudio = new HashMap<String, Audio>();
		}

		if (!hmGuildAudio.containsKey(strGuildID)) {
			hmGuildAudio.put(strGuildID, new Audio());
		}

		return hmGuildAudio.get(strGuildID);
	}
	
	public AudioPlayerManager manager;
	public AudioPlayer player;
	public TrackScheduler scheduler;
	public Audio() {
		manager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(manager);

		player = manager.createPlayer();

		scheduler = new TrackScheduler();
		player.addListener(scheduler);
		player.setVolume(25);
	}

	public void play(String url) {
		manager.loadItem(url, new AudioLoadResult());
	}
	
	public void skip() {
		scheduler.skip();
	}

	public void prev() {
		scheduler.prev();
	}
	
	public void clear() {
		scheduler.clear();
	}
	
	public void pause() {
		player.setPaused(!player.isPaused());
	}
	
	public void replay() {
		scheduler.replay();
	}

	public int getVolume() {
		return player.getVolume();
	}

	public void setVolume(int vol) {
		player.setVolume(vol);
	}
	
	public class AudioLoadResult implements AudioLoadResultHandler {
		
		@Override
		public void trackLoaded(AudioTrack track) {
			scheduler.addTrack(track);
		}

		@Override
		public void playlistLoaded(AudioPlaylist playlist) {
			// TODO Auto-generated method stub
			for (AudioTrack track: playlist.getTracks()) {
				scheduler.addTrack(track);
			}
		}

		@Override
		public void noMatches() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void loadFailed(FriendlyException exception) {
			// TODO Auto-generated method stub
			System.out.println(exception.getMessage());
		}
		
	}

	public class TrackScheduler extends AudioEventAdapter {
		
		List<AudioTrack> lstTracks;
		int index = -1;
		
		public TrackScheduler() {
			lstTracks = new ArrayList<AudioTrack>();
		}
		
		public void addTrack(AudioTrack track) {
			lstTracks.add(track);
			if (player.getPlayingTrack() == null) {
				index++;
				player.playTrack(lstTracks.get(index).makeClone());
			}
		}

		public void skip() {
			if (lstTracks.size() > index + 1) {
				index++;
				player.playTrack(lstTracks.get(index).makeClone());
			} else {
				player.stopTrack();
			}
		}
		
		public void prev() {
			if (index - 1 >= 0) {
				index--;
				player.playTrack(lstTracks.get(index).makeClone());
			} else {
				player.stopTrack();
			}
		}
		
		public void clear() {
			lstTracks.clear();
			index = -1;
			player.stopTrack();
		}
		
		public void replay() {
			player.playTrack(lstTracks.get(index).makeClone());
		}
		
		@Override
		public void onPlayerPause(AudioPlayer player) {
			// Player was paused
		}

		@Override
		public void onPlayerResume(AudioPlayer player) {
			// Player was resumed
		}

		@Override
		public void onTrackStart(AudioPlayer player, AudioTrack track) {
			// A track started playing
		}

		@Override
		public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
			if (endReason.mayStartNext) {
				// Start next track
				if (lstTracks.size() > index + 1) {
					index++;
					player.playTrack(lstTracks.get(index));
				}
			}

			// endReason == FINISHED: A track finished or died by an exception (mayStartNext
			// = true).
			// endReason == LOAD_FAILED: Loading of a track failed (mayStartNext = true).
			// endReason == STOPPED: The player was stopped.
			// endReason == REPLACED: Another track started playing while this had not
			// finished
			// endReason == CLEANUP: Player hasn't been queried for a while, if you want you
			// can put a
			// clone of this back to your queue
		}

		@Override
		public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
			// An already playing track threw an exception (track end event will still be
			// received separately)
			System.out.println(exception.getMessage());
		}

		@Override
		public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
			// Audio track has been unable to provide us any audio, might want to just start
			// a new track
		}
	}
	
	public static class AudioPlayerSendHandler implements AudioSendHandler {
		  private final AudioPlayer audioPlayer;
		  private AudioFrame lastFrame;

		  public AudioPlayerSendHandler(AudioPlayer audioPlayer) {
		    this.audioPlayer = audioPlayer;
		  }

		  @Override
		  public boolean canProvide() {
		    lastFrame = audioPlayer.provide();
		    return lastFrame != null;
		  }

		  @Override
		  public ByteBuffer provide20MsAudio() {
		    return ByteBuffer.wrap(lastFrame.getData());
		  }

		  @Override
		  public boolean isOpus() {
		    return true;
		  }
		}
}
