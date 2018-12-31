
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Endorsement;
import domain.Endorser;

@Repository
public interface EndorsementRepository extends JpaRepository<Endorsement, Integer> {

	@Query("select h from Endorser h join h.userAccount u where u.username = ?1")
	public Endorser getEndorserByUsername(String username);
}
