
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ApplicationRepository;
import domain.Application;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Status;

@Service
@Transactional
public class ApplicationService {

	// Managed repository ------------------------------------------

	@Autowired
	private ApplicationRepository	applicationRepository;
	@Autowired
	private FixUpTaskService		fixUpTaskService;
	@Autowired
	private HandyWorkerService		handyWorkerService;


	// Supporting Services ------------------------------------------

	//Simple CRUD methods ---------------------------------------------------------------------

	public Application createApplication() {
		Application application = new Application();

		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() - 1);
		FixUpTask fixUpTask = new FixUpTask();
		HandyWorker handyWorker = new HandyWorker();

		application.setMoment(thisMoment);
		application.setStatus(Status.PENDING);
		application.setOfferedPrice(0);
		List<String> comments = new ArrayList<>();
		application.setComments(comments);
		application.setFixUpTask(fixUpTask);
		application.setHandyWorker(handyWorker);

		return application;
	}

	public Application createApplication(double offeredPrice, FixUpTask fixUpTask, HandyWorker handyWorker) {
		Application application = new Application();

		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() - 1);

		application.setMoment(thisMoment);
		application.setStatus(Status.PENDING);
		application.setOfferedPrice(offeredPrice);
		List<String> comments = new ArrayList<>();
		application.setComments(comments);
		application.setFixUpTask(fixUpTask);
		application.setHandyWorker(handyWorker);

		return application;
	}

	//Aux

	public boolean validateCreditCardNumber(String str) {

		int[] ints = new int[str.length()];
		for (int i = 0; i < str.length(); i++)
			ints[i] = Integer.parseInt(str.substring(i, i + 1));
		for (int i = ints.length - 2; i >= 0; i = i - 2) {
			int j = ints[i];
			j = j * 2;
			if (j > 9)
				j = j % 10 + 1;
			ints[i] = j;
		}
		int sum = 0;
		for (int i = 0; i < ints.length; i++)
			sum += ints[i];
		if (sum % 10 == 0)
			return true;
		else
			return false;
	}

	// Simple CRUD methods ------------------------------------------

	public Collection<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	public Application findOne(int id) {
		return this.applicationRepository.findOne(id);
	}

	public Application save(Application application) {
		return this.applicationRepository.save(application);
	}

	public void delete2(Application application) {
		this.applicationRepository.delete(application);
	}

	public void delete(Application application) {
		List<Application> applicationsF = (List<Application>) application.getFixUpTask().getApplications();
		List<Application> applicationsH = application.getHandyWorker().getApplications();

		applicationsF.remove(application);
		applicationsH.remove(application);

		FixUpTask f = application.getFixUpTask();
		f.setApplications(applicationsF);
		this.fixUpTaskService.save(f);
		HandyWorker h = application.getHandyWorker();
		h.setApplications(applicationsH);
		this.handyWorkerService.save(h);
		this.applicationRepository.delete(application);
	}
	public void deleteAllFromHandyWorker(List<Application> applications) {

		while (applications.size() != 0) {
			this.delete(applications.get(0));
			applications.remove(applications.get(0));
		}
	}

	// Test methods ------------------------------------------

	public Collection<Application> getApplicationsHandy(HandyWorker handyWorker) {
		return this.applicationRepository.getAllApplicationsFromAHandyWorker(handyWorker.getId());
	}

	public Collection<Application> getApplicationsFix(FixUpTask fixupTask) {
		return this.applicationRepository.getAllApplicationsFromAFixUpTask(fixupTask.getId());
	}
}
