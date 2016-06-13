package au.com.capitalradiology.Utils;

import com.google.gson.Gson;

import org.json.JSONObject;

public class JSONParsingAdapter<T> {

	public Object parseJsonObject(JSONObject jobj,T objectType){
		
		Gson gson = new Gson();
		Object object = gson.fromJson(jobj.toString(), objectType.getClass());
		return object;
	}
	
}
