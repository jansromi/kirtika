package webscraping;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public final class YKLQuery {
	/**
	 * 
	 * Scrapes finto.fi with given YKL ID
	 * 
	 * @param yklId
	 * @return genres description in finnish
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	 public static Document yklQuery(String yklId) throws IOException, InterruptedException {
		    // Build the urd with yklId
		    String url = "https://finto.fi/ykl/fi/page/" + yklId;

		    // New http-client and request
		    HttpClient client = HttpClient.newHttpClient();
		    HttpRequest request = HttpRequest.newBuilder()
		            .uri(URI.create(url))
		            .build();

		    // assign the response body
		    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		    String result = response.body();
		    return Jsoup.parse(result);
	    }
	 
	 /**
	  * Parses subgenres from the HTML-document
	  * @param doc HTML-document from finto
	  * @return Subgenres in a list
	  */
	 public static ArrayList<String> parseSubGenres(Document doc) {
		 ArrayList<String> list = new ArrayList<>();
		 Elements liElements = doc.select("div.row.property.prop-skos_narrower li");
         for (Element li : liElements) {
        	 list.add(li.text());
         }
         return list;
	 }

	 
	 public static void main(String[] args){
		Document doc;
		try {
			doc = yklQuery("80");
			ArrayList<String> list = parseSubGenres(doc);
			for (String string : list) {
				System.out.println(string);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}
	 
