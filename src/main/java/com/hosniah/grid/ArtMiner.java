/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hosniah.grid;

import java.io.Serializable;
import java.util.Iterator;

/**
 *
 * @author hosniah
 * @param <T>
 */
public class ArtMiner<T extends Comparable<T>> implements Serializable, Iterable<T[]>, Comparable<ArtMiner<T>> { 
    public String[] gridObjects;
    public  static int TCSIZE;
    public ArtMiner() {
            super();
            this.gridObjects = (String[]) (T[]) new String[TCSIZE];
    }

    public ArtMiner(String[] groupOfTasks) {
		this();
		this.gridObjects = groupOfTasks;
               // System.out.println();
		
	}

    public Iterator<T[]> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int compareTo(ArtMiner<T> o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
