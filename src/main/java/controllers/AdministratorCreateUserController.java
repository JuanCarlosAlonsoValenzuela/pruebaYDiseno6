
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
import services.RefereeService;
import domain.Admin;
import domain.Referee;

@Controller
@RequestMapping("/administrator")
public class AdministratorCreateUserController extends AbstractController {

	@Autowired
	private AdminService	adminService;
	@Autowired
	private RefereeService	refereeService;


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

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(admin);
		} else {
			try {
				admin.getUserAccount().setPassword(encoder.encodePassword(admin.getUserAccount().getPassword(), null));
				this.adminService.saveCreate(admin);
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

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(referee);
		} else {
			try {
				referee.getUserAccount().setPassword(encoder.encodePassword(referee.getUserAccount().getPassword(), null));
				this.refereeService.saveCreate(referee);
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
