/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package supermarkt;

/**
 *
 * @author Yannick
 */
public class Artikel
{
    private String prodNaam;
    private double prodPrijs;
    private String prodDatum;
    private String produType;
    private int prodPortie;
    
    public Artikel(String prodNaam, double prodPrijs, String prodDatum, String produType, int prodPortie)
    {
        this.prodNaam = prodNaam;
        this.prodPrijs = prodPrijs;
        this.prodDatum = prodDatum;
        this.produType = produType;
        this.prodPortie = prodPortie;
    }
    
    public void setprodNaam(String prodNaam)
    {
        this.prodNaam = prodNaam;
    }
    
    public String getprodNaam()
    {
        return this.prodNaam;
    }
    
    public void setprodPrijs(double prodPrijs)
    {
        this.prodPrijs = prodPrijs;
    }
    
    public double getprodPrijs()
    {
        return prodPrijs;
    }
    
    public void setprodDatum(String prodDatum)
    {
        this.prodDatum = prodDatum;
    }
    
    public String getprodDatum()
    {
        return prodDatum;
    }
    
    public void setproduType(String produType)
    {
        this.produType = produType;
    }
    
    public String getproduType()
    {
        return produType;
    }
    
    public void setprodPortie(int prodPortie)
    {
        this.prodPortie = prodPortie;
    }
    
    public int getprodPortie()
    {
        return prodPortie;
    }
    
    @Override
    public String toString()
    {
        return "["+Artikel.class.getSimpleName() + " " + prodNaam + " " 
                + prodPrijs + " " + prodDatum + " " + produType + " " + prodPortie + "]";
    }
}
