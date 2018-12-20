
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ReportRepository;
import domain.Note;
import domain.Report;

@Service
@Transactional
public class ReportService {

	@Autowired
	private ReportRepository	reportRepository;


	public Report create() {

		List<String> attachments = new ArrayList<String>();
		List<Note> notes = new ArrayList<Note>();

		Report report = new Report();
		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() - 1);
		report.setMoment(thisMoment);
		report.setDescription("");
		report.setAttachments(attachments);
		report.setFinalMode(false);
		report.setNotes(notes);

		return report;
	}

	public Report create(String description, List<String> attachments, List<Note> notes) {

		Report report = new Report();
		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() - 1);
		report.setMoment(thisMoment);
		report.setDescription(description);
		report.setAttachments(attachments);
		report.setFinalMode(false);
		report.setNotes(notes);

		return report;
	}

	public Report save(Report report) {
		return this.reportRepository.save(report);
	}

	public void flush() {
		this.reportRepository.flush();
	}

	public Report findOne(int reportId) {
		return this.reportRepository.findOne(reportId);
	}

	public void delete(Report report) {
		this.reportRepository.delete(report);
	}

	public List<Report> findAll() {
		return this.reportRepository.findAll();
	}
}
