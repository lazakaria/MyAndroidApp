package com.example.gestionproduitsapplication;

public class Modelclient {
    private  int id;
    private  String nomclient;
    private String prenomclient;
    // private byte[]image;


    public Modelclient(int id, String nomclient, String prenomclient) {
        this.id = id;
        this.nomclient = nomclient;
        this.prenomclient = prenomclient;
        //   this.image = image;
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
}
