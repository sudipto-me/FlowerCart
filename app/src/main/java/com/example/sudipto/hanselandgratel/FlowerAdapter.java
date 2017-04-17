package com.example.sudipto.hanselandgratel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.sudipto.hanselandgratel.model.Flowers;
import java.util.List;


/**
 * Created by sudipto on 2/27/2017.
 */

public class FlowerAdapter extends ArrayAdapter<Flowers> {

    private Context context;
    private List<Flowers>flowersList;


    public FlowerAdapter(Context context, int resource,List<Flowers>objects) {
        super(context, resource,objects);
        this.flowersList = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_flower,parent,false);

        //Display flowers name in the text view widget

        Flowers flowers = flowersList.get(position);
        TextView tv = (TextView)view.findViewById(R.id.textView1);

        tv.setText(flowers.getName());

        //Display flower photo in imageview widget

        ImageView image = (ImageView)view.findViewById(R.id.imageView1);
        image.setImageBitmap(flowers.getBitmap());

        return view;

    }


}
