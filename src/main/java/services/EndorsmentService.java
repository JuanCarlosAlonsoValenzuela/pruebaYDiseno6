
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.EndorsmentRepository;
import security.LoginService;
import security.UserAccount;
import domain.Endorser;
import domain.Endorsment;

@Service
@Transactional
public class EndorsmentService {

	// Managed repository ------------------------------------------

	@Autowired
	private EndorsmentRepository	endorsmentRepository;
	@Autowired
	private ActorService			actorService;


	// Supporting Services ------------------------------------------

	// Simple CRUD methods ------------------------------------------

	public Endorsment createEndorsment() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		List<String> comments = new ArrayList<String>();

		Endorser sender = (Endorser) this.actorService.getActorByUsername(userAccount.getUsername());

		Endorsment endorsment = new Endorsment();

		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() - 1);

		endorsment.setComments(comments);
		endorsment.setMoment(thisMoment);
		endorsment.setWrittenBy(sender);
		endorsment.setWrittenTo(null);

		return endorsment;

	}

	public Endorsment createEndorsment(List<String> comments, Endorser writtenTo) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Endorser sender = (Endorser) this.actorService.getActorByUsername(userAccount.getUsername());

		Endorsment endorsment = new Endorsment();

		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() - 1);

		endorsment.setComments(comments);
		endorsment.setMoment(thisMoment);
		endorsment.setWrittenBy(sender);
		endorsment.setWrittenTo(writtenTo);

		return endorsment;

	}

	public Collection<Endorsment> findAll() {
		return this.endorsmentRepository.findAll();
	}

	public Endorsment findOne(int id) {
		return this.endorsmentRepository.findOne(id);
	}

	public Endorsment save(Endorsment endorsment) {
		return this.endorsmentRepository.save(endorsment);
	}

	public void delete(Endorsment endorsment) {
		this.endorsmentRepository.delete(endorsment);
	}

	public void deleteAll(List<Endorsment> endorsments) {
		this.endorsmentRepository.deleteInBatch(endorsments);
	}

}
