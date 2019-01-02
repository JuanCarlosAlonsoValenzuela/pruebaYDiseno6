
package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.HandyWorkerService;
import services.ReportService;
import domain.Complaint;
import domain.Report;

@Controller
@RequestMapping("/report/handyWorker")
public class ReportHandyWorkerController extends AbstractController {

	//Service
	@Autowired
	private HandyWorkerService	handyWorkerService;
	@Autowired
	private ReportService		reportService;
	@Autowired
	private ComplaintService	complaintService;


	//Constructor
	public ReportHandyWorkerController() {
		super();
	}

	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView reportList(@RequestParam int complaintId) {
		ModelAndView result;

		Complaint complaint = this.complaintService.findOne(complaintId);
		List<Report> reports = complaint.getReports();

		result = new ModelAndView("handy-worker/complaintsReport");

		result.addObject("reports", reports);
		result.addObject("requestURI", "/report/handyWorker/list.do");

		return result;
	}

	//AttchmentsList
	@RequestMapping(value = "/attachmentList", method = RequestMethod.GET)
	public ModelAndView complaintAttachmentList(@RequestParam int reportId) {

		ModelAndView result;

		Report report = this.reportService.findOne(reportId);
		List<String> attachments = report.getAttachments();

		result = new ModelAndView("handy-worker/reportsAttachments");

		result.addObject("attachments", attachments);
		result.addObject("requestURI", "report/handyWorker/attachmentList.do");

		return result;

	}
}
