package org.skunion.smallru8.NikoBot3.AutoUpdate;

import com.github.smallru8.NikoBot.Core;
import com.github.smallru8.NikoBot.Embed;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter{
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event){
		Message msg = event.getMessage();
		if(msg.getContentRaw().equalsIgnoreCase("/upgrade")&&Core.ADMINS.isAdmin("", msg)) {//Global Admin
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.PINK);
		    embed.setTitle(":arrows_counterclockwise: Upgrade");
		    embed.setDescription("Checking for update...");
		    msg.getChannel().sendMessageEmbeds(embed.build()).queue(message -> {
		    	String latestVer = AutoUpdate.getLatestVersion(AutoUpdate.NIKOBOT_GITHUB_RELEASE);
			    if(Core.version.compareTo(latestVer)==-1) {
			    	try {
						URL downloadURL = new URL("https://github.com/skw-nikobot/NikoBot/releases/download/"+latestVer+"/NikoBot-"+latestVer+"-lib.jar");
						File f = new File("libs/NikoBot-"+latestVer+"-lib.jar");
						HttpURLConnection con = (HttpURLConnection) downloadURL.openConnection();
						con.setRequestProperty("User-agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:70.0) Gecko/20100101 Firefox/70.0");
						BufferedInputStream in = new BufferedInputStream(con.getInputStream());
						//int total_len = con.getContentLength();
						String output = "Checking for update...\nCurrent version: "+Core.version+"\n"
								+ "Download "+latestVer+": ";
						embed.setDescription(output);
						message.editMessageEmbeds(embed.build()).queue();
						byte[] data = new byte[1024];
						f.createNewFile();//:white_large_square: :green_square:
						FileOutputStream fos = new FileOutputStream(f);
						int n = 0;
						while((n=in.read(data,0,1024))>=0) {
							fos.write(data,0,n);
							
							//prograss bar
							/*
							sum+=n;
							float f1 = (float)sum/total_len;
							int i1 = (int)(f1/0.2);
							String tmp = "";
							for(int i=0;i<i1;i++)
								tmp+=":white_large_square:";
							embed.setDescription(output+tmp+i1*20+"%");
							message.editMessageEmbeds(embed.build()).queue();
							*/
						}
						fos.flush();
						fos.close();
						in.close();
						
						for(int i=0;i<5;i++)
							output+=":green_square:";
						embed.setDescription(output+"100%");
						message.editMessageEmbeds(embed.build()).queue();
						
						String path = Core.class.getProtectionDomain().getCodeSource().getLocation().getFile();
						String decodedPath = URLDecoder.decode(path,"UTF-8");
						
						Embed.EmbedSender(Color.pink,event.getChannel(),":octagonal_sign: ????????????! System reboot!","");
						String guild_ch_msg = event.getGuild().getId()+" "+event.getChannel().getId()+" "+event.getMessageId();
						//?????????????????????
						if(Core.osType) {
							FileWriter fw = new FileWriter(new File("./rmPreviousLib.bat"));
							fw.write("rem "+guild_ch_msg+"\n");
							fw.write("timeout /t 10\n");
							fw.write("del "+decodedPath.replace("/", "\\")+"\njava -Dfile.encoding=UTF8 -jar "+System.getProperty("java.class.path"));
							fw.flush();
							fw.close();
							Runtime.getRuntime().exec("cmd /k start rmPreviousLib.bat");//windows
						}else {
							FileWriter fw = new FileWriter(new File("./rmPreviousLib.sh"));
							fw.write("#"+guild_ch_msg+"\n");
							fw.write("sleep 10\n");
							fw.write("rm -rf "+decodedPath+"\nnohup java -Dfile.encoding=UTF8 -jar "+System.getProperty("java.class.path"));
							fw.flush();
							fw.close();
							Runtime.getRuntime().exec("sh ./rmPreviousLib.sh");//linux
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.exit(0);
						
			    	} catch (IOException e) {
						e.printStackTrace();
					}	
			    }else {
			    	embed.setTitle(":white_check_mark: Upgrade");
			    	embed.setDescription("Checking for update...\n"
			    			+ "NikoBot version : "+Core.version+" is the latest version.");
			    	message.editMessageEmbeds(embed.build()).queue();
			    }
		    });
		    
		}
	}
}
