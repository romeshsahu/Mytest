package au.com.capitalradiology.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mehul on 3/9/2016.
 */
public class BinForRecords
{
    @SerializedName("Result")
    @Expose
    private ArrayList<Result> Result = new ArrayList<>();

    /**
     *
     * @return
     * The Result
     */
    public ArrayList<Result> getResult() {
        return Result;
    }

    /**
     *
     * @param Result
     * The Result
     */
    public void setResult(ArrayList<Result> Result) {
        this.Result = Result;
    }


public class Result {

    @SerializedName("record_type")
    @Expose
    private String recordType;
    @SerializedName("patient_id")
    @Expose
    private String patientId;
    @SerializedName("start_datetime")
    @Expose
    private String startDatetime;
    @SerializedName("end_datetime")
    @Expose
    private String endDatetime;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("mrn")
    @Expose
    private String mrn;
    @SerializedName("acc")
    @Expose
    private String acc;
    @SerializedName("suid")
    @Expose
    private Object suid;
    @SerializedName("attachment_url")
    @Expose
    private Object attachmentUrl;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("description")
    @Expose
    private String description;

    /**
     *
     * @return
     * The recordType
     */
    public String getRecordType() {
        return recordType;
    }

    /**
     *
     * @param recordType
     * The record_type
     */
    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    /**
     *
     * @return
     * The patientId
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     *
     * @param patientId
     * The patient_id
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     *
     * @return
     * The startDatetime
     */
    public String getStartDatetime() {
        return startDatetime;
    }

    /**
     *
     * @param startDatetime
     * The start_datetime
     */
    public void setStartDatetime(String startDatetime) {
        this.startDatetime = startDatetime;
    }

    /**
     *
     * @return
     * The endDatetime
     */
    public String getEndDatetime() {
        return endDatetime;
    }

    /**
     *
     * @param endDatetime
     * The end_datetime
     */
    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
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
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The mrn
     */
    public String getMrn() {
        return mrn;
    }

    /**
     *
     * @param mrn
     * The mrn
     */
    public void setMrn(String mrn) {
        this.mrn = mrn;
    }

    /**
     *
     * @return
     * The acc
     */
    public String getAcc() {
        return acc;
    }

    /**
     *
     * @param acc
     * The acc
     */
    public void setAcc(String acc) {
        this.acc = acc;
    }

    /**
     *
     * @return
     * The suid
     */
    public Object getSuid() {
        return suid;
    }

    /**
     *
     * @param suid
     * The suid
     */
    public void setSuid(Object suid) {
        this.suid = suid;
    }

    /**
     *
     * @return
     * The attachmentUrl
     */
    public Object getAttachmentUrl() {
        return attachmentUrl;
    }

    /**
     *
     * @param attachmentUrl
     * The attachment_url
     */
    public void setAttachmentUrl(Object attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    /**
     *
     * @return
     * The imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     *
     * @param imageUrl
     * The image_url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
}
