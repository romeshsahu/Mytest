package au.com.capitalradiology.request;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import au.com.capitalradiology.R;
import au.com.capitalradiology.volley.AuthFailureError;
import au.com.capitalradiology.volley.NetworkError;
import au.com.capitalradiology.volley.NetworkResponse;
import au.com.capitalradiology.volley.NoConnectionError;
import au.com.capitalradiology.volley.ServerError;
import au.com.capitalradiology.volley.TimeoutError;
import au.com.capitalradiology.volley.VolleyError;

public class VolleyErrorHelper 
{
	/**
	* Returns appropriate message which is to be displayed to the user 
	* against the specified error object.
	* 
	* @param error
	* @param context
	* @return
	*/
	
 public static String getMessage(Context context,Object error) 
 {
     if (error instanceof TimeoutError)
     {
    	 Log.e("", "Error1 : "+error);
         return context.getResources().getString(R.string.time_out_error);
     }
     else if (isServerProblem(error)) 
     {
    	 Log.e("", "Error2 : "+error);
         return handleServerError(context,error);
     }
     else if (isNetworkProblem(error)) {
    	 Log.e("", "Error3 : "+error);
         return context.getResources().getString(R.string.no_internet_error);
         
     }
     
     return context.getResources().getString(R.string.other_error);
 }
 
 /**
 * Determines whether the error is related to network
 * @param error
 * @return
 */
 private static boolean isNetworkProblem(Object error) {
     return (error instanceof NetworkError) || (error instanceof NoConnectionError);
 }
 /**
 * Determines whether the error is related to server
 * @param error
 * @return
 */
 private static boolean isServerProblem(Object error) {
     return (error instanceof ServerError) || (error instanceof AuthFailureError);
 }
 /**
 * Handles the server error, tries to determine whether to show a stock message or to 
 * show a message retrieved from the server.
 * 
 * @param err
 * @param context
 * @return
 */
 private static String handleServerError(Context context,Object err) {
     VolleyError error = (VolleyError) err;
 
     NetworkResponse response = error.networkResponse;
 
     if (response != null) {
         switch (response.statusCode) {
           case 404:
               try {
                   Log.e("", "Error : "+response.statusCode);
                   // server might return error like this { "error": "Some error occured" }
                   // Use "Gson" to parse the result

                   String json = new String(response.data);
                   JSONObject jsonObject = new JSONObject(json);

                   if(jsonObject.has("error")) {
                       JSONObject jmain = jsonObject.getJSONObject("error");
                       return jmain.getString("message");
                   }

               } catch (Exception e) {
                   e.printStackTrace();
               }
               // invalid request
               return error.getMessage();
             case 500:
                 try {
                     Log.e("", "Error : "+response.statusCode);
                     // server might return error like this { "error": "Some error occured" }
                     // Use "Gson" to parse the result

                     String json = new String(response.data);
                     JSONObject jsonObject = new JSONObject(json);

                     if(jsonObject.has("error")) {
                         JSONObject jmain = jsonObject.getJSONObject("error");
                         return jmain.getString("message");
                     }

                 } catch (Exception e) {
                     e.printStackTrace();
                 }
                 // invalid request
                 return error.getMessage();
           case 422:
               try {
                   Log.e("", "Error : "+response.statusCode);
                   // server might return error like this { "error": "Some error occured" }
                   // Use "Gson" to parse the result

                   String json = new String(response.data);
                   JSONObject jsonObject = new JSONObject(json);

                   if(jsonObject.has("error")) {
                       JSONObject jmain = jsonObject.getJSONObject("error");
                       return jmain.getString("message");
                   }

               } catch (Exception e) {
                   e.printStackTrace();
               }
               // invalid request
               return error.getMessage();
           case 401:
               try {
            	   Log.e("", "Error : "+response.statusCode);
                   // server might return error like this { "error": "Some error occured" }
                   // Use "Gson" to parse the result

                   String json = new String(response.data);
                   JSONObject jsonObject = new JSONObject(json);

                   if(jsonObject.has("error")) {
                       JSONObject jmain = jsonObject.getJSONObject("error");
                       return jmain.getString("message");
                   }

               } catch (Exception e) {
                   e.printStackTrace();
               }
               // invalid request
               return error.getMessage();

             case 400:
                 try {
                     Log.e("", "Error : "+response.statusCode);
                     // server might return error like this { "error": "Some error occured" }
                     // Use "Gson" to parse the result
                     String json = new String(response.data);
                     JSONObject jsonObject = new JSONObject(json);

                     if(jsonObject.has("error")) {
                         JSONObject jmain = jsonObject.getJSONObject("error");
                         return jmain.getString("message");
                     }

                 } catch (Exception e) {
                     e.printStackTrace();
                 }
                 // invalid request
                 return error.getMessage();

           default:
        	   return context.getResources().getString(R.string.other_error);
           }
     }
       return "Generic Error";
 }
}
