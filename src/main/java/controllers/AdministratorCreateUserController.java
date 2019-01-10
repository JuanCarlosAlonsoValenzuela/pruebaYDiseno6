
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdminService;
import services.ConfigurationService;
import services.RefereeService;
import domain.Admin;
import domain.Configuration;
import domain.Referee;

@Controller
@RequestMapping("/administrator")
public class AdministratorCreateUserController extends AbstractController {

	@Autowired
	private AdminService			adminService;
	@Autowired
	private RefereeService			refereeService;
	@Autowired
	private ConfigurationService	configurationService;


	public AdministratorCreateUserController() {
		super();
	}
	//------------------------------------------------------------
	//-------------------------ADMIN------------------------------

	//Create
	@RequestMapping(value = "/createAdmin", method = RequestMethod.GET)
	public ModelAndView createAdmin() {
		ModelAndView result;
		Admin admin;

		admin = this.adminService.createAdmin();
		result = this.createEditModelAndView(admin);

		return result;
	}

	//Save
	@RequestMapping(value = "/createAdmin", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Admin admin, BindingResult binding) {

		ModelAndView result;
		Md5PasswordEncoder encoder;
		encoder = new Md5PasswordEncoder();
		Configuration configuration = this.configurationService.getConfiguration();

		String prefix = configuration.getSpainTelephoneCode();

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(admin);
		} else {
			try {
				admin.getUserAccount().setPassword(encoder.encodePassword(admin.getUserAccount().getPassword(), null));
				if (admin.getPhoneNumber().matches("(\\+[0-9]{1,3})(\\([0-9]{1,3}\\))([0-9]{4,})$")) {
					this.adminService.saveCreate(admin);
				} else if (admin.getPhoneNumber().matches("([0-9]{4,})$")) {
					admin.setPhoneNumber(prefix + admin.getPhoneNumber());
					this.adminService.saveCreate(admin);
				} else {
					result = new ModelAndView("redirect:/anonymous/createAdmin.do");
				}
				result = new ModelAndView("redirect:/security/login.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(admin, "admin.commit.error");
			}
		}
		return result;
	}

	//CreateEditModelAndView
	protected ModelAndView createEditModelAndView(Admin admin) {
		ModelAndView result;

		result = this.createEditModelAndView(admin, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Admin admin, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("administrator/createAdmin");
		result.addObject("admin", admin);
		result.addObject("message", messageCode);

		return result;
	}

	//------------------------------------------------------------
	//-------------------------REFEREE------------------------------

	//Create
	@RequestMapping(value = "/createReferee", method = RequestMethod.GET)
	public ModelAndView createReferee() {
		ModelAndView result;
		Referee referee;

		referee = this.refereeService.create();
		result = this.createEditModelAndView(referee);

		return result;
	}

	//Save
	@RequestMapping(value = "/createReferee", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Referee referee, BindingResult binding) {

		ModelAndView result;
		Md5PasswordEncoder encoder;
		encoder = new Md5PasswordEncoder();
		Configuration configuration = this.configurationService.getConfiguration();
		String prefix = configuration.getSpainTelephoneCode();

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(referee);
		} else {
			try {
				referee.getUserAccount().setPassword(encoder.encodePassword(referee.getUserAccount().getPassword(), null));
				if (referee.getPhoneNumber().matches("(\\+[0-9]{1,3})(\\([0-9]{1,3}\\))([0-9]{4,})$")) {
					this.refereeService.saveCreate(referee);
				} else if (referee.getPhoneNumber().matches("([0-9]{4,})$")) {
					referee.setPhoneNumber(prefix + referee.getPhoneNumber());
					this.refereeService.saveCreate(referee);
				} else {
					result = new ModelAndView("redirect:/anonymous/createReferee.do");
				}
				result = new ModelAndView("redirect:/security/login.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(referee, "referee.commit.error");
			}
		}
		return result;
	}

	//CreateEditModelAndView
	protected ModelAndView createEditModelAndView(Referee referee) {
		ModelAndView result;

		result = this.createEditModelAndView(referee, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Referee referee, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("administrator/createReferee");
		result.addObject("referee", referee);
		result.addObject("message", messageCode);

		return result;
	}

}
