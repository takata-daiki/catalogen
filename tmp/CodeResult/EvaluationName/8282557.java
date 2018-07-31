package teammates;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * Email handles all operations with regards to sending e-mails.
 * 
 * @author Gerald GOH
 *
 */
public class Emails 
{
	private String from;
	private Properties props;
	
	/**
	 * Constructs an Email object. Sets the sender's e-mail address and instantiate a new Properties
	 * object.
	 * 
	 */
	public Emails()
	{
		from = "app.teammates@gmail.com";
		props = new Properties();
	}
	
	/**
	 * Sends an email to a Student informing him of new Evaluation details.
	 * 
	 * @param email	the email of the student (Precondition: Must not be null)
	 * 
	 * @param studentName	the name of the student (Precondition: Must not be null)
	 * 
	 * @param courseID	the course ID (Precondition: Must not be null)
	 * 
	 * @param evaluationName	the evaluation name (Precondition: Must not be null)
	 * 
	 * @param instructions	the evaluation instructions (Precondition: Must not be null)
	 * 
	 * @param deadline	the evaluation deadline (Precondition: Must not be null)
	 */
	public void informStudentsOfEvaluationChanges(String email,
			String studentName, String courseID, String evaluationName,
			String instructions, String start, String deadline) 
	{
		try
		{
			Session session = Session.getDefaultInstance(props, null); 
			MimeMessage message = new MimeMessage(session); 
		
			message.addRecipient(Message.RecipientType.TO,  new InternetAddress(email));
		
			message.setFrom(new InternetAddress(from));
			message.setSubject("Teammates Evaluation - Changes"); 
			message.setText("Dear " + studentName + ",\n\n" +
					"There are changes to the evaluation, \n\n" +
					courseID + " " + evaluationName + "\n\n" +  
					"made by your coordinator. The start, deadline and instructions of the evaluation are as follow, \n\n" +
					"Start: " + start + "H. \n\n" +
					"Deadline: " + deadline + "H. \n\n" +
					"Instructions : " + instructions + "\n\nRegards,\nTeammates Administrator");
			
			Transport.send(message);
					
		}
		
		catch (MessagingException e)
		{
			
		}
		
	}
	
	/**
	 * Sends an email to a Student informing him of the opening of an evaluation.
	
	 * @param email	the email of the student (Precondition: Must not be null)
	 * 
	 * @param studentName	the name of the student (Precondition: Must not be null)
	 * 
	 * @param courseID	the course ID (Precondition: Must not be null)
	 * 
	 * @param evaluationName	the evaluation name (Precondition: Must not be null)
	 */
	public void informStudentsOfEvaluationOpening(String email,
			String studentName, String courseID, String evaluationName) 
	{
		try
		{
			Session session = Session.getDefaultInstance(props, null); 
			MimeMessage message = new MimeMessage(session); 
		
			message.addRecipient(Message.RecipientType.TO,  new InternetAddress(email));
		
			message.setFrom(new InternetAddress(from));
			message.setSubject("Teammates Evaluation - Opening"); 
			message.setText("Dear " + studentName + ",\n\n" +
					"The following evaluation, \n\n" +
					courseID + " " + evaluationName + "\n\n" +  
					"is now open. Please do your submission through the Teammates website." + 
					"\n\nRegards,\nTeammates Administrator");
			
			Transport.send(message);
					
		}
		
		catch (MessagingException e)
		{
			
		}
		
	}

	
	/**
	 * Sends an email to a Student informing him of the publishing of results for a particular evaluation.
	
	 * @param email	the email of the student (Precondition: Must not be null)
	 * 
	 * @param studentName	the name of the student (Precondition: Must not be null)
	 * 
	 * @param courseID	the course ID (Precondition: Must not be null)
	 * 
	 * @param evaluationName	the evaluation name (Precondition: Must not be null)
	 */
	public void informStudentsOfPublishedEvaluation(String email,
			String studentName, String courseID, String evaluationName) 
	{
		try
		{
			Session session = Session.getDefaultInstance(props, null); 
			MimeMessage message = new MimeMessage(session); 
		
			message.addRecipient(Message.RecipientType.TO,  new InternetAddress(email));
		
			message.setFrom(new InternetAddress(from));
			message.setSubject("Teammates Evaluation - Results Published"); 
			message.setText("Dear " + studentName + ",\n\n" +
					"The results of the evaluation, \n\n" +
					courseID + " " + evaluationName + "\n\n" +  
					"have been published." + 
					"\n\nRegards,\nTeammates Administrator");
			
			Transport.send(message);
					
		}
		
		catch (MessagingException e)
		{
			
		}
		
	}

	
	/**
	 * Sends an email reminding the Student of the Evaluation deadline.
	 * 
	 * @param email	the email of the student (Precondition: Must not be null)
	 * 
	 * @param studentName	the name of the student (Precondition: Must not be null)
	 * 
	 * @param courseID	the course ID (Precondition: Must not be null)
	 * 
	 * @param evaluationName the evaluation name (Precondition: Must not be null)
	 * 
	 * @param deadline the evaluation deadline (Precondition: Must not be null)
	 */
	public void remindStudent(String email, String studentName, String courseID, String evaluationName, 
			String deadline) 
	{
		try
		{
			Session session = Session.getDefaultInstance(props, null); 
			MimeMessage message = new MimeMessage(session); 
		
			message.addRecipient(Message.RecipientType.TO,  new InternetAddress(email));
		
			message.setFrom(new InternetAddress(from));
			message.setSubject("Teammates Evaluation - Reminder"); 
			message.setText("Dear " + studentName + ",\n\n" +
					"You are reminded to submit the evaluation, \n\n" +
					courseID + " " + evaluationName + "\n\n" +  
					"by " + deadline + "H.\n\nRegards,\nTeammates Administrator");
			
			Transport.send(message);
					
		}
		
		catch (MessagingException e)
		{
			
		}
	}
	
	/**
	 * Sends a registration key to an e-mail address.
	 * 
	 * Pre-conditions: email, registrationKey, studentName, courseID, courseName and coordinatorName must not be null.
	 * Post-condition: The specified registrationKey is sent to the specified email.
	 * 
	 * @param email
	 * @param registrationKey
	 */
	public void sendRegistrationKey(String email, String registrationKey, String studentName, String courseID, 
			String courseName, String coordinatorName)
	{
		try
		{
			Session session = Session.getDefaultInstance(props, null); 
			MimeMessage message = new MimeMessage(session); 
			
			message.addRecipient(Message.RecipientType.TO,  new InternetAddress(email));
			
			message.setFrom(new InternetAddress(from));
			message.setSubject("Welcome to Teammates - New Course " + courseID); 
			message.setText("Dear " + studentName + ",\n\n" +
					"You have been given permission to join\n\n" + courseID + " " + courseName + "\n\n" +
					"on Teammates, an online peer evaluation system. This is how you can join the course -\n\n" +
					"1) Go to http://teammatesevaluation.appspot.com\n" +
					"2) Click \"STUDENT\" and login using your Google ID\n" +
					"3) Enter this key to join the course: " + registrationKey + "\n\n" +
					"The course should then be added on your screen. If you should have any problems, please contact your " +
					"course coordinator, " + coordinatorName + ", for assistance.\n\n" +
					"Regards,\nTeammates Administrator");
			
			Transport.send(message);
		}
		
		catch (MessagingException e)
		{
			
		}
	}

	
}