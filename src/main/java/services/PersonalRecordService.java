
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.PersonalRecordRepository;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {

	// Manged Repository

	@Autowired
	private PersonalRecordRepository	personalRecordRepository;


	public PersonalRecord create() {

		PersonalRecord personalRecord = new PersonalRecord();
		personalRecord.setFullName("");
		personalRecord.setPhoto("");
		personalRecord.setEmail("");
		personalRecord.setPhoneNumber("");
		personalRecord.setUrlLinkedInProfile("");

		return personalRecord;

	}

	// Simple CRUD methods

	public PersonalRecord create(String fullName, String photo, String email, String phoneNumber, String urlLinkedInProfile) {

		PersonalRecord personalRecord = new PersonalRecord();
		personalRecord.setFullName(fullName);
		personalRecord.setPhoto(photo);
		personalRecord.setEmail(email);
		personalRecord.setPhoneNumber(phoneNumber);
		personalRecord.setUrlLinkedInProfile(urlLinkedInProfile);

		return personalRecord;

	}

	public Collection<PersonalRecord> findAll() {
		return this.personalRecordRepository.findAll();
	}
	public PersonalRecord findOne(Integer id) {
		return this.personalRecordRepository.findOne(id);
	}

	public PersonalRecord save(PersonalRecord personalRecord) {
		return this.personalRecordRepository.save(personalRecord);
	}

	public void delete(PersonalRecord personalRecord) {
		this.personalRecordRepository.delete(personalRecord);
	}

}
