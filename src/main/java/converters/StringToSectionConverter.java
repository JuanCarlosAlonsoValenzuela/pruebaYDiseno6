
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.SectionRepository;
import domain.Section;

@Component
@Transactional
public class StringToSectionConverter implements Converter<String, Section> {

	@Autowired
	SectionRepository	sectionRepository;


	@Override
	public Section convert(String text) {

		Section result = new Section();
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = this.sectionRepository.findOne(id);
			}

		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
