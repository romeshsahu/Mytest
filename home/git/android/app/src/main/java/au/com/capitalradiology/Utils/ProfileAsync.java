package au.com.capitalradiology.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import au.com.capitalradiology.dreamfactory_api.ApiException;
import au.com.capitalradiology.dreamfactory_api.BaseAsyncRequest;
import au.com.capitalradiology.dreamfactory_api.FileRequest;

public class ProfileAsync extends BaseAsyncRequest
{
    private String strImage_Path="";
    public GetResponse getResponse;

    public ProfileAsync(String image_path,final GetResponse getResponse) {
        super();
        this.strImage_Path = image_path;
        this.getResponse = getResponse;
    }

    @Override
    protected void doSetup() throws ApiException, JSONException
    {
        use_logging = true;
        callerName = "registerActivity";
        serviceName = "s3";
        endPoint = "patient/"+ Utils.getUniqueName();
        verb = "POST";

        // post email and password to get back session token
        // need session token to make every call other than login and challenge
        requestBody = new JSONObject();
        fileRequest = new FileRequest();
        fileRequest.setName(Utils.getUniqueName());
        fileRequest.setPath(strImage_Path);
        fileRequest.setContent_type("image/*");
    }

    @Override
    protected void processResponse(String response) throws ApiException, JSONException {
        getResponse.getData(response);
    }
}