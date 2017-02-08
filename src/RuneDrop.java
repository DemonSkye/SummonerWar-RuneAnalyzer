import java.util.HashMap;

/**
 * Created by Damien on 2/7/2017.
 */
public class RuneDrop {
    public static boolean runeEval (String str){

        boolean noImplicit = false;

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
        if(grade.equals("5")){
            if(!str.contains("Legendary") && str.contains("SPD")){
                //return false;
            }
        }


        int rate = 0;
        //Don't bother if main stat is rubbish on 2/4/6
        if(grade == 6 ){
            if (slot == 2 || slot == 4 || slot ==6){
                if (mainStat.contains("DEF +") ||
                    mainStat.contains("ATK +") ||
                    mainStat.contains("HP +") ||
                    mainStat.contains("Resist") ||
                    mainStat.contains("CRI Rate")){
                    return false;
                }
            }

            //If the implicit is speed and it's not at least a hero or legendary, toss it, CR / CD runes are a chance that you may want it, but with a low base speed it's meh
            if(!noImplicit){
                if(implicit.contains("SPD") && (!rarity.equalsIgnoreCase("Legendary") || !rarity.equalsIgnoreCase("Hero"))  ){
                    return false;
                }
            }

            //Declare rating variable, if it is > 10, get the rune.

            //We want a bad stat to be our implicit unless it's legendary
            if(!noImplicit && !rarity.equalsIgnoreCase("Legendary")){
                if (implicit.contains("+")){
                    rate +=1;
                }
            }

            //We want a bad stat to be our implicit unless it's legendary
            if(!noImplicit && !rarity.equalsIgnoreCase("Legendary")){
                if (implicit.contains("%")){
                    rate +=1;
                }
            }

            HashMap<String, Integer> statMap = new HashMap<>();
            if(noImplicit){
                statMap = StatMap.statMapper("",substat1, substat2,substat3,substat4, rarity);
            }
            if(!noImplicit){
                statMap = StatMap.statMapper(implicit,substat1, substat2,substat3,substat4, rarity);
            }


            System.out.println("Outputting Hashmap");
            for (String name: statMap.keySet()){

                String key =name.toString();
                String val = statMap.get(name).toString();
                System.out.println(key + " " + val);
            }

        }
        if (grade >10) return true;
        else{ return false; }


    }
}
