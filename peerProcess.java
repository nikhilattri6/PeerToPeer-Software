import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*Ahmad Log Code*/
class MyLogClass{
	private DateFormat myTime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Date myDate= new Date();
	private BufferedWriter output;
	public StringBuffer logchange;/*Used but
	 								where*/
	public boolean logCreated=false;
	public MyLogClass(BufferedWriter output){
		myTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.output = output;
	}
	public void createLogEntry(String logType, int id1, int id2) {
		System.out.println("here");
		switch (logType) {
		case "connectionTo":
			myDate=new Date();
			logchange = new StringBuffer();
			logchange.append(myTime.format(myDate)
					+": Peer ["
					+id1
					+"] makes a connection to Peer ["
					+id2
					+"].");
			logCreated=true;
			System.out.println(logchange.toString());
			break;

		case "connectionFrom":
			myDate=new Date();
			logchange = new StringBuffer();
			logchange.append(myTime.format(myDate)
					+": Peer ["
					+id1
					+"] is connected from Peer ["
					+id2
					+"].");
			logCreated=true;
			System.out.println(logchange.toString());
			break;

		case "changeOptimisticallyUnchokedNeighbor":
			myDate=new Date();
			logchange=new StringBuffer();
			logchange.append(myTime.format(myDate)
					+": Peer ["
					+id1
					+"] has the optimistically unchoked neighbor ["
					+id2
					+"].");
			logCreated=true;
			System.out.println(logchange.toString());
			break;

		case "unchoked":
			myDate=new Date();
			logchange=new StringBuffer();
			logchange.append(myTime.format(myDate)
					+": Peer ["
					+id1
					+"] is unchoked by ["
					+id2
					+"].");
			logCreated=true;
			System.out.println(logchange.toString());
			break;

		case "choking":
			logchange=new StringBuffer();
			logchange.append(myTime.format(myDate)
					+": Peer ["
					+id1
					+"] is choked by ["
					+id2
					+"].");
			logCreated=true;
			System.out.println(logchange.toString());
			break;

		case "receiveHaveMessage":
			logchange=new StringBuffer();
			logchange.append(myTime.format(myDate)
					+": Peer ["
					+id1
					+"] received the 'have' message from ["
					+id2
					+"].");
			logCreated=true;
			System.out.println(logchange.toString());
			break;

		case "receiveInterestedMessage":
			logchange=new StringBuffer();
			logchange.append(myTime.format(myDate)
					+": Peer ["
					+id1
					+"] received the 'interested' message from ["
					+id2
					+"].");
			logCreated=true;
			System.out.println(logchange.toString());
			break;

		case "receiveNotInterestedMessage":
			logchange=new StringBuffer();
			logchange.append(myTime.format(myDate)
					+": Peer ["
					+id1
					+"] received the 'not interested' message from ["
					+id2
					+"].");
			logCreated=true;
			System.out.println(logchange.toString());
			break;

		case "downloadComplete":
			logchange=new StringBuffer();
			logchange.append(myTime.format(myDate)
					+": Peer ["
					+id1
					+"] has downloaded the complete file.");
			logCreated=true;
			System.out.println(logchange.toString());
			break;
		default:
			System.out.println("no such log exists!.\n");
			break;
		}
		/*
		 * here we print the log message inside the logs file 
		 * after every iteration
		 */
		if (logCreated) {
			System.out.println("Creating Log between ids "+id1+" "+id2+" "+logchange.toString());
			try {
				System.out.println(logchange.toString());
				output.write(logchange.toString());
				output.newLine();
				output.flush();
				logCreated=false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	public void createLogEntry(int id1, int id2, int indexNum, int numOfPieces) {
		logchange=new StringBuffer();
		logchange.append(myTime.format(myDate)
				+": Peer ["
				+id1
				+"]  has downloaded the piece "
				+indexNum
				+" from ["
				+id2
				+"].\nNow the number of pieces it has is ["
				+numOfPieces
				+"].");
		logCreated=true;
		System.out.println(logchange.toString());
		if (logCreated) {   
			try {
				output.write(logchange.toString());
				output.newLine();
				output.flush();
				//System.out.println("buffered Writer Error");
				logCreated=false;
			} catch (IOException e) {
				//           TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	public void createLogEntry(int id1, int [] ids) {
		logchange=new StringBuffer();
		logchange.append(myTime.format(myDate)
				+": Peer ["
				+id1
				+"] has the preferred neighbors [");
		for (int i=0; i<ids.length;i++) {
			logchange.append(ids[i]);
			if(i==ids.length-1) {
				logchange.append("]");
				logCreated=true;
			}
			else {
				logchange.append(",");
			}
		}
		System.out.println(logchange.toString());
		if(logCreated) {
			try {
				output.write(logchange.toString());
				output.newLine();
				output.flush();
				logCreated=false;
				//System.out.println("buffered Writer Error");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
}
class CommonFileInformation{
	private int preferredNumberOfNeighbours;
	private int intervalOfUnchoking;
	private int intervalOfOptimisticUnchoking;
	private String myFileName;
	private int myFileSize;
	private int myPieceSize;
	public void setPreferredNumberOfNeighbours(int k){
		preferredNumberOfNeighbours = k;
	}
	public int getPreferredNumberOfNeighbours(){
		return preferredNumberOfNeighbours;
	}
	public void setIntervalOfUnchoking(int u){
		intervalOfUnchoking = u;
	}
	public int getIntervalOfUnchoking(){
		return intervalOfUnchoking;
	}
	public void setIntervalOfOptimisticUnchoking(int o){
		intervalOfOptimisticUnchoking = o;
	}
	public int getIntervalOfOptimisticUnchoking(){
		return intervalOfOptimisticUnchoking;
	}
	public void setMyFileName(String filename){
		myFileName = filename;
	}
	public String getMyFileName(){
		return myFileName;
	}
	public void setMyFileSize(int size){
		myFileSize = size;
	}
	public int getMyFileSize(){
		return myFileSize;
	}
	public void setMyPieceSize(int psize){
		myPieceSize = psize;
	}
	public int getMyPieceSize(){
		return myPieceSize;
	}
}
class PeerFileInformation{
	private boolean haveFile;
	private int peerId;
	private String nameOfHost;
	private int portNumber;
	private boolean completeFile;
	private int [] bitfieldArray;
	private int numberOfPieces=0;
	public boolean getHaveFile() {
		return haveFile;
	}
	public void setHaveFile(boolean haveFile) {
		this.haveFile = haveFile;
	}
	public int [] getmybitFieldArray(){
		return this.bitfieldArray;
	}
	public void setmybitFieldArray(int [] array){
		this.bitfieldArray=array;
	}
	public void updateBitField(int index){
		this.bitfieldArray[index]=1;
	}
	public  void updatingNumberofPieces(){
		this.numberOfPieces++;
	}
	public int getbitfieldArraylength(){
		return this.bitfieldArray.length;
	}
	public int getNumberOfPieces(){
		return numberOfPieces;
	}
	public void setPeerId(int p_id){
		this.peerId=p_id;
	}
	public int getPeerId(){
		return this.peerId;
	}
	public void setNameOfHost(String name_Host){
		this.nameOfHost=name_Host;
	}
	public String getNameOfHost(){
		return this.nameOfHost;
	}
	public void setportNumber(int p_Number){
		this.portNumber=p_Number;
	}
	public int getportNumber(){
		return this.portNumber;
	}
	public void setCompleteFile(boolean value){
		this.completeFile=value;
	}
	public boolean getCompleteFile(){
		return this.completeFile;
	}
	public void setBitFieldMessage(int [] bitFieldMessage){
		this.bitfieldArray=bitFieldMessage;
	}
}
class ConfigFileReader{
	Properties configFile;
	public ConfigFileReader(){
		configFile = new java.util.Properties();
		try {
			//changed form /Users/nikhil/Desktop
			configFile.load(new FileInputStream(System.getProperty("user.dir")+"/Common.cfg"));
		}catch(Exception eta){
			eta.printStackTrace();
		}
	}
	public String getProperty(String key){
		String value = this.configFile.getProperty(key);
		return value;
	}
}
//class MyLogClass{
//	private BufferedWriter writer;
//	public MyLogClass(BufferedWriter mywriter){
//		this.writer=mywriter;
//	}
//}
public class peerProcess {
	private static String path;
	private static CommonFileInformation myCommonFile;
	private static LinkedHashMap<Integer, PeerFileInformation> everyPeer;
	private static ConcurrentHashMap<Integer, ConnectionToPeer> concurrentPeers;
	private static PeerFileInformation currentPeer;
	private static byte[][] fileChunks;
	private static int currentHostID;
	private static int numberOfPeersHasCompletedFile = 0;
	private static File mydir;
	private static File myLog;
	private static MyLogClass everyLogs;
	//private static log myLogClass = new log(); //changed name since we have used it.
	//private static ConcurrentHashMap<int,Peer>peerConnections = new ConcurrentHashMap<>();
	//make PeerConnection Class and concurrent HashMap;
	/*
	 * Message Function Creation Nikhil Attri
	 * */

	public static byte[] createMessage(String type,int length,byte[]payload){
		char messageType = 0;
		if(type.equals("CHOKE") ||type.equals("UNCHOKE") ||type.equals("INTERESTED") || type.equals("NOT_INTERESTED")){
			if(type.equals("CHOKE")){
				messageType ='0';
			}
			else if(type.equals("UNCHOKE")){
				messageType ='1';
			}
			else if(type.equals("INTERESTED")){
				messageType ='2';
			}
			else{
				messageType ='3';
			}
			byte[] myMessage = new byte[length+4];
			byte[] myMessageLengthInBytes = ByteBuffer.allocate(4).putInt(length).array();
			int index=0;
			for(int i=0;i<myMessageLengthInBytes.length;i++){
				myMessage[index++]=myMessageLengthInBytes[i];
			}
			byte myMessageType =(byte)messageType;
			myMessage[index++]=myMessageType; 
			return myMessage;
		}
		else if(type.equals("HAVE") ||type.equals("BITFIELD") ||type.equals("REQUEST") || type.equals("PIECE")){
			if(type.equals("HAVE")){
				messageType ='4';
			}
			else if(type.equals("BITFIELD")){
				messageType ='5';
			}
			else if(type.equals("REQUEST")){
				messageType ='6';
			}
			else{
				messageType ='7';
			}
			byte[] myMessage = new byte[length+4];
			byte[] myMessageLengthInBytes = ByteBuffer.allocate(4).putInt(length).array();
			int index=0;
			for(int i=0;i<myMessageLengthInBytes.length;i++){
				myMessage[index++]=myMessageLengthInBytes[i];
			}
			byte myMessageType =(byte)messageType;
			myMessage[index++]=myMessageType; 
			for(int i=0;i<payload.length;i++){
				myMessage[index++]=payload[i];
			}
			return myMessage;
		}
		return null;
	}
	public static byte [] calculatePayloadHaveAndCreate(int indexOfPiece){
		byte[] payload = ByteBuffer.allocate(4).putInt(indexOfPiece).array();
		return createMessage("HAVE", 5, payload);
	}
	public static byte [] calculatePayloadRequestAndCreate(int indexOfPiece){
		byte[] payload = ByteBuffer.allocate(4).putInt(indexOfPiece).array();
		return createMessage("REQUEST", 5, payload);
	}
	public static byte [] calculatePayloadBitFieldAndCreate(int[] bitfield){
		int length = (4 * bitfield.length);
		byte[] payload = new byte[length];
		int index = 0;
		for(int i=0;i<bitfield.length;i++){
			byte[] myBitBytes = ByteBuffer.allocate(4).putInt(bitfield[i]).array();
			for(int j=0;j<myBitBytes.length;j++){
				payload[index++] = myBitBytes[j];
			}
		}
		return createMessage("BITFIELD", length+1, payload);
	}
	public static byte [] calculatePayloadPieceAndCreate(int indexOfPiece, byte[] pieces){
		int length =pieces.length;
		byte[] payload = new byte[4+pieces.length];
		int index=0;
		byte[] myIndexInBytes = ByteBuffer.allocate(4).putInt(indexOfPiece).array();
		for(int i=0;i<myIndexInBytes.length;i++){
			payload[index++]=myIndexInBytes[i];
		}
		for(int i=0;i<pieces.length;i++){
			payload[index++]=pieces[i];
		}
		return createMessage("PIECE", length+5, payload);

	}
	public static void createDirectory(){
		try {
			mydir = new File("peerNumber_" + currentHostID);
			if (!mydir.exists()) {
				mydir.mkdir();
				System.out.println("directory Made");
			}
			path  = System.getProperty("user.dir") + "/" + "peerNumber_"+currentHostID;
			myLog = new File(System.getProperty("user.dir") + "/" + "Log_For_Peer_Number_" + currentHostID + ".log");
			if (!myLog.exists())
				myLog.createNewFile();
			BufferedWriter fileWrite = new BufferedWriter(new FileWriter(myLog.getAbsolutePath(), true));
			fileWrite.flush();
			everyLogs = new MyLogClass(fileWrite);
			System.out.println(mydir.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void divideToChunks(int sizeOfFile,int currentHostID,int sizeOfPiece) {
		System.out.println("dividing into chunks function");
		byte[] filepices = null;
		try {
			//			mydir = new File("peerNumber_" + currentHostID);
			//			if (!mydir.exists()) {
			//				mydir.mkdir();
			//				System.out.println("directory Made");
			//			}
			//			myLog = new File(System.getProperty("user.dir") + "/" + "Log_For_Peer_Number_" + currentHostID + ".log");
			//			if (!myLog.exists())
			//				myLog.createNewFile();
			//			BufferedWriter fileWrite = new BufferedWriter(new FileWriter(myLog.getAbsolutePath(), true));
			//			fileWrite.flush();
			//			everyLogs = new MyLogClass(fileWrite);
			//			System.out.println(mydir.getAbsolutePath());
			createDirectory();
			//changed from  "/Users/nikhil/Desktop"  to System.getProperty("user.dir")
			BufferedInputStream dataFile = new BufferedInputStream(new FileInputStream(System.getProperty("user.dir")+ "/"+myCommonFile.getMyFileName()));                
			filepices = new byte[sizeOfFile];
			dataFile.read(filepices);
			dataFile.close();
		} catch (IOException e) {
			System.out.println("Function :- divide into chunks exception");
			e.printStackTrace();
		}
		int count =0;
		int i=0;
		System.out.println("while starting");
		System.out.println(sizeOfFile);
		while(count<sizeOfFile){
			if(count+sizeOfPiece<=sizeOfFile){
				int initialRange = count;
				int finalRanger = count + sizeOfPiece;
				fileChunks[i] = Arrays.copyOfRange(filepices, initialRange, finalRanger);
			}
			else{
				System.out.println(count+"lastcall");
				fileChunks[i] = Arrays.copyOfRange(filepices, count, sizeOfFile);
			}
			currentPeer.updatingNumberofPieces();
			if(currentPeer.getNumberOfPieces() == currentPeer.getbitfieldArraylength())
				currentPeer.setCompleteFile(true);
			i++;
			count+= sizeOfPiece;
		}
		System.out.println("while End");
	}
	public static void doCalculation(int currentHostID){
		System.out.println("docalculation Called");
		currentPeer = everyPeer.get(currentHostID);
		int sizeOfFile = myCommonFile.getMyFileSize();
		System.out.println("sizeOfFile is" +sizeOfFile);
		int sizeOfPiece = myCommonFile.getMyPieceSize();
		System.out.println("sizeOfPiece is" +sizeOfPiece);
		double numberOfChunks = (double)sizeOfFile / sizeOfPiece;
		System.out.println("number of chunks should be"+numberOfChunks);
		int finalNumberOfChunks = (int) Math.ceil(numberOfChunks);
		System.out.println("finalNumberOfChunks is" +finalNumberOfChunks);
		fileChunks = new byte[finalNumberOfChunks][];
		int [] Bitfield = new int [finalNumberOfChunks];
		if (!currentPeer.getCompleteFile()) {     
			Arrays.fill(Bitfield, 0);
			currentPeer.setBitFieldMessage(Bitfield);
			System.out.println("do not have complete file");
			createDirectory();
		}else{
			numberOfPeersHasCompletedFile++;
			Arrays.fill(Bitfield, 1);
			currentPeer.setBitFieldMessage(Bitfield);
			System.out.println("have complete file");
			divideToChunks(sizeOfFile,currentHostID,sizeOfPiece);
		}	
	}
	private static class myClientConnection extends Thread{
		@Override
		public void run(){
			byte [] myResponseByteArray = new byte[32];
			try {
				for(int eachIdInHashMap:everyPeer.keySet()){
					System.out.println("============="+currentHostID+"========="+eachIdInHashMap);
					if(eachIdInHashMap==currentHostID) {
						System.out.println("We are breaking becuase same ID " + eachIdInHashMap);
						break;
					}
					else{
						PeerFileInformation mycurrentPeerInformation = everyPeer.get(eachIdInHashMap);
						int portNumber = mycurrentPeerInformation.getportNumber();
						System.out.println("Connected to PortNumber "+portNumber+" For ID "+eachIdInHashMap);
						String host = mycurrentPeerInformation.getNameOfHost();
						//String host = InetAddress.getLocalHost().getHostAddress();
						System.out.println("host"+host);
						System.out.println("Port Number is "+ portNumber);
						Socket clientSocket = new Socket(host,portNumber);
						DataOutputStream clientWriter = new DataOutputStream(clientSocket.getOutputStream());
						clientWriter.flush();
						byte [] myHandShakeMessage = new byte[32];
						byte[] myHeader = new String("P2PFILESHARINGPROJ").getBytes();
						byte[] bitZeros = new String("0000000000").getBytes();
						//instead of eachIdInHashMap use currentHostID;
						byte[] myPeerId = ByteBuffer.allocate(4).putInt(currentHostID).array();
						int i=0;
						for(int j=0;j<myHeader.length;j++){
							myHandShakeMessage[i++]=myHeader[j];
						}
						for(int j=0;j<bitZeros.length;j++){
							myHandShakeMessage[i++]=bitZeros[j];
						}
						for(int j=0;j<myPeerId.length;j++){
							myHandShakeMessage[i++]=myPeerId[j];
						}
						clientWriter.write(myHandShakeMessage);
						clientWriter.flush();
						DataInputStream myResponsehandShakeMessage =  new DataInputStream(clientSocket.getInputStream());
						myResponsehandShakeMessage.readFully(myResponseByteArray);
						byte[] arrayRange = Arrays.copyOfRange(myResponseByteArray, 28, 32);
						ByteBuffer myPeerInByte = ByteBuffer.wrap(arrayRange);
						int responseId = myPeerInByte.getInt();
						if(responseId!=eachIdInHashMap){
							clientSocket.close();
						}
						else{
							System.out.println("Running Host ID "+currentHostID+" Each id in LinkedHashMap "+responseId);
							// changed from respondId to eachIdInHashMap
							everyLogs.createLogEntry("connectionTo", currentHostID, eachIdInHashMap);// uncommented the line
							StringBuilder responseHandshakeMessage = new StringBuilder();
							byte[] temp = Arrays.copyOfRange(myResponseByteArray, 0, 28);
							String anotherTemp = new String(temp);
							responseHandshakeMessage.append(anotherTemp);
							responseHandshakeMessage.append(responseId);
							System.out.println("Respons got back from responseId(My Client) "+" "+responseId+" "+responseHandshakeMessage);
							concurrentPeers.put(eachIdInHashMap, new ConnectionToPeer(clientSocket, eachIdInHashMap));
						}
					}
				}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static class myServerConnections extends Thread{   // check in original code it is private in there
		@Override
		public void run(){
			byte[] myReceivedHandshakeMessage = new byte[32];
			//int portNumberOfHost = everyPeer.get(currentHostID).getportNumber(); // or do currentpeer.getportNumer()
			int portNumberOfHost = currentPeer.getportNumber();
			System.out.println("(My server Connections) Current Id "+currentPeer.getPeerId()+" 'sPort "+portNumberOfHost);
			try {
				ServerSocket myServer = new ServerSocket(portNumberOfHost); //doing something wrong check later
				while(concurrentPeers.size()<everyPeer.size()-1){
					Socket myNewSocket = myServer.accept();
					DataInputStream serverReader = new DataInputStream(myNewSocket.getInputStream());
					serverReader.readFully(myReceivedHandshakeMessage);
					String sNew = new String(myReceivedHandshakeMessage);
					System.out.println("Received HandShake Message is "+sNew);
					byte[] arrayRange = Arrays.copyOfRange(myReceivedHandshakeMessage, 28, 32);
					ByteBuffer myPeerInByte = ByteBuffer.wrap(arrayRange);
					int responseId = myPeerInByte.getInt();
					System.out.println("response Id "+responseId);
					System.out.println("Running Host ID (Server Side)"+currentHostID+" Each id in LinkedHashMap "+responseId);
					everyLogs.createLogEntry("connectionFrom", currentHostID,responseId);//uncommented the line
					StringBuilder responseHandshakeMessage = new StringBuilder();
					byte[] temp = Arrays.copyOfRange(myReceivedHandshakeMessage, 0, 28);
					String tempString = new String(temp);
					responseHandshakeMessage.append(tempString);
					responseHandshakeMessage.append(responseId);
					System.out.println("Response Message got From myServerConnections(My Server ) "+responseId+" is "+responseHandshakeMessage);
					concurrentPeers.put(responseId, new ConnectionToPeer(myNewSocket, responseId));
					/*
					 * Work Related to PeerConnection file and HashMap
					 * 
					 * Need to reply to handShake Message
					 * */
					byte [] myHandShakeMessage = new byte[32];
					byte[] myHeader = new String("P2PFILESHARINGPROJ").getBytes();
					byte[] bitZeros = new String("0000000000").getBytes();
					byte[] myPeerId = ByteBuffer.allocate(4).putInt(currentHostID).array();
					int i=0;
					for(int j=0;j<myHeader.length;j++){
						myHandShakeMessage[i++]=myHeader[j];
					}
					for(int j=0;j<bitZeros.length;j++){
						myHandShakeMessage[i++]=bitZeros[j];
					}
					for(int j=0;j<myPeerId.length;j++){
						myHandShakeMessage[i++]=myPeerId[j];
					}
					DataOutputStream serverWriter = new DataOutputStream(myNewSocket.getOutputStream());
					serverWriter.flush();
					serverWriter.write(myHandShakeMessage);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private static class PeerUnchokeClass extends Thread{
		@Override
		public void run() {
			while(numberOfPeersHasCompletedFile<everyPeer.size()){
				ArrayList<Integer> peersRunning = new ArrayList<>(concurrentPeers.keySet());
				int preferredNeighboursCount = myCommonFile.getPreferredNumberOfNeighbours();
				int [] neighboursSharing = new int[preferredNeighboursCount];
				if(currentPeer.getHaveFile()){
					ArrayList<Integer> peersWhichAreInterested = new ArrayList<>();
					for(int i=0;i<peersRunning.size();i++){
						int temp = peersRunning.get(i);
						if(concurrentPeers.get(temp).isInterested()){
							peersWhichAreInterested.add(temp);
						}
					}
					if(peersWhichAreInterested.size()>0){
						//flipped the conditions
						if(peersWhichAreInterested.size()>preferredNeighboursCount){
							Random random = new Random();
							for(int i=0;i<preferredNeighboursCount;i++){
								int randomNumberGenerated = random.nextInt();
								int size = peersWhichAreInterested.size();
								int calculation = Math.abs(randomNumberGenerated % size);
								//changed from double to int in 700
								neighboursSharing[i]=peersWhichAreInterested.remove(calculation);
							}
							for(int i=0;i<neighboursSharing.length;i++){
								int key = neighboursSharing[i];
								if(concurrentPeers.get(key).isChoked()){
									concurrentPeers.get(key).unchoke();
									concurrentPeers.get(key).sendMyMessage("UNCHOKE");
								}
							}
							for(int peer:peersWhichAreInterested){
								//We can remove the concurrentPeers.get(peer).isChoked() check later
								if(!concurrentPeers.get(peer).isChoked() && !concurrentPeers.get(peer).isOptimisticallyUnchoked()){
									concurrentPeers.get(peer).choke();
									concurrentPeers.get(peer).sendMyMessage("CHOKE");
								}
							}
						}else{
							//changed from Integer to Int in case of any Problem
							for(int peer : peersWhichAreInterested){
								if(concurrentPeers.get(peer).isChoked()){
									concurrentPeers.get(peer).unchoke();
									concurrentPeers.get(peer).sendMyMessage("UNCHOKE");
								}
							}
						}
					}
				}  // end of have file
				else{
					ArrayList<Integer> peersInterested = new ArrayList<>();
					int count =0;
					for(int peer:peersRunning){
						if(concurrentPeers.get(peer).isInterested()){
							if(concurrentPeers.get(peer).getDownloadrate()>=0){
								peersInterested.add(peer);
							}
						}
					}
					//flipped it
					if(peersInterested.size()>myCommonFile.getPreferredNumberOfNeighbours()){
						for(int i=0;i<preferredNeighboursCount;i++){
							int newPeer = peersInterested.get(0);
							for(int j=1;j<peersInterested.size();j++){
								double firstDownloadRate = concurrentPeers.get(newPeer).getDownloadrate();
								double secondDownloadRate = concurrentPeers.get(peersInterested.get(j)).getDownloadrate();
								if(firstDownloadRate<=secondDownloadRate){
									newPeer = peersInterested.get(j);
								}
							}
							if(concurrentPeers.get(newPeer).isChoked()){
								concurrentPeers.get(newPeer).unchoke();
								concurrentPeers.get(newPeer).sendMyMessage("UNCHOKE");
							}
							neighboursSharing[i]= newPeer;
							peersInterested.remove(Integer.valueOf(newPeer));
						}
						for(Integer peer :peersInterested){
							// we can use unchoked as well check later
							if(!concurrentPeers.get(peer).isChoked() && !concurrentPeers.get(peer).isOptimisticallyUnchoked()){
								concurrentPeers.get(peer).choke();
								concurrentPeers.get(peer).sendMyMessage("CHOKE");
							}
						}
					}
					//we have flipped it
					else{
						for(int peer:peersInterested){
							neighboursSharing[count] = peer;
							count++;
							if(concurrentPeers.get(peer).isChoked()){
								concurrentPeers.get(peer).unchoke();
								concurrentPeers.get(peer).sendMyMessage("UNCHOKE");
							}
						}
					}
				}
				//
//				for(int i=0;i<neighboursSharing.length;i++)
//				System.out.print(neighboursSharing[i]);
				everyLogs.createLogEntry(currentPeer.getPeerId(),neighboursSharing);
				try{
					Thread.sleep(myCommonFile.getIntervalOfUnchoking()*1000);
				}catch(Exception e){
					//exception handling
				}
			}
			try{
				Thread.sleep(5000);
			}catch (Exception e) {
				// TODO: handle exception
			}
			System.exit(0);
		}
	}
	private static class ConnectionToPeer{
		private boolean variableChoked = true;
		private Socket myConnection;
		private int myPeerId;
		private double downloadRate =0;
		private boolean variableOptimisticallyUnchoked = false;
		private boolean variableInterested = false;
		public ConnectionToPeer(Socket connection, int myId){
			myConnection = connection;
			myPeerId = myId;
			(new fileSharing(this)).start(); //reader file need to be done
		}
		public double getDownloadrate(){
			return this.downloadRate;
		}
		public void setDownladRate(double downloadrate){
			this.downloadRate =downloadrate;
		}
		public boolean isOptimisticallyUnchoked() {
			return this.variableOptimisticallyUnchoked;
		}
		public void optimisticallyUnchoke() {
			variableOptimisticallyUnchoked = true;
		}
		public void optimisticallyChoke(){
			variableOptimisticallyUnchoked = false;
		}
		public void setInterested() {
			variableInterested = true;
		}
		public void setNotInterested(){
			variableInterested = false;
		}
		public boolean isChoked() {
			return this.variableChoked;
		}
		public void choke() {
			variableChoked = true;
		}
		public boolean isInterested(){
			return variableInterested;
		}
		public void unchoke(){
			variableChoked = false;
		}
		public int getPeerID(){
			return this.myPeerId;
		}
		public Socket getConnection() {
			return this.myConnection;
		}
		public void sendMyMessage(String type){
			try{
				DataOutputStream myWriter = new DataOutputStream(myConnection.getOutputStream());
				myWriter.flush();
				if(type.equals("CHOKE"))
					myWriter.write(createMessage("CHOKE", 1, null));
				else if(type.equals("UNCHOKE"))
					myWriter.write(createMessage("UNCHOKE", 1, null));
				else if(type.equals("INTERESTED"))
					myWriter.write(createMessage("INTERESTED", 1, null));
				else if(type.equals("NOT_INTERESTED"))
					myWriter.write(createMessage("NOT_INTERESTED", 1, null));
				else if(type.equals("BITFIELD"))
					myWriter.write(calculatePayloadBitFieldAndCreate(currentPeer.getmybitFieldArray()));
				myWriter.flush();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		public void sendMyMessage(String type,int index){
			try{
				DataOutputStream myWriter = new DataOutputStream(myConnection.getOutputStream());
				myWriter.flush();
				if(type.equals("HAVE"))
					myWriter.write(calculatePayloadHaveAndCreate(index));
				else if(type.equals("REQUEST"))
					myWriter.write(calculatePayloadRequestAndCreate(index));
				else if(type.equals("PIECE"))
					myWriter.write(calculatePayloadPieceAndCreate(index, fileChunks[index]));
				myWriter.flush();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		public void bitfieldComparison(int[] bitfieldArray, int[] connectedPeerBitfield, int length){
			int index;
			for(index = 0; index < length; index++){
				if(bitfieldArray[index] == 0 && connectedPeerBitfield[index] == 1){
					sendMyMessage("INTERESTED");
					break;
				}
			}
			if(index == length)
				sendMyMessage("NOT_INTERESTED");
		}
		public void getPieceIndex(int[] bitfieldArray, int[] connectedPeerBitfield, int length){
			ArrayList<Integer> myArrayList = new ArrayList<>();
			int index;
			for(index = 0; index < length; index++){
				if(bitfieldArray[index] == 0 && connectedPeerBitfield[index] == 1){
					myArrayList.add(index);
				}
			}
			Random randomNumber = new Random();
			if(myArrayList.size() > 0){
				int indexPicked = myArrayList.get(Math.abs(randomNumber.nextInt() % myArrayList.size()));
				sendMyMessage("REQUEST", indexPicked);
			}
		}
		public void checkCompleted(){
			int count = 0;
			int [] tempArray = currentPeer.getmybitFieldArray();
			for(int i=0;i<tempArray.length;i++){
				if(tempArray[i]==1){
					count++;
				}
			}
			if(count == tempArray.length){
				//everyLogs.downloadCompleted(currentPeer.getPeerId()); where is downloadComplete and how should i use it?
				/*
				 * Ahmad will tell me what function should i call
				 * */
				everyLogs.createLogEntry("downloadComplete", currentPeer.getPeerId(), 0);
				count = 0;
				byte[] merge = new byte[myCommonFile.getMyFileSize()];
				for(byte[] piece : fileChunks){
					for(byte b : piece){
						merge[count] = b;
						count++;
					}
				}
				try {
					//need to look for absolute path
					//directory.getAbsolutePath()used for now we have used "/Users/nikhil/Desktop"
					//chasnge the directory here
					FileOutputStream file = new FileOutputStream(path+ "/" + myCommonFile.getMyFileName());
					BufferedOutputStream bos = new BufferedOutputStream(file);
					bos.write(merge);
					bos.close();
					file.close();
					System.out.println("File Download Completed.");
					currentPeer.setHaveFile(true);
					numberOfPeersHasCompletedFile++;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		private static class fileSharing extends Thread{
			private ConnectionToPeer connectionPeer;
			public fileSharing(ConnectionToPeer peer){
				this.connectionPeer = peer;
			}
			@Override
			public void run(){
				double beginingTime;
				double closingTime;
				synchronized (this) {
					try {
						DataInputStream myDataInputStream = new DataInputStream(connectionPeer.getConnection().getInputStream());
						connectionPeer.sendMyMessage("BITFIELD");
						while(numberOfPeersHasCompletedFile<everyPeer.size()){
							int lengthofMessage = myDataInputStream.readInt();
							byte[] receivedMessage = new byte[lengthofMessage];
							int temp =100000000;
							beginingTime = (double)System.nanoTime()/temp;
							myDataInputStream.readFully(receivedMessage);
							closingTime =(double)System.nanoTime()/temp;
							char type = (char)receivedMessage[0];
							int length = receivedMessage.length-1;
							byte[] actualMessage = new byte[length];
							int i=1;
							int j=0;
							while(i<lengthofMessage){
								actualMessage[j++]=receivedMessage[i++];
							}
							int myPointer = 0, totalBits =0;
							int id1 = currentPeer.getPeerId();
							int id2 = connectionPeer.myPeerId;
							if(type == '0'){
								//CHOKE
								everyLogs.createLogEntry("choking",id1,id2);
								connectionPeer.choke();
							}else if(type == '1'){
								//UNCHOKE
								connectionPeer.unchoke();
								everyLogs.createLogEntry("unchoked", id1, id2);
								int[] parameterOne = currentPeer.getmybitFieldArray();
								int [] parameterTwo = everyPeer.get(id2).getmybitFieldArray();
								int parameterThree = parameterOne.length;
								connectionPeer.getPieceIndex(parameterOne,parameterTwo,parameterThree);
							}else if(type=='2'){
								//INTERESTED
								String parameterOne ="receiveInterestedMessage";
								everyLogs.createLogEntry(parameterOne, id1, id2);
								connectionPeer.setInterested();
							}else if(type=='3'){
								//NOT INTERESTED
								String parameterOne ="receiveNotInterestedMessage";
								everyLogs.createLogEntry(parameterOne, id1, id2);
								connectionPeer.setInterested();
								/*
								 * Try Removing If Condition 
								*/
								if(!connectionPeer.isChoked()){
									connectionPeer.choke();
									connectionPeer.sendMyMessage("CHOKE");
								}
							}else if(type=='4'){
								//HAVE
								ByteBuffer tempBuffer = ByteBuffer.wrap(actualMessage);
								myPointer = tempBuffer.getInt();
								int getId = connectionPeer.getPeerID();
								everyPeer.get(getId).updateBitField(myPointer);
								totalBits = 0;
								for(int each : everyPeer.get(getId).getmybitFieldArray()){
									if(each==1){
										totalBits++;
									}
								}
								if(totalBits==currentPeer.getmybitFieldArray().length){
									everyPeer.get(getId).setHaveFile(true);
									numberOfPeersHasCompletedFile++;
								}
								int [] parameterOne = currentPeer.getmybitFieldArray();
								int [] parameterTwo = everyPeer.get(getId).getmybitFieldArray();
								int tempLength = parameterOne.length;
								connectionPeer.bitfieldComparison(parameterOne, parameterTwo, tempLength);
								everyLogs.createLogEntry("receiveHaveMessage", id1, id2);
							}else if(type=='5'){
								//BITFIELD
								int [] receiveBitField = new int[actualMessage.length/4];
								int index=0,k=0;
								while(k<actualMessage.length){
									byte [] tempArray = Arrays.copyOfRange(actualMessage, k, k+4);
									ByteBuffer temp1 = ByteBuffer.wrap(tempArray);
									receiveBitField[index++] = temp1.getInt();
									k+=4;
								}
								int tempID =connectionPeer.myPeerId;
								everyPeer.get(tempID).setBitFieldMessage(receiveBitField);
								totalBits =0;
								int tempID1= connectionPeer.getPeerID();
								for(int each : everyPeer.get(tempID1).getmybitFieldArray()){
									if(each == 1){
										totalBits++;
									}
								}
								if(totalBits!=currentPeer.getmybitFieldArray().length){
									everyPeer.get(tempID1).setHaveFile(false);
								}else{
									everyPeer.get(tempID1).setHaveFile(true);
									numberOfPeersHasCompletedFile++;
								}
								int array[] = currentPeer.getmybitFieldArray();
								connectionPeer.bitfieldComparison(array, receiveBitField, receiveBitField.length);
							}else if(type=='6'){
								//REQUEST
								ByteBuffer tempBYTE = ByteBuffer.wrap(actualMessage);
								connectionPeer.sendMyMessage("PIECE",tempBYTE.getInt());
							}
							else if(type=='7'){
								//PIECE
								byte [] arrayByte = Arrays.copyOfRange(actualMessage, 0, 4);
								ByteBuffer tempPara = ByteBuffer.wrap(arrayByte);
								myPointer = tempPara.getInt();
								int x=4;
								int len = actualMessage.length-x;
								fileChunks[myPointer] = new byte[len];
								int k=4;
								int place=0;
								while(k<actualMessage.length){
									fileChunks[myPointer][place] = actualMessage[k++];
									place++;	
								}
								currentPeer.updateBitField(myPointer);
								currentPeer.updatingNumberofPieces();
								if(!connectionPeer.isChoked()){
									int [] newBitField = currentPeer.getmybitFieldArray();
									int [] integerArray = everyPeer.get(connectionPeer.myPeerId).getmybitFieldArray();
									int bitFieldLength = newBitField.length;
									connectionPeer.getPieceIndex(newBitField, integerArray, bitFieldLength);
								}
								int headerLength = 4;
								int charLength = 1;
								int totalLength = actualMessage.length+headerLength+charLength;
								double totalTime  = closingTime - beginingTime;
								double myDownloadingSpeed = ((double)(totalLength)/(totalTime));
								if(!everyPeer.get(connectionPeer.getPeerID()).getHaveFile()){
									connectionPeer.setDownladRate(myDownloadingSpeed);
								}else{
									connectionPeer.setDownladRate(0); // In original Code it was -1;
								}
								everyLogs.createLogEntry(id1, id2, myPointer, currentPeer.getNumberOfPieces());
								int constantFactor = 100;
								int numerator = currentPeer.getNumberOfPieces()*constantFactor;
								double fileSizeTemp = myCommonFile.getMyFileSize();
								double pieceSize = myCommonFile.getMyPieceSize();
								double tempCalculation = (double)(fileSizeTemp/pieceSize);
								int denominator = (int)Math.ceil(tempCalculation);
								int percentageDownload = numerator/denominator;
								StringBuffer downloadReport = new StringBuffer();
								String toAppend = "Downloaded: "+percentageDownload; // Check it Later
								downloadReport.append("\r\n").append(toAppend);
								String changedToAppend = "% Number of Pieces: ";
								downloadReport.append(changedToAppend);
								downloadReport.append(currentPeer.getNumberOfPieces());
								System.out.println("Downloaded Report is: "+downloadReport);
								connectionPeer.checkCompleted();
								for(int each: concurrentPeers.keySet()){
									String toPass ="HAVE";
									concurrentPeers.get(each).sendMyMessage(toPass, myPointer);
								}
							}
							else {
								break;
							}
						}
						Thread.sleep(5000);
						System.out.println("It is exiting ================");
						System.exit(0);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	private static class OptimisticPeer extends Thread{
		public void run(){
			while(numberOfPeersHasCompletedFile<everyPeer.size()){
				ArrayList<Integer> peers = new ArrayList<>(concurrentPeers.keySet());
				ArrayList<Integer> myList = new ArrayList<>();
				for(int peer : peers){
					if(concurrentPeers.get(peer).isInterested()){
						myList.add(peer);
					}
				}
				if(myList.size()>0){
					Random random = new Random();
					int len = myList.size();
					int randomInteger =  Math.abs(random.nextInt()%len);
					int interestedPeer = myList.get(randomInteger);
					concurrentPeers.get(interestedPeer).unchoke();
					concurrentPeers.get(interestedPeer).sendMyMessage("UNCHOKE");
					concurrentPeers.get(interestedPeer).optimisticallyUnchoke();
					String paramsOne ="changeOptimisticallyUnchokedNeighbor";
					int id1 = currentPeer.getPeerId();
					int id2 = concurrentPeers.get(interestedPeer).getPeerID();
					everyLogs.createLogEntry(paramsOne, id1, id2);
					int tempConstantInteger = 1000;
					int seconds =myCommonFile.getIntervalOfOptimisticUnchoking()*tempConstantInteger;
					try {
						Thread.sleep(seconds);
						concurrentPeers.get(interestedPeer).optimisticallyChoke();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			int timeConstant = 4000; //changed fron 5000 to 4000;
			try {
				Thread.sleep(timeConstant);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		}
	}
	public static void main(String[] args) throws FileNotFoundException, IOException {	
		/*
		 * Reading From Common.cfg
		 * and Setting the Values*/
		ConfigFileReader myConfigurationFile = new ConfigFileReader();
		myCommonFile = new CommonFileInformation();
		int numberOfPreferredNeighbors = Integer.parseInt(myConfigurationFile.getProperty("NumberOfPreferredNeighbors"));
		int unchokingInterval = Integer.parseInt(myConfigurationFile.getProperty("UnchokingInterval"));
		int optimisticUnchokingInterval= Integer.parseInt(myConfigurationFile.getProperty("OptimisticUnchokingInterval"));
		String fileName = myConfigurationFile.getProperty("FileName");
		int myfileSize = Integer.parseInt(myConfigurationFile.getProperty("FileSize"));
		int mypieceSize = Integer.parseInt(myConfigurationFile.getProperty("PieceSize"));
		myCommonFile.setPreferredNumberOfNeighbours(numberOfPreferredNeighbors);
		myCommonFile.setIntervalOfUnchoking(unchokingInterval);
		myCommonFile.setIntervalOfOptimisticUnchoking(optimisticUnchokingInterval);
		myCommonFile.setMyFileName(fileName);
		myCommonFile.setMyFileSize(myfileSize);
		myCommonFile.setMyPieceSize(mypieceSize);
		System.out.println(myCommonFile.getPreferredNumberOfNeighbours());
		System.out.println(myCommonFile.getIntervalOfUnchoking());
		System.out.println(myCommonFile.getIntervalOfOptimisticUnchoking());
		System.out.println(myCommonFile.getMyFileName());
		System.out.println(myCommonFile.getMyFileSize());
		System.out.println(myCommonFile.getMyPieceSize());
		/*
		 * Reading From PeerInfo.cfg
		 * and Setting the Values*/
		//changed from "/Users/nikhil/Desktop to 
		BufferedReader peerInfoFile = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/PeerInfo.cfg"));
		everyPeer = new LinkedHashMap<>();
		for (Object everyLine : peerInfoFile.lines().toArray()) {
			PeerFileInformation peer = new PeerFileInformation();
			String[] parts = ((String) everyLine).split(" ");
			int peerId = Integer.parseInt(parts[0]);
			peer.setPeerId(peerId);
			String hostName = parts[1];
			peer.setNameOfHost(hostName);
			int portNumber = Integer.parseInt(parts[2]);
			peer.setportNumber(portNumber);
			if(Integer.parseInt(parts[3])==1){
				peer.setCompleteFile(true);
			}
			else{ 
				peer.setCompleteFile(false);
			}
			everyPeer.put(Integer.parseInt(parts[0]), peer);
		}
		peerInfoFile.close();
		System.out.println();
		for(int peerId :everyPeer.keySet()){
			PeerFileInformation peer = everyPeer.get(peerId);
			System.out.println(peer.getPeerId());
			System.out.println(peer.getNameOfHost());
			System.out.println(peer.getportNumber());
			System.out.println(peer.getCompleteFile());
			System.out.println();
		}
		System.out.println();
		currentHostID = Integer.parseInt(args[0]);
		//currentHostID = 1006;
		doCalculation(currentHostID);
		concurrentPeers = new ConcurrentHashMap<>();
		myClientConnection newClientConnection = new myClientConnection();
		newClientConnection.start();
		myServerConnections newServerConnection = new myServerConnections();
		newServerConnection.start();
		PeerUnchokeClass newUnchokeClass = new PeerUnchokeClass();
		newUnchokeClass.start();
		OptimisticPeer newOptimisticPeer = new OptimisticPeer();
		newOptimisticPeer.start();

		/*
		 * Checking Log generated Code with File directory 
		 * 
		System.out.println("start");
		String desktopPath = System.getProperty("user.home") + "/Desktop";
		desktopPath=desktopPath.replace("\\", "/");
		System.out.println(desktopPath);
		File logsFile = new File (desktopPath+"/logs/logpeer.log");
		if(!logsFile.exists()) {
		    logsFile.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(logsFile.getPath(),true));
		log log1 = new log();
		log1.Logs(bw);
		log1.createLogEntry("connectionTo",1,3);
		log1.createLogEntry("connectionFrom",1,3);
		log1.createLogEntry("changeOptimisticallyUnchokedNeighbor",1,3);
		log1.createLogEntry("unchoked",1,3);
		log1.createLogEntry("choking",1,3);
		log1.createLogEntry("receiveHaveMessage",1,3);
		log1.createLogEntry("connereceiveInterestedMessage",1,3);
		log1.createLogEntry("receiveNotInterestedMessage",1,3);
		log1.createLogEntry("downloadComplete",1,3);
		log1.createLogEntry(1, 3, 330, 21);
		int [] ident = {4,5,6,7,8};
		log1.createLogEntry(1, ident);*/

	}
}