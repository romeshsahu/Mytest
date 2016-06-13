package au.com.capitalradiology.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mehul on 3/2/2016.
 */
public class BinForPromoCode {

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
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("discount_percent")
        @Expose
        private Double discountPercent;
        @SerializedName("discount_value")
        @Expose
        private Double discountValue;

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
         * @return The code
         */
        public String getCode() {
            return code;
        }

        /**
         * @param code The code
         */
        public void setCode(String code) {
            this.code = code;
        }

        /**
         * @return The discountPercent
         */
        public Double getDiscountPercent() {
            return discountPercent;
        }

        /**
         * @param discountPercent The discount_percent
         */
        public void setDiscountPercent(Double discountPercent) {
            this.discountPercent = discountPercent;
        }

        /**
         * @return The discountValue
         */
        public Double getDiscountValue() {
            return discountValue;
        }

        /**
         * @param discountValue The discount_value
         */
        public void setDiscountValue(Double discountValue) {
            this.discountValue = discountValue;
        }
    }
}
