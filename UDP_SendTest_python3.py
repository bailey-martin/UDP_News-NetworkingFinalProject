import socket
#UDP_IP = "127.0.0.1"
UDP_IP = "10.18.207.18"
UDP_PORT = 55555
#MESSAGE = "Hello, World!"
MESSAGE = input("Enter your message: ")

print ("UDP target IP:", UDP_IP)
print ("UDP target port:", UDP_PORT)
print ("message:", MESSAGE)

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
sock.sendto(MESSAGE.encode(encoding='UTF-8',errors='strict'), (UDP_IP, UDP_PORT))
