package me.liveinacupboard.gsshop.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.liveinacupboard.gsshop.GuiSignShop;

public class Cmd implements CommandExecutor {

	private GuiSignShop pl;
	private CmdHandler cmdHandler;

	public Cmd(GuiSignShop pl) {

		this.pl = pl;

		cmdHandler = new CmdHandler(pl);

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {

		int length = args.length;

		if (length == 0) {

			cmdHandler.sendHelp(sender);

			return true;

		} else if (length == 1) {

			if (args[0].equalsIgnoreCase("reload")) {

				cmdHandler.reloadConfig(sender);

				return true;

			}

			cmdHandler.sendHelp(sender);

			return true;

		} else if (length == 2) {

			if (args[0].equalsIgnoreCase("ss") || args[0].equalsIgnoreCase("stickysign")) {

				if (!(sender instanceof Player)) {

					sender.sendMessage(pl.getPluginPrefix() + "Command cannot be used from the console");

					return true;
				}

				Player player = (Player) sender;

				String name = args[1];

				cmdHandler.createStickySign(player, name);

				return true;

			}

			cmdHandler.sendHelp(sender);

			return true;

		}

		return false;
	}

}
