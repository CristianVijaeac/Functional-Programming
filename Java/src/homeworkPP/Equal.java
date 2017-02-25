package homeworkPP;

import java.util.ArrayList;

public class Equal implements Element{

	ArrayList<Element> expressions = new ArrayList<Element>();	//lista expresiilor din noduri
	
	public Equal(){
		
	}

	@Override
	public Integer eval() {

		return expressions.get(0).eval() == expressions.get(1).eval() ? 1 : 0;	//daca operatia este valida se intoarce 1,altfel intoarce 0
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
