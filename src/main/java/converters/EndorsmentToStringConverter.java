
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Endorsment;

@Component
@Transactional
public class EndorsmentToStringConverter implements Converter<Endorsment, String> {

	@Override
	public String convert(Endorsment endorsment) {
		String result;

		if (endorsment == null) {
			result = null;
		} else {
			result = String.valueOf(endorsment.getId());
		}
		return result;
	}

}
