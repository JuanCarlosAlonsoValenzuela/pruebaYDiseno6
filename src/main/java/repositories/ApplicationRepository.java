
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select a.applications from HandyWorker a where a.id = ?1")
	public Collection<Application> getAllApplicationsFromAHandyWorker(int id);

	@Query("select a.applications from FixUpTask a where a.id = ?1")
	public Collection<Application> getAllApplicationsFromAFixUpTask(int id);
}
