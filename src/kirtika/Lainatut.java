package kirtika;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Lainatut {
	
	private String tiedostonNimi = "lainat.dat";
	private ArrayList<LainattuKirja> alkiot = new ArrayList<>();
	
	/**
	 * Vakiokonstruktori
	 */
	public Lainatut() {
		try {
			alusta();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Alustaa lainat tiedostosta
	 * @throws FileNotFoundException Jos tiedostoa ei saada auki
	 */
	private void alusta() throws FileNotFoundException {
		File f = new File("C:/kurssit/ohj2/kirtika/src/data/" + tiedostonNimi);
		Scanner scan = new Scanner(f);
		
		while (scan.hasNextLine()) {
			   String line = scan.nextLine();
			   if (line.equals("laina_id|kirja_id|laina_hlo|laina_alkupvm (YY-MM-DD) |laina_loppupvm")) continue;
			   alkiot.add(new LainattuKirja(line));
			}
		scan.close();
	}
	
	/**
	 * Palauttaa viitteen LainattuKirja-olioon
	 * @param i monesko kirja listassa
	 * @return
	 */
	public LainattuKirja annaLainattuKirja(int i) {
		return alkiot.get(i);
	}
	
	/**
	 * @return Lainausten määrä
	 */
	public int getLainatutLkm() {
		return alkiot.size();
	}
	
	/**
	 * Asettaa lainatun kirjan nimin
	 * @param lkId lainatun kirjan id. HUOM! Sama kuin kirja-id
	 * @param s Asetettava nimi
	 */
	public void setLainatunKirjanNimi(int lkId, String s) {
		for (LainattuKirja lainattuKirja : alkiot) {
			if (lainattuKirja.oletkoTamaKirja(lkId)) lainattuKirja.setLainatutKirjanNimi(s);
		}
	}

}
