package services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
	"classpath:spring/config/packages.xml" })
@Transactional
public class ConfigurationServiceTest extends AbstractTest {

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private HandyWorkerService handyWorkerService;

    @Test
    public void testGoodWords() {
	String goodWords;
	super.authenticate("PacoCustomer");
	goodWords = this.configurationService.showGoodWords();

	Assert.isTrue(!goodWords.isEmpty());
	super.authenticate(null);
    }

    @Test
    public void testBadWords() {
	String badWords;
	super.authenticate("PacoCustomer");
	badWords = this.configurationService.showBadWords();

	Assert.isTrue(!badWords.isEmpty());
	super.authenticate(null);
    }

    @Test
    public void testIsStringSpam() {
	Boolean result = false;
	super.authenticate("PacoCustomer");
	List<String> spam = new ArrayList<String>();
	spam = this.configurationService.getSpamWords();
	result = this.configurationService.isStringSpam("sex", spam);

	Assert.isTrue(result);

	super.authenticate(null);

    }

    @Test
    public void testComputeScore() {
	super.authenticate("PacoCustomer");
	Double res = 0.0;
	HandyWorker handyWorker = new HandyWorker();
	handyWorker = this.handyWorkerService
		.getHandyWorkerByUsername("PepeHW");

	res = this.configurationService.computeScore(handyWorker);
	Assert.isTrue(res == 0.3333333333333333);

	super.authenticate(null);
    }
}
