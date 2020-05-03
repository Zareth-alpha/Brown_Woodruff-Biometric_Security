#!/usr/bin/python3
"""
Author: Jonathan Brown
Date: 4/10/20
Python version: Python 3.7.5
Project: Biometric_Security
    Description: Biometric_Security is a senior capstone assignment for Jonathan Brown
                and Khalil Woodruff. It aims to provide an Android-mobile application
                that prompts biometric scanning, patterns, etc. to unlock one's locked
                Linux account & autofill their web-account credentials with the assistance
                of KeePassXC and Firefox.
File Description:
    This script is taken from
        https://people.csail.mit.edu/albert/bluez-intro/c212.html
    and will be vital in communications between a linux-desktop and a mobile device over
    bluetooth.

    This script also uses ideas from:
        https://gist.github.com/elvetemedve/c240ab26bdb25ce8ff8548c4f3297bcb

    libbluetooth-dev
    pip3 install pybluez
"""
"""
import bluetooth
import getpass
import os

repeat = True
while repeat:
    hostMACAddress = input("What is your mobile device's MAC address: ")  # MAC address of Mobile Device.
    print(hostMACAddress)
    response = input("Correct?")
    if response == 'y' or response == 'yes':
        repeat = False
port = 3
backlog = 1
size = 1024
btSocket = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
btSocket.bind((hostMACAddress, port))
btSocket.listen(backlog)
lock = 'loginctl lock-sessions'
user = getpass.getuser()
unlock = 'loginctl unlock-sessions {} --no-ask-password'.format(user)

try:
    client, clientInfo = btSocket.accept()
    while True:
        data = client.recv(size)
        if data:
            print(data)
            client.send(data)
            if data == 'lock':
                os.system(lock)
            elif data == 'unlock':
                os.system(unlock)
except:
    print("Closing socket")
    client.close()
    btSocket.close()

"""
import bluetooth
import getpass
import os

"""
target_name = input("What is your device name: ")  # needs to prompt user or check some sort of settings...
target_address = None
"""
backlog = 1
size = 1024
# This needs to be used on the Android device's side.
server_uuid = "a2087a8e-7dd3-11ea-bc55-0242ac130003"


# FUNCTION DEFINITIONS:
# Check the available, nearby devices for a matching name. return the target address.
def lookup_device(target_device_name, nearby_list):
    print("lookup_device: " + target_device_name)
    for bdaddr in nearby_list:
        if target_device_name == bluetooth.lookup_name(bdaddr):
            print("Found target device: " + target_device_name + ": " + bdaddr)
            target_device_address = bdaddr
            return target_device_address

"""
attempts = 1
target_name = input("Please enter your Android device name: ")
while attempts < 5:
    # The routine discover_devices() scans for approximately 10 seconds and returns a list of addresses of detected
    # devices.
    print("discover_devices: searching...")
    nearby_devices = bluetooth.discover_devices()

    target_address = lookup_device(target_name, nearby_devices)
    # If there is an address, print it out.
    if target_address is not None:
        print("found target bluetooth device with address %s \n" % target_address)
    # otherwise announce that there are none.
    elif attempts < 5:
        attempts += 1
        print("searching...")
    else:
        print("could not find target bluetooth device nearby.\n")
"""

print("server_sock: setting BluetoothSocket.")
server_sock = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
print("server_sock: {}".format(str(server_sock)))

# The documentation from the link above uses a depreciated function. This is the modern format.
print("Setting any port.")
port = bluetooth.PORT_ANY
server_sock.bind(("", port))
print("listening on port {}".format(port))

print("server_sock: listening")
server_sock.listen(backlog)

bluetooth.advertise_service(server_sock, "Biometric Security", server_uuid)

client_sock, address = server_sock.accept()
print("Accepted connection from {}".format(address))

data = client_sock(size)
print("received {}".formet(data))
'''
PyBluez represents a bluetooth address as a string of the form ``xx:xx:xx:xx:xx"
'''

# ================ START OF TEST LINUX CODE ============================================================================

lock = 'loginctl lock-sessions'
user = getpass.getuser()
unlock = 'loginctl unlock-sessions --no-ask-password' % user
if data == 'lock':
    os.system(lock)
elif data == 'unlock':
    os.system(unlock)

# ================== END OF TEST LINUX CODE ============================================================================

# Debug purpose "hello" string. Should be converted to an "Authenticate" code.
client_sock.send("hello!!")

client_sock.close()
server_sock.close()

"""
Error handling code has been omitted for clarity in the examples, but is fairly straightforward. If any of the Bluetooth
 operations fail for some reason (e.g. connection timeout, no local bluetooth resources are available, etc.) then a 
 BluetoothError is raised with an error message indicating the reason for failure. 
"""
