package com.example.gestionproduitsapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ClientListAdapter extends BaseAdapter {


    private Context context;
    private int Layout;
    private ArrayList<Modelclient> clientList;

    public ClientListAdapter(Context context, int layout, ArrayList<Modelclient> clientList) {
        this.context = context;
        Layout = layout;
        this.clientList = clientList;
    }

    @Override
    public int getCount() {
        return clientList.size();
    }

    @Override
    public Object getItem(int position) {
        return clientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imageViewcl;
        TextView nomctxtview, prenomctxtview;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowcl = convertView;
        ViewHolder holder = new ViewHolder();
        if (rowcl == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowcl = inflater.inflate(Layout, null);
            holder.nomctxtview = rowcl.findViewById(R.id.nomcltxtview);
            holder.prenomctxtview = rowcl.findViewById(R.id.prenomtxtview);

            //    holder.imageView = row.findViewById(R.id.imageicone);
            rowcl.setTag(holder);

        } else {
            holder = (ClientListAdapter.ViewHolder) rowcl.getTag();

        }
        Modelclient modelproduit = clientList.get(position);
        holder.nomctxtview.setText(modelproduit.getNomclient());
        holder.prenomctxtview.setText(modelproduit.getPrenomclient());

        // byte[] listImage = modelproduit.getImage();
        // Bitmap bitmap = BitmapFactory.decodeByteArray(listImage,0, listImage.length);
        //holder.imageView.setImageBitmap(bitmap);

        return rowcl;


    }
}
