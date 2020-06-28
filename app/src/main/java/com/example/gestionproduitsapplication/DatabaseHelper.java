package com.example.gestionproduitsapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.gestionproduitsapplication.Modelclient;
import com.example.gestionproduitsapplication.Modelproduit;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "app.db";
    public static final String TABLE_NAME = "registeruser";
    public static final String TABLE_NAME1 = "produits";
    public static final String TABLE_NAME2 = "clients";
    public static final String TABLE_NAME3 = "facture";


    public static final String COL_1 = "ID";
    public static final String COL_2 = "username";
    public static final String COL_3 = "password";
    public static final String COL_4 = "numeroproduit";
    public static final String COL_5 = "designation";
    public static final String COL_6 = "prix";
    public static final String COL_7 = "quantite";
     public static final String COL_8 ="image";
    public static final String COL_10 = "nomclient";
    public static final String COL_11 = "prenomclient";
    public static final String COL_12 = "numerofacture";
    public static final String COL_13 = "imageclient";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE produits (ID INTEGER PRIMARY KEY AUTOINCREMENT, num_produit INTEGER, designation_produit TEXT, prix_produit FLOAT, quantite_produit INTEGER, image_produit BLOB )");
        sqLiteDatabase.execSQL("CREATE TABLE clients (ID INTEGER PRIMARY KEY AUTOINCREMENT, nom_client TEXT, prenom_client TEXT, image_client BLOB )");
        sqLiteDatabase.execSQL("CREATE TABLE facture (ID INTEGER PRIMARY KEY AUTOINCREMENT, num_facture INTEGER, client_id INTEGER, produit_id INTEGER, " +
                " FOREIGN KEY(client_id) REFERENCES clients(ID),  " +
                "FOREIGN KEY(produit_id) REFERENCES produits(ID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(sqLiteDatabase);

    }

    public long ajouterUser(String user, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user);
        contentValues.put("password", password);
        long res = db.insert("registeruser", null, contentValues);
        db.close();
        return res;

    }

    public boolean CheckUser(String username, String password) {
        String[] columns = {COL_1};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count > 0)
            return true;
        else
            return false;
    }

    public long add_produit(int numeroproduit, int quantite, Float prix, String designation, byte [] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("num_produit", numeroproduit);
        contentValues.put("designation_produit", designation);
        contentValues.put("prix_produit", prix);
        contentValues.put("quantite_produit", quantite);
        contentValues.put("image_produit", image);

        long res = db.insert("produits", null, contentValues);
        db.close();
        return res;
    }


    public void supprimer_produit(int id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = ("DELETE FROM produits WHERE id=?");
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) id);
        statement.execute();
        database.close();
    }


    public void modifier_produit(int numproduit, String designation, Float prix, int quantite, int id) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE produits SET num_produit = ? ,designation_produit = ?  , prix_produit = ?  , quantite_produit = ?  WHERE id =? ";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1, String.valueOf(numproduit));
        statement.bindString(2, designation);
        statement.bindString(3, String.valueOf(prix));
        statement.bindString(4, String.valueOf(quantite));
        statement.bindString(5, String.valueOf((double) id));
        statement.execute();
        database.close();
    }


    public Cursor lister_produit(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }


    public long add_client(String nomclient, String prenomclient, byte[] imageclient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom_client", nomclient);
        contentValues.put("prenom_client", prenomclient);
        contentValues.put("image_client", imageclient);

        long res = db.insert("clients", null, contentValues);
        db.close();
        return res;
    }

    public void supprimer_client(int id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = ("DELETE FROM clients WHERE id=?");
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) id);
        statement.execute();
        database.close();
    }


    public void modifier_client(String nomclient, String prenomclient, int id) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE clients SET nom_client = ? , prenom_client = ?  WHERE id =? ";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1, String.valueOf(nomclient));
        statement.bindString(2, prenomclient);
        statement.bindString(3, String.valueOf((double) id));
        statement.execute();
        database.close();
    }


    public Cursor lister_client(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }


    public long add_facture(Integer numerofacture, int id_produit, int id_user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("numerofacture", numerofacture);
        contentValues.put("client_id", numerofacture);
        contentValues.put("produit_id", numerofacture);
        long res = db.insert("facture", null, contentValues);
        db.close();
        return res;

    }

    public List<Modelclient> getAllClients() {
        SQLiteDatabase database = getReadableDatabase();
        List<Modelclient> clients = new ArrayList<Modelclient>();
        Cursor cursor = database.rawQuery("select * from clients", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Modelclient c = new Modelclient();
            c.setId(cursor.getInt(0));
            c.setNomclient(cursor.getString(1));
            c.setPrenomclient(cursor.getString(2));

            clients.add(c);
            cursor.moveToNext();

        }
        cursor.close();
        return clients;
    }

        public List<Modelproduit> getAllProduits() {
            SQLiteDatabase database1 = getReadableDatabase();
            List<Modelproduit> produits = new ArrayList<Modelproduit>();
            Cursor cursor1 = database1.rawQuery("select * from produits", null);
            cursor1.moveToFirst();

            while (!cursor1.isAfterLast()) {
                Modelproduit c1 = new Modelproduit();
                c1.setId(cursor1.getInt(0));
                c1.setNumproduit(cursor1.getString(1));
                c1.setDesignation(cursor1.getString(2));
                c1.setPrix(cursor1.getFloat(3));
                c1.setQuantite(cursor1.getInt(4));
                c1.setImage(cursor1.getBlob(5));

                produits.add(c1);
                cursor1.moveToNext();

            }
            cursor1.close();
            return produits;


    }

//    public Cursor Data(){
//
//        SQLiteDatabase database = getReadableDatabase();
//        return  database.rawQuery("select * from clients", null);
//
//    }



}