
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Warranty;

@Repository
public interface WarrantyRepository extends JpaRepository<Warranty, Integer> {

	@Query("select w from Warranty w where w.isDraftMode=?1")
	public List<Warranty> warrantiesFilteredByMode(Boolean isDraftMode);

}
