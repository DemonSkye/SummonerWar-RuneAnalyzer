package org.DemonSkye.wut;

import java.io.File;

/**
 * Created by Damien on 2/7/2017.
 */
public class Main {
    public static void main(String args[]) {
        long start = System.nanoTime();
        File f = new File("C:\\swproxy\\7578424-runs.csv");
        String str = ReadLastLine.tail(f);

        System.out.println(str);
        boolean found = false;
        if (str.contains("Rainbowmon")){
            found = true;
            FileOut.write("RainbowMon");
        }
        if (str.contains("craft_stuff")){
            found = true;
            FileOut.write("craft");
        }
        if (str.contains("scroll")){
            found = true;
            FileOut.write("scroll");
        }
        if (str.contains("Rune")){
            Double runeReturn = RuneDrop.runeEval(str);
            if(runeReturn == 2){
                System.out.println("runeReturn get: " + runeReturn);
                found = true;
                FileOut.write("rune: Get");
            }
            if(runeReturn == 1){
                System.out.println("runeReturn maybe: " + runeReturn);
                found = true;
                FileOut.write("rune: Maybe");
            }
            if (runeReturn == 0){
                found = true;
                System.out.println("runeReturn sell: " + runeReturn);
                FileOut.write("rune: sell");
            }
        }
        if (!found){
            FileOut.write("Unknown Item");
        }
        long end = System.nanoTime();
        System.out.println("Execution took: " + (end-start) + " ns");
    }
}
