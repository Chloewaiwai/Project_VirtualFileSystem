package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Whether the files satisfy the composite criteria (negation) in the Virtual File System.
 **/
public class SatisfyNagation implements SimpleCri {

    /**
     * The simple criteria to form a composite criteria.
     **/
    private final SimpleCri criteria;

    /**
     * Name of the negation criteria.
     **/
    private String criname;

    /**
     * @param criname the name of the criteria that user want to negation
     * @param criteria the list of all the criteria
     * Instantiate a negation criteria.
     */
    public SatisfyNagation(String criname,SimpleCri criteria) {
        this.criteria = criteria;
        this.criname=criname;
    }

    /**
     * Return a list of file in the working directory which satisfy the negation criteria.
     */
    @Override
    public List filter(WorkDir workDir) {
        return negation(criteria,workDir);
    }

    /**
     * @param criteria the criteria list
     * @param workDir the working directory list
     * @return a list of negation criteria.
     */
    public List negation(SimpleCri criteria,WorkDir workDir){
        int flag=0;
        ArrayList negationCri =new ArrayList();
        List cri1 = criteria.filter(workDir);
        List workdir = workDir.getDir();
        for (Object o : workdir) {
            for (Object value : cri1) {
                if (o.equals(value)) {
                    flag = 1;
                    break; } }
            if (flag == 0) {
                negationCri.add(o);
            }
            flag = 0;
        }
        return negationCri;
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
        return criteria.getattrName();
    }

    /**
     * Return the op of the simple criteria. (i.e. contains, equals, >, <, >=, <=, ==, or !=)
     */
    @Override
    public String getop() {
        return criteria.getop();
    }

    /**
     * Return the val (a string in double quote) of the criteria.
     */
    @Override
    public String getVal() {
        return criteria.getVal();
    }

    /**
     * Return the val (string of an integer) of the criteria.
     */
    @Override
    public String getnormaleVal() {
        return criteria.getnormaleVal();
    }

    /**
     * Return the String representation of the negation criteria
     * in the format "!= Simple criteria name".
     */
    @Override
    public String toStringg() {
        return "!=" + " "+criteria.toStringg();
    }

    @Override
    public ArrayList<SimpleCri> getCriteria() {
        return null;
    }

    /**
     * Return the String representation of the negation criteria
     * in the format "(Negation criteria name Simple criteria name)".
     */
    public String toString(){
        return "(" + criname.toString() + " " + criteria.toString()+")";
    }
}
