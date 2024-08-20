package me.liveinacupboard.gsshop.stickysign;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;

public class StickySign {

	private Location loc;
	private ItemStack item;
	private String name;

	private static Set<StickySign> stickySigns = new HashSet<>();
	private static Map<UUID, String> playerData = new HashMap<>();

	public StickySign(Location loc, ItemStack item, String name) {

		this.loc = loc;
		this.item = item;
		this.name = name;

	}

	public static void addStickySign(StickySign stickySign) {

		stickySigns.add(stickySign);

	}

	public static void addPlayerData(UUID uuid, String name) {

		playerData.put(uuid, name);

	}

	public static void removeStickySign(StickySign stickySign) {

		stickySigns.remove(stickySign);

	}

	public static void removePlayerData(UUID uuid) {

		playerData.remove(uuid);

	}

	public static Set<StickySign> getStickySigns() {

		return stickySigns;
	}

	public static Map<UUID, String> getPlayerData() {

		return playerData;
	}

	public static StickySign getStickySign(Sign s) {

		for (StickySign ss : stickySigns) {

			if (ss.getLocation().getWorld().getName().equals(s.getWorld().getName())
					&& ss.getLocation().getX() == s.getLocation().getX()
					&& ss.getLocation().getY() == s.getLocation().getY()
					&& ss.getLocation().getX() == s.getLocation().getX())

				return ss;

		}

		return null;

	}

	public Location getLocation() {

		return loc;
	}

	public ItemStack getItem() {

		return item;
	}

	public String getName() {

		return name;
	}

}
