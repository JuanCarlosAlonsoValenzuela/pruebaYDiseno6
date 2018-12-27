
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;
import domain.Tutorial;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Integer> {

	@Query("select h from HandyWorker h join h.tutorials t where (select t2 from Tutorial t2 where t2.id = ?1) in t")
	HandyWorker getTutorialAuthor(int tutorialId);

}
