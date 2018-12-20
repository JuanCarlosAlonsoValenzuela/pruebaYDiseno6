
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.EndorsmentRepository;
import domain.Endorsment;

@Component
@Transactional
public class StringToEndorsmentConverter implements Converter<String, Endorsment> {

	@Autowired
	EndorsmentRepository	endorsmentRepository;


	@Override
	public Endorsment convert(String text) {

		Endorsment result = new Endorsment();
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = this.endorsmentRepository.findOne(id);
			}

		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
