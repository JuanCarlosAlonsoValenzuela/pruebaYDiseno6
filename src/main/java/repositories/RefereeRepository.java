
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;
import domain.Note;
import domain.Referee;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Integer> {

	@Query("select r from Referee r join r.userAccount u where u.username = ?1")
	Referee getRefereeByUsername(String username);

	@Query("select c from Complaint c where c.referee is null")
	Collection<Complaint> complaintsUnassigned();

	@Query("select n from Referee r join r.reports rep join rep.notes n where r.id = ?1")
	Collection<Note> notesReferee(int refereeId);

}
