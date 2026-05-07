package setting;

import java.util.ArrayList;
import java.util.List;


public class Split69Char {

	public Split69Char() {
//		for (String part : getParts(str, 69)) {
//            System.out.println(part);
//        }	
    }
    
	public static List<String> getParts(String string, int partitionSize) {
        List<String> parts = new ArrayList<String>();
        int len = string.length();
        for (int i=0; i<len; i+=partitionSize)
        {
            parts.add(string.substring(i, Math.min(len, i + partitionSize)));
        }
        System.out.println("parts=" + parts + "#");
        return parts;
    }
}
