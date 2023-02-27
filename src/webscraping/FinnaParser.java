package webscraping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FinnaParser {

	/**
	 * FinnaID parseri
	 * @param content JSON hakudata finnan APIsta
	 * @return Ensimmäisen hakutuloksen ID
	 */
	public static String parseId(String content){
		JSONObject obj = parseFirstRecord(content);
		return obj.getString("id");
	}
	
	/**
	 * Hakutulosparseri.
	 * @param content
	 * @return
	 */
	public static JSONObject parseFirstRecord(String content) {
		JSONObject obj = new JSONObject(content);
		JSONObject firstRecord = obj.getJSONArray("records").getJSONObject(0);
		return firstRecord;
	}
	
	/**
	 * Parsii kirjan nimen
	 * @param content
	 * @return
	 */
	public static String parseBookName(String content) {
		JSONObject obj = parseFirstRecord(content);
		return obj.getString("title");
	}
	
	/**
	 * Parsii kirjan kielen
	 * @param content
	 * @return
	 */
	public static List<String> parseLanguage(String content) {
		JSONArray s = parseFirstRecord(content).getJSONArray("languages");
		return getArrKeys(s);
	}
	
	
	/**
	 * Parsii pääkirjailijat listaan.
	 * 
	 * TODO: Jättää tällä hetkellä esim. kääntäjät
	 * pois tuloksesta.
	 * 
	 * @param content
	 * @return lista kirjailijoista
	 */
	public static List<String> parseWriter(String content) {
		JSONObject obj = parseFirstRecord(content);
	    JSONObject primaryWriters = obj.getJSONObject("authors").getJSONObject("primary");
	    return getObjKeys(primaryWriters);
	    
	}
	
	/**
	 * Palauttaa aihetunnisteet listana
	 * @param content
	 * @return
	 */
	public static List<String> parseSubjects(String content){
		JSONArray s = parseFirstRecord(content).getJSONArray("subjects");
		return getArrKeys(s);
	}
	
	/**
	 * TODO: org.json.JSONException: jos ykl luokkaa ei löydy
	 * @param content
	 * @return
	 */
	public static List<String> parseYKL(String content) {
		JSONArray s;
		try {
			s = parseFirstRecord(content).getJSONObject("classifications").getJSONArray("ykl");
			return getArrKeys(s);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Palauttaa objectin avaimet listana.
	 * Voidaan käyttää esim. kirjailijoiden
	 * liittämiseen listaan.
	 * @param obj
	 * @return
	 */
	public static List<String> getObjKeys(JSONObject obj){
		List<String> keysList = new ArrayList<>();
	    Iterator<String> keys = obj.keys();
	    while (keys.hasNext()) {
	        String key = keys.next();
	        keysList.add(key);
	    }
	    return keysList;
	}
	
	/**
	 * Palauttaa JSON-taulukon avaimet listana.
	 * @param arr
	 * @return
	 */
	public static List<String> getArrKeys(JSONArray arr){
		List<String> keysList = new ArrayList<>();
		for (Object obj : arr) {
			keysList.add(obj.toString());
		}
		return keysList;
	}
	

	
	/**
	 * Palauttaa listan alkiot
	 * @param list 
	 * @return
	 */
	public static String listToBarString(List<String> list) {
		String result = "";
		for (String string : list) {
			result = result + string + "|";
		}
		return result;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
