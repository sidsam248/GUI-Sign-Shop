package me.liveinacupboard.gsshop.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import me.liveinacupboard.gsshop.handlers.GuiShopHandler;
import me.liveinacupboard.gsshop.util.ShopDetails;

public class InventoryClose implements Listener {

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {

		Player player = (Player) e.getPlayer();
		String name = e.getView().getTitle();

		if (!ChatColor.stripColor(name).startsWith("Buy"))
			return;

		GuiShopHandler handler = ShopDetails.getShopDetails(player);

		if (handler == null)
			return;

		ShopDetails.removeShopDetail(handler);

	}

}
