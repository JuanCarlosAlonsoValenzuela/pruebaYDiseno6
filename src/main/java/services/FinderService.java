
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.FinderRepository;
import domain.Finder;
import domain.FixUpTask;

@Service
@Transactional
public class FinderService {

	// Managed repository ------------------------------------------

	@Autowired
	private FinderRepository	finderRepository;


	// Supporting Services ------------------------------------------

	// Simple CRUD methods ------------------------------------------

	public Finder createFinder() {

		Finder result = new Finder();
		List<FixUpTask> fixUpTasks = new ArrayList<FixUpTask>();

		result.setCategory("");

		result.setFixUpTasks(fixUpTasks);
		result.setKeyWord("");

		result.setMaxPrice(1.0);
		result.setMinPrice(1.0);
		Date startDate = new Date();
		Date endDate = new Date();

		result.setLastEdit(new Date());

		result.setEndDate(endDate);
		result.setStartDate(startDate);

		result.setWarranty("");

		return result;
	}

	public Finder createFinder(String keyWord, String category, String warranty, Double minPrice, Double maxPrice, Date startDate, Date endDate, List<FixUpTask> fixUpTasks) {

		Finder result = new Finder();

		result.setCategory(category);

		result.setFixUpTasks(fixUpTasks);
		result.setKeyWord(keyWord);
		if (minPrice != null && maxPrice != null && minPrice <= maxPrice) {
			result.setMaxPrice(maxPrice);
			result.setMinPrice(minPrice);
		}
		if (startDate != null && endDate != null && startDate.before(endDate)) {
			result.setEndDate(endDate);
			result.setStartDate(startDate);
		}
		result.setWarranty(warranty);

		return result;
	}

	public Collection<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	public Finder findOne(int id) {
		return this.finderRepository.findOne(id);
	}

	public Finder save(Finder finder) {
		return this.finderRepository.save(finder);
	}

	public void delete(Finder finder) {
		this.finderRepository.delete(finder);
	}
}
