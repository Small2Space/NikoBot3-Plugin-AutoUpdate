package org.skunion.smallru8.NikoBot3.AutoUpdate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.github.smallru8.NikoBot.Core;
import com.github.smallru8.NikoBot.plugins.PluginsInterface;

import net.dv8tion.jda.api.JDAInfo;

public class AutoUpdate implements PluginsInterface{

	public static String NIKOBOT_GITHUB_RELEASE = "https://github.com/skw-nikobot/NikoBot/releases/latest";
	
	@Override
	public void onDisable() {
		String s= JDAInfo.VERSION;
	}

	@Override
	public void onEnable() {
		Core.botAPI.addEventListener(new Listener());
	}

	@Override
	public String pluginsName() {
		return "AutoUpdate";
	}

	
	public static String getLatestVersion(String url) {
		String ver = "";
		try {
			ver = getFinalURL(url).split("tag/")[1];
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ver;
	}
	
	private static String getFinalURL(String url) throws IOException {
	    HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
	    con.setInstanceFollowRedirects(false);
	    con.connect();
	    con.getInputStream();

	    if (con.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM || con.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
	        String redirectUrl = con.getHeaderField("Location");
	        return getFinalURL(redirectUrl);
	    }
	    return url;
	}
	
}