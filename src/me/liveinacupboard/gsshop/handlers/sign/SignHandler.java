package me.liveinacupboard.gsshop.handlers.sign;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.liveinacupboard.gsshop.GuiSignShop;
import net.md_5.bungee.api.ChatColor;

public class SignHandler {

	private GuiSignShop pl;
	private Sign sign;

	public SignHandler(GuiSignShop pl) {

		this.pl = pl;

	}

	public SignHandler(GuiSignShop pl, Sign sign) {

		this.sign = sign;
		this.pl = pl;

	}

	public void signUpdate(Sign sign, Player p) {

		if (!sign.getLine(0).equalsIgnoreCase("[Shop]"))
			return;

		if (!sign.getLine(1).equalsIgnoreCase("Buy") && !sign.getLine(1).equalsIgnoreCase("Sell"))
			return;

		if (!p.hasPermission("guishop.admin")) {

			p.sendMessage(pl.getConfigUtil().noPerm());
			sign.getBlock().breakNaturally();
			return;
		}

		boolean isDigit = false;

		String mat = sign.getLine(2).contains(":") ? sign.getLine(2).split(":")[0] : sign.getLine(2);

		char[] matArr = mat.toCharArray();

		for (char c : matArr) {

			if (Character.isDigit(c)) {

				isDigit = true;

				break;
			}

		}

		Material material;

		if (isDigit)
			material = Material.getMaterial(Integer.valueOf(mat));
		else
			material = Material.getMaterial(mat.toUpperCase());

		if (material == null) {

			p.sendMessage(ChatColor.DARK_RED + "INVALID MATERIAL ENTERED");
			sign.getBlock().breakNaturally();
			return;

		}

		String price = sign.getLine(3);

		if (price == null || price.isEmpty()) {

			p.sendMessage(ChatColor.DARK_RED + "PRICE HAS NOT BEEN ENTERED");
			sign.getBlock().breakNaturally();
			return;

		}

		char[] priceArr = price.toCharArray();

		for (char c : priceArr) {

			if (!Character.isDigit(c)) {

				p.sendMessage(ChatColor.DARK_RED + "INVALID PRICE ENTERED");
				sign.getBlock().breakNaturally();
				return;

			}

		}

		sign.setLine(0, ChatColor.translateAlternateColorCodes('&', "&4[&6&lShop&4]"));

		String line1 = sign.getLine(1).equalsIgnoreCase("Buy") ? "&l&bBuy" : "&l&cSell";

		sign.setLine(1, ChatColor.translateAlternateColorCodes('&', line1));

		sign.setLine(2, ChatColor.translateAlternateColorCodes('&', "&l" + sign.getLine(2)));
		
		sign.update(true);

	}

	public ItemStack getItem() {

		int damageVal = 0;
		boolean isDigit = false;

		String s = ChatColor.stripColor(sign.getLine(2)).contains(":")
				? ChatColor.stripColor(sign.getLine(2)).split(":")[0] : ChatColor.stripColor(sign.getLine(2));

		for (char c : s.toCharArray()) {

			if (Character.isDigit(c)) {

				isDigit = true;
			}
		}

		Material mat;

		if (isDigit) {

			mat = Material.getMaterial(Integer.valueOf(s));

		} else {

			mat = Material.getMaterial(s.toUpperCase());

		}

		if (sign.getLine(2).contains(":")) {

			damageVal = Integer.valueOf(ChatColor.stripColor(sign.getLine(2)).split(":")[1]);
		}

		return new ItemStack(mat, 1, (short) damageVal);
	}

	public double getPrice() {

		return Double.valueOf(ChatColor.stripColor(sign.getLine(3)));
	}

	public short getData() {

		return sign.getLine(2).contains(":") ? Short.valueOf(ChatColor.stripColor(sign.getLine(2)).split(":")[1]) : 0;
	}

	public char getCurrencySign() {

		return pl.getConfigUtil().getCurrencySign();
	}

}
