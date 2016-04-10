/**
 **   Used linear search to find high index
 **
 **
 **/

import java.util.*;
import java.io.IOException;

public class Trie2 {
	
	private class TrieNode {
		Map<Character, TrieNode> children = new TreeMap<>();//Unlike hash table, keys in TreeMap are sorted! 
		boolean aword;						//Basically it acts like a Hashtable or Hashmap, establishing a mapping between Key and Value
		ArrayList<String> mfu = new ArrayList<String>();
	}

	private TrieNode root;
	private ArrayList<WordItem> dict;
   
   public Trie2() {
		this.root = new TrieNode();
      this.dict = new ArrayList<WordItem>();
	}

   public void insertDictionary(String s) throws IOException { // INSERT DICTIONARY
      WordProcessor wp = new WordProcessor();
	   dict = wp.fileRead(s);
      
      for(int i = 0; i < dict.size(); i++)
      {
         this.insertString(dict.get(i).getWord());
      }
   }
   
   public boolean hasDictionary() {
      return dict.size() > 0;
   }

	public void insertString(String s) {
		insertString(root, s);
	}

	private void insertString(TrieNode root, String s) { // INSERT STRING
		TrieNode cur = root;
		String str = "";
      int focc; // first occurence
      int locc; // last occurence
      
      for (char ch : s.toCharArray()) {
      
			TrieNode next = cur.children.get(ch);
			if (next == null)
				cur.children.put(ch, next = new TrieNode());
			
         str += ch;
         cur = next;
         if(cur.mfu.size() <= 0)
         {
            focc = lowIndex(str); // first occurrence
         
            if(focc >= 0) {
               locc = highIndex(str, focc); // last occurrence
               Object[] objectArray = dict.subList(focc, locc+1).toArray(); // sub-array
               WordItem[] subdic = Arrays.copyOf(objectArray, objectArray.length, WordItem[].class); // convert sub
               Arrays.sort(subdic, new WordItem.WordComparator(true));
               
               for(int i = subdic.length-1; i >= 0; i--)
               {
                  cur.mfu.add(subdic[i].getWord());
               }
            }
         }
		}
		cur.aword = true;
	}
   
   private int lowIndex(String s) { // LOW INDEX - Uses binary search
      int low = 0, high = dict.size() - 1, mid;
      
      while(low <= high)
      {
         mid = (low + high) / 2;
         String fword = dict.get(mid).getWord(); // found word
         
         if(s.compareTo(fword) < 0) {
            if(low == high)
            {
               if(fword.length() >= s.length())
               {
                  if(s.compareTo(fword.substring(0, s.length()) ) == 0)
                     return mid; // found
               }
               else return -1; // not found
            }
            high = mid;
         }
         else if(s.compareTo(fword) > 0)
         {
            if(mid-1 > 0)
            {
               if(fword.length() >= s.length())
               {
                  if(s.compareTo( (dict.get(mid-1)).getWord() ) < 0 &&
                     s.compareTo(fword.substring(0, s.length()) ) == 0)
                  {
                     return mid; // found
                  }
               }
               else if(low == high) return -1; // not found
            }
            low = mid + 1;            
         }
         else return mid;
      } // endwhile
      return -1;
   }
   
   private int highIndex(String s, int firstIndex) { // HIGH INDEX
      
      int nextIndex = firstIndex;
      String nword = ""; // next word
      String fword = dict.get(firstIndex).getWord(); // first word
      while(nextIndex < dict.size()-1) 
      {
         nword = dict.get(nextIndex+1).getWord();
         if(s.length() > nword.length()) break;
         if(s.compareTo(nword.substring(0, s.length()) ) != 0 ) {
            break;
         }
         nextIndex++;
      } // end while
      return nextIndex;
   }
	
	public void printSorted() {
		printSorted(root, "");
	}

	private void printSorted(TrieNode node, String s) { // PRINT
		if (node.aword) {
			System.out.println(s);
		}
		for (Character ch : node.children.keySet()) {
			printSorted(node.children.get(ch), s + ch);
		}
	}
	
	public ArrayList<String> findWord(String s) { 
		TrieNode node;
      if( (node = findWord(root, s)) == null)
         return new ArrayList<String>(); // empty ArrayList
         
      return node.mfu;
	}
	
	private TrieNode findWord(TrieNode node, String s) { // SEARCH
		if(s != null) {
			String rest = s.substring(1); //rest is a substring of s, by excluding the first character in s
			char ch = s.charAt(0);        //ch is the first letter of s
			TrieNode child = node.children.get(ch);	//return the child that ch associated with. 
			if( (s.length() == 1 && child != null) || child == null )
				return child;                 //base case
			else
				return findWord(child, rest);    //recursive, In this way, we follow the path of the trie from root down towards leaf
		}
		return new TrieNode(); // empty node
	}

	// Usage example
	public static void main(String[] args) {
		        
		Trie2 tr = new Trie2();
		
      try {
         tr.insertDictionary("files/dictsmall.txt");
      }
      catch(IOException e) {
         System.out.println("Unable to read dictionary");
         System.exit(0);
      }
      int low = tr.lowIndex("ba");
      int high = tr.highIndex("ba", low);
      
      
      
		// tr.insertString("hello");
// 		tr.insertString("world");
// 		tr.insertString("hi");
// 		tr.insertString("ant");
// 		tr.insertString("an");
// 		
// 		System.out.println(tr.findWord("ant"));
// 		System.out.println(tr.findWord("an"));
// 		System.out.println(tr.findWord("hello"));
// 		System.out.println(tr.findWord("cant"));
// 		System.out.println(tr.findWord("hig"));
// 		System.out.println(tr.findWord("he"));
		
		tr.printSorted();
	}
}
