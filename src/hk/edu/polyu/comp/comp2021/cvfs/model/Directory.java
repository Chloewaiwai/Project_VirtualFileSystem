package hk.edu.polyu.comp.comp2021.cvfs.model;


import java.util.ArrayList;
import java.util.IllegalFormatPrecisionException;

/**
 * Directory in the Virtual File System.
 **/
public class Directory extends WorkDir {
    private ArrayList<WorkDir> dir;
    /**
     * the initial size of directory
     */
    static final int size1=40;


    /**
     * Name of a directory.
     * Only digits and English letters are allowed in file names.
     * and each file name may have at most 10 characters.
     */
    private String directoryname;

    /**
     * @param directoryname the name of the directory
     * Instantiate a directory.
     */
    public Directory(String directoryname) {
        dir = new ArrayList<>();
        this.directoryname = directoryname;
    }



    /**
     * Add a document in the working directory.
     * @param name the name of the document
     * @param type the type of the document
     * @param content the content of the document
     */
    public void addDoc(String name, String type, String content) {
        dir.add(new Doc(name, type, content));
    }

    /**
     * Add a directory in the working directory.
     * @param name the new directory name
     */
    public void addDir(String name) {
        dir.add(new Directory(name));
    }

    /**
     * Delete a file in the working directory.
     * @param filename the file name that user want to delete
     */
    public void delete(String filename) {
        int place = -1;
        for (WorkDir workDir : dir) {
            if (workDir.getName().equals(filename)) {
                place = dir.indexOf(workDir); } }
        dir.remove(place);
    }

    /**
     * @param oldfilename the existing file name that user want to rename
     * @param newfilename the new file name that user want to rename
     * Rename a file in the working directory.
     */
    public void rename(String oldfilename, String newfilename) {
        for (WorkDir workDir : dir) {
            if (workDir.getName().equals(oldfilename)) {
                workDir.setName(newfilename); }} }

    /**
     *
     * Change the working directory.
     * Throws IllegalFormatPrecisionException if the directory is not exist in the working directory.
     * @param dirname the name of the directory that user want to change
     * @return the new working directory
     */
    public Directory changeDir(String dirname) {
        for (WorkDir workDir : dir) {
            if(workDir instanceof Directory){
                if (workDir.getName().equals(dirname)) {
                    Directory dir = (Directory) workDir;
                    return dir;
               } } }
           throw new IllegalFormatPrecisionException(5);
    }

    /**
     *
     * @return the name of the directory.
     */
    @Override
    public String getName() { return directoryname; }

    /**
     * Return the type of the directory.
     * As directory have no type, always return null.
     */
    @Override
    public String getType() { return null; }

    /**
     * Rename the directory name.
     */
    @Override
    public void setName(String newfilename) { this.directoryname = newfilename; }

    /**
     * Return the content in the working directory in a list.
     */
    @Override
    public ArrayList<WorkDir> getDir() { return dir; }

    /**
     * Return the String representation of the working directory in the format "working directory {}".
     */
    public String toString() {
        String temp = "";
        temp = temp + "{" + directoryname;
        for (WorkDir workDir : dir) {
            temp = temp + workDir.toString() + " ";
        }
        temp = temp + "}";
        return temp;
    }

    /**
     * Return the String representation of the working directory in the format "working directory size".
     */
    @Override
    public String toStringg() { return directoryname+" "+size(); }

    /**
     * Return the number of file contained in the directory.
     */
    @Override
    public int size() {
        int size=size1;
        for (WorkDir workDir : dir) {
            size = size + workDir.size(); }
        return size;
    }

    /**
     * @return  the size of the directory.
     * The size of a directory is calculated as 40 plus the total size of its contained files.
     */
    public int totalsize() {
        int totalsizes = 0;
        for (WorkDir workDir : dir) {
            totalsizes=totalsizes+workDir.size(); }
        return totalsizes; }

    /**
     * Print the content in the working directory,
     * with the total number and size of files listed.
     */
    public void list() {
        int counter = 0;
        for (WorkDir workDir : dir) {
            System.out.println(workDir.toStringg());
            counter++; }
        System.out.println("The total number of files listed : " + counter);
        System.out.println("The total size of files listed : " + totalsize());
    }

    /**
     * Print the content in the working directory with indentation indicating its level,
     * also with the total number and size of files listed.
     */
    @Override
    public int rlist(int counter,int indentation){
        String space="\t";
        for (WorkDir workDir : dir) {
            if (workDir.getType()!=null){
            for (int i=0;i<indentation;i++){ System.out.print(space);}
            System.out.println(workDir.toStringg());
            counter++;}
            if (workDir.getType()==null) {
                for (int i=0;i<indentation;i++){ System.out.print(space);}
                System.out.println(workDir.toStringg());
                indentation++;
                counter++;
                counter= workDir.rlist(counter,indentation);
                indentation--;
            } }
        return counter;
    }
}
