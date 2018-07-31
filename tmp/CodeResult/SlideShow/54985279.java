/*
 * Lucane - a collaborative platform
 * Copyright (C) 2004  Vincent Fiack <vfiack@mail15.com>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.lucane.applications.slideshow;

import org.lucane.applications.slideshow.gui.FollowerWindow;
import org.lucane.applications.slideshow.gui.PreloadDialog;
import org.lucane.applications.slideshow.gui.StarterWindow;
import org.lucane.applications.slideshow.net.SlideClient;
import org.lucane.applications.slideshow.net.SlideServer;
import org.lucane.client.*;
import org.lucane.client.widgets.DialogBox;
import org.lucane.common.*;
import org.lucane.common.net.ObjectConnection;

import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;

public class SlideShow
extends Plugin
{		
	//used for starter
	private ConnectInfo[] friends;
	private SlideServer slideServer;	
	private StarterWindow starterWindow;
	
	//used for follower
	private SlideClient slideClient;
	private FollowerWindow followerWindow;
	
	public SlideShow()
	{
	}
	
	public Plugin newInstance(ConnectInfo[] friends)
	{
		return new SlideShow(friends);
	}
	
	public SlideShow(ConnectInfo[] friends)
	{
		this.friends = friends;
	}
	
	public void load(ObjectConnection oc, ConnectInfo who, String data)
	{
		this.friends = new ConnectInfo[] {who};
		this.slideClient = new SlideClient(this, oc);
	}
	
	public void start()
	{		
		//-- select directory
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new ImageFileFilter(true, tr("lbl.images")));
		if(chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
		{
			exit();
			return;
		}
		
		PreloadDialog dialog = new PreloadDialog(this);
		dialog.show();
		
		//-- read directory
		File dir = chooser.getCurrentDirectory();
		File[] files = dir.listFiles(new ImageFileFilter(false, tr("lbl.images")));
		dialog.setMaxValue(files.length);		
		
		//-- preload images
		List list = FileUtils.sortFiles(files);		
		Object[] objects = FileUtils.preloadImages(this, list, dialog);		
			
		//-- show window
		starterWindow = new StarterWindow(this);
		starterWindow.setImages(objects);
		starterWindow.show();
		dialog.dispose();
		
		slideServer = new SlideServer(this);
		slideServer.sendInvitations(friends);
	}
	
	public void follow()
	{
		String msg = tr("msg.followSlideShow").replaceAll("%1", friends[0].getName());
		if(DialogBox.question(getTitle(), msg))
		{
			followerWindow = new FollowerWindow(this); 
			slideClient.acceptSlideShow();
			followerWindow.show();
		}
		else
			slideClient.rejectSlideShow();
	}	
	
	//-- 
	
	public SlideServer getServer()
	{
		return slideServer;
	}
	
	public StarterWindow getStarterWindow()
	{
		return starterWindow;
	}
	
	public SlideClient getClient()
	{
		return slideClient;		
	}
	
	public FollowerWindow getFollowerWindow()
	{
		return followerWindow;
	}
}

