package hk.edu.polyu.comp.comp2021.cvfs.model;

import com.sun.javafx.geom.IllegalPathStateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.IllegalFormatCodePointException;
import java.util.IllegalFormatPrecisionException;
import java.util.IllegalFormatWidthException;

import static org.junit.Assert.*;

/**
 * The test code for the Virtual File System.
 **/
public class CVFSTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    /**
     * setUpStreams
     */
    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    /**
     * restoreStreams
     */
    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    /**
     * test the CVFS Constructor
     */
    @Test
    public void testCVFSConstructor(){
        CVFS cvfs = new CVFS();
        cvfs.test();
        assert true;
    }

    /**
     * test if it is creating the new disk
     */
    @Test
    public void testnewDisk(){
        Define define=new Define();
        CVFS cvfs=new CVFS();
        define.define("newDisk 1000",cvfs);
        assertEquals("[]",cvfs.getDirectory().getDir().toString());
    }

    /**
     * test if it is creating new directory
     */
    @Test
    public void testnewDir(){
        Define define=new Define();
        CVFS cvfs=new CVFS();
        define.define("newDisk 1000",cvfs);
        define.define("newDir 111",cvfs);
        assertEquals("[{111}]",cvfs.getDirectory().getDir().toString());
        boolean thrown = false;
        try {
            define.define("newDir 111",cvfs);
        } catch (IllegalPathStateException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    /**
     * test if it is creating new directory
     */
    @Test
    public void testnewDoc(){
        Define define=new Define();
        CVFS cvfs=new CVFS();
        define.define("newDisk 1000",cvfs);
        define.define("newDoc AAA txt hi",cvfs);
        assertEquals("[[AAA txt hi]]",cvfs.getDirectory().getDir().toString());
        boolean thrown = false;
        try {
            define.define("newDoc AAA txt bye",cvfs);
        } catch (IllegalPathStateException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    /**
     * test if it is deleting the files
     */
    @Test
    public void testDelete(){
        Define define=new Define();
        CVFS cvfs=new CVFS();
        define.define("newDisk 1000",cvfs);
        define.define("newDoc AAA txt hi",cvfs);
        define.define("newDir 111",cvfs);
        define.define("delete AAA",cvfs);
        assertEquals("[{111}]",cvfs.getDirectory().getDir().toString());
        define.define("delete 111",cvfs);
        assertEquals("[]",cvfs.getDirectory().getDir().toString());
    }

    /**
     * test if it is renaming the files
     */
    @Test
    public void testrename(){
        Define define=new Define();
        CVFS cvfs=new CVFS();
        define.define("newDisk 1000",cvfs);
        define.define("newDoc AAA txt hi",cvfs);
        define.define("newDir 111",cvfs);
        define.define("rename AAA BBB",cvfs);
        assertEquals("[[BBB txt hi], {111}]",cvfs.getDirectory().getDir().toString());
        define.define("rename 111 222",cvfs);
        assertEquals("[[BBB txt hi], {222}]",cvfs.getDirectory().getDir().toString());
    }

    /**
     * Test change directory
     */
    @Test
    public void testchangeDir(){
        Define define=new Define();
        CVFS cvfs=new CVFS();
        define.define("newDisk 1000",cvfs);
        define.define("newDoc AAA txt hi",cvfs);
        define.define("newDir 111",cvfs);
        define.define("changeDir 111",cvfs);
        define.define("newDoc BBB txt bye",cvfs);
        assertEquals("[[BBB txt bye]]",cvfs.getDirectory().getDir().toString());
        define.define("changeDir ..",cvfs);
        assertEquals("[[AAA txt hi], {111[BBB txt bye] }]",cvfs.getDirectory().getDir().toString());
    }

    /**
     * test if it can list all the files directly contained in the working directory
     */
    @Test
    public void testlist(){
        Define define=new Define();
        CVFS cvfs=new CVFS();
        define.define("newDisk 1000",cvfs);
        define.define("newDoc AAA txt hi",cvfs);
        define.define("newDir 111",cvfs);
        define.define("list",cvfs);
        assertEquals("Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:AAA.txt 44\r\n" +
                "111 40\r\n" +
                "The total number of files listed : 2\r\n" +
                "The total size of files listed : 84\r\n" +
                "Please enter your next command\r\n" +
                "$:", outContent.toString().trim());
    }

    /**
     * test if it can list all the files contained in the working directory
     */
    @Test
    public void testrlist(){
        Define define=new Define();
        CVFS cvfs=new CVFS();
        define.define("newDisk 1000",cvfs);
        define.define("newDoc AAA txt hi",cvfs);
        define.define("newDir 111",cvfs);
        define.define("changeDir 111",cvfs);
        define.define("newDir 222",cvfs);
        define.define("changeDir ..",cvfs);
        define.define("rList",cvfs);
        assertEquals("Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "111:Please enter your next command\r\n" +
                "111:Please enter your next command\r\n" +
                "$:AAA.txt 44\r\n" +
                "111 80\r\n" +
                "\t222 40\r\n" +
                "The total number of files listed : 3\r\n" +
                "The total size of files listed : 124\r\n" +
                "Please enter your next command\r\n" +
                "$:", outContent.toString().trim());
    }

    /**
     * test if it can construct a simple criterion that can be referenced by criName.
     */
    @Test
    public void testnewSimpleCri(){
        Define define=new Define();
        CVFS cvfs=new CVFS();
        define.define("newDisk 1000",cvfs);
        define.define("newSimpleCri HH name contains \"A\"",cvfs);
        assertEquals("(HH name contains \"A\" )",cvfs.getcri("HH").toString());
        define.define("newSimpleCri AA type equals \"html\"",cvfs);
        assertEquals("(AA type equals \"html\" )",cvfs.getcri("AA").toString());
        define.define("newSimpleCri BB size != 10",cvfs);
        assertEquals("(BB size != 10 )",cvfs.getcri("BB").toString());
        define.define("newSimpleCri CC size > 10",cvfs);
        assertEquals("(CC size > 10 )",cvfs.getcri("CC").toString());
        define.define("newSimpleCri DD size < 10",cvfs);
        assertEquals("(DD size < 10 )",cvfs.getcri("DD").toString());
        define.define("newSimpleCri EE size >= 10",cvfs);
        assertEquals("(EE size >= 10 )",cvfs.getcri("EE").toString());
        define.define("newSimpleCri FF size <= 10",cvfs);
        assertEquals("(FF size <= 10 )",cvfs.getcri("FF").toString());
        define.define("newSimpleCri GG size == 10",cvfs);
        assertEquals("(GG size == 10 )",cvfs.getcri("GG").toString());
    }

    /**
     * test if it can construct a composite criterion that can be referenced by criName1.
     */
    @Test
    public void testnewNegation(){
        Define define=new Define();
        CVFS cvfs=new CVFS();
        define.define("newDisk 1000",cvfs);
        define.define("newSimpleCri HH name contains \"A\"",cvfs);
        define.define("newNegation AA HH",cvfs);
        assertEquals("(AA (HH name contains \"A\" ))",cvfs.getcri("AA").toString());
    }

    /**
     * test if it can construct binary criterion that composite by two existing criteria.
     */
    @Test
    public void testnewBinaryCri(){
        Define define=new Define();
        CVFS cvfs=new CVFS();
        define.define("newDisk 1000",cvfs);
        define.define("newSimpleCri HH name contains \"A\"",cvfs);
        define.define("newSimpleCri AA name contains \"H\"",cvfs);
        define.define("newBinaryCri AH AA && HH",cvfs);
        assertEquals("(AH(AA name contains \"H\" )AND(HH name contains \"A\" ))",cvfs.getcri("AH").toString());
    }

    /**
     * test if it can print out all the criteria defined.
     */
    @Test
    public void testprintAllCriteria(){
        Define define=new Define();
        CVFS cvfs=new CVFS();
        define.define("newDisk 1000",cvfs);
        define.define("newSimpleCri HH name contains \"A\"",cvfs);
        define.define("newSimpleCri AA name contains \"H\"",cvfs);
        define.define("newNegation BB HH",cvfs);
        define.define("newBinaryCri AH AA && HH",cvfs);
        define.define("newBinaryCri HA AA || HH",cvfs);
        define.define("printAllCriteria",cvfs);
        assertEquals("Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:All Criteria:\r\n" +
                "IsDocument\r\n" +
                "name contains \"A\"\r\n" +
                "name contains \"H\"\r\n" +
                "!= name contains \"A\"\r\n" +
                "name contains \"H\" && name contains \"A\"\r\n" +
                "name contains \"H\" || name contains \"A\"\r\n" +
                "Please enter your next command\r\n" +
                "$:",outContent.toString().trim());
    }

    /**
     * test if it can list all the files directly contained in the working directory that satisfy criterion criName
     */
    @Test
    public void testsearch(){
        Define define=new Define();
        CVFS cvfs=new CVFS();
        define.define("newDisk 1000",cvfs);
        define.define("newSimpleCri HH name contains \"A\"",cvfs);
        define.define("newSimpleCri AA type equals \"html\"",cvfs);
        define.define("newSimpleCri BB size != 40",cvfs);
        define.define("newNegation NB BB",cvfs);
        define.define("newBinaryCri AH AA && HH",cvfs);
        define.define("newBinaryCri HA AA || HH",cvfs);
        define.define("newDoc A html www.com",cvfs);
        define.define("newDir A",cvfs);
        define.define("newDir AH",cvfs);
        define.define("newDoc H txt yo",cvfs);
        define.define("search IsDocument",cvfs);
        assertEquals("Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:A.html 54\r\n" +
                "H.txt 44\r\n" +
                "The total number of files listed : 2\r\n" +
                "The total size of files listed : 98\r\n" +
                "Please enter your next command\r\n" + "$:",outContent.toString().trim());
        define.define("search NB",cvfs);
        assertEquals("Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:A.html 54\r\n" +
                "H.txt 44\r\n" +
                "The total number of files listed : 2\r\n" +
                "The total size of files listed : 98\r\n" +
                "Please enter your next command\r\n" +
                "$:A 40\r\n" +
                "AH 40\r\n" +
                "The total number of files listed : 2\r\n" +
                "The total size of files listed : 80\r\n"+
                "Please enter your next command\r\n" +
                "$:",outContent.toString().trim());
        define.define("search AH",cvfs);
        assertEquals("Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:A.html 54\r\n" +
                "H.txt 44\r\n" +
                "The total number of files listed : 2\r\n" +
                "The total size of files listed : 98\r\n" +
                "Please enter your next command\r\n" +
                "$:A 40\r\n" +
                "AH 40\r\n" +
                "The total number of files listed : 2\r\n" +
                "The total size of files listed : 80\r\n" +
                "Please enter your next command\r\n" +
                "$:A.html 54\r\n" +
                "The total number of files listed : 1\r\n" +
                "The total size of files listed : 54\r\n"+
                "Please enter your next command\r\n" +
                "$:",outContent.toString().trim());
        define.define("search HA",cvfs);
        assertEquals("Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:A.html 54\r\n" +
                "H.txt 44\r\n" +
                "The total number of files listed : 2\r\n" +
                "The total size of files listed : 98\r\n" +
                "Please enter your next command\r\n" +
                "$:A 40\r\n" +
                "AH 40\r\n" +
                "The total number of files listed : 2\r\n" +
                "The total size of files listed : 80\r\n" +
                "Please enter your next command\r\n" +
                "$:A.html 54\r\n" +
                "The total number of files listed : 1\r\n" +
                "The total size of files listed : 54\r\n" +
                "Please enter your next command\r\n" +
                "$:A.html 54\r\n" +
                "A 40\r\n" +
                "AH 40\r\n" +
                "The total number of files listed : 3\r\n" +
                "The total size of files listed : 134\r\n" +
                "Please enter your next command\r\n" +
                "$:",outContent.toString().trim());
    }

    /**
     * test if it can list all the files contained in the working directory that satisfy criterion criName.
     */
    @Test
    public void testrSearch(){
        Define define=new Define();
        CVFS cvfs=new CVFS();
        define.define("newDisk 1000",cvfs);
        define.define("newSimpleCri HH name contains \"A\"",cvfs);
        define.define("newSimpleCri AA type equals \"html\"",cvfs);
        define.define("newSimpleCri BB size != 40",cvfs);
        define.define("newNegation NB BB",cvfs);
        define.define("newBinaryCri AH AA && HH",cvfs);
        define.define("newBinaryCri HA AA || HH",cvfs);
        define.define("newDoc A html www.com",cvfs);
        define.define("newDir A",cvfs);
        define.define("newDir AH",cvfs);
        define.define("newDoc H txt yo",cvfs);
        define.define("rSearch IsDocument",cvfs);
        assertEquals("Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:A.html 54\r\n" +
                "H.txt 44\r\n" +
                "The total number of files listed : 2\r\n" +
                "The total size of files listed : 98\r\n" +
                "Please enter your next command\r\n" +
                "$:",outContent.toString().trim());
        define.define("rSearch NB",cvfs);
        assertEquals("Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:A.html 54\r\n" +
                "H.txt 44\r\n" +
                "The total number of files listed : 2\r\n" +
                "The total size of files listed : 98\r\n" +
                "Please enter your next command\r\n" +
                "$:A 40\r\n" +
                "AH 40\r\n" +
                "The total number of files listed : 2\r\n" +
                "The total size of files listed : 80\r\n" +
                "Please enter your next command\r\n" +
                "$:",outContent.toString().trim());
        define.define("rSearch AH",cvfs);
        assertEquals("Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:A.html 54\r\n" +
                "H.txt 44\r\n" +
                "The total number of files listed : 2\r\n" +
                "The total size of files listed : 98\r\n" +
                "Please enter your next command\r\n" +
                "$:A 40\r\n" +
                "AH 40\r\n" +
                "The total number of files listed : 2\r\n" +
                "The total size of files listed : 80\r\n" +
                "Please enter your next command\r\n" +
                "$:A.html 54\r\n" +
                "The total number of files listed : 1\r\n" +
                "The total size of files listed : 54\r\n" +
                "Please enter your next command\r\n" +
                "$:",outContent.toString().trim());
        define.define("rSearch HA",cvfs);
        assertEquals("Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:A.html 54\r\n" +
                "H.txt 44\r\n" +
                "The total number of files listed : 2\r\n" +
                "The total size of files listed : 98\r\n" +
                "Please enter your next command\r\n" +
                "$:A 40\r\n" +
                "AH 40\r\n" +
                "The total number of files listed : 2\r\n" +
                "The total size of files listed : 80\r\n" +
                "Please enter your next command\r\n" +
                "$:A.html 54\r\n" +
                "The total number of files listed : 1\r\n" +
                "The total size of files listed : 54\r\n" +
                "Please enter your next command\r\n" +
                "$:A.html 54\r\n" +
                "A 40\r\n" +
                "AH 40\r\n" +
                "The total number of files listed : 3\r\n" +
                "The total size of files listed : 134\r\n" +
                "Please enter your next command\r\n" +
                "$:",outContent.toString().trim());
    }

    /**
     * test if it can search all logic op in the criteria.
     */
    @Test
    public void testsearchtype() {
        Define define=new Define();
        CVFS cvfs=new CVFS();
        define.define("newDisk 1000",cvfs);
        define.define("newSimpleCri AA size > 30",cvfs);
        define.define("newSimpleCri BB size < 100",cvfs);
        define.define("newSimpleCri CC size >= 40",cvfs);
        define.define("newSimpleCri DD size <= 40",cvfs);
        define.define("newSimpleCri EE size == 40",cvfs);
        define.define("newDir 123",cvfs);
        define.define("search AA",cvfs);
        assertEquals("Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:123 40\r\n" +
                "The total number of files listed : 1\r\n" +
                "The total size of files listed : 40\r\n" +
                "Please enter your next command\r\n" +
                "$:",outContent.toString().trim());
        define.define("search BB",cvfs);
        assertEquals("Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:123 40\r\n" +
                "The total number of files listed : 1\r\n" +
                "The total size of files listed : 40\r\n" +
                "Please enter your next command\r\n" +
                "$:123 40\r\n" +
                "The total number of files listed : 1\r\n" +
                "The total size of files listed : 40\r\n" +
                "Please enter your next command\r\n" +
                "$:",outContent.toString().trim());
        define.define("search CC",cvfs);
        assertEquals("Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:123 40\r\n" +
                "The total number of files listed : 1\r\n" +
                "The total size of files listed : 40\r\n" +
                "Please enter your next command\r\n" +
                "$:123 40\r\n" +
                "The total number of files listed : 1\r\n" +
                "The total size of files listed : 40\r\n" +
                "Please enter your next command\r\n" +
                "$:123 40\r\n" +
                "The total number of files listed : 1\r\n" +
                "The total size of files listed : 40\r\n" +
                "Please enter your next command\r\n" +
                "$:",outContent.toString().trim());
        define.define("search DD",cvfs);
        assertEquals("Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:123 40\r\n" +
                "The total number of files listed : 1\r\n" +
                "The total size of files listed : 40\r\n" +
                "Please enter your next command\r\n" +
                "$:123 40\r\n" +
                "The total number of files listed : 1\r\n" +
                "The total size of files listed : 40\r\n" +
                "Please enter your next command\r\n" +
                "$:123 40\r\n" +
                "The total number of files listed : 1\r\n" +
                "The total size of files listed : 40\r\n" +
                "Please enter your next command\r\n" +
                "$:123 40\r\n" +
                "The total number of files listed : 1\r\n" +
                "The total size of files listed : 40\r\n" +
                "Please enter your next command\r\n" +
                "$:",outContent.toString().trim());
        define.define("search EE",cvfs);
        assertEquals("Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:Please enter your next command\r\n" +
                "$:123 40\r\n" +
                "The total number of files listed : 1\r\n" +
                "The total size of files listed : 40\r\n" +
                "Please enter your next command\r\n" +
                "$:123 40\r\n" +
                "The total number of files listed : 1\r\n" +
                "The total size of files listed : 40\r\n" +
                "Please enter your next command\r\n" +
                "$:123 40\r\n" +
                "The total number of files listed : 1\r\n" +
                "The total size of files listed : 40\r\n" +
                "Please enter your next command\r\n" +
                "$:123 40\r\n" +
                "The total number of files listed : 1\r\n" +
                "The total size of files listed : 40\r\n" +
                "Please enter your next command\r\n" +
                "$:123 40\r\n" +
                "The total number of files listed : 1\r\n" +
                "The total size of files listed : 40\r\n" +
                "Please enter your next command\r\n" +
                "$:",outContent.toString().trim());
    }

    /**
     * test the command is valid
     */
    @Test
    public void testCommand() {
        boolean thrown = false;
        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("hi",cvfs);
        } catch (IllegalFormatWidthException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    /**
     * test the length of command is suitable
     */
    @Test
    public void testCommandSuitable() {
        boolean thrown = false;
        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newDir AAA 111",cvfs);
        } catch (IllegalFormatWidthException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    /**
     * test the length of name (each file name may have at most 10 characters)
     */
    @Test
    public void testNameSize() {
        boolean docthrown = false;
        boolean dirthrown = false;
        boolean renamethrown = false;

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newDoc 12345678910 txt hi",cvfs);
        } catch (IllegalThreadStateException e) {
            docthrown = true;
        }
        assertTrue(docthrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newDir 12345678910",cvfs);
        } catch (IllegalThreadStateException e) {
            dirthrown = true;
        }
        assertTrue(dirthrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newDoc AAA txt hi",cvfs);
            define.define("rename AAA 12345678910",cvfs);
        } catch (IllegalThreadStateException e) {
            renamethrown = true;
        }
        assertTrue(renamethrown);
    }

    /**
     * test the name is only containing digits and English letters
     */
    @Test
    public void testName() {
        boolean docthrown = false;
        boolean dirthrown = false;
        boolean deletethrown = false;
        boolean renamethrown = false;

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newDoc A! txt hi",cvfs);
        } catch (IllegalFormatCodePointException e) {
            docthrown = true;
        }
        assertTrue(docthrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newDir A!",cvfs);
        } catch (IllegalFormatCodePointException e) {
            dirthrown = true;
        }
        assertTrue(dirthrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newDir AAA",cvfs);
            define.define("delete A!",cvfs);
        } catch (IllegalFormatCodePointException e) {
            deletethrown = true;
        }
        assertTrue(deletethrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newDoc AAA txt hi",cvfs);
            define.define("rename AAA B!",cvfs);
        } catch (IllegalFormatCodePointException e) {
            renamethrown = true;
        }
        assertTrue(renamethrown);
    }

    /**
     * test the type of document is only txt, java, html, and css
     */
    @Test
    public void testDocType() {
        boolean thrown = false;

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newDoc AAA txy hi",cvfs);
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    /**
     * test the files if reaching the maximum size of the disk
     */
    @Test
    public void testSpace() {
        boolean docthrown = false;
        boolean dirthrown = false;

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 50",cvfs);
            define.define("newDoc AAA txt hi12345678910",cvfs);
            define.define("newDoc BBB txt bye12345678910",cvfs);
        } catch (IllegalStateException e) {
            docthrown = true;
        }
        assertTrue(docthrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 30",cvfs);
            define.define("newDir 111",cvfs);
        } catch (IllegalStateException e) {
            dirthrown = true;
        }
        assertTrue(dirthrown);
    }

    /**
     * test the command in newDisk is valid, check if it is integer
     */
    @Test
    public void testDisk() {
        boolean thrown = false;
        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk A",cvfs);
        } catch (IllegalAccessError e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    /**
     * test the command "rename" and "delete" is renaming or deleting the existing file
     */
    @Test
    public void testContain() {
        boolean deletedocthrown = false;
        boolean deletedirthrown = false;
        boolean renamethrown = false;
        boolean changedirthrown = false;

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newDoc AAA txt hi",cvfs);
            define.define("delete BBB",cvfs);
        } catch (IllegalFormatPrecisionException e) {
            deletedocthrown = true;
        }
        assertTrue(deletedocthrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newDir 111",cvfs);
            define.define("delete 222",cvfs);
        } catch (IllegalFormatPrecisionException e) {
            deletedirthrown = true;
        }
        assertTrue(deletedirthrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newDoc AAA txt hi",cvfs);
            define.define("rename BBB CCC",cvfs);
        } catch (IllegalFormatPrecisionException e) {
            renamethrown = true;
        }
        assertTrue(renamethrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newDir 111",cvfs);
            define.define("changeDir 222",cvfs);
        } catch (IllegalFormatPrecisionException e) {
            changedirthrown = true;
        }
        assertTrue(changedirthrown);
    }

    /**
     * test the criteria name is contain only two Englist letters.
     */
    @Test
    public void testCriName() {
        boolean simlengththrown = false;
        boolean simtypethrown = false;
        boolean neglengththrown = false;
        boolean negtypethrown = false;
        boolean binlengththrown = false;
        boolean bintypethrown = false;

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newSimpleCri ABC name contains \"A\"",cvfs);
        } catch (IllegalArgumentException e) {
            simlengththrown = true;
        }
        assertTrue(simlengththrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newSimpleCri 11 contains \"A\"",cvfs);
        } catch (IllegalArgumentException e) {
            simtypethrown = true;
        }
        assertTrue(simtypethrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newSimpleCri AA name contains \"A\"",cvfs);
            define.define("newNegation NAA AA",cvfs);
        } catch (IllegalArgumentException e) {
            neglengththrown = true;
        }
        assertTrue(neglengththrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newSimpleCri AA name contains \"A\"",cvfs);
            define.define("newNegation 1A AA",cvfs);
        } catch (IllegalArgumentException e) {
            negtypethrown = true;
        }
        assertTrue(negtypethrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newSimpleCri AA name contains \"A\"",cvfs);
            define.define("newSimpleCri BB name contains \"B\"",cvfs);
            define.define("newBinaryCri ABB AA && BB",cvfs);
        } catch (IllegalArgumentException e) {
            binlengththrown = true;
        }
        assertTrue(binlengththrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newSimpleCri AA name contains \"A\"",cvfs);
            define.define("newSimpleCri BB name contains \"B\"",cvfs);
            define.define("newBinaryCri 22 AA && BB",cvfs);
        } catch (IllegalArgumentException e) {
            bintypethrown = true;
        }
        assertTrue(bintypethrown);
    }

    /**
     * test the criteria type is only "name", "type" and "size"
     */
    @Test
    public void testCritype() {
        boolean namethrown = false;
        boolean typethrown = false;
        boolean sizethrown = false;
        boolean notthrown = false;
        boolean name2thrown = false;
        boolean type2thrown = false;
        boolean size2thrown = false;

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newSimpleCri AA name have \"A\"",cvfs);
        } catch (IllegalArgumentException e) {
            namethrown = true;
        }
        assertTrue(namethrown);
        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newSimpleCri AA name contains A",cvfs);
        } catch (IllegalArgumentException e) {
            name2thrown = true;
        }
        assertTrue(name2thrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newSimpleCri BB type same \"txt\"",cvfs);
        } catch (IllegalArgumentException e) {
            typethrown = true;
        }
        assertTrue(typethrown);
        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newSimpleCri BB type equals txt",cvfs);
        } catch (IllegalArgumentException e) {
            type2thrown = true;
        }
        assertTrue(type2thrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newSimpleCri CC size = 40",cvfs);
        } catch (IllegalArgumentException e) {
            sizethrown = true;
        }
        assertTrue(sizethrown);
        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newSimpleCri CC size == A",cvfs);
        } catch (IllegalArgumentException e) {
            size2thrown = true;
        }
        assertTrue(size2thrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newSimpleCri AA content include \"hi\"",cvfs);
        } catch (IllegalArgumentException e) {
            notthrown = true;
        }
        assertTrue(notthrown);
    }

    /**
     * test whether the criteria are created before.
     */
    @Test
    public void testNoCri() {
        boolean negthrown = false;
        boolean binthrown = false;
        boolean searchthrown = false;
        boolean rsearchthrown = false;

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newSimpleCri AA name contains \"A\"",cvfs);
            define.define("newNegation BB CC",cvfs);
        }
        catch (IllegalPathStateException e) {
            negthrown = true;
        }
        assertTrue(negthrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newSimpleCri AA name contains \"A\"",cvfs);
            define.define("newBinaryCri BB CC && DD",cvfs);
        }
        catch (IllegalPathStateException e) {
            binthrown = true;
        }
        assertTrue(binthrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newSimpleCri AA name contains \"A\"",cvfs);
            define.define("search BB",cvfs);
        }
        catch (IllegalPathStateException e) {
            searchthrown = true;
        }
        assertTrue(searchthrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newSimpleCri AA name contains \"A\"",cvfs);
            define.define("rSearch BB",cvfs);
        }
        catch (IllegalPathStateException e) {
            rsearchthrown = true;
        }
        assertTrue(rsearchthrown);
    }

    /**
     * test whether the criteria are created already.
     */
    @Test
    public void testRepeatCri() {
        boolean negthrown = false;
        boolean binthrown = false;
        boolean simthrown = false;

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newSimpleCri AA name contains \"A\"",cvfs);
            define.define("newSimpleCri AA type equals \"java\"",cvfs);
        }
        catch (IllegalPathStateException e) {
            simthrown = true;
        }
        assertTrue(simthrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newSimpleCri AA name contains \"A\"",cvfs);
            define.define("newNegation AA AA",cvfs);
        }
        catch (IllegalPathStateException e) {
            negthrown = true;
        }
        assertTrue(negthrown);

        try {
            Define define=new Define();
            CVFS cvfs=new CVFS();
            define.define("newDisk 1000",cvfs);
            define.define("newSimpleCri AA name contains \"A\"",cvfs);
            define.define("newSimpleCri BB type equals \"java\"",cvfs);
            define.define("newBinaryCri BB AA && BB",cvfs);
        }
        catch (IllegalPathStateException e) {
            binthrown = true;
        }
        assertTrue(binthrown);
    }
}