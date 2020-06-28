package com.example.gestionproduitsapplication;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FactureActivity extends AppCompatActivity {

    private ArrayList<String> data = new ArrayList<String>();
    private ArrayList<String> data1 = new ArrayList<String>();
    private ArrayList<String> data2 = new ArrayList<String>();
    private ArrayList<String> data3 = new ArrayList<String>();
    private ArrayList<String> data4 = new ArrayList<String>();
    private TableLayout table;
    EditText ednumfac;
    EditText edquantite;
    Button btn_facture;
    Button btn_screen;
    DatabaseHelper db;
    TextView montant;
    Spinner spinner_client;
    Spinner spinner_produit;
    TextView nomclient;
    TextView numfact;
 //   ImageView imageView;
  //  View main;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facture);
        Calendar calendar = Calendar.getInstance();
        String dateauj = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        TextView textViewdate = findViewById(R.id.txtviewdate);
        textViewdate.setText(dateauj);
        db = new DatabaseHelper(getApplicationContext());
        nomclient = findViewById(R.id.txtviewnomclient);
        numfact = findViewById(R.id.txtviewnmfacture);
        montant = findViewById(R.id.txtviewmontant);
        btn_facture = findViewById(R.id.btn_facture);
        btn_screen = findViewById(R.id.btn_screen);
    //    imageView = findViewById(R.id.imageViewscreen);
  //      main = findViewById(R.id.main);
        ednumfac = findViewById(R.id.ednumfact);
        spinner_client= findViewById(R.id.spinner);
        spinner_produit = findViewById(R.id.spinnerproduit);
        edquantite = findViewById(R.id.edquantite);
        List<Modelproduit> modproduit = new ArrayList<Modelproduit>();
        List<Modelclient> modclient = new ArrayList<Modelclient>();
      final DatabaseHelper datb   = new DatabaseHelper(getApplicationContext());
      modclient =  datb.getAllClients();
      modproduit = datb.getAllProduits();

        ArrayAdapter<Modelclient> adp = new ArrayAdapter<Modelclient>(getApplicationContext(),android.R.layout.simple_spinner_item,modclient);
        spinner_client.setAdapter(adp);
        final ArrayAdapter<Modelproduit> adp1 = new ArrayAdapter<Modelproduit>(getApplicationContext(),android.R.layout.simple_spinner_item,modproduit);
        spinner_produit.setAdapter(adp1);

        spinner_produit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btn_facture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tot;
                int mont;
                int tva;
                String nameclient= spinner_client.getSelectedItem().toString();
               String nameproduit = spinner_produit.getSelectedItem().toString();
                int quantityproduit=Integer.parseInt(edquantite.getText().toString());
                //float priceproduit=spinner_client.getSelectedItem().toString();
                int numberfacture= Integer.parseInt(ednumfac.getText().toString());
                nomclient.setText(nameclient);

               numfact.setText(String.valueOf(numberfacture));

               tot= quantityproduit*10;
               tva = 20;
               mont =0;

                data.add(nameproduit);
                data1.add(String.valueOf(quantityproduit));
               //data2.add(String.valueOf(quantityproduit));
                data3.add(String.valueOf(tva));
                data4.add(String.valueOf(tot));


                for(int j = 0 ; j<data.size(); j++) {
                    mont = mont + tot;
                    montant.setText(String.valueOf(mont));


                    montant.setText(String.valueOf(mont));
                }


                TableLayout table = findViewById(R.id.tb1);
                TableRow row = new TableRow (getApplicationContext());
                TextView t1 = new TextView (getApplicationContext());
                TextView t2 = new TextView (getApplicationContext());
                TextView t3 = new TextView (getApplicationContext());
                TextView t4 = new TextView (getApplicationContext());
                TextView t5 = new TextView(getApplicationContext());

                String total;


                int sum = 0;
                int sum1 = 0;
                 for(int i = 0 ; i<data.size(); i++){

                      String namepr=data.get(i);
                      String quantitypr=data1.get(i);
                  //String quantitypr=data2.get(i);
                      String ttva=data3.get(i);
                      total = data4.get(i);


                         t1.setText(namepr);
                         t2.setText(quantitypr);
                         t4.setText(ttva);
                         t5.setText(total);


                     sum1 = sum + Integer.parseInt(data3.get(i).toString());
                   sum = sum + Integer.parseInt(data4.get(i).toString());

                 }

                 row.addView(t1);
                 row.addView(t2);
                 row.addView(t3);
                 row.addView(t4);
                 row.addView(t5);
                 table.addView(row);

            }
        });


        btn_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureecran();
            }
        });


    };

    public void captureecran(){
        Date date = new Date();
        CharSequence now = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss",date);
        String filename = Environment.getExternalStorageDirectory() + "/Screenshooter/" + now + ".jpg";

        View root = getWindow().getDecorView();
        root.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(root.getDrawingCache());
        root.setDrawingCacheEnabled(false);

        File file = new File(filename);
        file.getParentFile().mkdirs();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "image/*");
            startActivity(intent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}















