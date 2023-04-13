package kirtika;

import fi.jyu.mit.ohj2.Mjonot;

public class Genre {
    // Description of the YKL classification system
    private String genreDesc;
    // Same as YKL classification
    private String genreId;
    
    /**
     * Initialized from a file, etc.
     * @param line A string delimited by pipes ('|')
     * 
     * @example
     * <pre name="test">
     * Genre genre = new Genre("84.2|Suomalainen kertomakirjallisuus");
     * genre.getGenreId() === "84.2";
     * </pre>
     */
    public Genre(String line) {
        StringBuilder sb = new StringBuilder(line);
        genreId = Mjonot.erota(sb, '|');
        genreDesc = sb.toString();
    }
    
    /**
     * Formats genre to be bar delimited
     * @param line
     * @return
     */
    public static String formatGenre(String line) {
    	StringBuilder sb = new StringBuilder(line);
    	String id = Mjonot.erota(sb, ' ');
    	return id + "|" + sb.toString();
    }
    
    /**
     * Sets the YKL classification
     * @param id YKL classification code
     */
    public void setClassId(String id) {
        genreId = id;
    }
    
    /**
     * Returns the description of the genre
     * @return The genre description
     */
    public String getGenreDesc() {
        return genreDesc;
    }
    
    /**
     * Returns the YKL-ID of the genre
     * @return YKL ID
     */
    public String getGenreId() {
    	return genreId;
    }
    
    /**
     * Does this object have the given genre ID?
     * @param gid The genre ID (YKL classification code)
     * @return True if it matches, false otherwise
     */
    public boolean matchesId(String gid) {
        return this.genreId.equals(gid);
    }
    
    /**
     * Overrides the toString method
     * 
     * @example
     * <pre name="test">
     * Genre genre = new Genre("84.2|Suomalainen kertomakirjallisuus");
     * genre.toString() === "84.2 Suomalainen kertomakirjallisuus";
     * </pre>
     */
    @Override
    public String toString() {
    	return genreId + " " + genreDesc;
    }
}
