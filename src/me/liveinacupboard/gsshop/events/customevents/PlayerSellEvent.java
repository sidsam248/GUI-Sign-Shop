package me.liveinacupboard.gsshop.events.customevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PlayerSellEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private Player p;
	private ItemStack item;
	private double price;

	public PlayerSellEvent(Player p, ItemStack item, double price) {

		this.p = p;
		this.item = item;
		this.price = price;

	}

	public Player getPlayer() {

		return p;
	}

	public ItemStack getItem() {

		return item;
	}

	public double getPrice() {

		return price;
	}

	public static HandlerList getHandlerList() {

		return handlers;
	}

	@Override
	public HandlerList getHandlers() {

		return handlers;
	}

}
