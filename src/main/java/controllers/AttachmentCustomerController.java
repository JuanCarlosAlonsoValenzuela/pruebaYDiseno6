
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
@RequestMapping("/attachment/customer")
public class AttachmentCustomerController extends AbstractController {

	@Autowired
	private ComplaintService	ComplaintService;

	@Autowired
	private ReportService		reportService;


	// Constructor --------------------------------------------------------------

	public AttachmentCustomerController() {
		super();
	}

	// ModelAndView -------------------------------------------------------------

	@RequestMapping(value = "/listPerFixUpTask", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int complaintId, @RequestParam int fixUpTaskId) {

		ModelAndView result;

		Collection<String> attachments;

		Complaint c = new Complaint();

		c = this.ComplaintService.findOne(complaintId);

		attachments = c.getAttachments();

		result = new ModelAndView("attachment/customer/list");

		result.addObject("attachments", attachments);
		result.addObject("requestURI", "attachment/customer/listPerFixUpTask.do");

		result.addObject("fixUpTaskId", fixUpTaskId);

		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int complaintId) {

		ModelAndView result;

		Collection<String> attachments;

		Complaint c = new Complaint();

		c = this.ComplaintService.findOne(complaintId);

		attachments = c.getAttachments();

		result = new ModelAndView("attachment/customer/list");

		result.addObject("attachments", attachments);
		result.addObject("requestURI", "attachment/customer/list.do");

		return result;

	}

	@RequestMapping(value = "/listPerReport", method = RequestMethod.GET)
	public ModelAndView listRep(@RequestParam int repId, @RequestParam int comId) {

		ModelAndView result;

		Collection<String> attachments;

		Report r = new Report();

		r = this.reportService.findOne(repId);

		attachments = r.getAttachments();

		result = new ModelAndView("attachment/customer/listPerReport");

		result.addObject("attachments", attachments);
		result.addObject("requestURI", "attachment/customer/listPerReport.do");
		result.addObject("comId", comId);

		return result;

	}

}
