package net.wisedream.jfiler;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.wisedream.jfiler.util.StreamUtil;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    public void testMd5() throws Exception {
    	File f = new File("pom.xml");
    	
    	String md5 = StreamUtil.convertToHexString(StreamUtil.calcMD5(f));
    	System.out.println(md5);
    	assertEquals(md5, "ff842dc096fa7eebdd36499ca66299a6");
    }
}
