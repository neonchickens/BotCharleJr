package BotCharleJr;

import java.util.HashMap;

public class Settings {
	
	//Static calls
	private static HashMap<String, Settings> hmGuildSettings;
	
	public static Settings getGuildSettings(String strGuildID) {
		if (hmGuildSettings == null) {
			hmGuildSettings = new HashMap<String, Settings>();
		}
		
		if (!hmGuildSettings.containsKey(strGuildID)) {
			hmGuildSettings.put(strGuildID, new Settings());
		}
		
		return hmGuildSettings.get(strGuildID);
	}

	//Actual guild settings instance
	private HashMap<String, String> hmSettings;
	private HashMap<String, ChannelSettings> hmChannelSettings;
	
	public Settings() {
		hmSettings = new HashMap<String, String>();
		hmChannelSettings = new HashMap<String, ChannelSettings>();
	}

	public String getSetting(String key) {
		if (hmSettings.containsKey(key)) {
			return hmSettings.get(key);
		} else {
			return "";
		}
	}
	
	public void setSetting(String key, String value) {
		hmSettings.put(key, value);
	}
	
	public ChannelSettings getChannelSettings(String key) {
		if (!hmChannelSettings.containsKey(key)) {
			hmChannelSettings.put(key, new ChannelSettings());
		}
		return hmChannelSettings.get(key);
	}
	
	public class ChannelSettings {
		private HashMap<String, String> hmSettings;

		public ChannelSettings() {
			hmSettings = new HashMap<String, String>();
		}
		
		public String getSetting(String key) {
			if (hmSettings.containsKey(key)) {
				return hmSettings.get(key);
			} else {
				return "";
			}
		}
		
		public void setSetting(String key, String value) {
			hmSettings.put(key, value);
		}
	}
}
