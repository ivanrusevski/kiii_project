package mk.ukim.finki.wp.magacin.repository;

import mk.ukim.finki.wp.magacin.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
}
