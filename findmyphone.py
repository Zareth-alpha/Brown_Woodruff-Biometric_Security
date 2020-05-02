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

import bluetooth
import getpass
import os

target_name = input("What is your device name: ")  # needs to prompt user or check some sort of settings...
target_address = None
# This needs to be used on the Android device's side.
server_uuid = "a2087a8e-7dd3-11ea-bc55-0242ac130003"

# The routine discover_devices() scans for approximately 10 seconds and returns a list of addresses of detected devices.
nearby_devices = bluetooth.discover_devices()

server_sock = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
# The documentation from the link above uses a depreciated function. This is the modern format.
port = bluetooth.PORT_ANY
server_sock.bind(("", port))
server_sock.listen(1)
"""
PyBluez represents a bluetooth address as a string of the form ``xx:xx:xx:xx:xx"
"""


# FUNCTION DEFINITIONS:
# Check the available, nearby devices for a matching name. return the target address.
def lookup_device(target_device_name):
    for bdaddr in nearby_devices:
        if target_device_name == bluetooth.lookup_name(bdaddr):
            target_device_address = bdaddr
            return target_device_address


bluetooth.advertise_service(server_sock, "Biometric Service", server_uuid)

client_sock, address = server_sock.accept()
print("Accepted connection from %s" % address)

data = client_sock(1024)
print("received [%s]" % data)

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

# Running Code here: - JB 5/2
target_address = lookup_device(target_name)
# If there is an address, print it out.
if target_address is not None:
    print("found target bluetooth device with address %s \n" % target_address)
# otherwise announce that there are none.
else:
    print("could not find target bluetooth device nearby.\n")

# Debug print only. Comment out when testing is over.
print("Listening on port: %d" % port)
