package au.edu.mq.comp.junitGrading.grader;

import java.io.FileNotFoundException;
import java.io.IOException;


import org.ini4j.*;

import au.edu.mq.comp.common.Log;
import au.edu.mq.comp.common.OS;
import au.edu.mq.comp.common.SimpleFileIO;
import au.edu.mq.comp.common.concurrency.WorkGroup;
import au.edu.mq.comp.junitGrading.Assignment;
import au.edu.mq.comp.junitGrading.CSVFormatter;
import au.edu.mq.comp.junitGrading.CollectionOfAssignments;
import au.edu.mq.comp.junitGrading.GlobalSetting;
import au.edu.mq.comp.junitGrading.MQiLearnIdentificationTokenizer;
import au.edu.mq.comp.junitGrading.TableDataFormatter;
import au.edu.mq.comp.junitGrading.TestResult;
import au.edu.mq.comp.junitGrading.GraderWorker;


public class Automark extends Object
{
    public class Configuration extends Object
    {
        private int numberOfWorkers = 2;
        private boolean isSingleFileAssignment = false;
        private String targetClassNameToTest = null;
        private String junitTestClassName = "noname";
        private String outputDirectoryPath = "";
        private String pathToAssigments = "";
        private java.util.List<String> junitTestSourceFiles;
        private java.util.List<String> neededLibraryFiles;
        private java.util.List<String> neededResourceFiles;

        public Configuration(java.io.File configFile) throws InvalidFileFormatException, FileNotFoundException, IOException
        {
            Ini ini = new Ini();
            ini.load(new java.io.FileReader(configFile));
            java.util.Set<String> sections = ini.keySet();
            if(sections.size() < 1)
            {
                Log.error("there is no junit test class name specified");
                return;
            }

            this.junitTestClassName = sections.iterator().next(); // the first one

            Ini.Section section = ini.get(this.junitTestClassName);
            
            this.pathToAssigments = section.get("pathToAssigments");
            if(null == this.pathToAssigments || 0 == this.pathToAssigments.length())
            	this.pathToAssigments = section.get("assigmentsZip");
            
            this.outputDirectoryPath = section.get("outputDirectory");
            
            if(null == this.outputDirectoryPath) 
                this.outputDirectoryPath = SimpleFileIO.currentWorkingDirectory().getAbsolutePath();
            else if(0 == this.outputDirectoryPath.length())
                this.outputDirectoryPath = SimpleFileIO.currentWorkingDirectory().getAbsolutePath();
            
            this.junitTestSourceFiles = section.getAll("junitTestSource");
            if(null == this.junitTestSourceFiles)
                this.junitTestSourceFiles  = new java.util.ArrayList<String>();
                
            this.neededLibraryFiles = section.getAll("neededLibrary");
            if(null == this.neededLibraryFiles)
                this.neededLibraryFiles  = new java.util.ArrayList<String>();
            
            this.neededResourceFiles = section.getAll("neededResource");
            if(null == this.neededResourceFiles)
                this.neededResourceFiles  = new java.util.ArrayList<String>();
            
            String strTimeout = section.get("testProcessTimeOut");
            if(null == strTimeout)
                GlobalSetting.setProcessWaitTimeout(0);
            else
            {
                try
                {
                    au.edu.mq.comp.common.Log.message("Setting timeout value to " + strTimeout + " sec");
                    GlobalSetting.setProcessWaitTimeout(Long.valueOf(strTimeout));
                }
                catch(NumberFormatException e)
                {
                    au.edu.mq.comp.common.Log.error("error in configuration file testProcessTimeOut is not numeric");
                    GlobalSetting.setProcessWaitTimeout(0); 
                }
            }
            
            String strNWorkers = section.get("numberOfWorkers");
            try
            {
                if(null == strNWorkers)
                    strNWorkers = "2";
                au.edu.mq.comp.common.Log.message("Setting number of workers to " + strNWorkers);
                this.numberOfWorkers = Integer.valueOf(strNWorkers);
            }
            catch(NumberFormatException e)
            {
                this.numberOfWorkers = 2;
            }
            
            String strIsSingleFileAssignment = section.get("isSingleFileAssignment");
            if(null != strIsSingleFileAssignment)
            {
                int indexYes = strIsSingleFileAssignment.toLowerCase().indexOf("yes");
                int indexTrue = strIsSingleFileAssignment.toLowerCase().indexOf("true");
                if(indexYes >= 0 || indexTrue >= 0)
                {
                    this.isSingleFileAssignment = true; 
                    au.edu.mq.comp.common.Log.message("isSingleFileAssignment is set to YES");
                }
            }
            
            this.targetClassNameToTest = section.get("targetClassNameToTest");
        }
        
        public boolean isSingleFileAssignment()
        {
            return this.isSingleFileAssignment;
        }
        
        public String targetClassNameToTest()
        {
            if(null != this.targetClassNameToTest)
                return "";
            else
                return this.targetClassNameToTest;
        }

        public String junitTestClassName()
        {
            return new String(this.junitTestClassName);
        }
        
        public String outputDirectoryPath()
        {
            return new String(this.outputDirectoryPath);
        }
        
        public String pathToAssignments()
        {
            return new String(this.pathToAssigments);
        }
        
        public int numberOfWorkers()
        {
            return this.numberOfWorkers;
        }
        
        public java.util.List<String> neededLibraryFiles()
        {
        	return this.neededLibraryFiles;
        }
        public java.util.List<String> neededResourceFiles()
        {
        	return this.neededResourceFiles;
        }
        
        private java.util.List<String> junitTestSourceFiles()
        {
        	return this.junitTestSourceFiles;
        }

        public boolean checkAllFiles()
        {
            java.io.File f = SimpleFileIO.makeFileFromPath(this.outputDirectoryPath);
            if(false == f.exists())
            {
                Log.error("outputDirectory -> " + this.outputDirectoryPath + "  does NOT exists");
                return false;
            }

            if(false == f.isDirectory())
            {
                Log.error("outputDirectory -> " + this.outputDirectoryPath + "  is NOT a directory");
                return false;
            }    

            if(false == f.canWrite())
            {
                Log.error("outputDirectory -> " + this.outputDirectoryPath + "  cannot be written to");
                return false;
            }

            if(false == f.canRead())
            {
                Log.error("outputDirectory -> " + this.outputDirectoryPath + "  cannot be read");
                return false;
            }

            //////////////////////////////////////////////
            if(0 == this.junitTestSourceFiles.size())
            {
                Log.error("at least one junitTestSource must be specified");
                return false; 
            }
            else
            {
                for(String path : this.junitTestSourceFiles)
                {
                    f = SimpleFileIO.makeFileFromPath(path);
                    if(false == f.canRead())
                    {
                        Log.error(path + " --> cannot be read or does not exist");
                        return false;
                    }
                }
            }

            //////////////////////////////////////////////
            
            if(null != this.neededLibraryFiles)
            {
                for(String path : this.neededLibraryFiles)
                {
                    f = SimpleFileIO.makeFileFromPath(path);
                    if(false == f.canRead())
                    {
                        Log.error(path + " --> cannot be read or does not exist");
                        return false;
                    }
                    
                    else
                    {
                        if(false == SimpleFileIO.extensionOfFileName(f.getName()).equalsIgnoreCase("jar")  )
                        {
                            Log.error(path + " --> is not a jar file, all resources must be in a jar file");
                            return false;
                        }
                    } 
                }
            }

            //////////////////////////////////////////////
            for(String path : this.neededResourceFiles)
            {
                f = SimpleFileIO.makeFileFromPath(path);
                if(false == f.canRead())
                {
                    Log.error(path + " --> cannot be read or does not exist");
                    return false;
                }
                else
                {
                    if(false == SimpleFileIO.extensionOfFileName(f.getName()).equalsIgnoreCase("zip")  )
                    {
                        Log.error(path + " --> is not a zip file, all resources must be in a zip file");
                        return false;
                    }
                }
            }
            //////////////////////////////////////////////
            
            f = SimpleFileIO.makeFileFromPath(this.pathToAssigments);
            if(true == f.isFile())
            {
            	if(false == f.canRead())
            	{
            		Log.error(this.pathToAssigments + " --> cannot be read or does not exist");
            		return false;
            	}
            	else
            	{
            		if(false == SimpleFileIO.extensionOfFileName(f.getName()).equalsIgnoreCase("zip")  )
            		{
            			Log.error(this.pathToAssigments + " --> is not a zip file, assignments file download from iLearn must be in a zip file");
            			return false;
            		}
            	}
            }
            else if(true == f.isDirectory())
            {
            	if(false == f.canRead())
            	{
            		Log.error(this.pathToAssigments + " --> cannot be read or does not exist");
            		return false;
            	}
            	
            	if(false == f.canWrite())
            	{
            		Log.error(this.pathToAssigments + " --> cannot be written or does not exist");
            		return false;
            	}
            	
            }
            
            if(true == this.isSingleFileAssignment)
            {
                
                if(null == this.targetClassNameToTest)
                {
                    au.edu.mq.comp.common.Log.error("isSingleFileAssignment was set to YES, a targetClassNameToTest must be set");
                    return false;
                }
            }


            return true;
        }
    }
    
    /////////////////////////////////////////////////////////////////////////
    
    
    /////////////////////////////////////////////////////////////////////////
    private GraderWorker makeGrader(Automark.Configuration config) throws IOException
    {
    	JavaGrader dAm = new JavaGrader(config.junitTestClassName()); 
        for(String path : config.junitTestSourceFiles()) 
            dAm.addJUnitTestSource(path);

        for(String path : config.neededLibraryFiles())
            dAm.addLibrary(path);

        for(String path : config.neededResourceFiles())
            dAm.addTestDataZipFile(path);

        return dAm;
    	
    }
    
    public String run(java.io.File automarkFile, TableDataFormatter dataFormatter) throws Exception
    {
        Configuration config = new  Configuration(automarkFile);
        if(true == config.checkAllFiles())
        {
            CollectionOfAssignments ca = new CollectionOfAssignments(config.pathToAssignments(), config.outputDirectoryPath());

            if(true == config.isSingleFileAssignment)
                ca.preprocessingSingleFileAssignment(config.targetClassNameToTest);
            
            ca.setIdentificationTokenizer(new MQiLearnIdentificationTokenizer());
            
            WorkGroup<Assignment, TestResult> workGroup = new WorkGroup<Assignment, TestResult>();
    		for(int i = 0; i < config.numberOfWorkers(); i++)
    		    workGroup.addWorker(this.makeGrader(config));
    		
    		while(true == ca.hasMoreAssignment())
    		{
    			Assignment assignment = ca.nextAssignment();
    			if(null != assignment)
    				workGroup.pushWorkData(assignment);
    		}

    		while(0 < workGroup.currentDataCount())
    		{
    			TestResult result = workGroup.popWorkResult();
    			dataFormatter.addRow(result);
    		}
    		
    		return dataFormatter.toString();
        }
        else
        {
        	return "";
        }
    }
    

    //////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) throws Exception 
    {
        //Automark automark = new Automark();
    	//GlobalSetting.setDebugging(true);
        //automark.run(new java.io.File("/Users/psksvp/Desktop/Christophe.test/AutomarkConfigTest.txt"));
        
    	java.security.CodeSource codeSource = Automark.class.getProtectionDomain().getCodeSource();
    	java.io.File jarFile = new java.io.File(codeSource.getLocation().toURI().getPath());
    	System.out.println("Running from jar -> " + jarFile.getAbsolutePath());
    	
        System.out.println("Automark version:" + OS.getCompileTimeStamp(Automark.class));
        if(0 == args.length)
        {
            System.out.println("usage:  Automark configFile");
            return;
        }
        else
        {
        	GlobalSetting.setDebugging(false);
        	System.out.println("goint to run with config file " + args[0]);
        	String pathToAutomarkFile = args[0]; 
        	java.io.File automarkFile = SimpleFileIO.makeFileFromPath(pathToAutomarkFile);
        	if(true == automarkFile.canRead())
        	{
        		Automark automark = new Automark();
        		String csvOutputString = automark.run(automarkFile, new CSVFormatter());
        		if(false == csvOutputString.isEmpty())
        		{
        			SimpleFileIO.writeStringToTextFile(csvOutputString, "Result.csv");
            		Log.message("Wrote output csv file to -->" + "Result.csv");
        		}
        	}
        	else
        	{

        		System.err.println(pathToAutomarkFile + " cannot be read or does not exist");
        		System.err.println("Usage: Automake automarkConfigFile");

        	}   
        }
        
    }

}// class Automark


