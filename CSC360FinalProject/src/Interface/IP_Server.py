from socket import *
serverPort = 55555
serverSocket = socket(AF_INET, SOCK_DGRAM)
serverSocket.bind(('', serverPort))
print("The server is ready to receive")
ip_addresses = []
while True:
    message, clientAddress = serverSocket.recvfrom(2048)
    print("Incoming message " + message.decode())
    modifiedMessage = message.decode().upper()
    list.append(modifiedMessage)
    serverSocket.sendto(modifiedMessage.encode(), clientAddress)