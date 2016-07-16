package thenewherobrine.zeusmode;

import org.bukkit.entity.Player;

public class ZeusModeTask implements Runnable {
	private Main plugin;
	private Player player;
	
	public ZeusModeTask(Main plugin, Player player){
		this.plugin = plugin;
		this.player = player;
	}
	
	public void run() {
		if (plugin.getServer().getPlayer(player.getName()) == null) {
			plugin.getServer().getScheduler().cancelTask(plugin.tasks.get(player.getName()));
			plugin.tasks.remove(player.getName());
		} else {
			player.getWorld().strikeLightning(player.getLocation());
		}
	}
}
