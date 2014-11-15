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
public class Pad {

    Integer padnummer = 0;
    String type;
    ArrayList<Klant> klanten = new ArrayList<>();
    ArrayList<Artikel> artikelen = new ArrayList<>();
    ArrayList<Artikel> genList = new ArrayList<>();
    Werknemer operator;

    /**
     * @param type Welke producten zijn er in het vak
     * @param amount hoeveel variaties producten zijn er in een vak
     * @param stocksize hoeveel van deze producten zijn er in het vak
     */
    public Pad(String type, int amount, int stocksize) {

        this.type = type;
        
        for(int i = 0; i < Supermarkt.paden.size(); i++){
            if(Supermarkt.paden.get(padnummer) != null){
                this.padnummer++;
            }
        }
        
        for (int i = 0; i < amount; i++) {

            generateStock(type, stocksize);
        }

    }

    private void generateStock(String var, int stocksize) {
        switch (var) {
            case "koekjes": //koekjespad. Brownies en choc chip
                if (genList.isEmpty()) {

                    for (int i = 0; i < stocksize; i++) {
                        genList.add(new Artikel("brownies", 0.99, "05-06-1930", "koekjes", 6));
                    }
                } else {
                    for (int i = 0; i < stocksize; i++) {
                        genList.add(new Artikel("choc chip", 0.99, "05-06-1930", "koekjes", 6));

                    }
                   
                    for (Artikel a : genList) {
                        artikelen.add(a);
                    }
                    genList.clear();
                }
                break;
            case "drank": //dranken. Bier en energydrank
                if (genList.isEmpty()) {

                    for (int i = 0; i < stocksize; i++) {
                        genList.add(new Artikel("bier", 0.99, "05-06-1930", "drank", 24));
                    }
                } else {
                    for (int i = 0; i < stocksize; i++) {
                        genList.add(new Artikel("red bull", 0.99, "05-06-1930", "drank", 1));
                    }
                    for (Artikel a : genList) {
                        artikelen.add(a);
                    }
                    genList.clear();
                }
                break;

            case "zuivel": //zuivel pad, melk en boter
                if (genList.isEmpty()) {

                    for (int i = 0; i < stocksize; i++) {
                        genList.add(new Artikel("melk", 0.99, "05-06-1930", "zuivel", 12));
                    }
                } else {
                    for (int i = 0; i < stocksize; i++) {
                        genList.add(new Artikel("boter", 0.99, "05-06-1930", "zuivel", 6));
                    }
                    for (Artikel a : genList) {
                        artikelen.add(a);
                    }
                    genList.clear();
                }
                break;

            case "azie": //aziatisch pad (nasi en bami)
                if (genList.isEmpty()) {

                    for (int i = 0; i < stocksize; i++) {
                        genList.add(new Artikel("nasi", 0.99, "05-06-1930", "azie", 6));
                    }
                } else {
                    for (int i = 0; i < stocksize; i++) {
                        genList.add(new Artikel("bami", 0.99, "05-06-1930", "azie", 6));
                    }
                    for (Artikel a : genList) {
                        artikelen.add(a);
                    }
                    genList.clear();
                }
                break;

             default: //default snoeppad
                if (genList.isEmpty()) {

                    for (int i = 0; i < stocksize; i++) {
                        genList.add(new Artikel("haribo", 0.99, "05-06-1930", "snoep", 12));
                    }
                } else {
                    for (int i = 0; i < stocksize; i++) {
                        genList.add(new Artikel("kinder", 0.99, "05-06-1930", "snoep", 1));
                    }
                    for (Artikel a : genList) {
                        artikelen.add(a);
                    }
                    genList.clear();
                }
                break;
        }

    }

    public Artikel pakVoorwerp(Artikel gezocht){
        artikelen.remove(gezocht);
        return gezocht;
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
     * @param k , de toe te voegen klant
     */
    public void enterPad(Klant k){ // gooi maar weg, of gebruik deze, zo had ik het eerst bedacht.. maar misschien is de huidige situatie beter.
        klanten.add(k); //overlegpuntje? :3
    }
    
    /**
     * verwijder een klant uit dit pad
     * @param k , de te verwijderen klant
     */
    public void leavePad(Klant k){//zelfde als enterPad()
        klanten.remove(k);
    }
}
