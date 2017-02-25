package homeworkPP;

import java.util.HashMap;

public class Context {
	
	private HashMap<String,Integer> link = new HashMap<String,Integer>();	//mapeaza un simbol cu o SINGURA valoare,neacceptand duplicate
  
    public void add (String v, Integer i){
    	
    	link.put(v, i);	//punem stringul "v"cu valoarea "i"
    }
    // Treat undefined variable problem using exceptions
    public Integer valueOf(String v) {
    
    	return link.get(v);	//extragem valoarea simbolului "i"
    }
    
}