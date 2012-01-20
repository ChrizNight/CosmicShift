package me.chriznight.cosmicshift;

import java.io.File;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author ChrizNight
 * 
 */
public class CosmicShift extends JavaPlugin {
	protected FileConfiguration config;
	protected FileConfiguration save;
	protected static String N1;
	protected static String N;
	private CosmicShiftCommandExecutor CE;
	private Listener PL;
	private Logger logger;

	public CosmicShift() {
		this.logger = Logger.getLogger("CosmicShift");
		N1 = "[CS] ";
		N = ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "CS" + ChatColor.GRAY
				+ "] ";
	}

	@Override
	public void onDisable() {
		logStatus("disabled!");
	}

	@Override
	public void onEnable() {
		try {
			config = getConfig();
			File CosmicConfig = new File(getDataFolder() + "config.yml");
			if(!CosmicConfig.exists()) {
				config.options().header("Configuration of CosmicShift");
				if (!config.contains("Shift.Allow requests")) {
					config.set("Shift.Allow requests", true);
				}
				if (!config.contains("Shift.Disable the gamemode event")) {
					config.set("Shift.Disable the gamemode event", false);
				}
			}
			saveConfig();
		} catch (Exception e) {
			log(e.toString());
		}
		reloadConfig();
		log("Succesfully loaded the config!");
		PL = new CosmicShiftPlayerListener(this);
		Bukkit.getServer().getPluginManager().registerEvents(PL, this);
		CE = new CosmicShiftCommandExecutor(this);
		getCommand("cs").setExecutor(CE);
		logStatus("enabled!");
	}

	private final void logStatus(String state) {
		PluginDescriptionFile pdf = this.getDescription();
		this.logger.info(N1 + pdf.getName() + " v" + pdf.getVersion() + " "
				+ state);
	}

	protected final void log(String msg) {
		this.logger.info(N1 + msg);
	}

	protected final void send(CommandSender sender, String msg) {
		sender.sendMessage(N + msg);
	}
}
