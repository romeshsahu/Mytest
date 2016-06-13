package au.com.capitalradiology.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mehul on 2/26/2016.
 */
public class BinForAvailable_Time
{
    @SerializedName("resource")
    @Expose
    private ArrayList<Resource> resource = new ArrayList<Resource>();

    /**
     *
     * @return
     * The resource
     */
    public ArrayList<Resource> getResource() {
        return resource;
    }

    /**
     *
     * @param resource
     * The resource
     */
    public void setResource(ArrayList<Resource> resource) {
        this.resource = resource;
    }

public class Resource {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
    @SerializedName("service_id")
    @Expose
    private Integer serviceId;
    @SerializedName("start_datetime")
    @Expose
    private String startDatetime;
    @SerializedName("end_datetime")
    @Expose
    private String endDatetime;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("created_datetime")
    @Expose
    private String createdDatetime;
    @SerializedName("lastupdated_by")
    @Expose
    private Integer lastupdatedBy;
    @SerializedName("lastupdated_datetime")
    @Expose
    private String lastupdatedDatetime;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The locationId
     */
    public Integer getLocationId() {
        return locationId;
    }

    /**
     * @param locationId The location_id
     */
    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    /**
     * @return The serviceId
     */
    public Integer getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId The service_id
     */
    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return The startDatetime
     */
    public String getStartDatetime() {
        return startDatetime;
    }

    /**
     * @param startDatetime The start_datetime
     */
    public void setStartDatetime(String startDatetime) {
        this.startDatetime = startDatetime;
    }

    /**
     * @return The endDatetime
     */
    public String getEndDatetime() {
        return endDatetime;
    }

    /**
     * @param endDatetime The end_datetime
     */
    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
    }

    /**
     * @return The createdBy
     */
    public Integer getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy The created_by
     */
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return The createdDatetime
     */
    public String getCreatedDatetime() {
        return createdDatetime;
    }

    /**
     * @param createdDatetime The created_datetime
     */
    public void setCreatedDatetime(String createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    /**
     * @return The lastupdatedBy
     */
    public Integer getLastupdatedBy() {
        return lastupdatedBy;
    }

    /**
     * @param lastupdatedBy The lastupdated_by
     */
    public void setLastupdatedBy(Integer lastupdatedBy) {
        this.lastupdatedBy = lastupdatedBy;
    }

    /**
     * @return The lastupdatedDatetime
     */
    public String getLastupdatedDatetime() {
        return lastupdatedDatetime;
    }

    /**
     * @param lastupdatedDatetime The lastupdated_datetime
     */
    public void setLastupdatedDatetime(String lastupdatedDatetime) {
        this.lastupdatedDatetime = lastupdatedDatetime;
    }
}
}
