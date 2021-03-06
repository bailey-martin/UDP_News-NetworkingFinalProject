CSC 360 Networking Project--a P2P UDP-Based News Application
University of Mount Union
Dr.Weber
Team Members: Bailey Martin, Matt McMinn, Amanda Hegidus
Date Published: December 7, 2018
Project Description: https://silver.mountunion.edu/cs/csc/CSC360/Fall2018/index.htm#project
GitHub Project Link: https://github.com/bailey-martin/UDP_News-NetworkingFinalProject

Requirements
	-A computer to run the peer program as well as to start the IP service program on weberkcudafac (SSH into the machine using the Google Chrome Extension), acting as a IP server on the University of Mount Union's network.
	-A peer process will listen on port 55555 for incoming news items; when a message is delivered it will then print the message to the Java console and send it to the other connected peers (using the list of IPs from our IP server on weberkcudafac).
	-A user must be able to type in a message and have it sent to a peer. Our program prompts the user to enter a “news item” in the Java Console by saying: “"Please enter the news item that you wish to share, Enter '-' to escape input feed:”. This allows the user to send messages to other peers that are listening on port 55555.
	-Peers should use the UDP checksum to check to see if the item suffered an error in transmission. Based on our research and conferencing with other classmates, UDP Checksum is automatically implemented into the Operating System, so we do not need to create a separate algorithm for our program, since it is already handled. Additionally, during all of our testing, we never received an exception or a “NAK”, indicating that the datagram transfer was unsuccessful. UDP Checksum can be implemented at the OS or the Socket Level of a computer.
	-Delivery of a particular news item to all active peers is done on a “best effort”
		-Principle of UDP-no guarantee, is only a best-effort basis

Questions for the Project
	-How will a peer register with the service? A peer registers with the service when running SocketTest.java. In the constructor method, I used InetAddress ip = InetAddress.getLocalHost(); to get the IP address of the peer. The IP is then sent to a listening Python UDP server on weberkcudafac, which records the IP address as a element in the arrayList. The arrayList is then sent back down to all of the peers listening on port 55555, allowing each peer to have a complete list of all connected peers’ IP’s.
	-Effective News Delivery Method for Peers-We used P2P protocol to ensure that the news items are effectively and efficiently. P2P allows each individual peer to act as both a client and a server. This reduces the load on each peer, and eliminates the need for centralized equipment to handle large data loads (ex: large server centers and no longer needed because each peer IS the server).
	-When a peer receives a news item that has been damaged in transmission, it should just drop the damaged packet. It should not ask the sender to retransmit, as that is usually a characteristic of a TCP connection, not a UDP. It is acceptable to drop the packet because UDP is a “best-effort” protocol.
	-A peer will determine that a news item is a duplicate of a news item that has already been sent using the checksum of the datagram. It is okay if a peer has a duplicate datagram, but it is important that it is only displayed once and is not passed on.

Files Associated with this Program:
	-README.txt = A overview of the program with information and a user manual for our programming project.
	-CSC360 Final Project Netbeans File-can be opened as a project in Netbeans
	-IP_Server.py = A Python script that is designed to run on weberkcudafac, listening on port 55555. This program listens for IP addresses passed up to it from the peer as it starts executing SocketTest.java. It then stores the peer’s IP address in an arrayList and sends it out into all of the peer’s listening sockets, giving them all a list of current connected IPs.
	-Launching_Frame = A java GUI prototype that was originally designed for the user to be presented with upon startup, our original idea was when the user pressed the “Launch” Button, it pulled their IP into an arrayList. Also when clicking launch, it closed that window and opened User_Frame.java.
	-User_Frame = A java GUI prototype that was originally designed to act as the main window for the program, the sender/receiver windows. The sender types a message in the left-hand field, and clicks send, moving it over to the right-hand field and sending it to all of the connected peers. The right-hand field was designed as a “live feed” that was going to display the entire conversation of all news items for all of the peers.
	-SocketTest_Old.java = This java file was our first successful attempt at designing the peer-to-peer protocol, where multiple peers can have a conversation, allowing them to send and receive data. All activity is done via the Java Console. In this file, we have the IP addresses of the sample peers “hardcoded” into the program, and if they are going to be tested on a different set of computers, the datagram Inet Host fields will need to be modified in order to successfully run the program.
	-SocketTest.java = This program was a step above SocketTest_Old.java, because we wanted to improve upon the fact that IP addresses of the peers did not need to be hardcoded, but instead could be automatically given to each peer when joining the program. We achieved this by designing the IP_Server.py script (read above), which takes the new peer’s IP and adds it to the server arrayList, then sending the updated filled arrayList of all peer IP addresses to all of the peers who have an IP address in the arrayList. All of the peers receive this through their receiver port 55555. Despite extensive testing and troubleshooting, we were unable to get the peers to receive the messages that other peers sent. It seems to be an issue with the DatagramPacket packet = new DatagramPacket(data, data.length, aHost, 55555); The IP addresses seem to be received by all of the peers from the Python script on weberkcudafac, but when the peer goes to send a datagram to an IP address of another peer from the ip_addresses arrayList, it seems to send (on the sending peer’s end), but the receiving peer never seems to receive any datagrams. We have tried adding println’s after each loop so that we could isolate the issue, and like I mentioned it above, the issue lies in the sending of the news items to peers using their IP address from the arrayList.

Directions for Using the Program:
	—for SocketTest_Old.java:
		1. Open CSC360 Final Project in Netbeans
		2. In the SocketTest_Old class in lines 43-46, (InetAddress aHost = InetAddress.getByName("192.168.209.240”);, modify the IP address to be all of the peers that 		you want in the P2P network.
		3. Run SocketTest_Old on each of the peers. You will be promoted with your IP address in the console and have the ability to input a news item (or a “-“) to 			terminate the program. After typing your message and pressing “Enter”, the message will be shared with all peers and displayed in their Java Consoles. Each peer 		can continue to enter news items by just typing in the Java console and pressing “Enter” again, until terminating with a “-“.
	-for SocketTest.java:
		1. Open CSC360 Final Project in Netbeans
		2. Log into weberkcudafac, and use the smbclient to move/copy the IP_Server.py file from the CSC 360 Final Project (Netbeans) onto weberkcudafac.
		3. Execute IP_Server.py on weberkcudafac. NOTE:: YOU MUST EXECUTE THIS SCRIPT USING PYTHON3!!!!! Ex: ~python3 IP_Server.py
		4. After you see “The server is ready to receive” in weberkcudafac’s SSH window, you are good to go.
		5. Run/Execute the SocketTest.java file in NetBeans on each peer that you want to be a part of this P2P network. You should notice that the peer of each IP that 		is printed in the Java Console will appear in the SSH window of weberkcudafac as well, showing that our IP server has received and stored the IP address of the 		new peer.
