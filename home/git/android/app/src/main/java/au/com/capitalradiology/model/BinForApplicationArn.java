package au.com.capitalradiology.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mehul on 3/22/2016.
 */
public class BinForApplicationArn {
    @SerializedName("Application")
    @Expose
    private String Application;
    @SerializedName("PlatformApplicationArn")
    @Expose
    private String PlatformApplicationArn;
    @SerializedName("Attributes")
    @Expose
    private Attributes Attributes;

    /**
     * @return The Application
     */
    public String getApplication() {
        return Application;
    }

    /**
     * @param Application The Application
     */
    public void setApplication(String Application) {
        this.Application = Application;
    }

    /**
     * @return The PlatformApplicationArn
     */
    public String getPlatformApplicationArn() {
        return PlatformApplicationArn;
    }

    /**
     * @param PlatformApplicationArn The PlatformApplicationArn
     */
    public void setPlatformApplicationArn(String PlatformApplicationArn) {
        this.PlatformApplicationArn = PlatformApplicationArn;
    }

    /**
     * @return The Attributes
     */
    public Attributes getAttributes() {
        return Attributes;
    }

    /**
     * @param Attributes The Attributes
     */
    public void setAttributes(Attributes Attributes) {
        this.Attributes = Attributes;
    }

    public class Attributes {

        @SerializedName("Enabled")
        @Expose
        private String Enabled;

        /**
         * @return The Enabled
         */
        public String getEnabled() {
            return Enabled;
        }

        /**
         * @param Enabled The Enabled
         */
        public void setEnabled(String Enabled) {
            this.Enabled = Enabled;
        }
    }
}
