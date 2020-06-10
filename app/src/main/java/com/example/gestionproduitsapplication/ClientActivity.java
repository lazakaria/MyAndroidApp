package com.example.gestionproduitsapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ClientActivity extends AppCompatActivity {
    public  static DatabaseHelper db;

    private EditText nomc, prenomc;
    private ImageView imgviewc;
    private Button b_addclient;
    private Button b_affichclient;
   // final int REQUEST_CODE_GALLERY = 999;


   private TextWatcher poduitTextWatcher = new TextWatcher() {
       @Override
       public void beforeTextChanged(CharSequence s, int start, int count, int after) {

       }

       @Override
       public void onTextChanged(CharSequence s, int start, int before, int count) {
           String nomcInput = nomc.getText().toString().trim();
           String prenomcInput = prenomc.getText().toString().trim();


           b_addclient.setEnabled(!nomcInput.isEmpty() && !prenomcInput.isEmpty() );

       }

       @Override
       public void afterTextChanged(Editable s) {

       }
   };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        ActionBar actionBar = getSupportActionBar();


        db = new DatabaseHelper(this);



        nomc = findViewById(R.id.ednomclient);
        prenomc = findViewById(R.id.edprenomclient);

        b_addclient = findViewById(R.id.btn_ajouterC);
        imgviewc = findViewById(R.id.imageaddclient);
        b_affichclient = findViewById(R.id.btn_listerC);


        nomc.addTextChangedListener(poduitTextWatcher);
        prenomc.addTextChangedListener(poduitTextWatcher);

       /* imgviewc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(ClientActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY );

            }
        });*/



        b_addclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nom_client= nomc.getText().toString().trim();
                String prenom_client = prenomc.getText().toString().trim();


                long va = db.add_client(nom_client , prenom_client);
                if (va > 0) {
                    Toast.makeText(ClientActivity.this, "Client ajouté avec succés", Toast.LENGTH_SHORT).show();
                }

                nomc.setText("");
                prenomc.setText("");
            }
        });




        b_affichclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveTolist = new Intent(ClientActivity.this, Gestion_ClientActivity.class);
                startActivity(moveTolist);
            }
        });

    }



}
