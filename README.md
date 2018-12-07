# PeerToPeer-Software
"Peer to Peer File Sharing Software Using Java
Implemented P2P File Sharing Software, a simplified version of the Bit Torrent
Main Features are Choking, Unchoking and Preferred Neighboring Selection Mechanism. Tested using a minimum 6 peers and a large file of size greater than 1 Gb"

Compile javac peerProcess.java
Execute java peerProcess.java 1001 
Where 1001 represents the peerID.

The project Need two configFile 
First is Common.cfg
which has
NumberOfPreferredNeighbors 2
UnchokingInterval 5
OptimisticUnchokingInterval 15
FileName TheFile.dat
FileSize 21567
PieceSize 1000

Second is PeerInfo.cfg
1001 192.168.43.11 5000 1 
1002 192.168.43.11 6000 0 
1003 192.168.43.11 7010 0 
1004 192.168.43.11 8011 0
1005 192.168.43.11 9412 0 
1006 192.168.43.11 4013 0 

where 1001-1006 represents 6 different PeerId 
"192.168.43.11" represents the Ip address of the System on which files are running 
5000,6000,7010,8011,9412,4013 represents the Port Numbers and 1 represent the peer has the complete File 
and 0 represents that peer does not have the complete File and it needs to download the File.

TheFile.dat is a File which need to transfer among all Peers.

If you want to run or connect on different Systems then your PeerInfo.cfg file should look like this
1001 Ip Address of the 1st System 5000 1 
1002 Ip Address of the 2nd System 6000 0 
1003 Ip Address of the 3rd System 7010 0 
1004 Ip Address of the 4th System 8011 0
1005 Ip Address of the 5th System 9412 0 
1006 Ip Address of the 6th System 4013 0 

This PeerInfo.cfg should be same for each Peer and all systems should connect to same Wifi.


