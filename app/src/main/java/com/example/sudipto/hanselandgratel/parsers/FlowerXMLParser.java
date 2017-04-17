package com.example.sudipto.hanselandgratel.parsers;

import com.example.sudipto.hanselandgratel.model.Flowers;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sudipto on 2/16/2017.
 */

public class FlowerXMLParser {

    public static List<Flowers> parseFeed(String content) {

        try {

            boolean inDataItemTag = false;
            String currentTagName = "";
            Flowers flower = null;
            List<Flowers> flowersList = new ArrayList<>();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(content));

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        currentTagName = parser.getName();
                        if (currentTagName.equals("product")) {
                            inDataItemTag = true;
                            flower = new Flowers();
                            flowersList.add(flower);
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("product")) {
                            inDataItemTag = false;
                        }
                        currentTagName = "";
                        break;

                    case XmlPullParser.TEXT:
                        if (inDataItemTag && flower != null) {
                            switch (currentTagName) {
                                case "productId":
                                    flower.setProductID(Integer.parseInt(parser.getText()));
                                    break;
                                case "name":
                                    flower.setName(parser.getText());
                                    break;
                                case "instructions":
                                    flower.setInstructios(parser.getText());
                                    break;
                                case "category":
                                    flower.setCategory(parser.getText());
                                    break;
                                case "price":
                                    flower.setPrice(Double.parseDouble(parser.getText()));
                                    break;
                                case "photo":
                                    flower.setPhoto(parser.getText());
                                default:
                                    break;
                            }
                        }
                        break;
                }

                eventType = parser.next();

            }

            return flowersList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
