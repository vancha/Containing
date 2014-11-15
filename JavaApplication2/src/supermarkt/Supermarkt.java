/* Project Supermarkt - OOP & Software Ontwerp
 * Supermarkt.java
 * Hidde Westerhof | i2A 
 * In samenwerking met: 
 * Rik de Boer
 * Tjipke van der Heide
 * Yannick Strobl
 */

/*  TODO LIST!!!!!!
 * Overgang afdeling voordeelpad
 * Operators op verschillende plekken
 * Geenbijstand moet uitgewerkt worden
 * Het pakken van producten uit schappen en of afdeling.
 * Het afrekenen
 * Hibernate
 * JavaDOCS
 * Astah
 * Kan magazijn weg? 
 */
package supermarkt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Driving Ghost
 */
public class Supermarkt {

    static ArrayList<Werknemer> werkActief = new ArrayList<>();    //Werknemers die wat aan het doen zijn
    static ArrayList<Werknemer> werkPauze = new ArrayList<>();     //Werknemers die niks aan het doen zijn! Arbeiten >:(! 
    static ArrayList<String> toDo = new ArrayList<>();             //een lijst van taken die werknemers nog moeten doen
    static ArrayList<Kassa> kassas = new ArrayList<>();            //een lijst van mogelijke kassas
    static ArrayList<Pad> paden = new ArrayList<>();               //een lijst van alle paden
    static ArrayList<Afdeling> afdelingen = new ArrayList<>();     //een lijst van alle afdelingen
    static ArrayList<Klant> klanten = new ArrayList<>();           //Lijst van alle klanten (static want hij wordt in static methoden aangeroepen)
    static ArrayList<String> namen = new ArrayList<>();            //Lijst met alle namen van een .txt file. Gebruikt door Werknemers en Klanten.
    static ArrayList<Artikel> inventaris = new ArrayList<>();      //Lijst met alle artikelen in de winkel.
    //static Voordeelpad vdpad;
    static Magazijn magazijn;
    private static ArrayList<ArrayList<Klant>> klanten2;
    

    /**
     * Main methode, start het hele gebeuren op commando van de GUI.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ///Toont de GUI klasse.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new GUI());
                frame.pack();
                frame.setVisible(true);
            }
        });

        initPad();                              // Vult de padenlijst
        initAfd();                              // Vult de afdelingenlijst
        initNames();                            // Vult de namenlijst
        initJobs();                             // Vult de todoList met inititele taken
        initInventaris();                       // Vult de Inventaris met mogelijke producten
        initKassas();                           // Vult kassalijst met kassas, zonder operators.
        Random r = new Random();                // Maakt Random aan
        boolean limitset = false;               // Check of GUI de limit gezet heeft
        double limit = 8.0;                     // Standaard limiet waaraan moet worden voldaan. 

        for (int i = 0; i < 4; i++) {
            werkPauze.add(new Werknemer());
        }

        // paden.add();
        while (true) {
            if (limitset) {
                try {
                    Thread.sleep(100);                      //voor debug redenen
                    Double timed = r.nextDouble();          //onze random kans
                    generateKlant(timed, limit, r);         //maakt klanten aan
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    break; //we stoppen met de simulatie. 
                }

                updateGUI(paden, afdelingen);        //update Graphical User Interface met klantinformatie.
                geenBijstand();                             //zet medewerkers aan het werk.
                koopPaden();                                //update klanten in paden
                koopAfdelingen();                           //update klanten in afdelingen
                koopVoordeel();                             //update klanten in voordeelpad. 
                betaalProducten();
                
            } else {
                System.out.print(""); //Dit is nodig voor de Thread. 
                limitset = GUI.limitset;
                limit = GUI.limit;
            }

        }
    }
    
    public static void generateKlant(double timed, double limit, Random r) {
        if (timed > limit) {
            int itop = r.nextInt(10);
            //Check of itop negatief is, kan namelijk niet een negatief aantal klanten toevoegen
            if (itop < 0) {
                itop *= -1;
            }
            //voegt een aantal klanten toe, afhangend van het nummer dat bij itop gemaakt is.
            for (int i = 0; i < itop; i++) {
                paden.get(0).klanten.add(Supermarkt.maakKlant());
            }
            //limitcheck.
            if (limit <= GUI.limit) {
                limit += 0.1;
            }

        } else { //geen klant is toeworden gevoegd. 
            limit -= 0.05;
        }
    }

    /**
     * updateGUI
     *
     * @param paden alle paden, artikelen, en voordeelpad
     */
    public static void updateGUI(ArrayList<Pad> paden, ArrayList<Afdeling> afdelingen) {
        GUI.addKlant(paden, afdelingen);
    }

    public static void koopPaden() {
        for (Pad p : paden) {
            //eerst wordt voor elke klant in een pad de winkelen methode aangeroepen, daarna worden ze verwerkt.
            for (Klant k : p.klanten) {
                k.winkelen(p);
                if (k.wishlist.isEmpty()) {//hebben ze niks in hun wishlist?
                    p.klanten.remove(k);//move!
                    gaKassa(k);
                    klanten.remove(k);
                    break;
                }
            }
        }

        //nu worden de klanten die zijn overgebleven verplaatst naar hun aansluitende gangpad. 
        //kopie.
        klanten2 = new ArrayList<ArrayList<Klant>>();//kopie van de klantenlijst van elk pad.
        for (Pad p : paden) {
            klanten2.add(p.klanten);//voegt de klantenlijst van elk pad aan klanten2 toe
        }

        //beweging van laatste pad naar afdeling.
        if (!(paden.get(paden.size() - 1)).klanten.isEmpty()) { //if last pad !empty
            paden.get(paden.size() - 1).klanten = new ArrayList<>();//om te zorgen dat er niks in blijft staan,maakt het pad ff leeg.
            afdelingen.get(0).klanten = klanten2.get(paden.size() - 1);
            klanten2.set(paden.size() - 1, new ArrayList<Klant>());
        }

        //daadwerkelijke beweging tussen paden
        for (int i = 0; i < paden.size(); i++) {
            if (i == (0)) {
                paden.get(i).klanten = new ArrayList<>();//om te zorgen dat er niks in blijft staan,maakt het pad ff leeg.
                paden.get(i).klanten = klanten2.get(paden.size() - 1);//
            } else {
                paden.get(i).klanten = new ArrayList<>();//om te zorgen dat er niks in blijft staan,maakt het pad ff leeg.
                paden.get(i).klanten = klanten2.get(i - 1);
            }
        }
        klanten2 = new ArrayList<ArrayList<Klant>>();//zet de klantenbackup weer op leeg voor de volgende iteratie.
    }

    public static void koopAfdelingen() {
        for (Afdeling a : afdelingen) {
            //eerst wordt voor elke klant in een pad de winkelen methode aangeroepen, daarna worden ze verwerkt.
            for (Klant k : a.klanten) {
                k.winkelen(a);
                if (k.wishlist.isEmpty()) {//hebben ze niks in hun wishlist?
                    a.klanten.remove(k);//move!
                    gaKassa(k);
                    klanten.remove(k);
                    break;
                }
            }
        }
        //nu worden de klanten die zijn overgebleven verplaatst naar hun aansluitende gangpad. 
        //kopie.
        klanten2 = new ArrayList<ArrayList<Klant>>();//kopie van de klantenlijst van elk pad.
        for (Afdeling a : afdelingen) {
            klanten2.add(a.klanten);//voegt de klantenlijst van elk pad aan klanten2 toe
        }

        //daadwerkelijke beweging. 
        for (int i = 0; i < afdelingen.size(); i++) {
            if (i == (0)) {
                afdelingen.get(i).klanten = new ArrayList<Klant>();//om te zorgen dat er niks in blijft staan,maakt het pad ff leeg.
                afdelingen.get(i).klanten = klanten2.get(afdelingen.size() - 1);//
            } else {
                afdelingen.get(i).klanten = new ArrayList<Klant>();//om te zorgen dat er niks in blijft staan,maakt het pad ff leeg.
                afdelingen.get(i).klanten = klanten2.get(i - 1);
            }
        }
        klanten2 = new ArrayList<ArrayList<Klant>>();//zet de klantenbackup weer op leeg voor de volgende iteratie.
    }

    public static void koopVoordeel() {
    }
    
    public static void betaalProducten(){
        for(Kassa k : kassas){
            k.scanArtikelen();
        }
    }

    public static void gaKassa(Klant K) {
        int beste = kassas.get(0).klanten.size();
        int kassaIndex = 0;
        for (int i = 0; i < kassas.size(); i++) {
            if (kassas.get(i).klanten.size() < beste && kassas.get(i).operator != null) {
                kassaIndex = i;
            }
        }
        kassas.get(kassaIndex).klanten.add(K);
    }

    /**
     * initNames voegt namen toe aan de static lijst Namen. Hieruit haalt de
     * klasse Klant en Werknemer zijn naam.
     */
    public static void initNames() {

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Supermarkt.class.getResourceAsStream("randomnames.txt")));

            String line;
            while ((line = br.readLine()) != null) {
                namen.add(line);
            }
            br.close();
        } catch (IOException e) {
            //System.out.println(e.getMessage());
        }

    }

    /**
     * *
     * Initialiseert de inventaris van de winkel. Heeft effect op (nog niks)
     * klant zijn wishlist, pad zijn inventaris, afdeling zijn inventaris.
     */
    public static void initInventaris() {

        for (int i = 0; i < Supermarkt.paden.size(); i++) {
            for (int x = 0; x < paden.get(i).artikelen.size(); x += 10) {
                inventaris.add(paden.get(i).artikelen.get(x));
            }
        }
        for (int i = 0; i < Supermarkt.afdelingen.size(); i++) {
            for (int x = 0; x < afdelingen.get(i).artikelen.size(); x += 5) {
                inventaris.add(afdelingen.get(i).artikelen.get(x));
            }
        }

    }
    
    /**
     * 
     */
    public static void initKassas(){
        for(int i = 0; i < GUI.kassa; i++){
            kassas.add(new Kassa());
        }
    }
    

    /**
     * *
     * InitJobs vult de lijst toDo met dingen die nog gedaan moeten worden in de
     * opstart van het programma.
     *
     */
    public static void initJobs() {
        toDo.add("Brood");
        toDo.add("Kaas");
        toDo.add("Kassa");
    }

    /**
     * initPad is het maken van alle paden vars worden eerst hardcoded
     * toegevoegd aan een variatielijst voor elke variatie in deze lijst wordt
     * een pad aangemaakt
     */
    public static void initPad() {

        ArrayList<String> vars = new ArrayList<>();
        vars.add("koekjes");
        vars.add("azie");
        vars.add("drank");
        vars.add("zuivel");
        vars.add("snoep");

        for (int i = 0; i < vars.size(); i++) {
            paden.add(new Pad(vars.get(i), 2, 10));

        }

        //vdp = new Voordeelpad();
    }

    /**
     * initAfd is het maken van alle afdelingen vars worden eerst hardcoded
     * toegevoegd aan een variatielijst voor elke variatie in deze lijst wordt
     * een afdelingstype aangemaakt
     */
    public static void initAfd() {

        ArrayList<String> vars = new ArrayList<>();
        vars.add("deegwaar");
        vars.add("fruit");
        vars.add("vleeswaar");
        vars.add("zuivel");

        for (int i = 0; i < vars.size(); i++) {
            afdelingen.add(new Afdeling(vars.get(i), 1, 5));

        }
    }

    /**
     *
     * geenBijstand methode zorgt ervoor dat werknemers in de werkPauze in
     * werkActief komen te staan met een functie.
     *
     * @param werkPauze Lijst met non-actieve werknemers
     * @param toDo lijst met dingen die er gedaan moeten worden
     */
    public static void geenBijstand() {

        try {
            if (!werkPauze.isEmpty()) {
                if (!toDo.isEmpty()) {

                    werkPauze.get(0).taak = toDo.get(0);
                    werkActief.add(werkPauze.get(0));
                    // System.out.println(werkPauze.get(0).Naam + " is now going to do job: " + werkPauze.get(0).taak);
                    werkPauze.remove(0);
                    toDo.remove(0);

                } else {

                    //System.out.println(werkPauze.get(0).Naam + " has no dedicated task. Checking stocks.");
                    werkPauze.get(0).taak = "bijvullen";
                    werkActief.add(werkPauze.get(0));
                    werkPauze.remove(0);

                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * *
     * maakKlant methode voegt klanten toe aan de klanten lijst
     *
     * @return een nieuw aangemaakte klant
     */
    public static Klant maakKlant() {

        try {
            Klant k = new Klant();
            klanten.add(k);
            return k;
        } catch (Exception E) {
            System.out.println(E.getMessage());
        }
        return null;


    }
}
