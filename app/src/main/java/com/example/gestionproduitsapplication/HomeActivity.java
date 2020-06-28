package com.example.gestionproduitsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private CardView produitcard, clientcard , facturecard;
    Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        produitcard = (CardView) findViewById(R.id.cardproduit);
        clientcard = (CardView) findViewById(R.id.cardclient);
        facturecard = (CardView) findViewById(R.id.cardfacture);
        logout = findViewById(R.id.btn_logout);

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

        facturecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveTomenu = new Intent(HomeActivity.this, FactureActivity.class);
                startActivity(moveTomenu);

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent movetologin = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(movetologin);
                finish();
                Toast.makeText(HomeActivity.this, "déconnexion", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
