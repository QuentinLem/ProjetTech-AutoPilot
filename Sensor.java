import java.lang.String;
import java.net.*;
import java.io.*;

import org.json.*;

public class Sensor {

  private final static int MESSAGE_TAB_SIZE = 100;

  private static int sensorId = 0;
  private String sensorClass;
  private String sensorName;
  private int sensorCurrentMsgId;

  //Constructeur
  public Sensor(){
    sensorId += 1;
    sensorName = new String();
    sensorClass = new String();
    sensorCurrentMsgId = 0;
  }

  public Sensor(String class, String name){
    sensorId += 1;
    sensorClass = class;
    sensorName = name;
    sensorCurrentMsgId = 0;
  }

  // Setter
  public void setSensorId(int id){
    sensorId = id;
  }

  public void setSensorName(String name){
    sensorName = name;
  }

  public void setSensorClass(String class){
    sensorClass = class;
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

  public int getSensorCurrentMsgId(){
    return sensorCurrentMsgId;
  }

}
