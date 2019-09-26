package program;

import core.io.FileUtils;
import core.io.Inspector;
import core.langs.ListLanguages;
import core.utils.ParseArgs;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Program class
 */
public class Program implements Runnable {
	private static double totalLines = 0; // total lines
	private static double totalFiles = 0; // total files
	private static HashMap<String, Double> totalFilesByLang = new HashMap<>(); // total lines by lang
	private static HashMap<String, HashMap<String, Double>> totalLinesByLang = new HashMap<>();
	private Inspector inspector;
	
	/**
	 * Main constructor
	 *
	 * @param inspector
	 */
	private Program(Inspector inspector) {
		this.inspector = inspector;
	}
	
	/**
	 * Main entry app
	 *
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ParseArgs parseArgs = new ParseArgs(args);
		String location = parseArgs.get("Location");
		
		// exit program if file not exists
		if (location == null) {
			help();
			System.exit(0);
		}
		
		// parse location path
		location = FileUtils.parseLocation(location);
		Inspector inspector = new Inspector(location);
		Thread thread = new Thread(new Program(inspector));
		
		System.out.println("Running process....\n");
		thread.start();
	}
	
	/**
	 * Show help options
	 */
	@Contract(pure = true)
	private static void help() throws IOException {
		InputStream stream = Program.class.getResourceAsStream("help.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String line;
		
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		
		reader.close();
	}
	
	/**
	 * Run thread
	 */
	@Override
	public void run() {
		AtomicReference<Double> onlyTotalBlankLines  = new AtomicReference<>((double) 0);
		AtomicReference<Double> onlyTotalCodeLines   = new AtomicReference<>((double) 0);
		AtomicReference<Double> onlyTotalFiles       = new AtomicReference<>((double) 0);
		
		try {
			inspector.listAllFiles();
			totalFiles = inspector.getAllList().size();
			
			totalFilesByLang(inspector.getAllList());
			totalLinesByLang(inspector.getAllList());
			
			System.out.println(String.format("Total Files: %.0f", totalFiles));
			System.out.println(String.format("Total Lines: %.0f", totalLines));
			System.out.println();
			// table header
			String table = String.format("%12s\t\t\t\t%7s\t\t\t\t%5s\t\t\t\t%s",
				"File Type",
				"Blank Lines",
				"Code Lines",
				"Total Files");
			System.out.println(table);
			// separator
			System.out.println("__________________________________________________________________________" +
				"__________________________________________________________________________");
			// Add other files in totalLines
			if ( totalFilesByLang.get("Other") != null ) {
				HashMap<String, Double> other = new HashMap<>();
				other.put("Code", (double) 0);
				other.put("Blank", (double) 0);
				
				totalLinesByLang.put("Other", other);
			}
			// Type file
			totalLinesByLang.forEach((key, val) -> {
				double allFiles = totalFilesByLang.get(key);
				String blank = String.format("%.0f", val.get("Blank"));
				String code = String.format("%.0f", val.get("Code"));
				String allF = String.format("%.0f", allFiles);
				
				// Format string
				String format = String.format("%12s\t\t\t\t%10s\t\t\t\t%10s\t\t\t\t%s", key,
					blank,
					code,
					allF);
				
				// Print format output
				System.out.println(format);
				// Totals
				onlyTotalFiles.updateAndGet(v -> v + allFiles);
				onlyTotalBlankLines.updateAndGet(v -> v + val.get("Blank"));
				onlyTotalCodeLines.updateAndGet(v -> v + val.get("Code"));
			});
			
			// Results
			System.out.println("__________________________________________________________________________" +
				"__________________________________________________________________________");
			// Show totals
			String totalFormat = String.format("%12s\t\t\t\t%10s\t\t\t\t%10s\t\t\t\t%s",
				"",
				String.format("%.0f", onlyTotalBlankLines.get()),
				String.format("%.0f", onlyTotalCodeLines.get()),
				String.format("%.0f", onlyTotalFiles.get())
			);
			
			// Total string output
			System.out.println(totalFormat);
			// End of line
			System.out.println("\n");
			// Finish program
		} catch (IOException error) {
			System.out.println(error.getMessage());
		}
	}
	
	/**
	 * Organize all file languages
	 *
	 * @param list
	 */
	private void totalFilesByLang(@NotNull ArrayList<String> list) {
		for (String item : list) {
			String extension = FileUtils.getFileExtension(item);
			String fileType = ListLanguages.search(extension);
			
			if (totalFilesByLang.get(fileType) == null && fileType != null) {
				totalFilesByLang.put(fileType, (double) 1);
			} else {
				if (fileType != null)
					totalFilesByLang.replace(fileType, totalFilesByLang.get(fileType) + 1);
			}
		}
		
		AtomicReference<Double> other = new AtomicReference<>((double) 0);
		
		totalFilesByLang.values().forEach(val -> other.updateAndGet(v -> v + val));
		totalFilesByLang.put("Other", totalFiles - other.get());
	}
	
	/**
	 * Get all file code list by language
	 *
	 * @param list
	 */
	private void totalLinesByLang(@NotNull ArrayList<String> list) {
		for (String file : list) {
			String extension = FileUtils.getFileExtension(file);
			String typeFile = ListLanguages.search(extension);
			HashMap<String, Double> lines;
			
			if (totalLinesByLang.get(typeFile) == null) {
				if (typeFile != null && !typeFile.equals("Media File")) {
					lines = FileUtils.countFileLines(file);
					
					totalLines += Objects.requireNonNull(lines).get("Code");
					totalLines += Objects.requireNonNull(lines).get("Blank");
					
					totalLinesByLang.put(typeFile, lines);
				}
			} else {
				if (typeFile != null && !typeFile.equals("Media File")) {
					lines = FileUtils.countFileLines(file);
					
					totalLines += Objects.requireNonNull(lines).get("Code");
					totalLines += Objects.requireNonNull(lines).get("Blank");
					
					Objects.requireNonNull(lines).replace("Code", totalLinesByLang.get(typeFile).get("Code") + lines.get("Code"));
					Objects.requireNonNull(lines).replace("Blank", totalLinesByLang.get(typeFile).get("Blank") + lines.get("Blank"));
					
					totalLinesByLang.replace(typeFile, lines);
				}
			}
		}
	}
	
}
