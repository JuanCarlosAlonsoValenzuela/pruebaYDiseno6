
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.FixUpTaskRepository;
import utilities.RandomString;
import domain.Application;
import domain.Category;
import domain.Complaint;
import domain.FixUpTask;
import domain.Phase;
import domain.Warranty;

@Service
@Transactional
public class FixUpTaskService {

	@Autowired
	private FixUpTaskRepository	fixUpTaskRepository;


	public FixUpTask create() {
		FixUpTask fixUpTask = new FixUpTask();
		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() - 1);

		Collection<Phase> phases = null;
		Collection<Complaint> complaints = null;
		Collection<Application> applications = null;

		fixUpTask.setTicker(this.generateTicker());
		fixUpTask.setMomentPublished(thisMoment);
		fixUpTask.setDescription("");
		fixUpTask.setAddress("");
		fixUpTask.setMaxPrice(0);
		fixUpTask.setRealizationTime(null);
		fixUpTask.setWarranty(null);
		fixUpTask.setPhases(phases);
		fixUpTask.setCategory(null);
		fixUpTask.setComplaints(complaints);
		fixUpTask.setApplications(applications);
		return fixUpTask;
	}

	public FixUpTask create(String description, String address, Double maxPrice, Date realizationTime, Warranty warranty, Collection<Phase> phases, Category category, Collection<Complaint> complaints, Collection<Application> applications) {
		FixUpTask fixUpTask = new FixUpTask();
		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() - 1);

		fixUpTask.setTicker(this.generateTicker());
		fixUpTask.setMomentPublished(thisMoment);
		fixUpTask.setDescription(description);
		fixUpTask.setAddress(address);
		fixUpTask.setMaxPrice(maxPrice);
		fixUpTask.setRealizationTime(realizationTime);
		fixUpTask.setWarranty(warranty);
		fixUpTask.setPhases(phases);
		fixUpTask.setCategory(category);
		fixUpTask.setComplaints(complaints);
		fixUpTask.setApplications(applications);

		return fixUpTask;
	}

	public List<FixUpTask> findAll() {
		return this.fixUpTaskRepository.findAll();
	}

	public FixUpTask findOne(int fixUpTaskId) {
		return this.fixUpTaskRepository.findOne(fixUpTaskId);
	}

	public FixUpTask save(FixUpTask fixUpTask) {
		return this.fixUpTaskRepository.save(fixUpTask);
	}

	public void delete(FixUpTask fixUpTask) {
		this.fixUpTaskRepository.delete(fixUpTask);
	}

	//Método auxiliar para generar el ticker-------------------------------
	private String generateTicker() {
		String res = "";
		Date date = null;
		String date1;
		String date2 = LocalDate.now().toString();
		String gen = new RandomString(6).nextString();
		List<FixUpTask> lc = this.fixUpTaskRepository.findAll();
		SimpleDateFormat df_in = new SimpleDateFormat("yyMMdd");
		SimpleDateFormat df_output = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = df_output.parse(date2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		date1 = df_in.format(date);
		res = res + date1 + "-" + gen;
		for (FixUpTask c : lc)
			if (c.getTicker() == res)
				return this.generateTicker();
		return res;
	}
}
