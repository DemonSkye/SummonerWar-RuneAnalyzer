package org.DemonSkye.wut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Damien on 2/7/2017.
 */
public class RuneDrop {
    public static Double runeEval(String str) {

        boolean noImplicit = false;

        Double keep = 0.0;
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
                return 1.0;
            }
            else{
                return 0.0;
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
                        ) {System.out.println("Rejected for MainStat base type: " + mainStat); return 0.0;}
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

            int offset =0;
            // Add / Subtract points for quality
            if (rarity.equalsIgnoreCase("Common")){
                offset -= 2;
            }
            if (rarity.equalsIgnoreCase("Magic")){
                offset -= 1;
            }
            if (rarity.equalsIgnoreCase("Rare")){
                offset += 0;
            }
            if (rarity.equalsIgnoreCase("Hero")){
                offset += 2;
            }
            if (rarity.equalsIgnoreCase("Legendary")){
                offset += 3;
            }


            //SlowNuke
            //SlowTank
            //SpdNuke
            //Bruiser
            //Healer
            //AccNuke
            //Raid
            //Bomber
            List<String> preferredTypes = new ArrayList<>();
            if (type.equalsIgnoreCase("Blade")){
                preferredTypes.add("SlowNuke");
                preferredTypes.add("SpdNuke");
                preferredTypes.add("Bruiser");
                preferredTypes.add("AccNuke");
                if(slot == 1 || slot ==3 || slot == 5) {
                    keep = org.DemonSkye.wut.RuneType.runeRole.runeRankingOdd(statMap, offset, preferredTypes);
                }
            }

            if (type.equalsIgnoreCase("Despair")){ //give despair +1 because they have lots of end game uses
                preferredTypes.add("SpdNuke");
                preferredTypes.add("Bruiser");
                preferredTypes.add("Healer");
                preferredTypes.add("AccNuke");
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = org.DemonSkye.wut.RuneType.runeRole.runeRankingOdd(statMap, offset + 1, preferredTypes);
                }
            }

            if (type.equalsIgnoreCase("Destroy")){
                preferredTypes.add("Healer");
                preferredTypes.add("SpdNuke");
                preferredTypes.add("Bruiser");
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = org.DemonSkye.wut.RuneType.runeRole.runeRankingOdd(statMap, offset, preferredTypes);
                }
            }

            if (type.equalsIgnoreCase("Endure")){
                preferredTypes.add("Healer");
                preferredTypes.add("Raid");
                preferredTypes.add("Bruiser");
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = org.DemonSkye.wut.RuneType.runeRole.runeRankingOdd(statMap, offset, preferredTypes);
                }
            }

            if (type.equalsIgnoreCase("Energy")){
                preferredTypes.add("Healer");
                preferredTypes.add("Bruiser");
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = org.DemonSkye.wut.RuneType.runeRole.runeRankingOdd(statMap, offset, preferredTypes);
                }
            }

            if (type.equalsIgnoreCase("Fatal")){
                preferredTypes.add("SlowNuke");
                preferredTypes.add("SpdNuke");
                preferredTypes.add("Bomber");
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = org.DemonSkye.wut.RuneType.runeRole.runeRankingOdd(statMap, offset, preferredTypes);
                }
            }

            if (type.equalsIgnoreCase("Focus")){
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = org.DemonSkye.wut.RuneType.runeRole.runeRankingOdd(statMap, offset, preferredTypes);
                }
            }

            if (type.equalsIgnoreCase("Guard")){
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = org.DemonSkye.wut.RuneType.runeRole.runeRankingOdd(statMap, offset, preferredTypes);
                }
            }

            if (type.equalsIgnoreCase("Nemesis")){
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = org.DemonSkye.wut.RuneType.runeRole.runeRankingOdd(statMap, offset, preferredTypes);
                }
            }

            if (type.equalsIgnoreCase("Rage")){
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = org.DemonSkye.wut.RuneType.runeRole.runeRankingOdd(statMap, offset, preferredTypes);
                }
            }

            if (type.equalsIgnoreCase("Revenge")){
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = org.DemonSkye.wut.RuneType.runeRole.runeRankingOdd(statMap, offset, preferredTypes);
                }
            }

            if (type.equalsIgnoreCase("Shield")){
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = org.DemonSkye.wut.RuneType.runeRole.runeRankingOdd(statMap, offset, preferredTypes);
                }
            }

            if (type.equalsIgnoreCase("Swift")){
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = org.DemonSkye.wut.RuneType.runeRole.runeRankingOdd(statMap, offset, preferredTypes);
                }
            }

            if (type.equalsIgnoreCase("Vampire")){
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = org.DemonSkye.wut.RuneType.runeRole.runeRankingOdd(statMap, offset, preferredTypes);
                }
            }

            if (type.equalsIgnoreCase("Violent")){
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = org.DemonSkye.wut.RuneType.runeRole.runeRankingOdd(statMap, offset, preferredTypes);
                }
            }

            if (type.equalsIgnoreCase("Will")){
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = org.DemonSkye.wut.RuneType.runeRole.runeRankingOdd(statMap, offset, preferredTypes);
                }
            }
        }
        if (keep > 10.0) {return 2.0; }
        if(keep <10.0 && keep >7.0) {return 1.0;}
        else{
            return 0.0;
        }
    }
}

