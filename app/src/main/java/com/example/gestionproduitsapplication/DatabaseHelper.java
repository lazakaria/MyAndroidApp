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

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="app.db";
    public static final String TABLE_NAME ="registeruser";
    public static final String TABLE_NAME1 ="produits";
    public static final String TABLE_NAME2 ="clients";

    public static final String COL_1 ="ID";
    public static final String COL_2 ="username";
    public static final String COL_3 ="password";
    public static final String COL_4 ="numeroproduit";
    public static final String COL_5 ="designation";
    public static final String COL_6 ="prix";
    public static final String COL_7 ="quantite";
  // public static final String COL_8 ="imagep";
  public static final String COL_10 ="nomclient";
    public static final String COL_11 ="prenomclient";



    public DatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE produits (ID INTEGER PRIMARY KEY AUTOINCREMENT, num_produit INTEGER, designation_produit TEXT, prix_produit FLOAT, quantite_produit INTEGER )");
        sqLiteDatabase.execSQL("CREATE TABLE clients (ID INTEGER PRIMARY KEY AUTOINCREMENT, nom_client TEXT, prenom_client TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(sqLiteDatabase);

    }
    public  long ajouterUser(String user, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user);
        contentValues.put("password", password);
        long res = db.insert("registeruser",null,contentValues);
        db.close();
        return res;

    }
    public boolean CheckUser(String username, String password){
        String[] columns  = { COL_1};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_NAME,columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if(count>0)
            return true;
        else
            return false;
    }

    public  long add_produit(int numeroproduit, Integer quantite, Float prix, String designation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("num_produit", numeroproduit);
        contentValues.put("designation_produit", designation);
        contentValues.put("prix_produit", prix);
        contentValues.put("quantite_produit", quantite);
        long res = db.insert("produits",null,contentValues);
        db.close();
        return res;
    }


    public void supprimer_produit(int id ){
        SQLiteDatabase database= getWritableDatabase();
        String sql =  ("DELETE FROM produits WHERE id=?");
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1,(double)id);
        statement.execute();
        database.close();
    }


    public void modifier_produit (Integer numproduit, String designation, Float prix, Integer quantite, int id){

        SQLiteDatabase database= getWritableDatabase();
        String sql =  "UPDATE produits SET num_produit = ? ,designation_produit = ?  , prix_produit = ?  , quantite_produit = ?  WHERE id =? ";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1, String.valueOf(numproduit));
        statement.bindString(2, designation);
        statement.bindString(3, String.valueOf(prix));
        statement.bindString(4, String.valueOf(quantite));
        statement.bindString(5, String.valueOf((double) id));
        statement.execute();
        database.close();
    }


    public Cursor lister_produit(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }


    public  long add_client(String nomclient, String prenomclient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom_client", nomclient);
        contentValues.put("prenom_client", prenomclient);

        long res = db.insert("clients",null,contentValues);
        db.close();
        return res;
    }

    public void supprimer_client(int id ){
        SQLiteDatabase database= getWritableDatabase();
        String sql =  ("DELETE FROM clients WHERE id=?");
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1,(double)id);
        statement.execute();
        database.close();
    }


    public void modifier_client (String nomclient, String prenomclient, int id){

        SQLiteDatabase database= getWritableDatabase();
        String sql =  "UPDATE clients SET nom_client = ? , prenom_client = ?  WHERE id =? ";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1, String.valueOf(nomclient));
        statement.bindString(2, prenomclient);
        statement.bindString(3, String.valueOf((double) id));
        statement.execute();
        database.close();
    }


    public Cursor lister_client(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }



}


