import socket
#UDP_IP = "127.0.0.1"
UDP_IP = "10.18.207.18"
UDP_PORT = 55555


sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
sock.bind((UDP_IP, UDP_PORT))

while True:
    data, addr = sock.recvfrom(1024) #buffer size is 1024 bytes
    print ("recieved message:", data)
