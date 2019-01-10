
package controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.FixUpTaskService;
import domain.Category;
import domain.FixUpTask;

@Controller
@RequestMapping("/category/administrator")
public class CategoryController extends AbstractController {

	public CategoryController() {
		super();
	}


	@Autowired
	private CategoryService		categoryService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;


	//-------------------------------------------------------------------
	//---------------------------LIST------------------------------------

	//Listar Categories
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		List<Category> categories;

		categories = this.categoryService.findAll();
		Map<Category, Category> mapCategories = this.categoryService.findFatherCategoryPerCategory();

		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		result = new ModelAndView("category/administrator/list");
		result.addObject("categories", categories);
		result.addObject("mapCategories", mapCategories);
		result.addObject("locale", locale);
		result.addObject("requestURI", "category/administrator/list.do");

		return result;
	}

	//Listar Subcategorías
	@RequestMapping(value = "/subCategories/list", method = RequestMethod.GET)
	public ModelAndView subCategoriesList(@RequestParam int categoryId) {

		ModelAndView result;

		Category category = this.categoryService.findOne(categoryId);
		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		result = new ModelAndView("category/administrator/subCategories/list");
		result.addObject("locale", locale);
		result.addObject("categoryId", categoryId);
		result.addObject("subCategories", category.getSubCategories());
		result.addObject("requestURI", "category/administrator/subCategories/list.do");

		return result;
	}

	//-------------------------------------------------------------------
	//---------------------------CREATE----------------------------------

	//CREATE CATEGORY
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createCategory() {
		ModelAndView result;
		Category category;

		category = this.categoryService.create();
		result = this.createEditModelAndView(category);

		return result;
	}

	//SAVE CATEGORY
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Category category, BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(category);
		} else {
			try {
				this.categoryService.save(category);

				result = new ModelAndView("redirect:/category/administrator/list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(category, "category.commit.error");
			}
		}
		return result;
	}
	//-------------------------------------------------------------------
	//---------------------------EDIT------------------------------------

	//Edit Category
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editCategory(@RequestParam int categoryId) {
		ModelAndView result;
		Category category;

		category = this.categoryService.findOne(categoryId);
		Assert.notNull(category);
		result = this.createEditModelAndView(category);

		return result;
	}

	//MODEL AND VIEW CATEGORY
	protected ModelAndView createEditModelAndView(Category category) {
		ModelAndView result;

		result = this.createEditModelAndView(category, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Category category, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("category/administrator/edit");
		result.addObject("category", category);
		result.addObject("categoryId", category.getId());
		result.addObject("message", messageCode);

		return result;
	}

	//Edit SubCategory
	@RequestMapping(value = "/editSubCategory", method = RequestMethod.GET)
	public ModelAndView editSubCategory(@RequestParam int categoryId) {
		ModelAndView result;
		Category category;

		category = this.categoryService.findOne(categoryId);

		List<Category> possibleCategories = this.categoryService.findAll();
		List<Category> subCategories = category.getSubCategories();

		possibleCategories.remove(category);
		possibleCategories.removeAll(subCategories);
		possibleCategories.remove(this.categoryService.getCategoryByName("CATEGORY"));

		Assert.notNull(category);
		String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase();

		result = new ModelAndView("category/administrator/editSubCategory");
		result.addObject("categoryId", categoryId);
		result.addObject("locale", locale);
		result.addObject("category", category);
		result.addObject("possibleCategories", possibleCategories);

		return result;
	}

	//SAVE SUB CATEGORY
	@RequestMapping(value = "/editSubCategory", method = RequestMethod.POST, params = "create")
	public ModelAndView saveSubCategory(@Valid String possibleCategoryName, @Valid int categoryId) {

		ModelAndView result;
		//BindingResult binding

		try {

			Category possibleCategory = this.categoryService.getCategoryByName(possibleCategoryName);

			List<FixUpTask> fixUpTasks2 = this.categoryService.getFixUpTasksByCategory(possibleCategory.getName());

			//MOVER A CATEGORY Temporalmente
			for (FixUpTask fixUpTask : fixUpTasks2) {
				fixUpTask.setCategory(null);
				this.fixUpTaskService.save(fixUpTask);
			}

			Category oldFatherCategory = this.categoryService.findFatherCategory(possibleCategory);
			List<Category> oldFatherSubCategories = oldFatherCategory.getSubCategories();

			List<FixUpTask> fixUpTasks = this.categoryService.getFixUpTasksByCategory(oldFatherCategory.getName());

			//MOVER A CATEGORY Temporalmente
			for (FixUpTask fixUpTask : fixUpTasks) {
				fixUpTask.setCategory(null);
				this.fixUpTaskService.save(fixUpTask);
			}

			Category newFatherCategory = this.categoryService.findOne(categoryId);
			List<Category> newFatherSubCategories = newFatherCategory.getSubCategories();

			List<FixUpTask> fixUpTasks1 = this.categoryService.getFixUpTasksByCategory(newFatherCategory.getName());
			//MOVER A CATEGORY Temporalmente
			for (FixUpTask fixUpTask : fixUpTasks1) {
				fixUpTask.setCategory(null);
				this.fixUpTaskService.save(fixUpTask);
			}

			oldFatherSubCategories.remove(possibleCategory);
			oldFatherCategory.setSubCategories(oldFatherSubCategories);
			Category oldSaved = this.categoryService.save(oldFatherCategory);

			newFatherSubCategories.add(possibleCategory);
			newFatherCategory.setSubCategories(newFatherSubCategories);
			Category newSaved = this.categoryService.save(newFatherCategory);

			Category possibleSaved = this.categoryService.save(possibleCategory);

			//for possible
			for (FixUpTask fixUpTask : fixUpTasks2) {

				fixUpTask.setCategory(possibleSaved);
				this.fixUpTaskService.save(fixUpTask);
			}

			//forOld

			for (FixUpTask fixUpTask : fixUpTasks) {

				fixUpTask.setCategory(oldSaved);
				this.fixUpTaskService.save(fixUpTask);
			}

			//forNew

			for (FixUpTask fixUpTask : fixUpTasks1) {

				fixUpTask.setCategory(newSaved);
				this.fixUpTaskService.save(fixUpTask);
			}

			result = new ModelAndView("redirect:/category/administrator/list.do");  //"category/administrator/list"
			result.addObject("requestURI", "category/administrator/list.do");

		} catch (Throwable oops) {
			result = new ModelAndView("category/administrator/list");

		}

		return result;
	}

	//-------------------------------------------------------------------
	//---------------------------DELETE----------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Category category, BindingResult binding) {

		ModelAndView result;

		try {
			this.categoryService.delete(category);
			result = new ModelAndView("redirect:/category/administrator/list.do");
		} catch (Throwable oops) {
			result = this.createEditModelAndView(category, "category.commit.error");
		}
		return result;
	}

}
