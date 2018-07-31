/********************************************************************************
 *	Copyright 2011 Gerry Weirich
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *	$Id$
 ********************************************************************************/

package ch.rgw.cybert.persist;

import java.util.List;

import ch.rgw.cybert.Quant;

/**
 * decouple the Scala classes from the needs of the java wrapper ektorp
 * 
 * @author gerry
 * 
 */
class QuantWrapper {
	private int frequency;
	private int questionID;
	private float sum;
	private String creator;
	private int phase;
	private float npv;
	private float ppv;
	private List<String> cluster;
	
	public List<String> getEnvironment(){
		return cluster;
	}
	
	public void setEnvironment(List<String> cluster){
		this.cluster = cluster;
	}
	
	public float getNpv(){
		return npv;
	}
	
	public void setNpv(float npv){
		this.npv = npv;
	}
	
	public float getPpv(){
		return ppv;
	}
	
	public void setPpv(float ppv){
		this.ppv = ppv;
	}
	
	public int getPhase(){
		return phase;
	}
	
	public void setPhase(int phase){
		this.phase = phase;
	}
	
	void merge(QuantWrapper other){

	}
	
	public String getCreator(){
		return creator;
	}
	
	public void setCreator(String creator){
		this.creator = creator;
	}
	
	public int getFrequency(){
		return frequency;
	}
	
	public void setFrequency(int frequency){
		this.frequency = frequency;
	}
	
	public int getQuestionID(){
		return questionID;
	}
	
	public void setQuestionID(int questionID){
		this.questionID = questionID;
	}
	
	public float getSum(){
		return sum;
	}
	
	public void setSum(float sum){
		this.sum = sum;
	}
	
	QuantWrapper(){}
	
	QuantWrapper(Quant quant){
		frequency = quant.getFrequency();
		questionID = quant.getQuestionID();
		sum = quant.getSum();
		phase = quant.getPhase();
		creator = quant.getCreator();
		ppv = quant.getPpv();
		npv = quant.getNpv();
		cluster = quant.getCluster();
	}
	
	Quant createQuant(){
		Quant ret = new Quant(questionID, creator, phase);
		ret.setFrequency(getFrequency());
		ret.setSum(getSum());
		ret.setPpv(ppv);
		ret.setNpv(npv);
		if (cluster != null) {
			ret.setCluster(cluster);
		}
		return ret;
	}
}
