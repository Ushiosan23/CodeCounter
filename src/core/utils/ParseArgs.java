package core.utils;

import java.util.HashMap;

/**
 * ParseArgs class
 */
public final class ParseArgs {
	private String[] args;
	private HashMap<String, String> map;
	
	/**
	 * Default constructor
	 */
	public ParseArgs() {
		this.args = new String[]{};
		parsed();
	}
	
	/**
	 * Constructor parsed args
	 * @param args
	 */
	public ParseArgs(String[] args) {
		this.args = args;
		parsed();
	}
	
	/**
	 * Return map value
	 * @param arg
	 * @return
	 */
	public String get(String arg) {
		return this.map.get(arg);
	}
	
	/**
	 * Parsed args
	 */
	private void parsed() {
		this.map = new HashMap<>();
		String[] split;
		
		for ( String arg : this.args ) {
			split = arg.split("=");
			if (split.length > 1) {
				this.map.put(split[0], split[1]);
			} else {
				this.map.put("Location", arg);
			}
		}
	}
	
	/**
	 * Override toString method
	 * @return
	 */
	@Override
	public String toString() {
		return this.map.toString();
	}
}
