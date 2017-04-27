# IP_Project_P2P

We have implemeneted a P2P system. There are 2 parts, the P2P server and the P2P clients. The server is the central repository that keeps track of which files each of the peers (clients) have in their systems. The clients can then query the server to check what files are within the system, and which files are available with which peers. The system uses TCP for handling the queries and data transfer so as to ensure reliable communication.

### The server

The server is implemented in a multi-threaded environment. Each incoming TCP connection is handled by a newly spawned thread, which works until the connection is shut down by the client. The server maintains a list of all clients that are currently online. Also, the server maintains a hashmap of RFCs and the clients that have those files. The server handles 3 requests - Add, List and Lookup.

### The client

The client has 2 parts. One thread that communicates with the server and make requests to other clients, and another thread that handles the incoming requests from other clients. When the client is started up, it asks for the folder which it has. The folder is used to find all the RFCs this client has. The folder has a fixed prefix, just to make the input a little easier. This prefix can be modified in P2PClient.java. Next, the client also asks for the server's IP address. If testing locally, 127.0.0.1 will work. The client reads all the files in this folder, and adds them on the server's directory. I chose to do this, but even otherwise, it is easy to convert it into a user input. 

Once all files are added, the user gets a promt. He can choose one of 3 options. List all the info at server, lookup a certain RFC at the server and get an RFC from another client. List option queries the server and displays the output. Lookup asks for an RFC number to lookup on the server and returns the list of clients that have the rfc. Finally the get option asks for an RFC number to get from another client, and that client's ip address and port number. The file is then saved in the folder specified at startup, and an add request is sent to the server to let it know that this client also has the rfc now.

### Instructions to run
1. Clone or unzip the project.
2. Open Eclipse, create a new java project with the same name and location as the unzipped project.
3. Run the server - Run P2PServer.java. Run ifconfig/ipconfig in terminal to check the server's IP address.
4. Run the client - Run P2PClient.java. Use the folders in 'clientfolders' folder to access the rfc. Folder 1 contains first 500 RFCs. Note these are real RFCs.
