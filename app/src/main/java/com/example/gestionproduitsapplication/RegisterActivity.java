package com.example.gestionproduitsapplication;

import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText txtuser;
    EditText txtpwd;
    EditText confpwr;
    Button btn_rg;
    TextView textViewlogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);
        txtuser = (EditText)findViewById(R.id.edittextusername);
        txtpwd =  (EditText)findViewById(R.id.edittextpassword);
        confpwr =  (EditText)findViewById(R.id.edittextconfpassword);
        btn_rg =   (Button) findViewById(R.id.btn_reg);
        textViewlogin =  (TextView) findViewById(R.id.textview_log );


        textViewlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(LoginIntent);
            }
        });

        btn_rg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = txtuser.getText().toString().trim();
                String pwd = txtpwd.getText().toString().trim();
                String confpwd = confpwr.getText().toString().trim();


                if(txtuser.getText().toString().isEmpty() ) {
                    Toast.makeText(getApplicationContext(), "Nom utilisateur vide!", Toast.LENGTH_SHORT).show();
                }
                if(txtpwd.getText().toString().isEmpty() ) {
                    Toast.makeText(getApplicationContext(), "Mot de pass vide!", Toast.LENGTH_SHORT).show();
                }
                if(confpwr.getText().toString().isEmpty() ) {
                    Toast.makeText(getApplicationContext(), "Confirmation de mot de passe vide!", Toast.LENGTH_SHORT).show();
                }

                else if (pwd.equals(confpwd)) {
                    long val = db.ajouterUser(user, pwd);
                    if (val > 0) {
                        Toast.makeText(RegisterActivity.this, "Enregisté avec succés", Toast.LENGTH_SHORT).show();
                        Intent moveTologin = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(moveTologin);
                    } else {
                        Toast.makeText(RegisterActivity.this, " Ereur", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(RegisterActivity.this, "mot de passe n'est pas confirmé ! ", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }



}
