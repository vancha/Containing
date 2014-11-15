/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package supermarkt;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Driving Ghost
 */
public class Kassa {

    public Kassa() {
    }
    public static SessionFactory factory;
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
     public void addArtikel(String prodNaam, double prodPrijs, String datum, String prodType, int prodPortie){
        System.out.println("Ik voeg nu een artikel toe.");
        try{
            factory = new Configuration().configure().buildSessionFactory();
        }catch(Throwable ex){
            System.out.println(ex.toString());
        }
        Transaction tx = null;
        try
        {
            Session session = factory.openSession();
        
        Integer artikelID = null;
        tx = session.beginTransaction();
        Artikel artikel = new Artikel(prodNaam, prodPrijs, datum, prodType, prodPortie);
        session.save(artikel);
        tx.commit();
        session.close();
        }catch(HibernateException e){
            if(tx != null)tx.rollback();
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            //Logger.getLogger(Kassa.class.getName()).log(Level.SEVERE, null, ex);
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
