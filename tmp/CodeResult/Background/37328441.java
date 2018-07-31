/*
 * Copyright (C) 2012 - Marcin Piechota
 * Licensed under the Creative Commons Attribution-NonCommercial 3.0 Unported (CC BY-NC 3.0)
 * You may obtain a copy of the License at http://creativecommons.org/licenses/by-nc/3.0/legalcode
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.cremag.utils.file.genomic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.StatisticalSummary;
import org.apache.commons.math3.stat.descriptive.StatisticalSummaryValues;
import org.apache.commons.math3.stat.inference.TestUtils;
import org.apache.log4j.Logger;
import org.cremag.exception.InvalidBedStringException;
import org.cremag.genomeStore.Genome;
import org.cremag.genomic.BedItem;
import org.cremag.genomic.Coverage;
import org.cremag.utils.GenomeStoreFileUtils;
import org.cremag.utils.XmlUtils;
import org.cremag.utils.file.genomic.GenomicTrack.TrackClass;
import org.cremag.utils.stats.StatisticalResult;
import org.cremag.utils.stats.StatisticalResultSet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author marpiech
 * Background class contains description of precomputed background for calculation
 * of statistics
 */
public class Background {
	
	private static final Logger logger = Logger.getLogger(Background.class);
	
	/**
	 * List of genomic intervals for which background is computed
	 */
	List <BedItem> bedItems;
	
	/**
	 * The key: genomic track id
	 * The value: mean and sd computed for the given track
	 */
	Map <String, StatisticalSummary> background = 
			new TreeMap <String, StatisticalSummary> ();
	
	/**
	 * background file name
	 */
	String path;
	
	/**
	 * genome for which background was computed
	 */
	Genome genome;
	
	/**
	 * name of background
	 */
	String name;
	
	public Background (String path) {
		this.path = path;
	}
	
	public Background (List <BedItem> bedItems, String path) {
		this.bedItems = bedItems;
		this.path = path;
	}
	
	public Background (List <BedItem> bedItems) {
		this.bedItems = bedItems;
	}
	
	/**
	 * @param genome
	 * compute function calculates mean and sd for each bigwig track from genome
	 */
	public void compute(Genome genome) {
		//logger.info("Compute full");
		this.genome = genome;
		compute(genome.getBigWigTracks(), false);
	}

	public void compute(Genome genome, Set <TrackClass> filters) {
		//logger.info("Compute filters");
		this.genome = genome;
		compute(genome.getBigWigTracks(filters), true);
	}
	
	public void recompute() {
		//logger.info("Recompute full");
		compute(genome.getBigWigTracks(), false);
	}
	
	public void recompute(Set <TrackClass> filters) {
		//logger.info("Recompute filters");
		compute(genome.getBigWigTracks(filters), true);
	}
	
	private void compute (List <BigWig> bigWigTracks, boolean includeFilters) {
		
		/* 
		 * this function will remove unused tracks if the filters are on
		 * and will only add new entries if the filters are off
		 */
		
		Map <String, StatisticalSummary> recomputedBackground = 
				new TreeMap <String, StatisticalSummary> ();
		
		for (BigWig track : bigWigTracks) {
			
			StatisticalSummary existingSummary = background.get(track.getId()); 
			
			if (existingSummary == null) {
				DescriptiveStatistics stat = new DescriptiveStatistics();
				for(BedItem bed : bedItems) {
					try {
						Coverage coverage = track.getCoverage(bed);
						stat.addValue((double) coverage.getSum() / ((double) bed.getLength()));
					} catch (Exception e) {stat.addValue(0); /* if nothing then 0 */}
				}
				
				StatisticalSummary summary = new StatisticalSummaryValues(stat.getMean(), stat.getVariance(), stat.getN(), stat.getMax(), stat.getMin(), stat.getSum());
				
				if (includeFilters)
					recomputedBackground.put(track.getId(), summary);
				else 
					background.put(track.getId(), summary);
				
			} else {recomputedBackground.put(track.getId(), existingSummary);}
		}
		
		if (includeFilters)
			this.background = recomputedBackground;
		
	}
	
	public void save() throws ParserConfigurationException, IOException {
		
		/* using DocumentBuilder to set up new document */
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		
		/* creating root element */
		Element rootElement = doc.createElement("background");
		doc.appendChild(rootElement);
		
		Element bedItems = doc.createElement("bedItems");
		rootElement.appendChild(bedItems);
		
		for(BedItem bed : this.bedItems) {
			Element bedElement = doc.createElement("bed");
			bedElement.setTextContent(bed.toString());
			bedItems.appendChild(bedElement);
		}
		
		Element backgroundItems = doc.createElement("background");
		rootElement.appendChild(bedItems);
		
		for(Entry<String, StatisticalSummary> entry : this.background.entrySet()) {
			Element backgroundElement = doc.createElement("background");
			backgroundElement.setAttribute("id", entry.getKey());
			backgroundElement.setAttribute("mean", "" + entry.getValue().getMean());
			backgroundElement.setAttribute("variance", "" + entry.getValue().getVariance());
			backgroundElement.setAttribute("n", "" + entry.getValue().getN());
			backgroundElement.setAttribute("max", "" + entry.getValue().getMax());
			backgroundElement.setAttribute("min", "" + entry.getValue().getMin());
			backgroundElement.setAttribute("sum", "" + entry.getValue().getSum());
			backgroundItems.appendChild(backgroundElement);
		}
		
		rootElement.appendChild(backgroundItems);
		
		XmlUtils.saveDocumentToFile(this.path, doc);
	}
	
	/**
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void load () throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new File(this.path));
		doc.getDocumentElement().normalize();
		
		Element root = doc.getDocumentElement();
		Element bedItems = (Element) root.getElementsByTagName("bedItems").item(0);
		NodeList bedList = bedItems.getChildNodes();
		
		this.bedItems = new ArrayList <BedItem> ();
		
		for(int i = 0; i < bedList.getLength(); i++) { 
			try {
				this.bedItems.add(new BedItem(bedList.item(i).getTextContent()));
			} catch (InvalidBedStringException e) {
				/* this catch catches \n text entries */
			}
		}
		
		Element backgroundItems = (Element) root.getElementsByTagName("background").item(0);
		NodeList backgroundList = backgroundItems.getChildNodes();
		
		for(int i = 0; i < backgroundList.getLength(); i++) {
			try {
				Element backgroundItem = (Element) backgroundList.item(i);
				this.background.put(backgroundItem.getAttribute("id"),
						new StatisticalSummaryValues(
								new Double(backgroundItem.getAttribute("mean")),
								new Double(backgroundItem.getAttribute("variance")),
								new Long(backgroundItem.getAttribute("n")),
								new Double(backgroundItem.getAttribute("max")),
								new Double(backgroundItem.getAttribute("min")),
								new Double(backgroundItem.getAttribute("sum"))
								));
			} catch (Exception e) {
				// catching text nodes
			}
		}
	}
	
	/**
	 * @param bed
	 * @return
	 * @throws Exception
	 */
	public StatisticalResultSet zScoreTest(BedItem bed) throws Exception {
		
		if( this.genome == null) throw new Exception("Please run compute() before testing");
		StatisticalResultSet resultSet = new StatisticalResultSet();
		
		/* 
		 * Computation of z-score statistics is computed in parallel using all available threads
		 * The Executor Service waits for all tasks to finish for 10 seconds
		 * If one of the tasks is not finished error will be logged
		 */
		ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		int counter = 0;
		long time = System.currentTimeMillis(), startTime = time;
		try {
			//logger.trace("Calculating stats using " + Runtime.getRuntime().availableProcessors() + " processors");
			for(BigWig track : this.genome.getBigWigTracks()) {
				StatisticalSummary params = background.get(track.getId());
				if (params != null) {
					counter++;
					StatisticalSummary stat = background.get(track.getId());
					exec.submit(new StatCalculator(resultSet, bed, track, stat));
				}
				else
					logger.warn("No statistic for: " + track.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//logger.trace("All tasks submitted");
			exec.shutdown();
		}
		try {
			time = System.currentTimeMillis();
			long awaitTime = time;
			exec.awaitTermination(10, TimeUnit.SECONDS);
			time = System.currentTimeMillis();
			logger.info("Finished calculation in " + 
			(time - startTime) + " ms" + 
			", waited for tasks to finish " + 
			(time - awaitTime) + " ms");
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
		if (resultSet.getResult().size() != counter) {
			double percentDone = 100d * (double) resultSet.getResult().size() / (double) counter;
			logger.error("Done: " + percentDone + "% ; number of results: " + resultSet.getResult().size());
		}
		return resultSet;
	}

	public StatisticalSummary getStatisticalSummaryForTrack(String trackID) {
		return this.background.get(trackID);
	}
	
	public StatisticalResult tTest(Background query, BigWig track) {
		
		String trackID = track.getId();
		StatisticalSummary baseParams = this.background.get(trackID);
		StatisticalSummary queryParams = query.background.get(trackID);
		
		double pValue = 1;
		try {pValue = TestUtils.homoscedasticTTest(queryParams, baseParams);
		} catch (Exception e) {logger.error("Could not calculate t-test for: " + trackID);}
		
		StatisticalResult result = new StatisticalResult();
		result.setId(trackID);
		result.setName(track.getName());
		result.setPValue(pValue);
		result.setFoldChange(queryParams.getMean() / baseParams.getMean());

		return result;
	}
	
	public StatisticalResultSet tTest(Background querySet) throws Exception {
		
		StatisticalResultSet resultSet = new StatisticalResultSet();
		
		for(String trackID : this.background.keySet()) {
			StatisticalSummary baseParams = this.background.get(trackID);
			StatisticalSummary queryParams = querySet.background.get(trackID);
			double pValue = 1;
			try {
				pValue = TestUtils.homoscedasticTTest(queryParams, baseParams);
				
			} catch (Exception e) {
				logger.error("Could not calculate t-test for: " + trackID);
			}
			resultSet.put(
					trackID,
					genome.getTrackById(trackID).getName(),
					pValue,
					queryParams.getMean() / baseParams.getMean(), 
					baseParams.getMean(), 
					queryParams.getMean());
		}
		
		return resultSet;
	}
	
	public Background copy() {
		Background background = new Background(this.path);
		background.bedItems = this.bedItems;
		background.background =	new TreeMap <String, StatisticalSummary> (this.background);
		background.genome = this.genome;
		background.name = this.name;
		return background;
	}
	
	public int size() {
		return bedItems.size();
	}
	
	public String getId() {
		String fileName = GenomeStoreFileUtils.getFileName(this.path);
		return fileName.substring(0, fileName.indexOf('.'));
	}

	public Genome getGenome() {
		return genome;
	}

	public void setGenome(Genome genome) {
		this.genome = genome;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List <BedItem> getBedItems() {
		return bedItems;
	}
	
}

/*
 * Class needed to paralellize z-score statistics computing
 */
class StatCalculator implements Runnable {

	private static final Logger logger = Logger.getLogger(StatCalculator.class);
	
	StatisticalResultSet resultSet;
	BedItem bed;
	Double bedLength;
	BigWig track;
	StatisticalSummary params;
	
	StatCalculator(StatisticalResultSet resultSet, BedItem bed, BigWig track, StatisticalSummary params) {
		this.resultSet = resultSet;
		this.bed = bed;
		this.bedLength = (double) bed.getLength();
		this.track = track;
		this.params = params;
	}
	
	@Override
	public void run() {
		//logger.info("Started " + track.getId());
		double value = track.getCoverage(bed).getSum() / bedLength;
   		Double pValue;
		if (value > params.getMean())
			pValue = (2 * (1 - new NormalDistribution(params.getMean(), params.getStandardDeviation()).cumulativeProbability(value)));
		else 
			pValue = (new NormalDistribution(params.getMean(), params.getStandardDeviation()).cumulativeProbability(value) * 2);
		
		resultSet.put(track.getId(), track.getName(), pValue, value / params.getMean(), params.getMean(), value);
		//logger.info("Finished " + track.getId());
	}
	
}