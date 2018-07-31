/**
 * Date Created: Mar 21, 2009
 */
package forteresce.portprofile.profiles.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.ini4j.Config;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import forteresce.portprofile.config.enums.SystemPropertiesEnum;
import forteresce.portprofile.profiles.bean.GeneralBean;
import forteresce.portprofile.profiles.bean.ProfileBean;
import forteresce.portprofile.profiles.bean.ProfileBeanImpl;
import forteresce.portprofile.profiles.data.ProfileIniData;

/**
 * This class has methods to manage the profiles such as load,
 * write, add profiles 
 * @author fortersce
 */
public class ProfileUtil {
    
    private static Logger log = Logger.getLogger(ProfileUtil.class);
    private static final String CHARACTERS = "abcdefghijklmnopqrstuwxyz0123456789";

	/**
     * @return ProfileData object for the default system firefox profile
     */
    public static ProfileIniData load() throws Exception {
        return load(SystemUtil.getProfileIniPath());
    }
    
    /**
     * @param profileFilePath for the profile ini file
     * @return ProfileData for the ini file specified
     */
    public static ProfileIniData load(String profileFilePath) throws Exception {
        
        //check the file extension
        if (!profileFilePath.endsWith("ini")) {
            throw new Exception("Please provide a .ini file.");
        }
        
        //open the file and see if it exists
        File profileFile = new File(profileFilePath);
        if(!profileFile.exists() || !profileFile.canRead()) {
            throw new FileNotFoundException("Please provide a valid readable file path: " + profileFilePath);
        }
        
        //the mozilla ini sections start with a capital letter
        Config.getGlobal().setPropertyFirstUpper(true);
        Ini profileIni = new Ini(profileFile);
        
        //build the profile data object
        ProfileIniData profileIniData = new ProfileIniData();
        
        //get general data
        profileIniData.setGeneral(profileIni.get("General").as(GeneralBean.class));
        
        //get profile data
        Section profileSection;
        ProfileBean profileBean;
        for (int profileCount = 0; (null != (profileSection = profileIni.get("Profile" + profileCount))); profileCount++) {
			profileBean = new ProfileBeanImpl();
        	profileSection.to(profileBean);
            profileIniData.addProfile(profileBean);
        }
        
        return profileIniData;
    }
    
    /**
     * @param profile - new profile data to be added
     */
    public static boolean addNewProfile(ProfileBean profile) {
    		return addNewProfile(SystemUtil.getProfileIniPath(), profile);
    }
    
    /**
     * @param profileFilePath - path to the existing profile file
     * @param profile - new profile data to be added
     */
    public static boolean addNewProfile(String profileFilePath, ProfileBean profile) {
    	try {
	    	//load the existing profiles
	    	ProfileIniData profileIniData = load(profileFilePath);
	    	
	    	//load to the ini file
	    	Config.getGlobal().setPropertyFirstUpper(true);
	    	Config.getGlobal().setStrictOperator(true);
	    	Ini ini = new Ini(new File(profileFilePath));
	    	
			//change to default profile
	    	List<ProfileBean> existingProfiles = profileIniData.getProfileBeanList();
	    	for(int index = 0; index < existingProfiles.size(); index++) {
	    		ProfileBean profileBean = existingProfiles.get(index);
	    		if(null != profileBean.getDefault() && "1".equals(profileBean.getDefault())) {
	    			ini.get("Profile" + index).remove("Default");
	    		}
	    	}
	    	
	    	//add new profile as section
	    	Section section = ini.add("Profile" + existingProfiles.size());
	    	section.from(profile);
	    	
	    	//store the new file
	    	ini.store();
	    	
	    	return true;
    	} catch (Exception e) {
    		log.error("Eception while adding new profile.", e);
    	}

    	return false;
    }
    
    /**
     * @return profile name which is a substring of the profile folder
     */
    public static String getProfileNameFromProfileFolder(String folderName) {
    	return folderName.substring(folderName.indexOf('.') + 1);
    }
    
    /**
     * @return returns a profile path generated as profilename prefixed 
     *         with 8 random characters
     */
    public static String generateProfilePath(String profileName) {
    	Random random = new Random(System.currentTimeMillis());
    	StringBuffer profilePath = new StringBuffer(SystemUtil.getProfilePath());
    	profilePath.append("PortedProfiles").append(SystemPropertiesEnum.FILE_SEPARATOR.get());
    	
    	for (int index = 0; index < 8 ; index++) {
    		profilePath.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
    	}
    	
    	profilePath.append('.').append(profileName);
    	
    	return profilePath.toString();
    }
    
    /**
     * @return returns a profile if found by the given name or null
     * @throws Exception
     */
    public static ProfileBean getProfileByName(String profileFilePath, String profileName) throws Exception {
    	//get the profiles
    	ProfileIniData data = load(profileFilePath);
    	
    	//search and return if found
    	List<ProfileBean> profiles = data.getProfileBeanList();
    	for(ProfileBean profile : profiles) {
    		if (profileName.equals(profile.getName())) {
    			return profile;
    		}
    	}
    	
    	log.debug("Could not find a profile by name: " + profileName + " at: " + profileFilePath);
    	
    	return null;
    }

    /**
     * @return returns a profile if found by the given name or null
     * @throws Exception
     */
    public static ProfileBean getProfileByName(String profileName) throws Exception {
    	//get the profiles
    	ProfileIniData data = load();
    	
    	//search and return if found
    	List<ProfileBean> profiles = data.getProfileBeanList();
    	for(ProfileBean profile : profiles) {
    		if (profileName.equals(profile.getName())) {
    			return profile;
    		}
    	}
    	
    	log.debug("Could not find a profile by name: " + profileName);
    	
    	return null;
    }
}
