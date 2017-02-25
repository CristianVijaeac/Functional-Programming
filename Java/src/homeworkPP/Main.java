package homeworkPP;

import java.util.LinkedList;
import java.util.List;

public class Main {

	/**
	 * IMPORTANT! Your solution will have to implement this method.
	 * @param exp - a string, which represents an expression (that
	 * follows the specification in the homework);
	 * @param c - the context (a one-to-one association between variables
	 * and values);
	 * @return - the result of the evaluation of the expression;
	 */
	
	public static boolean isNumber(String exp){
		
		if (exp.matches("[0-9]*")) return true;	//verificare daca avem o valoare numerica
		else return false;
	}
	
	public static boolean isSymbol(String exp){
		
		if (exp.matches("[a-zA-Z]*")) return true;	//verificare daca avem o variabil(String)
		else return false;
		
	}
	
	public static Element createTree(String exp,Context c){
		
			if (isNumber(exp))
				return new Value(Integer.parseInt(exp));	//valoarea numerica va reprezenta o frunza in arbore
			
			if (isSymbol(exp))
				return new Symbol(exp,c);	//variabila va reprezenta o frunza in arbore
			
			String subString = exp.substring(1,exp.length()-1);	//extragem ceea ce se afla intre "[ ]"
			String[] first =splitList(subString);	//impartim subString-ul in String-uri dupa paranteze "[ ]" si " "(spatii)
			
			Element new_Node = null;
			
			//daca primul element din vectorul de string-uri este un operator,construim un nod in arbore aferent lui
			if (first[0].equals("+"))
				new_Node = new Sum();
			if (first[0].equals("*"))
				new_Node = new Mult();
			if (first[0].equals("<"))
				new_Node = new Comp();
			if (first[0].equals("=="))
				new_Node = new Equal();
			if (first[0].equals("="))
				new_Node = new Attribution(c);
			if (first[0].equals("if"))
				new_Node = new IFCond();
			if (first[0].equals("while"))
				new_Node = new WhileCond();
			if (first[0].equals(";"))
				new_Node = new SequenceFin();
			if (first[0].equals("return"))
				new_Node = new Return();
			
			for (int i=1; i<first.length; i++){
				new_Node.getExpressions().add(createTree(first[i],c));	//pentru fiecare expresie ramasa in vectorul de String-uri construim copii/urmatorul nivel din arbore
			}
			
			return new_Node;
	}
	
	public static Integer evalExpression(String exp, Context c){
		
		Element root;
		
		root = createTree(exp,c);	//construim arborele
		
		Integer result = root.eval();	//evaluam expresia pe baza arborelui
		
		return result;	//rezultatul obtinut
	}
	
	
		
		
	

	/**
	 * IMPORTANT! Your solution will have to implement this method.
	 * @param program - a string, which represents a program (that
	 * follows the specification in the homework);
	 * @return - the result of the evaluation of the expression;
	 */
	public static Integer evalProgram(String program) {
		
		Element root;
		Context c = new Context();
		
		root = createTree(program,c);	//construim arborele
		
		Integer result = root.eval();	//evaluam programul pe baza arborelui
		
		return result;	//rezultatul obtinut
		
	}

	/**
	 * IMPORTANT! Your solution will have to implement this method.
	 * @param program - a string, which represents a program (that
	 * follows the specification in the homework);
	 * @return - whether the given program follow the syntax rules
	 * specified in the homework (always return a value and always
	 * use variables that are "in scope");
	 */

	public static Boolean checkCorrectness(String program) {
	
		Element root;
		Context c = new Context();
		
		root = createTree(program,c);	//construim arborele
	
		c.add("hasReturn",0);	//variabila care are valoare 0-NU exisa return in program si 1-EXISTA
		c.add("notOut", 1);		//variabila care are valoarea 1-TOATE variabilele exista si 0-variabila OUT OF SCOPE
		
		root.accept(new ConcretVisitor(),c);	//folosindu-ne de pattern-ul VISITOR,verificam pentru corectitudine toate expresiile
		
		if(c.valueOf("notOut") == 0){
			System.out.println("OUT OF SCOPE");	//daca o singura variabile este OUT OF SCOPE,programul nu este corect	
			return false;
		}
		
		if (c.valueOf("hasReturn") == 0){	//daca nu se gaseste RETURN,programul nu este corect
				return false;
		}
		
		return true;	//in cazul in care nu se iese cu "false" inseamna ca programul este corect
	}


	/**
	 *
	 * @param s - a string, that contains a list of programs, each
	 * program starting with a '[' and ending with a matching ']'.
	 * Programs are separated by the whitespace caracter;
	 * @return - array of strings, each element in the array representing
	 * a program;
	 * Example: "[* [+ 1 2] 3] [* 4 5]" -> "[* [+ 1 2] 3]" & "[* 4 5]";
	 */
	 public static String[] splitList(String s){
		String[] result = new String[0];
		List<String> l = new LinkedList<String>();
        int inside = 0;
        int start = 0, stop = 0;
        for (int i=0; i<s.length(); i++){
                if (s.charAt(i) == '['){
                    inside++;
                    stop++;
                    continue;
                }
                if (s.charAt(i) == ']'){
                    inside--;
                    stop++;
                    continue;
                }
                if (s.charAt(i) == ' ' && inside == 0){
                    l.add(s.substring(start,stop));
                    start = i+1; //starting after whitespace
                    stop = start;

                    continue;
                }
                stop++; //no special case encountered
        }
        if (stop > start) {
            l.add(s.substring(start, stop));
        }

        return l.toArray(new String[l.size()]);

	 }

	public static void main(String[] args) {
		
		/* TESTE */
		String[] expresie=splitList("[* [+ 1 2] [+ 4 3]]");
		Context c=new Context();
		
		for (int i=0;i<expresie.length;i++){
			System.out.println("The Result of the Expression is:"+evalExpression(expresie[i],c));
		}
		
		String[] program =splitList("[; [= x [+ 2 1]] [; [= y [+ x 1]] [return [+ y 5]]]]");
		for (int i=0; i<program.length; i++){
			System.out.println("The Result of the Program is:"+evalProgram(program[i]));
		}
		
	    String[] check =splitList("[; [= x [+ 2 1]] [; [= y [+ x 1]]] [return x]]");
		for (int i=0; i<check.length; i++){
			System.out.println("Program is Correct?:"+checkCorrectness(check[i]));
		}
	
	}
}
