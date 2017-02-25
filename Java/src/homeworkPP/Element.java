package homeworkPP;

/*
 * Interfata comuna operatorilor/simbolurilor/valorilor (Decorator)
 */

import java.util.ArrayList;

public interface Element {

	public Integer eval();
	public ArrayList<Element> getExpressions();
	public void accept(Visitor v,Context c);
	
}
