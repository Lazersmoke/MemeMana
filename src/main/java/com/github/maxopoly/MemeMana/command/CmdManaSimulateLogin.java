package com.github.maxopoly.MemeMana.command;

import com.github.maxopoly.MemeMana.MemeManaPlugin;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vg.civcraft.mc.civmodcore.command.PlayerCommand;

public class CmdManaSimulateLogin extends PlayerCommand {
	public CmdManaSimulateLogin(String name) {
		super(name);
		setIdentifier("manasimulatelogin");
		setDescription("Simulate logging in for mana purposes");
		setUsage("/manasimulatelogin");
		setArguments(0,0);
	}

	public boolean execute(CommandSender sender, String [] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Can't simulate login from console");
			return true;
		}
		Player player = (Player) sender;
		sender.sendMessage(ChatColor.YELLOW + "Simulating your login");
		MemeManaPlugin.getInstance().getActivityManager().updatePlayer(player.getUniqueId());
		
		return true;
	}

	public List<String> tabComplete(CommandSender sender, String[] args) {
		return null; // Defaults to players
	}
}
