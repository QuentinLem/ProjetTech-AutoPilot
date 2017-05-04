package fr.quentinlem.prjtech;

import org.json.*;

public class Gyroscope extends Sensor {

  private float x;
  private float y;
  private float z;

  public Gyroscope(){
    super();
    this.x = 0;
    this.y = 0;
    this.z = 0;
  }

  public Gyroscope(float x, float y, float z){
    super();
    this.x = x;
    this.y = y;
    this.z = z;
  }

  // Getter
  public float getX(){
    return x;
  }

  public float getY(){
    return y;
  }

  public float getZ(){
    return z;
  }

  // Setter
  public void setX(){
    return x;
  }

  public void setY(){
    return y;
  }

  public void setZ(){
    return z;
  }

  // JSON
  public JSONObject getJsonObject(){
    JSONObject jObj = new JSONObject()
                      .put("x", getX())
                      .put("y", getY())
                      .put("z", getZ());
  }

  public JSONObject sendJsonObject(){
    JSONObject jObj = new JSONObject()
    .put("type", "send")
    .put("sender_id", getSensorId())
    .put("contents", getJsonObject());
  }
}
