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
	private boolean cancel;
	public static Player target;

	public CosmicShiftPlayerListener(CosmicShift instance) {
		plugin = instance;
		cancel = plugin.config.getBoolean("Shift.Disable the gamemode event");
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerGameModeChange(final PlayerGameModeChangeEvent event) {
		Player player = event.getPlayer();
		if (!player.hasPermission("cosmicshift.shift")
				|| !player.hasPermission("cosmicshift.shift.other")
				&& !player.isOp()) {
			event.setCancelled(cancel);
			if(event.isCancelled()) {
				plugin.send(player,
						"You don't have permission to run this command!");
				plugin.log(event.getPlayer().getName() + " tried to change his gamemode!");
			}
		}
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
