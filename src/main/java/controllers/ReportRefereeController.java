
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.RefereeService;
import services.ReportService;
import domain.Complaint;
import domain.Report;

@Controller
@RequestMapping("/report/referee")
public class ReportRefereeController {

	@Autowired
	private RefereeService		refereeService;
	@Autowired
	private ComplaintService	complaintService;
	@Autowired
	private ReportService		reportService;


	public ReportRefereeController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int complaintId) {

		ModelAndView result;

		Collection<Report> reports;
		Complaint complaint = this.complaintService.findOne(complaintId);
		reports = complaint.getReports();

		result = new ModelAndView("report/referee/list");

		result.addObject("reports", reports);
		result.addObject("requestURI", "report/referee/list.do");
		result.addObject("complaintId", complaintId);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int complaintId) {
		ModelAndView result;
		Report report;

		report = this.reportService.create();
		result = this.createEditModelAndView(report);
		result.addObject("complaintId", complaintId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@Valid int complaintId, @RequestParam int reportId) {
		ModelAndView result;
		Report report;

		report = this.reportService.findOne(reportId);
		Assert.notNull(report);
		result = this.createEditModelAndView(report);
		result.addObject("complaintId", complaintId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Report report, BindingResult binding, @RequestParam String newAttachments, @Valid int complaintId) {
		ModelAndView result;
		List<String> attachments = new ArrayList<String>();
		Complaint c = this.complaintService.findOne(complaintId);
		Date thisMoment = new Date();
		thisMoment.setTime(thisMoment.getTime() - 1);
		report.setMoment(thisMoment);

		if (!newAttachments.trim().equals("")) {
			attachments.add(newAttachments);
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(report);
			result.addObject("complaintId", complaintId);
		} else {

			try {
				report.setAttachments(attachments);
				this.refereeService.createReport(c, report);
				result = new ModelAndView("redirect:list.do");
				result.addObject("complaintId", complaintId);
			} catch (Throwable oops) {
				result = this.createEditModelAndView(report, "complaint.commit.error");
				result.addObject("complaintId", complaintId);
			}
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Report report, BindingResult binding, @Valid int complaintId) {
		ModelAndView result;

		try {
			Complaint c = this.complaintService.findOne(complaintId);
			c.getReports().remove(report);
			this.complaintService.save(c);
			this.reportService.delete(report);
			result = new ModelAndView("redirect:list.do");
			result.addObject("complaintId", complaintId);
		} catch (Throwable oops) {
			result = this.createEditModelAndView(report, "complaint.commit.error");
			result.addObject("complaintId", complaintId);
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(Report report) {
		ModelAndView result;

		result = this.createEditModelAndView(report, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Report report, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("report/referee/create");
		result.addObject("report", report);
		result.addObject("message", messageCode);

		return result;
	}
}
