import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/*
 *The WordProcessor class would extract words from the raw text file(a.k.a tokenization).
 *After extracted one word, it either creates a new WordItem object and insert
 *the object into LinkedList at a proper location, or it calls a method in MyLinkedList to increment 
 *the word occurrence and to update line-number list if a word has already been existing.
 *
 *The class also provides File I/O methods. Write the resultant string or list back to a file.
 *
 */

public class WordProcessor {

	public ArrayList<WordItem> fileRead(String name) throws IOException {
		ArrayList<WordItem> lines = new ArrayList<WordItem>();
		
		FileReader fileReader = new FileReader(name);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String aline = null;
      //read in the rest of rows
      //readLine() returns striped string, that discards any line-termination chars
      while ((aline = bufferedReader.readLine()) != null) {
         aline = aline.trim();
         if(aline.length() > 0) {
            int com = comma(aline);
            int occ = Integer.parseInt(aline.substring(com+1));
            WordItem wi = new WordItem(aline.substring(0, com), occ); 
            lines.add(wi); //skip empty lines
         }
      }
      fileReader.close();
		return lines;
	}
   
   public int comma(String s) 
   {   
      int i = 0;
      while(i != s.length()) 
      {
         if(s.charAt(i) == ',') {
            return i;
         }
         i++;
      }
      return -1;
   }
}//end of class