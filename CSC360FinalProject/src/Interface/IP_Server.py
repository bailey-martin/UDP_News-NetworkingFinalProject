from socket import *
serverPort = 55555
serverSocket = socket(AF_INET, SOCK_DGRAM)
serverSocket.bind(('', serverPort))
print("The server is ready to receive")
ip_addresses = []
messages = ""

def printTheIPs():
   global messages
   for x in range(len(ip_addresses)):
        messages += ip_addresses[x] + "/";

while True:
    message, clientAddress = serverSocket.recvfrom(2048)
    print("Incoming message " + message.decode())
    test = clientAddress[0]
    if '1' in test:
	       ip_addresses.append(clientAddress[0])
	       printTheIPs()
	       for i in range(0, len(ip_addresses)):
		             serverSocket.sendto(bytes(messages, 'utf-8'), (ip_addresses[i], 55555))
    #serverSocket.sendto(modifiedMessage.encode(), clientAddress)
