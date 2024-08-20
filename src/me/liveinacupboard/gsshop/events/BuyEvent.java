package me.liveinacupboard.gsshop.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.liveinacupboard.gsshop.GuiSignShop;
import me.liveinacupboard.gsshop.events.customevents.PlayerBuyEvent;
import me.liveinacupboard.gsshop.handlers.sign.SignType;
import me.liveinacupboard.gsshop.util.ShopDetails;
import net.milkbowl.vault.economy.EconomyResponse;

public class BuyEvent implements Listener {

	private GuiSignShop pl;

	public BuyEvent(GuiSignShop pl) {

		this.pl = pl;

	}

	// Sticky Sign
	@EventHandler
	public void onStickyBuy(PlayerBuyEvent e) {

		if (e.getShopHandler().getSignType() != SignType.STICKY_SIGN)
			return;

		Player player = e.getPlayer();
		ItemStack item = e.getShopHandler().getItem();
		int totalItems = e.getShopHandler().getTotalitems();
		double totalPrice = e.getShopHandler().getTotalPrice();

		item.setAmount(1);

		EconomyResponse r = pl.getEconomy().withdrawPlayer(player, totalPrice);

		if (!r.transactionSuccess()) {

			player.sendMessage(pl.getConfigUtil().noMoney(item));
			player.closeInventory();
			ShopDetails.removeShopDetail(e.getShopHandler());
			return;
		}

		while (totalItems > 0) {

			if (player.getInventory().firstEmpty() == -1) {

				player.sendMessage(pl.getConfigUtil().fullInventory());
				player.getLocation().getWorld().dropItemNaturally(player.getLocation(),
						new ItemStack(item.getType(), totalItems, item.getDurability()));

				break;
			}

			player.getInventory().addItem(item);
			totalItems--;
		}

		player.sendMessage(pl.getConfigUtil().transSuccess(item));
		player.sendMessage(pl.getConfigUtil().newBal(pl.getEconomy().getBalance(player)));

		player.updateInventory();

		ShopDetails.removeShopDetail(e.getShopHandler());

		player.closeInventory();

	}

	// Normal Buy Sign
	@EventHandler
	public void onItemBuy(PlayerBuyEvent e) {

		if (e.getShopHandler().getSignType() != SignType.BUY)
			return;

		Player player = e.getPlayer();
		ItemStack item = e.getShopHandler().getItem();
		short data = e.getShopHandler().getData();
		int totalItems = e.getShopHandler().getTotalitems();
		double totalPrice = e.getShopHandler().getTotalPrice();

		EconomyResponse r = pl.getEconomy().withdrawPlayer(player, totalPrice);

		if (!r.transactionSuccess()) {

			player.sendMessage(pl.getConfigUtil().noMoney(item));
			player.closeInventory();
			ShopDetails.removeShopDetail(e.getShopHandler());
			return;
		}

		if (item.getType() == Material.SPAWNER) {

			ItemMeta meta = item.getItemMeta();

			String mobName = EntityType.fromId(data).toString();

			String displayName = ChatColor.YELLOW + mobName.substring(0, 1)
					+ mobName.substring(1).replace("_", " ").toLowerCase() + ChatColor.WHITE + " Spawner";

			meta.setDisplayName(displayName);
			item.setItemMeta(meta);

		}

		while (totalItems > 0) {

			if (player.getInventory().firstEmpty() == -1) {

				player.sendMessage(pl.getConfigUtil().fullInventory());
				player.getLocation().getWorld().dropItemNaturally(player.getLocation(),
						new ItemStack(item.getType(), totalItems, item.getDurability()));

				break;
			}

			player.getInventory().addItem(item);
			totalItems--;
		}

		player.sendMessage(pl.getConfigUtil().transSuccess(item));
		player.sendMessage(pl.getConfigUtil().newBal(pl.getEconomy().getBalance(player)));

		player.updateInventory();

		ShopDetails.removeShopDetail(e.getShopHandler());

		player.closeInventory();
	}

}
