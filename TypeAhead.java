import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
public class Main
{
	public static boolean matchData(String[] data, String[] dataSecondary,int index){
		if(data.length<dataSecondary.length)
		return false;
		int secondaryLength=dataSecondary.length;
		for(int i=0;i<secondaryLength;i++){
			if(!data[index+i].equals(dataSecondary[i]))
			return false;
		}
		return true;
	}
	
	
	
	public static void main (String[] args)
	{
	
		try{
			
			File file = new File(args[0]);
			String Text ="Mary had a little lamb its fleece was white as snow; And everywhere that Mary went, the lamb was sure to go. It followed her to school one day, which was against the rule;It made the children laugh and play, to see a lamb at school.And so the teacher turned it out, but still it lingered near,And waited patiently about till Mary did appear.\"Why does the lamb love Mary so?\" the eager children cry;\"Why, Mary loves the lamb, you know\" the teacher did reply.\"";
			String[] charArray=Text.split("[ .\";?,]+");
			BufferedReader bufferReader = new BufferedReader(new FileReader(file));
			HashMap<String, Integer> hash=new HashMap<String, Integer> ();
			String line; 
			int Length;
			double PredictionScore;
			String word;
			while ((line = bufferReader.readLine()) != null)
			{	
				PredictionScore=0;
				hash.clear();
				String[] Input=line.split(",");
				String[] SeachLetter=Input[1].split(" ");
				Length=Integer.parseInt(Input[0]);
				for(int i=0;i<charArray.length-Length+1;i++)
				{
					if(matchData(charArray,SeachLetter,i))
					{
						word=charArray[i+Length-1];
						if(!hash.containsKey(word))
							hash.put(word, 1);
						else
							hash.put(word, hash.get(word)+1);
					}
				}
				
				List<Map.Entry<String, Integer>> listData= new ArrayList<Map.Entry<String, Integer>>(hash.entrySet());
				Collections.sort(listData, new Comparator<Map.Entry<String, Integer>>(){
					 public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2)
					 {
							 return (e1.getKey().compareTo(e2.getKey()));
							 }
				});
				
				
				Collections.sort(listData, new Comparator<Map.Entry<String, Integer>>(){
					 public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2){
							 return (e2.getValue().compareTo(e1.getValue()));
							 }
				});
				
				for(Map.Entry<String, Integer> s:listData)
					PredictionScore+=s.getValue();
				int count=0;
				for(Map.Entry<String, Integer> s:listData){
					count++;
					if(count!=listData.size())
						System.out.print(s.getKey()+","+new java.text.DecimalFormat("0.000").format(s.getValue()/PredictionScore)+";");
					else
						System.out.println(s.getKey()+","+new java.text.DecimalFormat("0.000").format(s.getValue()/PredictionScore));
				//bufferReader.close();
				}				
			}
		}
		
		catch(FileNotFoundException ex)
        {
            System.out.println( "Unable to open file '" + args[0]);              
        }
       
       catch(IOException ex)
       {
           System.out.println("Error reading file '" + args[0]);                  
           
       }
		
		 
	}
	

}
