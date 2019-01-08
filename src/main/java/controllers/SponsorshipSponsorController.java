
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.SponsorService;
import domain.Sponsor;
import domain.Sponsorship;

@Controller
@RequestMapping("/sponsorship/sponsor")
public class SponsorshipSponsorController extends AbstractController {

	@Autowired
	private SponsorService	sponsorService;


	public SponsorshipSponsorController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		Collection<Sponsorship> sponsoships;

		UserAccount userAccount = LoginService.getPrincipal();
		Sponsor logguedSponsor = this.sponsorService.getSponsorByUsername(userAccount.getUsername());

		sponsoships = logguedSponsor.getSponsorships();

		result = new ModelAndView("sponsorship/sponsor");

		result.addObject("sponsorships", sponsoships);
		result.addObject("requestURI", "sponsoshipst/sponsor/list.do");

		return result;

	}

}
