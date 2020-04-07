import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class TokenNizeType2 {
	public String filename = "src\\programming language.txt";//place source code
	
	ArrayList<String> lines = new ArrayList<String>();//hold the source code as an array list
	
	int count=0;
	
	String[] keyword= {"begin","bool","int",":=","if","then",//holds all the keywords in array
			"else","fi","while","do","od","print","or","and","not","=",
			"<","+","-","*","/","(",")","true","false","end",":"};
	
	String[] keyword1= {";",":","=","<","+","-","*","/","(",")",":="};//holds all keyword symbols
	
	String[] arrayId= {"q","w","e","r","t","y","u","i","o","p","a","s",//holds all id values
			"d","f","g","h","j","k","l","z","x","c","v","b","n","m","Q","W","E"
			,"R","T","Y","U","I","O","P","A","S","D","F",
			"G","H","J","K","L","Z","X","C","V","B","N","M"};
	
	String[] arrayNum= {"1","2","3","4","5","6","7","8","9","0"};//holds all number files
	
	String[] keywordSyntax= {"or","and","=","<","+","-","*","/","()","( )",")"};//begin with
	
	String[] keywordSyntax1= {"or","and","not","=","<","+","-","*","/",")"};//double up end
	
	String[] keywordSyntax12= {"or","and","not","=","<","+","-","*","/","("};//double up beginning
	
	String[] keywordSyntax2= {"or","and","not","=","<","+","-","*","/","("};//ends with
	
	String[] keywordSyntaxT= {"or","and","not","print","if","while"};//array for true and false syntax
	
	Boolean result=false;
	
	Boolean resultSyn=true;
	
	Boolean resultTF=false;
	
	int balCount=0;
	

	
	public String[] readLines() throws IOException //method to read the lines into string array
    {
        FileReader fileReader = new FileReader(filename); //reads .txt
         
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        ArrayList<String> lines = new ArrayList<String>();
        
        String line = null;
         
        while ((line = bufferedReader.readLine()) != null) //loops till the end of the line then stores it in lines
        {
            lines.add(line);//adds the line to array list
        }
         
        bufferedReader.close();
        
         
        return lines.toArray(new String[lines.size()]); //returns array list as string array
    }
	

	public void showAll() throws IOException {

         
        try
        {
            String[] lines = readLines();
         
            for (String line : lines) 
            {
                System.out.println(line);
            }
        }
        catch(IOException e)
        {
            // Print out the exception that occurred
            System.out.println("Unable to create "+filename+": "+e.getMessage());              
        }
       
	}
	
	public void look() throws Exception {//main method
		
		String[] lines= readLines();//stores array list as string array
		String[] lines1=null;
		String[] lines2=null;
		String[] curArray=null;
		

		
		for(int i=0; i< lines.length; i++) {//read line of code and put into string array
			
			if(lines[i].contains("//")) {//this is to remove the comment section of the code
				
				lines1=lines[i].toString().split("//");//seperates the comment from the main code
				
				for(int t=0; t<lines1.length-1;t++) {//this removes the comment
					
					String[] innerLine = lines1[t].toString().split("\\s+",-2);//makes array of each word by white space
					
					for(int k=0;k < innerLine.length; k++) {//read array of code and divide it by whitespace
						
						
						
						for(int l=0; l<keyword.length;l++) {//reads all the words element by element
						
							if (innerLine[k].contentEquals(keyword[l])) {//if their is a keyword it will reconize it
								
							System.out.println("< "+innerLine[k]+", keyword, Position "+ k+", line "+i+" >");
							result=true;
							
							
							}
							
									
						}
						
						if(!result) {//if it has no keyword then the statement switches to this line
							
							String curLine= innerLine[k];//hold current array of code
							
							
							curArray=curLine.split("\\s");
							
							//all of these if statements are for if the terminal is too close to the id or num but it only accounts for one	then split it into another array
							if(curLine.contains(":"))
								curArray=curLine.split(":");
							if(curLine.contains("="))
								curArray=curLine.split("=");
							if(curLine.contains(";"))
								curArray=curLine.split(";");
							if(curLine.contains(":=")) 
								curArray=curLine.split(":=");
							if(curLine.contains("<"))
								curArray=curLine.split("<");
							if(curLine.contains("+"))
								curArray=curLine.split("+");
							if(curLine.contains("*"))
								curArray=curLine.split("*");
							if(curLine.contains("-"))
								curArray=curLine.split("-");
							if(curLine.contains("/"))
								curArray=curLine.split("/");
							
							
							
							for(int r=0; r<curArray.length;r++) { //goes through another array to find id and num
							
								for(int q=0; q<arrayId.length;q++) {//for loop id
									if(curArray[r].startsWith(arrayId[q]))
										if(curArray[r].contentEquals("true")||curArray[r].contentEquals("false")) {//if an id is a keyword it will catch it
											System.out.println("< "+ curArray[r]+", keyword, Position "+ k+", line "+i+" >");
										}
										else {
											System.out.println("< "+ curArray[r]+", id, Position "+ k+", line "+i+" >");
										}
								}
							
								for(int w=0; w<arrayNum.length;w++) {
									if(curArray[r].startsWith(arrayNum[w]))//catches num
										System.out.println("< "+ curArray[r]+", num, Position "+ k+", line "+i+" >");
								}
							}
						}
						
						
						lines2 = innerLine[k].toString().split("");//turned each word into an array of chars
						
						for (int j=0; j< lines2.length; j++) {//reads character by character and check for errors
							
							Pattern p = Pattern.compile("[`~!@#$%^>&]");
						    Matcher m = p.matcher(lines2[j]);
						     
							if(m.find()==true) {
								
								throw new Exception("using in valid character on line: "+i+" position: "+k);
							}
								
						}
						
						
							
						result=false;//resets the result in case of keyword
						}
					
					for(int y=0; y<keywordSyntax.length;y++) {//checks to see if a keyword is at the beginning of the code  
						String line= lines1[t].replaceAll("\\s", "");// remove the white spaces so i can find the start of the line
						if(line.startsWith(keywordSyntax[y],0)) {
							throw new Exception("Syntactically false because of: "+ keywordSyntax[y]);
							
							
						}
					}
					
					for(int y=0; y<keywordSyntax2.length;y++) {//checks to see if the line of code ends with a invalid keyword 
						String line= lines1[t].replaceAll(";", "");// remove the ; at the end of the code so it can find the word
						if(line.endsWith(keywordSyntax2[y])) {
							throw new Exception("Syntactically: false because of: "+ keywordSyntax[y]);
							
						}
					}
					
					for(int y=0; y<keywordSyntax12.length;y++) {//two for loops checks to see if a keyword is followed by another keyword also checks if =) and (+  
						
						for(int u=0; u<keywordSyntax1.length;u++) {
						
							String line= lines1[t].replaceAll("\\s", "");
							
							if(line.contains(keywordSyntax12[y]+keywordSyntax1[u])) {
							
								throw new Exception("Syntactically: false because of: "+ keywordSyntax12[y]+"and "+ keywordSyntax1[u]);
								
						}
						
						
						}
					}
					
					if(lines1[t].contains("(")&&!lines1[t].contains(")")) {//if the code dont have (
						
						throw new Exception("Syntactically false because of uneven )");
						
						
					}	
					
					if(!lines1[t].contains("(")&&lines1[t].contains(")")) {//if the code dont have ) thorws false
						
						throw new Exception("Syntactically false because of uneven ( ");
						
						
					}
					
					if(lines1[t].contains("(")&&lines1[t].contains(")")) {//if it has both ( and ) breaks it down into a char array and counts the number of ( )
						
						String line= lines1[t].replaceAll("\\s", "");
						String[] bal=line.split("");
						
						for(int y=0; y<bal.length;y++) {
							if(bal[y].contains("("))
								balCount++;
							if(bal[y].contains(")"))
								balCount--;
						}
					}
					
					for(int y=0; y<arrayId.length;y++) {//checks to see if a id is after and before true and false  
						
						for(int u=0; u<arrayId.length;u++) {
						
							for(int o=0; o<keywordSyntaxT.length;o++) {
								
									
									String line= lines1[t].replaceAll("\\s", "");
									if(line.contains(keywordSyntaxT[o]+"true")) {
										resultTF=true;
									}
										if(!resultTF) {
											if(line.contains(arrayId[y]+"true")) { 
												throw new Exception("Syntactically false because of true is expecting ");
											}
											if(line.contains("true"+arrayId[y])) { 
												throw new Exception("Syntactically false because of true is expecting ");
											}
											if(line.contains(arrayId[u]+"true"+arrayId[y])) { 
												throw new Exception("Syntactically false because of true is expecting ");
											}
										}
									if(line.contains(keywordSyntaxT[o]+"false")) {
											resultTF=true;
									}
										if(!resultTF) {
											if(line.contains(arrayId[u]+"false"+arrayId[y])) { 
												throw new Exception("Syntactically false because of false is expecting something");
												}
											if(line.contains(arrayId[y]+"false")) { 
												throw new Exception("Syntactically false because of false is expecting something");
												}
											if(line.contains("false"+arrayId[y])) { 
												throw new Exception("Syntactically false because of false is expecting something");
												}
											}
								}
							}
								
					}
						
					for(int r=0;r<arrayNum.length;r++) {//checks to see if a id is after and before true and false
						
						String line= lines1[t].replaceAll("\\s", "");
						
						if(line.contains(arrayNum[r]+"false"))
							throw new Exception("Syntactically false because of false is expecting something");
						
						if(line.contains("false"+arrayNum[r]))
							throw new Exception("Syntactically false because of false is expecting something");
						
						if(line.contains(arrayNum[r]+"true"))
							throw new Exception("Syntactically false because of true is expecting something");
						
						if(line.contains("true"+arrayNum[r]))
							throw new Exception("Syntactically false because of true is expecting something");
						
					}
					
					if(balCount<0||balCount>0){//checks the count of the of () to see if they are unbalance
						
						throw new Exception("Syntactically false because of uneven ( )");
						
					}
					
					if(resultSyn) //if their is no errors print true
						System.out.println("Syntactically true");	
			
					if(lines1[t].contentEquals("end"))//if the last line ends the program
						System.out.println("end-of-program");
					}
					
					resultSyn=true;//resets to true		
					balCount=0;//resets the count for () balance
			}
			
			else {
				
				for(int y=0; y<keywordSyntax.length;y++) {
					String line= lines[i].replaceAll("\\s", "");
					if(line.startsWith(keywordSyntax[y],0)) {
						System.out.println("Syntactically: false");
					}
				}
				
				lines1 = lines[i].toString().split("\\s+",-2);//makes array of each word
			
			
			for(int k=0;k < lines1.length; k++) {//read array of code and divide it by whitespace
				
				
				for(int l=0; l<keyword.length;l++) {//reads all the words element by element
				
					if (lines1[k].contentEquals(keyword[l])) {
						
					System.out.println("< "+lines1[k]+", keyword, Position "+ k+", line "+i+" >");
					result=true;
					
					}
					
							
				}
				
				if(!result) {
					String curLine= lines1[k];
					
					
					curArray=curLine.split("\\s");
					
						
					if(curLine.contains(":"))
						curArray=curLine.split(":");
					if(curLine.contains("="))
						curArray=curLine.split("=");
					if(curLine.contains(";"))
						curArray=curLine.split(";");
					if(curLine.contains(":=")) 
						curArray=curLine.split(":=");
					if(curLine.contains("<"))
						curArray=curLine.split("<");
					if(curLine.contains("+"))
						curArray=curLine.split("+");
					if(curLine.contains("*"))
						curArray=curLine.split("*");
					if(curLine.contains("-"))
						curArray=curLine.split("-");
					if(curLine.contains("/"))
						curArray=curLine.split("/");
					
					
					
					for(int r=0; r<curArray.length;r++) {
					
						for(int q=0; q<arrayId.length;q++) {
							if(curArray[r].startsWith(arrayId[q]))
								if(curArray[r].contentEquals("true")||curArray[r].contentEquals("false")) {
									System.out.println("< "+ curArray[r]+", keyword, Position "+ k+", line "+i+" >");
								}
								else {
									System.out.println("< "+ curArray[r]+", id, Position "+ k+", line "+i+" >");
								}
						}
					
						for(int w=0; w<arrayNum.length;w++) {
							if(curArray[r].startsWith(arrayNum[w]))
								System.out.println("< "+ curArray[r]+", num, Position "+ k+", line "+i+" >");
						}
					}
				}
				
				
				lines2 = lines1[k].toString().split("");//turned each word into an array of chars
				
				for (int j=0; j< lines2.length; j++) {//reads character by character and check for errors
					
					Pattern p = Pattern.compile("[`~!@#$%^>&]");
				    Matcher m = p.matcher(lines2[j]);
				     
					if(m.find()==true) {
						
						throw new Exception("using in valid character on line: "+i+" position: "+k);
					}
						
				}
				
				
					
				result=false;	
				}
			
			for(int y=0; y<keywordSyntax.length;y++) {//checks to see if a keyword that needs to be followed by an expression starts the line of code  
				String line= lines[i].replaceAll("\\s", "");
				if(line.startsWith(keywordSyntax[y],0)) {
					System.out.println("Syntactically: false because of: "+ keywordSyntax[y]);
					resultSyn=false;
				}
			}
			
			for(int y=0; y<keywordSyntax12.length;y++) {//checks to see if a keyword that needs to be followed by an expression starts the line of code  
				
				for(int u=0; u<keywordSyntax1.length;u++) {
				
					String line= lines[i].replaceAll("\\s", "");
					
					if(line.contains(keywordSyntax12[y]+keywordSyntax1[u])) {
					
						throw new Exception("Syntactically false because of: "+ keywordSyntax12[y]+" and "+ keywordSyntax1[u]);
						
				}
				
				
				}
			}
			
			for(int y=0; y<arrayId.length;y++) {//checks to see if a id is after and before true and false  
				
				for(int u=0; u<arrayId.length;u++) {
				
					for(int o=0; o<keywordSyntaxT.length;o++) {
						
							
							String line= lines[i].replaceAll("\\s", "");
							if(line.contains(keywordSyntaxT[o]+"true")) {
								resultTF=true;
							}
								if(!resultTF) {
									if(line.contains(arrayId[y]+"true")) { 
										throw new Exception("Syntactically false because of true is expecting ");
									}
									if(line.contains("true"+arrayId[y])) { 
										throw new Exception("Syntactically false because of true is expecting ");
									}
									if(line.contains(arrayId[u]+"true"+arrayId[y])) { 
										throw new Exception("Syntactically false because of true is expecting ");
									}
								}
							if(line.contains(keywordSyntaxT[o]+"false")) {
									resultTF=true;
							}
								if(!resultTF) {
									if(line.contains(arrayId[u]+"false"+arrayId[y])) { 
										throw new Exception("Syntactically false because of false is expecting something");
										}
									if(line.contains(arrayId[y]+"false")) { 
										throw new Exception("Syntactically false because of false is expecting something");
										}
									if(line.contains("false"+arrayId[y])) { 
										throw new Exception("Syntactically false because of false is expecting something");
										}
									}
						}
					}
						
			}
				
			for(int r=0;r<arrayNum.length;r++) {//checks to see if a id is after and before true and false
				
				String line= lines[i].replaceAll("\\s", "");
				
				if(line.contains(arrayNum[r]+"false"))
					throw new Exception("Syntactically false because of false is expecting something");
				
				if(line.contains("false"+arrayNum[r]))
					throw new Exception("Syntactically false because of false is expecting something");
				
				if(line.contains(arrayNum[r]+"true"))
					throw new Exception("Syntactically false because of true is expecting something");
				
				if(line.contains("true"+arrayNum[r]))
					throw new Exception("Syntactically false because of true is expecting something");
				
			}
			
				
			
			
			
			for(int y=0; y<keywordSyntax2.length;y++) {//checks to see if the line of code ends with a invalid keyword 
				
				String line= lines[i].replaceAll(";", "");// remove the ; at the end of the code so it can find the word
				
				if(line.endsWith(keywordSyntax2[y])) {
					throw new Exception("Syntactically false because of: "+ keywordSyntax2[y]);

				}
			}
			
			if(lines[i].contains("(")&&!lines[i].contains(")")) {
				
				throw new Exception("Syntactically false because of uneven )");
			
				
			}
			
			if(!lines[i].contains("(")&&lines[i].contains(")")) {
				
				throw new Exception("Syntactically false because of uneven (");
				
				
			}
			
			if(lines[i].contains("(")&&lines[i].contains(")")) {
				
				String line= lines[i].replaceAll("\\s", "");
				String[] bal=line.split("");
				
				for(int y=0; y<bal.length;y++) {
					if(bal[y].contains("("))
						balCount++;
					if(bal[y].contains(")"))
						balCount--;
				}
			}
			
			if(balCount<0||balCount>0){
				
				throw new Exception("Syntactically false because of uneven ( )");

				
			}
				
			
			if(resultSyn)
				System.out.println("Syntactically true");
	
			if(lines[i].contentEquals("end"))
				System.out.println("end-of-program");
			
			balCount=0;
			}
			
		}
	}
}
			


