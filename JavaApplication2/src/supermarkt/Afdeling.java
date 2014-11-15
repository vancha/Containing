/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermarkt;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Yannick
 */
public class Afdeling {
    
    Integer afdnummer = 0;
    ArrayList<Artikel> artikelen = new ArrayList<>();
    ArrayList<Klant> klanten = new ArrayList<>();
    ArrayList<Artikel> generate = new ArrayList<>();
    String type;
    Werknemer Operator;

    public Afdeling(String produType, int amount, int stocksize) {
        this.type = produType;
        for (int i = 0; i < Supermarkt.afdelingen.size(); i++) {
            if (Supermarkt.afdelingen.get(afdnummer) != null) {
                this.afdnummer++;
            }
        }
        for (int i = 0; i < amount; i++) {

            generateStock(produType, stocksize);
        }
    }

    public void generateStock(String var, int stocksize) {
        
        for (int i = 0; i < stocksize; i++) {
            
            Random r = new Random();
            int portie = r.nextInt(5);
            if (portie < 0) {

                portie *= -1;

            }

            switch (var) {
                case "deegwaar":
                    if (generate.isEmpty()) {

                        generate.add(new Artikel("Brood", 1.49, "2014.09.12", "Deegwaar", portie));
                        generate.add(new Artikel("Koekjes", 2.99, "2014.09.12", "Deegwaar", portie));

                        for (Artikel a : generate) {
                            artikelen.add(a);
                        }
                        generate.clear();
                    }

                    break;
                case "fruit":
                    if (generate.isEmpty()) {
                        generate.add(new Artikel("Appel", 0.69, "2014.09.12", "Fruit", 20));
                        generate.add(new Artikel("Banaan", 0.69, "2014.09.12", "Fruit", 20));

                        for (Artikel a : generate) {
                            artikelen.add(a);
                        }
                        generate.clear();
                    }
                    break;
                case "zuivel":
                    if (generate.isEmpty()) {
                        generate.add(new Artikel("Melk", 1.99, "2014.09.12", "Zuivel", 20));
                        generate.add(new Artikel("Yoghurt", 2.25, "2014.09.12", "Zuivel", 20));

                        for (Artikel a : generate) {
                            artikelen.add(a);
                        }
                        generate.clear();
                    }
                    break;
                case "vleeswaar":
                    if (generate.isEmpty()) {
                        generate.add(new Artikel("Ossenworst", 2.85, "2014.09.12", "Vleeswaar", 20));
                        generate.add(new Artikel("Kipfilet", 3.50, "2014.09.12", "Vleeswaar", 20));

                        for (Artikel a : generate) {
                            artikelen.add(a);
                        }
                        generate.clear();
                    }
                    break;
            }
        }
    }

    public Artikel pakVoorwerp(Artikel gezocht) {
        //klant kijkt in het beschikbare of hij zijn product kan vinden.
        if (artikelen.contains(gezocht)) {
            artikelen.remove(gezocht);
            return gezocht;

        }
        //gewenste product is niet gevonden, klant haalt bij medewerker
        else if(Operator != null){
            return gezocht;
        }
        
        //medewerker is niet beschikbaar(!), klant heeft product niet kunnen kopen. Klacht omhoog. 
        else {
            GUI.klacht = GUI.klacht + 1;
            return null;
        }
    }

    public boolean needRestock() {
        
        if(artikelen.size() < 5){
            return true;
        }
        else
            return false;

    }
    
    /**
     * voeg een klant toe aan dit pad
     *
     * @param k , de toe te voegen klant
     */
    public void enterAfd(Klant k) { // gooi maar weg, of gebruik deze, zo had ik het eerst bedacht.. maar misschien is de huidige situatie beter.
        klanten.add(k); //overlegpuntje? :3
    }

    /**
     * verwijder een klant uit dit pad
     *
     * @param k , de te verwijderen klant
     */
    public void leaveAfd(Klant k) {//zelfde als enterPad()
        klanten.remove(k);
    }
}
