package au.com.capitalradiology.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mehul on 2/17/2016.
 */
public class BinForPain_Mgmt {
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
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("image_url")
        @Expose
        private String imageUrl;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("category_id")
        @Expose
        private Integer categoryId;

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
         * @return The name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name The name
         */
        public void setName(String name) {
            this.name = name;
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
         * @return The imageUrl
         */
        public String getImageUrl() {
            return imageUrl;
        }

        /**
         * @param imageUrl The image_url
         */
        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        /**
         * @return The content
         */
        public String getContent() {
            return content;
        }

        /**
         * @param content The content
         */
        public void setContent(String content) {
            this.content = content;
        }

        /**
         * @return The categoryId
         */
        public Integer getCategoryId() {
            return categoryId;
        }

        /**
         * @param categoryId The category_id
         */
        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

    }
}
