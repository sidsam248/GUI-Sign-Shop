package me.liveinacupboard.gsshop.events.customevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.liveinacupboard.gsshop.handlers.GuiShopHandler;

public class PlayerBuyEvent extends Event {

	private Player p;
	private GuiShopHandler guiShopHandler;

	private static final HandlerList handlers = new HandlerList();

	public PlayerBuyEvent(Player p, GuiShopHandler guiShopHandler) {

		this.p = p;
		this.guiShopHandler = guiShopHandler;

	}

	public Player getPlayer() {

		return p;
	}
	
	public GuiShopHandler getShopHandler() {
		
		return guiShopHandler;
	}
	
	public static HandlerList getHandlerList() {
		
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {

		return handlers;
	}

}
