package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Menu;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author comrad
 */
public interface MenuRepository extends CrudRepository<Menu, Long> {
    
    @Query(value = "from Menu m where m.parent is null and m.active = true order by m.weight, m.id")
    List<Menu> findMainMenu();
    
    @Query(value = "from Menu m where m.parent = :parent and m.active = true order by m.weight, m.id")
    List<Menu> findChildren(@Param("parent") final Menu menu);
}
