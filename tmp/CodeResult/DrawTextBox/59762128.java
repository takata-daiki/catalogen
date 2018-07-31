/*      D&D Character Sheet
* Copyright (c) 2013, Ryan Estep
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, 
* are permitted provided that the following conditions are met:
*  -Redistributions of source code must retain the above copyright notice, 
*	this list of conditions and the following disclaimer.
*  -Redistributions in binary form must reproduce the above copyright notice, 
*	this list of conditions and the following disclaimer in the documentation 
*	and/or other materials provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
* IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
* INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
* BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
* OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
* STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
* OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.ancantus.dnd.widget;

import com.ancantus.dnd.CharDatabaseHelper;
import com.ancantus.dnd.R;
import com.ancantus.dnd.listeners.CheckboxEditWatcher;
import com.ancantus.dnd.listeners.DatabaseEditWatcher;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SkillsWidget extends View implements ElementWidget 
{
	private Context con;
	
	private String name = "";
	private String ability = "";
	private int lvl = 0;
	private int total = 0;
	private int isTrained = 0;
	private int ablMod = 0;
	private int armorPenalty = 0; //not implemented yet (may not ever me automatic cause of flexabiltiy)
	private int misc = 0;
	
	private final float pixelDensityScale = getResources().getDisplayMetrics().density;	//used for pixel to dp conversion
	//private int width = 0;
	//private int height = 0;
	
	private Paint thinBlackBrush = new Paint();
	private Paint whiteFill = new Paint();
	private Paint textBrush = new Paint();
	private Paint smallTextBrush = new Paint();
	private Paint thickBlackBrush = new Paint();
	
	//three 'overloaded' constructors
	public SkillsWidget(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		con = context;
		initalize();
	}

	public SkillsWidget(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		con = context;
		initalize();
	}

	public SkillsWidget(Context context) 
	{
		super(context);
		con = context;
		initalize();
	}
	//override allows us to save the width and height of drawing canvas
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		
		//width = (int)(w / pixelDensityScale);
		//height = (int)(h / pixelDensityScale);
	}
	
	private void initalize()
	{
		setMinimumHeight(dpToPix(40));	//min height allows the scrollview to not squash everything
		
		thinBlackBrush.setColor(Color.BLACK);
		thinBlackBrush.setStyle(Paint.Style.STROKE);
		thinBlackBrush.setStrokeWidth(dpToPix(2));
		thinBlackBrush.setAlpha(255);
		
		whiteFill.setColor(Color.WHITE);
		whiteFill.setStyle(Paint.Style.FILL);
		whiteFill.setStrokeWidth(dpToPix(2));
		whiteFill.setAlpha(255);
		
		textBrush.setColor(Color.BLACK);
		textBrush.setStyle(Paint.Style.FILL);
		textBrush.setStrokeWidth(1);
		textBrush.setAlpha(255);
		textBrush.setAntiAlias(true);
		textBrush.setTextSize(dpToPix(20));
		
		smallTextBrush.setColor(Color.BLACK);
		smallTextBrush.setStyle(Paint.Style.FILL);
		smallTextBrush.setStrokeWidth(1);
		smallTextBrush.setAlpha(255);
		smallTextBrush.setAntiAlias(true);
		smallTextBrush.setTextSize(dpToPix(15));
		
		thickBlackBrush.setColor(Color.BLACK);
		thickBlackBrush.setStyle(Paint.Style.STROKE);
		thickBlackBrush.setStrokeWidth(dpToPix(3));
		thickBlackBrush.setAlpha(255);
		thickBlackBrush.setAntiAlias(true);
		
		this.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1));
	}
	
	public ViewGroup[] GeneratePaneLayout(LayoutInflater inflater, ViewGroup parentView)
	{
		//generate pane via xml, but don't add to parent yet (need a handle on it)
		View tmpView = inflater.inflate(R.layout.linear_pane_skeleton, parentView, false);
		
		//now add it to the view
		parentView.addView(tmpView);
		
		//now return the 2 view groups (index 0 for the header, 1 for the body)
		return new ViewGroup[]{(ViewGroup)tmpView.findViewById(R.id.header), (ViewGroup)tmpView.findViewById(R.id.body)};
	}
	
	@Override
	public void onDraw(Canvas c)
	{
		drawTextBox(c, total, 10);
		c.drawText(name, dpToPix(45),dpToPix(25), smallTextBrush);
		drawTextBox(c, ablMod, 155);
		drawTextBox(c, armorPenalty, 200);
		drawTextBox(c, misc, 245);
		
		//draw checkbox for trained?
		c.drawRect(dpToPix(290), dpToPix(13), dpToPix(300), dpToPix(23), thinBlackBrush);
		if (isTrained == 1)
		{
			c.drawLine(dpToPix(288), dpToPix(10), dpToPix(295), dpToPix(21), thickBlackBrush);
			c.drawLine(dpToPix(294), dpToPix(21), dpToPix(305), dpToPix(3), thickBlackBrush);
		}
	}
	
	private void drawTextBox(Canvas c, int text, int offsetDP)
	{
		c.drawRect(dpToPix(offsetDP), dpToPix(5), dpToPix(30 + offsetDP), dpToPix(30), whiteFill); //erase anything underneath
		c.drawRect(dpToPix(offsetDP), dpToPix(5), dpToPix(30 + offsetDP), dpToPix(30), thinBlackBrush); //draw box
		c.drawText(Integer.toString(text), dpToPix(offsetDP + 3), dpToPix(25), textBrush);
	}
	
	//pulls all the information from the database, and does the required calculations
	public void PopulateAttributes(Cursor skillEntry, Cursor generalEntry, CharDatabaseHelper dataWrapper)
	{
		name = skillEntry.getString(skillEntry.getColumnIndex("name"));

		//get ability string from the skill database, used for modentry lookup
		ability = skillEntry.getString(skillEntry.getColumnIndex("str_data_1"));
		Cursor modEntry = dataWrapper.QueryType(CharDatabaseHelper.ABL_SCORE, ability);
		
		//now all the entries have been pulled from the database, populate the attributes
		//isTrained will add a bonus of 5 to any skill (make modifyable perhaps...)
		if (skillEntry.getInt(skillEntry.getColumnIndex("int_data_2")) == 1)
		{
			isTrained = 1;
		}
		else
		{
			isTrained = 0;
		}
		lvl = generalEntry.getInt(generalEntry.getColumnIndex("int_data_1"));
		ablMod = modEntry.getInt(modEntry.getColumnIndex("int_data_2")) + (int)(lvl / 2);
		misc = skillEntry.getInt(skillEntry.getColumnIndex("int_data_1"));
		//add armor query and data if thats applicable (might be too rigid)
		total = ablMod + misc + armorPenalty + (isTrained * 5);
	}
	public void GeneratePaneHeader(RelativeLayout layout) 
	{
		//add the bonus label
        TextView t1 = new TextView(con);
        t1.setText("Bonus");
        t1.setTextColor(Color.BLACK);
        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp1.addRule(RelativeLayout.ALIGN_LEFT);
        lp1.leftMargin = dpToPix(5);
        layout.addView(t1, lp1);
        
        //add the Skill Name label
        TextView t2 = new TextView(con);
        t2.setText("Skill Name");
        t2.setTextColor(Color.BLACK);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.ALIGN_LEFT);
        lp2.leftMargin = dpToPix(60);
        layout.addView(t2, lp2);
        
        //add the Mod + lvl label
        TextView t3 = new TextView(con);
        t3.setText("Mod + lvl");
        t3.setId(453);
        t3.setTextColor(Color.BLACK);
        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp3.addRule(RelativeLayout.ALIGN_LEFT);
        lp3.leftMargin = dpToPix(138);
        layout.addView(t3, lp3);
        
        //add the Armor label
        TextView t4 = new TextView(con);
        t4.setText("Armor");
        t4.setTextColor(Color.BLACK);
        RelativeLayout.LayoutParams lp4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp4.addRule(RelativeLayout.ALIGN_LEFT);
        lp4.leftMargin = dpToPix(200);
        layout.addView(t4, lp4);
        
        //add the Misc label
        TextView t5 = new TextView(con);
        t5.setText("Misc");
        t5.setTextColor(Color.BLACK);
        RelativeLayout.LayoutParams lp5 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp5.addRule(RelativeLayout.ALIGN_LEFT);
        lp5.leftMargin = dpToPix(247);
        layout.addView(t5, lp5);
        
        //add the Trnd label
        TextView t6 = new TextView(con);
        t6.setText("Trnd");
        t6.setTextColor(Color.BLACK);
        RelativeLayout.LayoutParams lp6 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp6.addRule(RelativeLayout.ALIGN_LEFT);
        lp6.leftMargin = dpToPix(280);
        layout.addView(t6, lp6);
	}
	public void GenerateEditLine(TableLayout layout, CharDatabaseHelper dataWrapper) 
	{
		//when sql executed, will come up with only the line for this skill score
		String searchClause = "type=? AND name=?";
		String searchArgs[] = {Integer.toString(CharDatabaseHelper.SKILL_SCORE), name}; 
				
		//create row (used in tableView as a single row, we will only generate one row
		TableRow row = new TableRow(con);
		row.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		
		
		//make the label for the row
		TextView label = new TextView(con);
		label.setText(name + ":");
		label.setTextColor(Color.BLACK);
		row.addView(label);
		
		//make edittext for score
		EditText editScore = new EditText(con);
		editScore.setEms(3);
		editScore.setInputType(InputType.TYPE_CLASS_NUMBER);
		editScore.setText(Integer.toString(misc), TextView.BufferType.EDITABLE);
		TextWatcher scoreWatch = new DatabaseEditWatcher(searchClause, searchArgs, "int_data_1", dataWrapper);
		editScore.addTextChangedListener(scoreWatch);
		row.addView(editScore);
		
		//make the trained checkbox
		CheckBox trndBox = new CheckBox(con);
		if (isTrained == 1)
		{
			trndBox.setChecked(true);
		}
		CheckboxEditWatcher trndWatch = new CheckboxEditWatcher(searchClause, searchArgs, "int_data_2", con);
		trndBox.setOnCheckedChangeListener(trndWatch);
		row.addView(trndBox);
		
		layout.addView(row);
	}

	public void GenerateDatabaseHeader(TableLayout layout) 
	{
		//create row (used in tableView as a single row, we will only generate one row
		TableRow row = new TableRow(con);
		row .setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		
		TextView label1 = new TextView(con);
    	TextView label2 = new TextView(con);
    	TextView label3 = new TextView(con);
		label1.setText("Skill Name");
		label2.setText("Misc Mod");
		label3.setText("Trained?");
		label1.setTextColor(Color.BLACK);
		label2.setTextColor(Color.BLACK);
		label3.setTextColor(Color.BLACK);
		row.addView(label1);
		row.addView(label2);
		row.addView(label3);
		
		layout.addView(row);
	}
	//i dont know if this is really usefull, its at least a space/sanity saver
	private int dpToPix(int dp)
	{
		return (int)(dp * pixelDensityScale);
	}

	//returns the type for the database lookups
	public int getDatabaseType() 
	{
		return CharDatabaseHelper.SKILL_SCORE;
	}

	public String getName() 
	{
		return name;
	}
}
