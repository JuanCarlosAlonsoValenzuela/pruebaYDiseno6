
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.ReportService;
import domain.Complaint;
import domain.Report;

@Controller
@RequestMapping("/attachment/referee")
public class AttachmentRefereeController extends AbstractController {

	@Autowired
	private ComplaintService	ComplaintService;

	@Autowired
	private ReportService		reportService;


	public AttachmentRefereeController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int complaintId) {

		ModelAndView result;

		Collection<String> attachments;

		Complaint c = new Complaint();

		c = this.ComplaintService.findOne(complaintId);

		attachments = c.getAttachments();

		result = new ModelAndView("attachment/referee/list");

		result.addObject("attachments", attachments);
		result.addObject("requestURI", "attachment/referee/list.do");

		return result;

	}

	@RequestMapping(value = "/listRep", method = RequestMethod.GET)
	public ModelAndView listRep(@RequestParam int reportId) {

		ModelAndView result;

		Collection<String> attachments;

		Report r = new Report();

		r = this.reportService.findOne(reportId);

		attachments = r.getAttachments();

		result = new ModelAndView("attachment/referee/listRep");

		result.addObject("attachments", attachments);
		result.addObject("requestURI", "attachment/referee/listRep.do");

		return result;

	}

}
