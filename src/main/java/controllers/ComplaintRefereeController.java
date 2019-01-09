
package controllers;

import java.util.Collection;

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
import domain.Complaint;
import domain.Referee;

@Controller
@RequestMapping("/complaint/referee")
public class ComplaintRefereeController extends AbstractController {

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private RefereeService		refereeService;


	public ComplaintRefereeController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		Collection<Complaint> complaints;

		complaints = this.refereeService.selfAssignedComplaints();

		result = new ModelAndView("complaint/referee/list");

		result.addObject("complaints", complaints);
		result.addObject("requestURI", "complaint/referee/list.do");

		return result;

	}

	@RequestMapping(value = "/listUnassigned", method = RequestMethod.GET)
	public ModelAndView listUnassigned() {

		ModelAndView result;

		Collection<Complaint> complaints;

		complaints = this.refereeService.unassignedComplaints();

		result = new ModelAndView("complaint/referee/listUnassigned");

		result.addObject("complaints", complaints);
		result.addObject("requestURI", "complaint/referee/listUnassigned.do");

		return result;

	}

	@RequestMapping(value = "/assign", method = RequestMethod.GET)
	public ModelAndView assignComplaint(@RequestParam int comp) {
		ModelAndView result;
		Complaint complaint;

		complaint = this.complaintService.findOne(comp);
		Assert.notNull(complaint);
		result = this.createEditModelAndView(complaint);
		result.addObject("comp", comp);

		return result;
	}

	@RequestMapping(value = "/assign", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Complaint complaint, BindingResult binding, @RequestParam int comp) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(complaint);
			result.addObject("comp", comp);
		} else {
			try {
				this.refereeService.assingComplaint(complaint);
				//this.complaintService.save(complaint);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(complaint, "complaint.commit.error");
				result.addObject("comp", comp);
			}
		}
		return result;
	}
	protected ModelAndView createEditModelAndView(Complaint complaint) {
		ModelAndView result;

		result = this.createEditModelAndView(complaint, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Complaint complaint, String messageCode) {
		ModelAndView result;
		Referee referee;

		referee = this.refereeService.securityAndReferee();

		if (complaint.getReferee() == null) {
			complaint.setReferee(referee);
		}

		result = new ModelAndView("complaint/referee/assign");
		result.addObject("complaint", complaint);
		result.addObject("referee", referee);

		result.addObject("message", messageCode);

		return result;
	}

}
