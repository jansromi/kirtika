package webscraping;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;

import kirtika.Kirja;


/**
 * Jotta saadaan käyttisdataa, joudutaan hakemaan finnan omalla id:llä.
 * FINNA APIN TOIMINTA:
 * idn haku = / "record?id=FINNA_ID_TÄHÄN"
 * mitä kenttiä palautetaan= "&field[]=Kenttä"
 * ks. https://www.kiwi.fi/display/Finna/Finnan+avoin+rajapinta
 * 
 * PARAMETRIT:
 * authors = kirjan kirjoittajat
 * title = kirjan kokonimi
 * publishers = julkaisija
 * publicationDates = julkaisuvuosi
 * classification = kirjastoluokitus
 * subjects = tunnisteet
 * year = julkaisuvuosi
 * prettyPrint = JSONissa rivinvaihdot
 * 
 * @author Jansromi
 * @Version 25.2.2023
 */
public class Queries {
	
	/**
	 * 
	 * @param queryString 
	 * @param type Jos haetaan isbnllä, niin = "isbn"
	 * 			   Jos haetaan finna-idllä, niin "finna"
	 * 
	 * 
	 * @return JSON, joka sisältää alla kuvattuja tietoja
	 * 
	 * @example
	 * <pre name="test">
	 * #THROWS MalformedURLException
	 * #THROWS ProtocolException
	 * #THROWS IOException
	 * #THROWS InterruptedException
	 * 
	 * String s = finnaQuery("9789511297444", "isbn");
	 * String[] json = s.split("\\{");
	 * json[1] === "\"resultCount\":1,\"records\":[";
	 * json[6].replace('"', ' ').replace('\\', ' ') === " value : 1 /Book /Book / , translated : Kirja }], id : anders.1500431 , images :[], languages :[ fin ], nonPresenterAuthors :["
	 * 
	 * String finna = "anders.1654424";
	 * String s2 = finnaQuery("anders.1654424", "finna");
	 * String[] json2 = s2.split(System.lineSeparator());
	 * 
	 * </pre>
	 */
	public static String finnaQuery(String queryString, String type) {
	    // initialize the url variable to an empty string
	    String url = "";

	    // set the url based on the given type
	    switch (type) {
	        case "isbn":
	            url = "https://api.finna.fi/v1/search?lookfor=" + queryString;
	            break;
	        case "finna":
	            url = "https://api.finna.fi/v1/record?id=" + queryString +
	                    "&field[]=authors&field[]=title&field[]=publishers&field[]=publicationDates"
	                    + "&field[]=classifications&field[]=subjects&field[]=year&field[]=languages&"
	                    + "field[]=summary&prettyPrint=1";
	            break;
	        default:
	            break;
	    }

	    // create a new HttpClient instance
	    HttpClient client = HttpClient.newHttpClient();

	    // create a new HttpRequest instance with the given url
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(url))
	            .build();

	    try {
	        // send the request and get the response as a string
	        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
	        String result = response.body();
	        return result;
	    } catch (MalformedURLException e) {
	        // print the stack trace if a MalformedURLException occurs
	        e.printStackTrace();
	    } catch (IOException e) {
	        // print the stack trace if an IOException occurs
	        e.printStackTrace();
	    } catch (InterruptedException e) {
	        // print the stack trace if an InterruptedException occurs
	        e.printStackTrace();
	    } catch (Exception e) {
	        // print the stack trace if any other type of exception occurs
	        e.printStackTrace();
	    }

	    // return null if an error occurred
	    return null;
	}
	
	/**
	 * 
	 * @param yklNum
	 * @return
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	 public static String yklQuery(String yklNum) throws IOException, InterruptedException {
		    // Build the URL to query for the YKL number
		    String url = "https://finto.fi/ykl/fi/page/" + yklNum;

		    // Create a new HTTP client and request
		    HttpClient client = HttpClient.newHttpClient();
		    HttpRequest request = HttpRequest.newBuilder()
		            .uri(URI.create(url))
		            .build();

		    // Send the request and get the response
		    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		    String result = response.body();

		    // Parse the genre from the response using the YKLParser
		    result = YKLParser.parseGenre(result);
		    
		    // Return the parsed genre
		    return result;
	    }
	
	 
	public static void main(String[] args) throws IOException, InterruptedException {
		//String s = finnaQuery("9789510185377", "isbn");
		String s = finnaQuery("9512033585", "isbn");
		//String s = finnaQuery("9789511350545", "isbn");
		s = FinnaParser.parseId(s);
		String s2 = finnaQuery(s, "finna");
		System.out.println(FinnaParser.parseBookName(s2));
		List<String> lista = FinnaParser.parseWriter(s2);
		
		System.out.println(FinnaParser.listToBarString(lista));
		System.out.println(FinnaParser.parseSubjects(s2));
		System.out.println(FinnaParser.parseLanguage(s2));
		System.out.println(FinnaParser.parseYKL(s2));
		//System.out.println(yklQuery("80.2"));
	}

}
