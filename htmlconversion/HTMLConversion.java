/*
    Project 1
    Garret Carmouche
    CS2365-001 Fall 2020
*/
package htmlconversion;

import java.io.*;
import java.util.*;
public class HTMLConversion {

    /*
        Prompt the user for the input HTML file path.
        Read the file into a single string.

        Input:
        Output:
        String - HTML content
    */
   public static String readFile() throws FileNotFoundException {
       System.out.println("Enter input HTML path");
       Scanner pathScanner = new Scanner(System.in);
       File file = new File(pathScanner.nextLine());
       String s = "";
       
       Scanner inputScanner = new Scanner(file);
       while(inputScanner.hasNext()){
           s = s + inputScanner.next() + " ";
       }
       
       return s;
   }
   
   /*
        Write a string into the output file
        Input:
        String s - String to be written to the file
        PrintWriter outputWriter- PrintWriter s will be printed to
        Output:
   */
    public static void writeText(String s, PrintWriter outputWriter) throws FileNotFoundException{
        for(int i = s.length(); i > 80; i -= 80){
            int end;
            if(s.length() <= 80){
                end = s.length();
            }else{
                end = 80;
            }
            outputWriter.println(s.substring(0,end));
            s = s.substring(end, s.length());
        }
        outputWriter.println(s);
        
    }
    
    /*
        Write a string into the output file
        Input:
        String s - String to be written to the file
        PrintWriter outputWriter- PrintWriter s will be printed to
        boolean centered - Defines whether or not the text will be printed centered on the line. If not, left justified.
        Output:
   */
    public static void writeText(String s, PrintWriter outputWriter, boolean centered) throws FileNotFoundException{
        if(!centered){
            writeText(s, outputWriter);
            return;
        }
        
        while(s.length() < 79){
            s = " " + s + " ";
        }
        outputWriter.println(s);
    }
    
    /*
        Write a string into the output file
        Input:
        String s - String to be written to the file
        PrintWriter outputWriter- PrintWriter s will be printed to
        boolean centered - Defines whether or not the text will be printed centered on the line. If not, left justified.
        boolean skipNextLine - Defines whether the line after s will be skipped.
        Output:
   */
    public static void writeText(String s, PrintWriter outputWriter, boolean centered, boolean skipNextLine) throws FileNotFoundException{
        writeText(s, outputWriter, centered);
        if(skipNextLine){
            outputWriter.println();
        }
    }
    
    /*
        Recursively iterates through every open tag in the file to determine its behavior and whether it should be output to the txt file
        Input:
        String s - String containing HTML data
        PrintWriter outputWriter - outputWriter s will be written to
        Output:
    */
   public static void nextTag(String s, PrintWriter outputWriter) throws FileNotFoundException{
       if(s.length() <= 0){
           return;
       }
       
        int openTag = s.indexOf('<');
        int endOpenTag = s.indexOf('>');

        String tag = s.substring(openTag, endOpenTag+1);
        String text;
        int closeTag;
        boolean skipNextLine = false;
        switch(tag){
            case "<h1>":
            case "<h2>":
            case "<h3>":
            case "<h4>":
            case "<h5>":
            case "<h6>":
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
                skipNextLine = true;
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
                    writeText(text, outputWriter, false, skipNextLine);
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
   
   /*
        Create output file, PrintWriter, invokes readFile and nextTag. Closes outputWriter.
   */
    public static void main(String[] args) throws FileNotFoundException {
        File outputFile = new File("Output.txt");
        PrintWriter outputWriter = new PrintWriter(outputFile);
        
        String fileString = readFile();
        nextTag(fileString, outputWriter);
        
        outputWriter.close();
    }
}