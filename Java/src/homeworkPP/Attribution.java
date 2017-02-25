package homeworkPP;

import java.util.ArrayList;

public class Attribution implements Element{

	ArrayList<Element> expressions = new ArrayList<Element>(); //lista de expresii din noduri
	Context c;
	
	public Attribution(){
		
	}
	
	public Attribution(Context c){
		
		this.c = c;
	}
	
	@Override
	public Integer eval() {
		
		c.add(expressions.get(0).toString(), expressions.get(1).eval()); //adaugam in context variabila si valoarea ei in urma evaluarii
		
		return expressions.get(1).eval(); //intoarcem rezultatul evaluarii pentru o viitoare utilizare
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
