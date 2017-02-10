package org.DemonSkye.wut.RuneType;

import java.util.HashMap;

/**
 * Created by Damien on 2/9/2017.
 */
//Most important attributes:  Spd, HP/Def/Acc (for bruiser / healer)
public class Guard {
    public static int evalRune(HashMap<String, Integer> statMap, String rarity){

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


        if ( statMap.containsKey("SPD+")){
            Integer spd = statMap.get("SPD+");
            if (spd == 4) { rating += 5; }
            if (spd ==5) { rating += 6; }
            if (spd == 6) { rating += 7; }
            if (spd == 7) { rating += 4; } // Main stat
        }

        if ( statMap.containsKey("CRI Dmg%")){
            Integer cd = statMap.get("CRI Dmg%");
            if (cd == 3) { rating +=1; }
            if (cd == 4) { rating += 2; }
            if (cd ==5) { rating += 2; }
            if (cd == 6) { rating += 3.5; }
            if (cd == 7) { rating += 4; }
            if (cd == 11) { rating += 6; } // Main stat 6* highly desired
        }

        if ( statMap.containsKey("CRI Rate%")){
            Integer cr = statMap.get("CRI Rate%");
            if (cr == 4) { rating += 5; }
            if (cr ==5) { rating += 6; }
            if (cr == 6) { rating += 7; }
            if (cr == 7) { rating += 4; } // Main stat very niche
            if(statMap.containsKey("HP%") || statMap.containsKey("DEF%")){
                rating +=2;
            }
            if (statMap.containsKey("ATK%")){
                rating +=1;
            }
        }

        if ( statMap.containsKey("ATK%")){
            Integer atk = statMap.get("ATK%");
            if (atk == 4) { rating += 1; }
            if (atk ==5) { rating += 2; }
            if (atk == 6) { rating += 3; }
            if (atk == 7) { rating += 4; }
            if (atk == 8) { rating += 5; }
            if (atk == 11) { rating += 6; } //Main stat, highly desired
        }

        //Bruiser stats -- somewhat useful
        if ( statMap.containsKey("HP%")){
            Integer hp = statMap.get("HP%");
            if (hp == 4) { rating += 1; }
            if (hp ==5) { rating += 2; }
            if (hp == 6) { rating += 3; }
            if (hp == 7) { rating += 4; }
            if (hp == 8) { rating += 5; }
            if (hp == 11) { rating += 6; } //Main stat, highly desired
        }

        if ( statMap.containsKey("DEF%")){
            Integer def = statMap.get("DEF%");
            if (def == 4) { rating += 1; }
            if (def ==5) { rating += 2; }
            if (def == 6) { rating += 3; }
            if (def == 7) { rating += 4; }
            if (def == 8) { rating += 5; }
            if (def == 11) { rating += 6; } //Main stat, highly desired
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
