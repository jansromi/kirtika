package kirtika;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
	 * Test constructor that takes a string as input.
	 * @param s
	 */
	public Genres(String s) {
	    items.add(new Genre(s));
	}
	
	/**
	 * Test constructor
	 * @param b
	 */
	public Genres(boolean b) {
		
	}
	
	/**
	 * Adds a new genre to items.
	 * @param line where genre formatted as "84.2 Suomenkielinen kaunokirjallisuus"
	 */
	public void addGenre(String line) {
		items.add(new Genre(Genre.formatGenre(line)));
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
	 * genret.getYklDesc("67") === "Unknown";
	 * </pre>
	 */
	public String getYklDesc(String s) {
		for (Genre genre : items) {
			if (genre.matchesId(s)) return genre.getGenreDesc();
		}
		return "Unknown";
	}
	
	/**
	 * Scrapes the genre description from internet
	 * @param Genres YKL id
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void setUnknownClassDesc(String genreId) throws IOException, InterruptedException {
		//this.genreDesc = webscraping.Queries.yklQuery(s);
		// TODO: Tallenna genrekuvaus tiedostoon
	}
	
	/**
	 * @return All Genres in items.
	 */
	public ArrayList<Genre> getGenres(){
		return items;
	}
	
}
