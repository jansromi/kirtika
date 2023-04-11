package webscraping;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YKLQuery {
	
	/**
	 * Scrapes result from HTML
	 * @author Jansromi
	 *
	 */
	public static class YKLParser {
	private static final String regex = "<span class=\"prefLabel conceptlabel\" id=\"pref-label\">([^<]+)</span>";
	private static final Pattern pattern = Pattern.compile(regex);
		
	public static String parseGenre(String content) {
		String result = "";
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) result = matcher.group(1);
		return result;
		}
	}

	/**
	 * 
	 * Scrapes finto.fi with given YKL ID
	 * 
	 * @param yklId
	 * @return genres description in finnish
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	 public static String yklQuery(String yklId) throws IOException, InterruptedException {
		    // Rakennetaan url yklNumin avulla
		    String url = "https://finto.fi/ykl/fi/page/" + yklId;

		    // Uusi HTTP asiakas and pyyntö
		    HttpClient client = HttpClient.newHttpClient();
		    HttpRequest request = HttpRequest.newBuilder()
		            .uri(URI.create(url))
		            .build();

		    // sijoitetaan responsen body result-muuttujaan
		    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		    String result = response.body();

		    // Parsetaan ja palautetaan oikea genre
		    return YKLParser.parseGenre(result);
	    }

}
