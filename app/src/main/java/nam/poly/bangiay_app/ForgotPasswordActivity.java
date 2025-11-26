package nam.poly.bangiay_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import nam.poly.bangiay_app.network.ApiClient;
import nam.poly.bangiay_app.network.ApiService;
import nam.poly.bangiay_app.network.NetworkUtils;
import nam.poly.bangiay_app.network.model.BaseResponse;
import nam.poly.bangiay_app.network.request.ForgotPasswordRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText edtPhoneEmail;
    private Button btnSendCode;
    private View btnBack;
    private ApiService apiService;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        apiService = ApiClient.getApiService();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang xử lý...");
        progressDialog.setCancelable(false);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        edtPhoneEmail = findViewById(R.id.edtPhoneEmail);
        btnSendCode = findViewById(R.id.btnSendCode);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupClickListeners() {
        // Back button
        btnBack.setOnClickListener(v -> finish());

        // Send code button
        btnSendCode.setOnClickListener(v -> handleForgotPassword());
    }

    private void handleForgotPassword() {
        String email = edtPhoneEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            edtPhoneEmail.setError("Vui lòng nhập Email");
            edtPhoneEmail.requestFocus();
            return;
        }

        setLoading(true);
        ForgotPasswordRequest request = new ForgotPasswordRequest(email);
        apiService.forgotPassword(request).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                setLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    BaseResponse data = response.body();
                    Toast.makeText(ForgotPasswordActivity.this,
                            data.getMessage() != null ? data.getMessage() : "Vui lòng kiểm tra email của bạn",
                            Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    String message = NetworkUtils.getErrorMessage(response);
                    Toast.makeText(ForgotPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                setLoading(false);
                Toast.makeText(ForgotPasswordActivity.this,
                        "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLoading(boolean isLoading) {
        if (isLoading) {
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        } else if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}

