
package converters;

import java.net.URLDecoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import security.Authority;

@Component
@Transactional
public class StringToAuthorityConverter implements Converter<String, Authority> {

	@Override
	public Authority convert(String text) {

		Authority result;
		String parts[];

		if (text == null) {
			result = null;
		} else {
			try {
				parts = text.split("\\|");
				result = new Authority();
				result.setAuthority(URLDecoder.decode(parts[0], "UTF-8"));
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}

		}
		return result;
	}

}
