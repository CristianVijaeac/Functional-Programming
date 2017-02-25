:-dynamic(context/2).


valnum(X):- char_type(X, alnum), char_type(X, ascii).
vother(X):- member(X, [';','<','+','-','*','(',')','{','}']).
validc(X):- valnum(X) ; vother(X) ;  X == '='.

lparseq(['='|L],'==',L).
lparseq([X|L],'=',[X|L]):-dif(X,'=').
lparseq([],'=',[]).

lparsealn([X|L],L2,R,L3):- valnum(X), lparsealn(L, [X|L2], R, L3).
lparsealn([X|L],L2,R,[X|L]):- \+valnum(X), reverse(L2, L3), atom_chars(R, L3).
lparsealn([],L2,R,[]):- reverse(L2, L3), atom_chars(R, L3).

lparse2(['='|L],L2,L3):- lparseq(L,R,L4), lparse2(L4,[R|L2],L3).
lparse2([X|L],L2,L3):- valnum(X),lparsealn(L,[X],R,L4), lparse2(L4,[R|L2],L3).
lparse2([X|L],L2,L3):- vother(X), lparse2(L,[X|L2],L3).
lparse2([X|L],L2,L3):- \+validc(X), lparse2(L,L2,L3).
lparse2([],L2,L3):- reverse(L2,L3).

lparse(S, L):- atom_chars(S, L2), lparse2(L2,[],L),!.


/*
 *
 */


parseInputAux(L,R):- evalProg(L,R),!.


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Stergerea tuturor elementelor pana la intalnirea ;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

removeTerms([X|L],L4):- X == ';',L4 = L,!.
removeTerms([X|L],L4):-removeTerms(L,L4),!.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Stergerea tuturor elementelor pana la intalnirea )
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

removeTerms2([X|L],L5):- X == ')',L5 = L,!.
removeTerms2([X|L],L5):-removeTerms2(L,L5),!.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Stergerea tuturor elementelor pana la intalnirea }
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

removeTerms3([X|L],L5):- X == '}',L5 = L,!.
removeTerms3([X|L],L5):-removeTerms3(L,L5),!.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Evaluarea propriu-zisa a programului pe care-l primim sub forma de lista
%%%%%%%%%%%%%%%%%%%%%%%%%%%%

evalProg([],R).
evalProg([;|L],R):-evalProg(L,R).
evalProg(['{'|L],R):-evalProg(L,R).
evalProg(['}'|L],R).
evalProg([X|L],R):- X == 'return',evalExpr(L,L1,L2,L3,OP,R),write(R),!.
evalProg([X,Y|L],R):- Y == '=',evalExpr(L,L1,L2,L3,OP,R1),asserta(context(X,R1)),removeTerms(L,L4),evalProg(L4,R),!. 
evalProg([X,Y|L],R):- X == 'if',evalExpr(L,L1,L2,L3,OP,R1),R1 == 1,removeTerms2(L,L4),evalThen(L4,R2),removeTerms3(L4,L5),removeTerms3(L5,L6),evalProg(L6,R),!.
evalProg([X,Y|L],R):- X == 'if',evalExpr(L,L1,L2,L3,OP,R1),R1 == 0,removeTerms2(L,L4),evalElse(L4,R2),removeTerms3(L4,L5),evalProg(L5,R),!.
evalProg([X,Y|L],R):- X == 'while',evalExpr(L,L1,L2,L3,OP,R1),R1 == 1,removeTerms2(L,L4),evalProg(L4,R),evalProg([X,Y|L],R),!.
evalProg([X,Y|L],R):- X == 'while',evalExpr(L,L1,L2,L3,OP,R1),R1 == 0,removeTerms3(L,L4),evalProg(L4,R),!.
evalProg(L,R).

%%%%%%%%%%%%%%%%%%%
%In functie de valoarea de adevar a conditiei din if,intram pe una din ramuri
%%%%%%%%%%%%%%%%%%%

evalElse([X|L],R):- X == 'else',evalProg(L,R),!.
evalElse([X|L],R):- evalElse(L,R),!.

evalThen([X|L],R):- X == 'then',evalProg(L,R),!.
evalThen([X|L],R):- evalThen(L,R),!


print_result([],0).
print_result([X|L],R):- numeric(X,Y),write(Y), nl, print_result(L,R1),R is R1+Y.
print_result([X|L],R):- print_result(L,R).

%%%%%%%%%%%%%%%%%%%%
%Verificarea daca o variabila este de fapt un numar si convertirea lui din atom in numar
%%%%%%%%%%%%%%%%%%%%

numeric(X,Y) :-atom_number(X,Y).
not_numeric(X) :- 48 > X ; X > 57.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Memoreaza in variabila K ultimul element al unei
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

lastElement([X|L],K):-lastElement(L,K),!.
lastElement([X|[]],K):- context(X,Y),K is Y,!.
lastElement([X|[]],K):- K is X,!.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Eliminarea ultimului element dintr-o lista
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

without_last([_], []).
without_last([X|Xs], [X|WithoutLast]) :- without_last(Xs, WithoutLast),!.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Evaluarea propru-zisa a unei expresii
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

evalExpr([X,;|_],_,_,_,_,R1):-context(X,Y),R1 is Y,!.
evalExpr([X,;|_],_,_,_,_,R1):-R1 is X,!.
evalExpr([X,')'|_],_,_,_,_,R1):-R1 is X,!.
evalExpr(L,L1,L2,L3,OP,R):-findOp(L,O),splitExpr(L,L1,[X2|L2],[Z|OP],O,0),
								lastElement(L1,X1),without_last(L1,L3),!,
								chooseOp(Z,X1,X2,P1),P is P1,append(L3,[P|L2],L4),
								evalExpr(L4,L5,L6,L7,OP,R1),R is R1,!.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Alegerea operatiei si aplicarea ei asupra variabilelor in functie de operatorul gasit in lista
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

chooseOp('*',X1,X2,P1):- context(X2,Y),P1 is X1*Y,!.
chooseOp('*',X1,X2,P1):- P1 is X1*X2,!.
chooseOp('+',X1,X2,P1):- context(X2,Y),P1 is X1+Y,!.
chooseOp('+',X1,X2,P1):- P1 is X1+X2,!.
chooseOp('-',X1,X2,P1):- context(X2,Y),P1 is X1-Y,!.
chooseOp('-',X1,X2,P1):- P1 is X1-X2,!.
chooseOp('<',X1,X2,P1):- context(X2,Y),X1<Y,P1 is 1,!.
chooseOp('<',X1,X2,P1):- context(X2,Y),P1 is 0,!.
chooseOp('<',X1,X2,P1):- X1<X2,P1 is 1,!.
chooseOp('<',X1,X2,P1):- P1 is 0,!.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Cautarea operatorului in lista(cautarea fiecarui operator se face pe rand,in functie de prioritatea acestuia)
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

findOp(L,O):- member('*',L),O = '*',!.
findOp(L,O):- member('-',L),O = '-',!.
findOp(L,O):- member('+',L),O = '+',!.
findOp(L,O):- member('<',L),O = '<',!.
findOp(L,O).

%%%%%%%%%%%%%%%%%%%%%%
%Parsarea listei dupa operatorul primit ca parametru(rezultatul este reprezentat de 2 liste:ce se afla la stanga operatorului si ce se afla la dreapta)
%%%%%%%%%%%%%%%%%%%%%

splitExpr([],[],[],[],_,_).
splitExpr([X|L],[X|L1],L2,OP,O,0):- not(X == O),splitExpr(L,L1,L2,OP,O,0),!.
splitExpr([X|L],L1,L2,[X|OP],O,0):- splitExpr(L,L1,L2,OP,O,1),!.
splitExpr([X|L],L1,[X|L2],OP,O,1):-splitExpr(L,L1,L2,OP,O,1),!.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Transformarea din atom in numere ('123'-> 123)
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%5%
makeNum([],T,Rez):- reverse(T,Rez),!.
makeNum([X|L],T,Rez):- numeric(X,Y),makeNum(L,[Y|T],Rez),!.
makeNum([X|L],T,Rez):- makeNum(L,[X|T],Rez),!.


parseInput(F,R):-read_file_to_string(F,S,[]), lparse(S,L), makeNum(L,[],T),parseInputAux(T,R),!.


