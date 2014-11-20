/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacco.classes;

import java.sql.Connection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Antony
 */
public class DatabaseTest {
    
    public DatabaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getDBConnection method, of class Database.
     */
    @Test
    public void testGetDBConnection() {
        System.out.println("getDBConnection");
        Connection result = Database.getDBConnection();
        assertNotNull(result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of close method, of class Database.
     */
    @Test
    public void testClose() throws Exception {
        System.out.println("close");
        Connection instance = new Database().getConnection();
        instance.close();
        // TODO review the generated test code and remove the default call to fail.
        assertNull(instance);
    }
    
}
