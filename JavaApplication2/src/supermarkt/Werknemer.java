/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package supermarkt;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Driving Ghost
 */
public class Werknemer {
    
    String Naam;
    String taak = "Pauze";
    ArrayList<Artikel> pallet = new ArrayList<>();
    Random r = new Random();
    
    public Werknemer(){
        this.Naam = setNaam();
        
    }
    
    private String setNaam(){    
        int index = r.nextInt(Supermarkt.namen.size());
        if (index < 0){
            index *= -1;
        }
        
        return Supermarkt.namen.get(index);
    }
}
