
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.Endorsment;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Phase;
import domain.Tutorial;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {

	@Query("select a.applications from HandyWorker a where a.id = ?1")
	public Collection<Application> getAllApplicationsFromAHandyWorker(int id);

	@Query("select h from HandyWorker h join h.userAccount u where u.username = ?1")
	public HandyWorker getHandyWorkerByUsername(String username);

	@Query("select h from Endorser h join h.userAccount u where u.username = ?1")
	public HandyWorker getEndorserByUsername(String username);

	@Query("select a from Customer a join a.fixUpTasks b where b.id = ?1")
	public Customer getCustomerByFixUpTask(int id);

	//Se utiliza en deletePhaseForApplication
	@Query("select a.phases from FixUpTask a join a.applications b where b.id = ?1")
	public Collection<Phase> getPhasesByApplication(int id);

	@Query("select f from FixUpTask f join f.phases p where p.id= ?1")
	public FixUpTask getFixUpTaskByPhase(int id);

	//Se utiliza en deletePhaseForHandyWorker
	@Query("select a.phases from FixUpTask a join a.applications b join b.handyWorker c where c.id = ?1")
	public Collection<Phase> getPhasesByHandyWorker(int id);

	@Query("select a.finder from HandyWorker a where a.id = ?1")
	public Finder getFinderFromAHandyWorker(int id);

	@Query("select c.id from Customer c join c.fixUpTasks f join f.applications a join a.handyWorker b where b.id = ?1")
	public List<Integer> getCustomersFromHandyWorker(int id);

	@Query("select distinct c.complaints from FixUpTask c join c.applications a join a.handyWorker h where h.id = ?1")
	public List<Complaint> getComplaintsFromHandyWorker(int id);

	@Query("select a.tutorials from HandyWorker a where a.id = ?1")
	public List<Tutorial> getAllTutorialsFromAHandyWorker(int id);

	@Query("select a.endorsments from Endorser a where a.id = ?1")
	public List<Endorsment> getEndorsmentsByEndorser(int id);

	//Querys del Filtro de Finder
	@Query("select c from FixUpTask c where c.ticker like '?1' or c.description like '?1' or c.address like '?1'")
	public List<FixUpTask> getFixUpTaskByKeyWord(String keyWord);

	@Query("select f from FixUpTask f join f.category c where c.name='?1'")
	public List<FixUpTask> getFixUpTaskByCategory(String category);

	@Query("select a from FixUpTask a join a.warranty b where b.title = ?1")
	public Collection<FixUpTask> getFixUpTasksByWarranty(String warranty);

	@Query("select a from FixUpTask a where (a.maxPrice) between (select b.minPrice from Finder b where b.id = ?1) and (select c.maxPrice from Finder c where c.id = ?1) order by a.maxPrice")
	public Collection<FixUpTask> getFixUpTasksByPrice(int id);

	@Query("select a from FixUpTask a where (a.realizationTime) between (select b.startDate from Finder b where b.id = ?1) and (select c.endDate from Finder c where c.id = ?1) order by a.realizationTime")
	public Collection<FixUpTask> getFixUpTasksByDate(int id);

	@Query("select a.fixUpTask from Application a where a.id = ?1")
	public FixUpTask getFixUpTaskFromApplication(int id);

	@Query("select distinct a.fixUpTask from Application a join a.handyWorker b where b.id = ?1")
	public List<FixUpTask> getFixUpTasksFromHandyWorker(int id);

	@Query("select f.phases from FixUpTask f where f.id=?1")
	public List<Phase> getPhasesByFixUpTask(int id);

}
