package me.chriznight.cosmicshift;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author ChrizNight
 * 
 */
public class CosmicShiftPlayerListener implements Listener {
	private CosmicShift plugin;
	private GameMode gm;
	public static Player target;

	public CosmicShiftPlayerListener(CosmicShift instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerGameModeChange(final PlayerGameModeChangeEvent event) {
		Player player = event.getPlayer();
		gm = event.getNewGameMode();
		for (Player p : event.getPlayer().getServer().getOnlinePlayers()) {
			if ((p.hasPermission(Permissions.CM)) && player != p
					&& player.isOp() == false) {
				plugin.send(p, "The gamemode of " + ChatColor.RED
						+ player.getName().toString() + ChatColor.GRAY
						+ " changed to " + ChatColor.DARK_AQUA + gm.toString());
			}
		}
		target = event.getPlayer();
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event) {
		target = null;
	}

}
