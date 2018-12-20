
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.EndorserRepository;
import domain.Endorser;

@Service
@Transactional
public class EndorserService {

	// Managed repository ---------------------------------------------------------------------------------------------------

	@Autowired
	private EndorserRepository	endorserRepository;

	// Supporting Services --------------------------------------------------------------------------------------------------
	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods --------------------------------------------------------------------------------------------------

	public Endorser createEndorser(String name, String middleName, String surname, String photo, String email, String phoneNumber, String address, String userName, String password, Double score) {
		Endorser endorser = new Endorser();
		endorser = (Endorser) this.actorService.createActor(name, middleName, surname, photo, email, phoneNumber, address, userName, password);

		endorser.setScore(score);

		return endorser;

	}

	public Collection<Endorser> findAll() {
		return this.endorserRepository.findAll();
	}

	public Endorser findOne(int id) {
		return this.endorserRepository.findOne(id);
	}

	public Endorser save(Endorser endorser) {
		return this.endorserRepository.save(endorser);
	}

	public void delete(Endorser endorser) {
		this.endorserRepository.delete(endorser);
	}
}
