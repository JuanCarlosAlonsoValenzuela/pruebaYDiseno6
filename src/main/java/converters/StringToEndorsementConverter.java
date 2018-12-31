
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.EndorsementRepository;
import domain.Endorsement;

@Component
@Transactional
public class StringToEndorsementConverter implements Converter<String, Endorsement> {

	@Autowired
	EndorsementRepository	endorsmentRepository;


	@Override
	public Endorsement convert(String text) {

		Endorsement result = new Endorsement();
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
