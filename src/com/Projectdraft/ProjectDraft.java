/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Projectdraft;

/**
 *
 * @author CJ
 */
public class ProjectDraft {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        com.sacco.classes.database d = new com.sacco.classes.database();
        System.out.println(d.DBConnection);
    }

}
