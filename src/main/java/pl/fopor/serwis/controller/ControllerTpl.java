package pl.fopor.serwis.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

    /**
     * Pobranie wszystkich obiektów.
     *
     * Metoda domyślnie zwraca pustą listę.
     *
     * @author marcin22x
     * @deprecated Zaleca się skorzystanie z {@link #getPageOf(Pageable)}.
     * @return Lista obiektów.
     * @see #getPageOf(Pageable)
     */
    @Deprecated
    default List<E> getAll() {
        return new ArrayList<>();
    }

    /**
     * Pobiera listę obiektów z zastosowanie paginacji.
     *
     * @author marcin22x
     * @param pageable Ustawienia paginacji.
     * @return Lista obiektó typu {@code Page}.
     * @
     */
    Page<E> getPageOf(Pageable pageable);

    /**
     * Zapisuje nowy obiekt. Standardowo metoda przeznaczona
     * jest dla obiektu, który jeszcze nie istnieje.
     *
     * @author marcin22x
     * @param object Obiekt, który ma zostać zapisany.
     * @return Utworzony obiekt.
     */
    E postObject(E object);

    /**
     * Modyfikuje istniejący obiekt.
     *
     * @author marcin22x
     * @param object Obiekt, którego pola zostaną użyte do nadpisania.
     * @param id Identyfikator obiektu.
     * @return Utworzony obiekt.
     * @throws NoSuchElementException Jeżeli obiekt nie istnieje.
     */
    E putObject(E object , Integer id);

    /**
     * Usuwa istniejący obiekt.
     *
     * @author marcin22x
     * @param id Identyfikator obiektu.
     * @return {@code true} jeżeli obiekt został usunięty lub {@code false} jeżeli nie.
     */
    boolean deleteId(Integer id);

}
