
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.EndorserRecordRepository;
import domain.EndorserRecord;

@Service
@Transactional
public class EndorserRecordService {

	// Manged Repository

	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;


	// Simple CRUD methods

	public EndorserRecord create() {

		List<String> comments = new ArrayList<String>();

		EndorserRecord endorserRecord = new EndorserRecord();
		endorserRecord.setFullName("");
		endorserRecord.setEmail("");
		endorserRecord.setPhoneNumber("");
		endorserRecord.setLinkLinkedInProfile("");
		endorserRecord.setComments(comments);

		return endorserRecord;
	}

	public EndorserRecord create(String fullName, String email, String phoneNumber, String linkLinkedInProfile, List<String> comments) {

		EndorserRecord endorserRecord = new EndorserRecord();
		endorserRecord.setFullName(fullName);
		endorserRecord.setEmail(email);
		endorserRecord.setPhoneNumber(phoneNumber);
		endorserRecord.setLinkLinkedInProfile(linkLinkedInProfile);
		endorserRecord.setComments(comments);

		return endorserRecord;

	}

	public Collection<EndorserRecord> findAll() {
		return this.endorserRecordRepository.findAll();
	}
	public EndorserRecord findOne(Integer id) {
		return this.endorserRecordRepository.findOne(id);
	}

	public void save(EndorserRecord endorserRecord) {
		this.endorserRecordRepository.save(endorserRecord);
	}

	public void delete(EndorserRecord endorserRecord) {
		this.endorserRecordRepository.delete(endorserRecord);
	}

	public void deleteAll(List<EndorserRecord> endorserRecord) {
		this.endorserRecordRepository.deleteInBatch(endorserRecord);
	}

}
