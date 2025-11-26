package nam.poly.bangiay_app.network;

import nam.poly.bangiay_app.network.model.AuthResponse;
import nam.poly.bangiay_app.network.model.BaseResponse;
import nam.poly.bangiay_app.network.request.ForgotPasswordRequest;
import nam.poly.bangiay_app.network.request.LoginRequest;
import nam.poly.bangiay_app.network.request.RegisterRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("auth/login")
    Call<AuthResponse> login(@Body LoginRequest request);

    @POST("auth/register")
    Call<AuthResponse> register(@Body RegisterRequest request);

    @POST("auth/forgot-password")
    Call<BaseResponse> forgotPassword(@Body ForgotPasswordRequest request);
}



