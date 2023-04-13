package utils;

/**
 * Class containing String-sanitation functions
 * @author Jansromi
 */
public final class StringSanitizer {
	
	/**
	 * Replaces scandi/nordic characters with "regular" ones
	 * @return Input with scandic characters replaced.
	 * 
	 * @example
	 * <pre name="test">
	 * String input = "Älä höpötä, Øystein! Örjan on väärässä.";
	 * replaceNordicChars(input) === "Ala hopota, Oystein! Orjan on vaarassa.";
	 * </pre>
	 */
	public static String replaceNordicChars(String s) {
	    String output = s
	    	      .replaceAll("[ÄÆ]", "A")
	    	      .replaceAll("[äæ]", "a")
	    	      .replaceAll("[ÖØ]", "O")
	    	      .replaceAll("[öø]", "o")
	    	      .replaceAll("[Å]", "Aa")
	    	      .replaceAll("[å]", "aa");
	    return output;
	}
	
	/**
	 * Replaces all chars except A-z & 0-9
	 * @return String with all special characters replaced with empty string
	 * 
	 * @example
	 * <pre name="test">
	 * String input = "Ala hopota, Oystein! Orjan on vaarassa.";
	 * replaceSpecialChars(input) === "AlahopotaOysteinOrjanonvaarassa";
	 * </pre>
	 */
	public static String replaceSpecialChars(String s) {
	    return s.replaceAll("[^A-Za-z0-9]", "");
	}
}
