package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.ArrayList;

/**
 * Document in the Virtual File System.
 **/
public class Doc extends WorkDir {
    /**
     * the minimum size of the
     */
    static final int docsize = 40;

    /**
     * Name of the document.
     * Only digits and English letters are allowed in file names,
     * and each file name may have at most 10 characters.
     **/
    private String docName;

    /**
     * Type of the document.
     * Only documents of types txt, java, html, and css are allowed in the system
     **/
    private String docType;

    /**
     * Content of the document.
     **/
    private String docContent;

    /**
     * Size of the document.
     * The size of a document is calculated as 40+content.length*2.
     **/
    private int size;



    /**
     * Instantiate a document.
     * @param docName the name of the document
     * @param docType the type of the document
     * @param docContent the content of the document
     */
    public Doc(String docName, String docType,String docContent){
        this.docName=docName;
        this.docType=docType;
        this.docContent=docContent;
        size=(docContent.length()*2)+docsize;
    }

    /**
     * Rename the document name.
     */
    @Override
    public void setName(String newfilename) { this.docName = newfilename; }

    /**
     * Return the type of the document.
     */
    @Override
    public String getType(){
        return docType;
    }

    /**
     * Return the name of the document.
     */
    @Override
    public String getName(){
        return docName;
   }

    /**
     * Return the size of the document.
     */
    @Override
    public int size(){
        return size;
    }

    /**
     * Return the file in the document in a list.
     * As file could not create under document, always return null.
     */
    @Override
    public  ArrayList<WorkDir> getDir(){return null;}

    /**
     * Return the file in the document in a list with indentation indicating its level.
     * As file could not create under document, always return 0.
     */
    @Override
    int rlist(int counter, int indentation) {
        return 0;
    }

    /**
     * Return the String representation of the document in the format "[docName docType docContent]".
     */
    public String toString(){
        String temp = "";
        temp = "["+docName+" "+docType +" "+docContent +"]";

        return temp;
    }

    /**
     * Return the String representation of the document in the format "docName.docType size".
     */
    @Override
    public String toStringg() {
        return docName+"."+docType+" "+size;
    }
}
