from socket import *
serverPort = 55555
serverSocket = socket(AF_INET, SOCK_DGRAM)
serverSocket.bind(('', serverPort))
print("The server is ready to receive")
ip_addresses = []
messages = "";
while True:
    message, clientAddress = serverSocket.recvfrom(2048)
    print("Incoming message " + message.decode())
    modifiedMessage = message.decode().upper()
    ip_addresses.append(modifiedMessage)
    print(modifiedMessage)
    printTheIPs()
    for i in range (lenip_addresses)):
        serverSocket.sendTo(bytes(messages, 'utf-8'), (ip_addresses[i], 55555))
    #serverSocket.sendto(modifiedMessage.encode(), clientAddress)

def printTheIPs():
   for x in range(len(ip_addresses)):
        messages += ip_addresses[x];