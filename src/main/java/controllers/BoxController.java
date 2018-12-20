
package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BoxService;
import domain.Box;

@Controller
@RequestMapping("/box/actor")
public class BoxController extends AbstractController {

	@Autowired
	BoxService	boxService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		List<Box> boxes = new ArrayList<>();

		boxes = this.boxService.getActorBoxes();
		//boxes = this.boxService.findAll();

		result = new ModelAndView("box/actor/list");

		result.addObject("boxes", boxes);
		result.addObject("requestURI", "box/actor/list.do");

		return result;

	}

}
