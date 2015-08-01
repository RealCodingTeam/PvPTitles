package org.realcodingteam.pvptitles;

import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LadderCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!sender.hasPermission("pvptitles.ladder")) {
			sender.sendMessage(PvPTitles.errorPerm);
			return true;
		}
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(PvPTitles.errorOnlyInGame);
			return true;
		}
		
		TreeMap<Integer, UUID> players = new TreeMap<Integer, UUID>();
		Player player = (Player) sender;
		int count = 0;
		if(PvPTitles.getInstance().getConfig().contains("player")) {
			for (String s : PvPTitles.getInstance().getConfig().getConfigurationSection("player").getKeys(true)) {
				UUID id = UUID.fromString(s);
				players.put(PvPTitles.getInstance().getConfig().getInt("player."+id.toString()), id);
			}
				
			player.sendMessage(ChatColor.AQUA + "Ladder - Top Five Players");
			player.sendMessage(ChatColor.AQUA + "----------------------");
			
			for (Entry<Integer, UUID> entry : players.descendingMap().entrySet()) {
		
				if(players.entrySet().size()-1<count) break;
				
				if (count >= 5) {
					break;
				}
			
				player.sendMessage("" + ChatColor.AQUA + (count + 1) + ". " + ChatColor.GOLD + Bukkit.getOfflinePlayer(entry.getValue()).getName() + " (" + ChatColor.GREEN + entry.getKey() + " Fame" + ChatColor.GOLD + ")");
				count++;
			}
		} else {
			player.sendMessage(ChatColor.AQUA + "Ladder - Top Five Players");
			player.sendMessage(ChatColor.AQUA + "----------------------");
			player.sendMessage("No one has killed anyone yet.");
		}
		
		return true;
	}
	
}