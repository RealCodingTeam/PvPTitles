package org.realcodingteam.pvptitles;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FameCommand implements CommandExecutor {
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(PvPTitles.errorOnlyInGame);
			return true;
		}
		if(!sender.hasPermission("pvptitles.fame")) {
			sender.sendMessage(PvPTitles.errorPerm);
			return true;
		}
		
		Player p = (Player) sender;
		
		switch (args.length) {
			case 0:
				int fame = 0;
				
				if (PvPTitles.getInstance().getConfig().isInt("player." + p.getUniqueId())) {
					fame = PvPTitles.getInstance().getConfig().getInt("player." + p.getUniqueId());
				}
				
				String rankname = Manager.getRankNameByFame(fame);
				String nextrank = Manager.getNextRank(Manager.getRankNameByFame(fame).toLowerCase().replaceAll("'", "_"));
				
				p.sendMessage(ChatColor.GREEN + "Fame: " + fame);
				
				if (rankname.equals("Hero")) rankname = "";
				
				if (nextrank.equals("Hero")) nextrank = "";
						
				if (Manager.isMaxRank(fame)) {
					p.sendMessage(ChatColor.GREEN + "Rank: " + rankname);
					p.sendMessage(ChatColor.GREEN + "You are max rank!");
				}
				else if (rankname.equals("Default")) {
					p.sendMessage(ChatColor.GREEN + "Rank: Default Hero");
					p.sendMessage(ChatColor.GREEN + "Required fame for next rank (Hero): " + Manager.getRemainderFame(fame));
				}
				else {
					p.sendMessage(ChatColor.GREEN + "Rank:" +  rankname + " Hero");
					p.sendMessage(ChatColor.GREEN + "Required fame for next rank (" + nextrank + " Hero): " + Manager.getRemainderFame(fame));
				}
				
				return true;
			case 1: 
				
				if (!p.hasPermission("pvptitles.fame.others")) {
					p.sendMessage(PvPTitles.errorPerm);
					return true;
				}
				
				Player target = Bukkit.getServer().getPlayer(args[0]);
				int targetFame = 0;
				
				if (target == null) {
					p.sendMessage(ChatColor.RED + "That player does not exist or is not online!");
					return true;
				}
				
				if (PvPTitles.getInstance().getConfig().isInt("player." + target.getUniqueId())) {
					targetFame = PvPTitles.getInstance().getConfig().getInt("player." + target.getUniqueId());
				}
				
				String rankName = Manager.getRankNameByFame(targetFame);
				String nextRank = Manager.getNextRank(Manager.getRankNameByFame(targetFame).toLowerCase().replaceAll("'", "_"));
				
				if (rankName.equals("Hero")) rankName = "";
				
				if (nextRank.equals("Hero")) nextRank = "";
			
				p.sendMessage(ChatColor.GREEN + "Stats of " + target.getName());
				p.sendMessage(ChatColor.GREEN + "Fame: " + targetFame);
							
				if (Manager.isMaxRank(targetFame)) {
					p.sendMessage(ChatColor.GREEN + "This player is max rank!");
				}
				else if (rankName.equals("Default")) {
					p.sendMessage(ChatColor.GREEN + "Rank: Default Hero");
					p.sendMessage(ChatColor.GREEN + "Required fame for next rank (Hero): " + Manager.getRemainderFame(targetFame));
				}
				else {
					p.sendMessage(ChatColor.GREEN + "Rank: " + rankName + " Hero");
					p.sendMessage(ChatColor.GREEN + "Required fame for next rank (" + nextRank.replaceAll(" ", "") + " Hero): " + Manager.getRemainderFame(targetFame));
				}
				
				return true;
			default:
				sender.sendMessage(ChatColor.RED + "Use /fame to show your own fame or /fame <player> to show another player's fame.");
				
		}
		return true;
		
	}
}
