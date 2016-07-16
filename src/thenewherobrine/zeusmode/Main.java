package thenewherobrine.zeusmode;



import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{

    /*
        ZeusMode by TheNewHEROBRINE (kik: TheNewHEROBRINEX)

       	88888888888 888               888b    888                        
    		888     888               8888b   888                        
    		888     888               88888b  888                        
    		888     88888b.   .d88b.  888Y88b 888  .d88b.  888  888  888 
    		888     888 "88b d8P  Y8b 888 Y88b888 d8P  Y8b 888  888  888 
    		888     888  888 88888888 888  Y88888 88888888 888  888  888 
    		888     888  888 Y8b.     888   Y8888 Y8b.     Y88b 888 d88P 
    		888     888  888  "Y8888  888    Y888  "Y8888   "Y8888888P"  
    	-------------------------------------------------------------------------------------------------
		888    888 8888888888 8888888b.   .d88888b.  888888b.   8888888b.  8888888 888b    888 8888888888 
		888    888 888        888   Y88b d88P" "Y88b 888  "88b  888   Y88b   888   8888b   888 888        
		888    888 888        888    888 888     888 888  .88P  888    888   888   88888b  888 888        
		8888888888 8888888    888   d88P 888     888 8888888K.  888   d88P   888   888Y88b 888 8888888    
		888    888 888        8888888P"  888     888 888  "Y88b 8888888P"    888   888 Y88b888 888        
		888    888 888        888 T88b   888     888 888    888 888 T88b     888   888  Y88888 888        
		888    888 888        888  T88b  Y88b. .d88P 888   d88P 888  T88b    888   888   Y8888 888        
		888    888 8888888888 888   T88b  "Y88888P"  8888888P"  888   T88b 8888888 888    Y888 8888888888 
                                                                                                                                                                                            
    */

	Map<String, Integer> tasks = new HashMap<String, Integer>();
	private String consoleUsage = ChatColor.RED + "/lightning <player> or /lightning <x> <y> <z> <world>";
	private String playerUsage = ChatColor.RED + "/lightning [player]" + System.lineSeparator() + "or /lightning <x> <y> <z> [world]";
	
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		switch (cmd.getName()) {
		case "zeusmode":
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "This command only works in-game!");
				return true;
			}
			
			if (args.length < 1) {
				sender.sendMessage(ChatColor.RED + "/lightning <on|off> [ticks]");
				return true;
			}
			
			if (args[0].equalsIgnoreCase("on")) {
		
				if (!(tasks.containsKey(sender.getName()))) {
					tasks.put(sender.getName(), this.getServer().getScheduler().runTaskTimer(this,new ZeusModeTask(this, (Player) sender), 0, (args.length >= 2) ? Long.parseLong(args[1]) : 20).getTaskId());
				}
				
				else
					sender.sendMessage(ChatColor.RED + "ZeusMode is already on!");
			}	
			
			else if (args[0].equalsIgnoreCase("off")) {
				
				if(tasks.get(sender.getName()) != null){
					this.getServer().getScheduler().cancelTask(tasks.get(sender.getName()));
					tasks.remove(sender.getName());
				}
				
				else
					sender.sendMessage(ChatColor.RED + "ZeusMode is not on!");
			}
			break;
			
		case "lightning":
			switch (args.length) {
				case 0:
					if (!(sender instanceof Player)) {
						sender.sendMessage(consoleUsage);
					}
					
					else
						((Player)sender).getWorld().strikeLightning(((Player)sender).getLocation());
					break;
					
				case 1:
					Player target = this.getServer().getPlayer(args[0]);
					
					if(target == null){
						sender.sendMessage(ChatColor.RED + "Player " + args[0] + " is not online");
						return true;
					}
					
					((Player)sender).getWorld().strikeLightning(target.getLocation());
					break;
		
				case 3:
					if (!(sender instanceof Player)) {
						sender.sendMessage(consoleUsage);
						return true;
					}
					
					int x = Integer.parseInt(args[0]);
					int y = Integer.parseInt(args[1]);
					int z = Integer.parseInt(args[2]);
					
					((Player)sender).getWorld().strikeLightning(new Location(null, x, y, z));
					break;
					
				case 4:
					 x = Integer.parseInt(args[0]);
					 y = Integer.parseInt(args[1]);
					 z = Integer.parseInt(args[2]);
					World world = this.getServer().getWorld(args[3]);
					
					if(world == null){
						sender.sendMessage(ChatColor.RED + "The world " + args[3] + " does not exist");
					} else {
						world.strikeLightning(new Location(null, x, y, z));
					}
					break;
					
				default:
					sender.sendMessage(sender instanceof Player ? playerUsage : consoleUsage);
			}
		}
		return true;
	}
}
