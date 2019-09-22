package com.ynk.marketim.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.ynk.marketim.Presenter.ILoginPresenter;
import com.ynk.marketim.Presenter.LoginPresenter;
import com.ynk.marketim.R;
import com.ynk.marketim.Util.Utils;
import com.ynk.marketim.View.ILoginView;

import muyan.snacktoa.SnackToa;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    //Components
    private EditText etUserName, etPassword;
    private CheckBox cbRememberMe;

    //Presenter
    private ILoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init Variable
        presenter = new LoginPresenter(this, this);

        //AutoLogin kontrolü
        SharedPreferences preferences = getSharedPreferences(Utils.APP_NAME, MODE_PRIVATE);
        if (preferences.getBoolean(getString(R.string.loginControlkey), false)) {
            openOrderPage();
        }

        //InitComponents
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Kullanıcı Adı ve Şifre Alanı Null kontrolü
                if (!TextUtils.isEmpty(etUserName.getText().toString()) && !TextUtils.isEmpty(etPassword.getText().toString()))
                    presenter.login(etUserName.getText().toString(), etPassword.getText().toString(), cbRememberMe.isChecked());
                else if (TextUtils.isEmpty(etUserName.getText().toString()))
                    etUserName.setError(getString(R.string.loginPageUserNameError)); //Kullanıcı Adı alanı boş olması durumunda gösterilecek error mesajı
                if (TextUtils.isEmpty(etPassword.getText().toString()))
                    etPassword.setError(getString(R.string.loginPagePasswordError));        //Şifre alanı boş olması durumunda gösterilecek error mesajı
            }
        });
    }

    /*******************
     Order Page Intent
     Sayfa Geçiş Metodu
     *******************/
    private void openOrderPage() {
        Intent orderIntent = new Intent(LoginActivity.this, OrderActivity.class);
        startActivity(orderIntent);
        finish();
    }

    @Override
    public void loginResult(boolean result) {
        if (result) openOrderPage();
        else SnackToa.snackBarError(this, getString(R.string.loginPageLoginError));
    }
}
