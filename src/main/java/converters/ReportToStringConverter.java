
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Report;

@Component
@Transactional
public class ReportToStringConverter implements Converter<Report, String> {

	@Override
	public String convert(Report report) {
		String result;

		if (report == null) {
			result = null;
		} else {
			result = String.valueOf(report.getId());
		}
		return result;
	}

}
