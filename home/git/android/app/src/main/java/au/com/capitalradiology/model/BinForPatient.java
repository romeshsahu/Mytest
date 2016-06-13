package au.com.capitalradiology.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mehul on 2/25/2016.
 */
public class BinForPatient {

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
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("medicare_number")
        @Expose
        private Object medicareNumber;
        @SerializedName("medicare_number_position")
        @Expose
        private Object medicareNumberPosition;
        @SerializedName("medicare_number_valid_date")
        @Expose
        private Object medicareNumberValidDate;
        @SerializedName("medicare_image_url")
        @Expose
        private String medicareImageUrl;
        @SerializedName("profile_image_url")
        @Expose
        private String profileImageUrl;
        @SerializedName("mobile_number")
        @Expose
        private String mobileNumber;
        @SerializedName("timezone")
        @Expose
        private Object timezone;
        @SerializedName("agree_with_tos")
        @Expose
        private Object agreeWithTos;
        @SerializedName("agree_with_tos_date")
        @Expose
        private Object agreeWithTosDate;
        @SerializedName("mobile_os_id")
        @Expose
        private Integer mobileOsId;
        @SerializedName("device_token")
        @Expose
        private String deviceToken;
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
        public Integer getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(Integer id) {
            this.id = id;
        }

        /**
         * @return The userId
         */
        public String getUserId() {
            return userId;
        }

        /**
         * @param userId The user_id
         */
        public void setUserId(String userId) {
            this.userId = userId;
        }

        /**
         * @return The firstName
         */
        public String getFirstName() {
            return firstName;
        }

        /**
         * @param firstName The first_name
         */
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        /**
         * @return The lastName
         */
        public String getLastName() {
            return lastName;
        }

        /**
         * @param lastName The last_name
         */
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        /**
         * @return The dob
         */
        public String getDob() {
            return dob;
        }

        /**
         * @param dob The dob
         */
        public void setDob(String dob) {
            this.dob = dob;
        }

        /**
         * @return The gender
         */
        public String getGender() {
            return gender;
        }

        /**
         * @param gender The gender
         */
        public void setGender(String gender) {
            this.gender = gender;
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
         * @return The medicareNumber
         */
        public Object getMedicareNumber() {
            return medicareNumber;
        }

        /**
         * @param medicareNumber The medicare_number
         */
        public void setMedicareNumber(Object medicareNumber) {
            this.medicareNumber = medicareNumber;
        }

        /**
         * @return The medicareNumberPosition
         */
        public Object getMedicareNumberPosition() {
            return medicareNumberPosition;
        }

        /**
         * @param medicareNumberPosition The medicare_number_position
         */
        public void setMedicareNumberPosition(Object medicareNumberPosition) {
            this.medicareNumberPosition = medicareNumberPosition;
        }

        /**
         * @return The medicareNumberValidDate
         */
        public Object getMedicareNumberValidDate() {
            return medicareNumberValidDate;
        }

        /**
         * @param medicareNumberValidDate The medicare_number_valid_date
         */
        public void setMedicareNumberValidDate(Object medicareNumberValidDate) {
            this.medicareNumberValidDate = medicareNumberValidDate;
        }

        /**
         * @return The medicareImageUrl
         */
        public String getMedicareImageUrl() {
            return medicareImageUrl;
        }

        /**
         * @param medicareImageUrl The medicare_image_url
         */
        public void setMedicareImageUrl(String medicareImageUrl) {
            this.medicareImageUrl = medicareImageUrl;
        }

        /**
         * @return The profileImageUrl
         */
        public String getProfileImageUrl() {
            return profileImageUrl;
        }

        /**
         * @param profileImageUrl The profile_image_url
         */
        public void setProfileImageUrl(String profileImageUrl) {
            this.profileImageUrl = profileImageUrl;
        }

        /**
         * @return The mobileNumber
         */
        public String getMobileNumber() {
            return mobileNumber;
        }

        /**
         * @param mobileNumber The mobile_number
         */
        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        /**
         * @return The timezone
         */
        public Object getTimezone() {
            return timezone;
        }

        /**
         * @param timezone The timezone
         */
        public void setTimezone(Object timezone) {
            this.timezone = timezone;
        }

        /**
         * @return The agreeWithTos
         */
        public Object getAgreeWithTos() {
            return agreeWithTos;
        }

        /**
         * @param agreeWithTos The agree_with_tos
         */
        public void setAgreeWithTos(Object agreeWithTos) {
            this.agreeWithTos = agreeWithTos;
        }

        /**
         * @return The agreeWithTosDate
         */
        public Object getAgreeWithTosDate() {
            return agreeWithTosDate;
        }

        /**
         * @param agreeWithTosDate The agree_with_tos_date
         */
        public void setAgreeWithTosDate(Object agreeWithTosDate) {
            this.agreeWithTosDate = agreeWithTosDate;
        }

        /**
         * @return The mobileOsId
         */
        public Integer getMobileOsId() {
            return mobileOsId;
        }

        /**
         * @param mobileOsId The mobile_os_id
         */
        public void setMobileOsId(Integer mobileOsId) {
            this.mobileOsId = mobileOsId;
        }

        /**
         * @return The deviceToken
         */
        public String getDeviceToken() {
            return deviceToken;
        }

        /**
         * @param deviceToken The device_token
         */
        public void setDeviceToken(String deviceToken) {
            this.deviceToken = deviceToken;
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
