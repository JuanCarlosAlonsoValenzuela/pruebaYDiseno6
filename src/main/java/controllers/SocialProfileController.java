
package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.CustomerService;
import domain.Actor;
import domain.Customer;
import domain.SocialProfile;

@Controller
@RequestMapping("/authenticated")
public class SocialProfileController extends AbstractController {

	@Autowired
	private LoginService	loginService;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private CustomerService	customerService;


	//-------------------------------------------------------------------
	//---------------------------LIST------------------------------------
	@RequestMapping(value = "/showProfile", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Double score = 1000.0;
		String username = LoginService.getPrincipal().getUsername();
		Actor logguedActor = this.actorService.getActorByUsername(username);

		Customer customer = this.customerService.getCustomerByUsername(username);
		if (customer != null) {
			score = customer.getScore();
		}

		List<SocialProfile> socialProfiles = logguedActor.getSocialProfiles();

		result = new ModelAndView("authenticated/showProfile");
		result.addObject("socialProfiles", socialProfiles);
		result.addObject("score", score);
		result.addObject("actor", logguedActor);
		result.addObject("requestURI", "authenticated/showProfile.do");

		return result;
	}
}
