package com.example.sudipto.hanselandgratel.parsers;

import com.example.sudipto.hanselandgratel.model.Flowers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sudipto on 2/17/2017.
 */

public class FlowerJSONParser {

    public static List<Flowers>parseFeed(String content){

        // to parse the data from the json file

        try{
            //array starts with '['
            JSONArray ar = new JSONArray(content);
            List<Flowers>flowersList = new ArrayList<>();

            for(int i=0;i<ar.length();i++){

                //objects start with '{'

                JSONObject obj = ar.getJSONObject(i);
                Flowers flowers = new Flowers();

                flowers.setProductID(obj.getInt("productId"));
                flowers.setName(obj.getString("name"));
                flowers.setCategory(obj.getString("category"));
                flowers.setInstructios(obj.getString("instructions"));
                flowers.setPhoto(obj.getString("photo"));
                flowers.getPrice(obj.getDouble("price"));

                flowersList.add(flowers);
            }

            return flowersList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }
}
