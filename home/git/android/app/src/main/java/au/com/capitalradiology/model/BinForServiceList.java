package au.com.capitalradiology.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mehul on 2/26/2016.
 */
public class BinForServiceList
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

    @SerializedName("service_id")
    @Expose
    private Integer serviceId;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("modify_date")
    @Expose
    private Object modifyDate;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("cost")
    @Expose
    private Integer cost;
    @SerializedName("currency")
    @Expose
    private String currency;

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
     * @return The serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName The service_name
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * @return The createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate The create_date
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * @return The modifyDate
     */
    public Object getModifyDate() {
        return modifyDate;
    }

    /**
     * @param modifyDate The modify_date
     */
    public void setModifyDate(Object modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * @return The position
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * @param position The position
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * @return The cost
     */
    public Integer getCost() {
        return cost;
    }

    /**
     * @param cost The cost
     */
    public void setCost(Integer cost) {
        this.cost = cost;
    }

    /**
     * @return The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
}
