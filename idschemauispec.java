//importing the necessary classes and interfaces.
//import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

//main class I given the name of Tas

public class idschemauispec{     

    public static void main(String[] args) {

        String file1Path = "idschema.json"; 
        String file2Path = "uispec.json";

        try {

            String json1 = readFileAsString(file1Path);
            String json2 = readFileAsString(file2Path);


            printMissingFields(json1, json2);
        } catch (IOException e) {
            e.printStackTrace();  
        }
    }

    private static void printMissingFields(String json1, String json2) {
        Set<String> fields1 = extractFields(json1, false);
        Set<String> fields2 = extractFields(json2, true);
 /*If a field is not present in fields2, it prints a message indicating
 that the field is not present in the uispec JSON.*/
//fields1 and checks if each field is present in fields2

       for (String field : fields2) {
            if (!fields1.contains(field)) {
              System.out.println(field + " is present in the uispec and not present in idschema");
            }
        } 
        //System.out.println(fields2); 
    }

    private static Set<String> extractFields(String json , boolean flag) {
    // hash set It allows you to store multiple values in a collection
        Set<String> fields = new HashSet<>();
        int index = 0;
        boolean insideString = false;
        StringBuilder currentField = new StringBuilder();
      //  StringBuilder currentFieldid = new StringBuilder("id");

         boolean idcheck = false;
        while (index < json.length()) {
            char c = json.charAt(index);
            //System.out.println(c);
           // When encountering a double quote (") character, it changes the insideString flag to indicate
            //whether it is inside a string or not.
            if (c == '"') {//This condition checks if the current character c is equal to a double quotation mark (").f
                insideString = !insideString;
            } else if (c == ':' && !insideString) {
                String field = currentField.toString().trim();
                if (!field.isEmpty() && !flag) {
                    fields.add(field);
                } 
                 if(field.equals("id"))
                {
                  idcheck = true;
                 // System.out.println("if the flag is true" + field);
                }
                else {
                    idcheck = false;
                     //System.out.println("if the flag is false" + field);   
                }
                currentField.setLength(0);
            }
               else if (c == ',' && !insideString) {
                String field = currentField.toString().trim();
                if (!field.isEmpty() && flag && idcheck) {
                    fields.add(field);
                  //  idcheck = !idcheck;
                }               
                currentField.setLength(0);
            }
             else if (insideString) {
                currentField.append(c);
            }
            index++;
        }
        return fields;
    }

       //this methods reads file as string so we can use

    private static String readFileAsString(String filePath) throws IOException {
        // StringBuilder to accumulate the contents of the file line by line
        StringBuilder fileContent = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        //reads each line of the file and adds it to the fileContent string builder
        String line;
        while ((line = reader.readLine()) != null) {  //if its not null it will excutes
            fileContent.append(line);
        }
        reader.close();
        return fileContent.toString();
    }
} //After reading all the lines, it closes the reader and returns the accumulated file content as a string.