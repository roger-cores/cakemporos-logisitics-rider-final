package in.cakemporos.logistics.cakemporoslogistics.web.webmodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by roger on 10/8/16.
 */
public class LocationResponse {

    private Integer code;

    private String message;

    @SerializedName("_id")
    private String id;

    @SerializedName("__v")
    private String version;

    private Boolean close;

    public Boolean getClose() {
        return close;
    }

    public void setClose(Boolean close) {
        this.close = close;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
