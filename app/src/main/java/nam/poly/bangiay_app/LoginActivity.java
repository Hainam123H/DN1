package nam.poly.bangiay_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText edtPhoneEmail, edtPassword;
    private Button btnLogin, btnRegister, btnForgotPassword;
    private View btnBack;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
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
            edtPhoneEmail.setError("Vui lòng nhập SDT/Email");
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

        // TODO: Implement actual login logic here (API call, database check, etc.)
        // For now, just show a success message
        Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

        sessionManager.setLoggedIn(true);
        
        // Navigate to MainActivity after successful login
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

