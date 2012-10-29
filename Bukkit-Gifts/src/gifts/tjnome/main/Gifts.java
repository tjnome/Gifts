package gifts.tjnome.main;

import gifts.tjnome.main.conf.GiftsConf;

import java.util.Collections;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

//import ru.tehkode.permissions.PermissionManager;
//import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
*
* Gifts
* Copyright (C) 2011 tjnome
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
* 
*  @author tjnome
*/

public class Gifts extends JavaPlugin {
	public PluginManager pm;
	protected final GiftsConf configuration;
	
	public Gifts() {
		configuration = new GiftsConf(this);
	}

	public void onEnable() {
		registerEvents();
		PluginDescriptionFile pdfFile = this.getDescription();
		try {
			this.configuration.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}

	public void onDisable() {
		System.out.println("Gifts disablet!");
		try {
			configuration.cleanup();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void registerEvents() {
		this.pm = getServer().getPluginManager();
	}
	
	public GiftsConf getGlobalConfiguration() {
		return configuration;
	}
	
	public String colorTxt(String string) {
        string = string.replaceAll("&0", ChatColor.BLACK + "");
        string = string.replaceAll("&1", ChatColor.DARK_BLUE + "");
        string = string.replaceAll("&2", ChatColor.DARK_GREEN + "");
        string = string.replaceAll("&3", ChatColor.DARK_AQUA + "");
        string = string.replaceAll("&4", ChatColor.DARK_RED + "");
        string = string.replaceAll("&5", ChatColor.DARK_PURPLE + "");
        string = string.replaceAll("&6", ChatColor.GOLD + "");
        string = string.replaceAll("&7", ChatColor.GRAY + "");
        string = string.replaceAll("&8", ChatColor.DARK_GRAY + "");
        string = string.replaceAll("&9", ChatColor.BLUE + "");
        string = string.replaceAll("&a", ChatColor.GREEN + "");
        string = string.replaceAll("&b", ChatColor.AQUA + "");
        string = string.replaceAll("&c", ChatColor.RED + "");
        string = string.replaceAll("&d", ChatColor.LIGHT_PURPLE + "");
        string = string.replaceAll("&e", ChatColor.YELLOW + "");
        string = string.replaceAll("&f", ChatColor.WHITE + "");
        return string;
    }
	
	public boolean onCommand(PlayerCommandPreprocessEvent event, CommandSender sender, Command command, String label, String[] args) {
		if (isPlayer(sender)) {
			Player player = (Player) sender;
			if (command.getName().equalsIgnoreCase("gift") || (command.getName().equalsIgnoreCase("gave"))) {
				if (checkpermissions(player, "gift")) {
					if (!(this.configuration.getGiftPlayers().contains(player.getName()))) {
						if (player.getTargetBlock(null, 2) != null) {
							Block block = player.getTargetBlock(null, 5).getRelative(BlockFace.UP);
							if (block.getType() == Material.AIR) {
								if (((block.getRelative(BlockFace.DOWN).getType() == Material.CHEST) || (block.getRelative(BlockFace.UP).getType() == Material.CHEST)
										|| (block.getRelative(BlockFace.NORTH).getType() == Material.CHEST) || (block.getRelative(BlockFace.SOUTH).getType() == Material.CHEST) 
										|| (block.getRelative(BlockFace.WEST).getType() == Material.CHEST) || (block.getRelative(BlockFace.EAST).getType() == Material.CHEST))) {
									player.sendMessage(ChatColor.RED + this.configuration.chestclose);
									return true;
								}
								block.setType(Material.CHEST);
								Chest chest = (Chest) block.getState();
								for (int i = 0; i < chest.getInventory().getSize(); i++) {
									Random r = new Random(); 
									int gift = r.nextInt(100);
									if (gift <= this.configuration.prosent) {
										int amount = r.nextInt(64);
										Collections.shuffle(this.configuration.getGiftItems());
										ItemStack item = new ItemStack(this.configuration.getGiftItems().get(0), amount);
										chest.getInventory().addItem(item);
									}
								}
								this.configuration.getGiftPlayers().add(player.getName());
								player.sendMessage(ChatColor.RED + colorTxt(this.configuration.onCommand));
								return true;
							} else {
								player.sendMessage(ChatColor.RED + colorTxt(this.configuration.noSpace));
								return true;
							}
						} else {
							player.sendMessage(ChatColor.RED + colorTxt(this.configuration.badAim));
							return true;
						}
					} else {
						player.sendMessage(ChatColor.RED + colorTxt(this.configuration.playergift));
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean isPlayer(final CommandSender sender) {
		if (sender instanceof Player) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkpermissions(Player player, String action) {
		if (this.configuration.permissions) {
			if (event.getPlayer().hasPermission("Gift." + action)) {
				return true;
				}
		} else {
			return true;
		}
	return false;
	}
}
