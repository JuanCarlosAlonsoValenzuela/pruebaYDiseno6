
package services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ConfigurationRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Box;
import domain.Configuration;
import domain.Endorsement;
import domain.Endorser;
import domain.Message;
import domain.SocialProfile;

@Service
@Transactional
public class ConfigurationService {

	@Autowired
	private ConfigurationRepository	configurationRepository;

	@Autowired
	private EndorserService			endorserService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdminService			adminService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private HandyWorkerService		handyWorkerService;


	public Configuration getConfiguration() {
		return this.configurationRepository.findAll().get(0);
	}

	public Configuration save(Configuration configuration) {
		return this.configurationRepository.save(configuration);
	}

	public List<String> getSpamWords() {
		return this.configurationRepository.spamWords();
	}

	public Boolean isStringSpam(String s, List<String> spamWords) {
		Boolean result = false;

		this.actorService.loggedAsActor();
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Actor actor = this.actorService.getActorByUsername(userAccount.getUsername());

		List<String> trimmedString = new ArrayList<String>();
		trimmedString = Arrays.asList(s.split("\\+|(?=[,.¿?;!¡])"));

		//("\\s*(=>|,|\\s)\\s*"));
		for (String g : spamWords) {
			for (String c : trimmedString) {
				if (g.equals(c) || g.equalsIgnoreCase(c)) {
					result = true;
					break;
				}
			}
		}
		if (result == true) {
			actor.setHasSpam(true);
			this.actorService.save(actor);
		}
		return result;
	}
	public Boolean isActorSuspicious(Actor a) {
		Boolean result = false;
		List<String> spamWords = new ArrayList<String>();
		spamWords = this.getSpamWords();

		// COMPROBANDO PARAMETROS DEL PERFIL DE LOS ACTORES
		result = this.isStringSpam(a.getName(), spamWords);
		if (!result) {
			result = this.isStringSpam(a.getMiddleName(), spamWords);
		}
		if (!result) {
			result = this.isStringSpam(a.getSurname(), spamWords);
		}
		if (!result) {
			result = this.isStringSpam(a.getAddress(), spamWords);
		}
		if (!result) {
			result = this.isStringSpam(a.getEmail(), spamWords);
		}
		if (!result) {
			result = this.isStringSpam(a.getPhoneNumber(), spamWords);
		}
		if (!result) {
			result = this.isStringSpam(a.getPhoto(), spamWords);
		}
		if (!result) {
			result = this.isStringSpam(a.getUserAccount().getUsername(), spamWords);
		}
		if (!result) {
			// COMPROBANDO LAS REDES SOCIALES
			for (SocialProfile s : a.getSocialProfiles()) {
				if (this.isStringSpam(s.getName(), spamWords) || this.isStringSpam(s.getNick(), spamWords) || this.isStringSpam(s.getProfileLink(), spamWords)) {
					result = true;
					break;
				}
			}
		}
		if (!result) {
			// COMPROBANDO LAS CAJAS DEL ACTOR
			for (Box b : a.getBoxes()) {
				if (this.isStringSpam(b.getName(), spamWords)) {
					result = true;
					break;
				} else {
					// COMPROBANDO LOS MENSAJES DEL ACTOR
					for (Message g : b.getMessages()) {
						if (g.getSender().equals(a) && (this.isStringSpam(g.getBody(), spamWords) || this.isStringSpam(g.getSubject(), spamWords))) {
							result = true;
							break;
						}
					}
				}
			}
		}
		/*
		 * if (!result) {
		 * if (a instanceof HandyWorker) {
		 * HandyWorker h = (HandyWorker) a;
		 * // COMPROBANDO TUTORIALES DEL HANDY WORKER
		 * for (Tutorial t : h.getTutorials()) {
		 * if (this.isStringSpam(t.getTitle(), spamWords) || this.isStringSpam(t.getSummary(), spamWords)) {
		 * result = true;
		 * break;
		 * } else {
		 * for (Section s : t.getSections()) {
		 * if (this.isStringSpam(s.getSectionTitle(), spamWords) || this.isStringSpam(s.getText(), spamWords)) {
		 * result = true;
		 * break;
		 * }
		 * }
		 * }
		 * }
		 * 
		 * // COMPROBANDO LOS COMENTARIOS DE LAS APLICATIONS
		 * for (Application ap : h.getApplications()) {
		 * for (String s : ap.getComments()) {
		 * if (this.isStringSpam(s, spamWords)) {
		 * result = true;
		 * break;
		 * }
		 * }
		 * }
		 * // COMPROBANDO EL CURRICULUM DEL HANDY WORKER
		 * for (EndorserRecord b : h.getCurriculum().getEndorserRecords()) {
		 * if (this.isStringSpam(b.getEmail(), spamWords) || this.isStringSpam(b.getFullName(), spamWords) || this.isStringSpam(b.getLinkLinkedInProfile(), spamWords) || this.isStringSpam(b.getPhoneNumber(), spamWords)) {
		 * result = true;
		 * break;
		 * } else {
		 * for (String str : b.getComments()) {
		 * if (this.isStringSpam(str, spamWords)) {
		 * result = true;
		 * break;
		 * }
		 * }
		 * }
		 * }
		 * if (!result) {
		 * for (MiscellaneousRecord m : h.getCurriculum().getMiscellaneousRecords()) {
		 * if (this.isStringSpam(m.getTitle(), spamWords)) {
		 * result = true;
		 * break;
		 * } else {
		 * for (String str : m.getComments()) {
		 * if (this.isStringSpam(str, spamWords)) {
		 * result = true;
		 * break;
		 * }
		 * }
		 * }
		 * }
		 * }
		 * if (!result) {
		 * for (EducationRecord m : h.getCurriculum().getEducationRecords()) {
		 * if (this.isStringSpam(m.getTitle(), spamWords) || this.isStringSpam(m.getInstitution(), spamWords)) {
		 * result = true;
		 * break;
		 * } else {
		 * for (String str : m.getComments()) {
		 * if (this.isStringSpam(str, spamWords)) {
		 * result = true;
		 * break;
		 * }
		 * }
		 * }
		 * }
		 * }
		 * if (!result) {
		 * for (ProfessionalRecord m : h.getCurriculum().getProfessionalRecords()) {
		 * if (this.isStringSpam(m.getNameCompany(), spamWords) || this.isStringSpam(m.getRole(), spamWords)) {
		 * result = true;
		 * break;
		 * } else {
		 * for (String str : m.getComments()) {
		 * if (this.isStringSpam(str, spamWords)) {
		 * result = true;
		 * break;
		 * }
		 * }
		 * }
		 * }
		 * }
		 * if (!result) {
		 * if (this.isStringSpam(h.getCurriculum().getPersonalRecord().getFullName(), spamWords) || this.isStringSpam(h.getCurriculum().getPersonalRecord().getEmail(), spamWords)
		 * || this.isStringSpam(h.getCurriculum().getPersonalRecord().getPhoto(), spamWords) || this.isStringSpam(h.getCurriculum().getPersonalRecord().getPhoneNumber(), spamWords)) {
		 * }
		 * }
		 * 
		 * }
		 * }
		 * // COMRPOBACION COMPLETA REFEREE
		 * if (!result) {
		 * if (a instanceof Referee) {
		 * Referee h = (Referee) a;
		 * for (Report b : h.getReports()) {
		 * if (this.isStringSpam(b.getDescription(), spamWords)) {
		 * result = true;
		 * break;
		 * } else if (!result) {
		 * for (String str : b.getAttachments()) {
		 * if (this.isStringSpam(str, spamWords)) {
		 * result = true;
		 * break;
		 * 
		 * } else if (!result) {
		 * for (Note n : b.getNotes()) {
		 * if (this.isStringSpam(n.getMandatoryComment(), spamWords)) {
		 * result = true;
		 * break;
		 * } else {
		 * for (String string : n.getOptionalComments()) {
		 * if (this.isStringSpam(string, spamWords)) {
		 * result = true;
		 * break;
		 * }
		 * }
		 * }
		 * }
		 * }
		 * }
		 * }
		 * }
		 * }
		 * }
		 * if (!result) {
		 * if (a instanceof Customer) {
		 * Customer h = (Customer) a;
		 * 
		 * for (FixUpTask t : h.getFixUpTasks()) {
		 * if (this.isStringSpam(t.getAddress(), spamWords) || this.isStringSpam(t.getDescription(), spamWords)) {
		 * result = true;
		 * break;
		 * } else {
		 * for (Phase s : t.getPhases()) {
		 * if (this.isStringSpam(s.getTitle(), spamWords) || this.isStringSpam(s.getDescription(), spamWords)) {
		 * result = true;
		 * break;
		 * }
		 * }
		 * }
		 * }
		 * }
		 * }
		 * if (!result) {
		 * if (a instanceof Endorser) {
		 * Endorser h = (Endorser) a;
		 * for (Endorsement t : h.getEndorsements()) {
		 * for (String s : t.getComments()) {
		 * if (this.isStringSpam(s, spamWords)) {
		 * result = true;
		 * break;
		 * }
		 * }
		 * }
		 * }
		 * }
		 */
		return result;
	}

	public String showGoodWords() {
		return this.configurationRepository.goodWords();
	}

	public String showBadWords() {
		return this.configurationRepository.badWords();
	}

	public List<String> showGoodWordsList() {
		this.adminService.loggedAsAdmin();
		String goodWordString = this.configurationRepository.goodWords();

		List<String> goodWordsList = Arrays.asList(goodWordString.split(",[ ]*"));

		return goodWordsList;
	}

	public List<String> showBadWordsList() {
		this.adminService.loggedAsAdmin();
		String badWordString = this.configurationRepository.badWords();

		List<String> badWordsList = Arrays.asList(badWordString.split(",[ ]*"));

		return badWordsList;
	}

	public String addGoodWords(String word) {
		this.adminService.loggedAsAdmin();
		Configuration configuration = this.configurationRepository.configuration();
		String goodWords = configuration.getGoodWords();
		configuration.setGoodWords(goodWords = goodWords + "," + word);
		this.configurationRepository.save(configuration);

		return configuration.getGoodWords();
	}

	public String addBadWords(String word) {
		this.adminService.loggedAsAdmin();
		Configuration configuration = this.configurationRepository.configuration();
		String badWords = configuration.getBadWords();
		configuration.setBadWords(badWords = badWords + "," + word);
		this.configurationRepository.save(configuration);

		return configuration.getBadWords();
	}

	public String editWord(String word, String originalWord) {
		this.adminService.loggedAsAdmin();
		String result = "";
		String goodWords = this.showGoodWords();
		String badWords = this.showBadWords();
		Configuration configuration = this.configurationRepository.configuration();
		List<String> goodWordsList = Arrays.asList(goodWords.split(",[ ]*"));
		List<String> badWordsList = Arrays.asList(badWords.split(",[ ]*"));

		Integer cont = 0;

		if (goodWordsList.contains(originalWord)) {

			for (String s : goodWordsList) {
				if (s.equals(originalWord)) {
					goodWordsList.set(cont, word);

				}
				cont++;
			}

			for (int i = 0; i < goodWordsList.size(); i++) {
				if (i < goodWordsList.size() - 1) {
					result = result + goodWordsList.get(i) + ",";
				} else {
					result = result + goodWordsList.get(i);
				}
			}
			configuration.setGoodWords(result);

		} else {
			for (String s : badWordsList) {
				if (s.equals(originalWord)) {
					badWordsList.set(cont, word);

				}
				cont++;
			}

			for (int i = 0; i < badWordsList.size(); i++) {
				if (i < badWordsList.size() - 1) {
					result = result + badWordsList.get(i) + ",";
				} else {
					result = result + badWordsList.get(i);
				}
			}
			configuration.setBadWords(result);
		}

		this.configurationRepository.save(configuration);

		return configuration.getGoodWords();
	}

	public void deleteGoodWord(String word) {
		this.adminService.loggedAsAdmin();
		String goodWords = this.showGoodWords();
		Configuration configuration = this.configurationRepository.configuration();

		List<String> goodWordsList = new ArrayList<String>();
		goodWordsList.addAll(Arrays.asList(goodWords.split(",[ ]*")));

		if (goodWordsList.contains(word)) {
			goodWordsList.remove(word);
		}

		String result = "";

		for (int i = 0; i < goodWordsList.size(); i++) {
			if (i < goodWordsList.size() - 1) {
				result = result + goodWordsList.get(i) + ",";
			} else {
				result = result + goodWordsList.get(i);
			}
		}

		configuration.setGoodWords(result);
		this.configurationRepository.save(configuration);
	}

	public void deleteBadWord(String word) {
		this.adminService.loggedAsAdmin();
		String badWords = this.showBadWords();
		Configuration configuration = this.configurationRepository.configuration();

		List<String> badWordsList = new ArrayList<String>();
		badWordsList.addAll(Arrays.asList(badWords.split(",[ ]*")));

		if (badWordsList.contains(word)) {
			badWordsList.remove(word);
		}

		String result = "";

		for (int i = 0; i < badWordsList.size(); i++) {
			if (i < badWordsList.size() - 1) {
				result = result + badWordsList.get(i) + ",";
			} else {
				result = result + badWordsList.get(i);
			}
		}

		configuration.setBadWords(result);
		this.configurationRepository.save(configuration);
	}

	/*
	 * Launch a process that computes a score for every customer and handy
	 * worker. The score is computed building on the endorsements that theyâ€™ve
	 * got. The system must analyse the comments in the endorsements and compute
	 * the number of positive words (p) and the number of negative words (n).
	 * The score must be computed as p â€“ n normalised to range -1.00 up to
	 * +1.00 using a linear homothetic transformation.
	 */

	public Double computeScore(Endorser e) {
		String goodWords = this.configurationRepository.goodWords();
		String badWoords = this.configurationRepository.badWords();
		Double countGood = 0.0;
		Double countBad = 0.0;
		Double total = 0.0;
		List<Double> parcialresult = new ArrayList<Double>();

		List<String> goodWordsList = Arrays.asList(goodWords.split(",[ ]*"));
		List<String> badWordsList = Arrays.asList(badWoords.split(",[ ]*"));

		List<Endorsement> endorsments = e.getEndorsements();
		if (!endorsments.isEmpty()) {
			for (Endorsement endo : endorsments) {
				if (endo.getWrittenTo().equals(e)) {
					for (String g : endo.getComments()) {
						List<String> commentSplit = Arrays.asList(g.split("\\W"));
						for (String word : commentSplit) {
							if (goodWordsList.contains(word)) {
								countGood = countGood + 1.0;
							}
							if (badWordsList.contains(word)) {
								countBad = countBad - 1.0;
							}
							total = countGood - countBad;
						}
					}
				}
			}
		}
		if (total == 0.) {
			total = 1.;
		}
		parcialresult.add((countGood / total) + (countBad / total));
		Double res = 0.0;
		Double cont = 0.0;

		for (Double d : parcialresult) {
			cont = cont + d;
		}
		if (parcialresult.size() == 0) {
			res = 0.;
		} else {
			res = cont / parcialresult.size();
		}
		DecimalFormat df2 = new DecimalFormat(".##");
		e.setScore(Double.valueOf(df2.format(res)));

		this.endorserService.save(e);

		return res;
	}

	public Map<Endorser, Double> computeAllScores(List<Endorser> endorsers) {
		Map<Endorser, Double> result = new HashMap<Endorser, Double>();

		for (Endorser e : endorsers) {
			if (!e.getEndorsements().isEmpty()) {
				result.put(e, this.computeScore(e));
			}
		}
		return result;
	}

	public List<Double> computeAllScoresDouble(List<Endorser> endorsers) {
		List<Double> result = new ArrayList<Double>();

		for (Endorser e : endorsers) {
			if (!e.getEndorsements().isEmpty()) {
				Double res = 0.0;
				res = this.computeScore(e);
				result.add(res);
			}
		}
		return result;
	}

}
