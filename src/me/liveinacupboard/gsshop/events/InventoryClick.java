package me.liveinacupboard.gsshop.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import me.liveinacupboard.gsshop.GuiSignShop;
import me.liveinacupboard.gsshop.events.customevents.PlayerBuyEvent;
import me.liveinacupboard.gsshop.handlers.GuiShopHandler;
import me.liveinacupboard.gsshop.util.ShopDetails;

public class InventoryClick implements Listener {

	private GuiSignShop pl;

	public InventoryClick(GuiSignShop pl) {

		this.pl = pl;

	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {

		Player player = (Player) e.getWhoClicked();

		Inventory inv = (Inventory) e.getClickedInventory();

		if (inv == null)
			return;
		
		String name = e.getView().getTitle();

		if (!name.startsWith(ChatColor.GOLD + "Buy"))
			return;

		GuiShopHandler handler = ShopDetails.getShopDetails(player);

		if (handler == null)
			return;

		if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR
				|| e.getCurrentItem().getType() == Material.MINECART)
			return;

		int items = e.getCurrentItem().getAmount();

		e.setCancelled(true);

		switch (e.getCurrentItem().getType()) {

		case STAINED_GLASS_PANE:

			if (e.getCurrentItem().getDurability() == 5) {

				if (handler.getTotalitems() == 0) {

					player.closeInventory();
					ShopDetails.removeShopDetail(handler);
					player.sendMessage(pl.getConfigUtil().noItemInCart());
					return;

				}

				Bukkit.getPluginManager().callEvent(new PlayerBuyEvent(player, handler));
				player.closeInventory();

			}

			else if (e.getCurrentItem().getData().getData() == 14) {

				player.closeInventory();
				ShopDetails.removeShopDetail(handler);
				player.sendMessage(pl.getConfigUtil().transCancelled());

			}

			break;

		default:

			if (e.getClick().isLeftClick())
				handler.setTotalItems(handler.getTotalitems() + items);

			else if (e.getClick().isRightClick()) {

				if (handler.getTotalitems() - items < 0)
					handler.setTotalItems(0);

				handler.setTotalItems(handler.getTotalitems() - items);

			}

			break;
		}

	}

}
