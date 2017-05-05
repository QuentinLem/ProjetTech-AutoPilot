//package fr.quentinlem.prjtech;

import org.json.*;

public class Message {

    public JSONObject content;
    public long msg_id;
    public long time;

    //constructeurs
    public Message() {
      content = null;
      msg_id = 0;
      time = 0;
    }

    /*
    public Message( ?? ) {
      content = new JSONObject().put("msg_id", id).put("date", t).put("contents", JSONObject ?);
      msg_id = ??;
      time = ??;
    }
    */


    public long getMsgId() {
      return this.msg_id;
    }

    public void setMsgId(long id) {
      this.msg_id = id;
    }

    public long getMsgTime() {
      return this.time;
    }

    public void setMsgTime(long t) {
      this.time = System.currentTimeMillis();
    }

    public JSONObject getMsgContent() {
      return this.content;
    }

    public void setMsgContent(JSONObject jo) {
      this.content = jo;
    }

}
