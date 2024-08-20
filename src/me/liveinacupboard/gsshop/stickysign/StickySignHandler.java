package me.liveinacupboard.gsshop.stickysign;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.liveinacupboard.gsshop.GuiSignShop;

public class StickySignHandler {

	private GuiSignShop pl;

	public StickySignHandler(GuiSignShop pl) {

		this.pl = pl;

	}

	public void createNewStickyShop(ItemStack item, String name, Player player) {

		List<String> lores = new ArrayList<>();
		List<String> enchants = new ArrayList<>();

		String itemName;

		int id = item.getType().getId();
		int data = item.getDurability();

		if ((item.hasItemMeta()) && (item.getItemMeta().hasDisplayName())) {

			itemName = item.getItemMeta().getDisplayName().replace("§", "&");

		} else {

			itemName = (item.getType().name().substring(0, 1) + item.getType().name().substring(1).toLowerCase())
					.replace("_", " ");
		}

		if ((item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

			for (String s : item.getItemMeta().getLore()) {

				lores.add(s.replace("§", "&"));
			}
		}

		if ((item.hasItemMeta()) && (item.getItemMeta().hasEnchants())) {

			for (Enchantment ench : item.getItemMeta().getEnchants().keySet()) {

				enchants.add(ench.getKey() + ":" + item.getItemMeta().getEnchantLevel(ench));
			}
		}

		pl.getStickySignsFile().set(name + ".id", id);
		pl.getStickySignsFile().set(name + ".data", data);
		pl.getStickySignsFile().set(name + ".name", itemName);
		pl.getStickySignsFile().set(name + ".lore", lores);
		pl.getStickySignsFile().set(name + ".enchantments", enchants);

		pl.saveStickySignsFile();
		pl.reloadStickySignsFile();

		saveToCache(name);

	}

	public void saveToCache(String name) {

		String location = pl.getStickySignsFile().getString(name + ".location");
		String[] l = location.split(", ");

		World world = Bukkit.getWorld(l[0]);
		int x = Integer.parseInt(l[1]);
		int y = Integer.parseInt(l[2]);
		int z = Integer.parseInt(l[3]);

		Location loc = new Location(world, x, y, z);

		int id = pl.getStickySignsFile().getInt(name + ".id");
		int data = pl.getStickySignsFile().getInt(name + ".data");

		String n = ChatColor.translateAlternateColorCodes('&', pl.getStickySignsFile().getString(name + ".name"));

		List<String> lore = pl.getStickySignsFile().getStringList(name + ".lore");
		List<String> ench = pl.getStickySignsFile().getStringList(name + ".enchantments");

		List<String> newLore = new ArrayList<>();

		ItemStack item = new ItemStack(, 1, (short) data);
		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(n);

		if ((lore != null) && (!lore.isEmpty())) {

			for (String s : lore) {

				newLore.add(ChatColor.translateAlternateColorCodes('&', s));
			}

			meta.setLore(newLore);
		}

		if ((ench != null) && (!ench.isEmpty())) {

			for (String e : ench) {
				String[] e1 = e.split(":");

				String enchName = e1[0];
				int enchLvl = Integer.parseInt(e1[1]);

				meta.addEnchant(Enchantment.getByName(enchName), enchLvl, true);
			}
		}

		item.setItemMeta(meta);

		StickySign sign = new StickySign(loc, item, name);

		StickySign.addStickySign(sign);

	}

}
