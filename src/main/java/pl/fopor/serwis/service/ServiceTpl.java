package pl.fopor.serwis.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author marcin22x
 * @param <E> Odpowiednia klasa z adnotacją {@code @Entity}
 */
public interface ServiceTpl<E> {

    /**
     * Pobiera jeden obiekt.
     *
     * @author marcin22x
     * @param id Identyfikator obiektu.
     * @return Znaleziony obiekt jako {@code Optional}.
     */
    Optional<E> getId(Integer id);

    /**
     * Zwraca wszystkie istniejące obiekty.
     *
     * Domyślnie metoda zwraca pustą listę.
     *
     * @author marcin22x
     * @deprecated Zaleca się używanie {@link #getPage(Pageable)}
     * @return Listę obiektów.
     * @see #getPage(Pageable)
     */
    @Deprecated
    default List<E> getAll() {
        return new ArrayList<E>();
    }

    /**
     * Zwraca listę obiektu z zastosowanie paginacji.
     *
     * @author marcin22x
     * @param pageable Ustawienia paginacji.
     * @return Lista obiektó typu {@code Page}.
     */
    Page<E> getPage(Pageable pageable);

    /**
     * Zapisuje obiekt.
     *
     * @author marcin22x
     * @param object Obiekt, który ma zostać zapisany.
     * @return Utworzony obiekt.
     */
    E save(E object);

    /**
     * Usuwa istniejący obiekt.
     *
     * @author marcin22x
     * @param id Identyfikator obiektu.
     * @return {@code true} jeżęli obiekt zostł usunięty lub {@code false} jeżeli nie.
     */
    boolean deleteId(Integer id);
}
