
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ProfessionalRecordRepository;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	// Manged Repository

	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;


	// Simple CRUD methods

	public ProfessionalRecord create() {

		List<String> comments = new ArrayList<String>();
		ProfessionalRecord professionalRecord = new ProfessionalRecord();
		professionalRecord.setNameCompany("");
		professionalRecord.setStartDate(null);
		professionalRecord.setEndDate(null);
		professionalRecord.setRole("");
		professionalRecord.setLinkAttachment("");
		professionalRecord.setComments(comments);

		return professionalRecord;

	}

	public ProfessionalRecord create(String nameCompany, Date startDate, Date endDate, String role, String linkAttachment, List<String> comments) {

		ProfessionalRecord professionalRecord = new ProfessionalRecord();
		professionalRecord.setNameCompany(nameCompany);
		professionalRecord.setStartDate(startDate);
		professionalRecord.setEndDate(endDate);
		professionalRecord.setRole(role);
		professionalRecord.setLinkAttachment(linkAttachment);
		professionalRecord.setComments(comments);

		return professionalRecord;

	}

	public Collection<ProfessionalRecord> findAll() {
		return this.professionalRecordRepository.findAll();
	}
	public ProfessionalRecord findOne(Integer id) {
		return this.professionalRecordRepository.findOne(id);
	}

	public void save(ProfessionalRecord professionalRecord) {
		this.professionalRecordRepository.save(professionalRecord);
	}

	public void delete(ProfessionalRecord professionalRecord) {
		this.professionalRecordRepository.delete(professionalRecord);
	}

	public void deleteAll(List<ProfessionalRecord> professionalRecords) {
		this.professionalRecordRepository.deleteInBatch(professionalRecords);
	}
}
