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
import java.io.FileWriter;
import java.io.BufferedWriter;

public class SimpleFileWriter {
	private FileWriter fileWriter;
	private BufferedWriter bufferedWriter;
	public SimpleFileWriter(String fileName)
	{
		try{
		fileWriter = new FileWriter(fileName);
		bufferedWriter = new BufferedWriter(fileWriter);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void appendToNextLine(String s)
	{
		try{
			bufferedWriter.write(s);
			bufferedWriter.newLine();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void close()
	{
		try{
			bufferedWriter.close();
			fileWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		SimpleFileReader s = new SimpleFileReader("a.txt");
		SimpleFileWriter w = new SimpleFileWriter("b.txt");
		String ss;
		while((ss=s.readNextLine())!=null){
			System.out.println(ss);
			w.appendToNextLine(ss);
		}
		s.close();
		w.close();
	}
}
