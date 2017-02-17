package org.DemonSkye.wut.RuneType;
import java.util.HashMap;

/**
 * Created by Damien on 2/9/2017.
 */


//Most important attributes: Spd, CD, Atk, CR, Acc.
public class Blade {
    public static int evalRune(HashMap<String, Integer> statMap, String rarity, String mainStatType){
        int rating = 0;


        // Add / Subtract points for quality
        if (rarity.equalsIgnoreCase("Common")){
            rating -=1;
        }
        if (rarity.equalsIgnoreCase("Magic")){
            rating +=0;
        }
        if (rarity.equalsIgnoreCase("Rare")){
            rating +=1;
        }
        if (rarity.equalsIgnoreCase("Hero")){
            rating +=3;
        }
        if (rarity.equalsIgnoreCase("Legendary")){
            rating +=5;
        }


        if (mainStatType.equalsIgnoreCase("SPD")){
            rating += 4;

            //Def is pretty decent on a speed base
            if(statMap.containsKey("DEF%")){
                if(statMap.get("DEF%") > 6){
                    rating +=3;
                }
                else{
                    rating +=2;
                }
            }

            //Likewise with HP.  Cornerstone of a bruiser.
            if(statMap.containsKey("HP%")){
                if(statMap.get("HP%") > 6){
                    rating +=3;
                }
                else{
                    rating +=2;
                }
            }

            if(statMap.containsKey("Accuracy%")){
                if(statMap.get("Accuracy%") > 6){
                    rating +=1;
                }
            }

            //ATK is alright on a speed rune, but anyone that is truly dependent on atk might want a atk main stat rune.
            if(statMap.containsKey("ATK%")){
                if(statMap.get("ATK%") > 6){
                    rating +=2;
                }
                else{
                    rating +=1;
                }
            }

            //CD is huge on Blade, big damage increases.
            if(statMap.containsKey("CRI Dmg%")){
                if(statMap.get("CRI Dmg%") > 5){
                    rating +=4;
                }
                else{
                    rating +=3;
                }
            }

            //CR on top of a CR base makes other runes less dependent on CR, which is nice when optimization day comes around.
            if(statMap.containsKey("CRI Rate%")){
                if(statMap.get("CRI Rate%") > 4){
                    rating +=4;
                }
                else{
                    rating +=3;
                }
            }
        }


        //If we're building a bruiser we're going to have fairly high requirements.
        if(mainStatType.equalsIgnoreCase("DEF") || mainStatType.equalsIgnoreCase("HP")){
            //Rune will be good and tanky with both def / hp% subs
            if (statMap.containsKey("DEF%")) {
                if (statMap.get("DEF%") > 6) {
                    rating += 4;
                } else {
                    rating += 3;
                }
            }
            if (statMap.containsKey("HP%")) {
                if (statMap.get("HP%") > 6) {
                    rating += 4;
                } else {
                    rating += 3;
                }
            }
            if (statMap.containsKey("CRI Rate%")){
                if(statMap.get("CRI Rate%")  > 4){
                    rating +=4;
                }
                else{
                    rating +=3;
                }
            }
            if(statMap.containsKey("CRI Dmg%")){
                if(statMap.get("CRI Dmg%")>5){
                    rating +=4;
                }
                else{
                    rating +=3;
                }
            }
            //Fast bruisers win the day -- This doesn't really apply to NB10 teams, but yeahhhh.
            if(statMap.containsKey("SPD+")){
                if(statMap.get("SPD+") >4){
                    rating +=6;
                }
                else{
                    rating += 5;
                }
            }
            //Need some acc love.
            if (statMap.containsKey("Accuracy%")){
                rating+=1;
            }
        }

        if (mainStatType.equalsIgnoreCase("ATK")){
            rating +=4;
            if(statMap.containsKey("CRI Rate%")){
                rating +=6;
            }
            if (statMap.containsKey("CRI Dmg%")){
                rating +=6;
            }
            if (statMap.containsKey("SPD+")){
                if(statMap.get("SPD+") >4){
                    rating +=7;
                }
                else{
                    rating +=5;
                }
            }
            if (statMap.containsKey("Accuracy%")){
                rating += 2;
            }
        }

        if(mainStatType.equalsIgnoreCase("CRI D")){
            rating +=5;

            if(statMap.containsKey("CRI Rate%")){
                if(statMap.get("CRI Rate%") > 4){
                    rating += 6;
                }
                else{
                    rating +=4;
                }
            }

            if (statMap.containsKey("SPD+")){
                if(statMap.get ("SPD+") > 4){
                    rating += 5;
                }
                else{
                    rating +=4;
                }
            }

            if (statMap.containsKey("ATK%")){
                if(statMap.get("ATK%") > 6){
                    rating +=3;
                }
                else{
                    rating +=2;
                }
            }

            if (statMap.containsKey("DEF%")){
                if (statMap.get("DEF%") > 6){
                    rating +=2;
                }
                else{
                    rating += 1;
                }
            }

            if (statMap.containsKey("HP%")){
                if (statMap.get("HP%") > 6){
                    rating +=2;
                }
                else{
                    rating += 1;
                }
            }

            if(statMap.containsKey("Accuracy%")){
                rating +=1;
            }
        }

        if(mainStatType.equalsIgnoreCase("CRI R")){
            if(statMap.containsKey("CRI Dmg%") && (statMap.containsKey("ATK%") || statMap.containsKey("DEF%") || statMap.containsKey("HP%") || statMap.containsKey("SPD+")) ){
                rating +=9;
            }
        }

        //Slot 1/3/5 runes only
        if(mainStatType.equalsIgnoreCase("Flat")) {
            if (statMap.containsKey("DEF%")) {
                if (statMap.get("DEF%") > 6) {
                    rating += 2;
                } else {
                    rating += 1;
                }
            }
            if (statMap.containsKey("HP%")) {
                if (statMap.get("HP%") > 6) {
                    rating += 2;
                } else {
                    rating += 1;
                }
            }
            if (statMap.containsKey("CRI Rate%")){
                if(statMap.get("CRI Rate%")  > 4){
                    rating +=5;
                }
                else{
                    rating +=4;
                }
            }
            if(statMap.containsKey("CRI Dmg%")) {
                if (statMap.get("CRI Dmg%") > 5) {
                    rating += 5;
                } else {
                    rating += 4;
                }
            }
            if (statMap.containsKey("SPD+")){
                if(statMap.get("SPD+") > 4){
                    rating +=6;
                }
                else{
                    rating += 5;
                }
            }
            if(statMap.containsKey("ATK%")){
                if(statMap.get("ATK%") >6){
                    rating +=5;
                }
                else{
                    rating +=3;
                }
            }
            if (statMap.containsKey("Accuracy%")){
                rating+=2;
            }
        }

        if(statMap.containsKey("ATK+")){
            rating -=1;
        }

        if(statMap.containsKey("DEF+")){
            rating -=1;
        }
        if(statMap.containsKey("HP+")){
            rating -=1;
        }

        System.out.println("Result of rating: " + rating);

        if(rating >= 10){ return 2; } //Keep
        if(rating <10 && rating >=7) { return 1; } // Maybe
        return 0; //Nah
    }
}

