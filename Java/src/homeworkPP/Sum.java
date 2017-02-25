package homeworkPP;

import java.util.ArrayList;

public class Sum implements Element{

	ArrayList<Element> expressions = new ArrayList<Element>();	//lista expresiilor din noduri
	
	public Sum(){
		
	}

	@Override
	public Integer eval() {
		
		return expressions.get(0).eval() + expressions.get(1).eval();	//se efectueaza operatia "+" asupra expresiilor din nodul stang/drept
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
