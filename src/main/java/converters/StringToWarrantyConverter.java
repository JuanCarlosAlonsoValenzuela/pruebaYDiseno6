
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.WarrantyRepository;
import domain.Warranty;

@Component
@Transactional
public class StringToWarrantyConverter implements Converter<String, Warranty> {

	@Autowired
	WarrantyRepository	warrantyRepository;


	@Override
	public Warranty convert(String text) {
		int id;
		Warranty result;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = this.warrantyRepository.findOne(id);
			}

		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
