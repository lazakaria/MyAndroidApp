package com.example.gestionproduitsapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Gestion_ProduitActivity extends AppCompatActivity {


    ListView mListView;
    ArrayList<Modelproduit> mList;
    ProduitListAdapter mAdapter = null;
    ImageView imageViewIcone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion__produit);

        ActionBar actionBar = getSupportActionBar();



        mListView = findViewById(R.id.Listview);

     mList = new ArrayList<>();
     mAdapter = new ProduitListAdapter(this, R.layout.row, mList);
     mListView.setAdapter(mAdapter);

        final Cursor cursor = Activity_menu.db.lister_produit("SELECT * FROM produits");
        mList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            int numproduit = cursor.getInt(1);
            String designation = cursor.getString(2);
            float prix = cursor.getFloat(3);
            int quantite = cursor.getInt(4);
            byte[] image = cursor.getBlob(5);
            mList.add(new Modelproduit(id, numproduit, designation, prix, quantite, image ));

        }
        mAdapter.notifyDataSetChanged();
        if (mList.size()==0){
            Toast.makeText(this, "pas de produit", Toast.LENGTH_SHORT).show();

        }
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long id) {

                final CharSequence[] items = {"Modifier", "Supprimer"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(Gestion_ProduitActivity.this);
                dialog.setTitle("Choisir action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int m) {
                        if (m == 0){
                            Cursor c =  Activity_menu.db.lister_produit("SELECT ID FROM produits");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt( 0));

                            }
                            aficherPageModification(Gestion_ProduitActivity.this,arrID.get(i));

                        }
                        if ( m == 1) {

                            Cursor c = Activity_menu.db.lister_produit("SELECT ID FROM produits");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }
                            afficherpagesupp(arrID.get(i));
                        }
                            }

                        });
                dialog.show();

                return true;
            }
        });

    }

    private void afficherpagesupp(final int idprod) {
        AlertDialog.Builder suppprod = new AlertDialog.Builder(Gestion_ProduitActivity.this);
        suppprod.setTitle(" Warning!");
        suppprod.setMessage("vous etes sur de supp?");
        suppprod.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Activity_menu.db.supprimer_produit(idprod);
                    Toast.makeText(Gestion_ProduitActivity.this, "produit supprimé!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("Erreur", e.getMessage());

                }
                modifierproduitList();

            }
        }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        suppprod.show();
    }

    private void aficherPageModification (Activity activity, final int p) {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.activity_modification);

        dialog.setTitle("Modifier");

        //imageViewIcone = dialog.findViewById(R.id.imageviewmodif);
        final EditText ednum = dialog.findViewById(R.id.modifiernum);
        final EditText edprix = dialog.findViewById(R.id.modifierprix);
        final EditText eddesig = dialog.findViewById(R.id.modifierdesig);
        final EditText edquantit = dialog.findViewById(R.id.modifierquantite);
        Button btn_mod = dialog.findViewById(R.id.btn_modifier);




        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels*0.95);
        int height =  (int) (activity.getResources().getDisplayMetrics().heightPixels*0.7);
        dialog.getWindow().setLayout(width,height);
        dialog.show();



        btn_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Activity_menu.db.modifier_produit(
                            Integer.parseInt(ednum.getText().toString().trim()) ,
                            eddesig.getText().toString().trim(),
                            Float.parseFloat(edprix.getText().toString().trim()),
                            Integer.parseInt(edquantit.getText().toString().trim()),p );


                dialog.dismiss();
                Toast.makeText(getApplicationContext(),"Modifié!", Toast.LENGTH_SHORT).show();
            }
            catch (Exception error){
                Log.e("Erreur", error.getMessage());

            };
                modifierproduitList();
                

    }

        });

    }

    private void modifierproduitList() {
        Cursor cursor = Activity_menu.db.lister_produit("SELECT * FROM produits");
        mList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            int numproduit = cursor.getInt(1);
            String designation = cursor.getString(2);
            float prix = cursor.getFloat(3);
            int quantite = cursor.getInt(4);
            byte[] image = cursor.getBlob(5);
            mList.add(new Modelproduit(id, numproduit, designation, prix, quantite,image));
        }
        mAdapter.notifyDataSetChanged();
    }
}
