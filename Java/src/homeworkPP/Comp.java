package homeworkPP;

import java.util.ArrayList;

public class Comp implements Element{

	public ArrayList<Element> expressions = new ArrayList<Element>(); //lista de expresii din noduri
	
	public Comp(){
		
	}
	
	@Override
	public Integer eval() {
	
		return (expressions.get(0).eval() < expressions.get(1).eval()) ? 1 : 0; //se intoarce 1 daca operatia este valida si 0 in schimb
	}

	@Override
	public ArrayList<Element> getExpressions() {
		
		return expressions;
	}

	@Override
	public void accept(Visitor v,Context c) {
		
		v.visit(this,c);
	}

}
