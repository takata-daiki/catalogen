package com.disney.business.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Rate implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer Id;
	private Integer day;
	private Date date;
	private BigDecimal rate;
	private String type;
	
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Rate [Id=" + Id + ", day=" + day + ", date=" + date + ", rate="
				+ rate + ", type=" + type + "]";
	}
		
	
	
	

}
