package me.liveinacupboard.gsshop.util;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

import me.liveinacupboard.gsshop.handlers.GuiShopHandler;

public class ShopDetails {

	private static Set<GuiShopHandler> shopDetails = new HashSet<>();

	public static GuiShopHandler getShopDetails(Player p) {

		for (GuiShopHandler handler : shopDetails) {

			if (handler.getPlayer() == p)
				return handler;

		}

		return null;

	}

	public static void addShopDetail(GuiShopHandler handler) {

		shopDetails.add(handler);

	}

	public static void removeShopDetail(GuiShopHandler handler) {

		shopDetails.remove(handler);

	}

}
