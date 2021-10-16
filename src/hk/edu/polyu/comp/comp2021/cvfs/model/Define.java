package hk.edu.polyu.comp.comp2021.cvfs.model;

import com.sun.javafx.geom.IllegalPathStateException;
import java.util.*;

/**
 * Define the class from the user input.
 **/
public class Define {
    /**
     * @param command the command from the user input
     * @param cvfs the CVFS.
     */
    public void define(String command,CVFS cvfs) {
        Checkinput checkinput= new Checkinput();
        String[] parts = command.split(" ");
        switch (parts[0]) {
            case "newDoc":
                String[] Docparts = command.split(" ",4);
                if (!checkinput.checkdocNameRepeat(parts[1],parts[2],cvfs.getDirectory())){System.out.println("Same name of document with same type is already existed in working directory.");throw new IllegalPathStateException(); }
                    if (!checkinput.checknamesize(parts[1])) { throw new IllegalThreadStateException(); }
                    if (!checkinput.checkType(parts[1])) { throw new IllegalFormatCodePointException(1); }
                    if (!checkinput.checkTypeoffile(parts[2])) { throw new IllegalMonitorStateException(); }
                    if (!cvfs.checkEnoughSpace(parts[3])) { throw new IllegalStateException(); }
                    cvfs.addDoc(parts[1],parts[2],Docparts[3]);
                break;
            case "newDisk":
                if (!checkinput.checksuitable(command,2)){throw new IllegalFormatWidthException(1); }
                    if (!checkinput.checkInt(parts[1])) { throw new IllegalAccessError(); }
                    cvfs.addDisk(parts[1]);
                break;
            case "newDir":
                if (!checkinput.checksuitable(command,2)){throw new IllegalFormatWidthException(1); }
                if (!checkinput.checkdirNameRepeat(parts[1],cvfs.getDirectory())){System.out.println("Same name of directory is already existed in working directory.");throw new IllegalPathStateException(); }
                    if (!checkinput.checkType(parts[1])) { throw new IllegalFormatCodePointException(1); }
                    if (!checkinput.checknamesize(parts[1])) { throw new IllegalThreadStateException(); }
                    if (!cvfs.checkEnoughSpace("")) { throw new IllegalStateException(); }
                    cvfs.addDir1(parts[1]);
                break;
            case "delete":
                if (!checkinput.checksuitable(command,2)){throw new IllegalFormatWidthException(1); }
                    if (!checkinput.checkType(parts[1])) { throw new IllegalFormatCodePointException(1); }
                    if (!checkinput.checkFileContains(parts[1], cvfs.getDirectory())){ throw new IllegalFormatPrecisionException(3); }
                    cvfs.delete(parts[1]);
                break;
            case "rename":
                if (!checkinput.checksuitable(command,3)){throw new IllegalFormatWidthException(1); }
                    if (!checkinput.checkType(parts[2])) { throw new IllegalFormatCodePointException(1); }
                    if (!checkinput.checknamesize(parts[2])) { throw new IllegalThreadStateException(); }
                    if (!checkinput.checkFileContains(parts[1], cvfs.getDirectory())){ throw new IllegalFormatPrecisionException(3); }
                    cvfs.rename(parts[1],parts[2]);
                break;
            case "changeDir":
                if (!checkinput.checksuitable(command,2)){throw new IllegalFormatWidthException(1); }
                cvfs.changeDir(parts[1]);
                break;
            case "list":
                if (!checkinput.checksuitable(command,1)){throw new IllegalFormatWidthException(1); }
                cvfs.list();
                break;
            case "rList":
                if (!checkinput.checksuitable(command,1)){throw new IllegalFormatWidthException(1); }
                cvfs.rlist(0,0);
                System.out.println("The total size of files listed : "+cvfs.totalsize());
                break;
            case "newSimpleCri":
                if(!checkinput.checkCriNameRepeat(parts[1],cvfs.getStoreCri())){
                    System.out.println("Same name of criteria was constructed!");
                    throw new IllegalPathStateException();}
                if (!checkinput.checksuitable(command,5)){throw new IllegalFormatWidthException(1); }
                if (!checkinput.checkCriName(parts[1])){ throw new IllegalArgumentException(String.valueOf(5)); }
                if (!checkinput.checkCri(parts[2],parts[3],parts[4] )){ throw new IllegalFormatPrecisionException(5); }
                cvfs.addsimplecri(parts[1],parts[2],parts[3],parts[4] );
                break;
            case "newNegation":
                if(!checkinput.checkCriNameRepeat(parts[1],cvfs.getStoreCri())){
                    System.out.println("Same name of criteria was constructed!");
                    throw new IllegalPathStateException();}
                if (!checkinput.checksuitable(command,3)){throw new IllegalFormatWidthException(1); }
                if (!checkinput.checkCriName(parts[1])){ throw new IllegalArgumentException(String.valueOf(5)); }
                SimpleCri cri= cvfs.getcri(parts[2]);
                cvfs.addNegation(parts[1],cri);
                break;
            case "newBinaryCri":
                if(!checkinput.checkCriNameRepeat(parts[1],cvfs.getStoreCri())){
                    System.out.println("Same name of criteria was constructed!");
                    throw new IllegalPathStateException();}
                if (!checkinput.checksuitable(command,5)){throw new IllegalFormatWidthException(1); }
                if (!checkinput.checkCriName(parts[1])){ throw new IllegalArgumentException(String.valueOf(5)); }
                if (!checkinput.checkLogicOp(parts[3])){System.out.println("Wrong logic op!"); throw new IllegalPathStateException();}
                SimpleCri firstcri= cvfs.getcri( parts[2]);
                SimpleCri secondcri= cvfs.getcri(parts[4]);
                if (parts[3].equals("&&")){ cvfs.addBinary(parts[1],firstcri,secondcri,CompositeCri.AND); }
                else if(parts[3].equals("||")){ cvfs.addBinary(parts[1],firstcri,secondcri,CompositeCri.OR); }
                break;
            case"search" :
                if (!checkinput.checksuitable(command,2)){throw new IllegalFormatWidthException(1); }
                cvfs.search(cvfs.getDirectory(), parts[1]);
                break;
            case"rSearch":
                if (!checkinput.checksuitable(command,2)){throw new IllegalFormatWidthException(1); }
                int []temp=new int[2];
                cvfs.rsearch(cvfs.getDirectory(), parts[1],temp);
                break;
            case "printAllCriteria":
                if (!checkinput.checksuitable(command,1)){throw new IllegalFormatWidthException(1); }
                System.out.println("All Criteria:");
                cvfs.printAllCriteria();
                break;
            default:
                throw new IllegalFormatWidthException(2);
        }
        System.out.println("Please enter your next command");
        System.out.print(cvfs.getDirectory().getName()+":");
    }
}


