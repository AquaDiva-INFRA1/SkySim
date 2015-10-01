/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifis.skysim2.experiments;

/**
 *
 * @author fish
 */
public class CreateObjects {

	public static void main(String[] args) {
            int n = 100;        //generate n integers to the file
            int range = 7771;   //those integers ranges from 1 to [range]
            String fileName="./data/existObjects.txt";
            String s="";
            for(int i=0;i<n;i++){
                s=s+(int)(Math.random()*range+1)+" ";
            }
            SimpleFileWriter sw = new SimpleFileWriter(fileName);
            sw.appendToNextLine(s);
            sw.close();	
	}
}
