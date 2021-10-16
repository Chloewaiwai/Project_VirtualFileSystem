package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite criteria in the Virtual File System.
 **/
public enum CompositeCri {
    /**
     * AND is "&&"
     */
    AND,
    /**
     *OR is "||"
     */
    OR;

    /**
     * @param criteria1 First criteria
     * @param criteria2 Second criteria
     * @param workDir list in the working directory
     * @return the composition of composite criteria in the system.
     **/
    public List composite (SimpleCri criteria1, SimpleCri criteria2,WorkDir workDir){
        int flag=0;
        ArrayList compositeCri =new ArrayList();
        List cri1,cri2;
        switch (this){
            case AND:
                cri1=criteria1.filter(workDir);
                cri2=criteria2.filter(workDir);
                for (Object o : cri1) {
                    for (Object value : cri2) {
                        if (o.equals(value)) {
                            compositeCri.add(o);
                            break; } } }
                break;
            case OR:
                cri1=criteria1.filter(workDir);
                cri2=criteria2.filter(workDir);
                compositeCri.addAll(cri1);
                for (Object o : cri2) {
                    for (Object value : compositeCri) {
                        if (value.equals(o)) { flag = 1; } }
                    if (flag == 0) { compositeCri.add(o); }
                    flag = 0;
                }
                break;
        }
        return compositeCri;
    }
}


