
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.EndorsementRepository;
import security.LoginService;
import security.UserAccount;
import domain.Endorsement;
import domain.Endorser;

@Service
@Transactional
public class EndorsementService {

	// Managed repository ------------------------------------------

	@Autowired
	private EndorsementRepository	endorsmentRepository;
	@Autowired
	private ActorService			actorService;


	// Supporting Services ------------------------------------------

	// Simple CRUD methods ------------------------------------------

	public Endorsement create() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		List<String> comments = new ArrayList<String>();

		Endorser sender = this.endorsmentRepository.getEndorserByUsername(userAccount.getUsername());

		Endorsement endorsment = new Endorsement();

		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() - 1);

		endorsment.setComments(comments);
		endorsment.setMoment(thisMoment);
		endorsment.setWrittenBy(sender);
		endorsment.setWrittenTo(null);

		return endorsment;

	}

	public Endorsement createEndorsment(List<String> comments, Endorser writtenTo) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Endorser sender = (Endorser) this.actorService.getActorByUsername(userAccount.getUsername());

		Endorsement endorsment = new Endorsement();

		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() - 1);

		endorsment.setComments(comments);
		endorsment.setMoment(thisMoment);
		endorsment.setWrittenBy(sender);
		endorsment.setWrittenTo(writtenTo);

		return endorsment;

	}

	public Collection<Endorsement> findAll() {
		return this.endorsmentRepository.findAll();
	}

	public Endorsement findOne(int id) {
		return this.endorsmentRepository.findOne(id);
	}

	public Endorsement save(Endorsement endorsment) {
		return this.endorsmentRepository.save(endorsment);
	}

	public void delete(Endorsement endorsment) {
		this.endorsmentRepository.delete(endorsment);
	}

	public void deleteAll(List<Endorsement> endorsments) {
		this.endorsmentRepository.deleteInBatch(endorsments);
	}

}
