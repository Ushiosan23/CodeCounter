package core.langs;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ListLanguages class
 */
public final class ListLanguages {
	/**
	 * Save all list languages
	 */
	private static HashMap<String, String> lisLanguages = new HashMap<>();
	
	/**
	 * insert default languages
	 */
	private static void insertDefault() {
		// Javascript lang
		add(factoryExtensions("js", "jsx", "coffee"), "Javascript");
		// Typescript lang
		add(factoryExtensions("ts"), "Typescript");
		// GoLang
		add(factoryExtensions("go"), "GoLang");
		// Rust lang
		add(factoryExtensions("rs"), "Rust");
		// C lang
		add(factoryExtensions("c"), "C");
		// C++ lang
		add(factoryExtensions("cpp", "cxx"), "C++");
		// Objective C lang
		add(factoryExtensions("m"), "Objective C");
		// Objective C++ lang
		add(factoryExtensions("mm"), "Objective C++");
		// D Lang
		add(factoryExtensions("d"), "D Lang");
		// Header files
		add(factoryExtensions("h", "hh", "hxx"), "Header File");
		// Php lang
		add(factoryExtensions("php", "php4", "php3", "php5", "php7", "phps", "phtml"), "PHP");
		// Hack lang
		add(factoryExtensions("hh", "hck", "hack"), "Hack Lang");
		// Java lang
		add(factoryExtensions("java", "jsp", "jspx", "wss", "do", "action"), "Java");
		// Kotlin lang
		add(factoryExtensions("kt", "kts", "ktm"), "Kotlin");
		// Groovy lang
		add(factoryExtensions("groovy"), "Groovy");
		// Scala lang
		add(factoryExtensions("scala", "sc"), "Scala Lang");
		// C# .NET
		add(factoryExtensions("cs", "cshtml"), "C#");
		// ASP Classic
		add(factoryExtensions("asp"), "ASP Classic");
		// ASP .NET
		add(factoryExtensions("aspx", "axd", "asx", "asmx", "ashx"), "ASP .NET");
		// Css Style
		add(factoryExtensions("css", "sass", "scss"), "CSS Styles");
		// Erlang
		add(factoryExtensions("yaws"), "Erlang");
		// Flash
		add(factoryExtensions("swf"), "Flash");
		// HTML
		add(factoryExtensions("html", "htm", "xhtml", "jhtml"), "HTML Markup");
		// Perl lang
		add(factoryExtensions("pl"), "Perl");
		// Python lang
		add(factoryExtensions("py"), "Python");
		// Ruby lang
		add(factoryExtensions("rb", "rhtml"), "Ruby Lang");
		// GDScript lang
		add(factoryExtensions("gd"), "Godot Script");
		// Lua lang
		add(factoryExtensions("lua"), "Lua");
		// Assembler lang
		add(factoryExtensions("s", "asm"), "Assembler");
		// XML Markup
		add(factoryExtensions("xml", "rss", "svg", "xaml", "fxml", "iml"), "XML Markup");
		// Resource files
		add(factoryExtensions("rc", "res", "resources", "rc2"), "Resource");
		// Json file
		add(factoryExtensions("json"), "Json");
		// Config files
		add(factoryExtensions("config", "htaccess", "ini", "conf", "toml", "mf"), "Config File");
		// Text plain
		add(factoryExtensions("txt"), "Text File");
		// 3D Mesh
		add(factoryExtensions("dae", "fbx", "blender"), "3D model");
		// Media files
		add(
			factoryExtensions(
				"mp3", "mp4", "ogg", "svg", "mkv", "avi", "png", "jpg", "bmp", "asset", "jar",
				"import", "piskel", "vox", "wav", "wma", "m4a", "oga", "iso", "img", "rar", "zip",
				""
			), "Media File");
		// Unity Engine Project
		add(factoryExtensions("unity"), "Unity Project");
		// Godot project
		add(factoryExtensions("godot"), "Godot Project");
		// Godot scenes
		add(factoryExtensions("tres", "tscn"), "Godot Scene");
	}
	
	/**
	 * Get type file if exists
	 * @param extension
	 * @return
	 */
	@Contract("null -> null")
	public static String search(String extension) {
		if (extension == null)
			return null;
		
		if ( lisLanguages.size() == 0 )
			insertDefault();
		
		final String[] typeFile = {null};
		
		lisLanguages.forEach((val1, val2) -> {
			if ( val1.equals(extension.trim().toLowerCase()) ) {
				typeFile[0] = val2;
			}
		});
		
		return typeFile[0];
	}
	
	/**
	 * Create new ArrayList to extensions
	 *
	 * @param extensions
	 * @return
	 */
	@NotNull
	@Contract("_ -> new")
	private static ArrayList<String> factoryExtensions(String... extensions) {
		return new ArrayList<>(Arrays.asList(extensions));
	}
	
	/**
	 * Add list of array
	 *
	 * @param extensions
	 * @param name
	 * @return
	 */
	public static boolean add(@NotNull ArrayList<String> extensions, String name) {
		boolean result = true;
		
		for (String ext : extensions) {
			result = lisLanguages.put(ext, name) != null;
		}
		
		return result;
	}
	
	/**
	 * Get all list languages
	 *
	 * @return
	 */
	public static HashMap<String, String> getLisLanguages() {
		if (lisLanguages.size() == 0)
			insertDefault();
		return lisLanguages.entrySet()
			.stream()
			.sorted(Map.Entry.comparingByKey())
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldVal, newVal) -> oldVal, LinkedHashMap::new));
	}
}
