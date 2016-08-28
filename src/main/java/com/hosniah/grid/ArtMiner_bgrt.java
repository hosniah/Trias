/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hosniah.grid;

import java.util.Arrays;


/**
 *
 * @author hosniah
 * @param 
 */
public class ArtMiner_bgrt { 

    private final String premise;
    private final String conclusion;
    public final String frequentTriconcept;
    public final int conditionalTriadicSupport;
    public final int conditionalTriadicConfidence;
    public boolean belongsToBGRT;

    public ArtMiner_bgrt(String premise, String conclusion, String[] FilesOfRA, String[] tasks, String[] sites) {
        super();
            this.premise                      = null;
            this.conclusion                   = null;
            this.frequentTriconcept           = "Tasks = "+Arrays.toString(tasks)+", Files = "+Arrays.toString(FilesOfRA)+", Sites ="+Arrays.toString(sites);
            this.conditionalTriadicSupport    = 0;
            this.conditionalTriadicConfidence = 0;
            this.belongsToBGRT                = false;

    }

    public double computeConditionalTriadicSupport() {
        return 0;        
    }

    public double computeConditionalTriadicConfidence() {
        return 0;        
    }

    public void addToBGRT() {
        this.belongsToBGRT  = true;
    }
}
