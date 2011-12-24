package gifts.tjnome.main.conf;

import gifts.tjnome.main.Gifts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

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

public class GiftsConf {

	private Gifts plugin;
	private List<Integer> giftitems = new ArrayList<Integer>();
	private ArrayList<String> giftplayers = new ArrayList<String>();
	private HashMap<String, Object> configDefaults = new HashMap<String, Object>();
	private YamlConfiguration config;
	private File binFile;
	private File configFile;
	
	public GiftsConf(Gifts plugin) {
		this.plugin = plugin;
	}
	public String onCommand;
	public String noSpace;
	public String badAim;
	public String chestclose;
	public String playergift;
	public int prosent;
	public boolean permissions;
	

	@SuppressWarnings("unchecked")
	public void load() throws Exception {
		this.binFile = new File(plugin.getDataFolder(), "playergift.bin");
		if (this.binFile.exists()) {
			this.giftplayers = (ArrayList<String>)SavedObject.load(this.binFile);
		}
		this.config = new YamlConfiguration();
		this.configFile = new File(plugin.getDataFolder(), "config.yml");	
		
		this.configDefaults.put("Server.gifts.item", "1,5,12,45,48,49,86,91,82,115,263,264,265,266,332,334,336,337,341,344,348,351,353,357,359,360,369,370,371,372,375,376,377,378,382");
		this.configDefaults.put("Server.gifts.onCommand", "Merry Christmas!");
		this.configDefaults.put("Server.gifts.chest-close", "There is a chest to close");
		this.configDefaults.put("Server.gifts.no-space", "There is no available space for chest to be placed");
		this.configDefaults.put("Server.gifts.bad-aim", "You have to aim at a block no more than 5 blocks away.");
		this.configDefaults.put("Server.gifts.already-got-gift", "You have already got a gift for christmas");
		this.configDefaults.put("Server.gifts.prosent", 20);
		this.configDefaults.put("Server.permissions", true);
		
		if (!this.configFile.exists()) {
			for (String key : this.configDefaults.keySet()) {
				this.config.set(key, this.configDefaults.get(key));
			}
			try {
				config.save(this.configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				config.load(this.configFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String[] tempitem = config.getString("Server.gifts.item").split(",");
		for (String item : tempitem) {
			giftitems.add(Integer.parseInt(item));
        }
		this.onCommand = config.getString("Server.gifts.onCommand");
		this.noSpace = config.getString("Server.gifts.no-space");
		this.badAim = config.getString("Server.gifts.bad-aim");
		this.chestclose = config.getString("Server.gifts.chest-close");
		this.playergift = config.getString("Server.gifts.already-got-gift");
		this.prosent = config.getInt("Server.gifts.prosent");
		this.permissions = config.getBoolean("Server.permissions");
	}
	
	public void cleanup() throws Exception {
		if (!(giftplayers.isEmpty())) {
			SavedObject.save(giftplayers, this.binFile);
		}
		configDefaults.clear();
		giftitems.clear();
		giftplayers.clear();
	}
	
	public List<Integer> getGiftItems() {
		return this.giftitems;
	}
	
	public ArrayList<String> getGiftPlayers() {
		return this.giftplayers;
	}
}