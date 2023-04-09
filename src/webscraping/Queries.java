package webscraping;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;



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
	    String url = "";

	    // Alustetaan url sen mukaan, onko kyseessä isbn vai finna-id-haku
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
	            return null;
	    }

	    // luodaan uusi asiakasinstanssi
	    HttpClient client = HttpClient.newHttpClient();

	    // luodaan uusi pyyntö urlilla
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(url))
	            .build();

	    try {
	        // lähetetään pyyntö ja palautetaan se
	        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
	        String result = response.body();
	        return result;
	    } catch (MalformedURLException e) {
	    	System.err.println("Huono url" + e.getLocalizedMessage());
	    } catch (IOException e) {
	    	System.err.println("IO-toiminnan häiriö" + e.getLocalizedMessage());
	    } catch (InterruptedException e) {
	    	System.err.println("Pyyntö kestää liian kauan" + e.getLocalizedMessage());
	        e.printStackTrace();
	    }
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
		    // Rakennetaan url yklNumin avulla
		    String url = "https://finto.fi/ykl/fi/page/" + yklNum;

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
	
	 
	public static void main(String[] args) throws IOException, InterruptedException {
		//String s = finnaQuery("9789510185377", "isbn");
		//String s = finnaQuery("9512033585", "isbn");
		String s = finnaQuery("9789511350545", "isbn");
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
