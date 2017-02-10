package org.DemonSkye.wut;

import java.util.HashMap;

/**
 * Created by Damien on 2/8/2017.
 */
public class StatMap {
    public static HashMap<String, Integer> statMapper (String imp, String ms, String s1, String s2, String s3, String s4, String rarity){
        HashMap<String, Integer> mappedValues = new HashMap<>();
        //mappedValues.put(stringifier(ms), valueGrabber(ms));
        //No need to grab main stat since it's static.

        mappedValues.put(stringifier(s1), valueGrabber(s1));
        mappedValues.put(stringifier(s2), valueGrabber(s2));
        if(rarity.equalsIgnoreCase("Hero")){
            mappedValues.put(stringifier(s3), valueGrabber(s3));
        }
        if(rarity.equalsIgnoreCase("Legendary")){
            mappedValues.put(stringifier(s3), valueGrabber(s3));
            mappedValues.put(stringifier(s4), valueGrabber(s4));
        }

        return mappedValues;
    }

    public static String stringifier (String str){
        String strTemp = str.substring(0,str.indexOf(" "));
        str = str.substring(str.indexOf(" "));
        if(str.contains("+")) {
            strTemp += str.substring(1,2); // Get the + and add it to imptemp
        }
        else{
            if (str.contains("Rate") || str.contains("Dmg")){
                strTemp+= str.substring(0, str.indexOf("%")-2);
            }
            strTemp +="%";
        }
        return strTemp;
    }

    public static Integer valueGrabber (String str){
        Integer value;
        if(str.contains("%")){
            str = str.substring(str.indexOf(" ")+1);
            if(str.contains(" ")) {
                str = str.substring(str.indexOf(" ") + 1);
            }
            value = Integer.parseInt(str.substring(0, str.indexOf("%")) );
    }
        else{
            value = Integer.parseInt(str.substring(str.indexOf("+")+1, str.length()) );
        }

        return value;
    }


}
