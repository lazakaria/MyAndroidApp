package com.example.gestionproduitsapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Activity_menu extends AppCompatActivity {
    public  static DatabaseHelper db;
    private ArrayList<String> data = new ArrayList<String>();
    private ArrayList<String> data1 = new ArrayList<String>();
    private ArrayList<String> data2 = new ArrayList<String>();
    private ArrayList<String> data3 = new ArrayList<String>();
    private TableLayout table;

    private EditText nump, desp, prixp, quantp;
    private TextView textView;
    private ImageView imgview;

    final int REQUEST_CODE_GALLERY = 999;

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

                ActivityCompat.requestPermissions(Activity_menu.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY );

            }
        });




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int num_produit = Integer.parseInt(nump.getText().toString().trim());
                float prix_produit = Float.parseFloat(prixp.getText().toString().trim());
                String designation_produit = desp.getText().toString().trim();

                int quantite_produit = Integer.parseInt(quantp.getText().toString().trim());


                    long va = db.add_produit(num_produit , quantite_produit, prix_produit, designation_produit);
                    if (va > 0) {
                        Toast.makeText(Activity_menu.this, "Produit ajouté avec succés", Toast.LENGTH_SHORT).show();




                }

                        nump.setText("");
                        prixp.setText("");
                        desp.setText("");
                        quantp.setText("");





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




       /* @Override
        public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int [] grantResults){
            if (requestCode == REQUEST_CODE_GALLERY) {
                if( grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);

                }
                else {
                    Toast.makeText(this, "Tu n'a pas le droit d'accés", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            super.onRequestPermissionsResult(requestCode,permissions, grantResults);
        };
        @Override
         protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            if(requestCode == REQUEST_CODE_GALLERY && requestCode == RESULT_OK){
                Uri imageUri = data.getData();
                CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(this);

            }
            if( requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if( requestCode == RESULT_OK){
                    Uri resultUri = result.getUri();
                    imgview.setImageURI(resultUri);

                }
                else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception error = result.getError();

                }
            }




            super.onActivityResult(requestCode, resultCode, data);


        }
*/
}

