package nam.poly.bangiay_app.network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class NetworkUtils {

    private NetworkUtils() {
    }

    public static String getErrorMessage(Response<?> response) {
        if (response == null) {
            return "Không nhận được phản hồi từ máy chủ";
        }

        ResponseBody errorBody = response.errorBody();
        if (errorBody != null) {
            try {
                String raw = errorBody.string();
                JSONObject json = new JSONObject(raw);
                if (json.has("message")) {
                    return json.getString("message");
                }
                if (json.has("error")) {
                    return json.getString("error");
                }
            } catch (IOException | JSONException e) {
                return "Lỗi: " + e.getMessage();
            }
        }
        return "Có lỗi xảy ra (" + response.code() + ")";
    }
}



