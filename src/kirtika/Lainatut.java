package kirtika;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Lainatut {
	
	private String tiedostonNimi = "lainat.dat";
	private ArrayList<LainattuKirja> alkiot = new ArrayList<>();
	
	public Lainatut() {
		try {
			alusta();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @throws FileNotFoundException Jos tiedostoa ei saada auki
	 */
	private void alusta() throws FileNotFoundException {
		File f = new File("C:/kurssit/ohj2/kirtika/src/data/" + tiedostonNimi);
		Scanner scan = new Scanner(f);
		
		int i = 1;
		while (scan.hasNextLine()) {
			   String line = scan.nextLine();
			   if (line.equals("laina_id|kirja_id|laina_hlo|laina_alkupvm (YY-MM-DD) |laina_loppupvm")) continue;
			   alkiot.add(new LainattuKirja(line));
			}
		scan.close();
	}
	
	
	
	public LainattuKirja annaLainattuKirja(int i) {
		return alkiot.get(i);
	}
	
	public int getLainatutLkm() {
		return alkiot.size();
	}
	
	
	public void setLainatunKirjanNimi(int lkId, String s) {
		for (LainattuKirja lainattuKirja : alkiot) {
			if (lainattuKirja.oletkoTamaKirja(lkId)) lainattuKirja.setLainatutKirjanNimi(s);
		}
	}

	private void alustaFeikkidata() {
		
		LainattuKirja laina1 = new LainattuKirja("Odysseia", "Matti Meikäläinen", LocalDate.of(2022, 01, 01), LocalDate.of(2022, 12, 12));
		alkiot.add(laina1);
		LainattuKirja laina2 = new LainattuKirja("Odysseia", "Maija Meikäläinen", LocalDate.of(2022, 02, 02), LocalDate.of(2022, 03, 02));
		alkiot.add(laina2);
		LainattuKirja laina3 = new LainattuKirja("Odysseia", "Matti Meikäläinen", LocalDate.of(2022, 7, 7), LocalDate.of(2022, 8, 8));
		alkiot.add(laina3);
	}
	
	public static void main(String[] args) {
		Lainatut lainatut = new Lainatut();
		System.out.println("hellos");
	}

}
