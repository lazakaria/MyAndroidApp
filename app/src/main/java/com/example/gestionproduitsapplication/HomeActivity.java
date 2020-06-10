package com.example.gestionproduitsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    private CardView produitcard, clientcard , facturecard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        produitcard = (CardView) findViewById(R.id.cardproduit);
        clientcard = (CardView) findViewById(R.id.cardclient);
        facturecard = (CardView) findViewById(R.id.cardfacture);


        produitcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveTomenu = new Intent(HomeActivity.this, Activity_menu.class);
                startActivity(moveTomenu);
            }
        });
        clientcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveTomenu = new Intent(HomeActivity.this, ClientActivity.class);
                startActivity(moveTomenu);
            }
        });



    }
}
