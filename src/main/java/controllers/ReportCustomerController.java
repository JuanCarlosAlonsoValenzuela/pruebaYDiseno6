
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.CustomerService;
import domain.Complaint;
import domain.Report;

@Controller
@RequestMapping("/report/customer")
public class ReportCustomerController extends AbstractController {

	@Autowired
	private CustomerService		customerService;
	@Autowired
	private ComplaintService	complaintService;


	public ReportCustomerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int complaintId) {

		ModelAndView result;

		Collection<Report> reports;
		Complaint complaint = this.complaintService.findOne(complaintId);
		reports = this.customerService.listReports(complaint);

		result = new ModelAndView("report/customer/list");

		result.addObject("reports", reports);
		result.addObject("requestURI", "report/customer/list.do");

		result.addObject("comId", complaintId);

		return result;

	}
}
