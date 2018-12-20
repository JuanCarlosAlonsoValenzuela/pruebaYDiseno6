
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Admin;
import domain.Customer;
import domain.HandyWorker;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	@Query("select avg(c.fixUpTasks.size), min(c.fixUpTasks.size), max(c.fixUpTasks.size), (sqrt(sum(c.fixUpTasks.size * c.fixUpTasks.size)/count(c.fixUpTasks.size) - (avg(c.fixUpTasks.size)* avg(c.fixUpTasks.size)))) from Endorser c")
	public Double[] fixUpTaskPerUser();

	@Query("select avg(a.applications.size), min(a.applications.size), max(a.applications.size), (sqrt(sum(a.applications.size * a.applications.size) / count(a.applications.size) - (avg(a.applications.size)*avg(a.applications.size)))) from FixUpTask a")
	public Double[] applicationPerFixUpTask();

	@Query("select avg(f.maxPrice), min(f.maxPrice), max(f.maxPrice), (sqrt(sum(f.maxPrice * f.maxPrice) / count(f.maxPrice) - (avg(f.maxPrice) * avg(f.maxPrice)))) from FixUpTask f")
	public Double[] maxPricePerFixUpTask();

	@Query("select avg(a.offeredPrice), min(a.offeredPrice), max(a.offeredPrice), (sqrt(sum(a.offeredPrice*a.offeredPrice)/count(a.offeredPrice)-(avg(a.offeredPrice) * avg(a.offeredPrice)))) from Application a")
	public Double[] priceOferredPerApplication();

	@Query("select distinct (cast((select count(a1.status) from Application a1 where status='PENDING') as float)/ (select count(a2.status) from Application a2) * 100) from Application a3")
	public Double ratioPendingApplications();

	@Query("select distinct (cast((select count(a1.status) from Application a1 where status='ACCEPTED') as float)/ (select count(a2.status) from Application a2) * 100) from Application a3")
	public Double ratioAcceptedApplications();

	@Query("select distinct (cast((select count(a1.status) from Application a1 where status='REJECTED') as float)/ (select count(a2.status) from Application a2) * 100) from Application a3")
	public Double ratioRejectedApplications();

	@Query("select distinct(cast((select count(a1.status) from Application a1 join a1.fixUpTask f where a1.status='PENDING' and f.realizationTime < (NOW())) as float)/ (select count(a2.status) from Application a2) * 100) from Application a3")
	public Double ratioPendingElapsedApplications();

	@Query("select distinct c from Customer c, FixUpTask d where c.fixUpTasks.size >= 1.1 * (select avg(c.fixUpTasks.size) from Customer c) order by d.applications.size")
	public List<Customer> customers10PercentMoreApplications();

	@Query("select c from HandyWorker c where c.applications.size >= 1.1 * (select avg(c.applications.size) from HandyWorker c) order by c.applications.size")
	public List<HandyWorker> handyWorkers10PercentMoreApplications();

	@Query("select avg(c.complaints.size), min(c.complaints.size), max(c.complaints.size), (sqrt(sum(c.complaints.size * c.complaints.size)/count(c.complaints.size) - (avg(c.complaints.size)* avg(c.complaints.size)))) from FixUpTask c")
	public Double[] numberComplaintsPerFixUpTask();

	@Query("select avg(c.notes.size), min(c.notes.size), max(c.notes.size), (sqrt(sum(c.notes.size * c.notes.size)/count(c.notes.size) - (avg(c.notes.size)* avg(c.notes.size)))) from Report c")
	public Double[] notesPerReferee();

	@Query("select distinct(cast((select count(a1) from FixUpTask a1 where a1.complaints is not empty) as float)/ (select count(a2) from FixUpTask a2) * 100) from Application a3")
	public Double fixUpTaskWithComplain();

	@Query("select distinct a from Customer a join a.fixUpTasks f where (f.complaints.size) > 0 order by f.complaints.size")
	public List<Customer> customerTermsofComplainsOrdered();

	@Query("select distinct a.handyWorker from Application a join a.fixUpTask f where (f.complaints.size) > 0 order by f.complaints.size")
	public List<HandyWorker> HandyWorkerTermsofComplainsOrdered();

	@Query("select a from Admin a join a.userAccount b where b.username = ?1")
	public Admin getAdminByUserName(String a);

	@Query("select a from Admin a")
	public List<Admin> findAll2();

}
