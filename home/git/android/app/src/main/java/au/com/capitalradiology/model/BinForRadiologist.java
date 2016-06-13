package au.com.capitalradiology.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BinForRadiologist {
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

        @SerializedName("Id")
        @Expose
        private Integer Id;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("education")
        @Expose
        private String education;
        @SerializedName("speciality")
        @Expose
        private String speciality;
        @SerializedName("special_interest")
        @Expose
        private String specialInterest;
        @SerializedName("profile_image_url")
        @Expose
        private String profileImageUrl;

        /**
         * @return The Id
         */
        public Integer getId() {
            return Id;
        }

        /**
         * @param Id The Id
         */
        public void setId(Integer Id) {
            this.Id = Id;
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
         * @return The description
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description The description
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * @return The specialInterest
         */
        public String getSpecialInterest() {
            return specialInterest;
        }

        /**
         * @param specialInterest The special_interest
         */
        public void setSpecialInterest(String specialInterest) {
            this.specialInterest = specialInterest;
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

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getSpeciality() {
            return speciality;
        }

        public void setSpeciality(String speciality) {
            this.speciality = speciality;
        }
    }

}