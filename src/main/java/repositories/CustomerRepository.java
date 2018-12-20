
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.Endorsment;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Note;
import domain.Report;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c join c.userAccount u where u.username = ?1")
	Customer getCustomerByUsername(String username);

	@Query("select c.fixUpTasks from Customer c where c.id = ?1")
	Collection<FixUpTask> findFixUpTasksById(int customerId);

	@Query("select f.complaints from Customer c join c.fixUpTasks f where c.id = ?1")
	Collection<Complaint> findComplaintsById(int customerId);

	@Query("select f.applications from Customer c join c.fixUpTasks f where c.id = ?1")
	Collection<Application> findApplicationsById(int customerId);

	//CAMBIAR TRAS CAMBIO DE RELACION REPORT-COMPLAINT
	@Query("select rep from Customer c join c.fixUpTasks f join f.complaints co join co.referee r join r.reports rep where c.id = ?1")
	Collection<Report> findReportsById(int customerId);

	//CAMBIAR TRAS CAMBIO DE RELACION REPORT-COMPLAINT
	@Query("select n from Customer c join c.fixUpTasks f join f.complaints co join co.referee r join r.reports rep join rep.notes n where c.id = ?1")
	Collection<Note> findNotesById(int customerId);

	@Query("select e from Endorsment e join e.writtenBy a where a.id = ?1 union select e from Endorsment e join e.writtenTo a where a.id = ?1")
	Collection<Endorsment> AllEndorsmentsById(int customerId);

	@Query("select e from Endorsment e join e.writtenBy a where a.id = ?1")
	Collection<Endorsment> endorsmentsOfById(int customerId);

	@Query("select h from Customer c join c.fixUpTasks f join f.applications a join a.handyWorker h where c.id = ?1")
	Collection<HandyWorker> handyWorkersById(int customerId);

}
