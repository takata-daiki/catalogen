package com.vdm.starlight.shared.objects;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "WORKBOOK")
public class Workbook implements Serializable, Comparable<Workbook> {
	private static final long serialVersionUID = 548061226837832993L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;

	private String description;

	private Date dateCreated;

	private Date dateModified;

	private String ageGroup;

	private int[] pageOrder;

	@ManyToOne
	private Educator creator;

	/**
	 * Indicates if the design of this work is completed such that it can be assigned to students
	 */
	private boolean isFinished;

	@OneToMany(cascade = CascadeType.ALL)
	private Set<Workpage> workpages;

	@OneToMany(mappedBy = "workbook")
	private Set<StudentWorkbook> studentWorkbooks;

	@ManyToMany(mappedBy = "workbooks")
	private Set<Student> students;

	@OneToMany(mappedBy = "workbook")
	private Set<ClassroomWorkbook> classroomWorkbooks;

	@ManyToMany(mappedBy = "workbooks")
	private Set<Classroom> classrooms;

	private boolean defaultImage;

	@ManyToOne
	private Resource image;

	// Constructors
	public Workbook() {
	}

	public Workbook(String name, String description, Educator educator) {
		this.name = name;
		this.description = description;
		this.creator = educator;
		this.dateCreated = new java.util.Date();
		this.dateModified = this.dateCreated;
		this.isFinished = false;
		this.ageGroup = "";
		this.defaultImage = true;

	}

	// Id getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// Name getter and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Is Ready for practice
	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	// Age getter and setter
	public String getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(String agegroup) {
		this.ageGroup = agegroup;
	}

	// Creator getter and setters
	public Educator getCreator() {
		return creator;
	}

	public void setCreator(Educator educator) {
		this.creator = educator;
	}

	// Date created getter and setters
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date date) {
		this.dateCreated = date;
	}

	// Date created getter and setters
	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date date) {
		this.dateModified = date;
	}

	// Description getter and setters
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// Image getter and setter
	public Resource getImage() {
		return image;
	}

	public void setImage(Resource image) {
		defaultImage = false;
		this.image = image;
	}

	// default image getter and setter
	public boolean isDefaultImage() {
		return defaultImage;
	}

	public void setDefaultImage(boolean defaultImage) {
		this.defaultImage = defaultImage;
	}

	// Workpage getter and setters
	public void addWorkPage(Workpage workpage) {
		if (this.workpages == null)
			this.workpages = new HashSet<Workpage>();
		this.workpages.add(workpage);
	}

	public Set<Workpage> getWorkpages() {
		return workpages;
	}

	public void setWorkpages(Set<Workpage> workpages) {
		this.workpages = workpages;
	}

	// Student getter and setters
	public void addStudent(Student s, StudentWorkbook sw) {
		if (students == null)
			students = new HashSet<Student>();

		if (studentWorkbooks == null) {
			studentWorkbooks = new HashSet<StudentWorkbook>();
		}
		students.add(s);
		studentWorkbooks.add(sw);
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	// Student workbook getter and setters
	public Set<StudentWorkbook> getStudentWorkbooks() {
		return studentWorkbooks;
	}

	public void addStudentWorkbook(StudentWorkbook studentWorkbook) {
		if (studentWorkbooks == null) {
			studentWorkbooks = new HashSet<StudentWorkbook>();
		}
		studentWorkbooks.add(studentWorkbook);
		if (!students.contains(studentWorkbook.getStudent())) {
			students.add(studentWorkbook.getStudent());
		}
	}

	public void setStudentWorkbooks(Set<StudentWorkbook> studentWorkbooks) {
		this.studentWorkbooks = studentWorkbooks;
	}

	public StudentWorkbook getStudentWorkbook(int studentId) {
		for (StudentWorkbook sw : studentWorkbooks) {
			if (sw.getStudent().getId() == studentId) {
				return sw;
			}

		}
		return null;
	}

	// Classroom

	public void addClassroom(Classroom s) {
		if (classrooms == null)
			classrooms = new HashSet<Classroom>();

		if (classroomWorkbooks == null) {
			classroomWorkbooks = new HashSet<ClassroomWorkbook>();
		}
		classrooms.add(s);
		classroomWorkbooks.add(new ClassroomWorkbook(s, this));
		s.addWorkbook(this);
	}

	public Set<Classroom> getClassrooms() {
		return classrooms;
	}

	public void setClassrooms(Set<Classroom> classrooms) {
		this.classrooms = classrooms;
	}

	// Classroom workbook getter and setters
	public Set<ClassroomWorkbook> getClassroomWorkbooks() {
		return classroomWorkbooks;
	}

	public void addClassroomWorkbook(ClassroomWorkbook classroomsWorkbook) {
		if (classroomWorkbooks == null) {
			classroomWorkbooks = new HashSet<ClassroomWorkbook>();
		}
		classroomWorkbooks.add(classroomsWorkbook);
		if (!classrooms.contains(classroomsWorkbook.getClassroom())) {
			classrooms.add(classroomsWorkbook.getClassroom());
		}
	}

	public void setClassroomWorkbooks(Set<ClassroomWorkbook> classroomWorkbooks) {
		this.classroomWorkbooks = classroomWorkbooks;
	}

	// Override methods
	@Override
	public String toString() {
		return "WorkBook [id=" + id + ", name=" + name + ", description=" + description + " ]";
	}

	@Override
	public int compareTo(Workbook o) {
		return -(o.getId() - id);
	}

	@Override
	public boolean equals(Object obj) {

		return getId() == ((Workbook) obj).getId();
	}

}
