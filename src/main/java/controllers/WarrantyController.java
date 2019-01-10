
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

import services.WarrantyService;
import domain.Warranty;

@Controller
@RequestMapping("/warranty/administrator")
public class WarrantyController extends AbstractController {

	@Autowired
	private WarrantyService	warrantyService;


	//-------------------------------------------------------------------
	//---------------------------LIST------------------------------------

	//Listar Warranties
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

	//Listar Terms
	@RequestMapping(value = "/terms/list", method = RequestMethod.GET)
	public ModelAndView warrantyTermsList(@RequestParam int warrantyId) {
		ModelAndView result;

		Warranty warranty = this.warrantyService.findOne(warrantyId);

		result = new ModelAndView("warranty/administrator/terms/list");
		result.addObject("warrantyId", warrantyId);
		result.addObject("warranty", warranty);
		result.addObject("terms", this.warrantyService.findOne(warrantyId).getTerms());
		result.addObject("requestURI", "warranty/administrator/terms/list.do");

		return result;
	}

	//Listar Laws
	@RequestMapping(value = "/laws/list", method = RequestMethod.GET)
	public ModelAndView warrantyLawsList(@RequestParam int warrantyId) {
		ModelAndView result;

		Warranty warranty = this.warrantyService.findOne(warrantyId);

		result = new ModelAndView("warranty/administrator/laws/list");
		result.addObject("warrantyId", warrantyId);
		result.addObject("warranty", warranty);
		result.addObject("laws", this.warrantyService.findOne(warrantyId).getLaws());
		result.addObject("requestURI", "warranty/administrator/laws/list.do");

		return result;
	}

	//-------------------------------------------------------------------
	//---------------------------CREATE----------------------------------

	//CREATE WARRANTY
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createWarranty() {
		ModelAndView result;
		Warranty warranty;

		warranty = this.warrantyService.create();
		result = this.createEditModelAndView(warranty);

		return result;
	}

	//SAVE WARRANTY
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Warranty warranty, BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(warranty);
		} else {
			try {
				this.warrantyService.save(warranty);
				result = new ModelAndView("redirect:/warranty/administrator/list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(warranty, "warranty.commit.error");
			}
		}
		return result;
	}

	//MODEL AND VIEW WARRANTY
	protected ModelAndView createEditModelAndView(Warranty warranty) {
		ModelAndView result;

		result = this.createEditModelAndView(warranty, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Warranty warranty, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("warranty/administrator/create");
		result.addObject("warranty", warranty);
		result.addObject("message", messageCode);

		return result;
	}

	//CREATE TERM
	@RequestMapping(value = "/terms/create", method = RequestMethod.GET)
	public ModelAndView newTerm(@RequestParam int warrantyId) {
		ModelAndView result;

		result = new ModelAndView("warranty/administrator/terms/create");
		result.addObject("warrantyId", warrantyId);

		return result;
	}

	@RequestMapping(value = "/terms/create", method = RequestMethod.POST, params = "create")
	public ModelAndView saveTerm(@Valid int warrantyId, @Valid String term) {
		ModelAndView result;

		try {
			Warranty warranty = this.warrantyService.findOne(warrantyId);
			List<String> terms = warranty.getTerms();
			if (!term.trim().isEmpty()) {
				terms.add(term);
			}
			warranty.setTerms(terms);
			this.warrantyService.save(warranty);
			result = new ModelAndView("warranty/administrator/terms/list");
			result.addObject("warrantyId", warrantyId);
			result.addObject("warranty", warranty);
			result.addObject("terms", this.warrantyService.findOne(warrantyId).getTerms());
			result.addObject("requestURI", "warranty/administrator/terms/list.do");

		} catch (Throwable oops) {
			result = new ModelAndView("warranty/administrator/terms/list");
			result.addObject("term", term);
			result.addObject("warrantyId", warrantyId);

		}
		return result;
	}

	//CREATE LAW
	@RequestMapping(value = "/laws/create", method = RequestMethod.GET)
	public ModelAndView newLaw(@RequestParam int warrantyId) {
		ModelAndView result;

		result = new ModelAndView("warranty/administrator/laws/create");
		result.addObject("warrantyId", warrantyId);

		return result;
	}

	@RequestMapping(value = "/laws/create", method = RequestMethod.POST, params = "create")
	public ModelAndView saveLaw(@Valid int warrantyId, @Valid String law) {
		ModelAndView result;

		try {
			Warranty warranty = this.warrantyService.findOne(warrantyId);
			List<String> laws = warranty.getLaws();
			if (!law.trim().isEmpty()) {
				laws.add(law);
			}
			warranty.setLaws(laws);
			this.warrantyService.save(warranty);
			result = new ModelAndView("warranty/administrator/laws/list");
			result.addObject("warranty", warranty);
			result.addObject("warrantyId", warrantyId);
			result.addObject("laws", this.warrantyService.findOne(warrantyId).getLaws());
			result.addObject("requestURI", "warranty/administrator/laws/list.do");

		} catch (Throwable oops) {
			result = new ModelAndView("warranty/administrator/laws/list");
			result.addObject("laws", law);
			result.addObject("warrantyId", warrantyId);

		}
		return result;
	}

	//-------------------------------------------------------------------
	//---------------------------EDIT------------------------------------

	//Edit Warranty
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int warrantyId) {
		ModelAndView result;
		Warranty warranty;

		warranty = this.warrantyService.findOne(warrantyId);
		Assert.notNull(warranty);
		result = this.createEditModelAndView1(warranty);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveWarranty(@Valid Warranty warranty, BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(warranty);
		} else {
			try {
				this.warrantyService.save(warranty);
				result = new ModelAndView("redirect:/warranty/administrator/list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(warranty, "warranty.commit.error");
			}
		}
		return result;
	}

	//MODEL AND VIEW WARRANTY
	protected ModelAndView createEditModelAndView1(Warranty warranty) {
		ModelAndView result;

		result = this.createEditModelAndView1(warranty, null);

		return result;
	}

	protected ModelAndView createEditModelAndView1(Warranty warranty, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("warranty/administrator/edit");
		result.addObject("warranty", warranty);
		result.addObject("warrantyId", warranty.getId());
		result.addObject("message", messageCode);

		return result;
	}

	//-------------------------------------------------------------------
	//---------------------------DELETE----------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Warranty warranty, BindingResult binding) {

		ModelAndView result;

		try {
			this.warrantyService.delete(warranty);
			result = new ModelAndView("redirect:/warranty/administrator/list.do");
		} catch (Throwable oops) {
			result = this.createEditModelAndView1(warranty, "warranty.commit.error");
		}
		return result;
	}

}
