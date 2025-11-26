package nam.poly.bangiay_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private TextView tvUserName, tvUserEmail;
    private LinearLayout layoutLogout;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        sessionManager = new SessionManager(this);
        initViews();
        bindUserInfo();
        setupClicks();
    }

    private void initViews() {
        tvUserName = findViewById(R.id.tvUserName);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        layoutLogout = findViewById(R.id.layoutLogout);
        btnBack = findViewById(R.id.btnBack);
    }

    private void bindUserInfo() {
        String name = sessionManager.getUserName();
        String email = sessionManager.getUserEmail();

        if (name == null || name.isEmpty()) {
            name = getString(R.string.default_user_name);
        }

        tvUserName.setText(name);
        tvUserEmail.setText(email != null ? email : "");
    }

    private void setupClicks() {
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> navigateBackToMain());
        }

        if (layoutLogout != null) {
            layoutLogout.setOnClickListener(v -> {
                sessionManager.clearSession();
                Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }
    }

    @Override
    public void onBackPressed() {
        navigateBackToMain();
    }

    private void navigateBackToMain() {
        Intent intent = new Intent(AccountActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}



