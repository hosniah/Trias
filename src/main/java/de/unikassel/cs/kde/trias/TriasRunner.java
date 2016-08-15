/**
 *  
 *  Trias Algorithm - Trias is an algorithm for computing triadic concepts which
 * 		fulfill minimal support constraints.
 *   
 *  Copyright (C) 2006 - 2009 Knowledge & Data Engineering Group, 
 *                            University of Kassel, Germany
 *                            http://www.kde.cs.uni-kassel.de/
 *  
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.unikassel.cs.kde.trias;

import com.hosniah.grid.ArtMiner;
import de.unikassel.cs.kde.trias.io.TriConceptReader;
import de.unikassel.cs.kde.trias.model.TriConcept;
import de.unikassel.cs.kde.trias.neighborhoods.TriNeighborhoodRunner;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import de.unikassel.cs.kde.trias.util.ConfigurationException;
import static de.unikassel.cs.kde.trias.util.Dimension.U;
import de.unikassel.cs.kde.trias.util.TriasCommandLineArgumentsConfigurator;
import de.unikassel.cs.kde.trias.util.TriasConfigurator;
import de.unikassel.cs.kde.trias.util.TriasPropertiesConfigurator;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author:  rja
 * @version: $Id: TriasRunner.java,v 1.3 2009-04-23 06:53:17 rja Exp $
 * $Author: rja $
 * 
 */
public class TriasRunner {

	private static final String PROPERTIES_FILE_NAME = "trias.properties";
	private static final String TRICONCEPTS_FILE_NAME = "concepts";
        private static int gridFilesCount;

	public static void main(String[] args) throws IOException {
		
		final Trias trias = new Trias();
		TriasConfigurator config;

		if (args.length > 0) {
			/*
			 * use command line arguments for configuration
			 */
			config = new TriasCommandLineArgumentsConfigurator(args);
		} else {
			final InputStream resourceAsStream = TriasRunner.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
			if (resourceAsStream == null) {
				System.err.println("Could not find file '" + PROPERTIES_FILE_NAME + "' for configuration.");
				System.exit(1);
			}

			final Properties prop = new Properties();
			prop.load(resourceAsStream);
			config = new TriasPropertiesConfigurator(prop);
			
		}
		System.err.println(config.usage());
		

		/*
		 * configure trias
		 */
		try {
			config.configureTrias(trias);
		} catch (final ConfigurationException e) {
			System.err.println("Could not configure Trias: " + e);
			System.err.println(config.usage());
			System.exit(1);
		}

		trias.doWork();
                
                
            /* Triconcepts are retrieved, we create groups of tasks and their matching vectors */
        int[] gridHistoryDimensions = trias.getNumberOfItemsPerDimension();        
         TriasRunner.gridFilesCount = gridHistoryDimensions[1];
                   try {
                  getTriLatticeBufferedReader();
                   }catch(Exception ex) {
                Logger.getLogger(TriasRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
        
public static void getTriLatticeBufferedReader() throws FileNotFoundException, Exception {
	// Open the file
FileInputStream fstream = new FileInputStream("concepts");
BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

String strLine;

            try {
                //Read File Line By Line
                while ((strLine = br.readLine()) != null)   {
                    // Print the content on the console
                    String result = strLine.substring(strLine.indexOf("{") + 1, strLine.indexOf("}"));
                  //  System.out.println (strLine);
                   // System.out.println (result);
                    String[] groupOfTasks = result.split("\\s*,\\s*");
                    buildGroupOfTasksVector(groupOfTasks);
                    //ArtMiner rat = new ArtMiner(groupOfTasks);
                }      
            } catch (IOException ex) {
                Logger.getLogger(TriasRunner.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                //Close the input stream
                br.close(); } catch (IOException ex) {
                Logger.getLogger(TriasRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
		
	}
       
    private static void buildGroupOfTasksVector(String[] groupOfTasks) throws FileNotFoundException, Exception {            
        System.out.println(Arrays.toString(groupOfTasks));
        List valid = Arrays.asList(groupOfTasks);
        // init Vector              
        HashMap<String, Integer> h = new HashMap<String, Integer>(); 
        for (int i = 1; i <= TriasRunner.gridFilesCount; i++) {
            h.put("F"+i, 0);
        }      
        
            try {
                FileInputStream fstream = new FileInputStream("fixture.input");
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                String strLine;
                while ((strLine = br.readLine()) != null)   {        
                    Pattern pattern = Pattern.compile("(\\d+)\\s+"); //$NON-NLS-1$
                    Matcher matcher = pattern.matcher(strLine);
                    List<Integer> numbers = new LinkedList<Integer>();

                    while (matcher.find()) {
                        numbers.add(Integer.valueOf(matcher.group(1)));
                    }
                    Integer[] output = numbers.toArray(new Integer[numbers.size()]);
                                  //              System.out.println(Arrays.toString(output));

                    // triConcepts = runner.readConceptsFromFile();
                   // if(Arrays.asList(groupOfTasks).contains(Integer.toString(output[0]))) {
                         if(valid.contains(Integer.toString(output[0]))) {
                             h.put("F"+output[1], h.get("F"+output[1]) + 1);
     
                         }

                   // }

                }
                System.out.println(h.toString());

            } catch (IOException ex) {
                Logger.getLogger(TriasRunner.class.getName()).log(Level.SEVERE, null, ex);
            }        
        //To change body of generated methods, choose Tools | Templates.
    }
}

