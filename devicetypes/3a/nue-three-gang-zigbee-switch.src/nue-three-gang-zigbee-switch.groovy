/**
*  Copyright 2015 SmartThings
*
*  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License. You may obtain a copy of the License at:
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
*  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
*  for the specific language governing permissions and limitations under the License.
*
*/
 
metadata {
    definition (name: "Nue Three Gang ZigBee Switch", namespace: "3A", author: "SmartThings", ocfDeviceType: "oic.d.switch") {
		capability "Switch"
		capability "Configuration"
		capability "Refresh"
		capability "Actuator"
		capability "Sensor"
		command "OneOn"
		command "OneOff"
		command "TwoOn"
		command "TwoOff"
		command "ThreeOn"
		command "ThreeOff"
        attribute "switch1","ENUM",["on","off"]
        attribute "switch2","ENUM",["on","off"]
        attribute "switch3","ENUM",["on","off"]    
        attribute "switchstate","ENUM",["on","off"] 
 
        fingerprint profileId: "0104", inClusters: "0000, 0003, 0004, 0005, 0006"
        fingerprint profileId: "0104", inClusters: "0000, 0003, 0006", outClusters: "0003, 0006, 0019, 0406", manufacturer: "Leviton", model: "ZSS-10", deviceJoinName: "Leviton Switch"
        fingerprint profileId: "0104", inClusters: "0000, 0003, 0006", outClusters: "000A", manufacturer: "HAI", model: "65A21-1", deviceJoinName: "Leviton Wireless Load Control Module-30amp"
        fingerprint profileId: "0104", inClusters: "0000, 0003, 0004, 0005, 0006", outClusters: "0003, 0006, 0008, 0019, 0406", manufacturer: "Leviton", model: "DL15A", deviceJoinName: "Leviton Lumina RF Plug-In Appliance Module"
        fingerprint profileId: "0104", inClusters: "0000, 0003, 0004, 0005, 0006", outClusters: "0003, 0006, 0008, 0019, 0406", manufacturer: "Leviton", model: "DL15S", deviceJoinName: "Leviton Lumina RF Switch"
        fingerprint profileId: "0104", inClusters: "0000, 0003, 0004, 0005, 0006", outClusters: "0003, 0006, 0008, 0019, 0406", manufacturer: "Feibit Inc co.", model: "FB56+ZSW1IKJ1.7", deviceJoinName: "3A Nue Three Gang ZigBee Switch (1.7)"
        fingerprint profileId: "0104", inClusters: "0000, 0003, 0004, 0005, 0006", outClusters: "0003, 0006, 0008, 0019, 0406", manufacturer: "Feibit Inc co.", model: "FB56+ZSW05HG1.2", deviceJoinName: "3A Nue Three Gang ZigBee Switch (1.2)"
    }
 
    // simulator metadata
        simulator {
        // status messages
        status "on": "on/off: 1"
        status "off": "on/off: 0"
 
        // reply messages
        reply "zcl on-off on": "on/off: 1"
        reply "zcl on-off off": "on/off: 0"
    }
 
// UI tile definitions
tiles {
        multiAttributeTile(name:"switch1", type: "lighting", width: 1, height: 1, canChangeIcon: true) {
        tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
		attributeState "on", label:'SW1 On', action:"OneOff", icon:"st.switches.light.on", backgroundColor:"#00a0dc", nextState:"turningOff"
		attributeState "off", label:'SW1 Off', action:"OneOn", icon:"st.switches.light.off", backgroundColor:"#ffffff", nextState:"turningOn"
        attributeState "turningOn", label:'SW1 On', action:"OneOff", icon:"st.switches.light.on", backgroundColor:"#00A0DC", nextState:"turningOff"
        attributeState "turningOff", label:'SW1 Off', action:"OneOn", icon:"st.switches.light.off", backgroundColor:"#ffffff", nextState:"turningOn"
	}
}
        multiAttributeTile(name:"switch2", type: "lighting", width: 1, height: 1, canChangeIcon: true) {
        tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
		attributeState "on", label:'SW2 On', action:"TwoOff", icon:"st.switches.light.on", backgroundColor:"#00a0dc", nextState:"turningOff"
		attributeState "off", label:'SW2 Off', action:"TwoOn", icon:"st.switches.light.off", backgroundColor:"#ffffff", nextState:"turningOn"
        attributeState "turningOn", label:'SW2 On', action:"TwoOff", icon:"st.switches.light.on", backgroundColor:"#00A0DC", nextState:"turningOff"
        attributeState "turningOff", label:'SW2 Off', action:"TwoOn", icon:"st.switches.light.off", backgroundColor:"#ffffff", nextState:"turningOn" 
	}
	}
        multiAttributeTile(name:"switch3", type: "lighting", width: 1, height: 1, canChangeIcon: true) {
        tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
		attributeState "on", label:'SW3 On', action:"ThreeOff", icon:"st.switches.light.on", backgroundColor:"#00a0dc", nextState:"turningOff"
		attributeState "off", label:'SW3 Off', action:"ThreeOn", icon:"st.switches.light.off", backgroundColor:"#ffffff", nextState:"turningOn"
        attributeState "turningOn", label:'SW3 On', action:"ThreeOff", icon:"st.switches.light.on", backgroundColor:"#00A0DC", nextState:"turningOff"
        attributeState "turningOff", label:'SW3 Off', action:"ThreeOn", icon:"st.switches.light.off", backgroundColor:"#ffffff", nextState:"turningOn"
	}
}
standardTile("refresh", "device.refresh", inactiveLabel: false, decoration: "flat", width: 1, height: 1) {
            state "default", label:"", action:"refresh.refresh", icon:"st.secondary.refresh"
        }
	main (["switch1", "switch2", "switch3"])
	details(["switch1", "switch2", "switch3", "refresh"])
}
}
 
// Parse incoming device messages to generate events
def parse(String description) {
    log.debug "description is $description"
    def event = zigbee.getEvent(description)
    log.debug zigbee.parseDescriptionAsMap(description)
    if (event) {
    	sendEvent(event)
    }
    else {
        log.warn "DID NOT PARSE MESSAGE for description : $description"
        log.debug zigbee.parseDescriptionAsMap(description)
    }
}
 
def OneOff() {
    device.endpointId ="10"
    zigbee.off()
}
 
def OneOn() {
    device.endpointId ="10"
    zigbee.on()
}


def TwoOff() {
    device.endpointId ="11"
    zigbee.off()
}
 
def TwoOn() {
    device.endpointId ="11"
    zigbee.on()
}
 
 
def ThreeOff() {
    device.endpointId ="12"
    zigbee.off()
}
 
def ThreeOn() {
    device.endpointId ="12"
    zigbee.on()
}
 
 
/*def refresh() {
    zigbee.onoffRefresh() + zigbee.onOffConfig()
}
 
def configure() {
    // Device-Watch allows 2 check-in misses from device + ping (plus 2 min lag time)
    sendEvent(name: "checkInterval", value: 2 * 10 * 60 + 2 * 60, displayed: false, data: [protocol: "zigbee", hubHardwareId: device.hub.hardwareID])
    log.debug "Configuring Reporting and Bindings."
    zigbee.onOffRefresh() + zigbee.onOffConfig()
}
*/
def refresh() {
    return zigbee.readAttribute(0x0006, 0x0000) +
        zigbee.readAttribute(0x0008, 0x0000) +
        zigbee.configureReporting(0x0006, 0x0000, 0x10, 0, 600, null) +
        zigbee.configureReporting(0x0008, 0x0000, 0x20, 1, 3600, 0x01)
}

def configure() {
    log.debug "Configuring Reporting and Bindings."

    return zigbee.configureReporting(0x0006, 0x0000, 0x10, 0, 600, null) +
        zigbee.configureReporting(0x0008, 0x0000, 0x20, 1, 3600, 0x01) +
        zigbee.readAttribute(0x0006, 0x0000) +
        zigbee.readAttribute(0x0008, 0x0000)
}