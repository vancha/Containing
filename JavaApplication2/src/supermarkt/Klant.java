/* Project Supermarkt - OOP & Software Ontwerp
 * Klant.java
 * Hidde Westerhof | i2A 
 * In samenwerking met: 
 * Rik de Boer
 * Tjipke van der Heide
 * Yannick Strobl
 * 
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
public class Klant {

    /**
     * *
     * Attributen van de klant
     */
    public String naam;                                         //naam = naam van de klant
    public String variant;                                      //met wat voor type klant hebben we te maken
    public Double geld;                                         //hoeveelheid geld dat de klant heeft
    public ArrayList<Artikel> wishlist = new ArrayList<>();     //Welke artikelen heeft de klant op zijn lijstje
    public ArrayList<Artikel> mandje = new ArrayList<>();       //welke artikelen heeft de klant in zijn mandje
    private ArrayList<String> types = new ArrayList<>();        //lijst met typetjes die de klant kan zijn
    private Random r = new Random();                            //declareer random voor random functies.
    boolean debug = false;

    /**
     * *
     * Constructor klant.
     */
    public Klant() {

        this.geld = setGeld();
        this.naam = setNaam();
        
        this.variant = setVariant();
        
        if (!debug) {
            System.out.println("\n" + this.naam + " ( "+ this.variant + ")" + " komt zojuist de winkel binnen");
        }
        
        int items = r.nextInt(8);

        if (items < 0) {
            items *= -1;
        }


        this.wishlist = setWishlist(variant);
        for(Artikel a : wishlist){
            System.out.println(a.getprodNaam());
        }

    }

    /**
     * *
     * Geeft aan hoeveel geld de klant heeft
     *
     * @return double geld, 2 decimalen.
     */
    private double setGeld() {

        double cash = r.nextDouble() * (10 * r.nextInt(100));
        cash = Math.round(cash * 100) / 100;
        return cash;
    }

    /**
     * *
     * Bepaalt wat voor typetje de klant is.
     *
     * @return String variant
     */
    private String setVariant() {

        types.add("Moeder");
        types.add("Student");
        types.add("Bejaarde");
        types.add("Minderjarige");
        types.add("Engineer");
        types.add("Normaal");

        String newVariant = types.get(r.nextInt(types.size()));


        //Secret #1
        double suprise = r.nextDouble();
        switch (newVariant) {
            case "Engineer":
                if (suprise < 0.05) {
                    this.naam = "Jos Foppele";
                }
                break;
            case "Moeder":
                if (suprise < 0.05) {
                    this.naam = "Fiona Saridiene";
                }
                break;
            case "Student":
                if (suprise < 0.1) {
                    this.naam = "Tjipke van der Heide";
                } else if (suprise < 0.15) {
                    this.naam = "Yannick Strobl";
                } else if (suprise < 0.2) {
                    this.naam = "Hidde Westerhof";
                } else if (suprise < 0.25) {
                    this.naam = "Rik de Boer";
                }
                break;
        }

        return newVariant;
    }

    /**
     * *
     * Bepaalt de naam van de klant.
     *
     * @return String met naam
     */
    private String setNaam() {
        int index = r.nextInt(Supermarkt.namen.size());
        if (index < 0) {
            index *= -1;
        }

        return Supermarkt.namen.get(index);
    }

    public void winkelen(Pad p) {
        for (int i = 0; i < p.artikelen.size(); i++) {
            if (this.wishlist.contains(p.artikelen.get(i))) {
                while (this.wishlist.contains(p.artikelen.get(i))) {
                    this.wishlist.remove(p.artikelen.get(i));//haal het product een aantal keer van de wenslijst.(hij kan wel twee koekjes willen i.p.v. één)

                }
                if (!debug) {
                    System.out.println("product " + p.artikelen.get(i).getprodNaam() + " zit niet meer in de wishlist van " + this.naam + ",: " + this.wishlist);
                }
            }
            if (this.wishlist.size() == 1) {
                if (debug && i == p.artikelen.size() - 1) {
                    System.out.println(this.naam + " hoeft alleen nog:" + wishlist.get(0));
                }
            }

        }
    }
    
    public void winkelen(Afdeling a) {
        for (int i = 0; i < a.artikelen.size(); i++) {
            if (this.wishlist.contains(a.artikelen.get(i))) {
                while (this.wishlist.contains(a.artikelen.get(i))) {
                    this.wishlist.remove(a.artikelen.get(i));//haal het product een aantal keer van de wenslijst.(hij kan wel twee koekjes willen i.p.v. één)

                }
                if (!debug) {
                    System.out.println("product " + a.artikelen.get(i).getprodNaam() + " zit niet meer in de wishlist van " + this.naam + ",: " + this.wishlist);
                }
            }
            if (this.wishlist.size() == 1) {
                if (debug && i == a.artikelen.size() - 1) {
                    System.out.println(this.naam + " hoeft alleen nog:" + wishlist.get(0));
                }
            }

        }
    }

    /**
     * *
     * Vult het boodschappenlijstje van de klant met Artikelen
     *
     * @param Type dat de klant is.
     * @return Een Artikel dat in de lijst moet
     */
    private ArrayList<Artikel> setWishlist(String type) {

        ArrayList<Artikel> toAdd = new ArrayList<>();
        int product = r.nextInt(Supermarkt.inventaris.size()); //random indexwaarde product
        double nurture = r.nextDouble(); //of ze dit product nodig hebben vanwege andere redenen.
        if (nurture < 0.1) {
            nurture *= 10;
        }
        double nature = 0.5; //hoe graag ze dit product willen hebben aan de hand van hun type

        if (product < 0) {
            product *= -1;
        }

        switch (type) {

            case "Moeder": //Koopt alles.
                System.out.println("Type is Moeder");
                for (int i = 0; i < Supermarkt.inventaris.size(); i++) {
                    for (int x = 0; x < (nurture * 10); x++) {
                        toAdd.add(Supermarkt.inventaris.get(i));
                    }
                }
                break;

            case "Student": //studenten moeten bier en/of Energydrank hebben.
                System.out.println("Type is student");
                for (int i = 0; i < Supermarkt.inventaris.size(); i++) {
                    for (int x = 0; x < (nurture * 10); x++) {
                        if (nature < nurture) {
                            if ("bier".equals(Supermarkt.inventaris.get(i).getprodNaam())) {
                                toAdd.add(Supermarkt.inventaris.get(i));
                            }
                        } else {
                            if ("red bull".equals(Supermarkt.inventaris.get(i).getprodNaam())) {
                                toAdd.add(Supermarkt.inventaris.get(i));
                            }
                        }
                    }
                }
                break;

            case "Bejaarde": //bejaarde koopt heel veel van een product
                System.out.println("Type is Old bitch");
                for (int i = 0; i < (nurture * 10) + (nurture * 10); i++) {
                    toAdd.add(Supermarkt.inventaris.get(product));
                }
                break;

            case "Minderjarige": //minderjarige koopt één ding.
                toAdd.add(Supermarkt.inventaris.get(product));
                if (toAdd.size() > 1) {
                    toAdd.clear();
                    toAdd.add(Supermarkt.inventaris.get(product));
                }
                break;

            case "Engineer": //Engineer koopt van alles, maar vooral energydrank/koffie
                System.out.println("Type is Engineer");
                for (int i = 0; i < Supermarkt.inventaris.size(); i++) {
                    if ("red bull".equals(Supermarkt.inventaris.get(i).getprodNaam())) {
                        for (int x = 0; x < (nurture * 10); x++) {
                            toAdd.add(Supermarkt.inventaris.get(i));
                        }
                    } else {
                        for (int x = 0; x < 2; x++) {
                            toAdd.add(Supermarkt.inventaris.get(i));
                        }
                    }
                    break;
                }
            default:
                System.out.println("Type is Moeder");
                for (int i = 0; i < Supermarkt.inventaris.size(); i++) {
                    for (int x = 0; x < (nurture * 5); x++) {
                        toAdd.add(Supermarkt.inventaris.get(i));
                    }
                }
                break;

        }


        return toAdd;
    }

    /**
     *
     * @return De naam van de Klant
     */
    @Override
    public String toString() {

        return this.naam;

    }
}
