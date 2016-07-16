package thenewherobrine.zeusmode;



import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
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
	
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		switch (cmd.getName()) {
		case "zeusmode":
			
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "This command only works in-game!");
				return false;
			}
			
			if (args.length < 1) {
				return false;
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

		default:
			return false;
		}
		return true;
	}
}
