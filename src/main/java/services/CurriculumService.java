
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CurriculumRepository;
import utilities.RandomString;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;

@Service
@Transactional
public class CurriculumService {

	// Manged Repository

	@Autowired
	private CurriculumRepository	curriculumRepository;

	// Supporting service

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private PersonalRecordService	personalRecordService;


	// Simple CRUD methods

	public Curriculum create() {

		List<EndorserRecord> endorserRecords = new ArrayList<EndorserRecord>();
		List<MiscellaneousRecord> miscellaneousRecords = new ArrayList<MiscellaneousRecord>();
		List<EducationRecord> educationRecords = new ArrayList<EducationRecord>();
		List<ProfessionalRecord> professionalRecords = new ArrayList<ProfessionalRecord>();
		PersonalRecord personalRecord = new PersonalRecord();
		personalRecord = this.personalRecordService.create();

		Curriculum curriculum = new Curriculum();
		curriculum.setTicker(this.generateTicker());
		curriculum.setEndorserRecords(endorserRecords);
		curriculum.setMiscellaneousRecords(miscellaneousRecords);
		curriculum.setEducationRecords(educationRecords);
		curriculum.setPersonalRecord(personalRecord);
		curriculum.setProfessionalRecords(professionalRecords);

		return curriculum;

	}

	public Curriculum create(List<EndorserRecord> endorserRecords, List<MiscellaneousRecord> miscellaneousRecords, List<EducationRecord> educationRecords, List<ProfessionalRecord> professionalRecords, PersonalRecord personalRecord) {

		Curriculum curriculum = new Curriculum();
		curriculum.setTicker(this.generateTicker());
		curriculum.setEndorserRecords(endorserRecords);
		curriculum.setMiscellaneousRecords(miscellaneousRecords);
		curriculum.setEducationRecords(educationRecords);
		curriculum.setPersonalRecord(personalRecord);
		curriculum.setProfessionalRecords(professionalRecords);

		return curriculum;

	}

	//Método auxiliar para generar el ticker-------------------------------
	public String generateTicker() {
		String res = "";
		Date date = null;
		String date1;
		String date2 = LocalDate.now().toString();
		String gen = new RandomString(6).nextString();
		List<Curriculum> lc = this.curriculumRepository.findAll();
		SimpleDateFormat df_in = new SimpleDateFormat("yyMMdd");
		SimpleDateFormat df_output = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = df_output.parse(date2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		date1 = df_in.format(date);
		res = res + date1 + "-" + gen;
		for (Curriculum c : lc) {
			if (c.getTicker() == res) {
				return this.generateTicker();
			}
		}
		return res;
	}

	public Collection<Curriculum> findAll() {
		Collection<Curriculum> result;

		result = this.curriculumRepository.findAll();

		return result;
	}

	public Curriculum findOne(Integer id) {

		Curriculum result = this.curriculumRepository.findOne(id);
		return result;

	}

	public Curriculum save(Curriculum curriculum) {
		return this.curriculumRepository.save(curriculum);

	}

	public void delete(Curriculum curriculum) {
		HandyWorker handyCurriculum = this.getHandyWorkerByCurriculum(curriculum.getId());
		handyCurriculum.setCurriculum(null);
		this.handyWorkerService.save(handyCurriculum);
		this.curriculumRepository.delete(curriculum);

	}

	public HandyWorker getHandyWorkerByCurriculum(int id) {
		return this.curriculumRepository.getHandyWorkerByCurriculum(id);
	}

}
