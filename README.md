# eDziennik - Projekt BD2021
## Autorzy:
* Paweł Dymara - [Akatroj](https://github.com/Akatroj)
* Tomasz Słonina - [Sirtus](https://github.com/Sirtus)
* Konrad Starowiejski - [konradSt00](https://github.com/konradSt00)

## Jak używać:
Projekt wykorzystuje Maven do zarządzania zależnościami. Do uruchomienia wystarczy dodać w IntelliJ konfiguracje: 
```
mvn compile javafx:run
```
Do uruchomienia aplikacji potrzebny jest uruchomiony na 127.0.0.1 serwer bazodanowy Apache Derby (nie załączony w repozytorium).

W repozytorium znajduje się plik `MERGED_MOCK_DATA.sql` który pozwala wypełnić bazę przykładowymi danymi.

Po wypełnieniu bazy tymi danymi można zalogować się do aplikacji m.in. przez te konta:

|   Rodzaj   |       Login      |  Hasło   |
|------------|------------------|----------|
|   Student  | Maribeth Calvert | haslo123 |
| Nauczyciel |   Osmund Inder   | haslo123 |


## Oryginalna koncepcja:
W czasach zdalnego nauczania szkoła nie może istnieć bez
elektornicznego systemu zarządzania, dlatego zdecydowaliśmy 
się na stworzenie e-dziennika, który umożliwi uczniom przejrzenie
ocen, nauczycielom wpisanie ich oraz odnotowanie uwag z 
zachowania. Uczniowie będą mieli możliwość sprawdzić szczęśliwy 
numerek, który chroni przed pytaniem "przy tablicy". Aby to 
wszystko mogło zadziałać kluczową rolę odegrać musi baza danych.
Wybraliśmy bazę PostgreSQL, a aplikację napiszemy wykorzystując
język Java oraz framework Hibernate. GUI wykonamy w JavaFX.

## Zmiany w stosunku do oryginalnej koncepcji
* Zamiast PostgreSQL zastosowaliśmy Apache Derby.
* Zrezygnowaliśmy ze szczęśliwych numerków oraz uwag z zachowania.

## Co zostało zrobione:
* Ekran logowania

By uzyskać dostęp do aplikacji należy zalogować się przy pomocy loginu i hasła.


<img src="https://github.com/Sirtus/eDziennik/blob/assets/demo%20images/login.png" alt="Login screen" height=500px width=auto>

* Widok ocen dla ucznia:


Jako uczeń możesz wyświetlić swoje oceny z każdego przedmiotu. Brzydkie kolory dla złych ocen mają zachęcić uczniów do zdobywania lepszych ocen.


<img src="https://github.com/Sirtus/eDziennik/blob/assets/demo%20images/student.png" alt="Student's grades" height=500px width=auto>

* Widok główny dla nauczyciela:


Nauczyciel może wybrać czy chce wpisywać oceny klasie na prowadzonych zajęciach, czy też podejrzeć oceny swoich wychowanków (klasy której jest wychowawcą)


<img src="https://github.com/Sirtus/eDziennik/blob/assets/demo%20images/Teacher.png" alt="Teacher main view" height=500px width=auto>

* Widok dla wychowawcy:


Po kliknięciu w ucznia na liście uczniów wychowywanej klasy można podejrzeć jego wszystkie oceny.


<img src="https://github.com/Sirtus/eDziennik/blob/assets/demo%20images/classTutor.png" alt="Class tutor view" height=500px width=auto>

* Wystawianie i edytowanie ocen twoim uczniom:


Po wybraniu przedmiotu z którego nauczyciel chce wpisać oceny, jego oczom ukazuje się lista klas których uczy tego przedmiotu.


<img src="https://github.com/Sirtus/eDziennik/blob/assets/demo%20images/teacherClassPick.png" alt="Teacher picking class" height=500px width=auto>

Po wybraniu klasy, nauczyciel może dodawać, edytować i usuwać oceny uczniów wybranej klasy z prowadzonego przedmiotu.


<img src="https://github.com/Sirtus/eDziennik/blob/assets/demo%20images/editGrade.png" alt="Teacher grade editing" height=500px width=auto>


# Schemat bazy danych:

<img src="https://github.com/Sirtus/eDziennik/blob/assets/demo%20images/schema.png" alt="Database schema" height=500px width=auto>

