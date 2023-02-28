package kirtika;

import java.time.LocalDate;
import java.util.ArrayList;

public class Lainatut {
	
	private String tiedostonNimi = "lainat.dat";
	private ArrayList<LainattuKirja> alkiot = new ArrayList<>();
	
	public Lainatut() {
		alustaFeikkidata();
	}
	
	public LainattuKirja annaLainattuKirja(int i) {
		return alkiot.get(i);
	}
	
	public int getLainatutLkm() {
		return alkiot.size();
	}

	private void alustaFeikkidata() {
		
		LainattuKirja laina1 = new LainattuKirja("Odysseia", "Matti Meikäläinen", LocalDate.of(2022, 01, 01), LocalDate.of(2022, 12, 12));
		alkiot.add(laina1);
		LainattuKirja laina2 = new LainattuKirja("Odysseia", "Maija Meikäläinen", LocalDate.of(2022, 02, 02), LocalDate.of(2022, 03, 02));
		alkiot.add(laina2);
		LainattuKirja laina3 = new LainattuKirja("Odysseia", "Matti Meikäläinen", LocalDate.of(2022, 7, 7), LocalDate.of(2022, 8, 8));
		alkiot.add(laina3);
	}

}
