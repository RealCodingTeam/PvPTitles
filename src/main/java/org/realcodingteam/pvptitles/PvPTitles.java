package org.realcodingteam.pvptitles;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class PvPTitles extends JavaPlugin {
	
	public final static String errorOnlyInGame = ChatColor.DARK_RED + "Only in-game players may use this command.";
	public final static String errorPerm = ChatColor.DARK_RED + "You do not have permission to run this command.";
	public static PvPTitles pvpTitles;
	public static ArrayList<String> ranks = new ArrayList<String>();
	
	public void onEnable() {
		pvpTitles = this;
		this.saveDefaultConfig();
		errorCheckLoad();
		loadReadMe();
		initRanks();
		getCommand("ladder").setExecutor(new LadderCommand());
		getCommand("fame").setExecutor(new FameCommand());
		getCommand("pvptitles").setExecutor(new PvPTitlesCommand());
		getCommand("setfame").setExecutor(new SetFameCommand());
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
	}
	
	public void onDisable() {
		saveConfig();
	}
	
	public static PvPTitles getInstance() {
		return pvpTitles;
	}
	
	public void errorCheckLoad() {
		if (!getConfig().contains("ranks")) {
			getConfig().set("ranks.default", 0);
			getConfig().set("ranks.hero", 25);
			getConfig().set("ranks.fierce", 75);
			getConfig().set("ranks.mighty", 180);
			getConfig().set("ranks.deadly", 360);
			getConfig().set("ranks.terrifying", 600);
			getConfig().set("ranks.conquering", 1000);
			getConfig().set("ranks.subjugating", 1680);
			getConfig().set("ranks.vanquishing", 2800);
			getConfig().set("ranks.renowned", 4665);
			getConfig().set("ranks.illustrious", 7750);
			getConfig().set("ranks.eminent", 12960);
			getConfig().set("ranks.king_s", 21600);
			getConfig().set("ranks.emperor_s", 36000);
			getConfig().set("ranks.balthazar_s", 60000);
			getConfig().set("ranks.legendary", 100000);
		}
		saveConfig();
		
		for (String s : getConfig().getConfigurationSection("ranks").getKeys(false)) {
			if(!getConfig().isInt("ranks." + s)) {
				getLogger().severe("Rank required fame values are not integers! Error with rank: " + s);
				Bukkit.getPluginManager().disablePlugin(this);
			}
				
		}
	}
	
	public void loadReadMe() {
		File readmeFile = new File(getInstance().getDataFolder(), "readme.txt");
		if(!readmeFile.exists()) {
			try {
				readmeFile.createNewFile();
				PrintWriter writer = new PrintWriter(readmeFile.getAbsolutePath(), "UTF-8");
				writer.println("PvPTitles plugin for 1.8 - Based on the abandoned plugin: http://dev.bukkit.org/bukkit-plugins/pvp-titles/ - Coded by RealCodingTeam (ProfLuz and Comp)");
				writer.println("You may add new ranks by adding a new entry under the section \"ranks\" with an int (required fame for rank)");
				writer.println("Replace all apostrophes \"'\" with an underscore \"_\"");
				writer.println("DO NOT REMOVE THE DEFAULT RANK!!!");
				writer.println("Player's fame is saved under the section \"player\" with entries as player UUIDs.");
				writer.println("You may reset the config sections to default states by deleting the sections you wish to reset,");
				writer.println("I.E. delete the \"ranks\" section if you wish to get the default ranks back.");
				writer.println("Permission nodes:");
				writer.println("pvptitles.ladder - Allows access to the /ladder command (leaderboard command)");
				writer.println("pvptitles.fame - Allows access to the /fame command (information command)");
				writer.println("pvptitles.fame.others - Allows viewing of other player's fame");
				writer.println("pvptitles.pvptitles - Allows access to the /pvptitles command (about this plugin command)");
				writer.println("pvptitles.setfame - Change a player's fame in game");
				writer.println("----------------------------------------------------");
				writer.println("Commands:");
				writer.println("/fame [player]                - Display your own fame and information or display another player's fame and information");
				writer.println("/ladder                       - List five players who have the most fame on the server");
				writer.println("/pvptitles                    - Display information about this plugin");
				writer.println("/setfame [player] [amt]       - Change another player's fame in game");
				writer.close();
			} catch (IOException e) {}
		}
	}
	
	public void initRanks() {
		for(String s : getConfig().getConfigurationSection("ranks").getKeys(false)) {
			ranks.add(s);
		}		
	}
}
