package kirtika;

import java.io.IOException;

public class Genre {
	private String genreDesc;
	private String genreId;
	
	public Genre() {
		this.genreDesc = "";
		this.genreId = "";
	}

	public void setUnknownClassDesc(String s) throws IOException, InterruptedException {
		this.genreDesc = webscraping.Queries.yklQuery(s);
		// TODO: Tallenna genrekuvaus tiedostoon
	}
	
	public void setClassId(String id) {
		this.genreId = id;
	}
	
	public String getGenreDesc() {
		return this.genreDesc;
	}
	
	public static void main(String[] args) {
		Genre genre = new Genre();
		try {
			genre.setUnknownClassDesc("84.2");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(genre.getGenreDesc());
	}
}
