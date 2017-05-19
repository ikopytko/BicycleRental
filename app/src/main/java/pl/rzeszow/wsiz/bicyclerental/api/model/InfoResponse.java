package pl.rzeszow.wsiz.bicyclerental.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Roman Savka on
 * 04-Apr-15.
 */
public class InfoResponse {

    @SerializedName("Code")
    private int code;
    @SerializedName("Message")
    private String message;
    @SerializedName("Value")
    private int value;

    public boolean isOk(){
        return code == 200;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getValue() {
        return value;
    }
}
