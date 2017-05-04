package fr.quentinlem.prjtech;

import org.json.*;



public class Bus {

    private final static int N=100;
    private static Sensor[] devices=new Sensor[N];
    



    public JSONObject register(JSONObject rec)
    {
	//Récupération des informations de l'objet JSON à propos du nouvel appareil
	String Sclass=rec.getString("sender_class");
	String name=rec.getString("sender_name");

	//Création d'une nouvelle instance Sensor représentant l'appareil
	Sensor dev=new Sensor(Sclass,name);
	JSONObject resp;

	//Recherche d'un espace libre pour l'enregistrement de l'appareil dans le bus
	for(int i=1;i<N;i++)
	    {
		if(devices[i]==null)
		    {
			//Attribution d'un ID pour l'appareil
			dev.setSensorId(i);
			//Enregistrement de l'appareil dans le bus
			devices[i]=dev;
			//Création de l'acquittement
			resp=new JSONObject()
			    .put("type","register")
			    .put("sender_id",i)
			    .put("ack",new JSONObject()
				 .put("resp","ok"));
			//Envoi de l'acquittement
			return resp;
		    }
	    }

	//Création de l'acquittement avec erreur (plus d'espace dans le bus)
	resp=new JSONObject()
	    .put("type","register")
	    .put("ack",new JSONObject()
		 .put("resp","error")
		 .put("error_id",400));
	//Envoi de l'acquittement
	return resp;
    }

    public JSONObject deregister(JSONObject rec)
    {
	//Récupération de l'ID de l'appareil dans l'objet JSON
	int id=rec.getInt("sender_id");
	JSONObject resp;

	//Vérification de l'existence d'un appareil à l'adresse de l'appareil
	if(devices[id]!=null)
	    {
		//Effacement de l'appareil dans le bus
		devices[id]=null;
		//Envoi de l'acquittement
		resp=new JSONObject()
		    .put("type","deregister")
		    .put("ack",new JSONObject()
			 .put("resp","ok"));
	    }
	else
	    {
		//Envoi de l'acquitement avec erreur (pas d'objet à l'emplacement donné)
		resp=new JSONObject()
		    .put("type","deregister")
		    .put("ack",new JSONObject()
			 .put("resp","error")
			 .put("error_id",404));
	    }
	return resp;
    }

    public JSONObject list(JSONObject rec)
    {
	String Sclass=null;
	String name=null;
	JSONArray array=new JSONArray();
	JSONObject result;
	
	//Création du début de l'acquittement
	JSONObject resp=new JSONObject()
	    .put("type","list");
	
	//Classe précisée?
	try{
	    Sclass=rec.getString("sender_class");
	}
	finally
	    {
		//Nom précisé?
		try{
		    name=rec.getString("sender_name");
		}
		finally
		    {
			for(int i=1;i<N;i++)
			    {
				//Si on cherche un nom ET une classe précis
				if(!(Objects.equals(null,Sclass) || Objects.equals(null,name)))
				    {
					if(Objects.equals(Sclass,devices[i].getSensorClass()) && Objects.equals(name,devices[i].getSensorName()))
					    {
						//Ajout de l'appareil dans les résultats
						result=new JSONObject()
						    .put("sender_id",devices[i].getSensorId())
						    .put("sender_class",devices[i].getSensorClass())
						    .put("sender_class",devices[i].getSensorName())
						    .put("last_message_id",devices[i].getSensorCurrentMsgId());
						array.put(result);
					    }
				    }
				//Si on cherche uniquement une classe d'appareil
				else if(!(Objects.equals(null,Sclass)))
				    {
					if(Objects.equals(Sclass,devices[i].getSensorClass()))
					    {
						//Ajout de l'appareil dans les résultats
						result=new JSONObject()
						    .put("sender_id",devices[i].getSensorId())
						    .put("sender_class",devices[i].getSensorClass())
						    .put("sender_class",devices[i].getSensorName())
						    .put("last_message_id",devices[i].getSensorCurrentMsgId());
						array.put(result);
					    }
				    }
				
				//Si on cherche uniquement un nom d'appareil
				else if(!(Objects.equals(null,name)))
				    {
					if(Objects.equals(name,devices[i].getSensorName()))
					    {
						//Ajout de l'appareil dans les résultats
						result=new JSONObject()
						    .put("sender_id",devices[i].getSensorId())
						    .put("sender_class",devices[i].getSensorClass())
						    .put("sender_class",devices[i].getSensorName())
						    .put("last_message_id",devices[i].getSensorCurrentMsgId());
						array.put(result);
					    }
				    }
			    }
			//Fin du message d'acquittement
			if(array.length()>0)//Si des résultats ont été trouvés
			    {
				resp
				    .put("ack",new JSONObject()
					 .put("resp","ok"))
				    .put("results",array);
			    }
			else//S'il n'y a pas de résultats
			    {
				resp
				    .put("ack",new JSONObject()
					 .put("resp","error")
					 .put("error_id",404));
			    }
			return resp;
		    }
	    }
    }
    
    public JSONObject send(JSONObject rec)
    {
	
    }

    public JSONObject get(JSONObject rec)
    {
	
    }

    public JSONObject get_last(JSONObject rec)
    {
	
    }
}
