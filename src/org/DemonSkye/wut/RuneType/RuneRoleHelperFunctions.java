package org.DemonSkye.wut.RuneType;

import java.util.HashMap;

/**
 * Created by Damien on 3/3/2017.
 */
public class RuneRoleHelperFunctions {

    //Weights
    public static double accuracyWeight(int runeValue, double value1, double value2, double value3, double value4, double value5){
        switch(runeValue){
            case 4: return value1;
            case 5: return value2;
            case 6: return value3;
            case 7: return value4;
            case 8: return value5;
            default: return 0;
        }
    }
    public static double attackWeight(int runeValue, double value1, double value2, double value3, double value4, double value5){
        switch(runeValue){
            case 4: return value1;
            case 5: return value2;
            case 6: return value3;
            case 7: return value4;
            case 8: return value5;
            default: return 0;
        }
    }
    public static double critDmgWeight(int runeValue, double value1, double value2, double value3, double value4){
        switch(runeValue){
            case 4: return value1;
            case 5: return value2;
            case 6: return value3;
            case 7: return value4;
            default: return 0;
        }
    }
    public static double critRateWeight(int runeValue, double value1, double value2, double value3){
        switch(runeValue){
            case 4: return value1;
            case 5: return value2;
            case 6: return value3;
            default: return 0;
        }
    }
    public static double defenseWeight(int runeValue, double value1, double value2, double value3, double value4, double value5){
        switch(runeValue){
            case 4: return value1;
            case 5: return value2;
            case 6: return value3;
            case 7: return value4;
            case 8: return value5;
            default: return 0;
        }
    }
    public static double healthWeight(int runeValue, double value1, double value2, double value3, double value4, double value5){
        switch(runeValue){
            case 4: return value1;
            case 5: return value2;
            case 6: return value3;
            case 7: return value4;
            case 8: return value5;
            default: return 0;
        }
    }
    public static double resistanceWeight(int runeValue, double value1, double value2, double value3, double value4, double value5){
        switch(runeValue){
            case 4: return value1;
            case 5: return value2;
            case 6: return value3;
            case 7: return value4;
            case 8: return value5;
            default: return 0;
        }
    }
    public static double speedWeight(int runeValue, double value1, double value2, double value3){
        switch(runeValue){
            case 4: return value1;
            case 5: return value2;
            case 6: return value3;
            default: return 0;
        }
    }



    //AccNuke Counters
    public static int accNukeCountMain(String mainStat,HashMap<String, Integer> rune){
        //AccNuke: Atk / Acc / Cr / CD
        int match = 0;
        if (mainStat.equalsIgnoreCase("ATK") || mainStat.equalsIgnoreCase("CRI D") || mainStat.equalsIgnoreCase("SPD+")){
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
        if(mainStat.equalsIgnoreCase("DEF")|| mainStat.equalsIgnoreCase("HP") || mainStat.equalsIgnoreCase("CRI D") || mainStat.equalsIgnoreCase("SPD+")){
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
        if(mainStat.equalsIgnoreCase("ATK") || mainStat.equalsIgnoreCase("CRI D")){
            if(rune.containsKey("CRI Rate%")){
                match += 1;
            }
            if(rune.containsKey("CRI Dmg%")){
                match +=1 ;
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
                match += 1;
            }
            if(rune.containsKey("Resistance%")){
                match += 1;
            }
        }
        if(mainStat.equalsIgnoreCase("HP")){
            if(rune.containsKey("DEF%")){
                match += 1;
            }
            if(rune.containsKey("Resistance%")){
                match += 1;
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
        if(mainStat.equalsIgnoreCase("SPD") || mainStat.equalsIgnoreCase("ATK") || mainStat.equalsIgnoreCase("CRI D")){
            if(rune.containsKey("SPD")){
                match += 1;
            }
            if(rune.containsKey("ATK%")){
                match += 1;
            }
            if(rune.containsKey("CRI Dmg%")){
                match += 1;
            }
            if(rune.containsKey("CRI Rate%")){
                match += 1;
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
