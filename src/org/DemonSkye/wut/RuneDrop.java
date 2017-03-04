package org.DemonSkye.wut;

import org.DemonSkye.wut.RuneType.RuneRole;

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
        System.out.println("after substr: " + str); //raw string after substr
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
            /*System.out.println("Outputting Hashmap");
            for (String name : statMap.keySet()) {
                String key = name;
                String val = statMap.get(name).toString();
                System.out.println(key + " " + val);
            }*/

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

            Double offset =0.0;
            // Add / Subtract points for quality
            if (rarity.equalsIgnoreCase("Common")){
                offset -= 2.0;
            }
            if (rarity.equalsIgnoreCase("Magic")){
                offset -= 1.0;
            }
            if (rarity.equalsIgnoreCase("Rare")){
                offset += 0.0;
            }
            if (rarity.equalsIgnoreCase("Hero")){
                offset += 2.0;
            }
            if (rarity.equalsIgnoreCase("Legendary")){
                offset += 5.0;
            }



            //AccNuke
            //Bomber
            //Bruiser
            //Healer
            //Raid
            //SlowNuke
            //SlowTank
            //SpdNuke

            List<String> preferredTypes = new ArrayList<>();
            if (type.equalsIgnoreCase("Blade")){
                preferredTypes.add("Bruiser");
                preferredTypes.add("AccNuke");
                preferredTypes.add("SlowNuke");
                preferredTypes.add("SpdNuke");

                if(slot == 1 || slot ==3 || slot == 5) {
                    keep = RuneRole.runeRankingOdd(statMap, implicit,offset+1, preferredTypes,rarity);
                }
                else{
                    keep = RuneRole.runeRankingEven(mainStatType,statMap, implicit, offset,preferredTypes,rarity);
                }
            }

            if (type.equalsIgnoreCase("Despair")){ //give despair +1 because they have lots of end game uses
                preferredTypes.add("AccNuke");
                preferredTypes.add("Bruiser");
                preferredTypes.add("Healer");
                preferredTypes.add("SpdNuke");
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = RuneRole.runeRankingOdd(statMap,implicit, offset + 1, preferredTypes,rarity);
                }
                else{
                    keep = RuneRole.runeRankingEven(mainStatType,statMap,implicit,offset,preferredTypes,rarity);
                }
            }

            if (type.equalsIgnoreCase("Destroy")){
                preferredTypes.add("Healer");
                preferredTypes.add("Bruiser");
                preferredTypes.add("SpdNuke");
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = RuneRole.runeRankingOdd(statMap,implicit, offset, preferredTypes,rarity);
                }
                else{
                    keep = RuneRole.runeRankingEven(mainStatType,statMap,implicit,offset,preferredTypes,rarity);
                }
            }

            if (type.equalsIgnoreCase("Endure")){
                preferredTypes.add("Bruiser");
                preferredTypes.add("Healer");
                preferredTypes.add("Raid");
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = RuneRole.runeRankingOdd(statMap,implicit, offset, preferredTypes,rarity);
                }
                else{
                    keep = RuneRole.runeRankingEven(mainStatType,statMap,implicit,offset,preferredTypes,rarity);
                }
            }

            if (type.equalsIgnoreCase("Energy")){
                preferredTypes.add("Bruiser");
                preferredTypes.add("Healer");
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = RuneRole.runeRankingOdd(statMap,implicit, offset, preferredTypes,rarity);
                }
                else{
                    keep = RuneRole.runeRankingEven(mainStatType,statMap,implicit,offset,preferredTypes,rarity);
                }
            }

            if (type.equalsIgnoreCase("Fatal")){
                preferredTypes.add("Bomber");
                preferredTypes.add("SlowNuke");
                preferredTypes.add("SpdNuke");
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = RuneRole.runeRankingOdd(statMap,implicit, offset, preferredTypes,rarity);
                }
                else{
                    keep = RuneRole.runeRankingEven(mainStatType,statMap,implicit,offset,preferredTypes,rarity);
                }
            }

            if (type.equalsIgnoreCase("Focus")){
                preferredTypes.add("AccNuke");
                preferredTypes.add("Healer");
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = RuneRole.runeRankingOdd(statMap,implicit, offset, preferredTypes,rarity);
                }
                else{
                    keep = RuneRole.runeRankingEven(mainStatType,statMap,implicit,offset,preferredTypes,rarity);
                }
            }

            if (type.equalsIgnoreCase("Guard")){
                preferredTypes.add("Bruiser");
                preferredTypes.add("Healer");
                preferredTypes.add("SlowTank");
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = RuneRole.runeRankingOdd(statMap,implicit, offset, preferredTypes,rarity);
                }
                else{
                    keep = RuneRole.runeRankingEven(mainStatType,statMap,implicit,offset,preferredTypes,rarity);
                }
            }

            if (type.equalsIgnoreCase("Nemesis")){
                preferredTypes.add("AccNuke");
                preferredTypes.add("Bruiser");
                preferredTypes.add("Healer");
                preferredTypes.add("SlowTank");
                preferredTypes.add("SpdNuke");
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = RuneRole.runeRankingOdd(statMap,implicit, offset+4, preferredTypes,rarity);
                }
                else{
                    keep = RuneRole.runeRankingEven(mainStatType,statMap,implicit,offset,preferredTypes,rarity);
                }
            }

            if (type.equalsIgnoreCase("Rage")){
                preferredTypes.add("AccNuke");
                preferredTypes.add("Bruiser");
                preferredTypes.add("SlowNuke");
                preferredTypes.add("SpdNuke");
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = RuneRole.runeRankingOdd(statMap,implicit, offset+3, preferredTypes,rarity);
                }
                else{
                    keep = RuneRole.runeRankingEven(mainStatType,statMap,implicit,offset,preferredTypes,rarity);
                }
            }

            if (type.equalsIgnoreCase("Revenge")){
                preferredTypes.add("Bruiser");
                preferredTypes.add("Raid");
                preferredTypes.add("SlowTank");
                preferredTypes.add("SpdNuke");

                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = RuneRole.runeRankingOdd(statMap,implicit, offset, preferredTypes,rarity);
                }
                else{
                    keep = RuneRole.runeRankingEven(mainStatType,statMap,implicit,offset,preferredTypes,rarity);
                }
            }

            if (type.equalsIgnoreCase("Shield")){
                preferredTypes.add("Bruiser");
                preferredTypes.add("Healer");
                preferredTypes.add("SlowTank");
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = RuneRole.runeRankingOdd(statMap,implicit, offset, preferredTypes,rarity);
                }
                else{
                    keep = RuneRole.runeRankingEven(mainStatType,statMap,implicit,offset,preferredTypes,rarity);
                }
            }

            if (type.equalsIgnoreCase("Swift")){
                preferredTypes.add("Healer");
                preferredTypes.add("Raid");
                preferredTypes.add("SpdNuke");

                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = RuneRole.runeRankingOdd(statMap,implicit, offset+2, preferredTypes,rarity);
                }
                else{
                    keep = RuneRole.runeRankingEven(mainStatType,statMap,implicit,offset,preferredTypes,rarity);
                }
            }

            if (type.equalsIgnoreCase("Vampire")){
                preferredTypes.add("Bruiser");
                preferredTypes.add("Raid");
                preferredTypes.add("SlowNuke");
                preferredTypes.add("SpdNuke");
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = RuneRole.runeRankingOdd(statMap,implicit, offset+1, preferredTypes,rarity);
                }
                else{
                    keep = RuneRole.runeRankingEven(mainStatType,statMap,implicit,offset,preferredTypes,rarity);
                }
            }

            if (type.equalsIgnoreCase("Violent")){
                preferredTypes.add("AccNuke");
                preferredTypes.add("Bruiser");
                preferredTypes.add("Healer");
                preferredTypes.add("Raid");
                preferredTypes.add("SpdNuke");
                preferredTypes.add("SlowNuke");
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = RuneRole.runeRankingOdd(statMap,implicit, offset+4, preferredTypes,rarity);
                }
                else{
                    keep = RuneRole.runeRankingEven(mainStatType,statMap,implicit,offset,preferredTypes,rarity);
                }
            }

            if (type.equalsIgnoreCase("Will")){
                preferredTypes.add("AccNuke");
                preferredTypes.add("Bruiser");
                preferredTypes.add("Healer");
                preferredTypes.add("SpdNuke");
                if(slot == 1 || slot == 3 || slot ==5) {
                    keep = RuneRole.runeRankingOdd(statMap,implicit, offset+2, preferredTypes,rarity);
                }
                else{
                    keep = RuneRole.runeRankingEven(mainStatType,statMap,implicit,offset,preferredTypes,rarity);
                }
            }
        }

        System.out.println("Keep Value:" + keep);
        if (keep > 10.0) {return 2.0; }
        if(keep <10.0 && keep >=7.0) {return 1.0;}
        else{
            return 0.0;
        }
    }
}

