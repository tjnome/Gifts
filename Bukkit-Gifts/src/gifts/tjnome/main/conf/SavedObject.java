package gifts.tjnome.main.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

public class SavedObject {
	
	public static void save(Object obj,File binFile) throws Exception {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(binFile));
		oos.writeObject(obj);
		oos.flush();
		oos.close();
	}
	public static Object load(File binFile) throws Exception {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(binFile));
		Object result = ois.readObject();
		ois.close();
		return result;
	}
}
