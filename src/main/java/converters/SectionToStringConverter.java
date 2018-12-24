
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Section;

@Component
@Transactional
public class SectionToStringConverter implements Converter<Section, String> {

	@Override
	public String convert(Section section) {
		String result;

		if (section == null) {
			result = null;
		} else {
			result = String.valueOf(section.getId());
		}
		return result;
	}
}
