package core.io;

import org.jetbrains.annotations.Contract;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Inspector class
 */
public final class Inspector {
	private String location;
	private File currentFile;
	private ArrayList<String> allList;
	
	/**
	 * Constructor
	 * @param location
	 */
	public Inspector(String location) {
		this.location       = location;
		this.currentFile    = new File(this.location);
		this.allList        = new ArrayList<>();
	}
	
	/**
	 * Iterate all files
	 * @throws IOException
	 */
	public void listAllFiles() throws IOException {
		if ( !this.currentFile.exists() )
			throw new IOException("File \"" + this.currentFile.getPath() + "\" isn't exists.");
		
		if ( this.currentFile.isDirectory() ) {
			File[] files = Objects.requireNonNull(this.currentFile.listFiles());
			
			for ( File file : files ) {
				this.currentFile = file;
				this.listAllFiles();
			}
		} else {
			this.allList.add(this.currentFile.getPath());
		}
		
	}
	
	/**
	 * Get all list
	 * @return
	 */
	@Contract(pure = true)
	public ArrayList<String> getAllList() {
		return this.allList;
	}
	
	/**
	 * To string location
	 * @return
	 */
	@Contract(pure = true)
	public String toString() {
		return this.location;
	}
}
