package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.ArrayList;

/**
 * Working directory in the Virtual File System.
 **/
abstract class WorkDir {
    /**
     * @return the size of files
     */
    public abstract int size();

    /**
     * @return the String
     */
    public abstract String toString();

    /**
     * @return the search string
     */
    public abstract String toStringg();

    /**
     * @return the name of file
     */
    public abstract String getName();

    /**
     * @return the type of file
     */
    public abstract String getType();

    /**
     * @param newfilename the new file name that user want to rename
     */
    public abstract void setName(String newfilename);

    /**
     * @return the list of workin directory
     */
    public abstract ArrayList<WorkDir> getDir();

    /**
     * @param counter the number of size
     * @param indentation the indentation
     * @return both the counter and number of indentation
     */
    abstract int rlist(int counter, int indentation);

}


