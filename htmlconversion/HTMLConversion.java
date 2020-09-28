
package htmlconversion;

import java.io.*;
import java.util.*;
public class HTMLConversion {

   public static String readFile() throws FileNotFoundException {
       File file = new File("TestFile.html");
       String s = "";
       
       Scanner inputScanner = new Scanner(file);
       while(inputScanner.hasNext()){
           s = s + inputScanner.next() + " ";
       }
       
       return s;
   }
   
    public static void writeText(String s, PrintWriter outputWriter) throws FileNotFoundException{
        String newS = s;
        for(int i = 0; i < s.length()/80; i++){
            //newS = newS + s.substring(i*80,(i+1)*80) + "\n";
        }
        //newS = newS + "\n";
        outputWriter.println(newS);
    }
    public static void writeText(String s, PrintWriter outputWriter, boolean centered) throws FileNotFoundException{
        if(!centered){
            writeText(s, outputWriter);
            return;
        }
        
        for(int i = 0; i < (80-s.length())/2; i++){
            s = " " + s + " ";
        }
        outputWriter.println(s);
    }  
    
   
   public static void nextTag(String s, PrintWriter outputWriter) throws FileNotFoundException{
       if(s.length() <= 0){
           return;
       }
       
        int openTag = s.indexOf('<');
        int endOpenTag = s.indexOf('>');

        String tag = s.substring(openTag, endOpenTag+1);
        String text;
        int closeTag;
        switch(tag){
            case "<h1>":
            case "<h2>":
            case "<h3>":
            case "<h4>":
            case "<h5>":
            case "<h6>":
            case "<title>":
                outputWriter.println();
                if(s.substring(1).contains("<")){
                    closeTag = s.substring(1).indexOf("<");
                }else{
                    closeTag = s.length();
                }
               
                text = s.substring(endOpenTag+1,closeTag+1);
                if(text.compareTo("") != 0)
                    writeText(text.toUpperCase(), outputWriter, true);
                break;
                
            case "<p>":
                outputWriter.println();
            case "<td>":
            case "<th>":
            case "<li>":
                if(s.substring(1).contains("<")){
                    closeTag = s.substring(1).indexOf("<");
                }else{
                    closeTag = s.length();
                }
               
                text = s.substring(endOpenTag+1,closeTag+1);
                if(text.compareTo("") != 0)
                    writeText(text, outputWriter);
                break;
        }
        if(!s.contains("<"))
            return;
        int nextOpenTag = s.indexOf('<',endOpenTag);
        
        if(nextOpenTag <= 0)
            return;
        
        s = s.substring(nextOpenTag);
        nextTag(s, outputWriter);
   }
   
    public static void main(String[] args) throws FileNotFoundException {
        File outputFile = new File("Output.txt");
        PrintWriter outputWriter = new PrintWriter(outputFile);
        
        String fileString = readFile();
        nextTag(fileString, outputWriter);
        
        outputWriter.close();
    }
    
}
