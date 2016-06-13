package au.com.capitalradiology.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mehul on 2/18/2016.
 */
public class BinForService {
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

        @SerializedName("service_id")
        @Expose
        private Integer serviceId;
        @SerializedName("service_name")
        @Expose
        private String serviceName;
        @SerializedName("location_id")
        @Expose
        private Integer locationId;
        @SerializedName("location_name")
        @Expose
        private String locationName;
        @SerializedName("location_description")
        @Expose
        private String locationDescription;
        @SerializedName("position")
        @Expose
        private Integer position;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("latitude")
        @Expose
        private Double latitude;
        @SerializedName("longitude")
        @Expose
        private Double longitude;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("fax")
        @Expose
        private String fax;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("opening_times")
        @Expose
        private String openingTimes;
        @SerializedName("full_address")
        @Expose
        private String fullAddress;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("image_id")
        @Expose
        private Integer imageId;
        @SerializedName("image_main_url")
        @Expose
        private String imageMainUrl;
        @SerializedName("image_thumb_url")
        @Expose
        private String imageThumbUrl;
        @SerializedName("surge_fee")
        @Expose
        private Integer surgeFee;
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
         * @return The locationName
         */
        public String getLocationName() {
            return locationName;
        }

        /**
         * @param locationName The location_name
         */
        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        /**
         * @return The locationDescription
         */
        public String getLocationDescription() {
            return locationDescription;
        }

        /**
         * @param locationDescription The location_description
         */
        public void setLocationDescription(String locationDescription) {
            this.locationDescription = locationDescription;
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
         * @return The location
         */
        public String getLocation() {
            return location;
        }

        /**
         * @param location The location
         */
        public void setLocation(String location) {
            this.location = location;
        }

        /**
         * @return The latitude
         */
        public Double getLatitude() {
            return latitude;
        }

        /**
         * @param latitude The latitude
         */
        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        /**
         * @return The longitude
         */
        public Double getLongitude() {
            return longitude;
        }

        /**
         * @param longitude The longitude
         */
        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        /**
         * @return The phone
         */
        public String getPhone() {
            return phone;
        }

        /**
         * @param phone The phone
         */
        public void setPhone(String phone) {
            this.phone = phone;
        }

        /**
         * @return The fax
         */
        public String getFax() {
            return fax;
        }

        /**
         * @param fax The fax
         */
        public void setFax(String fax) {
            this.fax = fax;
        }

        /**
         * @return The email
         */
        public String getEmail() {
            return email;
        }

        /**
         * @param email The email
         */
        public void setEmail(String email) {
            this.email = email;
        }

        /**
         * @return The openingTimes
         */
        public String getOpeningTimes() {
            return openingTimes;
        }

        /**
         * @param openingTimes The opening_times
         */
        public void setOpeningTimes(String openingTimes) {
            this.openingTimes = openingTimes;
        }

        /**
         * @return The fullAddress
         */
        public String getFullAddress() {
            return fullAddress;
        }

        /**
         * @param fullAddress The full_address
         */
        public void setFullAddress(String fullAddress) {
            this.fullAddress = fullAddress;
        }

        /**
         * @return The category
         */
        public String getCategory() {
            return category;
        }

        /**
         * @param category The category
         */
        public void setCategory(String category) {
            this.category = category;
        }

        /**
         * @return The imageId
         */
        public Integer getImageId() {
            return imageId;
        }

        /**
         * @param imageId The image_id
         */
        public void setImageId(Integer imageId) {
            this.imageId = imageId;
        }

        /**
         * @return The imageMainUrl
         */
        public String getImageMainUrl() {
            return imageMainUrl;
        }

        /**
         * @param imageMainUrl The image_main_url
         */
        public void setImageMainUrl(String imageMainUrl) {
            this.imageMainUrl = imageMainUrl;
        }

        /**
         * @return The imageThumbUrl
         */
        public String getImageThumbUrl() {
            return imageThumbUrl;
        }

        /**
         * @param imageThumbUrl The image_thumb_url
         */
        public void setImageThumbUrl(String imageThumbUrl) {
            this.imageThumbUrl = imageThumbUrl;
        }

        /**
         * @return The surgeFee
         */
        public Integer getSurgeFee() {
            return surgeFee;
        }

        /**
         * @param surgeFee The surge_fee
         */
        public void setSurgeFee(Integer surgeFee) {
            this.surgeFee = surgeFee;
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
