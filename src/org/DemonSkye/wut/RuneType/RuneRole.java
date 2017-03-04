package org.DemonSkye.wut.RuneType;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Damien on 2/21/2017.
 */

//TODO: Remove returns and calculate per subtype
public class RuneRole {
    public static Double runeRankingOdd(HashMap<String, Integer> rune, String implicit, Double offset, List <String> prefTypes, String rarity) {
        Double accNukeResult = 0.0, bomberResult=0.0, bruiserResult=0.0, healerResult=0.0, raidResult = 0.0, slowNukeResult = 0.0, slowTankResult = 0.0, speedNukeResult =0.0;
        Double runeValue = 0.0;
        String preferredTypes[] = new String[prefTypes.size()];
        prefTypes.toArray(preferredTypes);

        ////
        //Acc / Cr / CD -- acc nuker
        ////
        int accNuke = RuneRoleHelperFunctions.accNukeCount(rune);
        if (accNuke > 2 || (accNuke >1 && rarity.equalsIgnoreCase("Rare"))) {
            if(rune.containsKey("Accuracy%")){
                accNukeResult += RuneRoleHelperFunctions.accuracyWeight(rune.get("Accuracy%"), 2,2.5,3,3.5,4);
            }
            if (rune.containsKey("CRI Rate%")) {
                accNukeResult += RuneRoleHelperFunctions.critRateWeight(rune.get("CRI Rate%"), 4,5,6);
            }
            if (rune.containsKey("CRI Dmg%")) {
                accNukeResult += RuneRoleHelperFunctions.critDmgWeight(rune.get("CRI Dmg%"), 2,2.5,4,5);
            }
            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("AccNuke")) {
                    accNukeResult += 3;
                }
            }
            System.out.println("AccNuke Result: " + accNukeResult);
        }

        ////
        //Bomber Atk + Acc
        ////
        int bomber = RuneRoleHelperFunctions.bomberCount(rune);
        if(bomber > 1){
            if(rune.containsKey("ATK%")){
                bomberResult += RuneRoleHelperFunctions.attackWeight(rune.get("ATK%"), 1,2,3,4,6);
            }
            if(rune.containsKey("Accuracy%")){
                bomberResult += RuneRoleHelperFunctions.accuracyWeight(rune.get("Accuracy%"), 1,2,3,4,6);
            }
            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("Bomber")) {
                    accNukeResult += 3;
                }
            }
            System.out.println("Bomber Result: " + bomberResult);
        }

        ////
        //Def + CR / CD / HP-- Bruiser
        ////
        int bruiser = RuneRoleHelperFunctions.bruiserCount(rune);
        if (bruiser > 2 || (bruiser >1 && rarity.equalsIgnoreCase("Rare"))) {
            if(rune.containsKey("DEF%")){
                bruiserResult += RuneRoleHelperFunctions.defenseWeight(rune.get("DEF%"), 2,3,3.5,4,5);
            }

            if(rune.containsKey("HP%")){
                bruiserResult += RuneRoleHelperFunctions.healthWeight(rune.get("HP%"),2,3,3.5,4,5);
            }

            if (rune.containsKey("CRI Dmg%")) {
                bruiserResult += RuneRoleHelperFunctions.critDmgWeight(rune.get("CRI Dmg%"), 2,2.5,4,5);
            }

            if (rune.containsKey("CRI Rate%")) {
                bruiserResult += RuneRoleHelperFunctions.critRateWeight(rune.get("CRI Rate%"), 2,3.5,5);
            }
            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("Bruiser")) {
                    bruiserResult += 3;
                }
            }
            System.out.println("Bruiser Result: " + bruiserResult);
        }

        //
        //Def + Hp /// Hp + Acc + Def-- Healer
        //
        int healer = RuneRoleHelperFunctions.healerCount(rune);
        if (healer >=1) {
            if(rune.containsKey("DEF%")){
                healerResult += RuneRoleHelperFunctions.defenseWeight(rune.get("DEF%"), 3,4,4.5,5,6);
            }
            if(rune.containsKey("HP%")){
                healerResult += RuneRoleHelperFunctions.healthWeight(rune.get("HP%"),4,5,5.5,6,6.5);
            }

            //Additional love for speed freaks, super-important to healers
            if(rune.containsKey("SPD+")){
                healerResult += RuneRoleHelperFunctions.speedWeight(rune.get("SPD+"), 1.5,3,4);
            }
            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("Healer")) {
                    healerResult += 3;
                }
            }
            System.out.println("Healer Result" + healerResult);
        }

        ////
        //Res + Cr + Cd /// Res + Hp + Def -- Raid monster
        ////
        int raid = RuneRoleHelperFunctions.raidCount(rune);
        if (raid > 2 || (raid >1 && rarity.equalsIgnoreCase("Rare"))) {
            if(rune.containsKey("Resistance%")){
                raidResult += RuneRoleHelperFunctions.resistanceWeight(rune.get("Resistance%"),1,1.5,3,3.5,4);
            }
            if (rune.containsKey("CRI Rate%") && rune.containsKey("CRI Dmg%")) {
                raidResult += RuneRoleHelperFunctions.critRateWeight(rune.get("CRI Rate%"), 4,5,6);
                raidResult += RuneRoleHelperFunctions.critDmgWeight(rune.get("CRI Dmg%"), 2,3,4,5);
            }
            if (rune.containsKey("hp%") && rune.containsKey("DEF%")) {
                raidResult += RuneRoleHelperFunctions.healthWeight(rune.get("HP%"), 4,5,5.5,6,6.5);
            }
            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("Raid")) {
                    raidResult += 3;
                }
            }
            System.out.println("Raid Result: " + raidResult);
        }

        ////
        //SlowNuke: Atk / CR / CD
        ////
        int slowNukeValue = RuneRoleHelperFunctions.slowNukeCount(rune);
        if(slowNukeValue >=2 || slowNukeValue >=1 && rarity.equalsIgnoreCase("Rare")){
            //Atk valuation in slow nuke rune
            if(rune.containsKey("ATK%")){
                slowNukeResult += RuneRoleHelperFunctions.attackWeight(rune.get("ATK%"), 1,1,2,2.5,4);
            }

            if(rune.containsKey("CRI Rate%")){
                slowNukeResult += RuneRoleHelperFunctions.critRateWeight(rune.get("CRI Rate%"), 2,3.5,5);
            }


            if(rune.containsKey("CRI Dmg%")){
                slowNukeResult += RuneRoleHelperFunctions.critDmgWeight(rune.get("CRI Dmg%"), 2,3,4,5);
            }
            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("SlowNuke")) {
                    slowNukeResult += 3;
                }
            }
            System.out.println("slowNukeResult: " + slowNukeResult);
        }


        ////
        //SlowTank: Def + Hp + Res
        ////
        int slowTank = RuneRoleHelperFunctions.slowTankCount(rune);
        if (slowTank >=2 || slowTank >=1 && rarity.equalsIgnoreCase("Rare") ) {
            if(rune.containsKey("DEF%")){
                slowTankResult += RuneRoleHelperFunctions.defenseWeight(rune.get("DEF%"), 1,1,3,4,5);
            }
            if(rune.containsKey("HP")){
                slowTankResult += RuneRoleHelperFunctions.healthWeight(rune.get("HP%"), 1,1,3,4,5);
            }

            if(rune.containsKey("Resistance%")){
                slowTankResult += RuneRoleHelperFunctions.resistanceWeight(rune.get("Resistance%"), 1,1,3,3.5,4);
            }
            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("SlowTank")) {
                    slowTankResult += 3;
                }
            }
            System.out.println("slowTankResult:" + slowTankResult);
        }

        ////
        //Speedy Nuker -- Spd + One of (Atk / CD) + CR / Speed
        ////
        int speedNuke = RuneRoleHelperFunctions.spdNukeCount(rune);
        if (speedNuke > 2 || (speedNuke >1 && rarity.equalsIgnoreCase("Rare")) ) {
            speedNukeResult += 2;

            if (rune.containsKey("ATK%")) {
                speedNukeResult += RuneRoleHelperFunctions.attackWeight(rune.get("ATK%"), 2,2.5,3,3.5,5);
            }

            if (rune.containsKey("CRI DMG%")) {
                speedNukeResult += RuneRoleHelperFunctions.critDmgWeight(rune.get("CRI Dmg%"), 2,2.75,3.5,6.5);
            }

            if(rune.containsKey("CRI Rate%")){
                speedNukeResult += RuneRoleHelperFunctions.critRateWeight(rune.get("CRI Rate%"), 4, 5, 7);
            }
            for (String s : preferredTypes) {
                if (s.equalsIgnoreCase("SpdNuke")) {
                    speedNukeResult += 3;
                }
            }
            System.out.println("speedNukeResult:" + speedNukeResult);
        }

        if(accNukeResult > runeValue){
            runeValue = accNukeResult;
        }
        if(bomberResult > runeValue){
            runeValue = bomberResult;
        }
        if(bruiserResult > runeValue){
            runeValue = bruiserResult;
        }
        if(healerResult > runeValue){
            runeValue = healerResult;
        }
        if(raidResult > runeValue){
            runeValue = raidResult;
        }
        if(slowNukeResult > runeValue){
            runeValue = slowNukeResult;
        }
        if(slowTankResult > runeValue){
            runeValue = slowTankResult;
        }
        if(speedNukeResult > runeValue){
            runeValue = speedNukeResult;
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

        if(implicit.contains("SPD")){
            runeValue += 1;
        }
        if(implicit.contains("CRI Rate") || implicit.contains("CRI Dmg")){
            runeValue+=1;
        }
        if(rune.containsKey("SPD+")){
            runeValue += RuneRoleHelperFunctions.speedWeight(rune.get("DEF%"), 3,6,8);
        }

        //add additional user specified value.
        runeValue += offset;

        return runeValue;
    }




    ////////
    ////Rune ranking for runes with variable main stats
    ////////


    public static Double runeRankingEven(String mainStat,HashMap<String, Integer> rune, String implicit, Double offset, List <String> prefTypes, String rarity) {
        Double accNukeResult = 0.0, bomberResult=0.0, bruiserResult=0.0, healerResult=0.0, raidResult = 0.0, slowNukeResult = 0.0, slowTankResult = 0.0, speedNukeResult =0.0;
        Double runeValue = 0.0;


        ////
        //AccNuke: Atk / Acc / Cr / CD
        ////

        int accNuke = RuneRoleHelperFunctions.accNukeCountMain(mainStat, rune);
        if (accNuke >= 2 || accNuke >= 1 && rarity.equalsIgnoreCase("Rare")) {
            if (mainStat.equalsIgnoreCase("CRI D")) {
                accNukeResult += 4;
            }
            if (rune.containsKey("Accuracy%")) {
                accNukeResult += RuneRoleHelperFunctions.accuracyWeight(rune.get("Accuracy%"), 2, 2.5, 3, 3.5, 5);
            }
            if (rune.containsKey("CRI Rate%")) {
                accNukeResult += RuneRoleHelperFunctions.critRateWeight(rune.get("CRI Rate%"), 4, 5, 7);
            }
            if (rune.containsKey("CRI Dmg%")) {
                accNukeResult += RuneRoleHelperFunctions.critDmgWeight(rune.get("CRI Dmg%"), 2, 3, 4, 5);
            }
            for (String s : prefTypes) {
                if (s.equalsIgnoreCase("AccNuke")) {
                    accNukeResult += 3;
                }
            }
            System.out.println("AccNukeResult: " + accNukeResult);
        }

        ////
        //Bomber: Atk + Acc
        ////
        int bomber = RuneRoleHelperFunctions.bomberCountMain(mainStat, rune);
        if (bomber >= 2 || bomber >= 1 && rarity.equalsIgnoreCase("Rare")) {
            if (rune.containsKey("Accuracy%")) {
                bomberResult += RuneRoleHelperFunctions.accuracyWeight(rune.get("Accuracy%"), 3, 4, 5, 6, 8);
            }
            if (rune.containsKey("SPD+")) {
                bomberResult += RuneRoleHelperFunctions.speedWeight(rune.get("SPD+"), 3, 4, 5);
            }
            for (String s : prefTypes) {
                if (s.equalsIgnoreCase("Bomber")) {
                    bomberResult += 3;
                }
            }
            System.out.println("BomberResult: " + bomberResult);
        }

        ////
        //Bruiser: DEF | HP + CR / CD
        ////
        int bruiser = RuneRoleHelperFunctions.bruiserCountMain(mainStat, rune);
        if (bruiser >= 2 || bruiser >= 1 && rarity.equalsIgnoreCase("Rare")) {
            if (mainStat.equalsIgnoreCase("CRI D")) {
                bruiserResult += 4;
            }
            if (rune.containsKey("CRI Rate%")) {
                bruiserResult += RuneRoleHelperFunctions.critRateWeight(rune.get("CRI Rate%"), 4, 5, 6);
            }
            if (rune.containsKey("CRI Dmg%")) {
                bruiserResult += RuneRoleHelperFunctions.critDmgWeight(rune.get("CRI Dmg%"), 2, 3, 4, 5);
            }
            if (rune.containsKey("DEF%")) {
                bruiserResult += RuneRoleHelperFunctions.defenseWeight(rune.get("DEF%"), 3, 4, 4.5, 5, 6);
            }
            if (rune.containsKey("HP%")) {
                bruiserResult += RuneRoleHelperFunctions.healthWeight(rune.get("HP%"), 3, 4, 4.5, 5, 6);
            }
            for (String s : prefTypes) {
                if (s.equalsIgnoreCase("Bruiser")) {
                    bruiserResult += 3;
                }
            }
            System.out.println("BruiserResult:" + bruiserResult);
        }


        ////
        //Healer: Def + Hp /// Hp + Acc + Def
        ////
        int healer = RuneRoleHelperFunctions.healerCountMain(mainStat, rune);
        if (healer >= 2 || healer >= 1 && rarity.equalsIgnoreCase("Rare")) {

            if (rune.containsKey("Accuracy%")) {
                healerResult += RuneRoleHelperFunctions.accuracyWeight(rune.get("Accuracy%"), 2,3,3.5,4,5);
            }
            if (rune.containsKey("DEF%")) {
                healerResult += RuneRoleHelperFunctions.defenseWeight(rune.get("DEF%"), 3, 4, 4.5, 5, 6);
            }
            if (rune.containsKey("HP%")) {
                healerResult += RuneRoleHelperFunctions.healthWeight(rune.get("HP%"), 3, 4, 4.5, 5, 6);
            }
            for (String s : prefTypes) {
                if (s.equalsIgnoreCase("Healer")) {
                    healerResult += 3;
                }
            }
            System.out.println("healerResult:" + healerResult);
        }

        ////
        //Raid: Res + Cr + Cd /// Res + Hp + Def
        ////
        int raid = RuneRoleHelperFunctions.raidCountMain(mainStat,rune);
        if(raid >=2 || raid >=1 && rarity.equalsIgnoreCase("Rare")) {
            if(rune.containsKey("Resistance%")){
                raidResult += RuneRoleHelperFunctions.resistanceWeight(rune.get("Resistance%"), 3,3,3,3.5,4);
            }
            if (rune.containsKey("DEF%")) {
                raidResult += RuneRoleHelperFunctions.defenseWeight(rune.get("DEF%"), 3, 4, 4.5, 5, 6);
            }
            if (rune.containsKey("HP%")) {
                raidResult += RuneRoleHelperFunctions.healthWeight(rune.get("HP%"), 3, 4, 4.5, 5, 6);
            }

            if(rune.containsKey("CRI Rate%") && rune.containsKey("CRI Dmg%")){
                raidResult += RuneRoleHelperFunctions.critRateWeight(rune.get("CRI Rate%"), 4, 5, 6);
                raidResult += RuneRoleHelperFunctions.critDmgWeight(rune.get("CRI Dmg%"), 2, 3, 4, 5);
            }
            for (String s : prefTypes) {
                if (s.equalsIgnoreCase("Raid")) {
                    raidResult += 3;
                }
            }
            System.out.println("raidResult:" + raidResult);
        }


        ////
        //SlowNuke: Atk / CR / CD
        ////
        int slowNuke = RuneRoleHelperFunctions.slowNukeCountMain(mainStat,rune);
        if(slowNuke >= 2 || slowNuke >=1 && rarity.equalsIgnoreCase("Rare")){
            if(mainStat.equalsIgnoreCase("CRI D")){
                slowNukeResult += 4;
            }
            if(rune.containsKey("CRI Rate%")){
                slowNukeResult += RuneRoleHelperFunctions.critRateWeight(rune.get("CRI Rate%"), 4, 5, 6);
            }
            if(rune.containsKey("CRI Dmg%")){
                slowNukeResult += RuneRoleHelperFunctions.critDmgWeight(rune.get("CRI Dmg%"), 4,5,6,7);
            }
            for (String s : prefTypes) {
                if (s.equalsIgnoreCase("SlowNuke")) {
                    slowNukeResult += 3;
                }
            }
            System.out.println("slowNukeResult: " + slowNukeResult);
        }


        ////
        //SlowTank: Def + Hp + Res
        ////
        int slowTank = RuneRoleHelperFunctions.slowTankCountMain(mainStat,rune);
        if(slowTank >=2 || slowTank >=1 && rarity.equalsIgnoreCase("Rare")){

            if (rune.containsKey("DEF%")) {
                slowTankResult += RuneRoleHelperFunctions.defenseWeight(rune.get("DEF%"), 3, 4, 4.5, 5, 6);
            }
            if (rune.containsKey("HP%")) {
                slowTankResult += RuneRoleHelperFunctions.healthWeight(rune.get("HP%"), 3, 4, 4.5, 5, 6);
            }
            if(rune.containsKey("Resistance%")){
                slowTankResult += RuneRoleHelperFunctions.resistanceWeight(rune.get("Resistance%"), 3,4,4.5,5,6);
            }
            for (String s : prefTypes) {
                if (s.equalsIgnoreCase("SlowTank")) {
                    slowTankResult += 3;
                }
            }
            System.out.println("Slow tank result: " + slowTankResult);
        }

        ////
        //SpdNuke: Atk | Spd // CD / CR / (Atk|Spd)
        ////
        int speedNuke = RuneRoleHelperFunctions.spdNukeCountMain(mainStat,rune);
        if(speedNuke >=2 || speedNuke >=1 && rarity.equalsIgnoreCase("Rare")){
            if(rune.containsKey("ATK%")){
                speedNukeResult += RuneRoleHelperFunctions.attackWeight(rune.get("ATK%"),3,4,4.5,5,6);
            }
            if(rune.containsKey("CRI Rate%")){
                speedNukeResult += RuneRoleHelperFunctions.critRateWeight(rune.get("CRI Rate%"), 4, 5, 7);
            }
            if(rune.containsKey("CRI Dmg%")){
                speedNukeResult += RuneRoleHelperFunctions.critDmgWeight(rune.get("CRI Dmg%"), 4,5,6,7);
            }
            for (String s : prefTypes) {
                if (s.equalsIgnoreCase("SpdNuke")) {
                    speedNukeResult += 3;
                }
            }
            System.out.println("Speed Nuke Result: " + speedNukeResult);
        }

        if(accNukeResult > runeValue){
            runeValue = accNukeResult;
        }
        if(bomberResult > runeValue){
            runeValue = bomberResult;
        }
        if(bruiserResult > runeValue){
            runeValue = bruiserResult;
        }
        if(healerResult > runeValue){
            runeValue = healerResult;
        }
        if(raidResult > runeValue){
            runeValue = raidResult;
        }
        if(slowNukeResult > runeValue){
            runeValue = slowNukeResult;
        }
        if(slowTankResult > runeValue){
            runeValue = slowTankResult;
        }
        if(speedNukeResult > runeValue){
            runeValue = speedNukeResult;
        }

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
        runeValue += offset;

        return runeValue;
    }
}