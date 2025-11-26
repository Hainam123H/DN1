package nam.poly.bangiay_app.network.model;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private String error;

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }
}



