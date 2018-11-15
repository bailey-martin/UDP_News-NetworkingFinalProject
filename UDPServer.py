from socket import *
IP_Addresses = []
serverPort = 55555 #12000
serverSocket = socket(AF_INET, SOCK_DGRAM)
serverSocket.bind(('', serverPort))
print("The server is ready to receive")
while True:
    message, clientAddress = serverSocket.recvfrom(2048)
    print("Incoming message " + message.decode())
    IP_Addresses.append(message.decode())
    for i in range (len(IP_Addresses)):
        print IP_Addresses[i]
    modifiedMessage = message.decode().upper()
    serverSocket.sendto(modifiedMessage.encode(), clientAddress)
