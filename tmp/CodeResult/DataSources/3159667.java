/*
 * @(#)DataSources.java
 * 
 * Copyright 2011 MBARI
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */



package org.mbari.dss.client.jd.model;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

public class DataSources {

//  http://coastwatch.pfeg.noaa.gov/erddap/griddap/erdSHchla1day.kml?chlorophyll[(2006-03-14T12:00:00Z):1:(2006-03-14T12:00:00Z)][(0.0):1:(0.0)][(36.5):1:(37.1)][(237.5):1:(238.3)]
    ArrayList<FunctionalityInfo> secondarySites = new ArrayList<FunctionalityInfo>();

    public DataSources() {
        secondarySites.add(new FunctionalityInfo("Chlorophyll-a (8 day)"));
        secondarySites.add(new FunctionalityInfo("AVHRR (8 day)"));
        secondarySites.add(new FunctionalityInfo("HF Radar (25 hr avg)"));

//      secondarySites.add(new FunctionalityInfo("MERIS (TBD)"));
//      secondarySites.add(new FunctionalityInfo("Provenance (TBD)"));
    }
}
