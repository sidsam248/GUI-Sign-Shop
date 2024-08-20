package me.liveinacupboard.gsshop;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.liveinacupboard.gsshop.cmd.Cmd;
import me.liveinacupboard.gsshop.events.BuyEvent;
import me.liveinacupboard.gsshop.events.InteractEvent;
import me.liveinacupboard.gsshop.events.InventoryClick;
import me.liveinacupboard.gsshop.events.InventoryClose;
import me.liveinacupboard.gsshop.events.SellEvent;
import me.liveinacupboard.gsshop.events.SignChange;
import me.liveinacupboard.gsshop.events.SpawnerChange;
import me.liveinacupboard.gsshop.events.StickySignBreakEvent;
import me.liveinacupboard.gsshop.stickysign.StickySignHandler;
import me.liveinacupboard.gsshop.util.ConfigUtil;
import net.milkbowl.vault.economy.Economy;

public class GuiSignShop extends JavaPlugin {

	private static Economy economy = null;

	private final String prefix = "[GuiSignShop] ";

	private CommandSender console = Bukkit.getConsoleSender();

	private File stickySignsFile;
	private FileConfiguration stickySigns;

	private ConfigUtil cUtil;
	private StickySignHandler ssHandler;

	@Override
	public void onEnable() {

		console.sendMessage(prefix + "Loading the plugin...");

		saveDefaultConfig();

		if (!setupEconomy()) {

			console.sendMessage(prefix + "Vault not detected, disabling the plugin..");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		console.sendMessage(prefix + "Loading StickySigns.yml");

		stickySignsFile = new File(getDataFolder(), "StickySigns.yml");
		stickySigns = YamlConfiguration.loadConfiguration(stickySignsFile);

		saveStickySignsFile();

		console.sendMessage(prefix + "StickySigns.yml has been loaded");

		cUtil = new ConfigUtil(this);
		ssHandler = new StickySignHandler(this);

		registerCmd();
		registerEvents(new SignChange(this), new BuyEvent(this), new InteractEvent(this), new InventoryClick(this),
				new SellEvent(this), new InventoryClose(), new SpawnerChange(), new StickySignBreakEvent(this));
		
		console.sendMessage(prefix + "Loading all the Sticky Signs from the file");
		
		for(String name : getStickySignsFile().getKeys(false)) {
			
			ssHandler.saveToCache(name);
			
		}

		console.sendMessage(prefix + "Plugin loaded perfectly");

	}

	@Override
	public void onDisable() {

		saveConfig();
		saveStickySignsFile();

	}

	private void registerCmd() {

		getCommand("guishop").setExecutor(new Cmd(this));

	}

	private void registerEvents(Listener... listeners) {

		for (Listener l : listeners) {

			getServer().getPluginManager().registerEvents(l, this);

		}

	}

	public String getPluginPrefix() {

		return prefix;
	}

	public ConfigUtil getConfigUtil() {

		return cUtil;
	}

	public FileConfiguration getStickySignsFile() {

		return stickySigns;
	}

	public void reloadStickySignsFile() {

		stickySigns = YamlConfiguration.loadConfiguration(stickySignsFile);

	}

	public void saveStickySignsFile() {

		try {

			stickySigns.save(stickySignsFile);

		} catch (IOException e) {

			console.sendMessage(prefix + "Unable to save the file, please take a backup immediately!");

		}

	}

	public Economy getEconomy() {

		return economy;
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}

}
