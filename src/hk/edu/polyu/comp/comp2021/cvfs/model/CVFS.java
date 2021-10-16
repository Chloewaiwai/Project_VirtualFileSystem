package hk.edu.polyu.comp.comp2021.cvfs.model;

/**
 * In-Memory Virtual File System
 */
public class CVFS {

    /**
     * the CVFSTest
     */
    public void test(){ }

    private Directory directory;
    private Directory temp;
    private StoreCri storeCri;
    private int diskSizee;
    /**
     * the minimum size of a file.
     */
    static final int documentsize= 40;

    /**
     * the constructor of CVFS
     */
    public CVFS(){
        directory=new Directory("$");
        temp=directory;
        storeCri=new StoreCri();
    }

    /**
     * @param diskSize the size of the new disk.
     *[REQ1] The tool should support the creation of a new disk with a specified maximum size.
     */
    public void addDisk(String diskSize){
        directory=new Directory("$");
        temp=directory;
        storeCri=new StoreCri();
        addIsDocument("IsDocument","","","");
        this.diskSizee=Integer.parseInt(diskSize);
    }

    /**
     * @param name the name of the new document.
     * @param content the content of the new document.
     * @param type the type of the new document.
     *[REQ2] The tool should support the creation of a new document in the working directory.
     */
    public void addDoc(String name, String type, String content ){
        directory.addDoc(name, type, content);
    }

    /**
     * @param name the name of the new directory
     *[REQ3] The tool should support the creation of a new directory in the working directory.
     */
    public void addDir1(String name){
        directory.addDir(name);
    }

    /**
     * @param filename the name of file that user want to delete.
     *[REQ4] The tool should support the deletion of an existing file in the working directory.
     */
    public void delete(String filename){
        directory.delete(filename);
    }

    /**
     *@param oldfilename the name of existing file in the working directory.
     *@param newfilename the new name of that file in the working directory.
     *[REQ5] The tool should support the rename of an existing file in the working directory.
     */
    public void rename(String oldfilename, String newfilename){
        directory.rename(oldfilename, newfilename);
    }

    /**
     * @param dirname the name of the existing directory that user want to change.
     *[REQ6] The tool should support the change of working directory.
     */
    public void changeDir(String dirname){
        if (dirname.equals("..")){
            directory=temp;
            return; }
        directory=directory.changeDir(dirname);}

    /**
     *[REQ7] The tool should support listing all files direclty contained in the working directory.
     */
    public void list(){ directory.list(); }

    /**
     * @param counter number of files
     * @param indentation to indicate the level of each file.
     *[REQ8] The tool should support recursively listing all files in the working directory.
     */
    public void rlist(int counter,int indentation){
        counter=directory.rlist(counter, indentation);
        System.out.println("The total number of files listed : " +counter);}

    /**
     * @param criName of the criteria (i.e. two English letters)
     * @param attrName of the criteria. (i.e. name, type or size)
     * @param op of the criteria. (i.e. contains, equals, >, <, >=, <=, ==, or !=)
     * @param val of the criteria. (i.e. a string in double quote or an integer)
     *[REQ9] The tool should support the construction of simple criteria.
     */
    public void addsimplecri(String criName,String attrName,String op,String val){
        storeCri.addsimplecri(criName, attrName, op, val);
    }

    /**
     * @param criName is "IsDocument"
     * @param attrName null
     * @param op null
     * @param val null
     *[REQ10] The tool should support a simple criterion to check whether a file is a document.
     */
    public void addIsDocument(String criName,String attrName,String op,String val){
        storeCri.addIsDocument(criName, attrName, op, val);
    }

    /**
     * @param criname the Criteria Name input from the user
     * @param criteria1 First existing criteria
     * @param criteria2 Second existing criteria
     * @param logicOp is either && or ||
     *[REQ11] The tool should support the construction of composite criteria.
     * newNegation
     */
    public void addBinary(String criname, SimpleCri criteria1,SimpleCri criteria2,CompositeCri logicOp){
        storeCri.addBinary(criname, criteria1, criteria2, logicOp);
    }

    /**
     * @param criname the criteria name from the user input
     * @param simpleCri the list containing all the criteria
     *[REQ11] The tool should support the construction of composite criteria.
     * newBinaryCri
     */
    public void addNegation(String criname,SimpleCri simpleCri){
        storeCri.addNegation(criname,simpleCri);
    }

    /**
     *[REQ12] The tool should support the printing of all defined criteria.
     */
    public void printAllCriteria(){
        storeCri.printAllCriteria();
    }

    /**
     * @param workDir the list in the working directory
     * @param reply the criteria name that user want to search
     *[REQ13] The tool should support searching for files in the working directory based on an existing criterion.
     */
    public void search(WorkDir workDir,String reply){
        storeCri.search(workDir,reply);
    }

    /**
     * @param workDir the list in the working directory
     * @param reply the criteria name that user want to search
     * @param temp temp[1] is the number of files , temp [0] is the size of files
     *[REQ14] The tool should support recursively searching for files in the working directory based on an existing criterion.
     */
    public void rsearch(WorkDir workDir,String reply, int [] temp){
        storeCri.rsearch(workDir, reply,temp);
        System.out.println("The total number of files listed : "+temp[1]);
        System.out.println("The total size of files listed : "+temp[0]);
    }

    /**
     * @param criname Criteria name that want to be search.
     * @return  the name of the Criteria in string.
     */
    public SimpleCri getcri(String criname){
        return storeCri.getcri(criname);
    }

    /**
     * @param command the command input from the user.
     * @return true if there is enough space for adding the file,
     * otherwise false.
     */
    public boolean checkEnoughSpace(String command) {
        int remainsize = 0;
        int inputsize=documentsize+command.length()*2;
        remainsize = diskSizee - temp.totalsize();
        return inputsize <= remainsize;
    }

    /**
     * @return the list of directory.
     */
    public Directory getDirectory() {
        return directory;
    }

    /**
     * @return the list of criteria.
     */
    public StoreCri getStoreCri() {
        return storeCri;
    }


    /**
     * @return the size of the directory.
     */
    public int totalsize() {
        return directory.totalsize();
    }

}
