package org.realcodingteam.pvptitles;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerListener implements Listener {
	
	Map<String, Integer> k = new HashMap<String, Integer>();
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		
		if (Manager.getFame(e.getPlayer()) < 25) {
			return;
		}
		
		String format = ChatColor.GREEN + "[" + Manager.getRankName(e.getPlayer()) + " Hero] " + ChatColor.RESET;
		if(Manager.getRankName(e.getPlayer()).equals("Hero")) {
			format = ChatColor.GREEN + "[Hero] " + ChatColor.RESET;
		}
		e.setFormat(format + e.getFormat());
	}
	
	@EventHandler
	public void onPlayerKill(PlayerDeathEvent e) {
		
		if (k.containsKey(e.getEntity().getName())) 
			k.put(e.getEntity().getName(), 1);
		
		if(e.getEntity().getKiller() == null) {
			return;
		}
		if (!(e.getEntity().getKiller() instanceof Player)) {
			return;
		}
		
		Player killer = e.getEntity().getKiller();
		int kills = 0;		
		
		if (k.containsKey(killer.getName())) {
			kills = k.get(killer.getName()).intValue();
		}
		else {
			kills++;
		}
		kills++;
		int extrafame=0;
		switch (kills) { 
			case 0:
			case 1:
				extrafame=1;
				break;
			case 2: 
				extrafame=2;
				break;
			case 3:
				extrafame=3;
				break;
			case 4:
				extrafame=4;
				break;
			case 5:
				extrafame=6;
				break;
			case 6:
				extrafame=8;
				break;
			case 7:
				extrafame=12;
				break;
			case 8:
				extrafame=16;
				break;
			case 9:
				extrafame=20;
				break;
			case 10:
				extrafame=24;
				break;
			case 11:
				extrafame=28;
				break;
			case 12:
				extrafame=32;
				break;
			case 13:
				extrafame=36;
				break;
			default:
				extrafame=40;
				break;
		}
		
		Manager.addFame(killer, extrafame);
		killer.sendMessage(ChatColor.GREEN + "You killed " + e.getEntity().getName() + " and received " + extrafame + " fame.");
		k.put(killer.getName(), kills);
	}
	
}
