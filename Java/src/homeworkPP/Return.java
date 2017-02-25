package homeworkPP;

import java.util.ArrayList;

public class Return implements Element{

	ArrayList<Element> expressions = new ArrayList<Element>();	//lista expresiilor din noduri
	
	public Return(){
		
	}
	
	@Override
	public Integer eval() {
	
		return expressions.get(0).eval();	//se intoarce expresia asocatiata operatiei "return"
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
