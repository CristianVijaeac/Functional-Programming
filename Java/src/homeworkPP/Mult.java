package homeworkPP;

import java.util.ArrayList;

public class Mult implements Element{

	ArrayList<Element> expressions = new ArrayList<Element>();	//lista expresiilor din noduri
	
	public Mult(){
		
	}

	@Override
	public Integer eval() {
		
		return expressions.get(0).eval() * expressions.get(1).eval(); //rezultatul operatiei de inmultire dintre 2 expresii
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
