package kirtika;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

public class Genres {
	private final String genresFilePath = "src/data/genret.dat";
	private ArrayList<Genre> items = new ArrayList<>();
	
	/**
	 * Initializes objects of the class from a file.
	 */
	public Genres() {
	    try {
	        initGenres();
	    } catch (FileNotFoundException e) {
	        System.err.println("File not found: " + genresFilePath);
	        try {
	            File newFile = new File(genresFilePath);
	            newFile.createNewFile();
	            System.out.println("Created new file: " + genresFilePath);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	

	
	/**
	 * Saves genres
	 * @throws FileNotFoundException 
	 */
	public void saveGenres() throws FileNotFoundException{
		File ftied = new File(genresFilePath);
		try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
			for (int i = 0; i < items.size(); i++) {
				Genre genre = items.get(i);
				fo.println(Genre.formatGenre(genre.toString()));
			}
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Tiedosto: " + ftied.getAbsolutePath() + " ei aukea");
		}
	}
	
	/**
	 * Adds a new genre to items.
	 * @param line where genre formatted as "84.2 Suomenkielinen kaunokirjallisuus"
	 * 
	 * @example
	 * <pre name="test">
	 * Genres g = new Genres("84.2|Suomalainen kertomakirjallisuus");
	 * g.addGenre("76.7 Paperitaide");
	 * g.getGenreCount() === 2;
	 * Genre genre = g.getGenre(1);
	 * genre.getGenreDesc() === "Paperitaide";
	 * </pre>
	 */
	public void addGenre(String line) {
		items.add(new Genre(Genre.formatGenre(line)));
	}
	
	/**
	 * Deletes a genre from items
	 * @param genre To be deleted
	 * 
	 * @example
	 * <pre name="test">
	 * Genres g = new Genres("84.2|Suomalainen kertomakirjallisuus");
	 * g.addGenre("57.1 Putkilokasvit");
	 * g.addGenre("61.3 Ohjelmointi. Ohjelmointikielet");
	 * g.getGenreCount() === 3;
	 * Genre a = g.getGenre(2);
	 * g.deleteGenre(a);
	 * a = g.getGenre(1);
	 * g.deleteGenre(a);
	 * a = g.getGenre(0);
	 * g.deleteGenre(a);
	 * g.getGenreCount() === 0;
	 * g.deleteGenre(a); // nothing happens
	 * </pre>
	 */
	public void deleteGenre(Genre genre) {
		String genreId = genre.getGenreId();
		Iterator<Genre> iterator = items.iterator();
	    while (iterator.hasNext()) {
	        Genre g = iterator.next();
	        if (g.getGenreId().equals(genreId)) {
	            iterator.remove();
	            break;
	        }
	    }
	}
	
	/**
	 * Initialize genres from a file.
	 * @throws FileNotFoundException if genret.dat file is not found.
	 */
	private void initGenres() throws FileNotFoundException {
		File f = new File(genresFilePath);
		Scanner scan = new Scanner(f);
		
		while (scan.hasNextLine()) {
			   String line = scan.nextLine();
			   // Initialize a genre with a parser
			   items.add(new Genre(line));
			}
		scan.close();
	}
	
	/**
	 * Sorts the genres file in ascending order based on the numerical value in each line.
	 * Reads the genres file line by line into an ArrayList, then sorts the ArrayList.
	 * Sorted lines are then overwritten back to the genres file
	 * @throws IOException if there is an error reading or writing the genres file.
	 * 
	 */
	public void sortGenres() throws IOException {
		ArrayList<String> lines = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(genresFilePath));
	       String line;
	       while ((line = reader.readLine()) != null) {
	           lines.add(line);
	       }
	       reader.close();
	       
	    // Sort the List based on the numerical value
	        Collections.sort(lines, new Comparator<String>() {
	            @Override
	            public int compare(String s1, String s2) {
	                double num1 = Double.parseDouble(s1.split("\\|")[0]);
	                double num2 = Double.parseDouble(s2.split("\\|")[0]);
	                return Double.compare(num1, num2);
	            }
	        });
	        
	        // Write the sorted List to the output file
	        BufferedWriter writer = new BufferedWriter(new FileWriter(genresFilePath));
	        for (String sortedLine : lines) {
	            writer.write(sortedLine);
	            writer.newLine();
	        }
	        writer.close();
	}
	
	/**
	 * Checks if items already contains this ykl id
	 * @param yklId
	 * @return
	 */
	public boolean genresContain(String yklId) {
		for (Genre genre : items) {
			if (genre.matchesId(yklId)) return true;
		}
		return false;
	}

	/**
	 * Get the description of a genre.
	 * @param genre The genre whose description is to be returned.
	 * @return The description of the genre.
	 */
	public String getGenreDesc(Genre genre) {
		return genre.getGenreDesc();
	}
	
	/**
	 * Searches for the finnish description corresponding to the YKL classification.
	 * @param s YKL classification, for example "84.2"
	 * @return YKL description, for example "Suomalainen kaunokirjallisuus"
	 * 
	 * TODO: return exception if genredesc not found
	 * 
	 * @example
	 * <pre name="test">
	 * Genres genret = new Genres("84.2|Suomalainen kertomakirjallisuus");
	 * genret.getYklDesc("84.2") === "Suomalainen kertomakirjallisuus";
	 * genret.getYklDesc("67") === null;
	 * </pre>
	 */
	public String getYklDesc(String s) {
		for (Genre genre : items) {
			if (genre.matchesId(s)) return genre.getGenreDesc();
		}
		return null;
	}
	
	/**
	 * @return All Genres in items.
	 */
	public ArrayList<Genre> getGenres(){
		return items;
	}
	
	// === TEST METHODS
	
	/**
	 * @return amount of genres in items-list
	 */
	public int getGenreCount() {
		return items.size();
	}
	
	/**
	 * Test constructor that takes a string as input.
	 * @param s
	 */
	public Genres(String s) {
	    items.add(new Genre(s));
	}
	
	/**
	 * Returns a genre from the arraylist
	 * @param i index
	 */
	public Genre getGenre(int i) {
		return items.get(i);
	}
	
	/**
	 * Test constructor
	 * @param b
	 */
	public Genres(boolean b) {
		
	}
	
}
