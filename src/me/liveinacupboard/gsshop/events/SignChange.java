package me.liveinacupboard.gsshop.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.liveinacupboard.gsshop.GuiSignShop;
import me.liveinacupboard.gsshop.handlers.sign.SignHandler;
import me.liveinacupboard.gsshop.stickysign.StickySign;

public class SignChange implements Listener {

	private GuiSignShop pl;
	private SignHandler bSign;

	public SignChange(GuiSignShop pl) {

		this.pl = pl;
		bSign = new SignHandler(pl);

	}

	@EventHandler
	public void onPlace(SignChangeEvent e) {

		if (!e.getLine(0).equalsIgnoreCase("[Shop]"))
			return;

		new BukkitRunnable() {

			public void run() {

				bSign.signUpdate((Sign) e.getBlock().getState(), e.getPlayer());

			}

		}.runTaskLater(pl, 2L);

	}

	// Sticky Sign
	@EventHandler
	public void onStickyPlace(SignChangeEvent e) {

		if (!e.getLine(0).equalsIgnoreCase("<sticky>")) {
			return;
		}

		String price = e.getLine(1);

		if ((price == null) || (price.isEmpty())) {

			e.getPlayer().sendMessage(ChatColor.DARK_RED + "INVALID PRICE ENTERED!");
			e.getBlock().breakNaturally();
			return;

		}

		for (char c : price.toCharArray()) {

			if (!Character.isDigit(c)) {

				e.getPlayer().sendMessage(ChatColor.DARK_RED + "INVALID PRICE ENTERED!");
				e.getBlock().breakNaturally();
				return;
			}

		}

		Player player = e.getPlayer();

		if (!StickySign.getPlayerData().containsKey(player.getUniqueId()))
			return;

		String name = StickySign.getPlayerData().get(player.getUniqueId());

		Location loc = e.getBlock().getLocation();

		pl.getStickySignsFile().set(name + ".location",
				loc.getWorld().getName() + ", " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());

		pl.saveStickySignsFile();

		player.sendMessage(pl.getConfigUtil().getPrefix() + ChatColor.GREEN
				+ "Created a sticky sign! Now left click it with an item to create a sign!");
	}

}
