/*hELLO*/
import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashMap; //DB
import java.util.Map; //DB
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry; //SQS

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region; //SQS + DB
import com.amazonaws.regions.Regions; //SQS + DB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.Tables;
import com.amazonaws.services.sqs.AmazonSQS; //SQS
import com.amazonaws.services.sqs.AmazonSQSClient; //SQS
import com.amazonaws.services.sqs.model.CreateQueueRequest; //SQS
import com.amazonaws.services.sqs.model.DeleteMessageRequest; //SQS
import com.amazonaws.services.sqs.model.DeleteQueueRequest; //SQS
import com.amazonaws.services.sqs.model.Message; //SQS
import com.amazonaws.services.sqs.model.ReceiveMessageRequest; //SQS
import com.amazonaws.services.sqs.model.SendMessageRequest; //SQS
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.DomainMetadataRequest;
import com.amazonaws.services.simpledb.model.DomainMetadataResult;
import com.amazonaws.services.simpledb.model.ListDomainsRequest;
import com.amazonaws.services.simpledb.model.ListDomainsResult;

import javax.swing.*; //GUI
import javax.swing.text.*;

import java.awt.*; //GUI
import java.awt.event.*;
import java.io.*;

import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;
import javax.imageio.*;

import java.nio.*;
import java.awt.image.*;
import java.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class AwsConsoleApp
{
	static String directoryName = "C:/Users/Richard/Desktop/";
	
	static AmazonEC2 ec2; static AmazonS3 s3;  static AmazonDynamoDBClient dynamoDB; //static AmazonSimpleDB sdb;
	static String accountsTable = "snider-accounts";  static String picturesTable = "snider-pictures";
	static String user = null; static String current_friend = null;
    static String bucketName = "snider-pictures-bucket";
	
    private static void init() throws Exception 
    { //This credentials provider implementation loads your AWS credentials from a properties file at the root of your classpath.
        AWSCredentialsProvider credentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
        ec2 = new AmazonEC2Client(credentialsProvider); s3  = new AmazonS3Client(credentialsProvider);
        dynamoDB = new AmazonDynamoDBClient(new ClasspathPropertiesFileCredentialsProvider()); // This credentials provider implementation loads your AWS credentials from a properties file at the root of your classpath.
        dynamoDB.setRegion(Region.getRegion(Regions.US_WEST_2)); s3.setRegion(Region.getRegion(Regions.US_WEST_2));
        
        if (Tables.doesTableExist(dynamoDB, accountsTable)) { System.out.println("\nTable " + accountsTable + " is already ACTIVE"); }
        else 
        {
            CreateTableRequest createAccountsTableRequest = new CreateTableRequest().withTableName(accountsTable)
                .withKeySchema(new KeySchemaElement().withAttributeName("username").withKeyType(KeyType.HASH))
                .withAttributeDefinitions(new AttributeDefinition().withAttributeName("username").withAttributeType(ScalarAttributeType.S))
                .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(1L).withWriteCapacityUnits(1L));
            TableDescription accountsTableDescription = dynamoDB.createTable(createAccountsTableRequest).getTableDescription();
            Tables.waitForTableToBecomeActive(dynamoDB, accountsTable);
        }
        
        if(s3.doesBucketExist(bucketName) == false) { s3.createBucket(bucketName); } 
    }
          
    public static void main(String[] args) throws Exception 
    {
        init();
        final DefaultListModel friend_list = new DefaultListModel(); 
		final DefaultListModel status_list = new DefaultListModel(); 
        
        try 
        {
        	final JFrame guiFrame = new JFrame(); guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //make sure the program exits when the frame closes
        	guiFrame.addWindowListener( new WindowAdapter()
        	{
        	    public void windowClosing(WindowEvent e)
        	    {
        	        JFrame frame = (JFrame)e.getSource();
        	        int result = JOptionPane.showConfirmDialog(frame,"Are you sure you want to exit AWS Messenger?","Exit Messenger",JOptionPane.YES_NO_OPTION);

        	        if (result == JOptionPane.YES_OPTION)
        	        {
        	        	Map<String, AttributeValue> user_key = new HashMap<String, AttributeValue>();
    	    			user_key.put("username", new AttributeValue(user));
        	        	
    	    			//UPDATE STATUS TO "OFFLINE"
    	    			Map<String, AttributeValueUpdate> status_update_item = new HashMap<String, AttributeValueUpdate>();
    	    			AttributeValueUpdate status_value_update = new AttributeValueUpdate(new AttributeValue("Offline"), AttributeAction.PUT);
    	    			status_update_item.put("status", status_value_update);
    	    			dynamoDB.updateItem(accountsTable, user_key, status_update_item, "NONE");
    	    			
        	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	        }
        	        else { frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); }
        	    }
        	});
        	
        	guiFrame.setTitle("AWS Messenger"); guiFrame.setSize(650,300); guiFrame.setLocationRelativeTo(null);
            final JFrame loginFrame = new JFrame(); loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setTitle("AWS Messenger Login"); loginFrame.setSize(425,135); loginFrame.setLocationRelativeTo(null);
            
            JPanel user_name_panel = new JPanel(); JPanel password_panel = new JPanel(); JPanel error_msg_panel = new JPanel();
            final JTextArea error_text_box = new JTextArea(1,35); error_text_box.setEditable(false); error_msg_panel.add(error_text_box);
            error_text_box.setText("Welcome to AWS Messenger!");
            JLabel username_label = new JLabel("Username: "); user_name_panel.add(username_label);
            JLabel password_label = new JLabel("Password: "); password_panel.add(password_label);
            final JTextField user_name_field = new JTextField(20); user_name_panel.add(user_name_field);
            final JPasswordField password_field = new JPasswordField(20); password_panel.add(password_field);
            JButton login_button = new JButton("Login"); user_name_panel.add(login_button);
            JButton register_button = new JButton("Register"); password_panel.add(register_button);
            
            JPanel error_panel = new JPanel(); 
            final JTextArea current_friend_box = new JTextArea(1,20); current_friend_box.setEditable(false); error_panel.add(current_friend_box);
            final JTextArea errorMsg = new JTextArea(1,35); errorMsg.setEditable(false); error_panel.add(errorMsg);
           
            final JTextArea chatbox = new JTextArea(10,35); chatbox.setLineWrap(true); chatbox.setEditable(false); JScrollPane chatscroll = new JScrollPane(chatbox);           
            JList friends_list_box = new JList(friend_list); JList status_list_box = new JList(status_list); 
            JScrollPane friendscroll = new JScrollPane(friends_list_box); JScrollPane statusscroll = new JScrollPane(status_list_box);
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,friendscroll,statusscroll); splitPane.setDividerLocation(100);
            
            final JPanel MessagePanel = new JPanel(); final JTextField texty = new JTextField(30); MessagePanel.add(texty);
            JButton SendButton = new JButton("Send"); MessagePanel.add(SendButton); final JButton AttachButton = new JButton("Upload"); MessagePanel.add(AttachButton);
            JButton AddFriend = new JButton("+"); MessagePanel.add(AddFriend);
            JButton downloadPic = new JButton("Download"); MessagePanel.add(downloadPic);
              
            final JPanel FilePanel = new JPanel(); final JFileChooser file_chooser = new JFileChooser(); FilePanel.add(file_chooser); //FileNameExtensionFilter filter = new FileNameExtensionFilter(".bmp .png and .jpg", "bmp", "png", "jpg"); file_chooser.setFileFilter(filter);

            ActionListener open_file = new ActionListener()
            {
            	public void actionPerformed(ActionEvent actionEvent) 
            	{ 
            	    if (actionEvent.getSource() == AttachButton)
            	    {
            	        if (file_chooser.showOpenDialog(FilePanel) == JFileChooser.APPROVE_OPTION && current_friend != null)
            	        { 
            	        	File selected_file = file_chooser.getSelectedFile();
            	        	String file_name = selected_file.getName(); // String file_path = selected_file.getPath();
            	        	String picture_message = "\n" + user + ": (file) " + file_name;
            	        	errorMsg.setText("Please wait while your file is uploading.");
                	        s3.putObject(new PutObjectRequest(bucketName, file_name, selected_file));
                	    	
                	        //UPDATE THIS USERS CHAT
                	    	Map<String, AttributeValue> friend_key = new HashMap<String, AttributeValue>();
            	    		friend_key.put("friend_name", new AttributeValue(current_friend));
            	    		Map<String, AttributeValueUpdate> chat_update_item = new HashMap<String, AttributeValueUpdate>();
            	    		chatbox.append(picture_message);
            	    		chat_update_item.put("chat", new AttributeValueUpdate(new AttributeValue(chatbox.getText()), AttributeAction.PUT));
            	    		dynamoDB.updateItem("snider-table-" + user, friend_key, chat_update_item);
            	    			
            	    		//UPDATE OTHER USERS CHAT
            	    		friend_key.put("friend_name", new AttributeValue(user));
            	    		dynamoDB.updateItem("snider-table-" + current_friend, friend_key, chat_update_item, "NONE");
            	    		
            	    		errorMsg.setText("File has been uploaded and sent.");
            	        } 
            			else { chatbox.append("Open command cancelled by user.\n"); }
            	    }
            	}
            };
            AttachButton.addActionListener(open_file);
            
            ActionListener loginpressed = new ActionListener()
    	    {
    	    	public void actionPerformed(ActionEvent actionEvent) 
    	    	{  	
    	    		String userTable = "snider-table-" + user_name_field.getText();
    	    		
    	    		if (Tables.doesTableExist(dynamoDB, userTable)) 
    	    		{ 
    	    			//GET PASSWORD
    	    			Map<String, AttributeValue> user_key = new HashMap<String, AttributeValue>();
    	    			user_key.put("username", new AttributeValue(user_name_field.getText()));
    	    			GetItemResult username_result = dynamoDB.getItem(accountsTable, user_key);
    	    			Object correct_password_key = username_result.getItem().get("password");
    	    			String entered_password = "{S: " + password_field.getText() + ",}"; //Must add this because string conversion is messy

    	    			if (entered_password.equals(correct_password_key.toString()))
    	    			{
    	    				
    	    				user = user_name_field.getText();
    	    				System.out.println("user = " + user);
    	    				guiFrame.setTitle("AWS Messenger - " + user);
    	    				guiFrame.setVisible(true); loginFrame.setVisible(false);
    	    				
    	    				//UPDATE STATUS TO "ONLINE"
        	    			Map<String, AttributeValueUpdate> status_update_item = new HashMap<String, AttributeValueUpdate>();
        	    			AttributeValueUpdate status_value_update = new AttributeValueUpdate(new AttributeValue("Online"), AttributeAction.PUT);
        	    			status_update_item.put("status", status_value_update);
        	    			dynamoDB.updateItem(accountsTable, user_key, status_update_item, "NONE");
        	    			
        	    			//REFRESH FRIENDS LIST
    	    	            ScanRequest scanListRequest = new ScanRequest("snider-table-" + user);
        	                ScanResult scanListResult = dynamoDB.scan(scanListRequest);
    	    	            for (Map<String, AttributeValue> friend_item : scanListResult.getItems()) 
    		                {//MODIFY FRIENDS LIST
    	    	    			String obtained_friend = friend_item.get("friend_name").toString();
    	    	    			String obtained_friend_name = obtained_friend.substring(4, obtained_friend.length()-2);
        	    	    		friend_list.addElement(obtained_friend_name);
    	    	    			
    	    	    			//MODIFY STATUS
        	    	    		
        	    	    		Map<String, AttributeValue> friend_name_key = new HashMap<String, AttributeValue>();
    	    	    			friend_name_key.put("username", new AttributeValue(obtained_friend_name));
        	    	    		GetItemResult user_name_result = dynamoDB.getItem("snider-accounts",friend_name_key);
        	    	    		String obtained_status = user_name_result.getItem().get("status").toString();
        	    	    		status_list.addElement(obtained_status.substring(4, obtained_status.length()-2));		
    		                }
    	    	            current_friend = friend_list.get(0).toString();
    	    			}
    	    			else { error_text_box.setText("Wrong Password!"); }
    	    		}
    	    		else
    	    		{ error_text_box.setText("That username does not exist!"); }  	    		
    	    	}
    	    };
    	    login_button.addActionListener(loginpressed);
    	    
    	    ActionListener registerpressed = new ActionListener()
    	    {
    	    	public void actionPerformed(ActionEvent actionEvent) 
    	    	{ 
    	    		String UserTable = "snider-table-" + user_name_field.getText();
    	    		if (password_field.getText() == null) { error_text_box.setText("You need a password!"); }
    	    		else if (Tables.doesTableExist(dynamoDB, UserTable)) { error_text_box.setText("That username already exists!"); }
    	            else //THIS IS A NEW USERNAME AND WE MUST ADD AN ITEM TO ACCOUNTS, THEN CREATE A TABLE FOR THAT USER
    	            { 
        	    		Map<String, AttributeValue> new_user = newUser(user_name_field.getText(), "Online", password_field.getText());
        	            PutItemResult newUserResult = dynamoDB.putItem(new PutItemRequest(accountsTable, new_user));
    	            	
    	                CreateTableRequest createUserTableRequest = new CreateTableRequest().withTableName(UserTable)
    	                    .withKeySchema(new KeySchemaElement().withAttributeName("friend_name").withKeyType(KeyType.HASH))
    	                    .withAttributeDefinitions(new AttributeDefinition().withAttributeName("friend_name").withAttributeType(ScalarAttributeType.S))
    	                    .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(1L).withWriteCapacityUnits(1L));
    	                TableDescription UserTableDescription = dynamoDB.createTable(createUserTableRequest).getTableDescription();
    	                error_text_box.setText("Please wait while your new account is registered.");
    	                Tables.waitForTableToBecomeActive(dynamoDB, UserTable);
    	                error_text_box.setText("Please wait while your new account is registered.");
    	                
    	                user = user_name_field.getText();
    	                guiFrame.setTitle("AWS Messenger - " + user);
	    				guiFrame.setVisible(true); loginFrame.setVisible(false);
	    				
    	                //REFRESH FRIENDS LIST
	    	            ScanRequest scanListRequest = new ScanRequest("snider-table-" + user);
    	                ScanResult scanListResult = dynamoDB.scan(scanListRequest);
	    	            for (Map<String, AttributeValue> friend_item : scanListResult.getItems()) 
		                {//MODIFY FRIENDS LIST
	    	    			String obtained_friend = friend_item.get("friend_name").toString();
	    	    			String obtained_friend_name = obtained_friend.substring(4, obtained_friend.length()-2);
    	    	    		friend_list.addElement(obtained_friend_name);
	    	    			
	    	    			//MODIFY STATUS
    	    	    		Map<String, AttributeValue> friend_name_key = new HashMap<String, AttributeValue>();
	    	    			friend_name_key.put("username", new AttributeValue(obtained_friend_name));
    	    	    		GetItemResult username_result = dynamoDB.getItem("snider-accounts",friend_name_key);
    	    	    		String obtained_status = username_result.getItem().get("status").toString();
    	    	    		status_list.addElement(obtained_status.substring(4, obtained_status.length()-2));		
		                }
    	            }
    	    	}
    	    };
    	    register_button.addActionListener(registerpressed);
            
    	    ActionListener send_pressed = new ActionListener()
    	    {
    	    	public void actionPerformed(ActionEvent actionEvent) 
    	    	{  
    	    		if (current_friend != null)
    	    		{
	    	    		String new_message = "\n" + user + ": " + texty.getText();
	    	    		texty.setText(null);
	    	    		chatbox.append(new_message);
	    	    		
	    	    		Map<String, AttributeValue> friend_key = new HashMap<String, AttributeValue>();
		    			friend_key.put("friend_name", new AttributeValue(current_friend));
	    	    	
	    	    		//UPDATE THIS USERS CHAT
		    			Map<String, AttributeValueUpdate> chat_update_item = new HashMap<String, AttributeValueUpdate>();
		    			chat_update_item.put("chat", new AttributeValueUpdate(new AttributeValue(chatbox.getText()), AttributeAction.PUT));
		    			dynamoDB.updateItem("snider-table-" + user, friend_key, chat_update_item);
		    			
		    			//UPDATE OTHER USERS CHAT
		    			friend_key.put("friend_name", new AttributeValue(user));
		    			dynamoDB.updateItem("snider-table-" + current_friend, friend_key, chat_update_item, "NONE");
    	    		}
    	    	}
    	    };
    	    SendButton.addActionListener(send_pressed);
    	    
    	    ActionListener addpressed = new ActionListener()
    	    {
    	    	public void actionPerformed(ActionEvent actionEvent) 
    	    	{  
    	    		String a_new_friend = texty.getText();
    	    		if (a_new_friend.equals(user)) { errorMsg.setText("You cannot add yourself!"); }
    	    		else if (Tables.doesTableExist(dynamoDB, "snider-table-" + a_new_friend))
    	    		{
    	    			//CHECK IF FRIEND IS ALREADY IN FRIENDS LIST
    	    			HashMap<String, Condition> scanFriends = new HashMap<String, Condition>();
    	                Condition conditionFriendName = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString()).withAttributeValueList(new AttributeValue().withS(a_new_friend));            
    	                scanFriends.put("friend_name", conditionFriendName); ScanRequest scanFriendsRequest = new ScanRequest("snider-table-" + user).withScanFilter(scanFriends);
    	                ScanResult scanFriendsResult = dynamoDB.scan(scanFriendsRequest);
    	    			
    	                
    	                if(scanFriendsResult.getCount() == 0)
    	    			{ //IF THE FRIEND NAME WAS NOT ALREADY IN THE CURRENT LIST OF FRIENDS, ADD A NEW FRIEND TO THE LIST
    	    				Map<String, AttributeValue> new_friend = newFriend(a_new_friend, "Begin conversation . . ."); 
    	    	            PutItemResult newFriendResult = dynamoDB.putItem(new PutItemRequest("snider-table-" + user, new_friend));
    	    	            texty.setText(null);
    	    	            
    	    	            //REFRESH FRIENDS LIST
    	    	            friend_list.clear(); status_list.clear();
    	    	            ScanRequest scanListRequest = new ScanRequest("snider-table-" + user);
        	                ScanResult scanListResult = dynamoDB.scan(scanListRequest);
    	    	            for (Map<String, AttributeValue> friend_item : scanListResult.getItems()) 
    		                {//MODIFY FRIENDS LIST
    	    	    			String obtained_friend = friend_item.get("friend_name").toString();
    	    	    			String obtained_friend_name = obtained_friend.substring(4, obtained_friend.length()-2);
        	    	    		friend_list.addElement(obtained_friend_name);
    	    	    			
    	    	    			//MODIFY STATUS
        	    	    		Map<String, AttributeValue> friend_name_key = new HashMap<String, AttributeValue>();
    	    	    			friend_name_key.put("username", new AttributeValue(obtained_friend_name));
        	    	    		GetItemResult username_result = dynamoDB.getItem("snider-accounts",friend_name_key);
        	    	    		String obtained_status = username_result.getItem().get("status").toString();
        	    	    		status_list.addElement(obtained_status.substring(4, obtained_status.length()-2));		
    		                }
    	    			}
    	    			else { errorMsg.setText("That user is already in your friends list!"); }         
    	    		}
    	    		else { errorMsg.setText("That user doesn't exist!"); }
    	    	} 
    	    };
    	    AddFriend.addActionListener(addpressed);
    	    
    	    ActionListener downloadpressed = new ActionListener()
    	    {
    	    	public void actionPerformed(ActionEvent actionEvent) 
    	    	{  
    	    			errorMsg.setText("Please wait while your file is downloading.");
    	    			
    	    			try 
    	    			{ 
    	    				S3Object obtained_pic = s3.getObject(new GetObjectRequest(bucketName, texty.getText()));
	    	    			S3ObjectInputStream objectContent = obtained_pic.getObjectContent();
    	    				FileOutputStream pic_stream_out = new FileOutputStream(directoryName + texty.getText());
    	    				IOUtils.copy(objectContent, pic_stream_out); 
    	    				pic_stream_out.close();
    	    			
    	    			} catch (IOException e) {e.printStackTrace(); }
    	    			catch (AmazonServiceException ase) { errorMsg.setText("That file has not been uploaded."); } 
    	    			catch (AmazonClientException ace) { errorMsg.setText("That file has not been uploaded."); }
    	    			
    	    			errorMsg.setText("File has downloaded.");
    	    			
    	    	} 
    	    };
    	    downloadPic.addActionListener(downloadpressed);
    	      
    	    MouseListener mouseListener = new MouseAdapter() 
    	    {
    	        public void mouseClicked(MouseEvent mouseEvent) 
    	        {
    	          JList theList = (JList) mouseEvent.getSource();
    	          if (mouseEvent.getClickCount() == 2) //OPEN CHAT WITH THAT FRIEND
    	          {
    	            int index = theList.locationToIndex(mouseEvent.getPoint());
    	            if (index >= 0)
    	            { //GET CHAT HISTORY WITH CURRENT_FRIEND
    	            	current_friend = theList.getModel().getElementAt(index).toString();
    	    			Map<String, AttributeValue> friend_key = new HashMap<String, AttributeValue>();
    	    			friend_key.put("friend_name", new AttributeValue(current_friend));
    	    			GetItemResult friend_result = dynamoDB.getItem("snider-table-" + user, friend_key);
    	    			String obtained_chat = friend_result.getItem().get("chat").toString();
    	    			chatbox.setText(obtained_chat.substring(4, obtained_chat.length()-2));
    	    			current_friend_box.setText(current_friend);
    	            }
    	          }
    	          
    	          if (mouseEvent.getButton() == mouseEvent.BUTTON3) //REMOVE FRIEND AND STATUS FROM LIST
    	          {
    	        	int index = theList.locationToIndex(mouseEvent.getPoint());
    	        	if (index >= 0) 
    	        	{
    	        		String friend_to_remove = theList.getModel().getElementAt(index).toString();
    	        		Map<String, AttributeValue> friend_key = new HashMap<String, AttributeValue>();
    	    			friend_key.put("friend_name", new AttributeValue(friend_to_remove));
    	                dynamoDB.deleteItem(new DeleteItemRequest("snider-table-" + user, friend_key)); //dynamoDB.deleteTable(new DeleteTableRequest(accountsTable));
    	        	
    	                //REFRESH FRIENDS LIST
	    	            friend_list.clear(); status_list.clear();
	    	            ScanRequest scanListRequest = new ScanRequest("snider-table-" + user);
    	                ScanResult scanListResult = dynamoDB.scan(scanListRequest);
	    	            for (Map<String, AttributeValue> friend_item : scanListResult.getItems()) 
		                {//MODIFY FRIENDS LIST
	    	    			String obtained_friend = friend_item.get("friend_name").toString();
	    	    			String obtained_friend_name = obtained_friend.substring(4, obtained_friend.length()-2);
    	    	    		friend_list.addElement(obtained_friend_name);
	    	    			
	    	    			//MODIFY STATUS
    	    	    		Map<String, AttributeValue> friend_name_key = new HashMap<String, AttributeValue>();
	    	    			friend_name_key.put("username", new AttributeValue(obtained_friend_name));
    	    	    		GetItemResult username_result = dynamoDB.getItem("snider-accounts",friend_name_key);
    	    	    		String obtained_status = username_result.getItem().get("status").toString();
    	    	    		status_list.addElement(obtained_status.substring(4, obtained_status.length()-2));		
		                }
    	        	}
    	          } 	          
    	        }
    	    };
    	    friends_list_box.addMouseListener(mouseListener);
    	    
    	    loginFrame.add(user_name_panel,BorderLayout.NORTH); loginFrame.add(password_panel,BorderLayout.CENTER); loginFrame.add(error_msg_panel,BorderLayout.SOUTH);
            
    	    guiFrame.add(chatscroll,BorderLayout.WEST); guiFrame.add(error_panel,BorderLayout.NORTH);
            guiFrame.add(MessagePanel,BorderLayout.SOUTH);
            guiFrame.setVisible(false);  loginFrame.setVisible(true);	
            
            try 
            {  
            	while (true)
            	{ 
            		guiFrame.add(splitPane, BorderLayout.CENTER); 
            		
            		if(user != null )
            		{
            			//UPDATE STATUS TO "ONLINE"
            			Map<String, AttributeValue> user_key = new HashMap<String, AttributeValue>();
    	    			user_key.put("username", new AttributeValue(user));
    	    			Map<String, AttributeValueUpdate> status_update_item = new HashMap<String, AttributeValueUpdate>();
    	    			AttributeValueUpdate status_value_update = new AttributeValueUpdate(new AttributeValue("Online"), AttributeAction.PUT);
    	    			status_update_item.put("status", status_value_update);
    	    			dynamoDB.updateItem(accountsTable, user_key, status_update_item, "NONE");
    	    			
    	    			
            			if(friend_list.getSize() > 0)
            			{ //RELOAD FRIENDS LIST AND STATUSES
            				ScanRequest scanListRequestRepeating = new ScanRequest("snider-table-" + user);
                    		ScanResult scanListResultRepeating = dynamoDB.scan(scanListRequestRepeating);
                    		int i = 0;
            				for (Map<String, AttributeValue> friend_item : scanListResultRepeating.getItems()) 
    		                {
    	    	    			String obtained_friend = friend_item.get("friend_name").toString();
    	    	    			String obtained_friend_name = obtained_friend.substring(4, obtained_friend.length()-2);
    	    	    			
    	    	    			//MODIFY STATUS
        	    	    		Map<String, AttributeValue> friend_name_key = new HashMap<String, AttributeValue>();
    	    	    			friend_name_key.put("username", new AttributeValue(obtained_friend_name));
        	    	    		GetItemResult username_result = dynamoDB.getItem("snider-accounts",friend_name_key);
        	    	    		String obtained_status = username_result.getItem().get("status").toString();
        	    	    		String the_status = obtained_status.substring(4, obtained_status.length()-2);
        	    	    		status_list.setElementAt(the_status, i);
        	    	    		i++;
    		                }
            			}
            		}
            		
            		if(current_friend != null)
            		{ //RELOAD CHAT
            			current_friend_box.setText("Talking to " + current_friend);
            			Map<String, AttributeValue> a_friend_key = new HashMap<String, AttributeValue>();
    	    			a_friend_key.put("friend_name", new AttributeValue(current_friend));
    	    			GetItemResult friend_result = dynamoDB.getItem("snider-table-" + user, a_friend_key);
    	    			String obtained_chat = friend_result.getItem().get("chat").toString();
    	    			chatbox.setText(obtained_chat.substring(4, obtained_chat.length()-2));
            		}
            		Thread.sleep(2000); 
            	}  
            } 
            catch (InterruptedException e) { e.printStackTrace(); }
        }
        catch (AmazonServiceException ase) { System.out.println("\n\nCaught an AmazonServiceException, which means your request made it to AWS, but was rejected with an error response for some reason."); System.out.println("Error Message:    " + ase.getMessage() + "HTTP Status Code: " + ase.getStatusCode()  + "AWS Error Code:   " + ase.getErrorCode() + "Error Type:       " + ase.getErrorType() + "Request ID:       " + ase.getRequestId()); } 
        catch (AmazonClientException ace) { System.out.println("\n\nCaught an AmazonClientException, which means the client encountered a serious internal problem while trying to communicate with AWS, such as not being able to access the network."); System.out.println("Error Message: " + ace.getMessage()); }
    }
    
    private static Map<String, AttributeValue> newUser(String username, String status, String password) 
    {
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("username", new AttributeValue(username)); 
        item.put("status", new AttributeValue(status));
        item.put("password", new AttributeValue(password));
        return item;
    }
    
    private static Map<String, AttributeValue> newFriend(String friend_name, String chat) 
    {
    	Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("friend_name", new AttributeValue(friend_name));
        item.put("chat", new AttributeValue(chat));
        return item;
    }
}
