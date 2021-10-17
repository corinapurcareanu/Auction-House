# Auction-House
Proiectul implementeaza un sistem de licitatii online, avand o casa de licitatii,
produse, clienti si angajati. 

Casa de licitatii este reprezentata de clasa AuctionsHouse, ce contine liste
implementate cu ajutorul ArrayList-urilor, in care se stocheaza produse, clienti
sau brokeri. Citirea acestora am facut-o din fisiere Excel.

Produsele pot fi de 3 tipuri, asa ca am creat clasa abstacta Products ce contine
proprietatile comune ale tuturor tipulorilor de produse. Din aceasta clasa am
extins tipuri specifice, Painting, Jewel si Furniture, care reprezinta tipuri
concrete de produse, cu caracteristici diferite. Datele despre ele le-am citit
din fisierul Product.xlsx, care contine foi separate pentru fiecare tip de produs,
si le-am adaugat in lista de produse cu ajutorul Administratorului. Instantierea
unui tip de produs l-am facut cu ajutorul design pattern-ului Factory, implementat
in clasa ProductFactory care contine metoda createProduct ce intoarce un anumit 
tip de produs, in functie de ce ii este specificat ca parametru. Am procedat 
similar si pentru clienti, avand clasa ClientFactory care instantiaza un client
in functie de tipul specificat. 

Administratorul este unic, si este creat la inceputul fiecarui test. Pentru
a ma asigura ca nu sunt creati mai multi, am folosit design pattern-ul Singleton.
Acesta are rolul de a adauga produse in lista de produse a casei de licitatii,
iar acest lucru l-am realizat printr-un Thread.

De asemenea, si casa de licitatii este unica, asadar clasa AuctionsHouse implementeaza
tot deign Pattern-ul Singleton. Clientii isi exprima in mod direct casei de licitatii
dorinta de a licita pentru un anumit produs, avand drept sa vizualizeze lista de 
produse. Acest lucru l-am facut in metoda wantsToBuy care primeste ca parametru
pretul maxim pe care clientul il poate oferi pentru acel produs, id-ul produsului
dorit si casa de licitatii pentru a avea acces la lista ei de produse. Daca produsul
nu este gasit se arunca exceptia ProductNotFoundException. Daca este gasit, casa de
licitatii verifica daca este deja o licitatie in asteptare pentru acel produs.
Licitatiile in asteptare se afla intr-un map, ele fiind cheia map-ului, iar valoarea
este un array cu clienti in asteptare. Daca exista deja licitatia in asteptare,
clientul este adaugat pe lista de asteptare pana cand vor fi suficienti clienti ca
sa inceapa licitatia, daca nu, se va creea licitatia si se va adauga primul client.
Licitatia o creez cu ajutorul unei clase AuctionBuilder, pentru a separa construirea
de reprezentarea ei, fiind un obiect destul de complex. 

Cand s-a atins numarul dorit de clienti, se porneste licitatia. Clientii sunt
repartizati in mod aleatoriu unui broker, iar din acest moment incepe comunicarea
brokeri-clienti. Pentru acest lucru am ales sa implementez design pattern-ul
Observer. Clasa client implementeaza interfata Observer, ce contine metoda update,
ce primeste ca parametru un mesaj. Acest mesaj ar trebui sa vina de la brokerul
care il reprezinta, pentru a-l instiinta de evolutia licitatiei. Brokerii implementeaza
interfata subject ce contine metodele Attach pentru a adauga un nou client, Detach
pentru a scoate un client si Notify pentru a anunta clientii. Cand se incepe
licitatia, brokerii le trimit clientilor un mesaj de instiintare. La fiecare pas,
brokerul le cere clientilor sa ofere o suma de bani. Acest lucru il face cu ajutorul
metodei askClientsToBid ce primeste un pret minim pe care el ar trebui sa-l ofere,
si anume cel mai mare pret oferit la pasul anterior, si un array de preturi pentru
a le adauga pe cele curente. Clientul poate oferi cat doreste cu conditia sa fie
mai mult ca la pasul precedent si bineinteles mai putin decat suma maxima pe care
a declarat-o la inceput. Acest lucru il face prin metoda offerMoney care primeste
ca parametru pretul minim pe care ar trebui sa-l ofere, iar pretul oferit este
stabilit random, ca fiind un numar intre pretul minim si suma maxima pe care el
ar putea-o oferi. Daca nu poate oferi o suma mai mare decat cea minima impusa este
eliminat din lista brokerului si ii creste cu 1 numarul de participari la licitatii. 
La fiecare pas, casa de licitatii anunta suma maxima oferita la acel pas, pe care o
calculeaza din array-ul de preturi. La final, castiga cel care a oferit suma cea mai
mare. Atunci se incrementeaza cu 1 numarul de dati in care a castigat acel client,
i se adauga produsul in lista, restul sunt instiintati de brokeri ca s-a incheiat
licitatia si sunt eliminati din lista brokerului, bineinteles, crescandu-le cu 1
numarul de participari. Daca licitatia nu ajunge la final, se arunca exceptia
AuctionInterrupted.

Comenzile pentru testare le-am luat din fisierele denumite test de la 1 la 10, iar
output-ul il afisez in consola. 
