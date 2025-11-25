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

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText edtPhoneEmail, edtVerificationCode, edtNewPassword, edtConfirmNewPassword;
    private Button btnSendCode, btnResetPassword;
    private View btnBack;
    private boolean isCodeSent = false;
    private boolean isCodeVerified = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        edtPhoneEmail = findViewById(R.id.edtPhoneEmail);
        edtVerificationCode = findViewById(R.id.edtVerificationCode);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmNewPassword = findViewById(R.id.edtConfirmNewPassword);
        btnSendCode = findViewById(R.id.btnSendCode);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupClickListeners() {
        // Back button
        btnBack.setOnClickListener(v -> finish());

        // Send code button
        btnSendCode.setOnClickListener(v -> handleSendCode());

        // Reset password button
        btnResetPassword.setOnClickListener(v -> handleResetPassword());
    }

    private void handleSendCode() {
        String phoneEmail = edtPhoneEmail.getText().toString().trim();

        if (TextUtils.isEmpty(phoneEmail)) {
            edtPhoneEmail.setError("Vui lòng nhập SDT/Email");
            edtPhoneEmail.requestFocus();
            return;
        }

        // TODO: Implement actual code sending logic here (API call, SMS/Email service, etc.)
        // For now, just show a success message and reveal the verification code field
        Toast.makeText(this, "Mã xác nhận đã được gửi!", Toast.LENGTH_SHORT).show();
        
        isCodeSent = true;
        edtVerificationCode.setVisibility(View.VISIBLE);
        edtPhoneEmail.setEnabled(false);
        btnSendCode.setText("Gửi lại mã");
    }

    private void handleResetPassword() {
        if (!isCodeSent) {
            Toast.makeText(this, "Vui lòng gửi mã xác nhận trước", Toast.LENGTH_SHORT).show();
            return;
        }

        String verificationCode = edtVerificationCode.getText().toString().trim();
        String newPassword = edtNewPassword.getText().toString().trim();
        String confirmNewPassword = edtConfirmNewPassword.getText().toString().trim();

        // If code is not verified yet, verify it first
        if (!isCodeVerified) {
            if (TextUtils.isEmpty(verificationCode)) {
                edtVerificationCode.setError("Vui lòng nhập mã xác nhận");
                edtVerificationCode.requestFocus();
                return;
            }

            // TODO: Implement actual code verification logic here (API call, etc.)
            // For demo purposes, accept any 6-digit code
            if (verificationCode.length() != 6) {
                edtVerificationCode.setError("Mã xác nhận phải có 6 chữ số");
                edtVerificationCode.requestFocus();
                return;
            }

            // Code verified successfully
            isCodeVerified = true;
            Toast.makeText(this, "Mã xác nhận hợp lệ!", Toast.LENGTH_SHORT).show();
            
            // Show password fields and reset button
            edtNewPassword.setVisibility(View.VISIBLE);
            edtConfirmNewPassword.setVisibility(View.VISIBLE);
            btnResetPassword.setVisibility(View.VISIBLE);
            edtVerificationCode.setEnabled(false);
            return;
        }

        // Code is verified, now reset password
        if (TextUtils.isEmpty(newPassword)) {
            edtNewPassword.setError("Vui lòng nhập mật khẩu mới");
            edtNewPassword.requestFocus();
            return;
        }

        if (newPassword.length() < 6) {
            edtNewPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            edtNewPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirmNewPassword)) {
            edtConfirmNewPassword.setError("Vui lòng nhập lại mật khẩu mới");
            edtConfirmNewPassword.requestFocus();
            return;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            edtConfirmNewPassword.setError("Mật khẩu không khớp");
            edtConfirmNewPassword.requestFocus();
            return;
        }

        // TODO: Implement actual password reset logic here (API call, etc.)
        Toast.makeText(this, "Đặt lại mật khẩu thành công!", Toast.LENGTH_SHORT).show();
        
        // Navigate to LoginActivity after successful password reset
        Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

