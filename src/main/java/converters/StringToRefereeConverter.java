
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.RefereeRepository;
import domain.Referee;

@Component
@Transactional
public class StringToRefereeConverter implements Converter<String, Referee> {

	@Autowired
	RefereeRepository	refereeRepository;


	@Override
	public Referee convert(String text) {

		Referee result = new Referee();
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = this.refereeRepository.findOne(id);
			}

		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
