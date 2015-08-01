package org.realcodingteam.pvptitles;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Manager {
	
	public static HashMap<Player, String> playerPrefixes = new HashMap<Player, String>();
	
	public static PvPTitles getInstance() {
		return PvPTitles.getInstance();
	}
	
	public static int getRemainderFame(int fame) {
		String rank = getRankNameByFame(fame).toLowerCase();
		int reqFame = 0;
		if(getNextRank(rank).toLowerCase().equals("FINALRANKNONEXTRANKYOUFUCKINGTARDFUCKOFFFAGGOT")) {
			return 0;
		}
		reqFame = PvPTitles.getInstance().getConfig().getInt("ranks." + getNextRank(rank.replaceAll("'", "_")).toLowerCase().replaceAll("'", "_"));
		return reqFame - fame;
	}
	
	public static String getRankName(Player player) {
		return getRankNameByFame(getInstance().getConfig().getInt("player." + player.getUniqueId()));
	}
	
	public static String getRankNameByFame(int fame) {
		boolean rankFlag = false;
		String rankName = "";
		for(String rank : getInstance().getConfig().getConfigurationSection("ranks").getKeys(false)) {
			if(rankFlag && getInstance().getConfig().getInt("ranks." + rank)>fame) {
				if(rankName.length()>0) {
					rankName = rankName.substring(0, 1).toUpperCase() + rankName.substring(1);
					return rankName.replaceAll("_", "'");
				}
			}
			if(getInstance().getConfig().getInt("ranks." + rank)<= fame) {
				rankFlag = true;
				rankName = rank;
			}
		}
		if(rankName.length()>0) { 
			rankName = rankName.substring(0, 1).toUpperCase() + rankName.substring(1);
			return rankName.replaceAll("_", "'");
		} else {
			return rankName.replaceAll("_", "'").toUpperCase();
		}
	}
	
	public static String getNextRank(String rank) {
		int pos=0;
		for(int i = 0; i<=PvPTitles.ranks.size()-1;i++) {
			if(PvPTitles.ranks.get(i).equals(rank)) {
				pos = i;
				break;
			}
		}
		if (PvPTitles.ranks.size()-1==pos) {
			return "FINALRANKNONEXTRANKYOUFUCKINGTARDFUCKOFFFAGGOT";
		} else {
			pos+=1;
			if(PvPTitles.ranks.get(pos).length()>0) {
				String rankName = PvPTitles.ranks.get(pos).substring(0, 1).toUpperCase() + PvPTitles.ranks.get(pos).substring(1);
				return rankName.replaceAll("_", "'");
			} else {
				return PvPTitles.ranks.get(pos);
			}
		}
	}
	
	public static void addFame(Player player, int fame) {
		int currentfame = getInstance().getConfig().getInt("player." + player.getUniqueId());
		int totalFame = getInstance().getConfig().getInt("player." + player.getUniqueId()) + fame;
		String currentrank = getRankNameByFame(currentfame).toLowerCase().replaceAll("'","_");
		
		if(currentfame+fame>=getInstance().getConfig().getInt("ranks."+getNextRank(currentrank).toLowerCase().replaceAll("'","_"))) {
			
			if (getNextRank(currentrank).equalsIgnoreCase("hero")) {
				player.sendMessage(ChatColor.GREEN + "Congrats! You are now a Hero!");
			}
			else {
				player.sendMessage(ChatColor.GREEN + "Congrats! You are now a " + getNextRank(currentrank) + " Hero!");
			}
		}

		getInstance().getConfig().set("player." + player.getUniqueId(), totalFame);
		getInstance().saveConfig();
	}
	
	public static int getFame(Player player) {
		return getInstance().getConfig().getInt("player." + player.getUniqueId());
	}
	
	public static boolean isMaxRank(int fame) {
		return fame >= getInstance().getConfig().getInt("ranks." + PvPTitles.ranks.get(PvPTitles.ranks.size() - 1).replace("'", "_"));
	}
}
