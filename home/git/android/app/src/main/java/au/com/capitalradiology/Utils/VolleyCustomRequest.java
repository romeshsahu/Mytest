package au.com.capitalradiology.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import au.com.capitalradiology.volley.AuthFailureError;
import au.com.capitalradiology.volley.NetworkResponse;
import au.com.capitalradiology.volley.ParseError;
import au.com.capitalradiology.volley.Response;
import au.com.capitalradiology.volley.toolbox.HttpHeaderParser;
import au.com.capitalradiology.volley.toolbox.JsonObjectRequest;

public class VolleyCustomRequest extends JsonObjectRequest
{
	private Response.Listener<JSONObject> listener;
	private String postData ;
	private Priority mPriority = Priority.NORMAL;
	
	
	public VolleyCustomRequest(int method,String url, String params,JSONObject jsonRequest,Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener)
	{
        super(method, url, jsonRequest, reponseListener,errorListener);
		this.listener = reponseListener;
		this.postData = params;
        Utils.LogE("Parameters : "+params);

 	}

    public Map getHeaders() throws AuthFailureError
    {
        HashMap hashmap = new HashMap();
        hashmap.put("X-DreamFactory-Api-Key", UserSession.currentSession().Api_Key);
        hashmap.put("Content-Type", "application/json");
        if (UserSession.currentSession().userToken != null)
        {
            hashmap.put("X-DreamFactory-Session-Token", UserSession.currentSession().userToken);
        }
        return hashmap;
    }

	@Override
	public Priority getPriority() {
		return mPriority;
	}

	public void setPriority(Priority priority) {
		mPriority = priority;
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

			try 
			{
				String jsonString = new String(response.data,	HttpHeaderParser.parseCharset(response.headers));
                Utils.LogE("==================================================");
				Utils.LogE("Response " +jsonString);
                Utils.LogE("==================================================");
				return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
			}
			catch (UnsupportedEncodingException e) 
			{
				return Response.error(new ParseError(e));
			}
			catch (JSONException je) 
			{
				//Log.e("Error...!!!!" ,"Error...!!!!"+je);
				return Response.error(new ParseError(je));
			}
	}

	@Override
	protected void deliverResponse(JSONObject response) {
		listener.onResponse(response);
		
	}
	
	@Override
	 public byte[] getBody() {
	  return postData.getBytes();
	 }
}