package com.defshare.crm.po;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Emp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Emp", schema = "dbo", catalog = "CRM", uniqueConstraints = @UniqueConstraint(columnNames = "ENO"))
public class Emp extends com.defshare.crm.po.CRMEntity implements
		java.io.Serializable {

	// Fields

	private Long empId;
	private Dept dept;
	private Role role;
	private String eno;
	private String ename;
	private String esex;
	private String eaddress;
	private String emobile;
	private String ecardId;
	private String eeduLevel;
	private String epwd;
	private Timestamp edate;
	private Integer estate;
	private String ephoto;
	private String eremark;
	private Set<Customer> customers = new HashSet<Customer>(0);
	private Set<SellRecord> sellRecords = new HashSet<SellRecord>(0);
	private Set<CallBoard> callBoards = new HashSet<CallBoard>(0);
	private Set<FeedbackRecord> feedbackRecords = new HashSet<FeedbackRecord>(0);
	private Set<ContactRecord> contactRecords = new HashSet<ContactRecord>(0);
	private Set<Goods> goodses = new HashSet<Goods>(0);
	private Set<ContactTimeLimit> contactTimeLimits = new HashSet<ContactTimeLimit>(
			0);

	// Constructors

	/** default constructor */
	public Emp() {
	}

	/** full constructor */
	public Emp(Dept dept, Role role, String eno, String ename, String esex,
			String eaddress, String emobile, String ecardId, String eeduLevel,
			String epwd, Timestamp edate, Integer estate, String ephoto,
			String eremark, Set<Customer> customers,
			Set<SellRecord> sellRecords, Set<CallBoard> callBoards,
			Set<FeedbackRecord> feedbackRecords,
			Set<ContactRecord> contactRecords, Set<Goods> goodses,
			Set<ContactTimeLimit> contactTimeLimits) {
		this.dept = dept;
		this.role = role;
		this.eno = eno;
		this.ename = ename;
		this.esex = esex;
		this.eaddress = eaddress;
		this.emobile = emobile;
		this.ecardId = ecardId;
		this.eeduLevel = eeduLevel;
		this.epwd = epwd;
		this.edate = edate;
		this.estate = estate;
		this.ephoto = ephoto;
		this.eremark = eremark;
		this.customers = customers;
		this.sellRecords = sellRecords;
		this.callBoards = callBoards;
		this.feedbackRecords = feedbackRecords;
		this.contactRecords = contactRecords;
		this.goodses = goodses;
		this.contactTimeLimits = contactTimeLimits;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "EmpID", unique = true, nullable = false)
	public Long getEmpId() {
		return this.empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DeptID")
	public Dept getDept() {
		return this.dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RoleID")
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Column(name = "ENO", unique = true, length = 64)
	public String getEno() {
		return this.eno;
	}

	public void setEno(String eno) {
		this.eno = eno;
	}

	@Column(name = "EName", length = 32)
	public String getEname() {
		return this.ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	@Column(name = "ESex", length = 2)
	public String getEsex() {
		return this.esex;
	}

	public void setEsex(String esex) {
		this.esex = esex;
	}

	@Column(name = "EAddress", length = 128)
	public String getEaddress() {
		return this.eaddress;
	}

	public void setEaddress(String eaddress) {
		this.eaddress = eaddress;
	}

	@Column(name = "EMobile", length = 64)
	public String getEmobile() {
		return this.emobile;
	}

	public void setEmobile(String emobile) {
		this.emobile = emobile;
	}

	@Column(name = "ECardID", length = 32)
	public String getEcardId() {
		return this.ecardId;
	}

	public void setEcardId(String ecardId) {
		this.ecardId = ecardId;
	}

	@Column(name = "EEduLevel", length = 32)
	public String getEeduLevel() {
		return this.eeduLevel;
	}

	public void setEeduLevel(String eeduLevel) {
		this.eeduLevel = eeduLevel;
	}

	@Column(name = "EPWD", length = 32)
	public String getEpwd() {
		return this.epwd;
	}

	public void setEpwd(String epwd) {
		this.epwd = epwd;
	}

	@Column(name = "EDate", length = 23)
	public Timestamp getEdate() {
		return this.edate;
	}

	public void setEdate(Timestamp edate) {
		this.edate = edate;
	}

	@Column(name = "EState")
	public Integer getEstate() {
		return this.estate;
	}

	public void setEstate(Integer estate) {
		this.estate = estate;
	}

	@Column(name = "EPhoto", length = 256)
	public String getEphoto() {
		return this.ephoto;
	}

	public void setEphoto(String ephoto) {
		this.ephoto = ephoto;
	}

	@Column(name = "ERemark", length = 256)
	public String getEremark() {
		return this.eremark;
	}

	public void setEremark(String eremark) {
		this.eremark = eremark;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "emp")
	public Set<Customer> getCustomers() {
		return this.customers;
	}

	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "emp")
	public Set<SellRecord> getSellRecords() {
		return this.sellRecords;
	}

	public void setSellRecords(Set<SellRecord> sellRecords) {
		this.sellRecords = sellRecords;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "emp")
	public Set<CallBoard> getCallBoards() {
		return this.callBoards;
	}

	public void setCallBoards(Set<CallBoard> callBoards) {
		this.callBoards = callBoards;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "emp")
	public Set<FeedbackRecord> getFeedbackRecords() {
		return this.feedbackRecords;
	}

	public void setFeedbackRecords(Set<FeedbackRecord> feedbackRecords) {
		this.feedbackRecords = feedbackRecords;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "emp")
	public Set<ContactRecord> getContactRecords() {
		return this.contactRecords;
	}

	public void setContactRecords(Set<ContactRecord> contactRecords) {
		this.contactRecords = contactRecords;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "emp")
	public Set<Goods> getGoodses() {
		return this.goodses;
	}

	public void setGoodses(Set<Goods> goodses) {
		this.goodses = goodses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "emp")
	public Set<ContactTimeLimit> getContactTimeLimits() {
		return this.contactTimeLimits;
	}

	public void setContactTimeLimits(Set<ContactTimeLimit> contactTimeLimits) {
		this.contactTimeLimits = contactTimeLimits;
	}

}