package hk.edu.polyu.comp.comp2021.cvfs.model;

import com.sun.javafx.geom.IllegalPathStateException;
import java.util.ArrayList;
import java.util.List;

/**
 * Storing criteria in the Virtual File System.
 **/
public class StoreCri implements SimpleCri {

    /**
     * An arraylist of the criteria in the system.
     **/
    private ArrayList<SimpleCri> cri;

    /**
     * An arraylist of the files that criteria in the system.
     **/
    private ArrayList<SimpleCri> satisfy;

    /**
     * Instantiate the two arraylist.
     */
    public StoreCri() {
        cri = new ArrayList<>();
        satisfy=new ArrayList<>();
    }

    /**
     * Add a binary criteria to the arraylist of criteria.
     * @param criname the Criteria Name input from the user
     * @param criteria1 First existing criteria
     * @param criteria2 Second existing criteria
     * @param logicOp is either && or ||
     */
    public void addBinary(String criname, SimpleCri criteria1,SimpleCri criteria2,CompositeCri logicOp){
        cri.add(new SatisfyFiles(criname, criteria1, criteria2, logicOp));
    }

    /**
     *@param criname the criteria name from the user input
     *@param criteria the list containing all the criteria
     * Add a negation criteria to the arraylist of criteria.
     */
   public void addNegation(String criname, SimpleCri criteria){
        cri.add(new SatisfyNagation(criname, criteria));
    }

    /**
     * Add a simple criteria to the arraylist of criteria.
     * @param criName of the criteria (i.e. two English letters)
     * @param attrName of the criteria. (i.e. name, type or size)
     * @param op of the criteria. (i.e. contains, equals, >, <, >=, <=, ==, or !=)
     * @param val of the criteria. (i.e. a string in double quote or an integer)
     */
    public void addsimplecri(String criName,String attrName,String op,String val){
        cri.add(new Criteria(criName, attrName, op, val));
    }


    /**
     * Add a simple criterion (i.e.to check whether a file is a document) to the arraylist of criteria.
     * @param criName is "IsDocument"
     * @param attrName null
     * @param op null
     * @param val null
     */
    public void addIsDocument(String criName,String attrName,String op,String val){
        cri.add(new Criteria(criName, attrName, op, val));
    }

    /**
     * Print all the criteria in the system.
     */
    public void printAllCriteria(){
        for (SimpleCri simpleCri:cri){
            if (simpleCri.getcriName().equals("IsDocument")){
                    System.out.println(simpleCri.getcriName()); }
            else { System.out.println(simpleCri.toStringg()); } } }


    /**
     * @param criname the criteria name
     * @return  the name of the criteria.
     * Throw IllegalChannelGroupException if the criteria is not exist in the system.
     */
    public SimpleCri getcri(String criname){
        for (SimpleCri simpleCri:cri){
            if (criname.equals(simpleCri.getcriName())){
                return simpleCri; } }
        System.out.println("No Such Criteria!");
        throw new IllegalPathStateException();
    }

    /**
     * Print the file in the working directory that satisfy the criteria with the total number and size of files listed;
     * Throw IllegalArgumentException if the criteria is not exist in the system.
     * @param workDir the list in the working directory
     * @param reply the criteria name that user want to search
     */
    public void search(WorkDir workDir,String reply) {
        int size = 0;
        int flag = 0;
        List files = null;
        for (SimpleCri simpleCri : cri) {
            if (reply.equals(simpleCri.getcriName())) {
                files = simpleCri.filter(workDir);
                flag = 1;
            }
        }
        if (flag == 0) {
            System.out.println("No Such Criteria!");
            throw new IllegalPathStateException();
        }
        for (Object file : files) {
            WorkDir workDir1 = (WorkDir) file;
            System.out.println(workDir1.toStringg());
            size = size + workDir1.size();
        }

        System.out.println("The total number of files listed : " + files.size());
        System.out.println("The total size of files listed : " + size);
    }

    /**
     * Print the file in the working directory that satisfy the criteria with the total number and size of files listed;
     * Throw IllegalArgumentException if the criteria is not exist in the system.
     * @param workDir the list in the working directory
     * @param reply the criteria name that user want to search
     * @param temp temp[1] is the number of files , temp [0] is the size of files
     */
     public void rsearch(WorkDir workDir,String reply,int []temp){
         int flag=0;
         List files=null;
         ArrayList<WorkDir> workDirDir =workDir.getDir();
         for (WorkDir workDir2:workDirDir){
             if (workDir2.getType() == null) {
                 rsearch(workDir2,reply,temp); } }
         for(SimpleCri simpleCri:cri){
             if (reply.equals(simpleCri.getcriName())){
                 files=(simpleCri.filter(workDir));
                 flag=1; } }
         if(flag==0){
             System.out.println("No Such Criteria!");
             throw new IllegalPathStateException(); }
         for (Object file : files) {
             WorkDir workDir1 = (WorkDir) file;
                 System.out.println(workDir1.toStringg());
                 temp[1]++;
                 temp[0]=temp[0]+ workDir1.size();
     }}


    /**
     * Always return null
     */
    @Override
    public List filter(WorkDir workDir) { return null; }
    @Override
    public String getcriName() { return null; }
    @Override
    public String getattrName() { return null; }
    @Override
    public String getop() { return null; }
    @Override
    public String getVal() { return null; }
    @Override
    public String getnormaleVal() { return null; }
    @Override
    public String toStringg() { return null; }

    @Override
    public ArrayList<SimpleCri> getCriteria() {
        return cri;
    }

}
