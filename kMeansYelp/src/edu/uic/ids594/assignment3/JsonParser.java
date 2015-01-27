package edu.uic.ids594.assignment3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.StringTokenizer;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonParser {

	// create variables to store the file paths
	private static final String filePath = "/Users/himansubadhai/Documents/input/assignment3/JsonSample.txt";
	private static final String parserOutput ="/Users/himansubadhai/Documents/input/assignment3/ParserOutput/";
	private static final String stopWords ="/Users/himansubadhai/Documents/input/assignment3/StopWords.txt";

	//initialize variables
	private HashSet<String> hashSet = new HashSet<String>();
	private int lineCount;
	private BufferedReader reader;
	private PrintWriter printWriter;

	// method to read stop words and store in hashset
	public void readStopWords (){

		try {
			BufferedReader reader=new BufferedReader(new FileReader(stopWords));

			//read line by line
			String line=reader.readLine().trim(); 

			while(line!=null){		
				// add the value in hashset
				this.hashSet.add(line.trim());
				line = reader.readLine(); 
			}
			System.out.println("Stop words added to hashset");
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}

	// method to read Json File
	public void readJson (){
		try {
			// read the json file
			FileReader fileReader = new FileReader(filePath);
			reader = new BufferedReader(fileReader);
			String data="";

			// read only first 40 Json objects
			for(lineCount = 0; lineCount < 40; lineCount++){

				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(reader.readLine());

				// get the text from the JSON object
				String text = (String) jsonObject.get("text");

				// take each value from the json array separately
				StringTokenizer tokens = new StringTokenizer(text," ,.:;\"().?<>!-/%~#$^&*1234567890");

				// construct the actual data
				while(tokens.hasMoreTokens()){

					String token= tokens.nextToken();

					if(!(hashSet.contains(token.trim().toLowerCase()))){
						data = data +token+" ";
					}

				}
				File file = new File ("");

				file=new File(parserOutput+"output_"+lineCount+".txt");
				
				// write to file
				printWriter=new PrintWriter(file);
				printWriter.println(data);
				
				// set the data to null before writing to next file
				data ="";
				printWriter.close();
			}
			reader.close();

		} catch (FileNotFoundException ex) {

			ex.printStackTrace();

		} catch (IOException ex) {

			ex.printStackTrace();

		} catch(ParseException ex){

			ex.printStackTrace();

		} catch (Exception ex) {

			ex.printStackTrace();

		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonParser jsonParser = new JsonParser();
		jsonParser.readStopWords();
		jsonParser.readJson();

	}
}

