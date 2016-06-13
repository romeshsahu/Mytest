package au.com.capitalradiology.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mehul on 2/20/2016.
 */
public class BinForPatient_Register {
    @SerializedName("resource")
    @Expose
    private ArrayList<Resource> resource = new ArrayList<Resource>();

    /**
     * @return The resource
     */
    public ArrayList<Resource> getResource() {
        return resource;
    }

    /**
     * @param resource The resource
     */
    public void setResource(ArrayList<Resource> resource) {
        this.resource = resource;
    }

    public class Resource {

        @SerializedName("id")
        @Expose
        private Integer id;

        /**
         * @return The id
         */
        public Integer getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(Integer id) {
            this.id = id;
        }
    }
}
