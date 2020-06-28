package com.example.gestionproduitsapplication;

public class Modelproduit {

    private  int id;
    private int numproduit;
    private  float prix;
    private  int quantite;
    private String designation;
    private byte[]image;


    public Modelproduit( int id, int numproduit, String designation, float prix, int quantite, byte[]image) {
       this.id = id;
        this.numproduit = numproduit;
        this.designation = designation;
        this.prix = prix;
        this.quantite = quantite;

        this.image = image;
    }
    public Modelproduit() {

    }

    public int getId() {
       return id;
    }

 public void setId(int id) {
        this.id = id;
    }

    public int getNumproduit() {
        return numproduit;
    }

    public void setNumproduit(String nom) {
        this.numproduit = numproduit;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

   public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
   @Override
   public String toString(){
       return designation;
   }

}


