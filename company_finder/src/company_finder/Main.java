package company_finder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

	private static Search search;
	private static final String[] INVALID = {"inc", "llc", "group", "partnership", "company",
		"incorporated","llp","holding","holdings", "corp","ltd"};
	
	public static void main(String[] args) throws IOException {
		Main.search = new SearchWithSeliniumBing();
		processList();
		
	}

	public static void processList() throws IOException {
		File file = new File("e:\\tmp\\companies\\input.csv");
		File out = new File("e:\\tmp\\companies\\output.csv");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		LinkedList<String> lines = new LinkedList<String>();
		while ((line = reader.readLine()) != null) {
			lines.add(line);
			System.out.println(line);
		}
		reader.close();
		BufferedWriter writer = new BufferedWriter(new FileWriter(out));
		for (String s : lines) {
			writer.write(s);
			s = process(s);
			writer.write(",");
			writer.write(s);
			writer.write(",");
			String res = search.search(s);
			System.out.println("Search result " + res);
			writer.write(res);
			for(String pattern : tryPatterns(s)) {
				writer.write(",");
				writer.write(pattern);
				writer.write(",");
				writer.write(search.search(s));
			}
			writer.write("\n");
			writer.flush();
		}
		writer.close();
	}

	private static List<String> tryPatterns(String s) {
		ArrayList<String> ret = new ArrayList<String>();
		return ret;
	}

	private static String process(String line) {
		line = line.toLowerCase();
		line = line.replace("llc", " llc");
		line = line.replace("\"", "");
		line = line.replace("&", " & ");
		line = line.replace(".", " ");
		line = line.replace(",", " ");
		
		line = line.replace("   ", " ");
		line = line.replace("  ", " ");
		line = line.replace("  ", " ");
		line = line.replace("l l c", " llc");
		line = line.replace(" l c", " llc");
		line = line.trim();
		for (String invalid : INVALID) {
			if (line.endsWith(invalid)) {
				line = line.substring(0,line.length() - invalid.length()).trim();
			}
		}
		return line;
	}
}
