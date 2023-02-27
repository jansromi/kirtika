package webscraping;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YKLParser {
	private static final String regex = "<span class=\"prefLabel conceptlabel\" id=\"pref-label\">([^<]+)</span>";
	private static final Pattern pattern = Pattern.compile(regex);
	
	public static String parseGenre(String content) {
		String result = "";
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) result = matcher.group(1);
		return result;
	}
}
