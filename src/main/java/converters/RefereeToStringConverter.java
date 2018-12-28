
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Referee;

@Component
@Transactional
public class RefereeToStringConverter implements Converter<Referee, String> {

	@Override
	public String convert(Referee referee) {
		String result;

		if (referee == null) {
			result = null;
		} else {
			result = String.valueOf(referee.getId());
		}
		return result;
	}

}
