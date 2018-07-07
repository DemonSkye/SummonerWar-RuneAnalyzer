package main.java.org.DemonSkye.wut;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Damien on 2/7/2017.
 */
public class FileOut {
    public static void write (String str){
        try (PrintWriter out = new PrintWriter("output.txt")) {
            out.print(str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeRune(String type, String fileName, String mainStat, String implicit, String substat1, String substat2, String substat3, String substat4){
        try(FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            out.print(getTime() +" - " + type +" Main Stat: " + mainStat + " - ");
            if(!implicit.isEmpty() && !implicit.equalsIgnoreCase("")){
                out.print("Implicit: " + implicit);
            }
            if(!substat1.isEmpty() && !substat1.equalsIgnoreCase("")){
                out.print(" - " +"Substat 1: " + substat1);
            }
            if(!substat2.isEmpty() && !substat2.equalsIgnoreCase("")){
                out.print(" - " +"Substat 2: " + substat2);
            }
            if(!substat3.isEmpty() && !substat3.equalsIgnoreCase("")){
                out.print(" - " +"Substat 3: " + substat3);
            }
            if(!substat4.isEmpty() && !substat4.equalsIgnoreCase("")){
                out.print(" - " + "Substat 4: " + substat4 + "\r\n");
            }
            out.println();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) { ioe.printStackTrace(); }
    }


    public static String getTime(){
        return  new SimpleDateFormat("MM/dd/yy HH:mm:ss").format(Calendar.getInstance().getTime());
    }
}
