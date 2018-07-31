package teammates.jdo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

/**
 * Submission is a persistent data class that contains an evaluation submission
 * from a student to another student. 
 * 
 * @author Gerald GOH
 *
 */
@PersistenceCapable
public class Submission 
{
	@SuppressWarnings("unused")
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
	
	@Persistent
	String fromStudent;
	
	@Persistent
	String toStudent;
	
	@Persistent
	String fromStudentName;
	
	@Persistent
	String toStudentName;
	
	
	@Persistent
	String courseID;
	
	@Persistent
	String evaluationName;
	
	@Persistent
	int points;
	
	@Persistent
	Float bumpRatio = null;
	
	@Persistent
	Text justification;
	
	@Persistent
	Text commentsToStudent;
	
	@Persistent
	String teamName;

	public Submission(String fromStudent, String toStudent, String fromName, String toName, String courseID, String evaluationName, String teamName)
	{
		this.setFromStudent(fromStudent);
		this.setToStudent(toStudent);
		this.setCourseID(courseID);
		this.setEvaluationName(evaluationName);
		this.setTeamName(teamName);
		this.setFromStudentName(fromName);
		this.setToStudentName(toName);
		
		this.setJustification(new Text(""));
		this.setCommentsToStudent(new Text(""));
		this.points = -999;
		this.bumpRatio = new Float(1.0);
	}
	
	public Submission(String fromStudent, String toStudent, String fromName, String toName, String courseID, String evaluationName, 
			String teamName, int points, Text justification, Text commentsToStudent)
	{
		this.setFromStudent(fromStudent);
		this.setToStudent(toStudent);
		this.setCourseID(courseID);
		this.setEvaluationName(evaluationName);
		this.setTeamName(teamName);
		this.setFromStudentName(fromName);
		this.setToStudentName(toName);
		this.setPoints(points);
		this.setJustification(justification);
		this.setCommentsToStudent(commentsToStudent);
	}
	

    public Submission(String fromStudent, String toStudent, String courseID, String evaluationName, 
                    String teamName, int points, Text justification, Text commentsToStudent)
    {
            this.setFromStudent(fromStudent);
            this.setToStudent(toStudent);
            this.setCourseID(courseID);
            this.setEvaluationName(evaluationName);
            this.setTeamName(teamName);
            
            this.setPoints(points);
            this.setJustification(justification);
            this.setCommentsToStudent(commentsToStudent);
    }
	
	/**
	 * Return fromStudent's Email ID
	 */
	public String getFromStudent() {
		return fromStudent;
	}

	public void setFromStudent(String fromStudent) {
		this.fromStudent = fromStudent;
	}

	public String getToStudent() {
		return toStudent;
	}

	public void setToStudent(String toStudent) {
		this.toStudent = toStudent;
	}

	public void setFromStudentName(String fromName) {
		this.fromStudentName = fromName;
	}
	
	public void setBumpRation(float ratio) {
		this.bumpRatio = new Float(ratio);
	}
	
	public Float getPointsBumpRatio() {
		return this.bumpRatio;
	}
	public String getFromStudentName() {
		return this.fromStudentName;
	}
	
	public void setToStudentName(String toName) {
		this.toStudentName = toName;
	}
	
	public String getToStudentName() {
		return this.toStudentName;
	}
	
	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getEvaluationName() {
		return evaluationName;
	}

	public void setEvaluationName(String evaluationName) {
		this.evaluationName = evaluationName;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Text getJustification() {
		return justification;
	}

	public void setJustification(Text justification) {
		this.justification = justification;
	}

	public Text getCommentsToStudent() {
		return commentsToStudent;
	}

	public void setCommentsToStudent(Text commentsToStudent) {
		this.commentsToStudent = commentsToStudent;
	}
	
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.courseID+"|");
		sb.append(this.evaluationName+"|");
		sb.append(this.fromStudent+"|");
		sb.append(this.toStudent+"|");
		sb.append(this.teamName+"\n");
		return sb.toString();
	}
	
}
