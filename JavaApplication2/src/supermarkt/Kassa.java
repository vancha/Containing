/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package supermarkt;

import java.util.ArrayList;

/**
 *
 * @author Driving Ghost
 */
public class Kassa {

    public Kassa() {
    }
    Werknemer operator;
    ArrayList<Klant> klanten = new ArrayList<>();

    /**
     * *
     * scanArtikelen kijkt naar alle artikelen die de klant bij zich heeft, en
     * berekent dan de waarde hiervan.
     *
     * @param winkelwagen met alle producten van de klant
     * @return prijs die betaald moet worden
     */
    public void scanArtikelen() {
        if (!klanten.isEmpty()) {
            
            double bedrag = 0;
            
            for (Artikel a : klanten.get(0).wishlist) {
                bedrag += a.getProdPrijs();
            }
            
            if (bedrag > klanten.get(0).geld) {
                System.out.println("teveel gekocht handler needed");
            }
            
            GUI.setKopers();
            GUI.updateProfit(bedrag);
            klanten.remove(0); //toedeledokie klant. 
        }
        if (klanten.size() > 5) {
            kassaBij();
        }
        if (klanten.isEmpty() && (Supermarkt.kassas.size() != 1)) {
            leaveKassa();
        }
    }

    /**
     * *
     * Zorgt ervoor dat er een kassa bij komt
     */
    private void kassaBij() {
        Supermarkt.toDo.add("Kassa");
    }

    /**
     * *
     * Zorgt ervoor dat de operator bij de kassa weggaat
     *
     * @param operator
     */
    private void leaveKassa() {
        Supermarkt.werkPauze.add(operator);
        this.operator = null;
    }
}
