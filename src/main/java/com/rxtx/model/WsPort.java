package com.rxtx.model;

public class WsPort {
	private int id;
	private String portName;
	private int portType;
	private String portSerial;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPortName() {
		return portName;
	}
	public void setPortName(String portName) {
		this.portName = portName;
	}
	public int getPortType() {
		return portType;
	}
	public void setPortType(int portType) {
		this.portType = portType;
	}
	public String getPortSerial() {
		return portSerial;
	}
	public void setPortSerial(String portSerial) {
		this.portSerial = portSerial;
	}

}
