package homeworkPP;

import java.util.ArrayList;

public class SequenceFin implements Element{
	
	ArrayList<Element> expressions = new ArrayList<Element>();	//lista expresiilor din noduri
	
	public SequenceFin(){
		
	}

	@Override
	public Integer eval() {
	
		this.expressions.get(0).eval();	//se evalueaza expresia din stanga
		return this.expressions.get(1).eval();	//se evalueaza expresia din dreapta
	}

	@Override
	public ArrayList<Element> getExpressions() {
	
		return this.expressions;
	}

	@Override
	public void accept(Visitor v,Context c) {
	
		v.visit(this,c);
	}

}
