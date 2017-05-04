import org.json.*;



public class Bus {

    private final static int N=100;
    private static Sensor[] devices=new JSONObject[N];
    



    public JSONObject register(JSONObject rec)
    {
	String class=rec.getString("sender_class");
	String name=rec.getString("sender_name");
	JSONObject dev=new JSONObject()
	    .put("class",class)
	    .put("name",name);
	JSONObject resp;
	for(int i=1;i<N;i++)
	    {
		if(devices[i]!=null)
		    {
			devices[i]=dev;
			resp=new JSONObject()
			    .put("type","register")
			    .put("sender_id",i)
			    .put("ack",new JSONObject()
				 .put("resp","ok"));
			return resp;
		    }
	    }
	resp=new JSONObject()
	    .put("type","register")
	    .put("ack",new JSONObject()
		 .put("resp","error")
		 .put("error_id",400));
	return resp;
    }

    public JSONObject deregister(JSONObject rec)
    {
	int id=rec.getInt("sender_id");
	JSONObject resp;
	if(devices[id]!=null)
	    {
		devices[id]=null;
		resp=new JSONObject()
		    .put("type","deregister")
		    .put("ack",new JSONObject()
			 .put("resp","ok"));
	    }
	else
	    {
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
