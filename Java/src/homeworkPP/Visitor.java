package homeworkPP;

/*
 * Visitor
 */

public interface Visitor {

	public void visit(Attribution obj,Context c);
	public void visit(Comp obj,Context c);
	public void visit(Equal obj,Context c);
	public void visit(IFCond obj,Context c);
	public void visit(Mult obj,Context c);
	public void visit(Return obj,Context c);
	public void visit(SequenceFin obj,Context c);
	public void visit(Sum obj,Context c);
	public void visit(Symbol obj,Context c);
	public void visit(Value obj,Context c);
	public void visit(WhileCond obj,Context c);
}
