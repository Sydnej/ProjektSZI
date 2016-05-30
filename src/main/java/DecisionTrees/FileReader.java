package DecisionTrees;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class FileReader {
	static String path = System.getProperty("user.dir");
	static String path2 = "\\src\\main\\java\\DecisionTrees\\playtennis.data";
	static String pathf = path + path2;
	public static final String PATH_TO_DATA_FILE = pathf;

    public static ArrayList<Record> buildRecords() {
		BufferedReader reader = null;
		DataInputStream dis = null;
		ArrayList<Record> records = new ArrayList<Record>();

        try { 
           File f = new File(PATH_TO_DATA_FILE);
           FileInputStream fis = new FileInputStream(f); 
           reader = new BufferedReader(new InputStreamReader(fis));;
           
           // read the first record of the file
           String line;
           Record r = null;
           ArrayList<DiscreteAttribute> attributes;
           while ((line = reader.readLine()) != null) {
              StringTokenizer st = new StringTokenizer(line, ",");
              attributes = new ArrayList<DiscreteAttribute>();
              r = new Record();
			   System.out.println(line);
			   System.out.println(Hw1.NUM_ATTRS);
			   System.out.println(st.countTokens());

              if((Hw1.NUM_ATTRS-1) != st.countTokens()) {
            	  throw new Exception("Unknown number of attributes!");
              }
              	
			  @SuppressWarnings("unused")
			  //String day = st.nextToken();
			  String outlook = st.nextToken();
			  String temperature = st.nextToken();
			  String humidity = st.nextToken();
			  String wind = st.nextToken();
			  String playTennis = st.nextToken();
			  
			  if(outlook.equalsIgnoreCase("overcast")) {
				  attributes.add(new DiscreteAttribute("Outlook", DiscreteAttribute.Overcast));
			  }
			  else if(outlook.equalsIgnoreCase("sunny")) {
				  attributes.add(new DiscreteAttribute("Outlook", DiscreteAttribute.Sunny));
			  }
			  else if(outlook.equalsIgnoreCase("rain")) {
				  attributes.add(new DiscreteAttribute("Outlook", DiscreteAttribute.Rain));
			  }
			  
			  if(temperature.equalsIgnoreCase("hot")) {
				  attributes.add(new DiscreteAttribute("Temperature", DiscreteAttribute.Hot));
			  }
			  else if(temperature.equalsIgnoreCase("mild")) {
				  attributes.add(new DiscreteAttribute("Temperature", DiscreteAttribute.Mild));
			  }
			  else if(temperature.equalsIgnoreCase("cool")) {
				  attributes.add(new DiscreteAttribute("Temperature", DiscreteAttribute.Cool));
			  }
			  
			  if(humidity.equalsIgnoreCase("high")) {
				  attributes.add(new DiscreteAttribute("Humidity", DiscreteAttribute.High));
			  }
			  else if(humidity.equalsIgnoreCase("normal")) {
				  attributes.add(new DiscreteAttribute("Humidity", DiscreteAttribute.Normal));
			  }
			  
			  if(wind.equalsIgnoreCase("weak")) {
				  attributes.add(new DiscreteAttribute("Wind", DiscreteAttribute.Weak));

			  }
			  else if(wind.equalsIgnoreCase("strong")) {
				  attributes.add(new DiscreteAttribute("Wind", DiscreteAttribute.Strong));

			  }
			  
			  if(playTennis.equalsIgnoreCase("no")) {
				  attributes.add(new DiscreteAttribute("PlayTennis", DiscreteAttribute.PlayNo));
			  }
			  else if(playTennis.equalsIgnoreCase("yes")) {
				  attributes.add(new DiscreteAttribute("PlayTennis", DiscreteAttribute.PlayYes));
			  }
			    		    
			  r.setAttributes(attributes);
			  records.add(r);
           }

        } 
        catch (IOException e) { 
           System.out.println("Uh oh, got an IOException error: " + e.getMessage()); 
        } 
        catch (Exception e) {
            System.out.println("Uh oh, got an Exception error: " + e.getMessage()); 
        }
        finally { 
           if (dis != null) {
              try {
                 dis.close();
              } catch (IOException ioe) {
                 System.out.println("IOException error trying to close the file: " + ioe.getMessage()); 
              }
           }
        }
		return records;
	}
}
