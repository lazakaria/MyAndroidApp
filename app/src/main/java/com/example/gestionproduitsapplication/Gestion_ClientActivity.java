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

public class Gestion_ClientActivity extends AppCompatActivity {
                                        // création des variables
    ListView mListViewcl;
    ArrayList<Modelclient> mListcl;
    ClientListAdapter mAdaptercl = null;
    ImageView imageViewIcone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion__client);

                             // création et affectation des variables avec ListeView et Adapter
        ActionBar actionBar = getSupportActionBar();
        mListViewcl = findViewById(R.id.Listview1);
        mListcl = new ArrayList<>();
        mAdaptercl = new ClientListAdapter(this, R.layout.rowcl, mListcl);
        mListViewcl.setAdapter(mAdaptercl);

                              //Chargement de la liste

        final Cursor cursor = ClientActivity.db.lister_client("SELECT * FROM clients");
        mListcl.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nomclient  = cursor.getString(1);
            String prenomclient = cursor.getString(2);
            byte[] imageclient = cursor.getBlob(3);
            mListcl.add(new Modelclient( id, nomclient, prenomclient, imageclient ));
        }

        mAdaptercl.notifyDataSetChanged();
        if (mListcl.size()==0){
            Toast.makeText(this, "pas de client", Toast.LENGTH_SHORT).show();
        }


                                  // Lorsqu'on clique longtemps pour modifier ou supprimer

        mListViewcl.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long id) {

                final CharSequence[] items = {"Modifier", "Supprimer"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(Gestion_ClientActivity.this);
                dialog.setTitle("Choisir action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int n) {
                        if (n == 0){
                            Cursor c1 =  ClientActivity.db.lister_client("SELECT ID FROM clients");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c1.moveToNext()){
                                arrID.add(c1.getInt( 0));

                            }
                            aficherPageModificationclient(Gestion_ClientActivity.this,arrID.get(i));

                        }
                        if ( n == 1 ) {

                            Cursor c = ClientActivity.db.lister_client("SELECT ID FROM clients");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }
                            afficherpagesuppcli(arrID.get(i));

                        }
                    }

                });
                dialog.show();

                return true;
            }
        });

    }

    private void afficherpagesuppcli(final int idcli) {
        AlertDialog.Builder suppcli = new AlertDialog.Builder(Gestion_ClientActivity.this);
        suppcli.setTitle(" Warning!");
        suppcli.setMessage("vous etes sur de supp?");



        suppcli.setPositiveButton("OK", new DialogInterface.OnClickListener() {                //Quand on clique sur Ok
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    ClientActivity.db.supprimer_client(idcli);
                    Toast.makeText(Gestion_ClientActivity.this, "produit supprimé!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("Erreur", e.getMessage());

                }
                modifierclientList();                                                               //retour vers la liste
            }
        });

        suppcli.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {           //Quand on clique sur Annuler
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        suppcli.show();
    }

    private void aficherPageModificationclient (Activity activity, final int p) {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.activity_modificationclient);                                // Affectation layout modifecatioclient


        //imageViewIcone = dialog.findViewById(R.id.imageviewmodif);
        final EditText ednom = dialog.findViewById(R.id.modifiernom);
        final EditText edprenom = dialog.findViewById(R.id.modifierprenom);
        Button btn_modclient = dialog.findViewById(R.id.btn_modifierclient);


                             // Cadre fenetre de modification
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels*0.95);
        int height =  (int) (activity.getResources().getDisplayMetrics().heightPixels*0.7);
        dialog.getWindow().setLayout(width,height);
        dialog.show();


                             //Lorsau'on on clique sur "modifier"
        btn_modclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    ClientActivity.db.modifier_client(
                            ednom.getText().toString().trim() ,
                            edprenom.getText().toString().trim(),p);


                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Modifié!", Toast.LENGTH_SHORT).show();
                }
                catch (Exception error){
                    Log.e("Erreur", error.getMessage());

                };
                modifierclientList();


            }

        });

    }
    private void modifierclientList() {
        Cursor cursor = ClientActivity.db.lister_client("SELECT * FROM clients");
        mListcl.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nomclient = cursor.getString(1);
            String prenomclient = cursor.getString(2);
            byte[] imageclient = cursor.getBlob(3);

            mListcl.add(new Modelclient(id, nomclient, prenomclient,imageclient)) ;
        }
        mAdaptercl.notifyDataSetChanged();
    }


}
