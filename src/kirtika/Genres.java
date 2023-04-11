package kirtika;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Genres {
	private String genresFilePath = "src/data/genret.dat";
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
	 * Initialize genres from a file.
	 * @throws FileNotFoundException if genret.dat file is not found.
	 */
	private void initGenres() throws FileNotFoundException {
		File f = new File("C:/kurssit/ohj2/kirtika/src/data/" + genresFilePath);
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
	
	public void setUnknownClassDesc(String s) throws IOException, InterruptedException {
		//this.genreDesc = webscraping.Queries.yklQuery(s);
		// TODO: Tallenna genrekuvaus tiedostoon
	}
	
	@Override
	public ArrayList<Genre> clone(){
		ArrayList<Genre> list = new ArrayList<Genre>();
		for (Genre genre : items) {
			list.add(genre);
		}
		return list;
	}
	
	/**
	 * @return All Genres in items.
	 */
	public ArrayList<Genre> getGenres(){
		return items;
	}
}
