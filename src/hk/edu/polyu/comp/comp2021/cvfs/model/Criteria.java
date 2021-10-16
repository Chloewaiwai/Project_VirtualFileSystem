package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple criteria in the Virtual File System.
 **/
interface SimpleCri {
    /**
     * @param workDir the list of the working directory
     * @return list of the workDir
     */
    List filter(WorkDir workDir);

    /**
     * @return the criteria
     */
    String getcriName();

    /**
     * @return the attrName
     */
    String getattrName();

    /**
     * @return the op
     */
    String getop();

    /**
     * @return the val
     */
    String getVal();

    /**
     * @return the normal val without ""
     */
    String getnormaleVal();

    /**
     * @return the criteria to string
     */
    String toStringg();

    /**
     * @return the criteria list
     */
    ArrayList<SimpleCri> getCriteria();

}

/**
 * Simple criteria in the Virtual File System.
 */
public class Criteria implements SimpleCri {

    /**
     * Name of the criteria.
     */
    private String criName;

    /**
     * Attrname of the criteria. (i.e. name, type or size)
     */
    private String attrName;

    /**
     * op of the criteria. (i.e. contains, equals, >, <, >=, <=, ==, or !=)
     */
    private String op;

    /**
     * Val of the criteria. (i.e. a string in double quote or an integer)
     */
    private String val;

    /**
     * @param criName of the criteria (i.e. two English letters)
     * @param attrName of the criteria. (i.e. name, type or size)
     * @param op of the criteria. (i.e. contains, equals, >, <, >=, <=, ==, or !=)
     * @param val of the criteria. (i.e. a string in double quote or an integer)
     * Instantiate a criteria.
     */
    public Criteria(String criName,String attrName,String op,String val){
        this.criName=criName;
        this.attrName=attrName;
        this.op=op;
        this.val=val;
    }

    /**
     * Return the String representation of the criteria in the format "(criName attrName op val )".
     */
    public String toString() { return "("+criName+" "+attrName+" "+ op+ " "+ val+" "+")"; }

    /**
     * Return the name of the criteria.
     */
    @Override
    public String getcriName() {
        return criName;
    }

    /**
     * Return the attrname of the criteria.
     */
    @Override
    public String getattrName(){
        return attrName;
    }

    /**
     * Return the op of the criteria.
     */
    @Override
    public String getop(){
        return op;
    }

    /**
     * Return the val (a string in double quote) of the criteria.
     */
    @Override
    public String getVal(){
        if (val.startsWith("\"")&& val.endsWith("\"")){
            return val.substring(1,val.length()-1);
        }
        return val;
    }

    /**
     * Return the val (an integer) of the criteria.
     */
    @Override
    public String getnormaleVal(){
        return val;
    }

    /**
     * Return the String representation of the IsDocument criteria
     * in the format "(IsDocument)";
     * If the name of the criteria is not "IsDocument",
     * return the String representation in the format "attrName op val".
     */
    @Override
    public String toStringg() {
        return attrName+" "+ op+ " "+ val;
    }

    @Override
    public ArrayList<SimpleCri> getCriteria() {
        return null;
    }

    /**
     * Return a list of file in the working directory which satisfy the criteria.
     */
    @Override
    public List filter(WorkDir workDir){
        ArrayList satisfy=new ArrayList();
        ArrayList<WorkDir> dir= workDir.getDir();
        if (criName.equals("IsDocument")){
            for(WorkDir workDir1:dir){
                if (workDir1 instanceof Doc){
                    satisfy.add(workDir1); } } }
        switch (attrName){
            case "name":
                for(WorkDir workDir1:dir){
                    if (workDir1.getName().contains(getVal())){
                        satisfy.add(workDir1); } }
                break;
            case "type":
                for (WorkDir workDir1:dir){
                    if (workDir1.getType()!=null){
                    if (workDir1.getType().equals(getVal())){
                        satisfy.add(workDir1); } }}
                break;
            case "size":
                switch (op){
                    case ">":
                        for (WorkDir workDir1:dir){
                            if (workDir1.size()>Integer.parseInt(getVal())){
                                satisfy.add(workDir1); } }
                        break;
                    case "<":
                        for (WorkDir workDir1:dir){
                            if (workDir1.size()<Integer.parseInt(getVal())){
                                satisfy.add(workDir1); } }
                        break;
                    case ">=":
                        for (WorkDir workDir1:dir){
                            if (workDir1.size()>=Integer.parseInt(getVal())){
                                satisfy.add(workDir1); } }
                        break;
                    case "<=":
                        for (WorkDir workDir1:dir){
                            if (workDir1.size()<=Integer.parseInt(getVal())){
                                satisfy.add(workDir1); } }
                        break;
                    case "==":
                        for (WorkDir workDir1:dir){
                            if (workDir1.size()==Integer.parseInt(getVal())){
                                satisfy.add(workDir1); } }
                        break;
                    case "!=":
                        for (WorkDir workDir1:dir){
                            if (workDir1.size()!=Integer.parseInt(getVal())){
                                satisfy.add(workDir1); } }
                        break; }
                break; }
        return satisfy;
    }
}
