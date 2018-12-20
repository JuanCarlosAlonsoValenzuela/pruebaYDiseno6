
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CategoryRepository;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository	categoryRepository;


	public Category create() {

		Category category = new Category();
		List<Category> subCategories = new ArrayList<Category>();
		category.setName("");
		category.setSubCategories(subCategories);

		return category;
	}

	public Category create(String name, Category fatherCategory) {

		Category category = new Category();
		List<Category> subCategories = new ArrayList<Category>();
		category.setName(name);
		category.setSubCategories(subCategories);

		return category;
	}

	public Category save(Category category) {
		return this.categoryRepository.save(category);
	}

	public Category findOne(int categoryId) {
		return this.categoryRepository.findOne(categoryId);
	}

	public void delete(Category category) {
		List<Category> subCategories = category.getSubCategories();
		if (subCategories.size() == 0)
			this.categoryRepository.delete(category);
		else
			for (Category subCategory : subCategories)
				this.categoryRepository.delete(subCategory);

	}

	public List<Category> findAll() {
		return this.categoryRepository.findAll();
	}

}
