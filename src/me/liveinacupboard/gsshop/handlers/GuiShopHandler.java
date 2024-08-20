package me.liveinacupboard.gsshop.handlers;

import java.util.Collections;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.liveinacupboard.gsshop.handlers.sign.SignType;

public class GuiShopHandler {

	private double price;
	private int totalItems;
	private ItemStack item;
	private short data;
	private Inventory inv;
	private Player p;
	private SignType signType;

	public GuiShopHandler(double price, int totalItems, ItemStack item, short data, 
			Inventory inv, Player p, SignType signType) {

		this.price = price;
		this.totalItems = totalItems;
		this.item = item;
		this.data = data;
		this.inv = inv;
		this.p = p;
		this.signType = signType;

	}

	public SignType getSignType() {

		return signType;
	}

	public Player getPlayer() {

		return p;
	}

	public int getTotalitems() {

		return totalItems;
	}

	public double getTotalPrice() {

		return price * totalItems;
	}

	public ItemStack getItem() {

		return item;
	}
	
	public short getData() {
		
		return data;
	}

	public void setTotalItems(int x) {

		totalItems = x;

		ItemStack item = inv.getItem(22);
		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eTotal Items: " + totalItems));
		meta.setLore(Collections
				.singletonList(ChatColor.translateAlternateColorCodes('&', "&6Price: &a" + totalItems * price)));

		item.setItemMeta(meta);
	}

}
