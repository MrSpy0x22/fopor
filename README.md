# FoPor

---

Forum-Porad jest prostym projektem realizowanym na potrzeby zaliczenia projektu z Programowania Aplikacji Mobilnych na "UTP w Bydgoszczy". TO repozytorium realizuje funkcję back-endu.

---

## Sprawy techniczne
Srodowisko pracy:
  * Java 11,
  * [SpringBoot](https://spring.io/projects/spring-boot) 2.4.3<,
  * [IntelliJ](https://www.jetbrains.com/idea/) jako IDE.
  * [JavaDoc](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html#@version)

---

## Kilka zasad:
Przy pracy z repozytorium należy pamiętać o kilku zasadach:
1. Dodajemu jak najwięcej komentarzy do kodu tak, aby inni mogli zrozumieć myśl autora,
2. Tworzymy dokumentacje komentarzami dla klas i metod,
3. Komentarze z dokumentacją obowiązkowo muszą zawierać adnotację `@author`,
4. Nie dodajemy komentarzy z dokumentacją dla klas z adnotacją `@Entity`, `@Service`, `@RestController`,
5. Tworzenie branch-ów do dodawanych ficzerów może być dobrym pomysłem,
6. Używamy interfejsów (patrz niżęj),
7. Dodajemy tylko potrzebne funkcje,
8. ...

---

## Przykłady
Poniżej przedstawiony został przykład użycia
* Komentarzy z dokumentacją,
* Adnotację @author,
* Interfejs jako wzór metod.
```java
 /**
  * @author marcin22x
  * @param <E> Odpowiednia klasa z adnotacją {@code @Entity}
  */
  public interface ControllerTpl<E> {

    /**
     * Pobiera jednego obiektu.
     *
     * @author marcin22x
     * @param id Identufikator obiektu.
     * @return Znaleziony obiekt.
     * @throws NoSuchElementException Jeżeli obiekt {@code id} nie istnieje.
     */
    E getId(Integer id) throws NoSuchElementException;

    // [...]
}
```

## ...

---

## Autorzy
1. [marcin22x](https://github.com/MrSpy0x22)
2. ...
3. ...