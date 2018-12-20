
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CurriculumServiceTest extends AbstractTest {

	@Autowired
	private CurriculumService	curriculumService;


	@Test
	public void testSaveCurriculum() {
		Curriculum curriculum, saved;
		Collection<Curriculum> curriculums;

		List<EndorserRecord> endorserRecords = new ArrayList<EndorserRecord>();
		List<ProfessionalRecord> professionalRecords = new ArrayList<ProfessionalRecord>();
		List<EducationRecord> educationRecords = new ArrayList<EducationRecord>();
		List<MiscellaneousRecord> miscellaneousRecords = new ArrayList<MiscellaneousRecord>();

		PersonalRecord personalRecord = new PersonalRecord();
		personalRecord.setEmail("paco@gmail.com");
		personalRecord.setFullName("Paco");
		personalRecord.setPhoneNumber("+34710425963");
		personalRecord.setPhoto("https://www.photo.com");
		personalRecord.setUrlLinkedInProfile("https://www.linkedin.com");

		MiscellaneousRecord miscellaneousRecord = new MiscellaneousRecord();
		ProfessionalRecord professionalRecord = new ProfessionalRecord();
		EducationRecord educationRecord = new EducationRecord();
		EndorserRecord endorserRecord = new EndorserRecord();

		endorserRecord.setLinkLinkedInProfile("https://www.linkedin.com");
		endorserRecord.setEmail("paco@gmail.com");
		endorserRecord.setFullName("Paco");
		endorserRecord.setPhoneNumber("+34710425963");

		Date endDate = new Date();
		endDate.setYear(28);
		Date startDate = new Date();
		startDate.setYear(27);
		educationRecord.setEndDateStudy(endDate);
		educationRecord.setStartDateStudy(startDate);
		educationRecord.setInstitution("institution");
		educationRecord.setTitle("title");
		educationRecord.setUrl("https://www.url.com/example30");

		professionalRecord.setEndDate(endDate);
		professionalRecord.setStartDate(startDate);
		professionalRecord.setLinkAttachment("https://www.url.com/example30");
		professionalRecord.setNameCompany("nameCompany");
		professionalRecord.setRole("d");

		miscellaneousRecord.setLinkAttachment("https://www.url.com/example30");
		miscellaneousRecord.setTitle("title");

		miscellaneousRecords.add(miscellaneousRecord);
		endorserRecords.add(endorserRecord);
		educationRecords.add(educationRecord);
		professionalRecords.add(professionalRecord);
		curriculum = this.curriculumService.create(endorserRecords, miscellaneousRecords, educationRecords, professionalRecords, personalRecord);
		saved = this.curriculumService.save(curriculum);
		curriculums = this.curriculumService.findAll();
		Assert.isTrue(curriculums.contains(saved));

	}

	@Test
	public void testDeleteCurriculum() {
		List<Curriculum> curriculums = (List<Curriculum>) this.curriculumService.findAll();
		Curriculum curriculum = curriculums.get(0);

		Assert.notNull(curriculum);
		this.curriculumService.delete(curriculum);
		curriculums = (List<Curriculum>) this.curriculumService.findAll();
		Assert.isTrue(!curriculums.contains(curriculum));

	}

}
