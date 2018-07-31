/*
 * NetworkServer.java
 *
 * Author:  snowdog
 * Created: January 9, 2006, 12:35 PM
 *
 * $Id: NetworkServer.java,v 1.2 2006/01/24 04:28:33 snowdog_ Exp $
 *
 * ====================================================================
 * Copyright (C) 2005-2006 The OpenRPG Project (www.openrpg.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License on www.gnu.org for more details.
 * ====================================================================
 */

package openrpg2.common.core.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;
import openrpg2.common.core.ORPGMessage;
import openrpg2.common.core.ORPGMessageQueue;
import openrpg2.common.core.ORPGSettingManager;
import openrpg2.common.module.NetworkedModule;


/**
 * Network Server. This class represents the interface between all network
 * functions of the OpenRPG server and the remainder of the application.
 * This is intended to be a black-box object such that network operations are
 * wholely contained and implemented within this object. All interaction with
 * the network functions of OpenRPG should occure though this objects API.
 * @author snowdog
 */

public class NetworkServer implements NetworkMessageRelay, NetworkConnectionNotifier, NetworkModuleProvider{
    private ServerSocketChannel ssc = null;
    private Selector selector = null;
    private boolean initDone = false;
    private boolean runFlag = false;
    private InetSocketAddress svrAddress = null;
    private ORPGMessageQueue inQueue;
    private ORPGMessageQueue outQueue;
    private Thread serverThread = null;
    private Thread monitorThread = null;
    private int nextId = 1;
    private Object idLock = new Object();
    private NetworkServer self = this;
    private NetworkThreadPool threadPool;
    private Hashtable clients  = new Hashtable();
    private Object monitorLock = new Object();
    private NetworkConnectionAlerter connectionState = new NetworkConnectionAlerter();
    private Logger log = Logger.getLogger(this.getClass().getName());
    
    static final int DEFAULT_THREAD_POOL_SIZE = 5;
    static final String SERVER_SETTINGS_FILENAME = "server.properties";
    static final String SETTING_DEFAULT_IP = "defaultIP";
    static final String SETTING_DEFAULT_PORT = "defaultPort";
    private int threadPoolSize = DEFAULT_THREAD_POOL_SIZE;
    
    
    /**
     * Create an OpenRPG NetworkServer object. This object contains all the operational code for managing new connections and managing sending and retrieving of messages via internal message queues.
     */
    public NetworkServer() {
        inQueue = new ORPGMessageQueue();  //queue for messages from network to handlers above
        outQueue = new ORPGMessageQueue(); //queue for spooling outgoing messages
        svrAddress = new InetSocketAddress(NetworkConnection.DEFAULT_HOST,NetworkConnection.DEFAULT_PORT);
    }
    
    /**
     * Sets the socket and IP address from which to host network connections from. This method is only viable before initializeServer() is called. Once initializeServer() has been called the InetSocketAddress of the server cannot be changed.
     * @param serverAddress The InetSocketAddress to host connections on with this instance of the server
     * @return true if server address was changes. Once server is initialized this will always return false;
     */
    public boolean setServerAddress(InetSocketAddress serverAddress){
        if (!initDone) {
            svrAddress = serverAddress;
            return true;
        }
        return false;
    }
    
    /**
     * Gets the default ip and port to run the server on by checking for user perferences first and then falling back on the hard coded defaults if no preferences exist.
     * @return InetSocketAddress to run server on.
     */
    public InetSocketAddress getDefaultAddress(){

        Properties netProps = ORPGSettingManager.loadSettings(SERVER_SETTINGS_FILENAME);
        String dfip = netProps.getProperty(SETTING_DEFAULT_IP);
        String dfport = netProps.getProperty(SETTING_DEFAULT_PORT);
        boolean resave = false;
        int port;
        if ( dfip == null ){
            dfip = NetworkConnection.DEFAULT_HOST;
            netProps.setProperty(SETTING_DEFAULT_IP,dfip);
            resave = true;
        }
        
        if ( dfport == null){
            port = NetworkConnection.DEFAULT_PORT;
            dfport = Integer.toString(port);
            netProps.setProperty(SETTING_DEFAULT_PORT,dfport);
            resave = true;
        } else {
            port = Integer.parseInt(dfport);
        }
        if (resave){
            ORPGSettingManager.saveSettings(netProps,SERVER_SETTINGS_FILENAME); //write the settings to settings file for later.
        }
        return new InetSocketAddress(dfip,port);
        
    }
    
    /**
     * Internal Method. Gets the next client Id number.
     * @return unique client id number
     */
    protected int getNextId(){
        synchronized(idLock){
            int idNum = nextId;
            nextId++;
            return idNum;
        }
    }
    
    /**
     * inserts a NetworkConnection into the internal client list so that outbound messages may be directed without scanning for client ID numbers
     * @param newClient The client NetworkConnection to add
     * @return true if insert successful, false if failed.
     */
    protected boolean insert(NetworkConnection newClient){
        //TODO: add error checking to prevent replacement of clientId keys
        int clientId = newClient.getId();
        synchronized(clients){
            clients.put(new Integer(clientId), newClient);
        }
        connectionState.announceConnection(clientId);
        return true;
    }
    
    protected void removeConnection(int connectionId){
        synchronized(clients){
            clients.remove(new Integer(connectionId));
        }
        connectionState.announceDisconnection(connectionId);
    }
    
    /**
     * startOutboundMonitor creates a thread that monitors for new ORPGMessages
     * being added to the outbound message queue. When a message arrives on 
     * the queue this thread is awakened and attempts to flush the queue through
     * the network. This thread stays in a preempted state while not actively 
     * delivering messages.
     */
    private void startOutboundMonitor(){
        monitorThread = new Thread(){
            public void run(){
                while(runFlag){
                    try{
                        synchronized(monitorLock){ monitorLock.wait(); }
                    }catch(InterruptedException e){
                        e.printStackTrace();
                        this.interrupted(); //clear inturrupted flag
                    }
                    while(outQueue.hasRemaining()){
                        delegateMessageDelivery(outQueue.pullMessage()); 
                    }
                }
                log.finer("Monitor thread terminating");
            }
        };
        monitorThread.start();
    }
    
    
    /**
     * delegateMessageDelivery is a helper method to the thread in startOutboundMonitor.
     * This method pulls service threads from the network thread pool and tasks them to 
     * deliver copies of the supplied ORPGMessage to each client specified in the message's
     * recipientList. This method may block during execution if no service threads are available.
     * @param m the ORPGMessage to deliver.
     */
    private void delegateMessageDelivery(ORPGMessage m){
        NetworkServiceThread nst=null;
        
        //get a message to handle and its associated recipient list
        int[] destId = m.getFinalRecipientList();
        
        //loop though recipient list
        for (int i = 0; i < destId.length; i++) {
            
            //wait/block until a service thread becomes available (could be a while if network is very busy)
            do {
                nst = threadPool.getServiceThread();
            } while (nst == null);
            
            //locate the NetworkConnection for the each destination id and tell the service thread to handle the message
            if (clients.containsKey(new Integer(destId[i]))){
                NetworkConnection con = (NetworkConnection)clients.get(new Integer(destId[i]));
                nst.serviceChannel(con,  m.asNetworkMessage());
            }
        }
    }
    
    /**
     * Initializes the NetworkServer object.
     * During initialization the server socket is bound to the InetSocketAddress (svrAddress)
     * and the NetworkThreadPool is generated to service the network connections.
     * @return true if initialization completed ok
     */
    private boolean initializeServer(){
        return initializeServer(svrAddress);
    }
    
    /**
     * initializes the server using a specific InetSocketAddress
     * @see initialize()
     * @param serverAddress InetSocketAddress to bind server to
     * @return true if initialization completed ok
     */
    private boolean initializeServer(InetSocketAddress serverAddress){
        try{
            selector = Selector.open();
            ssc = ServerSocketChannel.open();
            ssc.socket().bind(serverAddress);
            log.info("SERVER: Bound to "+ssc.socket().getInetAddress().getHostAddress()+" on port "+ssc.socket().getLocalPort());
            ssc.configureBlocking(false);
            ssc.register(selector,SelectionKey.OP_ACCEPT);
        } catch (Exception e){
            log.warning("EXCEPTION DURING INIT: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
        //set the initDone flag so serverAddress cannot be changed
        initDone = true;
        
        //set up the network thread pool
        threadPool = new NetworkThreadPool(self, threadPoolSize );
        
        return true;
    }
    
    /**
     * starts the NetworkServer handling messages and accepting new connections
     */
    public void startServer(){
        //make sure server initialized before starting selection thread...
        if (!initDone){ initializeServer(); }
        runFlag=true;
        serverThread = new Thread(){
            public void run(){
                int readyKeys = 0;
                while(runFlag){
                    try{
                        readyKeys = selector.select(1000);
                    }catch( ClosedSelectorException e){
                        log.warning("CLOSED SELECTOR EXCEPTION IN SERVER: "+e.getMessage());
                        e.printStackTrace();
                        stopServer();
                    }catch( IOException ioe){
                        log.warning("EXCEPTION IN SERVER: "+ioe.getMessage());
                        ioe.printStackTrace();
                    }
                    if ((readyKeys > 0)&&(runFlag)){
                        //keys ready for processing
                        Set readySet = selector.selectedKeys();
                        for( Iterator i = readySet.iterator(); i.hasNext();){
                            SelectionKey k = (SelectionKey)i.next();
                            i.remove();
                            
                            if(k.isAcceptable()){
                                NetworkAcceptThread acceptThread = new NetworkAcceptThread(self, ssc, selector);
                                acceptThread.start();
                            } else {
                                NetworkServiceThread nst = threadPool.getServiceThread();
                                if (nst != null){
                                    //networkServiceThread available to handle request
                                    nst.serviceChannel(k);
                                }else{
                                    // if no NetworkServiceThreads are currently available to handle
                                    // the key it will be ignored and handled on a later cycle though
                                    // the selector as the key will still be in the selected set.
                                    //because this thread can run much faster than the servicethreads
                                    //sleep for a short period of time to allow service threads to complete
                                    try{
                                        sleep(100);
                                    }catch(InterruptedException ie){ }
                                }
                            }
                        }
                    } else{
                        // This loop occurs only when there are no channels ready for IO
                        // after the 1 second delay on the selector readiness scan
                        //
                        // server should be considered "idle" in this situation
                    }
                }
                log.finer("[DEBUG] Main select thread terminating");
            }
        };
        serverThread.start();
        startOutboundMonitor();
        
        
        
    }
    
    /**
     * stops the network functions of the NetworkServer
     */
    public void stopServer(){
        runFlag=false;
        threadPool.shutdownThreads();
        synchronized(monitorLock){
            monitorLock.notify(); //let the outbound monitor know a message is ready
        }
        try{ Thread.sleep(1000); }//wait 2 seconds to let threads finish closing
        catch(InterruptedException e){}
        log.finer("[DEBUG] killing connections");
        Enumeration en = clients.keys();
        while(en.hasMoreElements()){
            Integer i = (Integer)en.nextElement();
            NetworkConnection n = (NetworkConnection)clients.get(i);
            n.disconnect();
        }
        
    }
    
    /**
     * send an ORPGMessage to the network. ORPGMessages must include routing information in their header or message will be discarded.
     * @param msg the ORPGMessage object to send via the network
     */
    public void sendMessage(ORPGMessage msg){
        outQueue.putMessage(msg);
        synchronized(monitorLock){
            monitorLock.notify(); //let the outbound monitor know a message is ready
        }
    }
    
    /**
     * Internal Method used to transfer messages from the NetworkServiceThreads to the internal inbound message queue
     * @param msg ORPGMessage to send to inbound message queue to be passed to higher level message handlers
     */
    protected void putMessage(ORPGMessage msg){
        inQueue.putMessage(msg);
    }
    
    public Observable addMessageQueueObserver(Observer o){
        inQueue.addObserver(o);
        return outQueue; //return reference to object for comparison only in case more than one observable is being monitored.
    }
    
    /**
     * Returns the number of messages that are pending network send in the internal outbound message queue
     * @return The number of messages remaining to be sent
     */
    public int pendingOutboundMessages(){
        return outQueue.size();
    }
    
    public boolean hasClientId(int clientId){
        Integer id = new Integer(clientId);
        if (clients.containsKey(id)){
            return true;
        }
        return false;
    }
    
    public boolean isClientConnected(int clientId){
        Integer id = new Integer(clientId);
        if (clients.containsKey(id)){
            NetworkConnection nc = (NetworkConnection)clients.get(id);
            if (nc != null){
                return nc.getSocketChannel().isConnected();
            }
        }
        return false;
    }
    
    protected boolean getRunFlag(){
        return runFlag;
    }
    
    //methods to satisfy NetworkMessageRelay interface
    public boolean putORPGMessage(ORPGMessage msg){
        sendMessage(msg);
        return true;
    }
    /**
     * Retrieve first available ORPGMessage object
     * @throws openrpg2.common.core.network.NoMessageAvailableException Thrown if no messages are available for retrieval
     * @return ORPGMessage object
     */
    public ORPGMessage getORPGMessage() throws NoMessageAvailableException{
        ORPGMessage msg =  inQueue.pullMessage();
        if (msg == null){ throw new NoMessageAvailableException(); }
        return msg;
    }
    /**
     * Check if ORPGMessage can be retrieved with getORPGMessage() method
     * @return True if 1 or more ORPGMessages are ready for retrieval otherwise false.
     */
    public boolean hasORPGMessage(){
        if (inQueue.hasRemaining()) return true;
        return false;
    }
    
    /**
     * Required for implementation of NetworkConnectionNotifier interface.
     * Allows other core components to be notified when a client connects or disconnects.
     * @param o An Observer Object to register for network state notification
     */
    public void registerNetworkConnectionObserver(Observer o){
        connectionState.addObserver(o);

        
    }
    
    /**
     * Required for implementation of NetworkModuleProvider interface.
     *
     */
    public NetworkedModule getNetworkModule(){
        return new NetworkServerModule(this);
    }
    
}
