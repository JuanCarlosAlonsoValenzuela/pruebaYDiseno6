
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.MiscellaneousRecordRepository;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Manged Repository

	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;


	// Simple CRUD methods

	public MiscellaneousRecord create() {

		List<String> comments = new ArrayList<String>();
		MiscellaneousRecord miscellaneousRecord = new MiscellaneousRecord();
		miscellaneousRecord.setTitle("");
		miscellaneousRecord.setLinkAttachment("");
		miscellaneousRecord.setComments(comments);

		return miscellaneousRecord;

	}

	public MiscellaneousRecord create(String title, String linkAttachment, List<String> comments) {

		MiscellaneousRecord miscellaneousRecord = new MiscellaneousRecord();
		miscellaneousRecord.setTitle(title);
		miscellaneousRecord.setLinkAttachment(linkAttachment);
		miscellaneousRecord.setComments(comments);

		return miscellaneousRecord;

	}
	public Collection<MiscellaneousRecord> findAll() {
		return this.miscellaneousRecordRepository.findAll();
	}
	public MiscellaneousRecord findOne(Integer id) {
		return this.miscellaneousRecordRepository.findOne(id);
	}

	public void save(MiscellaneousRecord niscellaneousRecord) {
		this.miscellaneousRecordRepository.save(niscellaneousRecord);
	}

	public void delete(MiscellaneousRecord miscellaneousRecord) {
		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
	}

	public void deleteAll(List<MiscellaneousRecord> miscellaneousRecord) {
		this.miscellaneousRecordRepository.deleteInBatch(miscellaneousRecord);
	}
}
