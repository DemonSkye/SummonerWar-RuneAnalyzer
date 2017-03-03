package org.DemonSkye.wut.RuneType;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Damien on 2/21/2017.
 */
public class runeRole {
    public static Double runeRankingOdd(HashMap<String, Integer> rune, String implicit, Double offset, List <String> prefTypes, String rarity) {
        Double runeValue = offset;
        String preferredTypes[] = new String[prefTypes.size()];
        prefTypes.toArray(preferredTypes);

        boolean hasSpeed = false;
        boolean slowNuke = false;
        boolean slowTank = false;
        if (rune.containsKey("Spd+") ||  implicit.contains("SPD")) {
            hasSpeed = true;
        }
        else if (rune.containsKey("ATK%") && rune.containsKey("CRI Rate%") && rune.containsKey("CRI Dmg%")) {
            if (rune.get("ATK%") > 5 && rune.get("CRI Rate%") > 4) {
                slowNuke = true;
            }
        } else if (rune.containsKey("DEF%") && rune.containsKey("HP%") && rune.containsKey("Resistance%")) {
            if (rune.get("DEF%") > 5 && rune.get("HP%") > 5 && rune.get("Resistance%") > 5) {
                slowTank = true;
            }
        }


        //If it matches none of the architypes return it as a useless rune.
        if (!( hasSpeed || slowNuke || slowTank)) {
            return 0.0;
        }


        //Always ding flat stats for stealing our gains
        if(rune.containsKey("ATK+")){
            runeValue-=1;
        }
        if (rune.containsKey("DEF+")) {
            runeValue -= 1;
        }
        if (rune.containsKey("HP+")) {
            runeValue -= 1;
        }

        //Determine type of rune.
        //Slow Nuker
        int slowNukeValue = slowNukeCount(rune);
        if(slowNuke){
            //Atk valuation in slow nuke rune
            if (rune.get("ATK%") == 6) {
                runeValue += 2;
            }
            if (rune.get("ATK%") == 7) {
                runeValue += 2.5;
            }
            if (rune.get("ATK%") == 8) {
                runeValue += 4;
            }

            if (rune.get("CRI Rate%") == 4) {
                runeValue += 2;
            }
            if (rune.get("CRI Rate%") == 5) {
                runeValue += 3.5;
            }
            if (rune.get("CRI Rate%") == 6) {
                runeValue += 5;
            }

            if (rune.get("CRI Dmg%") == 4) {
                runeValue += 2;
            }
            if (rune.get("CRI Dmg%") == 5) {
                runeValue += 3;
            }
            if (rune.get("CRI Dmg%") == 6) {
                runeValue += 4;
            }
            if (rune.get("CRI Dmg%") == 7) {
                runeValue += 5;
            }


            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("SlowNuke")) {
                    runeValue += 3;
                }
            }
            //return RuneValue for slow nuker
            return runeValue;
        }


        //Slow Tank
        if (slowTank) {
            if (rune.get("DEF%") == 6) {
                runeValue += 3;
            }
            if (rune.get("DEF%") == 7) {
                runeValue += 4;
            }
            if (rune.get("DEF%") == 8) {
                runeValue += 5;
            }

            if (rune.get("HP%") == 6) {
                runeValue += 3;
            }
            if (rune.get("HP%") == 7) {
                runeValue += 4;
            }
            if (rune.get("HP%") == 8) {
                runeValue += 5;
            }

            if (rune.get("Resistance%") == 6) {
                runeValue += 3;
            }
            if (rune.get("Resistance%") == 7) {
                runeValue += 3.5;
            }
            if (rune.get("Resistance%") == 8) {
                runeValue += 4;
            }

            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("SlowTank")) {
                    runeValue += 3;
                }
            }

            return runeValue;
        }


        ////
        //All remaining values should be for speedy rune sets
        ////

        if (rune.get("SPD+") == 4) {
            runeValue += 3;
        }
        if (rune.get("SPD+") == 5) {
            runeValue += 6;
        }
        if (rune.get("SPD+") == 6) {
            runeValue += 8;
        }


        ////
        //Speedy Nuker -- Spd + One of (Atk / CD) + CR / Speed
        ////

        int speedNuke = spdNukeCount(rune);

        if (speedNuke > 2 || (speedNuke >1 && rarity.equalsIgnoreCase("Rare")) ) {
            runeValue += 2;

            if (rune.containsKey("ATK%")) {
                if (rune.get("ATK%") == 4) {
                    runeValue += 2;
                }
                if (rune.get("ATK%") == 5) {
                    runeValue += 2.5;
                }
                if (rune.get("ATK%") == 6) {
                    runeValue += 3;
                }
                if (rune.get("ATK%") == 7) {
                    runeValue += 3.5;
                }
                if (rune.get("ATK%") == 8) {
                    runeValue += 5;
                }
            }

            if (rune.containsKey("CRI DMG%")) {
                if (rune.get("CRI DMG%") == 4) {
                    runeValue += 2;
                }
                if (rune.get("CRI DMG%") == 5) {
                    runeValue += 2.75;
                }
                if (rune.get("CRI DMG%") == 6) {
                    runeValue += 3.5;
                }
                if (rune.get("CRI DMG%") == 7) {
                    runeValue += 6.5;
                }
            }

            if (rune.get("CRI Rate%") == 4) {
                runeValue += 4;
            }
            if (rune.get("CRI Rate%") == 5) {
                runeValue += 5;
            }
            if (rune.get("CRI Rate%") == 6) {
                runeValue += 7;
            }

            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("SpdNuke")) {
                    runeValue += 3;
                }
            }

            return runeValue;
        }//End Speed Nuker


        ////
        //Def + CR / CD / HP-- Bruiser
        ////
        int bruiser = bruiserCount(rune);
        if (bruiser > 2 || (bruiser >1 && rarity.equalsIgnoreCase("Rare"))) {
            if (rune.get("DEF%") == 4) {
                runeValue += 2;
            }
            if (rune.get("DEF%") == 5) {
                runeValue += 3;
            }
            if (rune.get("DEF%") == 6) {
                runeValue += 3.5;
            }
            if (rune.get("DEF%") == 7) {
                runeValue += 4;
            }
            if (rune.get("DEF%") == 8) {
                runeValue += 5;
            }

            if (rune.get("HP%") == 4) {
                runeValue += 2;
            }
            if (rune.get("HP%") == 5) {
                runeValue += 3;
            }
            if (rune.get("HP%") == 6) {
                runeValue += 3.5;
            }
            if (rune.get("HP%") == 7) {
                runeValue += 4;
            }
            if (rune.get("HP%") == 8) {
                runeValue += 5;
            }

            if (rune.containsKey("CRI Dmg%")) {
                if (rune.get("CRI Dmg%") == 4) {
                    runeValue += 2;
                }
                if (rune.get("CRI Dmg%") == 5) {
                    runeValue += 2.5;
                }
                if (rune.get("CRI Dmg%") == 6) {
                    runeValue += 4;
                }
                if (rune.get("CRI Dmg%") == 7) {
                    runeValue += 5;
                }
            }

            if (rune.containsKey("CRI Rate%")) {
                if (rune.get("CRI Rate%") == 4) {
                    runeValue += 2;
                }
                if (rune.get("CRI Rate%") == 5) {
                    runeValue += 3.5;
                }
                if (rune.get("CRI Rate%") == 6) {
                    runeValue += 5;
                }
            }

            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("Bruiser")) {
                    runeValue += 3;
                }
            }

            return runeValue;
        }// End bruiser


        //
        //Def + Hp /// Hp + Acc + Def-- Healer
        //
        int healer = healerCount(rune);
        if (healer >=1) {
            if (rune.get("DEF%") == 4) {
                runeValue += 3;
            }
            if (rune.get("DEF%") == 5) {
                runeValue += 4;
            }
            if (rune.get("DEF%") == 6) {
                runeValue += 4.5;
            }
            if (rune.get("DEF%") == 7) {
                runeValue += 5;
            }
            if (rune.get("DEF%") == 8) {
                runeValue += 6;
            }

            if (rune.get("HP%") == 4) {
                runeValue += 4;
            }
            if (rune.get("HP%") == 5) {
                runeValue += 5;
            }
            if (rune.get("HP%") == 6) {
                runeValue += 5.5;
            }
            if (rune.get("HP%") == 7) {
                runeValue += 6;
            }
            if (rune.get("HP%") == 8) {
                runeValue += 6.5;
            }

            //Additional love for speed freaks, super-important to healers
            if (rune.get("SPD+") == 4) {
                runeValue += 2;
            }
            if (rune.get("SPD+") == 5) {
                runeValue += 3;
            }
            if (rune.get("SPD+") == 6) {
                runeValue += 4;
            }

            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("Healer")) {
                    runeValue += 3;
                }
            }

            return runeValue;
        }//End healer


        ////
        //Acc / Cr / CD -- acc nuker
        ////
        int accNuke = accNukeCount(rune);
        if (accNuke > 2 || (accNuke >1 && rarity.equalsIgnoreCase("Rare"))) {
            if (rune.get("Accuracy%") == 4) {
                runeValue += 2;
            }
            if (rune.get("Accuracy%") == 5) {
                runeValue += 2.5;
            }
            if (rune.get("Accuracy%") == 6) {
                runeValue += 3;
            }
            if (rune.get("Accuracy%") == 7) {
                runeValue += 3.5;
            }
            if (rune.get("Accuracy%") == 8) {
                runeValue += 4;
            }

            if (rune.containsKey("CRI Rate%")) {
                if (rune.get("CRI Rate%") == 4) {
                    runeValue += 4;
                }
                if (rune.get("CRI Rate%") == 5) {
                    runeValue += 5;
                }
                if (rune.get("CRI Rate%") == 6) {
                    runeValue += 6;
                }
            }
            if (rune.containsKey("CRI Dmg%")) {
                if (rune.get("CRI Dmg%") == 4) {
                    runeValue += 2;
                }
                if (rune.get("CRI Dmg%") == 5) {
                    runeValue += 3;
                }
                if (rune.get("CRI Dmg%") == 6) {
                    runeValue += 4;
                }
                if (rune.get("CRI Dmg%") == 7) {
                    runeValue += 5;
                }
            }
            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("AccNuke")) {
                    runeValue += 3;
                }
            }
            return runeValue;
        }//End acc Nuker

        ////
        //Res + Cr + Cd /// Res + Hp + Def -- Raid monster
        ////

        int raid = raidCount(rune);
        if (raid > 2 || (raid >1 && rarity.equalsIgnoreCase("Rare"))) {
            if (rune.get("Resistance%") == 6) {
                runeValue += 3;
            }
            if (rune.get("Resistance%") == 7) {
                runeValue += 3.5;
            }
            if (rune.get("Resistance%") == 8) {
                runeValue += 4;
            }

            if (rune.containsKey("CRI Rate%") && rune.containsKey("CRI Dmg%")) {
                if (rune.containsKey("CRI Rate%")) {
                    if (rune.get("CRI Rate%") == 4) {
                        runeValue += 4;
                    }
                    if (rune.get("CRI Rate%") == 5) {
                        runeValue += 5;
                    }
                    if (rune.get("CRI Rate%") == 6) {
                        runeValue += 6;
                    }
                }
                if (rune.containsKey("CRI Dmg%")) {
                    if (rune.get("CRI Dmg%") == 4) {
                        runeValue += 2;
                    }
                    if (rune.get("CRI Dmg%") == 5) {
                        runeValue += 3;
                    }
                    if (rune.get("CRI Dmg%") == 6) {
                        runeValue += 4;
                    }
                    if (rune.get("CRI Dmg%") == 7) {
                        runeValue += 5;
                    }
                }
            }

            if (rune.containsKey("hp%") && rune.containsKey("DEF%")) {
                if (rune.get("DEF%") == 4) {
                    runeValue += 3;
                }
                if (rune.get("DEF%") == 5) {
                    runeValue += 4;
                }
                if (rune.get("DEF%") == 6) {
                    runeValue += 4.5;
                }
                if (rune.get("DEF%") == 7) {
                    runeValue += 5;
                }
                if (rune.get("DEF%") == 8) {
                    runeValue += 6;
                }

                if (rune.get("HP%") == 4) {
                    runeValue += 4;
                }
                if (rune.get("HP%") == 5) {
                    runeValue += 5;
                }
                if (rune.get("HP%") == 6) {
                    runeValue += 5.5;
                }
                if (rune.get("HP%") == 7) {
                    runeValue += 6;
                }
                if (rune.get("HP%") == 8) {
                    runeValue += 6.5;
                }
            }


            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("Raid")) {
                    runeValue += 3;
                }
            }

            return runeValue;
        }

        ////
        //Bomber Atk + Acc
        ////
        int bomber = bomberCount(rune);
        if(bomber > 1){
            if(rune.get("ATK%") == 4){
                runeValue += 1;
            }
            if(rune.get("ATK%") == 5){
                runeValue += 2;
            }
            if(rune.get("ATK%") == 6){
                runeValue += 3;
            }
            if(rune.get("ATK%") == 7){
                runeValue += 4;
            }
            if(rune.get("ATK%") == 8){
                runeValue += 6;
            }

            if(rune.get("Accuracy%") == 4){
                runeValue += 1;
            }
            if(rune.get("Accuracy%") == 5){
                runeValue += 2;
            }
            if(rune.get("Accuracy%") == 6){
                runeValue += 3;
            }
            if(rune.get("Accuracy%") == 7){
                runeValue += 4;
            }
            if(rune.get("Accuracy%") == 8){
                runeValue += 6;
            }
        }
        return runeValue;
    }




    ////////
    ////Rune ranking for runes with variable main stats
    ////////


    public static Double runeRankingEven(String mainStat,HashMap<String, Integer> rune, String implicit, Double offset, List <String> prefTypes, String rarity) {
        Double runeValue = offset;
        //
        //Initially reject if it doesn't match slow tank / SlowNuke criteria
        //

        boolean hasSpeed=false, slowNuke=false, slowTank=false;


        if (mainStat.equalsIgnoreCase("SPD") || rune.containsKey("Spd+") ||  implicit.contains("SPD")) {
            hasSpeed = true;
        }
        else if (mainStat.equalsIgnoreCase("ATK") && rune.containsKey("CRI Rate%") && rune.containsKey("CRI Dmg%")) {
            slowNuke = true;
        }
        else if ( (mainStat.equalsIgnoreCase("DEF") ||mainStat.equalsIgnoreCase("HP") ) && (rune.containsKey("HP%")
                || rune.containsKey("DEF%")) && rune.containsKey("Resistance%")) {
            slowTank = true;
        }

        //Shit rune if it doesn't have speed or fit a slow mold.
        if( !(hasSpeed || slowNuke || slowTank))
            return 0.0;


        //Always ding flat stats for stealing our gains
        if (rune.containsKey("DEF+")) {
            runeValue -= 1.5;
        }
        if(rune.containsKey("ATK+")){
            runeValue-=1.5;
        }
        if(rune.containsKey("HP+")){
            runeValue -=1.5;
        }

        if(rune.containsKey("SPD+")) {
            if (rune.get("SPD+") == 4) {
                runeValue += 3;
            }
            if (rune.get("SPD+") == 5) {
                runeValue += 6;
            }
            if (rune.get("SPD+") == 6) {
                runeValue += 8;
            }
        }


        ////
        //AccNuke: Atk / Acc / Cr / CD
        ////

        int accNuke = accNukeCountMain(mainStat,rune);
        if(accNuke >=2 || accNuke >=1 && rarity.equalsIgnoreCase("Rare")){
            if(rune.containsKey("Accuracy%") && (rune.containsKey("CRI Rate%") || rune.containsKey("CRI Dmg%")) && hasSpeed){
                if(rune.get("Accuracy%") == 4 ){
                    runeValue += 2;
                }
                if(rune.get("Accuracy%") == 5 ){
                    runeValue += 2.5;
                }
                if(rune.get("Accuracy%") == 6 ){
                    runeValue += 3;
                }
                if(rune.get("Accuracy%") == 7 ){
                    runeValue += 3.5;
                }
                if(rune.get("Accuracy%") == 8 ){
                    runeValue += 5;
                }
            }

            if (rune.containsKey("CRI Rate%")) {
                if (rune.get("CRI Rate%") == 4) {
                    runeValue += 4;
                }
                if (rune.get("CRI Rate%") == 5) {
                    runeValue += 5;
                }
                if (rune.get("CRI Rate%") == 6) {
                    runeValue += 6;
                }
            }
            if (rune.containsKey("CRI Dmg%")) {
                if (rune.get("CRI Dmg%") == 4) {
                    runeValue += 2;
                }
                if (rune.get("CRI Dmg%") == 5) {
                    runeValue += 3;
                }
                if (rune.get("CRI Dmg%") == 6) {
                    runeValue += 4;
                }
                if (rune.get("CRI Dmg%") == 7) {
                    runeValue += 5;
                }
            }
            for (String s : prefTypes) {
                if (s.equalsIgnoreCase("AccNuke")) {
                    runeValue += 3;
                }
            }
            return runeValue;
        }

        ////
        //Bomber: Atk + Acc
        ////
        int bomber = bomberCountMain(mainStat,rune);
        if(bomber >=2 || bomber >=1 && rarity.equalsIgnoreCase("Rare")){
            if(rune.containsKey("Accuracy%")&& hasSpeed){
                if(rune.get("Accuracy%") == 4 ){
                    runeValue += 6;
                }
                if(rune.get("Accuracy%") == 5 ){
                    runeValue += 7.5;
                }
                if(rune.get("Accuracy%") == 6 ){
                    runeValue += 8;
                }
                if(rune.get("Accuracy%") == 7 ){
                    runeValue += 9.5;
                }
                if(rune.get("Accuracy%") == 8 ){
                    runeValue += 10;
                }
            }
            for (String s : prefTypes) {
                if (s.equalsIgnoreCase("Bomber")) {
                    runeValue += 3;
                }
            }
            return runeValue;
        }

        ////
        //Bruiser: DEF | HP + CR / CD
        ////
        int bruiser = bruiserCountMain(mainStat,rune);
        if(bruiser >=2 || bruiser >=1 && rarity.equalsIgnoreCase("Rare")) {
            if ((rune.containsKey("CRI Rate%") || rune.containsKey("CRI Dmg%")) &&
                    (rune.containsKey("HP%") || rune.containsKey("DEF%")) && hasSpeed) {

                if (rune.containsKey("CRI Rate%")) {
                    if (rune.get("CRI Rate%") == 4) {
                        runeValue += 4;
                    }
                    if (rune.get("CRI Rate%") == 5) {
                        runeValue += 5;
                    }
                    if (rune.get("CRI Rate%") == 6) {
                        runeValue += 6;
                    }
                }
                if (rune.containsKey("CRI Dmg%")) {
                    if (rune.get("CRI Dmg%") == 4) {
                        runeValue += 2;
                    }
                    if (rune.get("CRI Dmg%") == 5) {
                        runeValue += 3;
                    }
                    if (rune.get("CRI Dmg%") == 6) {
                        runeValue += 4;
                    }
                    if (rune.get("CRI Dmg%") == 7) {
                        runeValue += 5;
                    }
                }
                if (rune.containsKey("DEF%")) {
                    if (rune.get("DEF%") == 4) {
                        runeValue += 3;
                    }
                    if (rune.get("DEF%") == 5) {
                        runeValue += 4;
                    }
                    if (rune.get("DEF%") == 6) {
                        runeValue += 4.5;
                    }
                    if (rune.get("DEF%") == 7) {
                        runeValue += 5;
                    }
                    if (rune.get("DEF%") == 8) {
                        runeValue += 6;
                    }
                }

                if (rune.containsKey("HP%")) {
                    if (rune.get("HP%") == 4) {
                        runeValue += 4;
                    }
                    if (rune.get("HP%") == 5) {
                        runeValue += 5;
                    }
                    if (rune.get("HP%") == 6) {
                        runeValue += 5.5;
                    }
                    if (rune.get("HP%") == 7) {
                        runeValue += 6;
                    }
                    if (rune.get("HP%") == 8) {
                        runeValue += 6.5;
                    }
                }
            }
            for (String s : prefTypes) {
                if (s.equalsIgnoreCase("Bruiser")) {
                    runeValue += 3;
                }
            }
            return runeValue;
        }


        ////
        //Healer: Def + Hp /// Hp + Acc + Def
        ////
        int healer = healerCountMain(mainStat,rune);
        if(healer >=2 || healer >=1 && rarity.equalsIgnoreCase("Rare")){
            if( rune.containsKey("DEF%") || rune.containsKey("HP%") && hasSpeed){

                if(rune.get("Accuracy%") == 4 ){
                    runeValue += 2;
                }
                if(rune.get("Accuracy%") == 5 ){
                    runeValue += 3;
                }
                if(rune.get("Accuracy%") == 6 ){
                    runeValue += 3.5;
                }
                if(rune.get("Accuracy%") == 7 ){
                    runeValue += 4;
                }
                if(rune.get("Accuracy%") == 8 ){
                    runeValue += 5;
                }
                if (rune.containsKey("DEF%")) {
                    if (rune.get("DEF%") == 4) {
                        runeValue += 3;
                    }
                    if (rune.get("DEF%") == 5) {
                        runeValue += 4;
                    }
                    if (rune.get("DEF%") == 6) {
                        runeValue += 4.5;
                    }
                    if (rune.get("DEF%") == 7) {
                        runeValue += 5;
                    }
                    if (rune.get("DEF%") == 8) {
                        runeValue += 6;
                    }
                }

                if (rune.containsKey("HP%")) {
                    if (rune.get("HP%") == 4) {
                        runeValue += 4;
                    }
                    if (rune.get("HP%") == 5) {
                        runeValue += 5;
                    }
                    if (rune.get("HP%") == 6) {
                        runeValue += 5.5;
                    }
                    if (rune.get("HP%") == 7) {
                        runeValue += 6;
                    }
                    if (rune.get("HP%") == 8) {
                        runeValue += 6.5;
                    }
                }
            }
            for (String s : prefTypes) {
                if (s.equalsIgnoreCase("Healer")) {
                    runeValue += 3;
                }
            }
            return runeValue;
        }


        ////
        //Raid: Res + Cr + Cd /// Res + Hp + Def
        ////
        int raid = raidCountMain(mainStat,rune);
        if(raid >=2 || raid >=1 && rarity.equalsIgnoreCase("Rare")) {
            if (rune.containsKey("Resistance%") && ((rune.containsKey("CRI Rate%") && rune.containsKey("CRI Dmg%")) ||
                    (rune.containsKey("HP%") || rune.containsKey("DEF%"))) && hasSpeed) {
                if (rune.get("Resistance%") == 4) {
                    runeValue += 3;
                }
                if (rune.get("Resistance%") == 5) {
                    runeValue += 3;
                }
                if (rune.get("Resistance%") == 6) {
                    runeValue += 3;
                }
                if (rune.get("Resistance%") == 7) {
                    runeValue += 3.5;
                }
                if (rune.get("Resistance%") == 8) {
                    runeValue += 4;
                }

                if (rune.containsKey("DEF%")) {
                    if (rune.get("DEF%") == 4) {
                        runeValue += 3;
                    }
                    if (rune.get("DEF%") == 5) {
                        runeValue += 4;
                    }
                    if (rune.get("DEF%") == 6) {
                        runeValue += 4.5;
                    }
                    if (rune.get("DEF%") == 7) {
                        runeValue += 5;
                    }
                    if (rune.get("DEF%") == 8) {
                        runeValue += 6;
                    }
                }

                if (rune.containsKey("HP%")) {
                    if (rune.get("HP%") == 4) {
                        runeValue += 4;
                    }
                    if (rune.get("HP%") == 5) {
                        runeValue += 5;
                    }
                    if (rune.get("HP%") == 6) {
                        runeValue += 5.5;
                    }
                    if (rune.get("HP%") == 7) {
                        runeValue += 6;
                    }
                    if (rune.get("HP%") == 8) {
                        runeValue += 6.5;
                    }
                }

                if (rune.containsKey("CRI Rate%")) {
                    if (rune.get("CRI Rate%") == 4) {
                        runeValue += 4;
                    }
                    if (rune.get("CRI Rate%") == 5) {
                        runeValue += 5;
                    }
                    if (rune.get("CRI Rate%") == 6) {
                        runeValue += 6;
                    }
                }
                if (rune.containsKey("CRI Dmg%")) {
                    if (rune.get("CRI Dmg%") == 4) {
                        runeValue += 2;
                    }
                    if (rune.get("CRI Dmg%") == 5) {
                        runeValue += 3;
                    }
                    if (rune.get("CRI Dmg%") == 6) {
                        runeValue += 4;
                    }
                    if (rune.get("CRI Dmg%") == 7) {
                        runeValue += 5;
                    }
                }
            }
            for (String s : prefTypes) {
                if (s.equalsIgnoreCase("Raid")) {
                    runeValue += 3;
                }
            }
            return runeValue;
        }


        ////
        //SlowNuke: Atk / CR / CD
        ////

        if(slowNuke){
            if(rune.get("CRI Rate%") == 4 ){
                runeValue += 4;
            }
            if(rune.get("CRI Rate%") == 5 ){
                runeValue += 5;
            }
            if(rune.get("CRI Rate%") == 6 ){
                runeValue += 7;
            }

            if(rune.get("CRI Dmg%") == 4 ){
                runeValue += 4;
            }
            if(rune.get("CRI Dmg%") == 5 ){
                runeValue += 5;
            }
            if(rune.get("CRI Dmg%") == 6 ){
                runeValue += 6;
            }
            if(rune.get("CRI Dmg%") == 7 ){
                runeValue += 7;
            }
            for (String s : prefTypes) {
                if (s.equalsIgnoreCase("SlowNuke")) {
                    runeValue += 3;
                }
            }
            return runeValue;
        }


        ////
        //SlowTank: Def + Hp + Res
        ////

        if(slowTank){
            if (rune.get("DEF%") == 4) {
                runeValue += 3;
            }
            if (rune.get("DEF%") == 5) {
                runeValue += 4;
            }
            if (rune.get("DEF%") == 6) {
                runeValue += 4.5;
            }
            if (rune.get("DEF%") == 7) {
                runeValue += 5;
            }
            if (rune.get("DEF%") == 8) {
                runeValue += 6;
            }

            if (rune.get("HP%") == 4) {
                runeValue += 4;
            }
            if (rune.get("HP%") == 5) {
                runeValue += 5;
            }
            if (rune.get("HP%") == 6) {
                runeValue += 5.5;
            }
            if (rune.get("HP%") == 7) {
                runeValue += 6;
            }
            if (rune.get("HP%") == 8) {
                runeValue += 6.5;
            }
            if (rune.get("Resistance%") == 4) {
                runeValue += 3;
            }
            if (rune.get("Resistance%") == 5) {
                runeValue += 4;
            }
            if (rune.get("Resistance%") == 6) {
                runeValue += 4.5;
            }
            if (rune.get("Resistance%") == 7) {
                runeValue += 5;
            }
            if (rune.get("Resistance%") == 8) {
                runeValue += 6;
            }
            for (String s : prefTypes) {
                if (s.equalsIgnoreCase("SlowTank")) {
                    runeValue += 3;
                }
            }
            return runeValue;
        }

        ////
        //SpdNuke: Atk | Spd // CD / CR / (Atk|Spd)
        ////
        int speedNuke = spdNukeCountMain(mainStat,rune);
        if(speedNuke >=2 || speedNuke >=1 && rarity.equalsIgnoreCase("Rare")){
            if(rune.containsKey("CRI Rate%") && rune.containsKey("CRI Dmg%") && hasSpeed){
                if(rune.containsKey("ATK%")){
                    if (rune.get("ATK%") == 4) {
                        runeValue += 3;
                    }
                    if (rune.get("ATK%") == 5) {
                        runeValue += 4;
                    }
                    if (rune.get("ATK%") == 6) {
                        runeValue += 4.5;
                    }
                    if (rune.get("ATK%") == 7) {
                        runeValue += 5;
                    }
                    if (rune.get("ATK%") == 8) {
                        runeValue += 6;
                    }
                }

                if(rune.get("CRI Rate%") == 4 ){
                    runeValue += 4;
                }
                if(rune.get("CRI Rate%") == 5 ){
                    runeValue += 5;
                }
                if(rune.get("CRI Rate%") == 6 ){
                    runeValue += 7;
                }

                if(rune.get("CRI Dmg%") == 4 ){
                    runeValue += 4;
                }
                if(rune.get("CRI Dmg%") == 5 ){
                    runeValue += 5;
                }
                if(rune.get("CRI Dmg%") == 6 ){
                    runeValue += 6;
                }
                if(rune.get("CRI Dmg%") == 7 ){
                    runeValue += 7;
                }
            }
            for (String s : prefTypes) {
                if (s.equalsIgnoreCase("SpdNuke")) {
                    runeValue += 3;
                }
            }
            return runeValue;
        }
        return runeValue;
    }


    //AccNuke Counters
    public static int accNukeCountMain(String mainStat,HashMap<String, Integer> rune){
        //AccNuke: Atk / Acc / Cr / CD
        int match = 0;
        if (mainStat.equalsIgnoreCase("ATK")){
            if(rune.containsKey("Accuracy%")){
                match += 1;
            }
            if(rune.containsKey("CRI Rate%")){
                match += 1;
            }
            if(rune.containsKey("CRI Dmg%")){
                match+=1;
            }
        }
        return match;
    }
    public static int accNukeCount(HashMap<String, Integer> rune){
        //AccNuke: Atk / Acc / Cr / CD
        int match = 0;

        if(rune.containsKey("Accuracy%")){
            match += 1;
        }
        if(rune.containsKey("ATK%")){
            match+=1;
        }
        if(rune.containsKey("CRI Rate%")){
            match += 1;
        }
        if(rune.containsKey("CRI Dmg%")){
            match+=1;
        }

        return match;
    }



    //Bomber
    public static int bomberCountMain(String mainStat,HashMap<String, Integer> rune){
        //Bomber: Atk + Acc
        int match = 0;
        if (mainStat.equalsIgnoreCase("ATK")){
            if(rune.containsKey("Accuracy%")){
                match += 1;
            }
            if(rune.containsKey("CRI Rate%")){
                match += 1;
            }
            if(rune.containsKey("CRI Dmg%")){
                match+=1;
            }
        }
        return match;
    }
    public static int bomberCount(HashMap<String, Integer> rune){
        //Bomber: Atk + Acc
        int match = 0;
        if(rune.containsKey("Accuracy%")){
            match += 1;
        }
        if(rune.containsKey("ATK%")){
            match += 1;
        }
        if(rune.containsKey("CRI Rate%")){
            match += 1;
        }
        if(rune.containsKey("CRI Dmg%")){
            match+=1;
        }
        return match;
    }


    //bruiser
    public static int bruiserCountMain(String mainStat,HashMap<String, Integer> rune){
        //Bruiser: DEF | HP + CR / CD
        int match = 0;
        if(mainStat.equalsIgnoreCase("DEF")|| mainStat.equalsIgnoreCase("HP")){
            if(rune.containsKey("CRI Rate%")){
                match += 1;
            }
            if(rune.containsKey("CRI Dmg%")){
                match+=1;
            }
        }

        return match;
    }
    public static int bruiserCount(HashMap<String, Integer> rune){
        //Bruiser: DEF | HP + CR / CD
        int match = 0;
        if(rune.containsKey("HP%")){
            match += 1;
        }
        if(rune.containsKey("DEF%")){
            match += 1;
        }
        if(rune.containsKey("CRI Rate%")){
            match += 1;
        }
        if(rune.containsKey("CRI Dmg%")){
            match+=1;
        }
        return match;
    }
    public static int healerCountMain(String mainStat,HashMap<String, Integer> rune){
        //Healer: Def + Hp /// Hp + Acc + Def
        int match = 0;
        if(mainStat.equalsIgnoreCase("HP") || mainStat.equalsIgnoreCase("SPD") || mainStat.equalsIgnoreCase("DEF")){
            if(rune.containsKey("HP%")){
                match += 1;
            }
            if(rune.containsKey("DEF%")){
                match += 1;
            }
            if(rune.containsKey("ACC%")){
                match += 1;
            }
        }
        return match;
    }
    public static int healerCount(HashMap<String, Integer> rune){
        //Healer: Def + Hp /// Hp + Acc + Def
        int match = 0;
        if(rune.containsKey("HP%")){
            match += 1;
        }
        if(rune.containsKey("DEF%")){
            match += 1;
        }
        if(rune.containsKey("ACC%")){
            match += 1;
        }
        return match;
    }
    public static int raidCountMain(String mainStat,HashMap<String, Integer> rune){
        //Raid: Res + Cr + Cd /// Res + Hp + Def
        int match = 0;
        if (mainStat.equalsIgnoreCase("HP") || mainStat.equalsIgnoreCase("DEF") || mainStat.equalsIgnoreCase("SPD")){
            if(rune.containsKey("HP%")){
                match += 1;
            }
            if(rune.containsKey("DEF%")){
                match += 1;
            }
            if(rune.containsKey("Resistance%")){
                match += 1;
            }
        }
        return match;
    }
    public static int raidCount(HashMap<String, Integer> rune){
        //Raid: Res + Cr + Cd /// Res + Hp + Def
        int match = 0;
        if(rune.containsKey("HP%")){
            match += 1;
            if(rune.containsKey("CRI Rate%")){
                match += 1;
            }
            if(rune.containsKey("CRI Dmg%")){
                match += 1;
            }
            if(rune.containsKey("Resistance%")){
                match += 1;
            }
        }
        if(rune.containsKey("DEF%")){
            match += 1;
            if(rune.containsKey("CRI Rate%")){
                match += 1;
            }
            if(rune.containsKey("CRI Dmg%")){
                match += 1;
            }
            if(rune.containsKey("Resistance%")){
                match += 1;
            }
        }
        return match;
    }

    public static int slowNukeCountMain(String mainStat,HashMap<String, Integer> rune){
        //SlowNuke: Atk / CR / CD
        int match = 0;
        if(mainStat.equalsIgnoreCase("ATK")){
            if(rune.containsKey("CRI Rate%")){
                match += 1;
            }
            if(rune.containsKey("CRI Dmg%")){
                match+=1;
            }
        }

        return match;
    }
    public static int slowNukeCount(HashMap<String, Integer> rune){
        //SlowNuke: Atk / CR / CD
        int match = 0;
        if(rune.containsKey("ATK%")){
            match += 1;
        }
        if(rune.containsKey("CRI Rate%")){
            match += 1;
        }
        if(rune.containsKey("CRI Dmg%")){
            match += 1;
        }
        return match;
    }
    public static int slowTankCountMain(String mainStat,HashMap<String, Integer> rune){
        //SlowTank: Def + Hp + Res
        int match = 0;
        if(mainStat.equalsIgnoreCase("DEF")){
            if(rune.containsKey("HP%")){
                match+=1;
            }
            if(rune.containsKey("Resistance%")){
                match+=1;
            }
        }
        if(mainStat.equalsIgnoreCase("HP")){
            if(rune.containsKey("DEF%")){
                match+=1;
            }
            if(rune.containsKey("Resistance%")){
                match+=1;
            }
        }
        return match;
    }
    public static int slowTankCount(HashMap<String, Integer> rune){
        //SlowTank: Def + Hp + Res
        int match = 0;
        if(rune.containsKey("HP%")){
            match += 1;
        }
        if(rune.containsKey("DEF%")){
            match += 1;
        }
        if(rune.containsKey("Resistance%")){
            match += 1;
        }
        return match;
    }
    public static int spdNukeCountMain(String mainStat,HashMap<String, Integer> rune){
        //SpdNuke: Atk | Spd // CD / CR / (Atk|Spd)
        int match = 0;
        if(mainStat.equalsIgnoreCase("SPD") || mainStat.equalsIgnoreCase("ATK")){
            if(rune.containsKey("SPD")){
                match+=1;
            }
            if(rune.containsKey("ATK%")){
                match+=1;
            }
            if(rune.containsKey("CRI Dmg%")){
                match+=1;
            }
            if(rune.containsKey("CRI Rate%")){
                match+=1;
            }
        }

        return match;
    }
    public static int spdNukeCount(HashMap<String, Integer> rune){
        //SpdNuke: Atk | Spd // CD / CR / (Atk|Spd)
        int match = 0;
        if(rune.containsKey("SPD")){
            match+=1;
        }
        if(rune.containsKey("ATK%")){
            match+=1;
        }
        if(rune.containsKey("CRI Dmg%")){
            match+=1;
        }
        if(rune.containsKey("CRI Rate%")){
            match+=1;
        }
        return match;
    }
}