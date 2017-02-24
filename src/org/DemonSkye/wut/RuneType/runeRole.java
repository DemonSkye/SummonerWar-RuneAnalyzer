package org.DemonSkye.wut.RuneType;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Damien on 2/21/2017.
 */
public class runeRole {
    public static Double runeRankingOdd(HashMap<String, Integer> rune, Double offset, List <String> prefTypes) {
        Double runeValue = offset;
        String preferredTypes[] = new String[prefTypes.size()];
        prefTypes.toArray(preferredTypes);

        //All require speed except slow nuker && slow Tank
        //Nuker -- Atk / Cr / CD
        //Def + CR / CD / HP-- Bruiser
        //Def + Hp /// Hp + Acc + Def-- Healer
        //Acc / Cr / CD -- acc nuker
        //Res + Cr + Cd /// Res + Hp + Def -- Raid monster

        //Def + Hp + Res -- R5 Slow Tank

        ////
        //Att+cr/cd -- Slow Nuke
        ////
        boolean hasSpeed = false;
        boolean slowNuke = false;
        boolean slowTank = false;
        if (rune.containsKey("Spd+")) {
            if (rune.get("Spd+") > 3) {
                hasSpeed = true;
            } else if (rune.containsKey("ATK%") && rune.containsKey("CRI Rate%") && rune.containsKey("CRI Dmg%")) {
                if (rune.get("ATK%") > 5 && rune.get("CRI Rate%") > 4) {
                    slowNuke = true;
                }
            } else if (rune.containsKey("DEF%") && rune.containsKey("HP%") && rune.containsKey("Resistance%")) {
                if (rune.get("DEF%") > 5 && rune.get("HP%") > 5 && rune.get("Resistance%") > 5) {
                    slowTank = true;
                }
            }
        }


        //If it matches none of the architypes return it as a useless rune.
        if (!( hasSpeed || slowNuke || slowTank)) {
            return 0.0;
        }


        //Always ding flat stats for stealing our gains
        if (rune.containsKey("DEF+") || rune.containsKey("ATK+") || rune.containsKey("HP+")) {
            runeValue -= 2;
        }

        //Determine type of rune.
        //Slow Nuker
        if (rune.containsKey("ATK%") && rune.containsKey("CRI Rate%") && rune.containsKey("CRI Dmg%") && slowNuke) { //Basic slow Nuker
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

        if ((rune.containsKey("ATK%") || rune.containsKey("CRI Dmg%")) && rune.containsKey("CRI Rate%") && hasSpeed) {
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

        if (rune.containsKey("DEF%") && (rune.containsKey("CRI Rate%") || rune.containsKey("CRI Dmg%")) && rune.containsKey("HP%")) {
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

        if (rune.containsKey("DEF%") && rune.containsKey("HP%")) {
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

        if (rune.containsKey("Accuracy%") && (rune.containsKey("CRI Rate%") || rune.containsKey("CRI Dmg%"))) {
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

        if (rune.containsKey("Resistance%") && ((rune.containsKey("CRI Rate%") && rune.containsKey("CRI Dmg%")) || (rune.containsKey("HP%") && rune.containsKey("DEF%")))) {
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
        if(rune.containsKey("ATK%") && rune.containsKey("Accuracy%")){
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


    public static Double runeRankingEven(String mainStat,HashMap<String, Integer> rune, Double offset, List <String> prefTypes) {
        Double runeValue = offset;
        //
        //Initially reject if it doesn't match slow tank / SlowNuke criteria
        //

        boolean hasSpeed=false, slowNuke=false, slowTank=false;

        if (mainStat.equalsIgnoreCase("SPD") || rune.containsKey("Spd+") ) {
            if (rune.get("Spd+") > 3) {
                hasSpeed = true;
            } else if (mainStat.equalsIgnoreCase("ATK") && rune.containsKey("CRI Rate%") && rune.containsKey("CRI Dmg%")) {
                slowNuke = true;
            } else if ( (mainStat.equalsIgnoreCase("DEF") ||mainStat.equalsIgnoreCase("HP") ) && (rune.containsKey("HP%")
                    || rune.containsKey("DEF%")) && rune.containsKey("Resistance%")) {
                slowTank = true;
            }
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
        if(mainStat.equalsIgnoreCase("ATK") || mainStat.equalsIgnoreCase("SPD")){
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
        }

        ////
        //Bomber: Atk + Acc
        ////
        if(mainStat.equalsIgnoreCase("ATK") || mainStat.equalsIgnoreCase("SPD")){
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
        }

        ////
        //Bruiser: DEF | HP + CR / CD
        ////
        if(mainStat.equalsIgnoreCase("DEF") || mainStat.equalsIgnoreCase("HP") || mainStat.equalsIgnoreCase("SPD")) {
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
            }


            ////
            //Healer: Def + Hp /// Hp + Acc + Def
            ////
            if(mainStat.equalsIgnoreCase("HP") || mainStat.equalsIgnoreCase("DEF") || mainStat.equalsIgnoreCase("SPD")){
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
            }


            ////
            //Raid: Res + Cr + Cd /// Res + Hp + Def
            ////

            if(mainStat.equalsIgnoreCase("DEF") || mainStat.equalsIgnoreCase("HP") || mainStat.equalsIgnoreCase("SPD")) {
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
            }

            ////
            //SpdNuke: Atk | Spd // CD / CR / (Atk|Spd)
            ////
            if(mainStat.equalsIgnoreCase("ATK") || mainStat.equalsIgnoreCase("SPD")){
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
            }
            return runeValue;
        }



    }
