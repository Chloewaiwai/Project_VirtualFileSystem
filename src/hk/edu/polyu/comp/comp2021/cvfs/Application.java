package hk.edu.polyu.comp.comp2021.cvfs;

import hk.edu.polyu.comp.comp2021.cvfs.model.CVFS;
import hk.edu.polyu.comp.comp2021.cvfs.model.Define;
import java.util.*;

/**
 *  command line interface (CLI) tool to facilitate the use of virtual disks.
 **/
public class Application {

    /**
     * @param args run the Application
     */
    public static void main(String[] args) {
        CVFS cvfs = new CVFS();
        Scanner a = new Scanner(System.in);
        System.out.println("Enter Your Command");
        System.out.println("Type \"End\" to end.");
        System.out.print("> ");
            int counter = 0;
            while (true) {
                String command = a.nextLine();
                String[] parts = command.split(" ");
                try{
                    if (command.equals("End")) { System.out.println("The system is ended.");
                        break; }
                if (counter == 0) {
                    if (!parts[0].equals("newDisk")) { throw new IllegalFormatFlagsException(command); } else {
                        counter++; }}
                new Define().define(command, cvfs); }

                catch (IllegalFormatFlagsException e){
                    System.out.println("Please create disk first!");
                    System.out.print(">"); }
                catch (IllegalThreadStateException e){
                    System.out.println("The name is too long! Each file name may have at most 10 characters!");
                    System.out.println("Please type your command again");
                    System.out.print(">"); }
                catch (IllegalFormatCodePointException e){
                    System.out.println("Only digits and English letters are allowed in file names!");
                    System.out.println("Please type your command again");
                    System.out.print(">"); }
                catch (IllegalMonitorStateException e){
                    System.out.println("Only documents of types txt, java, html, and css are allowed in the system!");
                    System.out.println("Please type your command again");
                    System.out.print(">"); }
                catch (IllegalStateException e){
                    System.out.println("Not enough space in the disk! Cannot add the file!");
                    System.out.println("Please type your command again");
                    System.out.print(">"); }
                catch (IllegalAccessError e){
                    System.out.println("Please input integer for the disk size!");
                    System.out.println("Please type your command again");
                    System.out.print(">"); }
                catch (IllegalFormatWidthException e){
                    System.out.println("Invalid command!");
                    System.out.println("Please type your command again");
                    System.out.print(">"); }
                catch (IllegalFormatPrecisionException e){
                    System.out.println("File does not exists in the working directory!");
                    System.out.println("Please type your command again");
                    System.out.print(">"); }
                catch (IllegalArgumentException e){
                    System.out.println("Invalid attrName, op or val!");
                    System.out.println("Please type your command again");
                    System.out.print(">"); }
                catch (Exception d){
                    System.out.println("Please type your command again");
                    System.out.print(">");
                }
            }
        }
    }


