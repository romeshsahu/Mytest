package au.com.capitalradiology.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mehul on 2/26/2016.
 */
public class BinForChooseLocation {
    @SerializedName("Record")
    @Expose
    private ArrayList<Record> Record = new ArrayList<Record>();

    /**
     * @return The Record
     */
    public ArrayList<Record> getRecord() {
        return Record;
    }

    /**
     * @param Record The Record
     */
    public void setRecord(ArrayList<Record> Record) {
        this.Record = Record;
    }

    public class Record {

        @SerializedName("location_id")
        @Expose
        private String locationId;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("location_name")
        @Expose
        private String location_name;

        @SerializedName("distance")
        @Expose
        private String distance;

        /**
         * @return The locationId
         */
        public String getLocationId() {
            return locationId;
        }

        /**
         * @param locationId The location_id
         */
        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }

        /**
         * @return The latitude
         */
        public String getLatitude() {
            return latitude;
        }

        /**
         * @param latitude The latitude
         */
        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        /**
         * @return The longitude
         */
        public String getLongitude() {
            return longitude;
        }

        /**
         * @param longitude The longitude
         */
        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        /**
         * @return The distance
         */
        public String getDistance() {
            return distance;
        }

        /**
         * @param distance The distance
         */
        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getLocation_name() {
            return location_name;
        }

        public void setLocation_name(String location_name) {
            this.location_name = location_name;
        }
    }
}
