package au.com.capitalradiology.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mehul on 4/6/2016.
 */
public class BinForSNSAttributes
{
    @SerializedName("Endpoint")
    @Expose
    private String Endpoint;
    @SerializedName("EndpointArn")
    @Expose
    private String EndpointArn;
    @SerializedName("Attributes")
    @Expose
    private Attributes Attributes;

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

    /**
     *
     * @return
     * The Attributes
     */
    public Attributes getAttributes() {
        return Attributes;
    }

    /**
     *
     * @param Attributes
     * The Attributes
     */
    public void setAttributes(Attributes Attributes) {
        this.Attributes = Attributes;
    }

     public class Attributes {

        @SerializedName("Enabled")
        @Expose
        private String Enabled;
        @SerializedName("Token")
        @Expose
        private String Token;
        @SerializedName("CustomUserData")
        @Expose
        private String CustomUserData;

        /**
         *
         * @return
         * The Enabled
         */
        public String getEnabled() {
            return Enabled;
        }

        /**
         *
         * @param Enabled
         * The Enabled
         */
        public void setEnabled(String Enabled) {
            this.Enabled = Enabled;
        }

        /**
         *
         * @return
         * The Token
         */
        public String getToken() {
            return Token;
        }

        /**
         *
         * @param Token
         * The Token
         */
        public void setToken(String Token) {
            this.Token = Token;
        }

        /**
         *
         * @return
         * The CustomUserData
         */
        public String getCustomUserData() {
            return CustomUserData;
        }

        /**
         *
         * @param CustomUserData
         * The CustomUserData
         */
        public void setCustomUserData(String CustomUserData) {
            this.CustomUserData = CustomUserData;
        }

    }
}
