/**
 * A library to interact with Virtual Worlds such as OpenSim
 * Copyright (C) 2012  Jitendra Chauhan, Email: jitendra.chauhan@gmail.com
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License,
 * or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package com.ngt.jopenmetaverse.tc;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.TimerTask;

import com.ngt.jopenmetaverse.shared.protocol.AgentDataUpdatePacket;
import com.ngt.jopenmetaverse.shared.protocol.AlertMessagePacket;
import com.ngt.jopenmetaverse.shared.protocol.AvatarAppearancePacket;
import com.ngt.jopenmetaverse.shared.protocol.Helpers.LogLevel;
import com.ngt.jopenmetaverse.shared.protocol.Packet;
import com.ngt.jopenmetaverse.shared.protocol.PacketType;
import com.ngt.jopenmetaverse.shared.sim.AgentManager.InstantMessageDialog;
import com.ngt.jopenmetaverse.shared.sim.GridClient;
import com.ngt.jopenmetaverse.shared.sim.GroupManager.Group;
import com.ngt.jopenmetaverse.shared.sim.GroupManager.GroupMember;
import com.ngt.jopenmetaverse.shared.sim.InventoryManager.InventoryFolder;
import com.ngt.jopenmetaverse.shared.sim.Settings;
import com.ngt.jopenmetaverse.shared.sim.events.EventObserver;
import com.ngt.jopenmetaverse.shared.sim.events.EventTimer;
import com.ngt.jopenmetaverse.shared.sim.events.ManualResetEvent;
import com.ngt.jopenmetaverse.shared.sim.events.PacketReceivedEventArgs;
import com.ngt.jopenmetaverse.shared.sim.events.am.InstantMessageEventArgs;
import com.ngt.jopenmetaverse.shared.sim.events.group.CurrentGroupsEventArgs;
import com.ngt.jopenmetaverse.shared.sim.events.group.GroupMembersReplyEventArgs;
import com.ngt.jopenmetaverse.shared.sim.events.im.InventoryObjectOfferedEventArgs;
import com.ngt.jopenmetaverse.shared.sim.events.om.PrimEventArgs;
import com.ngt.jopenmetaverse.shared.sim.events.terrain.LandPatchReceivedEventArgs;
import com.ngt.jopenmetaverse.shared.sim.login.LoginProgressEventArgs;
import com.ngt.jopenmetaverse.shared.sim.login.LoginStatus;
import com.ngt.jopenmetaverse.shared.types.UUID;
import com.ngt.jopenmetaverse.shared.util.JLogger;
import com.ngt.jopenmetaverse.shared.util.Utils;
import com.ngt.jopenmetaverse.tc.commands.Command;
import com.ngt.jopenmetaverse.tc.commands.CommandFactory;
import com.ngt.jopenmetaverse.tc.commands.communication.*;
import com.ngt.jopenmetaverse.tc.commands.debug.rendering.DumpHeightMapSimpleSplat;
import com.ngt.jopenmetaverse.tc.commands.debug.rendering.DumpHeightMapSplatTexture;
import com.ngt.jopenmetaverse.tc.commands.debug.rendering.DumpTerrainTextures;
import com.ngt.jopenmetaverse.tc.commands.inventory.*;
import com.ngt.jopenmetaverse.tc.commands.prims.*;
import com.ngt.jopenmetaverse.tc.commands.rendering.DrawExampleCommand;
import com.ngt.jopenmetaverse.tc.commands.rendering.DrawAllPrimitivesCommand;
import com.ngt.jopenmetaverse.tc.commands.rendering.DrawPrimitiveCommand;
import com.ngt.jopenmetaverse.tc.commands.rendering.DrawTerrainAndAllPrimitivesCommand;
import com.ngt.jopenmetaverse.tc.commands.rendering.DrawTerrainCommand;
import com.ngt.jopenmetaverse.tc.commands.system.*;
import com.ngt.jopenmetaverse.tc.commands.agent.*;
import com.ngt.jopenmetaverse.tc.commands.appearance.AvatarInfoCommand;
import com.ngt.jopenmetaverse.tc.commands.appearance.CloneCommand;
import com.ngt.jopenmetaverse.tc.commands.appearance.CloneCommand2;
import com.ngt.jopenmetaverse.tc.commands.appearance.CloneCommand3;
import com.ngt.jopenmetaverse.tc.commands.appearance.PrintAppearanceCommand;
import com.ngt.jopenmetaverse.tc.commands.movement.*;

public class TestClient extends GridClient 
{
	 public UUID GroupID = UUID.Zero;
     public Map<UUID, GroupMember> GroupMembers;
     public Map<UUID, AvatarAppearancePacket> Appearances = new HashMap<UUID, AvatarAppearancePacket>();
     public boolean Running = true;
     public boolean GroupCommands = false;
     public String MasterName = "";
     public UUID MasterKey = UUID.Zero;
     public boolean AllowObjectMaster = false;
     public ClientManager ClientManager;
     public CommandFactory commands = new CommandFactory();
     //TODO Need to uncomment following
//     public VoiceManager VoiceManager;
     
     // Shell-like inventory commands need to be aware of the 'current' inventory folder.
     public InventoryFolder CurrentDirectory = null;

     private EventTimer updateTimer;
     private UUID GroupMembersRequestID;
     public Map<UUID, Group> GroupsCache = null;
     private ManualResetEvent GroupsEvent = new ManualResetEvent(false);

     /// <summary>
     /// 
     /// </summary>
     public TestClient(ClientManager manager)
     {
    	 
    	 
         ClientManager = manager;

         TimerTask task = new TimerTask(){
			@Override
			public void run() {
				updateTimer_Elapsed();
			}
         };
         updateTimer = new EventTimer(task);
//         updateTimer.Elapsed += new System.Timers.ElapsedEventHandler(updateTimer_Elapsed);
         

         //Commands get automtically registered themselves
         RegisterAllCommands();

         InitializeClient();

         network.RegisterCallback(PacketType.AgentDataUpdate, new EventObserver<PacketReceivedEventArgs>()
        		 {
					@Override
					public void handleEvent(Observable o,
							PacketReceivedEventArgs arg) {
						AgentDataUpdateHandler(o, arg);						
					}
        		 }
        		 );
                  
         network.RegisterLoginProgressCallback(new EventObserver<LoginProgressEventArgs>()
         {
			@Override
			public void handleEvent(Observable o, LoginProgressEventArgs arg) {
				LoginHandler(o, arg);
			}       	 
         });         
               
         self.registerIM(new EventObserver<InstantMessageEventArgs>()
                 {
 			@Override
 			public void handleEvent(Observable o, InstantMessageEventArgs arg) {
 				try {
					Self_IM(o, arg);
				} catch (Exception e) {
					JLogger.error(Utils.getExceptionStackTraceAsString(e));
				}
 			}       	 
          });
         
//         groups.GroupMembersReply += GroupMembersHandler;
       groups.registerOnGroupMembersReply(new EventObserver<GroupMembersReplyEventArgs>()
               {
				@Override
				public void handleEvent(Observable sender,
						GroupMembersReplyEventArgs arg) {
					GroupMembersHandler(sender, arg);
				}				
               }
			);

         
//         inventory.InventoryObjectOffered += Inventory_OnInventoryObjectReceived;            
         inventory.registerOnInventoryObjectOffered(new EventObserver<InventoryObjectOfferedEventArgs>()
        		 {
					@Override
					public void handleEvent(Observable o,
							InventoryObjectOfferedEventArgs arg) {
						Inventory_OnInventoryObjectReceived(o, arg);
					}
        		 });
         
//         network.RegisterCallback(PacketType.AvatarAppearance, AvatarAppearanceHandler);
       network.RegisterCallback(PacketType.AvatarAppearance, new EventObserver<PacketReceivedEventArgs>(){
			@Override
			public void handleEvent(Observable arg0, PacketReceivedEventArgs arg1) {
				AvatarAppearanceHandler(arg0, (PacketReceivedEventArgs)arg1);
			}});
         
//         network.RegisterCallback(PacketType.AlertMessage, AlertMessageHandler);
         	
       network.RegisterCallback(PacketType.AlertMessage, new EventObserver<PacketReceivedEventArgs>(){
			@Override
			public void handleEvent(Observable arg0, PacketReceivedEventArgs arg1) {
					AlertMessageHandler(arg0, (PacketReceivedEventArgs)arg1);
			}});

         
       this.terrain.registerOnLandPatchReceived(new EventObserver<LandPatchReceivedEventArgs>()
       {
		@Override
		public void handleEvent(Observable sender,
				LandPatchReceivedEventArgs arg) {
			handleLandPatchReceived(arg);
		}
       });
       
       this.objects.registerOnObjectUpdate(new EventObserver<PrimEventArgs>()
       {
		@Override
		public void handleEvent(Observable sender, PrimEventArgs arg) {
			//System.out.println("Object Update " + arg.getPrim().LocalID);
		} 
       });
       
       
         //TODO need to uncomment following
//         VoiceManager = new VoiceManager(this);

         updateTimer.schedule(0, 500);
     }

//     public float[][] heighttable = new float[256][256];
     
	 //TODO only for debugging
 	private static void printfloatarray(float[] array)
 	{
 		int count = 0;
 		while(count < array.length)
 		{
 			System.out.println("");
 			for(int x =0 ; x < 50 && count <  array.length	 ; x++)
 			{
 				System.out.print((int)array[count++] + " ");
 			}
 		}
 	}

	 //TODO only for debugging
     void handleLandPatchReceived(LandPatchReceivedEventArgs e)
     {
//			System.out.println(String.format("Land Patch Received for X = %d Y = %d...", e.getX(), e.getY())); 
//			printfloatarray(e.getHeightMap());
     }
     
     void Self_IM(Object sender, InstantMessageEventArgs e) throws Exception
     {
         boolean groupIM = e.getIM().GroupIM && GroupMembers != null && GroupMembers.containsKey(e.getIM().FromAgentID) ? true : false;

         if (e.getIM().FromAgentID == MasterKey || (GroupCommands && groupIM))
         {
             // Received an IM from someone that is authenticated
        	 System.out.println(String.format("<%s (%s)> %s: %s (@%s:%s)", e.getIM().GroupIM ? "GroupIM" : "IM", e.getIM().Dialog, e.getIM().FromAgentName, e.getIM().Message, 
                 e.getIM().RegionID.toString(), e.getIM().Position.toString()));

             if (e.getIM().Dialog == InstantMessageDialog.RequestTeleport)
             {
            	 System.out.println("Accepting teleport lure.");
                 self.TeleportLureRespond(e.getIM().FromAgentID, e.getIM().IMSessionID, true);
             }
             else if (
                 e.getIM().Dialog == InstantMessageDialog.MessageFromAgent ||
                 e.getIM().Dialog == InstantMessageDialog.MessageFromObject)
             {
                 ClientManager.Instance.DoCommandAll(e.getIM().Message, e.getIM().FromAgentID);
             }
         }
         else
         {
             // Received an IM from someone that is not the bot's master, ignore
             System.out.println(String.format("<%s (%s)> %s (not master): %s (@%s:%s)", e.getIM().GroupIM ? "GroupIM" : "IM", e.getIM().Dialog, e.getIM().FromAgentName, e.getIM().Message,
                 e.getIM().RegionID.toString(), e.getIM().Position.toString()));
             return;
         }
     }

     /// <summary>
     /// Initialize everything that needs to be initialized once we're logged in.
     /// </summary>
     /// <param name="login">The status of the login</param>
     /// <param name="message">Error message on failure, MOTD on success.</param>
     public void LoginHandler(Object sender, LoginProgressEventArgs e)
     {
         if (e.getStatus().equals(LoginStatus.Success))
         {
             // Start in the inventory root folder.
             CurrentDirectory = inventory.getStore().getRootFolder();
             if(CurrentDirectory == null)
            	 System.out.println("Warn: CurrentDirectory is not initialized");
         }
     }

   public void RegisterAllCommands()
   {
      commands.registerCommand(new HelpCommand(this));
      
      //Movement
      commands.registerCommand(new BackCommand(this));
      commands.registerCommand(new CrouchCommand(this));
      commands.registerCommand(new FlyCommand(this));
      commands.registerCommand(new FlyToCommand(this));
      commands.registerCommand(new ForwardCommand(this));
      commands.registerCommand(new FollowCommand(this));
      
      
      commands.registerCommand(new GoHomeCommand(this));
      commands.registerCommand(new GotoCommand(this));
      commands.registerCommand(new GotoLandmarkCommand(this));
      commands.registerCommand(new JumpCommand(this));

      commands.registerCommand(new LeftCommand(this));
      commands.registerCommand(new LocationCommand(this));
      commands.registerCommand(new MovetoCommand(this));
      commands.registerCommand(new RightCommand(this));
      commands.registerCommand(new SetHomeCommand(this));
      commands.registerCommand(new SitCommand(this));
      commands.registerCommand(new SitOnCommand(this));

      commands.registerCommand(new StandCommand(this));
      commands.registerCommand(new TurnToCommand(this));
      
      
      //Inventory
      commands.registerCommand(new ScriptCommand(this));
      commands.registerCommand(new InventoryCommand(this));      
      commands.registerCommand(new ListContentsCommand(this));
      commands.registerCommand(new ChangeDirectoryCommand(this));

      //Agents
      commands.registerCommand(new PlayAnimationCommand(this));
      commands.registerCommand(new WhoCommand(this));
      
      commands.registerCommand(new WaitForLoginCommand(this));

      //Avatars
      commands.registerCommand(new BotsCommand(this));
      
      //Appearance
      commands.registerCommand(new CloneCommand(this));
      commands.registerCommand(new CloneCommand2(this));
      commands.registerCommand(new CloneCommand3(this));
      commands.registerCommand(new AvatarInfoCommand(this));
      commands.registerCommand(new PrintAppearanceCommand(this));
      
      
      //Communication
      commands.registerCommand(new EchoMasterCommand(this));
      commands.registerCommand(new ImCommand(this)) ;
      commands.registerCommand(new SayCommand(this));
      commands.registerCommand(new WhisperCommand(this)) ;
      commands.registerCommand(new ShoutCommand(this)) ;
      commands.registerCommand(new ImGroupCommand(this)) ;
      
      
      //Prims
      commands.registerCommand(new FindObjectsCommand(this));
      commands.registerCommand(new PrimInfoCommand(this));
      
      //Rendering
      commands.registerCommand(new DrawExampleCommand(this));
      commands.registerCommand(new DrawTerrainCommand(this));
      commands.registerCommand(new DrawAllPrimitivesCommand(this));
      commands.registerCommand(new DrawPrimitiveCommand(this));
      commands.registerCommand(new DrawTerrainAndAllPrimitivesCommand(this));
      
      //Debug Commands
      commands.registerCommand(new DumpTerrainTextures(this));
      commands.registerCommand(new DumpHeightMapSimpleSplat(this));
      commands.registerCommand(new DumpHeightMapSplatTexture(this));
   }

   
   private void InitializeClient()
   {
       this.settings.MULTIPLE_SIMS = false;

       this.settings.USE_LLSD_LOGIN = false;   
       
       this.settings.USE_INTERPOLATION_TIMER = false;
       this.settings.ALWAYS_REQUEST_OBJECTS = true;
       this.settings.ALWAYS_DECODE_OBJECTS = true;
       this.settings.OBJECT_TRACKING = true;
       this.settings.ENABLE_SIMSTATS = true;
       this.settings.FETCH_MISSING_INVENTORY = true;
       this.settings.SEND_AGENT_THROTTLE = true;
       this.settings.SEND_AGENT_UPDATES = true;
       this.settings.STORE_LAND_PATCHES = true;

       this.settings.USE_ASSET_CACHE = true;
//       this.settings.ASSET_CACHE_DIR = Path.Combine(userDir, "cache");
//       this.assets.Cache.AutoPruneEnabled = false;

       this.throttle.setTotal(5000000f);
       this.settings.THROTTLE_OUTGOING_PACKETS = false;
       this.settings.LOGIN_TIMEOUT = 120 * 1000;
       this.settings.SIMULATOR_TIMEOUT = 180 * 1000;
       this.settings.MAX_CONCURRENT_TEXTURE_DOWNLOADS = 20;

       Settings.LOG_LEVEL = LogLevel.Debug;
       Settings.LOG_RESENDS = false;
       
//       this.Self.Movement.AutoResetControls = false;
//       this.Self.Movement.UpdateInterval = 250;
//       RegisterClientEvents(client);
//       SetClientTag();
   }

   
     public void ReloadGroupsCache() throws InterruptedException
     {
    	 EventObserver<CurrentGroupsEventArgs> eobserver =  new EventObserver<CurrentGroupsEventArgs>()
        		 {
					@Override
					public void handleEvent(Observable sender,
							CurrentGroupsEventArgs arg) {
						Groups_CurrentGroups(sender, arg);
						
					} }; 
    	 
         groups.registerOnCurrentGroups(eobserver);  
         
         groups.RequestCurrentGroups();
         GroupsEvent.waitOne(10000);
         groups.unregisterOnCurrentGroups(eobserver);
         GroupsEvent.reset();
     }

     void Groups_CurrentGroups(Object sender, CurrentGroupsEventArgs e)
     {
         if (null == GroupsCache)
             GroupsCache = e.getGroups();
         else
             synchronized (GroupsCache) 
             { GroupsCache = e.getGroups(); }
         GroupsEvent.set();
     }

     public UUID GroupName2UUID(String groupName) throws InterruptedException
     {
         UUID tryUUID;
         if ((tryUUID =UUID.Parse(groupName))!=null)
                 return tryUUID;
         if (null == GroupsCache) {
                 ReloadGroupsCache();
             if (null == GroupsCache)
                 return UUID.Zero;
         }
         synchronized(GroupsCache) {
             if (GroupsCache.size() > 0) {
                 for (Group currentGroup : GroupsCache.values())
                     if (currentGroup.Name.equalsIgnoreCase(groupName.toLowerCase()))
                         return currentGroup.ID;
             }
         }
         return UUID.Zero;
     }      
     
     private void updateTimer_Elapsed()
     {
         for (Command c : commands.values())
             if (c.Active)
                 c.Think();
     }

     private void AgentDataUpdateHandler(Object sender, PacketReceivedEventArgs e)
     {
         AgentDataUpdatePacket p = (AgentDataUpdatePacket)e.getPacket();
         if (p.AgentData.AgentID.equals(e.getSimulator().Client.self.getAgentID()))
         {
             GroupID = p.AgentData.ActiveGroupID;
             
             GroupMembersRequestID = e.getSimulator().Client.groups.RequestGroupMembers(GroupID);
         }
     }

     private void GroupMembersHandler(Object sender, GroupMembersReplyEventArgs e)
     {
         if (!e.getRequestID().equals(GroupMembersRequestID)) return;

         GroupMembers = e.getMembers();
     }

     private void AvatarAppearanceHandler(Object sender, PacketReceivedEventArgs e)
     {
         Packet packet = e.getPacket();
         
         AvatarAppearancePacket appearance = (AvatarAppearancePacket)packet;

         synchronized (Appearances) 
         {Appearances.put(appearance.Sender.ID, appearance);}
     }

     private void AlertMessageHandler(Object sender, PacketReceivedEventArgs e)
     {
         Packet packet = e.getPacket();
         
         AlertMessagePacket message = (AlertMessagePacket)packet;

         try {
			JLogger.info("[AlertMessage] " + Utils.bytesToString(message.AlertData.Message));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
     }
    
     private void Inventory_OnInventoryObjectReceived(Object sender, InventoryObjectOfferedEventArgs e)
     {
         if (!MasterKey.equals(UUID.Zero))
         {
             if (!e.getOffer().FromAgentID.equals(MasterKey))
                 return;
         }
         else if (GroupMembers != null && !GroupMembers.containsKey(e.getOffer().FromAgentID))
         {
             return;
         }

         e.setAccept(true);
         return;
     }
}
