
package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdminRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Admin;
import domain.Box;
import domain.Category;
import domain.Customer;
import domain.HandyWorker;
import domain.Message;
import domain.SocialProfile;
import domain.Warranty;

@Service
@Transactional
public class AdminService {

	@Autowired
	private AdminRepository		adminRepository;

	@Autowired
	private WarrantyService		warrantyService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private MessageService		messageService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserAccountService	userAccountService;


	// 1. Create user accounts for new administrators.
	public void loggedAsAdmin() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		Assert.isTrue(authorities.get(0).toString().equals("ADMIN"));
	}

	public Admin createAdmin() {

		Admin admin = new Admin();

		List<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();
		List<Box> boxes = new ArrayList<Box>();

		UserAccount userAccountAdmin = new UserAccount();

		userAccountAdmin.setUsername("");
		userAccountAdmin.setPassword("");

		Box spamBox = new Box();
		List<Message> messages1 = new ArrayList<>();
		spamBox.setIsSystem(true);
		spamBox.setMessages(messages1);
		spamBox.setName("Spam");

		Box trashBox = new Box();
		List<Message> messages2 = new ArrayList<>();
		trashBox.setIsSystem(true);
		trashBox.setMessages(messages2);
		trashBox.setName("Trash");

		Box sentBox = new Box();
		List<Message> messages3 = new ArrayList<>();
		sentBox.setIsSystem(true);
		sentBox.setMessages(messages3);
		sentBox.setName("Sent messages");

		Box receivedBox = new Box();
		List<Message> messages4 = new ArrayList<>();
		receivedBox.setIsSystem(true);
		receivedBox.setMessages(messages4);
		receivedBox.setName("Received messages");

		boxes.add(receivedBox);
		boxes.add(sentBox);
		boxes.add(spamBox);
		boxes.add(trashBox);

		admin.setName("");
		admin.setMiddleName("");
		admin.setSurname("");
		admin.setPhoto("");
		admin.setEmail("");
		admin.setPhoneNumber("");
		admin.setAddress("");
		admin.setSocialProfiles(socialProfiles);
		admin.setBoxes(boxes);
		admin.setHasSpam(false);

		List<Authority> authorities = new ArrayList<Authority>();

		Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		authorities.add(authority);
		userAccountAdmin.setAuthorities(authorities);
		userAccountAdmin.setIsNotLocked(true);
		admin.setUserAccount(userAccountAdmin);

		return admin;
	}

	public Admin createAdmin(String name, String middleName, String surname, String photo, String email, String phoneNumber, String address, String userName, String password) {

		Admin admin = new Admin();

		List<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();
		List<Box> boxes = new ArrayList<Box>();

		UserAccount userAccountAdmin = new UserAccount();

		userAccountAdmin.setUsername(userName);
		userAccountAdmin.setPassword(password);

		Box spamBox = new Box();
		List<Message> messages1 = new ArrayList<>();
		spamBox.setIsSystem(true);
		spamBox.setMessages(messages1);
		spamBox.setName("Spam");

		Box trashBox = new Box();
		List<Message> messages2 = new ArrayList<>();
		trashBox.setIsSystem(true);
		trashBox.setMessages(messages2);
		trashBox.setName("Trash");

		Box sentBox = new Box();
		List<Message> messages3 = new ArrayList<>();
		sentBox.setIsSystem(true);
		sentBox.setMessages(messages3);
		sentBox.setName("Sent messages");

		Box receivedBox = new Box();
		List<Message> messages4 = new ArrayList<>();
		receivedBox.setIsSystem(true);
		receivedBox.setMessages(messages4);
		receivedBox.setName("Received messages");

		boxes.add(receivedBox);
		boxes.add(sentBox);
		boxes.add(spamBox);
		boxes.add(trashBox);

		admin.setName(name);
		admin.setMiddleName(middleName);
		admin.setSurname(surname);
		admin.setPhoto(photo);
		admin.setEmail(email);
		admin.setPhoneNumber(phoneNumber);
		admin.setAddress(address);
		admin.setSocialProfiles(socialProfiles);
		admin.setBoxes(boxes);
		admin.setHasSpam(false);

		List<Authority> authorities = new ArrayList<Authority>();

		Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		authorities.add(authority);
		userAccountAdmin.setAuthorities(authorities);
		userAccountAdmin.setIsNotLocked(true);
		admin.setUserAccount(userAccountAdmin);

		return admin;
	}

	public Admin save(Admin admin) {
		this.loggedAsAdmin();

		// Comprobacion en todos los SAVE de los ACTORES
		// Assert.isTrue(admin.getId() == 0 ||
		// userAccount.equals(admin.getUserAccount()));
		this.userAccountService.save(admin.getUserAccount());
		return this.adminRepository.save(admin);
	}

	/*
	 * 2. Manage the catalogue of warranties, which includes listing, showing,
	 * creating, updating, and deleting them. A warranty can be updated or
	 * deleted as long as it is saved in draft mode. Once it’s saved in final
	 * mode, it cannot be edited or deleted. Only warranties that are saved in
	 * final mode can be referenced by fix-up tasks.
	 */

	public Warranty createWarranty() {
		Warranty warranty = new Warranty();
		List<String> terms = new ArrayList<String>();
		List<String> laws = new ArrayList<String>();

		warranty.setLaws(laws);
		warranty.setTerms(terms);
		warranty.setIsDraftMode(true);

		return warranty;
	}

	public Warranty createWarranty(String tittle) {
		Warranty warranty = new Warranty();
		List<String> terms = new ArrayList<String>();
		List<String> laws = new ArrayList<String>();

		warranty.setTitle(tittle);
		warranty.setLaws(laws);
		warranty.setTerms(terms);
		warranty.setIsDraftMode(true);

		return warranty;
	}

	public Warranty saveWarranty(Warranty warranty) {
		this.loggedAsAdmin();

		Assert.isTrue(warranty.getIsDraftMode());
		return this.warrantyService.save(warranty);
	}

	public void warrantyToFinalMode(Warranty warranty) {
		this.loggedAsAdmin();

		warranty.setIsDraftMode(false);
		this.warrantyService.save(warranty);
	}

	public List<Warranty> listWarranty() {
		this.loggedAsAdmin();
		return this.warrantyService.findAll();
	}

	public void updateWarranty(Warranty warranty, String tittle, List<String> laws, List<String> terms) {
		this.loggedAsAdmin();
		warranty.setTitle(tittle);

		List<String> newLaws = warranty.getLaws();
		newLaws.addAll(laws);
		warranty.setLaws(newLaws);

		List<String> newTerms = warranty.getTerms();
		newTerms.addAll(terms);
		warranty.setTerms(newTerms);
		this.warrantyService.save(warranty);

	}

	public Map<String, Warranty> showWarranties() {
		this.loggedAsAdmin();
		Map<String, Warranty> result = new HashMap<String, Warranty>();
		List<Warranty> warranties = new ArrayList<Warranty>();

		warranties = this.listWarranty();

		for (Warranty w : warranties)
			result.put(w.getTitle(), w);
		return result;

	}

	public void deleteWarranty(Warranty warranty) {
		this.loggedAsAdmin();
		Assert.isTrue(warranty.getIsDraftMode());

		this.warrantyService.delete(warranty);
	}

	/*
	 * 3. Manage the catalogue of categories, which includes listing, showing,
	 * creating, updating, and deleting them. Note that categories evolve
	 * independently from fix-up tasks, which means that they can be created,
	 * modified, or deleted independently from whether they are referenced from
	 * a fix-up task or not.
	 */

	public List<Category> listCategory() {
		this.loggedAsAdmin();
		return this.categoryService.findAll();
	}

	public Map<String, Category> showCategory() {
		this.loggedAsAdmin();
		Map<String, Category> result = new HashMap<String, Category>();
		List<Category> categories = new ArrayList<Category>();

		categories = this.listCategory();

		for (Category c : categories)
			result.put(c.getName(), c);
		return result;
	}

	public Category createCategory() {
		this.loggedAsAdmin();

		Category category = new Category();

		return category;
	}

	public Category createCategory(List<Category> subCategories, String name) {
		this.loggedAsAdmin();

		Category category = new Category();

		category.setName(name);
		category.setSubCategories(subCategories);

		return category;
	}

	public Category updateCategory(Category category) {
		this.loggedAsAdmin();

		return this.categoryService.save(category);
	}

	public void deleteCategory(Category category) {
		this.loggedAsAdmin();
		// System.out.println(category.getSubCategories().size());
		/*
		 * if (category.getSubCategories().size() == 0) {
		 * System.out.println("Pasa por el if");
		 * this.categoryService.delete(category);
		 * 
		 * } else { System.out.println("Pasa por el else"); for (Category c :
		 * category.getSubCategories()) this.deleteCategory(c); }
		 */
		this.categoryService.delete(category);
	}

	// 4. Broadcast a message to all of the actors of the system.

	// Guardar copia del mensaje para cada uno de los usuarios

	/*
	 * Display a dashboard with the following information: The average, the
	 * minimum, the maximum, and the standard deviation of the number of fix-up
	 * tasks per user. The average, the minimum, the maximum, and the standard
	 * deviation of the number of applications per fix-up task. The average, the
	 * minimum, the maximum, and the standard deviation of the maximum price of
	 * the fix-up tasks. The average, the minimum, the maximum, and the standard
	 * deviation of the price offered in the applications. The ratio of pending
	 * applications. The ratio of accepted applications. The ratio of rejected
	 * applications. The ratio of pending applications that cannot change its
	 * status because their time period’s elapsed. The listing of customers who
	 * have published at least 10% more fix-up tasks than the average, ordered
	 * by number of applications. The listing of handy workers who have got
	 * accepted at least 10% more applications than the average, ordered by
	 * number of applications
	 */

	public Map<String, Double[]> computeStatistics() {
		this.loggedAsAdmin();

		final Map<String, Double[]> result;
		Double[] calculations1;
		Double[] calculations2;
		Double[] calculations3;
		Double[] calculations4;
		Double[] calculations5;
		Double[] calculations6;

		calculations1 = this.adminRepository.fixUpTaskPerUser();
		calculations2 = this.adminRepository.applicationPerFixUpTask();
		calculations3 = this.adminRepository.maxPricePerFixUpTask();
		calculations4 = this.adminRepository.priceOferredPerApplication();
		calculations5 = this.adminRepository.numberComplaintsPerFixUpTask();
		calculations6 = this.adminRepository.notesPerReferee();

		result = new HashMap<String, Double[]>();
		result.put("fixUpTaskPerUser", calculations1);
		result.put("applicationPerFixUpTask", calculations2);
		result.put("maxPricePerFixUpTask", calculations3);
		result.put("priceOferredPerApplication", calculations4);
		result.put("numberComplaintsPerFixUpTask", calculations5);
		result.put("notesPerReferee", calculations6);

		return result;
	}

	/*
	 * The ratio of pending applications. The ratio of accepted applications.
	 * The ratio of rejected applications. The ratio of pending applications
	 * that cannot change its status because their time period’s elapsed.
	 */

	public Map<String, Double> computeStatisticsRatios() {
		this.loggedAsAdmin();

		Double ratioPendingApplications, ratioAcceptedApplications, ratioRejectedApplications, ratioPendingElapsedApplications;
		Double fixUpTaskWithComplain;
		final Map<String, Double> result;

		ratioPendingApplications = this.adminRepository.ratioPendingApplications();
		ratioAcceptedApplications = this.adminRepository.ratioAcceptedApplications();
		ratioRejectedApplications = this.adminRepository.ratioRejectedApplications();
		ratioPendingElapsedApplications = this.adminRepository.ratioPendingElapsedApplications();
		fixUpTaskWithComplain = this.adminRepository.fixUpTaskWithComplain();

		result = new HashMap<String, Double>();
		result.put("ratioPendingApplications", ratioPendingApplications);
		result.put("ratioAcceptedApplications", ratioAcceptedApplications);
		result.put("ratioRejectedApplications", ratioRejectedApplications);
		result.put("ratioPendingElapsedApplications", ratioPendingElapsedApplications);
		result.put("fixUpTaskWithComplain", fixUpTaskWithComplain);

		return result;
	}
	// The listing of customers who have published at least 10% more fix-up
	// tasks
	// than the average, ordered by number of applications.

	public Map<String, List<Customer>> tenPercentMoreApplicationsCustomers() {
		this.loggedAsAdmin();

		Map<String, List<Customer>> result;
		List<Customer> calculations1;
		// List<HandyWorker> calculations2;

		calculations1 = this.adminRepository.customers10PercentMoreApplications();
		// calculations2 =
		// this.adminRepository.handyWorkers10PercentMoreApplications();

		result = new HashMap<String, List<Customer>>();
		result.put("customers10PercentMoreApplications", calculations1);
		return result;
	}

	/*
	 * The listing of handy workers who have got accepted at least 10% more
	 * applications than the average, ordered by number of applications
	 */
	public Map<String, List<HandyWorker>> tenPercentMoreApplicationsHandyWorker() {
		this.loggedAsAdmin();

		final Map<String, List<HandyWorker>> result;
		// List<Customer> calculations1;
		List<HandyWorker> calculations2;

		// calculations1 =
		// this.adminRepository.customers10PercentMoreApplications();
		calculations2 = this.adminRepository.handyWorkers10PercentMoreApplications();

		result = new HashMap<String, List<HandyWorker>>();
		result.put("customers10PercentMoreApplications", calculations2);
		return result;
	}

	/*
	 * public Admin saveReferee(final Referee referee) { UserAccount
	 * userAccount; userAccount = LoginService.getPrincipal();
	 * Assert.isTrue(userAccount.getAuthorities().contains("ADMIN")); return
	 * this.refereeService.save(referee); }
	 */

	/*
	 * The minimum, the maximum, the average, and the standard deviation of the
	 * number of complaints per fix-up task.
	 */

	/* The ratio of fix-up tasks with a complaint. */

	public Map<String, List<Customer>> top3Customers() {
		this.loggedAsAdmin();

		final Map<String, List<Customer>> result;
		// List<Customer> calculations1;
		List<Customer> calculations2;

		// calculations1 =
		// this.adminRepository.customers10PercentMoreApplications();
		calculations2 = this.adminRepository.customerTermsofComplainsOrdered();

		result = new HashMap<String, List<Customer>>();

		// Mirar que pasa si hay solo 2 elementos en la lista
		result.put("customers10PercentMoreApplications", calculations2.subList(0, 3));
		return result;
	}

	public Map<String, List<HandyWorker>> top3HandyWorker() {
		this.loggedAsAdmin();

		final Map<String, List<HandyWorker>> result;
		// List<Customer> calculations1;
		List<HandyWorker> calculations2 = new ArrayList<HandyWorker>();

		// calculations1 =
		// this.adminRepository.customers10PercentMoreApplications();
		calculations2 = this.adminRepository.HandyWorkerTermsofComplainsOrdered();

		result = new HashMap<String, List<HandyWorker>>();

		// Mirar que pasa si hay solo 2 elementos en la lista
		result.put("HandyWorkerTermsofComplainsOrdered", calculations2.subList(0, 3));
		return result;
	}

	public void broadcastMessage(Message message) {
		this.loggedAsAdmin();

		//List<Actor> actors = new ArrayList<Actor>();
		//actors = this.actorService.findAll();

		message.setReceiver(message.getReceiver());

		Message saved = this.messageService.save(message);
		this.messageService.sendMessage(saved);

	}

	public void banSuspiciousActor(Actor a) {
		this.loggedAsAdmin();

		Assert.isTrue(a.getHasSpam());

		a.getUserAccount().setIsNotLocked(false);
		this.actorService.save(a);
	}

	public void unBanSuspiciousActor(Actor a) {
		this.loggedAsAdmin();

		a.getUserAccount().setIsNotLocked(true);
		this.actorService.save(a);
	}

	public List<Admin> findAll() {
		return this.adminRepository.findAll();
	}

	public Admin getAdminByUsername(String a) {
		return this.adminRepository.getAdminByUserName(a);
	}

	public Admin findOne(int adminId) {
		return this.findOne(adminId);
	}

	public List<Admin> findAll2() {
		return this.adminRepository.findAll2();
	}

}
