//package fr.quentinlem.prjtech;

import org.json.*;

public class GPS extends Sensor {

  private double lng;   //longitude
  private double lat;   //latitude

  public GPS(){
    super();
    this.lng = 0;
    this.lat = 0;
  }

  public GPS(double lng, double lat){
    super();
    this.lng = lng;
    this.lat = lat;
  }

  // Getter
  public double getLng(){
    return lng;
  }

  public double getLat(){
    return lat;
  }

  // Setter
  public void setLng(double lng){
    this.lng = lng;
  }

  public void setLat(double lat){
    this.lat = lat;
  }

  // JSON
  public JSONObject getJsonObject(){
    JSONObject jObj = new JSONObject()
    .put("lat", getLat())
    .put ("lng", getLng());
    return jObj;
  }

  public JSONObject sendJsonObject(){
    JSONObject jObj = new JSONObject()
    .put("type", "send")
    .put("sender_id", getSensorId())
    .put("contents", getJsonObject());
    return jObj;
  }

}
