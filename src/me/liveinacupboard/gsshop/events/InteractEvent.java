package me.liveinacupboard.gsshop.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.liveinacupboard.gsshop.GuiSignShop;
import me.liveinacupboard.gsshop.events.customevents.PlayerSellEvent;
import me.liveinacupboard.gsshop.handlers.GuiShopHandler;
import me.liveinacupboard.gsshop.handlers.ShopHandler;
import me.liveinacupboard.gsshop.handlers.sign.SignType;
import me.liveinacupboard.gsshop.stickysign.StickySign;
import me.liveinacupboard.gsshop.stickysign.StickySignHandler;
import me.liveinacupboard.gsshop.util.ShopDetails;
import net.md_5.bungee.api.ChatColor;

public class InteractEvent implements Listener {

	private GuiSignShop pl;
	private GuiShopHandler guiHandler;
	private StickySignHandler ssHandler;

	public InteractEvent(GuiSignShop pl) {

		this.pl = pl;
		ssHandler = new StickySignHandler(pl);

	}

	@EventHandler
	public void onSignClick(PlayerInteractEvent e) {

		Player player = e.getPlayer();

		if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		if (!(e.getClickedBlock().getState() instanceof Sign))
			return;

		Sign sign = (Sign) e.getClickedBlock().getState();

		if (!ChatColor.stripColor(sign.getLine(0)).equals("[Shop]"))
			return;

		if (!player.hasPermission("guishop.use"))
			return;

		SignType type = getSignType(ChatColor.stripColor(sign.getLine(1)));

		StickySign ss = StickySign.getStickySign(sign);

		if (ss != null)
			type = SignType.STICKY_SIGN;

		ShopHandler shopHandler = new ShopHandler(pl, sign);

		if (type == SignType.NOT_DETECTABLE) {

			player.sendMessage(pl.getConfigUtil().getPrefix() + ChatColor.DARK_RED
					+ "This does not seem to be a valid sign, please contact an admin!");
			return;

		} else if (type == SignType.SELL) {

			double price = shopHandler.getPrice();
			ItemStack item = shopHandler.getItem();
			short data = shopHandler.getData();

			Bukkit.getPluginManager().callEvent(new PlayerSellEvent(player, item, price));
			return;

		} else if (type == SignType.STICKY_SIGN) {

			ShopHandler shop = new ShopHandler(pl, sign, ss);

			double stickyPrice = shop.getPrice();
			ItemStack stickyItem = ss.getItem();

			Inventory inv = shop.createStickyBuyInventory();

			guiHandler = new GuiShopHandler(stickyPrice, 0, stickyItem, (short) 0, inv, player, type);

			ShopDetails.addShopDetail(guiHandler);

			player.openInventory(inv);

			return;

		} else {

			Inventory inv = shopHandler.createBuyInventory();

			double price = shopHandler.getPrice();
			ItemStack item = shopHandler.getItem();
			short data = shopHandler.getData();

			guiHandler = new GuiShopHandler(price, 0, item, data, inv, player, type);

			ShopDetails.addShopDetail(guiHandler);

			player.openInventory(inv);

		}

	}

	@EventHandler
	public void onStickyInteract(PlayerInteractEvent e) {

		if (e.getAction() != Action.LEFT_CLICK_BLOCK)
			return;

		if (!(e.getClickedBlock().getState() instanceof Sign))
			return;

		Sign sign = (Sign) e.getClickedBlock().getState();

		if (!sign.getLine(0).equalsIgnoreCase("<sticky>"))
			return;

		if (!StickySign.getPlayerData().containsKey(e.getPlayer().getUniqueId()))
			return;

		e.setCancelled(true);

		String name = StickySign.getPlayerData().get(e.getPlayer().getUniqueId());
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();

		if ((item == null) || (item.getType() == Material.AIR))
			return;

		String price = sign.getLine(1);

		ssHandler.createNewStickyShop(item, name, e.getPlayer());

		sign.setLine(0, ChatColor.translateAlternateColorCodes('&', "&4[&6&lShop&4]"));
		sign.setLine(1, ChatColor.translateAlternateColorCodes('&', "&l&bBuy"));
		sign.setLine(2, ChatColor.translateAlternateColorCodes('&', "&l" + name));
		sign.setLine(3, price);

		e.getPlayer().sendMessage(
				pl.getConfigUtil().getPrefix() + ChatColor.GREEN + "The sticky sign has successfully been created!");

		StickySign.getPlayerData().remove(e.getPlayer().getUniqueId());

		sign.update();
	}

	private SignType getSignType(String type) {

		switch (type) {

		case "Buy":
			return SignType.BUY;

		case "Sell":
			return SignType.SELL;

		}

		return SignType.NOT_DETECTABLE;

	}

}
