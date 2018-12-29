
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.PhaseRepository;
import domain.Application;
import domain.FixUpTask;
import domain.Phase;

@Service
@Transactional
public class PhaseService {

	@Autowired
	private PhaseRepository		phaseRepository;

	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;


	public Phase create() {
		Phase phase = new Phase();
		phase.setTitle("");
		phase.setDescription("");
		phase.setStartMoment(null);
		phase.setEndMoment(null);
		return phase;
	}

	public Phase create(String title, String description, Date startMoment, Date endMoment) {
		Phase phase = new Phase();
		phase.setTitle(title);
		phase.setDescription(description);
		phase.setStartMoment(startMoment);
		phase.setEndMoment(endMoment);
		return phase;
	}

	public List<Phase> findAll() {
		return this.phaseRepository.findAll();
	}

	public Phase findOne(int phaseId) {
		return this.phaseRepository.findOne(phaseId);
	}

	public Phase save(Phase phase) {
		return this.phaseRepository.save(phase);
	}

	public void delete(Phase phase) {
		this.phaseRepository.delete(phase);
	}

	public void saveAndUpdateFixUpTask(Phase phase, int applicationId) {
		Phase newPhase = this.phaseRepository.save(phase);
		Application application = this.applicationService.findOne(applicationId);
		FixUpTask fixUpTask = application.getFixUpTask();
		Collection<Phase> phases = fixUpTask.getPhases();
		phases.add(newPhase);
		fixUpTask.setPhases(phases);
		this.fixUpTaskService.save(fixUpTask);
	}
}
