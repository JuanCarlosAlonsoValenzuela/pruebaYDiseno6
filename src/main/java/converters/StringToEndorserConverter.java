
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.EndorserRepository;
import domain.Endorser;

@Component
@Transactional
public class StringToEndorserConverter implements Converter<String, Endorser> {

	@Autowired
	EndorserRepository	endorserRepository;


	@Override
	public Endorser convert(String text) {

		Endorser result = new Endorser();
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = this.endorserRepository.findOne(id);
			}

		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
