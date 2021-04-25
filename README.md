# Allegro_Rekrutacja_Zadanie3

### Zadanie:

##### Cel zadania:
Stwórz oprogramowanie pozwalające na:
- listowanie repozytoriów (nazwa i liczba gwiazdek).
- zwracanie sumy gwiazdek we wszystkich repozytoriach.
dla dowolnego użytkownika serwisu GitHub.
Dane powinny być zwracane za pomocą protokołu HTTP.

##### Wymagania niefunkcjonalne:
- Wybierz jeden z języków programowania: Java/Kotlin/Python.
- Możesz korzystać z dowolnych bibliotek i frameworków do tworzenia aplikacji.
- Rozwiązanie powinno być zaimplementowane jako aplikacja serwerowa.
- Rozwiązanie posiada instrukcję instalacji/uruchomienia zamieszczoną w README.md.
- Wszystkie propozycje na późniejsze rozszerzenie/uwagi do rozwiązania projektu umieść w README.md.

### Instrukcja
##### Uruchomienie
Java: JDK 8 ver. 261
1. <b>IntelliJ IDEA:</b> Wczytanie projektu a następnie uruchomienie klasy GithubApplication.
2. <b>Maven:</b> Po wejściu w folder programu wywołanie komendy po przez terminal "mvn install" powodujące 
pojawienie się pliku .jar w podfolderze target. Następnym krokiem jest uruchomienie pliku .jar.
(np. po przez terminal przy pomocy komendy "java -jar sciezka_do_pliku_jar")
3. <b>Brak Mavena oraz Intellij IDEA:</b> wejście do folderu programu, 
a następnie przy pomocy terminala uruchomienie apllikacji po przez komendę "mvn spring-boot:run". 
(Można wykorzystać komendę mvn ponieważ Maven jest zawarty w folderze projektu)

We wszystkich powyższych przypadkach należy się upewnić, że port 8080 jest wolny.<br>
W przypadku opcji nr 2 oraz 3 należy się upewnić że wśród zmiennych systemowych znajduje się JAVA_HOME.<br>
W przypadku opcji nr 2 należy także się upewnić że dodana jest scieżka mavena.
##### Działanie 
Po uruchomieniu aplikacji uruchamiany jest serwer tomcat na porcie 8080 który obsługuje zapytania typu REST.
Dostępne są dwa zapytania typu get:
- /api/users/{user}/repos - zwraca listę obiektów(name = nazwa repozytorium,stars = ilość gwiazdek) w postaci 
    JSON zawierającą wszystkie repozytoria użytkownika {user}.
- /api/users/{user}/stars - zwraca obiekt typu stars(stars - suma gwiazdek) w postaci JSON który zawiera sumę 
gwiazdek wszystkich projektów użytkownika{user}.

W Przypadku problemów ze znalezieniem danego użytkownika zwracany jest kod 500.<br>
Działanie aplikacji można przetestować w przeglądarce internetowej po przez dodanie adresu lokalnego localhost:8080.<br>
Przykład:
- localhost:8080/api/users/Lambada9992/repos
- localhost:8080/api/users/Lambada9992/stars
### Propozycje rozszerzenia
- Rozszerzenia możliwości funkcji repos np. przez dodanie możliwości sortowania wywoływanej po przez argument zapytania etc.
- Wykorzystanie Rective Spring Web w celu umożliwienia pracy nie blokującej, asynchronicznej (Wykorzystanie FLUX, MONO oraz WebClient).
- Dodanie możliwości odpowiadania na kolejne(nowe) zapytania Http