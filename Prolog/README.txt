VIJAEAC CRISTIAN OCTAVIAN 325CB
TEMA 3 PARADIGME DE PROGRAMARE

Obs:Descrierea de mai jos a rezolvarii nu include si a doua parte a temei,intrucat nu am putut duce la bun sfarsit
functionalitatea "while"-ului.

Pasii pe care i-am urmarit in elaborarea acestei teme:

	1)Dupa ce primeam lista ca si parametru la citirea din fisier,trebuia sa identific numerele care sunt scrise in lista
	sub forma de atom,asa ca am facut o functie makeNum,care ia fiecare element in parte si verifica daca este numar,iar 
	daca vede ca este face conversie si-l reintroduce in lista
	
	2)Apelez evalProg care alege modul de functionare si evaluare al unei expresii:
		Return - daca acest element este intalnit in lista se apeleaza evalExpr care evalueaza expresia simpla ce urmeaza
		dupa si scrie rezultatul memorat in variabila R
		"=" - daca acest element este intalnit,cu ajutorul notiunii de "baza de date dinamica"(faptul ca pot sa pun in memorie
		date pe care le pot recupera atunci cand am nevoie de ele si le pot scoate;aceasta baza va juca rolul de context) se 
		evalueaza expresia din dreapta egalului,se asigneaza variabilei din stanga valoarea determinata in partea dreapta.Se
		sterge totul pana la ; (toata secventa) iat apoi se continua evaluarea programului
		"if" - daca intalnim "if",evaluam conditia la 1 sau 0,stergem tot pana la intalnirea ) pt ca nu mai avem nevoia
		,conditia evaluandu-se o singura data,si alege pe ce ramura merge 1->apela evalThen,0->apelam evalElse,in cadrul
		carora se evalueaza secventele din interior iar apoi,se sterg datele care au fost utilizate si se continua evaluarea
		programului
		"while" - la fel ca si if-ul,se evalueaza conditia,iar daca aceasta este indeplinita se executa secventele din bloc.Se
		pastreaza o copie a listei pentru ca atunci cand reintram in while sa putem accesa aceleasi secveneta,deoarece,ca si in 
		cazul if-ului,acestea se sterg la finalul blocului.

	3)evalExpr - evalueaza o expresie simpla primita ca parametru si executa operatiile intre termeni in functie de precedenta operatorilor
		3.1)mai intai cautam operatoul cu precedenta cea mai mare in lista
		3.2)facem split dupa acest operator,in urma lui obtinand 2 liste cu elementele aflate in stanga operatorului
		si elementele aflate in dreapta
		3.3)se ia si se elimina ultimul element al primei liste
		3.4)se cauta operatia care trebuie efectuata intre ultimul elemenet al primei liste obtinut mai sus si primul 
		element al celei de-a doua liste
		3.5)Rezultatul se operatiei se baga la inceputul celei de-a doua liste,iar apoi se concateneaza cele 2 liste
		pentru obtinerea vechii liste,dar in care s-a efectuat operatia 
		3.6)se continua evaluea expresiei din cadrul listei pana cand am ramas cu un element(rezultatul) sau pana cand
		am ajuns la ;.
	4)Rezultatul,memorat in R este afisat folosind write(R).