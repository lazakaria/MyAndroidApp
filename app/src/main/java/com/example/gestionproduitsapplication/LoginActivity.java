package com.example.gestionproduitsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText txtuser;
    EditText txtpwd;
    Button btn_log , btn_auth;
    TextView textViewregister;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DatabaseHelper(this);
        txtuser = (EditText)findViewById(R.id.edittextusername);
        txtpwd =  (EditText)findViewById(R.id.edittextpassword);
        btn_log =   (Button) findViewById(R.id.btn_login);
        btn_auth =  (Button) findViewById(R.id.btn_authent);
        textViewregister =  (TextView) findViewById(R.id.textview_register);

        textViewregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = txtuser.getText().toString().trim();
                String pwd = txtpwd.getText().toString().trim();
                Boolean res = db.CheckUser(user,pwd);
                if (res == true) {
                    Toast.makeText(LoginActivity.this, "Bienvenue", Toast.LENGTH_SHORT).show();
                    Intent moveTomenu = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(moveTomenu);
                    finish();


                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Veuillez entrez votre identifiant", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regiterIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(regiterIntent);

            }
        });



    }

}
