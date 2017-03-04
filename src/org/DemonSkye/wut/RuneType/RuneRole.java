package org.DemonSkye.wut.RuneType;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Damien on 2/21/2017.
 */

//TODO: Remove returns and calculate per subtype
public class RuneRole {
    public static Double runeRankingOdd(HashMap<String, Integer> rune, String implicit, Double offset, List <String> prefTypes, String rarity) {
        Double runeValue = offset;
        String preferredTypes[] = new String[prefTypes.size()];
        prefTypes.toArray(preferredTypes);

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

        if(implicit.contains("SPD")){
            runeValue += 1;
        }
        if(implicit.contains("CRI Rate") || implicit.contains("CRI Dmg")){
            runeValue+=1;
        }
        if(rune.containsKey("SPD+")){
            runeValue += RuneRoleHelperFunctions.speedWeight(rune.get("DEF%"), 3,6,8);
        }

        ////
        //Acc / Cr / CD -- acc nuker
        ////
        int accNuke = RuneRoleHelperFunctions.accNukeCount(rune);
        if (accNuke > 2 || (accNuke >1 && rarity.equalsIgnoreCase("Rare"))) {
            if(rune.containsKey("Accuracy%")){
                runeValue += RuneRoleHelperFunctions.accuracyWeight(rune.get("Accuracy%"), 2,2.5,3,3.5,4);
            }
            if (rune.containsKey("CRI Rate%")) {
                runeValue += RuneRoleHelperFunctions.critRateWeight(rune.get("CRI Rate%"), 4,5,6);
            }
            if (rune.containsKey("CRI Dmg%")) {
                runeValue += RuneRoleHelperFunctions.critDmgWeight(rune.get("CRI Dmg%"), 2,2.5,4,5);
            }
            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("AccNuke")) {
                    runeValue += 3;
                }
            }
            return runeValue;
        }

        ////
        //Bomber Atk + Acc
        ////
        int bomber = RuneRoleHelperFunctions.bomberCount(rune);
        if(bomber > 1){
            if(rune.containsKey("ATK%")){
                runeValue += RuneRoleHelperFunctions.attackWeight(rune.get("ATK%"), 1,2,3,4,6);
            }
            if(rune.containsKey("Accuracy%")){
                runeValue += RuneRoleHelperFunctions.accuracyWeight(rune.get("Accuracy%"), 1,2,3,4,6);
            }
            return runeValue;
        }

        ////
        //Def + CR / CD / HP-- Bruiser
        ////
        int bruiser = RuneRoleHelperFunctions.bruiserCount(rune);
        if (bruiser > 2 || (bruiser >1 && rarity.equalsIgnoreCase("Rare"))) {
            if(rune.containsKey("DEF%")){
                runeValue += RuneRoleHelperFunctions.defenseWeight(rune.get("DEF%"), 2,3,3.5,4,5);
            }

            if(rune.containsKey("HP%")){
                runeValue += RuneRoleHelperFunctions.healthWeight(rune.get("HP%"),2,3,3.5,4,5);
            }

            if (rune.containsKey("CRI Dmg%")) {
                runeValue += RuneRoleHelperFunctions.critDmgWeight(rune.get("CRI Dmg%"), 2,2.5,4,5);
            }

            if (rune.containsKey("CRI Rate%")) {
                runeValue += RuneRoleHelperFunctions.critRateWeight(rune.get("CRI Rate%"), 2,3.5,5);
            }
            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("Bruiser")) {
                    runeValue += 3;
                }
            }
            return runeValue;
        }

        //
        //Def + Hp /// Hp + Acc + Def-- Healer
        //
        int healer = RuneRoleHelperFunctions.healerCount(rune);
        if (healer >=1) {
            if(rune.containsKey("DEF%")){
                runeValue += RuneRoleHelperFunctions.defenseWeight(rune.get("DEF%"), 3,4,4.5,5,6);
            }
            if(rune.containsKey("HP%")){
                runeValue += RuneRoleHelperFunctions.healthWeight(rune.get("HP%"),4,5,5.5,6,6.5);
            }

            //Additional love for speed freaks, super-important to healers
            if(rune.containsKey("SPD+")){
                if (rune.get("SPD+") == 4) {
                    runeValue += 1.5;
                }
                if (rune.get("SPD+") == 5) {
                    runeValue += 3;
                }
                if (rune.get("SPD+") == 6) {
                    runeValue += 4;
                }
            }
            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("Healer")) {
                    runeValue += 3;
                }
            }
            return runeValue;
        }

        ////
        //Res + Cr + Cd /// Res + Hp + Def -- Raid monster
        ////
        int raid = RuneRoleHelperFunctions.raidCount(rune);
        if (raid > 2 || (raid >1 && rarity.equalsIgnoreCase("Rare"))) {
            if(rune.containsKey("Resistance%")){
                runeValue += RuneRoleHelperFunctions.resistanceWeight(rune.get("Resistance%"),1,1.5,3,3.5,4);
            }
            if (rune.containsKey("CRI Rate%") && rune.containsKey("CRI Dmg%")) {
                runeValue += RuneRoleHelperFunctions.critRateWeight(rune.get("CRI Rate%"), 4,5,6);
                runeValue += RuneRoleHelperFunctions.critDmgWeight(rune.get("CRI Dmg%"), 2,3,4,5);
            }
            if (rune.containsKey("hp%") && rune.containsKey("DEF%")) {
                runeValue += RuneRoleHelperFunctions.defenseWeight(rune.get("DEF%"), 3,4,4.5,5,6);
                runeValue += RuneRoleHelperFunctions.healthWeight(rune.get("HP%"), 4,5,5.5,6,6.5);
            }
            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("Raid")) {
                    runeValue += 3;
                }
            }
            return runeValue;
        }

        ////
        //SlowNuke: Atk / CR / CD
        ////
        int slowNukeValue = RuneRoleHelperFunctions.slowNukeCount(rune);
        if(slowNukeValue >=2 || slowNukeValue >=1 && rarity.equalsIgnoreCase("Rare")){
            //Atk valuation in slow nuke rune
            if(rune.containsKey("ATK%")){
                runeValue += RuneRoleHelperFunctions.attackWeight(rune.get("ATK%"), 1,1,2,2.5,4);
            }

            if(rune.containsKey("CRI Rate%")){
                runeValue += RuneRoleHelperFunctions.critRateWeight(rune.get("CRI Rate%"), 2,3.5,5);
            }


            if(rune.containsKey("CRI Dmg%")){
                runeValue += RuneRoleHelperFunctions.critDmgWeight(rune.get("CRI Dmg%"), 2,3,4,5);
            }
            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("SlowNuke")) {
                    runeValue += 3;
                }
            }
            return runeValue;
        }


        ////
        //SlowTank: Def + Hp + Res
        ////
        int slowTank = RuneRoleHelperFunctions.slowTankCount(rune);
        if (slowTank >=2 || slowTank >=1 && rarity.equalsIgnoreCase("Rare") ) {
            if(rune.containsKey("DEF%")){
                runeValue += RuneRoleHelperFunctions.defenseWeight(rune.get("DEF%"), 1,1,3,4,5);
            }
            if(rune.containsKey("HP")){
                runeValue += RuneRoleHelperFunctions.healthWeight(rune.get("HP%"), 1,1,3,4,5);
            }

            if(rune.containsKey("Resistance%")){
                runeValue += RuneRoleHelperFunctions.resistanceWeight(rune.get("Resistance%"), 1,1,3,3.5,4);
            }
            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("SlowTank")) {
                    runeValue += 3;
                }
            }
            return runeValue;
        }

        ////
        //Speedy Nuker -- Spd + One of (Atk / CD) + CR / Speed
        ////
        int speedNuke = RuneRoleHelperFunctions.spdNukeCount(rune);
        if (speedNuke > 2 || (speedNuke >1 && rarity.equalsIgnoreCase("Rare")) ) {
            runeValue += 2;

            if (rune.containsKey("ATK%")) {
                runeValue += RuneRoleHelperFunctions.attackWeight(rune.get("ATK%"), 2,2.5,3,3.5,5);
            }

            if (rune.containsKey("CRI DMG%")) {
                runeValue += RuneRoleHelperFunctions.critDmgWeight(rune.get("CRI Dmg%"), 2,2.75,3.5,6.5);
            }

            if(rune.containsKey("CRI Rate%")){
                runeValue += RuneRoleHelperFunctions.critRateWeight(rune.get("CRI Rate%"), 4, 5, 7);
            }
            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("SpdNuke")) {
                    runeValue += 3;
                }
            }
            return runeValue;
        }

        return runeValue;
    }




    ////////
    ////Rune ranking for runes with variable main stats
    ////////


    public static Double runeRankingEven(String mainStat,HashMap<String, Integer> rune, String implicit, Double offset, List <String> prefTypes, String rarity) {
        Double runeValue = offset;
        if (implicit.contains("SPD")) {
            runeValue += 1;
        }
        if (implicit.contains("CRI Rate") || implicit.contains("CRI Dmg")) {
            runeValue += 1;
        }
        //Always ding flat stats for stealing our gains
        if (rune.containsKey("DEF+")) {
            runeValue -= 1.5;
        }
        if (rune.containsKey("ATK+")) {
            runeValue -= 1.5;
        }
        if (rune.containsKey("HP+")) {
            runeValue -= 1.5;
        }

        if (rune.containsKey("SPD+")) {
            runeValue += RuneRoleHelperFunctions.speedWeight(rune.get("SPD+"), 3, 6, 8);
        }


        ////
        //AccNuke: Atk / Acc / Cr / CD
        ////

        int accNuke = RuneRoleHelperFunctions.accNukeCountMain(mainStat, rune);
        if (accNuke >= 2 || accNuke >= 1 && rarity.equalsIgnoreCase("Rare")) {
            if (mainStat.equalsIgnoreCase("CRI D")) {
                runeValue += 4;
            }
            if (rune.containsKey("Accuracy%")) {
                runeValue += RuneRoleHelperFunctions.accuracyWeight(rune.get("Accuracy%"), 2, 2.5, 3, 3.5, 5);
            }
            if (rune.containsKey("CRI Rate%")) {
                runeValue += RuneRoleHelperFunctions.critRateWeight(rune.get("CRI Rate%"), 4, 5, 7);
            }
            if (rune.containsKey("CRI Dmg%")) {
                runeValue += RuneRoleHelperFunctions.critDmgWeight(rune.get("CRI Dmg%"), 2, 3, 4, 5);
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
        int bomber = RuneRoleHelperFunctions.bomberCountMain(mainStat, rune);
        if (bomber >= 2 || bomber >= 1 && rarity.equalsIgnoreCase("Rare")) {
            if (rune.containsKey("Accuracy%")) {
                runeValue += RuneRoleHelperFunctions.accuracyWeight(rune.get("Accuracy%"), 3, 4, 5, 6, 8);
            }
            if (rune.containsKey("SPD+")) {
                runeValue += RuneRoleHelperFunctions.speedWeight(rune.get("SPD+"), 3, 4, 5);
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
        int bruiser = RuneRoleHelperFunctions.bruiserCountMain(mainStat, rune);
        if (bruiser >= 2 || bruiser >= 1 && rarity.equalsIgnoreCase("Rare")) {
            if (mainStat.equalsIgnoreCase("CRI D")) {
                runeValue += 4;
            }
            if (rune.containsKey("CRI Rate%")) {
                runeValue += RuneRoleHelperFunctions.critRateWeight(rune.get("CRI Rate%"), 4, 5, 6);
            }
            if (rune.containsKey("CRI Dmg%")) {
                runeValue += RuneRoleHelperFunctions.critDmgWeight(rune.get("CRI Dmg%"), 2, 3, 4, 5);
            }
            if (rune.containsKey("DEF%")) {
                runeValue += RuneRoleHelperFunctions.defenseWeight(rune.get("DEF%"), 3, 4, 4.5, 5, 6);
            }
            if (rune.containsKey("HP%")) {
                runeValue += RuneRoleHelperFunctions.healthWeight(rune.get("HP%"), 3, 4, 4.5, 5, 6);
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
        int healer = RuneRoleHelperFunctions.healerCountMain(mainStat, rune);
        if (healer >= 2 || healer >= 1 && rarity.equalsIgnoreCase("Rare")) {

            if (rune.containsKey("Accuracy%")) {
                runeValue += RuneRoleHelperFunctions.accuracyWeight(rune.get("Accuracy%"), 2,3,3.5,4,5);
            }
            if (rune.containsKey("DEF%")) {
                runeValue += RuneRoleHelperFunctions.defenseWeight(rune.get("DEF%"), 3, 4, 4.5, 5, 6);
            }
            if (rune.containsKey("HP%")) {
                runeValue += RuneRoleHelperFunctions.healthWeight(rune.get("HP%"), 3, 4, 4.5, 5, 6);
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
        int raid = RuneRoleHelperFunctions.raidCountMain(mainStat,rune);
        if(raid >=2 || raid >=1 && rarity.equalsIgnoreCase("Rare")) {
            if(rune.containsKey("Resistance%")){
                runeValue += RuneRoleHelperFunctions.resistanceWeight(rune.get("Resistance%"), 3,3,3,3.5,4);
            }
            if (rune.containsKey("DEF%")) {
                runeValue += RuneRoleHelperFunctions.defenseWeight(rune.get("DEF%"), 3, 4, 4.5, 5, 6);
            }
            if (rune.containsKey("HP%")) {
                runeValue += RuneRoleHelperFunctions.healthWeight(rune.get("HP%"), 3, 4, 4.5, 5, 6);
            }

            if(rune.containsKey("CRI Rate%") && rune.containsKey("CRI Dmg%")){
                runeValue += RuneRoleHelperFunctions.critRateWeight(rune.get("CRI Rate%"), 4, 5, 6);
                runeValue += RuneRoleHelperFunctions.critDmgWeight(rune.get("CRI Dmg%"), 2, 3, 4, 5);
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
        int slowNuke = RuneRoleHelperFunctions.slowNukeCountMain(mainStat,rune);
        if(slowNuke >= 2 || slowNuke >=1 && rarity.equalsIgnoreCase("Rare")){
            if(mainStat.equalsIgnoreCase("CRI D")){
                runeValue += 4;
            }
            if(rune.containsKey("CRI Rate%")){
                runeValue += RuneRoleHelperFunctions.critRateWeight(rune.get("CRI Rate%"), 4, 5, 6);
            }
            if(rune.containsKey("CRI Dmg%")){
                runeValue += RuneRoleHelperFunctions.critDmgWeight(rune.get("CRI Dmg%"), 4,5,6,7);
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
        int slowTank = RuneRoleHelperFunctions.slowTankCountMain(mainStat,rune);
        if(slowTank >=2 || slowTank >=1 && rarity.equalsIgnoreCase("Rare")){

            if (rune.containsKey("DEF%")) {
                runeValue += RuneRoleHelperFunctions.defenseWeight(rune.get("DEF%"), 3, 4, 4.5, 5, 6);
            }
            if (rune.containsKey("HP%")) {
                runeValue += RuneRoleHelperFunctions.healthWeight(rune.get("HP%"), 3, 4, 4.5, 5, 6);
            }
            if(rune.containsKey("Resistance%")){
                runeValue += RuneRoleHelperFunctions.resistanceWeight(rune.get("Resistance%"), 3,4,4.5,5,6);
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
        int speedNuke = RuneRoleHelperFunctions.spdNukeCountMain(mainStat,rune);
        if(speedNuke >=2 || speedNuke >=1 && rarity.equalsIgnoreCase("Rare")){
            if(rune.containsKey("ATK%")){
                RuneRoleHelperFunctions.attackWeight(rune.get("ATK%"),3,4,4.5,5,6);
            }
            if(rune.containsKey("CRI Rate%")){
                runeValue += RuneRoleHelperFunctions.critRateWeight(rune.get("CRI Rate%"), 4, 5, 7);
            }
            if(rune.containsKey("CRI Dmg%")){
                runeValue += RuneRoleHelperFunctions.critDmgWeight(rune.get("CRI Dmg%"), 4,5,6,7);
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
}