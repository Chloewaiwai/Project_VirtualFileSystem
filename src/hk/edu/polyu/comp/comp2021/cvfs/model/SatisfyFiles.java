package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Whether the files satisfy the composite criteria (binary) in the Virtual File System.
 **/
public class SatisfyFiles implements SimpleCri {

    /**
     * Two simple criteria to form a composite criteria.
     **/
    private final SimpleCri criteria1,criteria2;

    /**
     * The op for the binary criteria.
     **/
    private final CompositeCri logicOp;

    /**
     * Name of the binary criteria.
     **/
    private String criname;


    /**
     * Instantiate a binary criteria.
     * @param criname the Criteria Name input from the user
     * @param criteria1 First existing criteria
     * @param criteria2 Second existing criteria
     * @param logicOp is either && or ||
     */
    public SatisfyFiles (String criname,SimpleCri criteria1,SimpleCri criteria2,CompositeCri logicOp){
        this.criteria1=criteria1;
        this.criteria2=criteria2;
        this.logicOp=logicOp;
        this.criname=criname;
    }

    /**
     * Return a list of file in the working directory which satisfy the binary criteria.
     */
    @Override
    public List filter (WorkDir workDir){
        return  logicOp.composite(criteria1,criteria2,workDir);
    }

    /**
     * Return the name of the criteria.
     */
    @Override
    public String getcriName() {
        return criname;
    }

    /**
     * Return the attrname of the criteria. (i.e. either name, type, or size)
     */
    @Override
    public String getattrName() {
        return criteria1.getattrName();
    }

    /**
     * Return the op of the simple criteria. (i.e. contains, equals, >, <, >=, <=, ==, or !=)
     */
    @Override
    public String getop() {
        return criteria1.getop();
    }

    /**
     * Return the val (a string in double quote) of the criteria.
     */
    @Override
    public String getVal() {
        return criteria1.getVal();
    }

    /**
     * Return the val (string of an integer) of the criteria.
     */
    @Override
    public String getnormaleVal() {
        return criteria1.getnormaleVal();
    }

    /**
     * @return  the op of the binary criteria. (i.e. && or ||)
     */
    public String getlogicop() {
        if (logicOp.toString().equals("AND")){
        return "&&";}
        return "||";
    }


    /**
     * Return the String representation of the binary criteria
     * in the format "Binary criteria name 1st Simple criteria name op 2nd Simple criteria name".
     */
    public String toString(){
        return "("+criname + criteria1.toString() + logicOp.toString() + criteria2.toString()+")";
    }

    /**
     * Return the String representation of composition of the binary criteria
     * in the format "1st Simple criteria op 2nd Simple criteria".
     */
    @Override
    public String toStringg(){
        return criteria1.toStringg() +" " +getlogicop() +" "+ criteria2.toStringg();
    }

    @Override
    public ArrayList<SimpleCri> getCriteria() {
        return null;
    }

}
