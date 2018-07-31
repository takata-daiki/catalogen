package au.edu.unimelb.csse.smd.mechanix.client.text;

public class FontCollection {
	private static FontBoldWhite fBoldWhite;
	private static FontGrey fGrey;

	private FontCollection() {
	}

	// These use the singleton pattern loosely

	public static FontBoldWhite boldWhite() {
		if (fBoldWhite == null) {
			fBoldWhite = new FontBoldWhite();
		}
		return fBoldWhite;
	}

	public static FontGrey fontGrey() {
		if (fGrey == null) {
			fGrey = new FontGrey();
		}
		return fGrey;
	}
}
