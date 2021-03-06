//package fr.quentinlem.prjtech;

import org.json.*;

public class Bus {

  private static int devicesId = -1;
  private int N;
  private Sensor[] devices;

  public Bus(){
    N = 100;
    devices=new Sensor[N];
  }

  public Bus(int devices_number){
    N = devices_number;
    devices = new Sensor[N];
  }

  //Création de l'acquittement OK
  public JSONObject ackMessageOk(int senderId){
    JSONObject resp = new JSONObject()
    .put("type","register")
    .put("sender_id", senderId)
    .put("ack",new JSONObject()
    .put("resp","ok"));
    return resp;
  }

  //Création de l'acquittement avec erreur (plus d'espace dans le bus)
  public JSONObject ackMessageErr(int codeErreur){
    /*
     *  Type d'erreurs :
     *  400 : plus d'espace dans le bus
     *  404 : pas d'objet à l'emplacement donné
     */

    JSONObject resp = new JSONObject()
    .put("type","register")
    .put("ack",new JSONObject()
    .put("resp","error")
    .put("error_id", codeErreur));
    return resp;
  }

  //Requête d'enregistrement auprès du bus
  public JSONObject requestRegister(JSONObject rec){

    //Récupération des informations de l'objet JSON à propos du nouvel appareil
    String strClass = rec.getString("sender_class");
    if(strClass.equals("GPS") || strClass.equals("Accelerometer") || strClass.equals("Gyroscope")){
      String name = rec.getString("sender_name");
      devicesId++; // Incrémentation de l'ID unique de la classe Bus assimilé à chaque nouveau Sensor

      //Création d'une nouvelle instance Sensor représentant l'appareil
      Sensor dev = new Sensor(devicesId, strClass, name);

      //Recherche d'un espace libre pour l'enregistrement de l'appareil dans le bus
      for(int i = 0; i < N; i++){
        if(devices[i] == null){
          devices[i] = dev; // Enregistrement de l'appareil dans le bus
          return ackMessageOk(dev.getSensorId());
        }
      }
    }
    return ackMessageErr(400);
  }

  //Requête de déconnexion auprès du bus
  public JSONObject requestDeregister(JSONObject rec){

    //Récupération de l'ID de l'appareil dans l'objet JSON
    int id = rec.getInt("sender_id");

    //Vérification de l'existence d'un appareil à l'adresse de l'appareil
    for(int i = 0; i < N; i++){
      if(devices[i] == id){
        devices[i] = null; //Effacement de l'appareil dans le bus
        return ackMessageOk(); //Envoi de l'acquittement
      }
    }
    resp = ackMessageErr(404); //Envoi de l'acquitement avec erreur (pas d'objet à l'emplacement donné)
  }

  //Requête de listage des appareils connectés au bus
  public JSONObject requestList(JSONObject rec){
    String strClass = null;
    String name = null;
    JSONArray array = new JSONArray();
    JSONObject result;

    //Création du début de l'acquittement
    JSONObject resp=new JSONObject().put("type","list");

    //Classe précisée?
    try {
      strClass = rec.getString("sender_class");
    } finally {
      //Nom précisé?
      try {
        name = rec.getString("sender_name");
      } finally {
        for(int i = 0; i < N; i++){
          //Si on cherche une classe ET un nom précis
          if(!(strClass.equals(null) || name.equals(null))) {
            if(strClass.equals(devices[i].getSensorClass()) && name.equals(devices[i].getSensorName())){
              //Ajout de l'appareil dans les résultats
              result = new JSONObject()
              .put("sender_id",devices[i].getSensorId())
              .put("sender_class",devices[i].getSensorClass())
              .put("sender_class",devices[i].getSensorName())
              .put("last_message_id",devices[i].getSensorCurrentMsgId());
              array.put(result);
            }
          // Si on cherche uniquement une classe d'appareil
          } else if(!(strClass.equals(null))) {
            if(strClass.equals(devices[i].getSensorClass())) {
              //Ajout de l'appareil dans les résultats
              result = new JSONObject()
              .put("sender_id",devices[i].getSensorId())
              .put("sender_class",devices[i].getSensorClass())
              .put("sender_class",devices[i].getSensorName())
              .put("last_message_id",devices[i].getSensorCurrentMsgId());
              array.put(result);
            }
          // Si on cherche uniquement un nom d'appareil
          } else if(!(name.equals(null))) {
            if(name.equals(devices[i].getSensorName())){
              //Ajout de l'appareil dans les résultats
              result = new JSONObject()
              .put("sender_id",devices[i].getSensorId())
              .put("sender_class",devices[i].getSensorClass())
              .put("sender_class",devices[i].getSensorName())
              .put("last_message_id",devices[i].getSensorCurrentMsgId());
              array.put(result);
            }
          //Si on veut lister tous les appareils
          } else {
            result = new JSONObject()
            .put("sender_id",devices[i].getSensorId())
            .put("sender_class",devices[i].getSensorClass())
            .put("sender_class",devices[i].getSensorName())
            .put("last_message_id",devices[i].getSensorCurrentMsgId());
            array.put(result);
          }
        }

        //Fin du message d'acquittement
        if(array.length() > 0) { //Si des résultats ont été trouvés
          resp.put("ack",new JSONObject()
          .put("resp","ok"))
          .put("results",array);
        } else { //S'il n'y a pas de résultats
          resp.put("ack",new JSONObject()
          .put("resp","error")
          .put("error_id",404));
        }
        return resp;
      }
    }
  }


  //Requête d'envoi de message d'un appareil vers le bus
  public JSONObject requestSend(JSONObject rec)
  {
  //Récupération des informations sur l'appareil et le message
  JSONObject content=rec.getJSONObject("contents");
  int sId=rec.getInt("sender_id");

  //Écriture des informations dans un objet Message
  Message m=new Message();
  m.setMsgId(sId);
  m.setMsgContent(content);
  m.setMsgTime(System.currentTimeMillis());

  //Enregistrement du message
  devices[sId].addMessage(m);

  //Envoi de l'acquittement
  JSONObject resp=new JSONObject()
  .put("type","send")
  .put("ack",new JSONObject()
  .put("resp","ok"));
  return resp;
  }


  //Requête de récupération d'un message d'un appareil particulier, à l'aide de son ID, dans le bus
  public JSONObject requestGet(JSONObject rec)
  {
  JSONObject resp;
  Message rM=new Message();
  //Récupération des informations sur l'appareil et le message
  int sId=rec.getInt("sender_id");
  long mId=rec.getInt("msg_id");

  //Récupération de l'id du message le plus ancien, s'il est accessible
  if(mId!=0)
  {
  Message oldest=devices[sId].getMessageById(mId-1);
  long oId=oldest.getMsgId();

  //Si l'id du message est inférieur ou égal au plus ancien
  if(mId<=oId)
  {
  rM=oldest;
  }
  //Si l'id du message est supérieur au plus ancien
  else
  {
  //Si l'id du message est inférieur ou égal au plus récent
  if(mId<=devices[sId].getSensorCurrentMsgId())
  rM=devices[sId].getMessageById(mId);
  }
  }
  //Si on a bien récupéré un message
  if(rM.getMsgContent()!=null)
  {
  resp=new JSONObject()
  .put("type","get")
  .put("ack",new JSONObject()
  .put("resp","ok"))
  .put("msg_id",rM.getMsgId())
  .put("date",rM.getMsgTime())
  .put("contents",rM.getMsgContent());
  return resp;
  }

  //Si une erreur est subvenue (file vide, message antérieur au plus ancien)
  else
  {
  resp=new JSONObject()
  .put("type","get")
  .put("ack",new JSONObject()
  .put("resp","ok")
  .put("error_id",404));
  return resp;
  }
  }


  //Requête de récupération du message le plus récent d'un appareil dans le bus
  public JSONObject requestGetLast(JSONObject rec)
  {
  JSONObject resp;

  //Récupération des informations sur l'appareil et le message
  int sId=rec.getInt("sender_id");

  //Récupération du message
  Message m=devices[sId].getLastMessage();

  //S'il n'y a pas de message, envoi de l'acquittement avec erreur
  if(m.equals(null))
  {
  resp=new JSONObject()
  .put("type","get_last")
  .put("ack",new JSONObject()
  .put("resp","error")
  .put("error_id",404));
  return resp;
  }

  //Envoi de l'acquittement
  resp=new JSONObject()
  .put("type","get_last")
  .put("ack",new JSONObject()
  .put("resp","ok"))
  .put("msg_id",m.getMsgId())
  .put("date",m.getMsgTime())
  .put("contents",m.getMsgContent());
  return resp;
  }
}
