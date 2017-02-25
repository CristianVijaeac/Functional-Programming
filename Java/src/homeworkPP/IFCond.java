package homeworkPP;

import java.util.ArrayList;

public class IFCond implements Element{

	ArrayList<Element> expressions = new ArrayList<Element>();	//lista expresiilor din noduri
	
	public IFCond(){
		
	}
	
	@Override
	public Integer eval() {
		
		if (expressions.get(0).eval() == 1)	//cat timp evaluarea conditie este adevarata
			return expressions.get(1).eval();	//evaluam expresia corespunzatoare de pe ramura "then"
		else return expressions.get(2).eval();	//altfel intram pe ramura "else"
	
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
