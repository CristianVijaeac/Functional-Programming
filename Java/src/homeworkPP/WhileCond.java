package homeworkPP;

import java.util.ArrayList;

public class WhileCond implements Element{

	ArrayList<Element> expressions = new ArrayList<Element>();	//lista expresiilor din noduri
	
	public WhileCond(){
		
	}
	
	@Override
	public Integer eval() {
		
		Integer res = new Integer(0);
		while (expressions.get(0).eval() == 1){	//cat timp evaluarea conditiei ramane adevarata
			 res = expressions.get(1).eval();	//se evalueaza sub-programul asociat operatiei "while"
		}
		
		return res;
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
