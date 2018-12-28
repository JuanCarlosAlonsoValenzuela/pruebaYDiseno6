
package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdminService;
import services.ConfigurationService;
import services.EndorserService;
import domain.Endorser;

@Controller
@RequestMapping("/score/administrator")
public class AdministratorScoreController extends AbstractController {

	@Autowired
	private AdminService			adminService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private EndorserService			endorserService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView score() {
		ModelAndView result;
		List<Endorser> endorsers = new ArrayList<Endorser>();
		endorsers = this.endorserService.findAll();
		List<Double> scores = new ArrayList<Double>();

		for (Endorser e : endorsers) {
			this.configurationService.computeScore(e);
		}

		scores = this.configurationService.computeAllScoresDouble(endorsers);
		result = new ModelAndView("score/administrator/list");

		result.addObject("endorsers", endorsers);
		result.addObject("scores", scores);

		return result;
	}
}
