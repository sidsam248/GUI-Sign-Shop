package me.liveinacupboard.gsshop.util;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import me.liveinacupboard.gsshop.GuiSignShop;

public class ConfigUtil {

	private GuiSignShop pl;

	public ConfigUtil(GuiSignShop pl) {

		this.pl = pl;

	}

	public String getPrefix() {

		return replaceColour(pl.getConfig().getString("Prefix")) + " ";
	}

	public String noItem(ItemStack i) {

		return getPrefix() + replaceColour(
				pl.getConfig().getString("No-Item").replace("{item}", i.getType().toString().replace("_", " ")));

	}

	public String noPerm() {

		return getPrefix() + replaceColour(pl.getConfig().getString("No-Perm"));
	}

	public String noPermCmd() {

		return getPrefix() + replaceColour(pl.getConfig().getString("No-Perm-Cmd"));
	}

	public String newBal(double bal) {

		return getPrefix() + replaceColour(pl.getConfig().getString("New-Bal").replace("{bal}", String.valueOf(bal)));
	}

	public String noItemInCart() {

		return getPrefix() + replaceColour(pl.getConfig().getString("No-Item-In-Cart"));
	}

	public String noMoney(ItemStack i) {

		return getPrefix() + replaceColour(
				pl.getConfig().getString("No-Money").replace("{item}", i.getType().toString().replace("_", " ")));
	}

	public String fullInventory() {

		return getPrefix() + replaceColour(pl.getConfig().getString("Full-Inventory"));
	}

	public String transCancelled() {

		return getPrefix() + replaceColour(pl.getConfig().getString("Transaction-Cancelled"));
	}

	public String transSuccess(ItemStack i) {

		return getPrefix() + replaceColour(pl.getConfig().getString("Transaction-Successful").replace("{item}",
				i.getType().toString().replace("_", " ")));
	}

	public char getCurrencySign() {

		return pl.getConfig().getString("Currency-Sign").toCharArray()[0];
	}

	private static String replaceColour(String s) {

		return ChatColor.translateAlternateColorCodes('&', s);
	}

}
