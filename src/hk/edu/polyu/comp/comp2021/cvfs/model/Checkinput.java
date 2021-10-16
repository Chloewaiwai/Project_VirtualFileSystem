package hk.edu.polyu.comp.comp2021.cvfs.model;


import java.nio.channels.IllegalChannelGroupException;
import java.util.ArrayList;

/**
 * Check the input from user is valid.
 */
public class Checkinput {

    /**
     *@param command the Name that input from user
     *@return true if the name of the file contains only digits and English letters,
     * otherwise, return false.
     */
    public boolean checkType(String command){
        return command.matches("[a-zA-Z0-9]*");
    }

    /**
     * @param command the Type that input from user
     * @return true if the type of the document is txt, java, html or css,
     * otherwise, return false.
     */
    public boolean checkTypeoffile( String command){
        return command.equals("txt") || command.equals("java") || command.equals("html") || command.equals("css");
    }

    /**
     * @param command the Content that input from user
     * @return true if the name of the file contains only at most 10 characters,
     * otherwise, return false.
     */
    public boolean checknamesize(String command){
        return command.length()<=10;
    }

    /**
     * @param command the criteria name
     * @param criteria the list of criteria
     * @return true if criteria name have not yet used, false if criteria name have created already
     */
   public boolean checkCriNameRepeat(String command,SimpleCri criteria){
        int flag=0;
        ArrayList<SimpleCri> cri=criteria.getCriteria();
        for (SimpleCri simpleCri:cri){
            if (simpleCri.getcriName().equals(command)) {
                    flag=1;
                }}
        return flag==0;
    }

    /**
     * @param command the logic op input by user
     * @return true if the logic op is either "&&" or "||"
     */
    public boolean checkLogicOp(String command){
        return command.equals("&&") || command.equals("||");
    }

    /**
     * @param command the Name that input from user
     * @return true if the command contains only integer,
     * otherwise, return false.
     */
    public boolean checkInt(String command){
        int length=command.length();
        for (int i=0;i<length;i++) {
            if (!Character.isDigit(command.charAt(i))) {
                return false; } }
        return true; }

    /**
     * @param criName Name of the criteria.
     * @return  true if the name of the criteria contains exactly two English letters,
     * otherwise, return false.
     */
    public boolean checkCriName(String criName){
        return criName.equals("IsDocument") || (criName.length()==2 && criName.matches("^[a-zA-Z]*$"));
    }

    /**
     *@param command the Name that input from user
     *@param workDir list in the working directory
     *@return true if the file is already contained in the working directory,
     * otherwise, return false.
     */
    public boolean checkFileContains(String command,WorkDir workDir){
        int flag=0;
        ArrayList<WorkDir> dir=workDir.getDir();
        for (WorkDir workDir1 : dir) {
            if (workDir1.getName().equals(command)) {
                flag=1;
            }}
        return flag != 0;
    }

    /**
     * @param command the Name that input from user
     * @param command1 the Type that input form user
     * @param workDir list in the working directory
     * @return true if the the name and type of the document is exactly same with the existing document in the working directory,
     * otherwise, return false.
     */
    public boolean checkdocNameRepeat(String command,String command1,WorkDir workDir){
        int flag=1;
        ArrayList<WorkDir> dir=workDir.getDir();
        for (WorkDir workDir1: dir){
            if (!(workDir1.getType()==null)){
            if (workDir1.getName().equals(command)&&workDir1.getType().equals(command1)) {
                flag=0;
            }
        }}
        return flag!=0;
    }

    /**
     * @param command input from user
     * @param workDir the list of workDir
     * @return  true if the the name of the directory is exactly same with the existing directory in the working directory,
     * otherwise, return false.
     */
    public boolean checkdirNameRepeat(String command,WorkDir workDir){
        int flag=0;
        ArrayList<WorkDir> dir=workDir.getDir();
        for (WorkDir workDir1: dir){
            if (workDir1.getType()==null){
                if (workDir1.getName().equals(command)) {
                    flag=1;
                }}
        }
        return flag==0;
    }

    /**
     * @param command the command that input from user
     * @param limit the limit of number of command
     * @return true if the command is valid,
     * otherwise, return false.
     */
    public boolean checksuitable(String command, int limit){
        String[] parts = command.split(" ");
        int length= parts.length;
        if (length!=limit){
            return false;
        }
        return true;
    }

    /**
     * @param attrName Attrname of the criteria. (i.e. name, type or size)
     * @param op op of the criteria. (i.e. contains, equals, >, <, >=, <=, ==, or !=)
     * @param val Val of the criteria. (i.e. a string in double quote or an integer)
     * @return true if attrName is either name, type, or size;
     *      If attrName is name, op must be contains and val must be a string in double quote;
     *      If attrName is type, op must be equals and val must be a string in double quote;
     *      If attrName is size, op can be >, <, >=, <=, ==, or !=, and val must be an integer;
     *      otherwise, return false.
     */
    public boolean checkCri(String attrName,String op,String val){
        switch (attrName){
            case ("name"):
                if (op.equals("contains")){
                    if (val.startsWith("\"")&& val.endsWith("\"")){
                        break; } }
                System.out.println("op must be \"contains\" and val must be a string in double quote!");
                throw new IllegalChannelGroupException();
            case ("type"):
                if (op.equals("equals")){
                    if (val.startsWith("\"")&& val.endsWith("\"")){
                        break; } }
                System.out.println("op must be \"equals\" and val must be a string in double quote!");
                throw new IllegalChannelGroupException();
            case ("size"):
                if (op.equals(">")||op.equals("<")||op.equals(">=")||op.equals("<=")||op.equals("==")||op.equals("!=")){
                    if (val.matches("[0-9]*")){
                        break; } }
                System.out.println("op can only be >, <, >=, <=, ==, or !=, and val must be an integer!");
                throw new IllegalChannelGroupException();
            default:
                System.out.println("attrName must be either name, type, or size!");
                throw new IllegalChannelGroupException();

        }
        return true; }
}

