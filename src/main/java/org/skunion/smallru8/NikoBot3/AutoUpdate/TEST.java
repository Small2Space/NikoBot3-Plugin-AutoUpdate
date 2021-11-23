package org.skunion.smallru8.NikoBot3.AutoUpdate;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class TEST {

	public static void main(String[] argv) throws IOException {
		/*
		URL url = new URL("https://github.com/skw-nikobot/NikoBot/releases/latest/download");
		try {
			URLConnection urlc= url.openConnection();
			urlc.connect();
			urlc.getContent();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		float f1 = (float)256/1024;
		System.out.println((int)(f1/0.2));
	}
	public static String getVersion(String url) {
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
