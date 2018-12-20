package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
	"classpath:spring/config/packages.xml" })
@Transactional
public class SponsorServiceTest extends AbstractTest {

    @Autowired
    private SponsorService sponsorService;

    @Test
    public void testCreateSponsor() {
	Sponsor s = new Sponsor();
	Sponsor saved = new Sponsor();
	super.authenticate("PacoCustomer");
	s = this.sponsorService.create("Luisa", "Cosa", "Perez",
		"https://www.url2.com/example", "luisa@gmail.com",
		"+34746952921", "Reina Mercedes", "luisaSponsor",
		"81dc9bdb52d04dc20036dbd8313ed055");
	// Authority auth = new Authority();
	// auth.setAuthority(Authority.SPONSOR);
	// List<Authority> listAuth = new ArrayList<Authority>();
	System.out.println(s.getUserAccount().getAuthorities());
	// listAuth.add(auth);
	// s.getUserAccount().setAuthorities(listAuth);
	saved = this.sponsorService.save(s);

	System.out.println(s);

	Assert.isTrue(this.sponsorService.findAll().contains(saved));
	super.authenticate(null);
    }

}
