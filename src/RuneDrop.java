import java.util.HashMap;

/**
 * Created by Damien on 2/7/2017.
 */
public class RuneDrop {
    public static boolean runeEval (String str){

        boolean noImplicit = false;

        int rate = 0;
        //Parse out all the variables.  This is ugly but still more lightweight than using opencsv for a 1-off value grab


        str = str.substring(str.indexOf("Rune,")+5);
        //System.out.println("after substr: " + str); //raw string after substr
        Integer grade = Integer.parseInt(str.substring(0,1));
        str = str.substring(str.indexOf(",")+1);
        System.out.println("Grade: " + grade);

        Integer value = Integer.parseInt(str.substring(0,str.indexOf(",")));
        str = str.substring(str.indexOf(",")+1);
        System.out.println("Value: " + value);


        String type = str.substring(0,str.indexOf(","));
        str = str.substring(str.indexOf(",")+1);
        System.out.println("Type: " + type);

        Double efficiency = Double.parseDouble(str.substring(0,str.indexOf(",")-1));
        str = str.substring(str.indexOf(",")+1);
        System.out.println("Efficiency: " + efficiency);

        Integer slot = Integer.parseInt(str.substring(0,str.indexOf(",")));
        str = str.substring(str.indexOf(",")+1);
        System.out.println("Slot: " + slot);


        String rarity = str.substring(0, str.indexOf(","));
        str = str.substring(str.indexOf(",")+1);
        System.out.println("Rarity: " + rarity);


        String mainStat = str.substring(0, str.indexOf(","));
        str = str.substring(str.indexOf(",")+1);
        System.out.println("MainStat: " + mainStat);

        String implicit = str.substring(0, str.indexOf(","));
        if(implicit.isEmpty()){
            noImplicit = true;
            System.out.println("No Substat");
        }
        else{
            System.out.println("Implicit: " + implicit);
        }
        str = str.substring(str.indexOf(",")+1);

        String substat1 = str.substring(0, str.indexOf(","));
        str = str.substring(str.indexOf(",")+1);
        System.out.println("Substat1: " + substat1);

        String substat2 = str.substring(0, str.indexOf(","));
        str = str.substring(str.indexOf(",")+1);
        System.out.println("Substat2: " + substat2);


        String substat3 ="";
        String substat4 ="";
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
        if(grade.equals("5")){
            if(!str.contains("Legendary") && str.contains("SPD")){
                //return false;
            }
        }


        if(grade == 6 ){

                //Don't bother if main stat is rubbish
                // Shit that we never, ever want.
            if (mainStat.contains("DEF +") ||
                mainStat.contains("ATK +") ||
                mainStat.contains("HP +") ||
                mainStat.contains("Resist") ||
                mainStat.contains("CRI Rate")){
                return false;
            }

            //If the implicit is speed and it's not at least a hero or legendary, toss it, CR / CD runes are a chance that you may want it, but with a low base speed it's meh
            if(!noImplicit){
                if(implicit.contains("SPD") && rarity.equalsIgnoreCase("Rare")){
                    System.out.println("test");
                    return false;
                }
            }

            //We want a bad stat to be our implicit unless it's legendary, in which case we want it to be a
            if(!noImplicit && !rarity.equalsIgnoreCase("Legendary")){
                if (implicit.contains("+")){
                    rate +=2;
                }
            }

            //We want a bad stat to be our implicit unless it's legendary
            if(!noImplicit && !rarity.equalsIgnoreCase("Legendary")){
                if (implicit.contains("%")){
                    rate +=1;
                }
            }

            if (rarity.equalsIgnoreCase("Hero")){
                rate +=3;
            }

            if (rarity.equalsIgnoreCase("Legendary")){
                rate +=6;
            }

            if (implicit.contains("CRI R")){
                rate +=3;
            }

            if (implicit.contains("CRI D")){
                rate +=5;
            }


            HashMap<String, Integer> statMap = new HashMap<>();
            if(noImplicit){
                statMap = StatMap.statMapper("",mainStat,substat1, substat2,substat3,substat4, rarity);
            }
            if(!noImplicit){
                statMap = StatMap.statMapper(implicit,mainStat,substat1, substat2,substat3,substat4, rarity);
            }

            //If we find a "perfect" atk rune, return it regardless of stats
            if(statMap.containsKey("SPD+") && statMap.containsKey("CRI Rate%") && (statMap.containsKey("ATK%") ||  statMap.containsKey("CRI Dmg%"))  ){
                return true;
            }

            /*if(statMap.containsKey("SPD+") && statMap.containsKey("HP%")  && (statMap.containsKey("DEF%") || statMap.containsKey("Accuracy%") || statMap.containsKey("Resistance"))){
                return true;
            }*/


            //Debug for contents of hashmap
            System.out.println("Outputting Hashmap");
            for (String name: statMap.keySet()){
                String key =name.toString();
                String val = statMap.get(name).toString();
                System.out.println(key + " " + val);
            }

            if (statMap.containsKey("ATK+")){
                rate =-1;
            }

            if (statMap.containsKey("DEF+")){
                rate -=1;
            }

            if (statMap.containsKey("HP+")){
                rate -=1;
            }

            if (statMap.containsKey("CRI Rate%") && statMap.containsKey("CRI Dmg%")){
                rate +=2;
            }

            if(statMap.containsKey("SPD+")){
                if (statMap.get("SPD+") == 4){
                    rate +=7;
                }
                if (statMap.get("SPD+") ==5){
                    rate +=9;
                }
                if (statMap.get ("SPD+") ==6){
                    rate += 13;
                }
            }
            if(statMap.containsKey("CRI Rate%")){
                if (statMap.get("CRI Rate%") == 4){
                    rate +=2;
                }
                if (statMap.get("CRI Rate%") ==5){
                    rate +=4;
                }
                if (statMap.get ("CRI Rate%") ==6){
                    rate += 6;
                }
            }

            if (statMap.containsKey("CRI Dmg")){
                if (statMap.get("CRI Dmg%") == 4){
                    rate +=2;
                }
                if (statMap.get("CRI Dmg%") ==5){
                    rate +=3;
                }
                if (statMap.get ("CRI Dmg%") ==6){
                    rate += 4;
                }
                if (statMap.get ("CRI Dmg%") ==7){
                    rate += 6;
                }
            }

            if  ( statMap.containsKey("HP%") && !mainStat.contains("HP 11%") ){
                if (statMap.get("HP%") == 4){
                    rate +=1;
                }
                if (statMap.get("HP%") == 5){
                    rate +=1;
                }
                if (statMap.get("HP%") ==6){
                    rate +=2;
                }
                if (statMap.get ("HP%") ==7){
                    rate += 3;
                }
                if (statMap.get ("HP%") ==8){
                    rate += 4;
                }
            }

            if( statMap.containsKey("DEF%") && !mainStat.contains("DEF 11%")){
                if (statMap.get("DEF%") == 4){
                    rate +=1;
                }
                if (statMap.get("DEF%") ==5){
                    rate +=2;
                }
                if (statMap.get ("DEF%") ==6){
                    rate += 3;
                }
                if (statMap.get ("DEF%") ==7){
                    rate += 4;
                }
                if (statMap.get ("DEF%") ==8){
                    rate += 5;
                }
            }

            System.out.println("Rune rating: " + rate);

        }
        if (rate >10) { System.out.println("get rune" ); return true; }
        else{ return false; }


    }
}
