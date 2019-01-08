
package controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.CustomerService;
import services.HandyWorkerService;
import services.SocialProfileService;
import domain.Actor;
import domain.Customer;
import domain.HandyWorker;
import domain.SocialProfile;

@Controller
@RequestMapping("/authenticated")
public class SocialProfileController extends AbstractController {

	@Autowired
	private LoginService			loginService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private HandyWorkerService		handyWorkerService;
	@Autowired
	private SocialProfileService	socialProfileService;


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

		Boolean isHandyWorker = false;
		HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(username);
		if (handyWorker != null) {
			isHandyWorker = true;
		}

		List<SocialProfile> socialProfiles = logguedActor.getSocialProfiles();

		result = new ModelAndView("authenticated/showProfile");
		result.addObject("socialProfiles", socialProfiles);
		result.addObject("score", score);
		result.addObject("isHandyWorker", isHandyWorker);
		result.addObject("actor", logguedActor);
		result.addObject("requestURI", "authenticated/showProfile.do");

		return result;
	}

	//---------------------------------------------------------------------
	//---------------------------CREATE------------------------------------
	@RequestMapping(value = "/socialProfile/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SocialProfile socialProfile;

		socialProfile = this.socialProfileService.create();
		result = this.createEditModelAndView(socialProfile);

		return result;
	}

	//---------------------------------------------------------------------
	//---------------------------EDIT--------------------------------------
	@RequestMapping(value = "/socialProfile/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int socialProfileId) {

		ModelAndView result;
		SocialProfile socialProfile;

		socialProfile = this.socialProfileService.findOne(socialProfileId);
		Assert.notNull(socialProfile);
		result = this.createEditModelAndView(socialProfile);

		return result;
	}

	//---------------------------------------------------------------------
	//---------------------------SAVE--------------------------------------
	@RequestMapping(value = "/socialProfile/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid SocialProfile socialProfile, BindingResult binding) {
		ModelAndView result = null;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(socialProfile);
		} else {
			try {
				Actor logguedActor = this.actorService.getActorByUsername(LoginService.getPrincipal().getUsername());
				SocialProfile saved = this.socialProfileService.save(socialProfile);
				List<SocialProfile> socialProfiles = logguedActor.getSocialProfiles();

				if (socialProfiles.contains(socialProfile)) {
					socialProfiles.remove(socialProfile);
				}

				socialProfiles.add(saved);
				logguedActor.setSocialProfiles(socialProfiles);
				this.actorService.save(logguedActor);

				HandyWorker logguedHandyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());

				//				Boolean isHandyWorker = false;
				//				HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUsername(username);
				//				if (handyWorker != null) {
				//					isHandyWorker = true;
				//				}

				if (logguedHandyWorker != null) {
					result = new ModelAndView("redirect:/handyWorker/handyWorker/showProfile.do");
					result.addObject("isHandyWorker", true);
				} else {
					result = new ModelAndView("redirect:/authenticated/showProfile.do");
					result.addObject("isHandyWorker", false);
				}

			} catch (Throwable oops) {
				result = this.createEditModelAndView(socialProfile, "socialProfile.commit.error");
			}
		}
		return result;
	}
	//---------------------------------------------------------------------
	//---------------------------DELETE------------------------------------
	@RequestMapping(value = "/socialProfile/create", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(SocialProfile socialProfile, BindingResult binding) {

		ModelAndView result = null;

		try {
			Actor logguedActor = this.actorService.getActorByUsername(LoginService.getPrincipal().getUsername());
			List<SocialProfile> socialProfiles = logguedActor.getSocialProfiles();
			socialProfiles.remove(socialProfile);
			logguedActor.setSocialProfiles(socialProfiles);
			this.actorService.save(logguedActor);
			this.socialProfileService.delete(socialProfile);

			HandyWorker logguedHandyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());

			if (logguedHandyWorker != null) {
				result = new ModelAndView("redirect:/handyWorker/handyWorker/showProfile.do");
				result.addObject("isHandyWorker", true);
			} else {
				result = new ModelAndView("redirect:/authenticated/showProfile.do");
				result.addObject("isHandyWorker", false);
			}

		} catch (Throwable oops) {
			result = this.createEditModelAndView(socialProfile, "socialProfile.commit.error");
		}
		return result;
	}

	//---------------------------------------------------------------------
	//---------------------------CREATEEDITMODELANDVIEW--------------------

	protected ModelAndView createEditModelAndView(SocialProfile socialProfile) {

		ModelAndView result;

		result = this.createEditModelAndView(socialProfile, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(SocialProfile socialProfile, String messageCode) {

		ModelAndView result;

		HandyWorker logguedHandyWorker = this.handyWorkerService.getHandyWorkerByUsername(LoginService.getPrincipal().getUsername());

		result = new ModelAndView("authenticated/socialProfile/create");
		result.addObject("socialProfile", socialProfile);
		result.addObject("message", messageCode);

		if (logguedHandyWorker != null) {
			//result = new ModelAndView("redirect:/handyWorker/handyWorker/showProfile.do");
			result.addObject("isHandyWorker", true);
		} else {
			//result = new ModelAndView("redirect:/authenticated/showProfile.do");
			result.addObject("isHandyWorker", false);
		}

		return result;
	}

}
