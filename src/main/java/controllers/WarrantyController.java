
package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.WarrantyService;
import domain.Warranty;

@Controller
@RequestMapping("/warranty/administrator")
public class WarrantyController extends AbstractController {

	@Autowired
	WarrantyService	warrantyService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		List<Warranty> warranties;

		warranties = this.warrantyService.findAll();

		result = new ModelAndView("warranty/administrator/list");
		result.addObject("warranties", warranties);
		result.addObject("requestURI", "warranty/administrator/list.do");

		return result;
	}

}
