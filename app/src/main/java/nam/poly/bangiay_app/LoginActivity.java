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
import nam.poly.bangiay_app.network.model.AuthResponse;
import nam.poly.bangiay_app.network.request.LoginRequest;
import nam.poly.bangiay_app.network.model.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtPhoneEmail, edtPassword;
    private Button btnLogin, btnRegister, btnForgotPassword;
    private View btnBack;
    private SessionManager sessionManager;
    private ApiService apiService;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        apiService = ApiClient.getApiService();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang xử lý...");
        progressDialog.setCancelable(false);
        initViews();
        setupClickListeners();
    }

    private void initViews() {
        edtPhoneEmail = findViewById(R.id.edtPhoneEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupClickListeners() {
        // Back button
        btnBack.setOnClickListener(v -> finish());

        // Login button
        btnLogin.setOnClickListener(v -> handleLogin());

        // Register button - navigate to RegisterActivity
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Forgot password button - navigate to ForgotPasswordActivity
        btnForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void handleLogin() {
        String phoneEmail = edtPhoneEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // Validation
        if (TextUtils.isEmpty(phoneEmail)) {
            edtPhoneEmail.setError("Vui lòng nhập Email");
            edtPhoneEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Vui lòng nhập mật khẩu");
            edtPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            edtPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            edtPassword.requestFocus();
            return;
        }

        performLogin(phoneEmail, password);
    }

    private void performLogin(String email, String password) {
        setLoading(true);
        LoginRequest request = new LoginRequest(email, password);
        apiService.login(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                setLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse data = response.body();
                    sessionManager.setLoggedIn(true);
                    UserResponse user = data.getUser();
                    if (user != null) {
                        String displayName = user.getFullName() != null && !user.getFullName().isEmpty()
                                ? user.getFullName()
                                : user.getUsername();
                        sessionManager.saveUserInfo(displayName, user.getEmail());
                    }
                    if (data.getToken() != null) {
                        sessionManager.saveToken(data.getToken());
                    }
                    Toast.makeText(LoginActivity.this,
                            data.getMessage() != null ? data.getMessage() : "Đăng nhập thành công!",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, AccountActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    String message = NetworkUtils.getErrorMessage(response);
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                setLoading(false);
                Toast.makeText(LoginActivity.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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

