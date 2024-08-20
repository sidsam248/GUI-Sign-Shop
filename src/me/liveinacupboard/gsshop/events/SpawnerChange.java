package me.liveinacupboard.gsshop.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class SpawnerChange implements Listener {

	@EventHandler
	public void onSpawnerChange(BlockPlaceEvent e) {

		Player player = e.getPlayer();
		Block block = e.getBlock();

		if (block.getType() != Material.SPAWNER)
			return;

		if (!player.hasPermission("guishop.spawner")) {

			e.setCancelled(true);
			return;

		}

		CreatureSpawner spawner = (CreatureSpawner) block.getState();

		if ((!player.getInventory().getItemInMainHand().hasItemMeta())
				|| (!player.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()))
			return;

		String name = ChatColor.stripColor(
				player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().split(" Spawner")[0]
						.replace(" ", "_").toUpperCase());

		EntityType entity = EntityType.fromName(name);

		spawner.setSpawnedType(entity);
		spawner.update(true);

	}

}
