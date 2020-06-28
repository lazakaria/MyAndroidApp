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
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Activity_menu extends AppCompatActivity {
    public static DatabaseHelper db;
    private EditText nump, desp, prixp, quantp;
    private TextView textView;
    private ImageView imgview;

    private static  final int IMAGE_PICK_CODE = 1000;
    private static  final int PERMISSION_CODE = 1001;

    private Button b1;
    private Button b_affich;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ActionBar actionBar = getSupportActionBar();
        db = new DatabaseHelper(this);
        nump = findViewById(R.id.ednum);
        prixp = findViewById(R.id.edprix);
        desp = findViewById(R.id.eddesig);
        quantp = findViewById(R.id.edquant);
        b1 = findViewById(R.id.btn_ajouterP);
        imgview = findViewById(R.id.imageadd);
        b_affich = findViewById(R.id.btn_afficher);


        nump.addTextChangedListener(poduitTextWatcher);
        desp.addTextChangedListener(poduitTextWatcher);
        prixp.addTextChangedListener(poduitTextWatcher);
        quantp.addTextChangedListener(poduitTextWatcher);


        imgview.setOnClickListener(new View.OnClickListener() {
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


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.add_produit(
                    Integer.parseInt(nump.getText().toString().trim()),
                    Integer.parseInt(quantp.getText().toString().trim()),
                    Float.parseFloat(prixp.getText().toString().trim()),
                    desp.getText().toString().trim(),
                    imageViewToByte(imgview)
                    );




                        Toast.makeText(Activity_menu.this, "Produit ajouté avec succés", Toast.LENGTH_SHORT).show();

                    nump.setText("");
                    prixp.setText("");
                    desp.setText("");
                    quantp.setText("");
                    imgview.setImageResource(R.drawable.ic_add_a_photo_black_24dp);

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        b_affich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveTolist = new Intent(Activity_menu.this, Gestion_ProduitActivity.class);
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
            imgview.setImageURI(data.getData());

        }
    }

    private TextWatcher poduitTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nompInput = nump.getText().toString().trim();
            String desppInput = desp.getText().toString().trim();
            String prixpInput = prixp.getText().toString().trim();
            String quantpInput = quantp.getText().toString().trim();

            b1.setEnabled(!nompInput.isEmpty() && !desppInput.isEmpty() && !prixpInput.isEmpty() && !quantpInput.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };




}

