
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;
import domain.FixUpTask;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("select c from Category c where c.name = ?1")
	public Category getCategoryByName(String c);

	@Query("select f from FixUpTask f join f.category c where c.name = ?1")
	public List<FixUpTask> getFixUpTasksByCategory(String c);

}
