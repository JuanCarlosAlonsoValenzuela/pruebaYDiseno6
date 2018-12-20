
package services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.PhaseRepository;
import domain.Phase;

@Service
@Transactional
public class PhaseService {

	@Autowired
	private PhaseRepository	phaseRepository;


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

}
