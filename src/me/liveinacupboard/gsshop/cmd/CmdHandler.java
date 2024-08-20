package me.liveinacupboard.gsshop.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.liveinacupboard.gsshop.GuiSignShop;
import me.liveinacupboard.gsshop.stickysign.StickySign;

public class CmdHandler {

	private GuiSignShop pl;

	public CmdHandler(GuiSignShop pl) {

		this.pl = pl;

	}

	public void sendHelp(CommandSender p) {

		p.sendMessage(ChatColor.RED + "================");
		p.sendMessage(pl.getConfigUtil().getPrefix() + ChatColor.GOLD + "/guishop reload");
		p.sendMessage(pl.getConfigUtil().getPrefix() + ChatColor.GOLD + "/guishop [stickysign/ss] <name>");
		p.sendMessage(ChatColor.RED + "================");

	}

	public void reloadConfig(CommandSender p) {

		if (!p.hasPermission("guishop.load")) {

			p.sendMessage(pl.getConfigUtil().noPermCmd());
			return;
		}

		pl.reloadConfig();
		pl.saveConfig();

		pl.reloadStickySignsFile();
		pl.saveStickySignsFile();

		p.sendMessage(pl.getConfigUtil().getPrefix() + ChatColor.GREEN + "All configs have been reloaded successfully");

	}

	public void createStickySign(Player p, String name) {

		if (!p.hasPermission("guishop.admin")) {

			p.sendMessage(pl.getConfigUtil().noPermCmd());
			return;
		}

		if (name.length() >= 26) {

			p.sendMessage(pl.getConfigUtil().getPrefix() + ChatColor.RED
					+ "The name of the sign cannot be more than 26 characters!");
			return;
		}

		StickySign.addPlayerData(p.getUniqueId(), name);
		p.sendMessage(
				pl.getConfigUtil().getPrefix() + ChatColor.GREEN + "Place a sign to register it as a sticky sign");

	}

}
