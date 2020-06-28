package com.example.gestionproduitsapplication;

public class Modelclient {
    private  int id;
    private  String nomclient;
    private String prenomclient;
    private byte[]imageclient;


    public Modelclient(int id, String nomclient, String prenomclient, byte[]imageclient) {
        this.id = id;
        this.nomclient = nomclient;
        this.prenomclient = prenomclient;
        this.imageclient = imageclient;
    }

    public Modelclient() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomclient() {
        return nomclient;
    }

    public void setNomclient(String nomclient) {
        this.nomclient = nomclient;
    }

    public String getPrenomclient() {
        return prenomclient;
    }

    public void setPrenomclient(String prenomclient) {
        this.prenomclient = prenomclient;
    }

    public byte[] getImageclient() { return imageclient; }

    public void setImageclient(byte[] imageclient) { this.imageclient = imageclient; }


   @Override
   public String toString(){
        return nomclient;
   }

}
