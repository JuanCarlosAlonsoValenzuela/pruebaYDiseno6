
package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CategoryRepository;
import domain.Category;
import domain.FixUpTask;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository	categoryRepository;
	@Autowired
	private FixUpTaskService	fixUpTaskService;


	public Category create() {

		Category category = new Category();
		category.setName("");
		category.setNameSpanish("");
		List<Category> subCategories = new ArrayList<>();
		category.setSubCategories(subCategories);

		return category;
	}

	public Category create(String name, List<Category> subCategories) {

		Category category = new Category();

		category.setName(name);
		category.setSubCategories(subCategories);

		return category;
	}

	public Category save(Category category) {
		Category saved = this.categoryRepository.save(category);

		Boolean b = true;
		for (Category c : this.categoryRepository.findAll()) {
			if (c.getSubCategories().contains(saved)) {
				this.categoryRepository.save(c);
				b = false;
			}
		}
		if (b) {
			Category fatherCategory = this.categoryRepository.getCategoryByName("CATEGORY");
			List<Category> fatherSubCategories = fatherCategory.getSubCategories();
			fatherSubCategories.add(saved);
			fatherCategory.setSubCategories(fatherSubCategories);
			this.categoryRepository.save(fatherCategory);
		}

		return saved;
	}

	public Category findOne(int categoryId) {
		return this.categoryRepository.findOne(categoryId);
	}

	public void delete(Category category) {

		//VALORES QUE SE VAN A USAR
		//Subcategorías
		List<Category> subCategories = category.getSubCategories();

		//TRATAMIENTO DE LAS POSIBLES FIX UP TASK ASOCIADAS
		List<FixUpTask> fixUpTasks = this.categoryRepository.getFixUpTasksByCategory(category.getName());

		for (FixUpTask fixUpTask : fixUpTasks) {
			Category categorySystem = this.categoryRepository.getCategoryByName("CATEGORY");
			fixUpTask.setCategory(categorySystem);
			this.fixUpTaskService.save(fixUpTask);
		}

		//TRATAMIENTO DE LAS POSIBLES CATEGORÍAS HIJAS
		if (subCategories.size() > 0) {						//Hay hijos, añadir todos los hijos a CATEGORY

			Category categorySystem = this.categoryRepository.getCategoryByName("CATEGORY");

			List<Category> systemSubCategories = categorySystem.getSubCategories();
			systemSubCategories.addAll(subCategories);
			categorySystem.setSubCategories(systemSubCategories);
			this.categoryRepository.save(categorySystem);

			List<Category> emptyCategories = new ArrayList<>();
			category.setSubCategories(emptyCategories);

		}
		category = this.categoryRepository.save(category);

		//TRATAMIENTO DE LA CATEGORÍA PADRE
		for (Category father : this.categoryRepository.findAll()) {
			if (father.getSubCategories().contains(category)) {
				List<Category> fatherSubCategories = father.getSubCategories();
				fatherSubCategories.remove(category);
				father.setSubCategories(fatherSubCategories);
				this.categoryRepository.save(father);
			}

		}
		//Borrado final
		this.categoryRepository.delete(category);
	}

	public List<Category> findAll() {
		return this.categoryRepository.findAll();
	}

	public Category getCategoryByName(String c) {
		return this.categoryRepository.getCategoryByName(c);
	}

	public Category findFatherCategory(Category category) {
		Category result = new Category();
		for (Category c : this.categoryRepository.findAll()) {
			if (c.getSubCategories().contains(category)) {
				result = c;
				break;
			}
		}
		return result;
	}

	public Map<Category, Category> findFatherCategoryPerCategory() {
		Map<Category, Category> result = new HashMap<>();
		for (Category category : this.categoryRepository.findAll()) {
			result.put(category, this.findFatherCategory(category));
		}
		return result;
	}

	public List<FixUpTask> getFixUpTasksByCategory(String name) {
		return this.categoryRepository.getFixUpTasksByCategory(name);
	}

}
