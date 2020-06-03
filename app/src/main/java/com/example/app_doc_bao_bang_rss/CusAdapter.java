package com.example.app_doc_bao_bang_rss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CusAdapter extends ArrayAdapter<Docbao> {

    public CusAdapter(Context context, int resource, List<Docbao> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.layout_item, null);
        }
        Docbao p = getItem(position);
        if (p != null) {
            // Anh xa + Gan gia tri
            TextView txtTitle = view.findViewById(R.id.textViewTitle);
            txtTitle.setText(p.title);

            ImageView imageView = view.findViewById(R.id.imgView);

            Picasso.with(getContext()).load(p.imgae).into(imageView);


        }
        return view;
    }


}