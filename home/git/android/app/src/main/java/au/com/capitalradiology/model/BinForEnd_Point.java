package au.com.capitalradiology.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mehul on 3/14/2016.
 */
public class BinForEnd_Point
{
    @SerializedName("Endpoint")
    @Expose
    private String Endpoint;
    @SerializedName("EndpointArn")
    @Expose
    private String EndpointArn;

    /**
     *
     * @return
     * The Endpoint
     */
    public String getEndpoint() {
        return Endpoint;
    }

    /**
     *
     * @param Endpoint
     * The Endpoint
     */
    public void setEndpoint(String Endpoint) {
        this.Endpoint = Endpoint;
    }

    /**
     *
     * @return
     * The EndpointArn
     */
    public String getEndpointArn() {
        return EndpointArn;
    }

    /**
     *
     * @param EndpointArn
     * The EndpointArn
     */
    public void setEndpointArn(String EndpointArn) {
        this.EndpointArn = EndpointArn;
    }
}
