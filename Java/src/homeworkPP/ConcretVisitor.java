package homeworkPP;
/*
 * Visitor
 */
public class ConcretVisitor implements Visitor {

	@Override
	public void visit(Attribution obj,Context c) {
		
		obj.expressions.get(1).accept(this,c);	//verificam daca expresia din dreapta contine o variabila OUT OF SCOPE 
		c.add(obj.expressions.get(0).toString(), 0);	//adauga variabila careia i-a fost atribuita o valoare
			
	}

	@Override
	public void visit(Comp obj,Context c) {
		
		for (int i=0; i<obj.expressions.size(); i++)
			obj.expressions.get(i).accept(this,c);	//verificam corectitudinea expresiilor din noduri
	}

	@Override
	public void visit(Equal obj,Context c) {
		
		for (int i=0; i<obj.expressions.size(); i++)
			obj.expressions.get(i).accept(this,c);	//verificam corectitudinea expresiilor din noduri
	}

	@Override
	public void visit(IFCond obj,Context c) {

		for (int i=0; i<obj.expressions.size(); i++)
			obj.expressions.get(i).accept(this,c);	//verificam corectitudinea expresiilor din noduri
	}

	@Override
	public void visit(Mult obj,Context c) {
		
		for (int i=0; i<obj.expressions.size(); i++)
			obj.expressions.get(i).accept(this,c);	//verificam corectitudinea expresiilor din noduri
	}

	@Override
	public void visit(Return obj,Context c) {
		
		c.add("hasReturn", 1);	//daca intram in aceasta metoda,inseamna ca am gasit return-ul cautat
	}

	@Override
	public void visit(SequenceFin obj,Context c) {
		
		for (int i=0; i<obj.expressions.size(); i++)
			obj.expressions.get(i).accept(this,c);	//verificam corectitudinea expresiilor din noduri
	}

	@Override
	public void visit(Sum obj,Context c) {
		
		for (int i=0; i<obj.expressions.size(); i++)
			obj.expressions.get(i).accept(this,c);	//verificam corectitudinea expresiilor din noduri
	}

	@Override
	public void visit(Symbol obj,Context c) {

		if (c.valueOf(obj.val) == null)
			c.add("notOut", 0);	//daca nu se gaseste simbolul cautat in Context,inseamna ca aceasta este OUT OF SCOPE
	}

	@Override
	public void visit(Value obj,Context c) {
		
	}

	@Override
	public void visit(WhileCond obj,Context c) {
		
		for (int i=0; i<obj.expressions.size(); i++)
			obj.expressions.get(i).accept(this,c);	//verificam corectitudinea expresiilor din noduri
	}
	
}
