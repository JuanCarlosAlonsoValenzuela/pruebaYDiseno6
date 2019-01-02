
package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.HandyWorkerService;
import services.TutorialService;
import domain.HandyWorker;
import domain.Sponsorship;

@Controller
@RequestMapping("/handyWorker")
public class HandyWorkerController extends AbstractController {

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private TutorialService		tutorialService;


	public HandyWorkerController() {
		super();
	}

	@RequestMapping(value = "/handyWorker/showProfile", method = RequestMethod.GET)
	public ModelAndView handyProfile() {
		ModelAndView result;
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());
		Assert.notNull(handyWorker);
		Boolean canEdit = false;
		Boolean hasCurriculum;

		try {
			Assert.notNull(handyWorker.getCurriculum());
			hasCurriculum = true;
		} catch (Exception e) {
			hasCurriculum = false;
		}

		List<Sponsorship> sponsorships = this.tutorialService.getRandomSponsorShips(handyWorker.getTutorials());

		UserAccount userAccount = LoginService.getPrincipal();
		String username = userAccount.getUsername();
		canEdit = username.equals(handyWorker.getUserAccount().getUsername());

		result = new ModelAndView("handyWorker/actor/showProfile");

		result.addObject("hasCurriculum", hasCurriculum);
		result.addObject("sponsorships", sponsorships);
		result.addObject("canEdit", canEdit);
		result.addObject("handyWorker", handyWorker);
		result.addObject("requestURI", "handyWorker/actor/showProfile.do");

		return result;
	}
	@RequestMapping(value = "/anonymous/showProfile", method = RequestMethod.GET)
	public ModelAndView handyProfileAnonymous(@RequestParam int handyId) {
		ModelAndView result;
		HandyWorker handyWorker = this.handyWorkerService.findOne(handyId);
		Assert.notNull(handyWorker);
		Boolean canEdit = false;
		Boolean hasCurriculum;

		try {
			Assert.notNull(handyWorker.getCurriculum());
			hasCurriculum = true;
		} catch (Exception e) {
			hasCurriculum = false;
		}

		List<Sponsorship> sponsorships = this.tutorialService.getRandomSponsorShips(handyWorker.getTutorials());

		result = new ModelAndView("handyWorker/anonymous/showProfile");

		result.addObject("hasCurriculum", hasCurriculum);
		result.addObject("sponsorships", sponsorships);
		result.addObject("canEdit", canEdit);
		result.addObject("handyWorker", handyWorker);
		result.addObject("requestURI", "handyWorker/anonymous/showProfile.do");

		return result;
	}
}
