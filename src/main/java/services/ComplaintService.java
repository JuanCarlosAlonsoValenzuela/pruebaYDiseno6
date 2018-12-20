
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ComplaintRepository;
import utilities.RandomString;
import domain.Complaint;

@Service
@Transactional
public class ComplaintService {

	@Autowired
	private ComplaintRepository	complaintRepository;


	public Complaint create() {

		Complaint complaint = new Complaint();
		Date thisMoment = new Date();
		List<String> attachments = new ArrayList<String>();
		thisMoment.setTime(thisMoment.getTime() - 1);
		complaint.setTicker(this.generateTicker());
		complaint.setMoment(thisMoment);
		complaint.setAttachments(attachments);

		return complaint;
	}

	//Consultar lo del ticker
	public Complaint create(String description, List<String> attachments) {

		Complaint complaint = new Complaint();
		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() - 1);
		complaint.setTicker(this.generateTicker());
		complaint.setMoment(thisMoment);
		complaint.setDescription(description);
		complaint.setAttachments(attachments);

		return complaint;
	}

	//Método auxiliar para generar el ticker-------------------------------
	private String generateTicker() {
		String res = "";
		Date date = null;
		String date1;
		String date2 = LocalDate.now().toString();
		String gen = new RandomString(6).nextString();
		List<Complaint> lc = this.complaintRepository.findAll();
		SimpleDateFormat df_in = new SimpleDateFormat("yyMMdd");
		SimpleDateFormat df_output = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = df_output.parse(date2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		date1 = df_in.format(date);
		res = res + date1 + "-" + gen;
		for (Complaint c : lc)
			if (c.getTicker() == res)
				return this.generateTicker();
		return res;
	}
	//-----------------------------------------------------------------------

	public Complaint save(Complaint complaint) {
		return this.complaintRepository.save(complaint);
	}

	public Complaint findOne(int complaintId) {
		return this.complaintRepository.findOne(complaintId);
	}

	public void delete(Complaint complaint) {
		this.complaintRepository.delete(complaint);
	}

	public List<Complaint> findAll() {
		return this.complaintRepository.findAll();
	}
}
