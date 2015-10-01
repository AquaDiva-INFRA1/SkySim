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
import java.io.FileReader;
import java.io.BufferedReader;;

public class SimpleFileReader {
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	public SimpleFileReader(String fileName)
	{
		try{
		fileReader = new FileReader(fileName);
		bufferedReader = new BufferedReader(fileReader);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public String readNextLine()
	{
		String res;
		try{
			res=bufferedReader.readLine();
			return res;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public void close()
	{
		try{
			bufferedReader.close();
			fileReader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		SimpleFileReader s = new SimpleFileReader("a.txt");
		String ss;
		while((ss=s.readNextLine())!=null){
			System.out.println(ss);
		}
		s.close();
	}
}
