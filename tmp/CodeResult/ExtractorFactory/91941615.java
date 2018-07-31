package parsers;

import java.util.concurrent.ArrayBlockingQueue;

import data.ExtractedRow;
import data.TableDataFile;

/**
 * ExtractorFactory is used to create extractors according to a TableDataFile.
 */
public class ExtractorFactory {

	/**
	 * Creates an extractor suitable for parsing the given file.
	 * 
	 * @param file
	 *            the file to be parsed
	 * @param queue
	 *            the queue for staging extracted rows
	 * @return an extractor
	 */
	public static RowExtractor createExtractor(TableDataFile file,
			ArrayBlockingQueue<ExtractedRow> queue) {
		String fileName = file.getFileName().toString();
		String suffix = fileName.substring(fileName.lastIndexOf('.') + 1);

		if (suffix.equalsIgnoreCase("csv")) {
			return new CsvRowExtractor(file, queue);
		} else if (suffix.equalsIgnoreCase("xml")) {
			return new XmlRowExtractor(file, queue);
		} else if (suffix.equalsIgnoreCase("json")) {
			return new JsonRowExtractor(file, queue);
		} else {
			throw new IllegalArgumentException(String.format(
					"Cannot create extractor for file '%s'", fileName));
		}
	}

}
