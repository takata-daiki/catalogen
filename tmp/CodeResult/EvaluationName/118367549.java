package teammates.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import teammates.testing.config.Config;
import teammates.testing.lib.BrowserInstance;
import teammates.testing.lib.BrowserInstancePool;
import teammates.testing.lib.TMAPI;
import teammates.testing.object.Scenario;
import teammates.testing.object.Student;

/**
 * Coordinator ready to publish results.
 * 
 * @author huy
 * 
 */
public class TestCoordPublishResultsConcurrent extends BaseTest2 {
	
	static BrowserInstance bi;
	static Scenario scn = setupScenarioInstance("scenario");

	@BeforeClass
	public static void classSetup() throws IOException {
		System.out.println("=== TestCoordPublishResultsConcurrent");
		
		bi = BrowserInstancePool.request();
		
		TMAPI.cleanupCourse(scn.course.courseId);
		TMAPI.createCourse(scn.course);
		TMAPI.enrollStudents(scn.course.courseId, scn.students);
		TMAPI.createEvaluation(scn.evaluation);
		TMAPI.studentsJoinCourse(scn.students, scn.course.courseId);
		TMAPI.openEvaluation(scn.course.courseId, scn.evaluation.name);
		TMAPI.studentsSubmitFeedbacks(scn.students, scn.course.courseId, scn.evaluation.name);
		TMAPI.closeEvaluation(scn.course.courseId, scn.evaluation.name);

		bi.coordinatorLogin(scn.coordinator.username, scn.coordinator.password);
	}

	@AfterClass
	public static void classTearDown() throws Exception {
		TMAPI.cleanupCourse(scn.course.courseId);
		BrowserInstancePool.release(bi);
		System.out.println("=== /TestCoordPublishResultsConcurrent");
	}

	@Test
	public void testPublishResults() throws Exception {
		System.out.println("Test: Coordinator publishes results");

		bi.gotoEvaluations();
		bi.clickEvaluationPublish(scn.evaluation.name);

		bi.waitForElementText(bi.statusMessage, "The evaluation has been published.");

		// Check for status: PUBLISHED
		assertEquals("PUBLISHED", bi.getEvaluationStatus(scn.evaluation.name));

		bi.waitAWhile(5000);
		System.out.println("Checking students' emails to see if they're sent.");
		// Check if emails have been sent to all participants
		for (Student s : scn.students) {
			System.out.println("Checking " + s.email);
			assertTrue(checkResultEmailsSent(s.email, s.password, scn.course.courseId, scn.evaluation.name));
		}
	}

	@Test
	public void testUnpublishResults() throws Exception {
		System.out.println("Test: Unpublishing results.");
		bi.gotoEvaluations();

		bi.clickEvaluationUnpublish(scn.evaluation.name);

		bi.waitForElementText(bi.statusMessage, "The evaluation has been unpublished.");

		// Check for status: PUBLISHED
		assertEquals("CLOSED", bi.getEvaluationStatus(scn.evaluation.name));
	}

	public static boolean checkResultEmailsSent(String gmail, String password,
			String courseCode, String evaluationName)
			throws MessagingException, IOException {

		// Publish RESULTS Format
		final String HEADER_EVALUATION_PUBLISH = "TEAMMATES: Evaluation Published: %s %s";
		final String TEAMMATES_APP_URL = "You can view the result here: "
				+ Config.inst().TEAMMATES_LIVE_SITE;
		final String TEAMMATES_APP_SIGNATURE = "If you encounter any problems using the system, email TEAMMATES support";

		Session sessioned = Session.getDefaultInstance(System.getProperties(), null);
		Store store = sessioned.getStore("imaps");
		store.connect("imap.gmail.com", gmail, password);

		// Retrieve the "Inbox"
		Folder inbox = store.getFolder("inbox");
		// Reading the Email Index in Read / Write Mode
		inbox.open(Folder.READ_WRITE);
		FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
		Message messages[] = inbox.search(ft);
		System.out.println(messages.length + " unread message");

		// Loop over all of the messages
		for (int i = messages.length - 1; i >= 0; i--) {
			Message message = messages[i];
			System.out.println(message.getSubject());

			System.out.println(String.format(HEADER_EVALUATION_PUBLISH,
					courseCode, evaluationName));
			// matching email subject:
			if (!message.getSubject().equals(String.format(HEADER_EVALUATION_PUBLISH, courseCode, evaluationName))) {
				continue;
			} else {
				System.out.println("match");
			}

			// matching email content:
			String body = "";
			if (message.getContent() instanceof String) {
				body = message.getContent().toString();
			} else if (message.getContent() instanceof Multipart) {
				Multipart multipart = (Multipart) message.getContent();
				BodyPart bodypart = multipart.getBodyPart(0);
				body = bodypart.getContent().toString();
			}

			// check line 1: "The results of the evaluation:"
			if (body.indexOf("The results of the evaluation:") == -1) {
				System.out.println("fail 1");
				continue;
			}
			// check line 2: courseCode evaluationName
			if (body.indexOf(body.indexOf(courseCode + " " + evaluationName)) == -1) {
				System.out.println("fail 2");
				continue;
			}
			// check line 3: "have been published."
			if (body.indexOf("have been published.") == -1) {
				System.out.println("fail 3");
				continue;
			}
			// check line 4: "You can view the result here: [URL]"
			if (body.indexOf(TEAMMATES_APP_URL) == -1) {
				System.out.println("fail 4");
				continue;

			}
			// check line 5: teammates signature
			if (body.indexOf(TEAMMATES_APP_SIGNATURE) == -1) {
				System.out.println("fail 5");
				continue;

			}

			// Mark the message as read
			message.setFlag(Flags.Flag.SEEN, true);

			return true;
		}
		return false;
	}
}