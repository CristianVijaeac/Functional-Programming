package homeworkPP;

import java.util.ArrayList;

public class Value implements Element{

	
	Integer val;	//valoarea numerica memorata
	
	public Value(){
		
	}
	
	public Value(Integer val){
		
		this.val = val;
	}

	@Override
	public Integer eval() {
		
		return val;	//se intoarce valoarea numerica
	}

	@Override
	public ArrayList<Element> getExpressions() {
	
		return null;
	}

	@Override
	public void accept(Visitor v,Context c) {
	
		v.visit(this,c);	
	}

}
