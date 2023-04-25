package webscraping;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * The YKLQuery class is a utility class that provides methods for scraping and parsing subgenres from finto.fi with a given YKL ID.
 * The class includes a static method 'yklQuery' that takes a YKL ID and returns the HTML-document from finto.fi,
 * and another static method 'parseSubGenres' that parses subgenres from the HTML-document.
 * @author Jansromi
 * @version 25.4.2023
 * 
 * @example
 * <pre name="test">
 * #THROWS IOException, InterruptedException
 * #import java.io.IOException;
 * 
 * #import org.jsoup.nodes.Document;
 * #import org.junit.*;
 * #import webscraping.*;
 * 
 * </pre>
 */
public final class YKLQuery {
	public static final String YKL_BASE_URL = "https://finto.fi/ykl/fi/page/";
	public static final String YKL_SUBCLASS_SELECTOR = "div.row.property.prop-skos_narrower li span.versal";
	public static final String YKL_NUMBER_SELECTOR = "span.notation#notation";
	public static final String YKL_DESCRIPTION_SELECTOR = "span.prefLabel.conceptlabel#pref-label";
	
	/**
	 * Recursively queries finto 
	 * @param yklId
	 * @param genres
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void recursiveFintoQuery(String yklId, ArrayList<String> genres) throws IOException, InterruptedException {
	    String url = YKL_BASE_URL + yklId;

	    // New http-client and request
	    HttpClient client = HttpClient.newHttpClient();
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(url))
	            .build();

	    // assign the response body
	    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	    Document doc = Jsoup.parse(response.body());

	    // select the sub class-elements
	    Elements subclasses = doc.select(YKL_SUBCLASS_SELECTOR);

	    // If there are no subclasses, we have reached the end of the hierarchy
	    if (subclasses.isEmpty()) {
	    	Element value = doc.select("#maincontent > div > div.concept-info > div.concept-main > div.row.property.prop-preflabel > div.property-value-column").first();
	    	if (value == null) return;
	        Element yklNumber = value.select(YKL_NUMBER_SELECTOR).first();
	        if (yklNumber == null) return;
	        Element yklDescription = doc.select(YKL_DESCRIPTION_SELECTOR).first();
	        String ykl = yklNumber.text() + " " + yklDescription.text();
		    // Check if the genre already exists in the ArrayList before adding it
		    if (!genres.contains(ykl)) {
		        genres.add(ykl);
		        System.out.println("Fetched a genre: " + ykl);
		    }
	        return;
	    }

	    // Recursively process the subclasses
	    for (Element subclass : subclasses) {
	        String subclassValue = subclass.text();
	        recursiveFintoQuery(subclassValue, genres);
	    }

	    // Extract the YKL code and description
	    Element value = doc.select("#maincontent > div > div.concept-info > div.concept-main > div.row.property.prop-preflabel > div.property-value-column").first();
	    Element yklNumber = value.select(YKL_NUMBER_SELECTOR).first();
	    if (yklNumber == null) return;
	    Element yklDescription = doc.select(YKL_DESCRIPTION_SELECTOR).first();

	    // Construct the genre string
	    String ykl = yklNumber.text() + " " + yklDescription.text();
	    
	    // Check if the genre already exists in the ArrayList before adding it
	    if (!genres.contains(ykl)) {
	        genres.add(ykl);
	        System.out.println("Fetched a genre: " + ykl);
	    }
	}

	 
	 public static void main(String[] args) throws IOException, InterruptedException{
		//Document doc = yklQuery("11");
		ArrayList<String> g = new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			recursiveFintoQuery(String.valueOf(i), g);
			for (String string : g) {
				System.out.println(string);
			}
		}
	 }
}
	 
