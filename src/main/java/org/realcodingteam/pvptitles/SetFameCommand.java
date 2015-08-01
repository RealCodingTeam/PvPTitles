package org.realcodingteam.pvptitles;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetFameCommand implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission("pvptitles.setfame")) {
			sender.sendMessage(PvPTitles.errorPerm);
			return true;
		}
		if(args.length<2) {
			sender.sendMessage(ChatColor.RED + "Usage: /setfame <PLAYER NAME> <AMOUNT>");
			return true;
		}
		int fameamt = 0;
		try {
			fameamt = Integer.parseInt(args[1]);
		} catch(NumberFormatException e) {
			sender.sendMessage(ChatColor.RED + "Usage: /setfame <PLAYER NAME> <AMOUNT>");
			return true;
		}
		Player player = Bukkit.getServer().getPlayer(args[0]);
		if(player==null) {
			sender.sendMessage(ChatColor.RED + "That player does not exist or is not online!");
			return true;
		} else {
			int prevfame = PvPTitles.getInstance().getConfig().getInt("player." + player.getUniqueId().toString());
			PvPTitles.getInstance().getConfig().set("player." + player.getUniqueId().toString(), fameamt);
			sender.sendMessage(ChatColor.GREEN + "Set " + args[0] + (!(args[0].substring(args[0].length()-1, args[0].length()).equals("s"))? "'s" : "'") + " fame to " + fameamt + " (from " + prevfame + " Fame)");
			if(sender instanceof Player) {
				if(!((Player) sender).equals(player)) {
					player.sendMessage(ChatColor.GREEN + "[PvPTitles] Your fame has been set to " + fameamt + " from " + prevfame);
				}
			}
		}
		return true;
	}
}
