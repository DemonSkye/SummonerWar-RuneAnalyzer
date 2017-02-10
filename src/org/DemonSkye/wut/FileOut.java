package org.DemonSkye.wut;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;

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
}
