package me.liveinacupboard.gsshop.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import me.liveinacupboard.gsshop.GuiSignShop;
import me.liveinacupboard.gsshop.events.customevents.PlayerSellEvent;

public class SellEvent implements Listener {

	private GuiSignShop pl;

	public SellEvent(GuiSignShop pl) {

		this.pl = pl;

	}

	@EventHandler
	public void onSellEvent(PlayerSellEvent e) {

		Player player = e.getPlayer();
		ItemStack item = e.getItem();
		double price = e.getPrice();

		int totalItems = 0;

		for (ItemStack i : player.getInventory().getContents()) {

			if (i == null || i.getType() == Material.AIR)
				continue;

			if (!i.isSimilar(item))
				continue;

			totalItems += i.getAmount();

			player.getInventory().remove(i);
			player.updateInventory();

		}

		double totalPrice = totalItems * price;

		if (totalPrice > 0) {

			pl.getEconomy().depositPlayer(player, totalPrice);
			player.sendMessage(pl.getConfigUtil().newBal(pl.getEconomy().getBalance(player)));

		} else
			player.sendMessage(pl.getConfigUtil().noItem(item));

	}

}
