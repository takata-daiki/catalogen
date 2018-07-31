package com.ianl.podcasts;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.ianl.podcasts.PodcastCategoryList.PodcastsCategories;

class SAXHelper {
	private URL url;

	public SAXHelper(String url) throws MalformedURLException {
		this.url = new URL(url);
	}

	public ArrayList<Podcast> parseContent(PodcastsCategories podcastCategory) {
		// We need the SAX parser handler object
		PodcastRSSHandler df = new PodcastRSSHandler(podcastCategory);
		try {
			// At first we need to get an SAX Parser Factory object
			SAXParserFactory spf = SAXParserFactory.newInstance();
			// Using factory we create a new SAX Parser instance
			SAXParser sp = spf.newSAXParser();			
			XMLReader xr = sp.getXMLReader();
			xr.setContentHandler(df);
			// We call the method parsing our RSS Feed
			xr.parse(new InputSource(url.openStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return df.getAllPodcasts();
	}
	
}