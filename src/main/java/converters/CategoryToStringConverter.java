
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Category;

@Component
@Transactional
public class CategoryToStringConverter implements Converter<Category, String> {

	@Override
	public String convert(Category category) {
		String result;

		if (category == null) {
			result = null;
		} else {
			result = String.valueOf(category.getId());
		}
		return result;
	}

}
