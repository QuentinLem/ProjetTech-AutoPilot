//package fr.quentinlem.prjtech;

import java.lang.String;
import java.net.*;
import java.io.*;

import org.json.*;

public class Sensor {

    private final static int MESSAGE_TAB_SIZE = 100;

    private int sensorId = -1;
    private String sensorClass;
    private String sensorName;

    private Message[] sensorTabMessages;
    private long sensorCurrentMsgId;

    //Constructeur
    public Sensor(){
      sensorId = -1;
      sensorName = new String();
      sensorClass = new String();
      sensorTabMessages = new Message[MESSAGE_TAB_SIZE];
      sensorCurrentMsgId = 0;
    }

    public Sensor(int id, String strClass, String name){
      sensorId = id;
      sensorClass = strClass;
      sensorName = name;
      sensorTabMessages = new Message[MESSAGE_TAB_SIZE];
      sensorCurrentMsgId = 0;
    }

    // Setter
    public void setSensorId(int id){
      sensorId = id;
    }

    public void setSensorName(String name){
      sensorName = name;
    }

    public void setSensorClass(String strClass){
      sensorClass = strClass;
    }

    public void updateSensorCurrentMsgId(){
      sensorCurrentMsgId += 1;
    }


    // Getter
    public int getSensorId(){
      return sensorId;
    }

    public String getSensorName(){
      return sensorName;
    }

    public String getSensorClass(){
      return sensorClass;
    }

    public long getSensorCurrentMsgId(){
      return sensorCurrentMsgId;
    }

    //Traitement des messages
    public void addMessage(Message msg){
      msg.setMsgId(sensorCurrentMsgId);
      sensorTabMessages[(int)getSensorCurrentMsgId()%MESSAGE_TAB_SIZE] = msg;
      updateSensorCurrentMsgId();
    }

    public Message getLastMessage(){
	if(getSensorCurrentMsgId() == 0){
	    return sensorTabMessages[MESSAGE_TAB_SIZE-1];
	} else {
	    return sensorTabMessages[(int)getSensorCurrentMsgId()-1%MESSAGE_TAB_SIZE];
	}
    }

    public Message getMessageById(long id){
	return sensorTabMessages[(int)id%MESSAGE_TAB_SIZE];
    }

}
