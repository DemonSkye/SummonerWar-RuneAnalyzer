package org.DemonSkye.wut;

import java.util.HashMap;

/**
 * Created by Damien on 2/7/2017.
 */
public class RuneDrop {
    public static int runeEval(String str) {

        boolean noImplicit = false;

        int keep = 0;
        //keep 0 = no, keep 1 = maybe, keep 2 = yes.


        str = str.substring(str.indexOf("Rune,") + 5);
        //System.out.println("after substr: " + str); //raw string after substr
        Integer grade = Integer.parseInt(str.substring(0, 1));
        str = str.substring(str.indexOf(",") + 1);
        System.out.println("Grade: " + grade);

        Integer value = Integer.parseInt(str.substring(0, str.indexOf(",")));
        str = str.substring(str.indexOf(",") + 1);
        System.out.println("Value: " + value);


        String type = str.substring(0, str.indexOf(","));
        str = str.substring(str.indexOf(",") + 1);
        System.out.println("Type: " + type);

        Double efficiency = Double.parseDouble(str.substring(0, str.indexOf(",") - 1));
        str = str.substring(str.indexOf(",") + 1);
        System.out.println("Efficiency: " + efficiency);

        Integer slot = Integer.parseInt(str.substring(0, str.indexOf(",")));
        str = str.substring(str.indexOf(",") + 1);
        System.out.println("Slot: " + slot);


        String rarity = str.substring(0, str.indexOf(","));
        str = str.substring(str.indexOf(",") + 1);
        System.out.println("Rarity: " + rarity);


        String mainStat = str.substring(0, str.indexOf(","));
        str = str.substring(str.indexOf(",") + 1);
        System.out.println("MainStat: " + mainStat);

        String implicit = str.substring(0, str.indexOf(","));
        if (implicit.isEmpty()) {
            noImplicit = true;
            System.out.println("No Substat");
        } else {
            System.out.println("Implicit: " + implicit);
        }
        str = str.substring(str.indexOf(",") + 1);

        String substat1 = str.substring(0, str.indexOf(","));
        str = str.substring(str.indexOf(",") + 1);
        System.out.println("Substat1: " + substat1);

        String substat2 = str.substring(0, str.indexOf(","));
        str = str.substring(str.indexOf(",") + 1);
        System.out.println("Substat2: " + substat2);


        String substat3 = "";
        String substat4 = "";
        if (rarity.equalsIgnoreCase("Hero") || rarity.equalsIgnoreCase("Legendary")) {
            substat3 = str.substring(0, str.indexOf(","));
            str = str.substring(str.indexOf(",") + 1);
            System.out.println("Substat3: " + substat3);
        }

        if (rarity.equalsIgnoreCase("Legendary")) {
            substat4 = str.substring(0, str.length());
            System.out.println("Substat4: " + substat4);
        }

        //System.out.println("Grade: " + grade );
        //Only get 5 * legendaries with speed
        if (grade ==5) {
            if (!str.contains("Legendary") && str.contains("SPD")) {
                return 0;
            }
            else{
                return 1;
            }
        }


        if (grade == 6) {
            //Don't bother if main stat is rubbish
            // Shit that we never, ever want.
            if(slot == 2 || slot == 4 || slot == 6) {
                if (mainStat.contains("DEF +") ||
                    mainStat.contains("ATK +") ||
                    mainStat.contains("HP +") ||
                    mainStat.contains("Resist")
                ) {System.out.println("Rejected for base type: " + mainStat); return 0;}
            }

            HashMap<String, Integer> statMap;
            if (noImplicit) {
                statMap = StatMap.statMapper("", mainStat, substat1, substat2, substat3, substat4, rarity);
            }
            else{
                statMap = StatMap.statMapper(implicit, mainStat, substat1, substat2, substat3, substat4, rarity);
            }


            //Debug for contents of hashmap
            System.out.println("Outputting Hashmap");
            for (String name : statMap.keySet()) {
                String key = name;
                String val = statMap.get(name).toString();
                System.out.println(key + " " + val);
            }

            String mainStatType = "";
            if (mainStat.contains("HP")){
                mainStatType = "HP";
            }
            if (mainStat.contains("DEF")){
                mainStatType = "DEF";
            }
            if (mainStat.contains("ATK")){
                mainStatType = "ATK";
            }
            if (mainStat.contains("SPD")){
                mainStatType = "SPD";
            }
            if (mainStat.contains("CRI D")){
                mainStatType = "CRI D";
            }
            if (mainStat.contains("CRI R")){
                mainStatType = "CRI R";
            }
            if (mainStat.contains("+") && !mainStat.contains("SPD")){
                mainStatType = "Flat";
            }
            if (type.equalsIgnoreCase("Blade")){
                keep = org.DemonSkye.wut.RuneType.Blade.evalRune(statMap, rarity, mainStatType);
                return keep;
            }

            if (type.equalsIgnoreCase("Despair")){
                keep = org.DemonSkye.wut.RuneType.Despair.evalRune(statMap, rarity, mainStatType);
                return keep;
            }

            if (type.equalsIgnoreCase("Destroy")){
                keep = org.DemonSkye.wut.RuneType.Destroy.evalRune(statMap, rarity, mainStatType);
                return keep;
            }

            if (type.equalsIgnoreCase("Endure")){
                keep = org.DemonSkye.wut.RuneType.Endure.evalRune(statMap, rarity, mainStatType);
                return keep;
            }

            if (type.equalsIgnoreCase("Energy")){
                keep = org.DemonSkye.wut.RuneType.Blade.evalRune(statMap, rarity, mainStatType);
                return keep;
            }

            if (type.equalsIgnoreCase("Fatal")){
                keep = org.DemonSkye.wut.RuneType.Blade.evalRune(statMap, rarity, mainStatType);
                return keep;
            }

            if (type.equalsIgnoreCase("Focus")){
                keep = org.DemonSkye.wut.RuneType.Blade.evalRune(statMap, rarity, mainStatType);
                return keep;
            }

            if (type.equalsIgnoreCase("Guard")){
                keep = org.DemonSkye.wut.RuneType.Blade.evalRune(statMap, rarity, mainStatType);
                return keep;
            }

            if (type.equalsIgnoreCase("Nemesis")){
                keep = org.DemonSkye.wut.RuneType.Blade.evalRune(statMap, rarity, mainStatType);
                return keep;
            }

            if (type.equalsIgnoreCase("Rage")){
                keep = org.DemonSkye.wut.RuneType.Blade.evalRune(statMap, rarity, mainStatType);
                return keep;
            }

            if (type.equalsIgnoreCase("Revenge")){
                keep = org.DemonSkye.wut.RuneType.Blade.evalRune(statMap, rarity, mainStatType);
                return keep;
            }

            if (type.equalsIgnoreCase("Shield")){
                keep = org.DemonSkye.wut.RuneType.Blade.evalRune(statMap, rarity, mainStatType);
                return keep;
            }

            if (type.equalsIgnoreCase("Swift")){
                keep = org.DemonSkye.wut.RuneType.Blade.evalRune(statMap, rarity, mainStatType);
                return keep;
            }

            if (type.equalsIgnoreCase("Vampire")){
                keep = org.DemonSkye.wut.RuneType.Blade.evalRune(statMap, rarity, mainStatType);
                return keep;
            }

            if (type.equalsIgnoreCase("Violent")){
                keep = org.DemonSkye.wut.RuneType.Blade.evalRune(statMap, rarity, mainStatType);
                return keep;
            }

            if (type.equalsIgnoreCase("Will")){
                keep = org.DemonSkye.wut.RuneType.Blade.evalRune(statMap, rarity, mainStatType);
                return keep;
            }



        }
        return keep;
    }
}

