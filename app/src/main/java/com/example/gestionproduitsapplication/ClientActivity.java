package com.example.gestionproduitsapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class ClientActivity extends AppCompatActivity {
    public  static DatabaseHelper db;

    private EditText nomc, prenomc;
    private ImageView imgviewc;
    private Button b_addclient;
    private Button b_affichclient;
    private static  final int IMAGE_PICK_CODE = 1002;
    private static  final int PERMISSION_CODE = 1003;


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

        imgviewc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else {
                        pickImageFromGalley();
                    }
                }
                else {
                    pickImageFromGalley();
                }
            }
        });



        b_addclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    db.add_client(
                            nomc.getText().toString().trim(),
                            prenomc.getText().toString().trim(),
                            imageViewToByte(imgviewc)
                    );




                    Toast.makeText(ClientActivity.this, "Client ajouté avec succés", Toast.LENGTH_SHORT).show();

                    nomc.setText("");
                    prenomc.setText("");
                    imgviewc.setImageResource(R.drawable.ic_add_a_photo_black_24dp);

                }
                catch (Exception e){
                    e.printStackTrace();
                }
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
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
        byte[] byteArray = stream.toByteArray();
        return  byteArray;
    }

    private void pickImageFromGalley() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case  PERMISSION_CODE:{
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGalley();
                }
                else {
                    Toast.makeText(this, "pas de premission", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imgviewc.setImageURI(data.getData());

        }
    }



}
