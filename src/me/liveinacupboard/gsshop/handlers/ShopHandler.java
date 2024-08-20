package me.liveinacupboard.gsshop.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.liveinacupboard.gsshop.GuiSignShop;
import me.liveinacupboard.gsshop.handlers.sign.SignHandler;
import me.liveinacupboard.gsshop.stickysign.StickySign;

public class ShopHandler {

	private SignHandler signHandler;
	private StickySign ss;

	public ShopHandler(GuiSignShop pl, Sign sign) {

		signHandler = new SignHandler(pl, sign);

	}

	public ShopHandler(GuiSignShop pl, Sign sign, StickySign ss) {

		signHandler = new SignHandler(pl, sign);
		this.ss = ss;

	}

	public Inventory createBuyInventory() {

		String name = "&6Buy &c" + signHandler.getItem().getTypeId() + ":" + signHandler.getItem().getDurability()
				+ "&a - " + "&6" + signHandler.getCurrencySign() + signHandler.getPrice();

		if (name.length() > 32)
			name = name.substring(0, 31);

		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', name));

		inv.setItem(2, signHandler.getItem());
		inv.setItem(3, getItemWithAmount(2));
		inv.setItem(4, getItemWithAmount(5));
		inv.setItem(5, getItemWithAmount(32));
		inv.setItem(6, getItemWithAmount(64));

		inv.setItem(18, createItem(Material.STAINED_GLASS_PANE, (short) 5, "&aAccept Transaction",
				Collections.singletonList("&6Click to Accept transaction")));

		inv.setItem(22, createItem(Material.MINECART, (short) 0, "&eTotal Items: 0",
				Collections.singletonList("&6Price: &a0")));

		inv.setItem(26, createItem(Material.STAINED_GLASS_PANE, (short) 14, "&cDeny Transaction",
				Collections.singletonList("&6Click to Deny transaction")));

		return inv;
	}

	public Inventory createStickyBuyInventory() {

		String name = "&6Buy &c" + ss.getName() + "&a - " + "&6" + signHandler.getCurrencySign()
				+ signHandler.getPrice();

		if (name.length() > 32)
			name = name.substring(0, 31);

		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', name));

		inv.setItem(2, getStickyItemWithAmount(1));
		inv.setItem(3, getStickyItemWithAmount(2));
		inv.setItem(4, getStickyItemWithAmount(5));
		inv.setItem(5, getStickyItemWithAmount(32));
		inv.setItem(6, getStickyItemWithAmount(64));

		inv.setItem(18, createItem(Material.STAINED_GLASS_PANE, (short) 5, "&aAccept Transaction",
				Collections.singletonList("&6Click to Accept transaction")));

		inv.setItem(22, createItem(Material.MINECART, (short) 0, "&eTotal Items: 0",
				Collections.singletonList("&6Price: &a0")));

		inv.setItem(26, createItem(Material.STAINED_GLASS_PANE, (short) 14, "&cDeny Transaction",
				Collections.singletonList("&6Click to Deny transaction")));

		return inv;
	}

	private ItemStack getItemWithAmount(int amount) {

		return new ItemStack(signHandler.getItem().getType(), amount, signHandler.getItem().getDurability());
	}

	private ItemStack getStickyItemWithAmount(int amount) {

		ItemStack item = ss.getItem();

		item.setAmount(amount);

		return item;
	}

	public double getPrice() {

		return signHandler.getPrice();
	}

	public ItemStack getItem() {

		return signHandler.getItem();
	}

	public short getData() {

		return signHandler.getData();
	}

	private ItemStack createItem(Material mat, short data, String name, List<String> lore) {

		ItemStack item = new ItemStack(mat, 1, data);
		ItemMeta meta = item.getItemMeta();
		List<String> finalLore = new ArrayList<>();

		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

		if (!lore.isEmpty()) {

			for (String s : lore) {

				finalLore.add(ChatColor.translateAlternateColorCodes('&', s));

			}

		}

		meta.setLore(finalLore);

		item.setItemMeta(meta);

		return item;
	}

}
