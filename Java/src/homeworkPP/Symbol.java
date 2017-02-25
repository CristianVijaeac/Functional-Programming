package homeworkPP;

import java.util.ArrayList;

public class Symbol implements Element{


	String val = new String();	//memoreaza variabila
	Context c;
	
	public Symbol(){
		
	}
	
	public Symbol(String val,Context c){
		
		this.val = val;
		this.c = c;
	}

	@Override
	public Integer eval()  {
		
		return c.valueOf(val);	//se intoarce valoarea din context asociata acestei variabile
	}
		

	@Override
	public ArrayList<Element> getExpressions() {
		
		return null;
	}

	@Override
	public void accept(Visitor v,Context c) {
		
		v.visit(this,c);
	}
	
	@Override
	public String toString(){
		
		return this.val;
	}

}
