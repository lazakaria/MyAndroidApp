package com.example.gestionproduitsapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProduitListAdapter extends BaseAdapter {

    private Context context;
    private int Layout;
    private ArrayList<Modelproduit> produitList;

    public ProduitListAdapter(Context context, int layout, ArrayList<Modelproduit> produitList) {
        this.context = context;
        Layout = layout;
        this.produitList = produitList;
    }

    @Override
    public int getCount() {
        return produitList.size();
    }

    @Override
    public Object getItem(int position) {
        return produitList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView numtxtview, prixtxtview, designationtxtview, quantitetxtview;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = new ViewHolder();
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(Layout, null);
            holder.numtxtview = row.findViewById(R.id.numtxtview);
            holder.prixtxtview = row.findViewById(R.id.prixtxtview);
            holder.designationtxtview = row.findViewById(R.id.designationtxtview);
            holder.quantitetxtview = row.findViewById(R.id.quantitetxtview);
            holder.imageView = row.findViewById(R.id.imageicone);
            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();

        }
        Modelproduit modelproduit = produitList.get(position);
        holder.numtxtview.setText(String.valueOf(modelproduit.getNumproduit()));
        holder.designationtxtview.setText(modelproduit.getDesignation());
        holder.prixtxtview.setText(String.valueOf(modelproduit.getPrix()));
        holder.quantitetxtview.setText(String.valueOf(modelproduit.getQuantite()));
        byte[] produitImage = modelproduit.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(produitImage,0, produitImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;


    }
}