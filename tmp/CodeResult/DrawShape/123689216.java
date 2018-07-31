/*
gwLogViewer - a viewer to display and analyze well log data
This program module Copyright (C) 2006 G&W Systems Consulting Corp. 
and distributed by BHP Billiton Petroleum under license. 

This program is free software; you can redistribute it and/or modify it 
under the terms of the GNU General Public License Version 2 as as published 
by the Free Software Foundation.
 
This program is distributed in the hope that it will be useful, 
but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for 
more details.

You should have received a copy of the GNU General Public License along with 
this program; if not, write to the Free Software Foundation, Inc., 
51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
or visit the link http://www.gnu.org/licenses/gpl.txt.

To contact BHP Billiton about this software you can e-mail info@qiworkbench.org
or visit http://qiworkbench.org to learn more.
*/

package com.gwsys.welllog.view.curve;

import java.util.ArrayList;

import com.gwsys.welllog.core.curve.CaculateClass;
import com.gwsys.welllog.core.curve.ConstantVariableList;
import com.gwsys.welllog.core.curve.TrackTypeList;


/**
 * 
 * @author luming generate p 10 -50 -90 curve
 */
public class CurveP105090Mean {
	private CurveLayer cl;
	private ArrayList curveList = new ArrayList();

	private CurvePanel p10;

	private CurvePanel p50;

	private CurvePanel p90;

	private CurvePanel mean;

	private CaculateClass cc = new CaculateClass();

	public CurveP105090Mean(CurveLayer cl){
		this.cl = cl;
	}
	
	@SuppressWarnings("unchecked")
	public boolean constructP105090Mean() {
		// initial name, no hash name
		CurveData p10Data = new CurveData();
		CurveData p50Data = new CurveData();
		CurveData p90Data = new CurveData();
		CurveData meanData = new CurveData();
		p10Data.setCurveName(ConstantVariableList.P10);
		p10Data.setHashCurveName(ConstantVariableList.P10); 
		p50Data.setCurveName(ConstantVariableList.P50);
		p50Data.setHashCurveName(ConstantVariableList.P50);
		p90Data.setCurveName(ConstantVariableList.P90);
		p90Data.setHashCurveName(ConstantVariableList.P90);
		meanData.setCurveName(ConstantVariableList.MEAN);
		meanData.setHashCurveName(ConstantVariableList.MEAN);

		// initial y
		// first, get the max miny and the min maxy of curves
		float theMaxMin = 0; // the max miny
		float theMinMax = 1000000; // the min maxy;
		int curveNumber = this.getCurveList().size();
		float depthIncrement = 0;
		for (int i = 0; i < curveNumber; i++) {
			CurvePanel cp = (CurvePanel) this.getCurveList().get(i);
			float depth = cp.getValue(cp.getCd().getMinValue(),cp.getCd().getOriginalYType(),TrackTypeList.Y_DEPTH_MD,this.cl.parent);
			if (depth > theMaxMin) {
				theMaxMin = depth;
			}
			depth = cp.getValue(cp.getCd().getMaxValue(),cp.getCd().getOriginalYType(),TrackTypeList.Y_DEPTH_MD,this.cl.parent);
			if (depth < theMinMax) {
				theMinMax = depth;
			}
			if (i == 0) {				
				depthIncrement = cp.getValue(cp.getCd().getDepthStep(),cp.getCd().getOriginalYType(),TrackTypeList.Y_DEPTH_MD,this.cl.parent);
			}
			if(cp.getCd().getDepthStep()!=depthIncrement)
			{
				//if the curves have different depthincrement,return false
				return false;
			}
		}
		p10Data.setDepthStep(depthIncrement);
		p50Data.setDepthStep(depthIncrement);
		p90Data.setDepthStep(depthIncrement);
		meanData.setDepthStep(depthIncrement);
		// cut these curve by the max min segment,and save to the list
		ArrayList dataXArrayList = new ArrayList();

		int arraySize = (int) ((theMinMax - theMaxMin) / depthIncrement) + 1;
		float a = (theMinMax - theMaxMin) % depthIncrement;		
		if (a/depthIncrement > 0.5) {
			arraySize++;
		}
		float[] theY = new float[arraySize];
		for (int i = 0; i < getCurveList().size(); i++) {
			CurvePanel cp = (CurvePanel) this.getCurveList().get(i);
			int k = 0;
			float[] theX = new float[arraySize];
			for (int j = 0; j < cp.getCd().getY().length; j++) {
				if (cp.getCd().getY()[j] >= theMaxMin && cp.getCd().getY()[j] <= theMinMax) {

					if (i == 0) {

						theY[k] = cp.getCd().getY()[j]; // y only need to be initial for
												// once
					}

					theX[k] = cp.getCd().getX()[j];
					
					k++;
				}
			}

			dataXArrayList.add(theX);
		}

		p10Data.setY(theY);
		p50Data.setY(theY);
		p90Data.setY(theY);
		meanData.setY(theY);
		// initial x
		float p10x[] = new float[theY.length];
		float p50x[] = new float[theY.length];
		float p90x[] = new float[theY.length];
		float meanx[] = new float[theY.length];
		for (int i = 0; i < theY.length; i++) // each depth
		{
			float eachX[] = new float[curveNumber];
			for (int ni = 0; ni < curveNumber; ni++) // each curve
			{
				float[] x = (float[]) dataXArrayList.get(ni); // curve x
				eachX[ni] = x[i]; // the same depth , many x
			}
			cc.rankTheArray(eachX);
			p10x[i] = cc.getMin();
			p50x[i] = cc.getMiddle();
			p90x[i] = cc.getMax();
			meanx[i] = cc.getMean();
		}
		p10Data.setX(p10x);
		p50Data.setX(p50x);
		p90Data.setX(p90x);
		meanData.setX(meanx);
		// initial x,y end
		// initial max x , y , min x,y
		cc.rankTheArray(theY);
		p10Data.setMaxValue(cc.getMax());
		p10Data.setMinValue(cc.getMin());
		p50Data.setMaxValue(cc.getMax());
		p50Data.setMinValue(cc.getMin());
		p90Data.setMaxValue(cc.getMax());
		p90Data.setMinValue(cc.getMin());
		meanData.setMaxValue(cc.getMax());
		meanData.setMinValue(cc.getMin());

		cc.rankTheArray(p10x);
		p10Data.setMaxX(cc.getMax());
		p10Data.setMinX(cc.getMin());
		cc.rankTheArray(p50x);
		p50Data.setMaxX(cc.getMax());
		p50Data.setMinX(cc.getMin());
		cc.rankTheArray(p90x);
		p90Data.setMaxX(cc.getMax());
		p90Data.setMinX(cc.getMin());
		cc.rankTheArray(meanx);
		meanData.setMaxX(cc.getMax());
		meanData.setMinX(cc.getMin());

		p10 = new CurvePanel(p10Data);
		p50 = new CurvePanel(p50Data);
		p90 = new CurvePanel(p90Data);
		mean = new CurvePanel(meanData);
		
		p10.setOriginalCurve(OperationClass.drawShape(p10Data.getX(), p10Data.getY()));
		p50.setOriginalCurve(OperationClass.drawShape(p50Data.getX(), p50Data.getY()));
		p90.setOriginalCurve(OperationClass.drawShape(p90Data.getX(), p90Data.getY()));
		mean.setOriginalCurve(OperationClass
				.drawShape(meanData.getX(), meanData.getY()));
		return true;
	}

	public ArrayList getCurveList() {
		return curveList;
	}

	public void setCurveList(ArrayList curveList) {
		this.curveList = curveList;
	}

	public CurvePanel getP10() {
		return p10;
	}

	public CurvePanel getP50() {
		return p50;
	}

	public CurvePanel getP90() {
		return p90;
	}

	public CurvePanel getMean() {
		return mean;
	}
}
