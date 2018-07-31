package com.example.inventory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class InsertActivity extends Activity{

	private File pictureData;
	private static final int PIC_REQUEST = 1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.insert);
		
	} 
	 
	public void submitone(View v) 
	{  
		Product p = new Product();
		EditText et = (EditText) findViewById(R.id.insertName);
		p.setName(et.getText().toString());		
		 et = (EditText) findViewById(R.id.insertPrice);
		 p.setPrice(Double.parseDouble(et.getText().toString()));
		 et = (EditText) findViewById(R.id.insertQuantity);
			 p.setQuantity(Integer.parseInt(et.getText().toString()));
			 p.pic = this.pictureData;
		
			 
			 
			 UploadProductTask task = new UploadProductTask(this);
			 task.execute(p);
			 
			 
			/* 
			int returnedId = Model.insert(p);
			  
			if(returnedId > -1)
			{
			p.setId(returnedId); 
			
			Bundle data = new Bundle();
			data.putSerializable("product", p);
			Intent i = new Intent(this.getApplicationContext(), ViewProductActivity.class);
			i.putExtra("data", data);
			startActivity(i);
			}
			else
			{ 
				Toast.makeText(this, "Insertion Failed!", Toast.LENGTH_LONG).show();
			}*/
	}
	
	public void takePic(View v)
	{
		Random r = new Random(System.currentTimeMillis());
		Integer randomInt = r.nextInt(Integer.MAX_VALUE);
		String randomName = "pic"+randomInt;
	   
		
		File storageDir = new File(
		    Environment.getExternalStoragePublicDirectory(
		        Environment.DIRECTORY_PICTURES
		    ).toString()
		);    
		Log.v("TAKEPIC", storageDir.toString());
		File picFile = new File(storageDir, randomName+".jpg");
		
		while(picFile.exists())
		{
			randomInt = r.nextInt(Integer.MAX_VALUE);
			randomName = "pic"+randomInt;
			picFile = new File(storageDir, randomName+".jpg");
		}
			
		try {
			picFile.createNewFile();
			/*File image = File.createTempFile(
			       randomName, 
			        ".jpg", 
			        storageDir
			    );
			*/
			
		    Intent takePictureIntent = new Intent("android.media.action.IMAGE_CAPTURE");
		   
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picFile));
		    
			this.pictureData = picFile;
			
		    startActivityForResult(takePictureIntent, PIC_REQUEST);
		
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(this, "Error, couldn't create temp file for photo!", Toast.LENGTH_LONG).show();
		}  
	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		switch(requestCode)
		{
		case PIC_REQUEST:
			if(resultCode == Activity.RESULT_OK)
			{
		//	 Bundle extras = intent.getExtras();
			//   Bitmap mImageBitmap = (Bitmap) extras.get("data");
			 
			//   Uri selectedImage = intent.getData();
		    //    ImageView photo = (ImageView) findViewById(R.id.add_contact_label_photo);
		        Bitmap mBitmap = BitmapFactory.decodeFile(this.pictureData.toString());
		       
			   
			   ImageView mImageView = (ImageView) findViewById(R.id.imageID);
			    
			    mImageView.setImageBitmap(mBitmap);
			}
			else
			{
				Toast.makeText(this, "Camera returned bad result code", Toast.LENGTH_LONG).show();
			}

			break;
		}
		
	}
}
