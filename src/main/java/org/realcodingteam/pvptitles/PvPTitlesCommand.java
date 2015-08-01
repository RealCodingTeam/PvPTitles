package org.realcodingteam.pvptitles;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PvPTitlesCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission("pvptitles.pvptitles")) {
			sender.sendMessage(PvPTitles.errorPerm);
			return true;
		}
		sender.sendMessage("" + ChatColor.GREEN + "PvPTitles for 1.8");
		sender.sendMessage("" + ChatColor.GREEN + "Based on the abandoned plugin PvPTitles by " + ChatColor.GOLD + ChatColor.ITALIC + "asc_dreanor");
		sender.sendMessage("" + ChatColor.GREEN + "Recreated by " + ChatColor.RED + ChatColor.BOLD + "RCT" + ChatColor.GREEN + " (RealCodingTeam): " + ChatColor.DARK_RED + "ProfLuz" + ChatColor.GREEN + ", " + ChatColor.DARK_PURPLE + "Comp");
		sender.sendMessage("" + ChatColor.GREEN + "Commands:");
		sender.sendMessage("" + ChatColor.GREEN + "/fame: Display your fame, next rank, and required fame for next rank");
		sender.sendMessage("" + ChatColor.GREEN + "/ladder: List the top five players on this server");
		sender.sendMessage("" + ChatColor.GREEN + "/pvptitles: Display information about PvPTitles");
		sender.sendMessage("" + ChatColor.GREEN + "---------------- version 1.0");
		return true;
	}
}
