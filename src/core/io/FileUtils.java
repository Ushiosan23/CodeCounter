package core.io;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * FileUtils class
 */
public final class FileUtils {
	
	/**
	 * Get file extension
	 * @param file
	 * @return
	 */
	public static String getFileExtension(@NotNull File file) {
		String name = file.getName();
		return getFileExtension(name);
	}
	
	/**
	 * Get file extension
	 * @param location
	 * @return
	 */
	@Nullable
	public static String getFileExtension(@NotNull String location) {
		int lastIndex = location.lastIndexOf(".");
		return lastIndex != -1 ? location.trim().substring(lastIndex + 1).toLowerCase() : null;
	}
	
	/**
	 * Get file lines
	 * @param file
	 * @return
	 */
	@Nullable
	public static HashMap<String, Double> countFileLines(@NotNull File file) {
		if ( !file.exists() )
			return null;
		
		HashMap<String, Double> map    = new HashMap<>();
		String location                 = file.getPath();
		String line;
		BufferedReader reader;
		
		map.put("Code", (double) 0);
		map.put("Blank", (double) 0);
		
		try {
			reader  = new BufferedReader(new FileReader(location));
			while ((line = reader.readLine()) != null) {
				if ( line.trim().length() != 0 )
					map.replace("Code", map.get("Code") + 1);
				else
					map.replace("Blank", map.get("Blank") + 1);
			}
			reader.close();
		} catch (IOException err) {
			System.out.println(
				Arrays.toString(err.getStackTrace())
			);
		}
		
		return map;
	}
	
	/**
	 * Get count lines
	 * @param location
	 * @return
	 */
	@Nullable
	public static HashMap<String, Double> countFileLines(@NotNull String location) {
		File file = new File(location);
		return countFileLines(file);
	}
	
	/**
	 * Get parsed location
	 * @param location
	 * @return
	 */
	public static String parseLocation(@NotNull String location) {
		String slash            = System.getProperty("file.separator");
		String regex            = String.format("(/|%s)", "\\\\");
		String[] strings        = location.split(regex);
		String currentLocation  = System.getProperty("user.dir");
		String finalLocation;
		
		switch (strings[0]) {
			case ".":
				strings         = Arrays.copyOfRange(strings, 1, strings.length);
				finalLocation   = String.format("%s%s%s", currentLocation, slash, String.join(slash, strings));
				break;
			case "..":
				strings         = Arrays.copyOfRange(strings, 1, strings.length);
				File file       = new File(currentLocation);
				finalLocation   = String.format("%s%s%s", file.getParent(), slash, String.join(slash, strings));
				break;
			default:
				finalLocation   = location.replaceAll(regex, String.format("\\%s", slash));
				break;
		}
		
		return finalLocation;
	}
	
}
