package au.edu.mq.comp.junitGrading.grader;

import au.edu.mq.comp.common.SimpleFileIO;
import au.edu.mq.comp.common.concurrency.WorkGroup;
import au.edu.mq.comp.junitGrading.Assignment;
import au.edu.mq.comp.junitGrading.CSVFormatter;
import au.edu.mq.comp.junitGrading.CollectionOfAssignments;
import au.edu.mq.comp.junitGrading.MQiLearnIdentificationTokenizer;
import au.edu.mq.comp.junitGrading.TestResult;

public class APIUsedExample 
{

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

	}
	
	
	public void simpleExample() 
	{
		try 
		{
			// construct our data source
			CollectionOfAssignments ca = new CollectionOfAssignments("comp125Classroom.zip", "");
			//preprocessing comp125Classroom.zip into correct directory structure
			ca.preprocessingSingleFileAssignment("comp125.Classroom"); 
			// this will be used by JavaGrader when construct TestResult
			ca.setIdentificationTokenizer(new MQiLearnIdentificationTokenizer()); 
			// construct our data processor
			JavaGrader grader = new JavaGrader("comp125.ClassroomTest");
			// add the zip of test source with package structure specified as comp125.ClassroomTest
			// The zip MUST have the directory structure like below
			//  comp125+
			//         |
			//         +-ClassroomTest.java
			grader.addJUnitTestSource("comp125.zip");
			
			// start processing(using single thread)
			while(true == ca.hasMoreAssignment())
			{
				TestResult result = grader.gradeAnAssignment(ca.nextAssignment());
				System.out.println(result);
			}
			
			// done
			System.out.println("done");
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void multhreadedExample()
	{
		try 
		{
			// construct our data source
			CollectionOfAssignments ca = new CollectionOfAssignments("comp125Classroom.zip", "");
			//preprocessing comp125Classroom.zip into correct directory structure
			ca.preprocessingSingleFileAssignment("comp125.Classroom"); 
			// this will be used by JavaGrader when construct TestResult
			ca.setIdentificationTokenizer(new MQiLearnIdentificationTokenizer()); 
			// construct our data processor
			JavaGrader grader = new JavaGrader("comp125.ClassroomTest");
			// add the zip of test source with package structure specified as comp125.ClassroomTest
			// The zip MUST have the directory structure like below
			//  comp125+
			//         |
			//         +-ClassroomTest.java
			grader.addJUnitTestSource("comp125.zip");
			
			// start processing(using 4 threads)
			WorkGroup<Assignment, TestResult> workGroup = new WorkGroup<Assignment, TestResult>();
    		workGroup.addWorker(grader);
    		workGroup.addWorker(grader.clone());
    		workGroup.addWorker(grader.clone());
    		workGroup.addWorker(grader.clone());
    		
    		//pushing assignments into workgroup
    		while(true == ca.hasMoreAssignment())
    		{
    			Assignment assignment = ca.nextAssignment();
    			if(null != assignment)
    				workGroup.pushWorkData(assignment);
    		}

    		CSVFormatter dataFormatter = new CSVFormatter();
    		//keeping getting the TestResult until done
    		while(0 < workGroup.currentDataCount())
    		{
    			TestResult result = workGroup.popWorkResult();
    			dataFormatter.addRow(result);
    		}
    		
    		//write result.csv
    		SimpleFileIO.writeStringToTextFile(dataFormatter.toString(), "Result.csv");
			
			// done
			System.out.println("done");
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
