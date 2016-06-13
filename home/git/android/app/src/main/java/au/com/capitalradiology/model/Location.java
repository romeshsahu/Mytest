package au.com.capitalradiology.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mehul on 2/14/2016.
 */
public class Location
{
    private ArrayList<Resource> resource = new ArrayList<Resource>();

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

        @SerializedName("location_id")
        @Expose
        private Integer locationId;
        @SerializedName("service_names")
        @Expose
        private String serviceNames;
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
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
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
         *
         * @return
         * The locationId
         */
        public Integer getLocationId() {
            return locationId;
        }

        /**
         *
         * @param locationId
         * The location_id
         */
        public void setLocationId(Integer locationId) {
            this.locationId = locationId;
        }

        /**
         *
         * @return
         * The locationName
         */
        public String getLocationName() {
            return locationName;
        }

        /**
         *
         * @param locationName
         * The location_name
         */
        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        /**
         *
         * @return
         * The locationDescription
         */
        public String getLocationDescription() {
            return locationDescription;
        }

        /**
         *
         * @param locationDescription
         * The location_description
         */
        public void setLocationDescription(String locationDescription) {
            this.locationDescription = locationDescription;
        }

        /**
         *
         * @return
         * The position
         */
        public Integer getPosition() {
            return position;
        }

        /**
         *
         * @param position
         * The position
         */
        public void setPosition(Integer position) {
            this.position = position;
        }

        /**
         *
         * @return
         * The location
         */
        public String getLocation() {
            return location;
        }

        /**
         *
         * @param location
         * The location
         */
        public void setLocation(String location) {
            this.location = location;
        }

        /**
         *
         * @return
         * The latitude
         */
        public String getLatitude() {
            return latitude;
        }

        /**
         *
         * @param latitude
         * The latitude
         */
        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        /**
         *
         * @return
         * The longitude
         */
        public String getLongitude() {
            return longitude;
        }

        /**
         *
         * @param longitude
         * The longitude
         */
        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        /**
         *
         * @return
         * The phone
         */
        public String getPhone() {
            return phone;
        }

        /**
         *
         * @param phone
         * The phone
         */
        public void setPhone(String phone) {
            this.phone = phone;
        }

        /**
         *
         * @return
         * The fax
         */
        public String getFax() {
            return fax;
        }

        /**
         *
         * @param fax
         * The fax
         */
        public void setFax(String fax) {
            this.fax = fax;
        }

        /**
         *
         * @return
         * The email
         */
        public String getEmail() {
            return email;
        }

        /**
         *
         * @param email
         * The email
         */
        public void setEmail(String email) {
            this.email = email;
        }

        /**
         *
         * @return
         * The openingTimes
         */
        public String getOpeningTimes() {
            return openingTimes;
        }

        /**
         *
         * @param openingTimes
         * The opening_times
         */
        public void setOpeningTimes(String openingTimes) {
            this.openingTimes = openingTimes;
        }


        /**
         *
         * @return
         * The fullAddress
         */
        public String getFullAddress() {
            return fullAddress;
        }

        /**
         *
         * @param fullAddress
         * The full_address
         */
        public void setFullAddress(String fullAddress) {
            this.fullAddress = fullAddress;
        }

        /**
         *
         * @return
         * The category
         */
        public String getCategory() {
            return category;
        }

        /**
         *
         * @param category
         * The category
         */
        public void setCategory(String category) {
            this.category = category;
        }

        public String getServiceNames() {
            return serviceNames;
        }

        public void setServiceNames(String serviceNames) {
            this.serviceNames = serviceNames;
        }

        public Integer getImageId() {
            return imageId;
        }

        public void setImageId(Integer imageId) {
            this.imageId = imageId;
        }

        public String getImageMainUrl() {
            return imageMainUrl;
        }

        public void setImageMainUrl(String imageMainUrl) {
            this.imageMainUrl = imageMainUrl;
        }

        public String getImageThumbUrl() {
            return imageThumbUrl;
        }

        public void setImageThumbUrl(String imageThumbUrl) {
            this.imageThumbUrl = imageThumbUrl;
        }

        public Integer getSurgeFee() {
            return surgeFee;
        }

        public void setSurgeFee(Integer surgeFee) {
            this.surgeFee = surgeFee;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }
    }
}
