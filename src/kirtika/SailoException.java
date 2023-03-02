package kirtika;

public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;


    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa
     * käytettävä viesti
     * @param viesti Poikkeuksen viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
    
    
    public static class TaulukkoTaysiException extends Exception {
        private static final long serialVersionUID = 1L;
        public TaulukkoTaysiException(String viesti) {
            super(viesti);
        }
    }

}
