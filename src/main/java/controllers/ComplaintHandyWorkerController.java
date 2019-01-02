
package controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import domain.Complaint;

@Controller
@RequestMapping("/complaint/handyWorker")
public class ComplaintHandyWorkerController extends AbstractController {

	//Service
	@Autowired
	private HandyWorkerService	handyWorkerService;
	@Autowired
	private ComplaintService	complaitnService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;


	//Constructor
	public ComplaintHandyWorkerController() {
		super();
	}

	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView complaintList(@RequestParam int fixUpTaskId) {

		ModelAndView result;

		Collection<Complaint> complaints;

		complaints = this.fixUpTaskService.findOne(fixUpTaskId).getComplaints();

		result = new ModelAndView("handy-worker/complaints");

		result.addObject("complaints", complaints);
		result.addObject("requestURI", "complaint/handyWorker/list.do");

		return result;

	}

	//AttchmentsList
	@RequestMapping(value = "/attachmentList", method = RequestMethod.GET)
	public ModelAndView complaintAttachmentList(@RequestParam int complaintId) {

		ModelAndView result;

		Complaint complaint = this.complaitnService.findOne(complaintId);
		List<String> attachments = complaint.getAttachments();

		result = new ModelAndView("handy-worker/complaintsAttachment");

		result.addObject("attachments", attachments);
		result.addObject("requestURI", "complaint/handyWorker/attachmentList.do");

		return result;

	}

}
