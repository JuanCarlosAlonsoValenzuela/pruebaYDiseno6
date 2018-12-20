
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.SocialProfileRepository;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {

	@Autowired
	private SocialProfileRepository	socialProfileRepository;


	public SocialProfile save(SocialProfile socialProfile) {
		return this.socialProfileRepository.save(socialProfile);
	}

	public SocialProfile create() {
		SocialProfile socialProfile = new SocialProfile();

		socialProfile.setName("");
		socialProfile.setNick("");
		socialProfile.setProfileLink("");

		return socialProfile;
	}

	public SocialProfile create(String nick, String name, String profileLink) {
		SocialProfile socialProfile = new SocialProfile();

		socialProfile.setName(name);
		socialProfile.setNick(nick);
		socialProfile.setProfileLink(profileLink);

		return socialProfile;
	}

	public List<SocialProfile> findAll() {
		return this.socialProfileRepository.findAll();
	}

	public SocialProfile findOne(int socialProfileId) {
		return this.socialProfileRepository.findOne(socialProfileId);
	}

	public void delete(SocialProfile socialProfile) {
		this.socialProfileRepository.delete(socialProfile);
	}

}
