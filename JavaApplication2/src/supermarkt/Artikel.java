package supermarkt;
// Generated 15-nov-2014 13:09:47 by Hibernate Tools 3.2.1.GA



/**
 * Artikel generated by hbm2java
 */
public class Artikel  implements java.io.Serializable {


     private Integer id;
     private String prodNaam;
     private double prodPrijs;
     private String datum;
     private String prodType;
     private int prodPortie;

    public Artikel() {
    }

    public Artikel(String prodNaam, double prodPrijs, String datum, String prodType, int prodPortie) {
       this.prodNaam = prodNaam;
       this.prodPrijs = prodPrijs;
       this.datum = datum;
       this.prodType = prodType;
       this.prodPortie = prodPortie;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getProdNaam() {
        return this.prodNaam;
    }
    
    public void setProdNaam(String prodNaam) {
        this.prodNaam = prodNaam;
    }
    public double getProdPrijs() {
        return this.prodPrijs;
    }
    
    public void setProdPrijs(double prodPrijs) {
        this.prodPrijs = prodPrijs;
    }
    public String getDatum() {
        return this.datum;
    }
    
    public void setDatum(String datum) {
        this.datum = datum;
    }
    public String getProdType() {
        return this.prodType;
    }
    
    public void setProdType(String prodType) {
        this.prodType = prodType;
    }
    public int getProdPortie() {
        return this.prodPortie;
    }
    
    public void setProdPortie(int prodPortie) {
        this.prodPortie = prodPortie;
    }




}


