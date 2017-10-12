import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

//@Author:Aradhana Elisa
public class Main {

    

    private static void Solve(String[] args) throws Throwable {
        BufferedReader reader = new BufferedReader(new FileReader(new File(args[0])));
        String fileLine;
        //
        while ((fileLine = reader.readLine()) != null) {
            if (!fileLine.isEmpty()) {
                stringPermutation(fileLine);
            }
        }
    }

    private static void permute(String remaining, String prefix, StringBuilder result) {
        if (remaining.isEmpty())
		{
            result.append(prefix).append(',');
        } 
		else {
            int remainingSize = remaining.length();
            for (int i = 0; i < remainingSize; i++) 
			{
                permute(remaining.substring(0, i) + remaining.substring(i + 1, remainingSize), prefix + remaining.charAt(i), result);
            }
        }
    }
	
	private static void stringPermutation(String fileLine) {
		//reading all the inputs from the fileLine
        char[] items = fileLine.toCharArray();
		//sorting the item
        Arrays.sort(items);
        StringBuilder result = new StringBuilder();
		//passing the string
        permute(String.valueOf(items), "", result);
        System.out.println(result.deleteCharAt(result.length() - 1));

    }
	
	public static void main(String[] args) throws Throwable {
        Solve(args);
    }
}
