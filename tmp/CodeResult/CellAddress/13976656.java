package core;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.Link;
import com.google.gdata.data.batch.BatchOperationType;
import com.google.gdata.data.batch.BatchUtils;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;

public class SpreadsheetBatch {

	private static class CellAddress {
		public final int row;
		public final int col;
		public final String idString;

		/**
		 * Constructs a CellAddress representing the specified {@code row} and
		 * {@code col}. The idString will be set in 'RnCn' notation.
		 */
		public CellAddress(int row, int col) {
			this.row = row;
			this.col = col;
			this.idString = String.format("R%sC%s", row, col);
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws ServiceException
	 */
	public static void main(String[] args) throws IOException, ServiceException {
		// load properties
		Properties configFile = new Properties();
		configFile.load(ListGenerator.class.getClassLoader()
				.getResourceAsStream("config.properties"));

		// get service
		SpreadsheetService client = new SpreadsheetService(
				configFile.getProperty("appName"));
		client.setUserCredentials(configFile.getProperty("login"),
				configFile.getProperty("password"));

		// get feed
		URL metafeedUrl = new URL(
				"https://spreadsheets.google.com/feeds/spreadsheets/private/full");
		SpreadsheetFeed feed = client.getFeed(metafeedUrl,
				SpreadsheetFeed.class);

		// list spreadsheets
		List<SpreadsheetEntry> spreadsheets = feed.getEntries();

		SpreadsheetEntry spreadsheetEntry = spreadsheets.get(0);
		System.out.println("\t" + spreadsheetEntry.getTitle().getPlainText());
		System.out.println(spreadsheetEntry.getKey());

		// list worksheets
		List<WorksheetEntry> worksheets = spreadsheetEntry.getWorksheets();

		WorksheetEntry worksheetEntry = worksheets.get(0);

		// Build list of cell addresses to be filled in
		List<CellAddress> cellAddrs = new ArrayList<CellAddress>();
		for (int row = 1; row <= 4; ++row) {
			for (int col = 1; col <= 3; ++col) {
				cellAddrs.add(new CellAddress(row, col));
			}
		}

		CellFeed batchRequest = new CellFeed();
		for (CellAddress cellId : cellAddrs) {
			CellEntry batchEntry = new CellEntry(cellId.row, cellId.col,
					cellId.idString);
			batchEntry.setId(String.format("%s/%s", worksheetEntry.getCellFeedUrl().toString(),
					cellId.idString));
			BatchUtils.setBatchId(batchEntry, cellId.idString);
			BatchUtils.setBatchOperationType(batchEntry,
					BatchOperationType.DELETE);
			batchRequest.getEntries().add(batchEntry);
		}

		CellFeed cellFeed = client.getFeed(worksheetEntry.getCellFeedUrl(), CellFeed.class);
		CellFeed queryBatchResponse = client.batch(
				new URL(cellFeed.getLink(Link.Rel.FEED_BATCH, Link.Type.ATOM)
						.getHref()), batchRequest);


	}

}
