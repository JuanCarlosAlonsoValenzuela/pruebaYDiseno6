
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.AdminService;
import services.CustomerService;
import services.RefereeService;
import services.SponsorService;
import domain.Admin;
import domain.Customer;
import domain.Referee;
import domain.Sponsor;

@Controller
@RequestMapping("/personalData")
public class EditPersonalDataController extends AbstractController {

	@Autowired
	private CustomerService	customerService;
	@Autowired
	private SponsorService	sponsorService;
	@Autowired
	private RefereeService	refereeService;
	@Autowired
	private AdminService	adminService;


	//Constructor
	public EditPersonalDataController() {
		super();
	}

	//-------------------------------------------------------------------------------------------	
	//-------------------------- CUSTOMER -------------------------------------------------------		
	@RequestMapping(value = "/customer/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		Customer customer;

		UserAccount userAccount = LoginService.getPrincipal();
		String username = userAccount.getUsername();

		customer = this.customerService.getCustomerByUsername(username);
		Assert.notNull(customer);
		result = this.createEditModelAndView(customer);

		return result;
	}

	//Save Customer
	@RequestMapping(value = "/customer/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Customer customer, BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(customer);
		} else {
			try {
				this.customerService.updateCustomer(customer);	//Cambiar por update
				result = new ModelAndView("redirect:edit.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(customer, "customer.commit.error");
			}
		}
		return result;
	}

	//createEditModelAndView
	protected ModelAndView createEditModelAndView(Customer customer) {
		ModelAndView result;

		result = this.createEditModelAndView(customer, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(Customer customer, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("personalData/customer/edit");
		result.addObject("customer", customer);
		result.addObject("message", messageCode);

		return result;
	}

	//-------------------------------------------------------------------------------------------	
	//-------------------------- SPONSOR -------------------------------------------------------	
	@RequestMapping(value = "/sponsor/edit", method = RequestMethod.GET)
	public ModelAndView editSponsor() {
		ModelAndView result;

		Sponsor sponsor;

		UserAccount userAccount = LoginService.getPrincipal();
		String username = userAccount.getUsername();

		sponsor = this.sponsorService.getSponsorByUsername(username);
		Assert.notNull(sponsor);
		result = this.createEditModelAndView(sponsor);

		return result;
	}

	//Save Sponsor
	@RequestMapping(value = "/sponsor/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveSponsor(@Valid Sponsor sponsor, BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(sponsor);
		} else {
			try {
				this.sponsorService.updateSponsor(sponsor);	//Cambiar por update
				result = new ModelAndView("redirect:edit.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(sponsor, "sponsor.commit.error");
			}
		}
		return result;
	}

	//createEditModelAndView
	protected ModelAndView createEditModelAndView(Sponsor sponsor) {
		ModelAndView result;

		result = this.createEditModelAndView(sponsor, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(Sponsor sponsor, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("personalData/sponsor/edit");
		result.addObject("sponsor", sponsor);
		result.addObject("message", messageCode);

		return result;
	}

	//-------------------------------------------------------------------------------------------	
	//-------------------------- REFEREE --------------------------------------------------------	
	@RequestMapping(value = "/referee/editReferee", method = RequestMethod.GET)
	public ModelAndView editReferee() {
		ModelAndView result;

		Referee referee;

		UserAccount userAccount = LoginService.getPrincipal();
		String username = userAccount.getUsername();

		referee = this.refereeService.getRefereeByUsername(username);
		Assert.notNull(referee);
		result = this.createEditModelAndView(referee);

		return result;
	}

	//Save Referee
	@RequestMapping(value = "/referee/editReferee", method = RequestMethod.POST, params = "save")
	public ModelAndView saveReferee(@Valid Referee referee, BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(referee);
		} else {
			try {
				this.refereeService.updateReferee(referee);	//Cambiar por update
				result = new ModelAndView("redirect:edit.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(referee, "referee.commit.error");
			}
		}
		return result;
	}

	//createEditModelAndView
	protected ModelAndView createEditModelAndView(Referee referee) {
		ModelAndView result;

		result = this.createEditModelAndView(referee, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Referee referee, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("personalData/referee/editReferee");
		result.addObject("referee", referee);
		result.addObject("message", messageCode);

		return result;
	}

	//-------------------------------------------------------------------------------------------	
	//-------------------------- ADMIN --------------------------------------------------------	
	@RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
	public ModelAndView editAdministrator() {
		ModelAndView result;

		Admin admin;

		UserAccount userAccount = LoginService.getPrincipal();
		String username = userAccount.getUsername();

		admin = this.adminService.getAdminByUsername(username);
		Assert.notNull(admin);
		result = this.createEditModelAndView(admin);

		return result;
	}

	//Save Referee
	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdministrator(@Valid Admin admin, BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(admin);
		} else {
			try {
				this.adminService.updateAdmin(admin);	//Cambiar por update
				result = new ModelAndView("redirect:edit.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(admin, "admin.commit.error");
			}
		}
		return result;
	}

	//createEditModelAndView
	protected ModelAndView createEditModelAndView(Admin admin) {
		ModelAndView result;

		result = this.createEditModelAndView(admin, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(Admin admin, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("personalData/administrator/edit");
		result.addObject("admin", admin);
		result.addObject("message", messageCode);

		return result;
	}
}
