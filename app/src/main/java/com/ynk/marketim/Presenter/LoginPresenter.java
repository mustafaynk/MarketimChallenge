package com.ynk.marketim.Presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.ynk.marketim.R;
import com.ynk.marketim.Util.Utils;
import com.ynk.marketim.View.ILoginView;

import static android.content.Context.MODE_PRIVATE;

public class LoginPresenter implements ILoginPresenter {

    private ILoginView loginView;
    private Context context;

    //Login Authentication
    private SharedPreferences preferences;

    public LoginPresenter(Context context, ILoginView loginView) {
        preferences = context.getSharedPreferences(Utils.APP_NAME, MODE_PRIVATE);
        this.context = context;
        this.loginView = loginView;
    }

    @Override
    public void login(String userName, String password, boolean autoLogin) {
        if (userName.equals(context.getString(R.string.loginUserName)) && password.equals(context.getString(R.string.loginPassword))) {
            if (autoLogin)
                saveLogin();
            loginView.loginResult(true);
        } else
            loginView.loginResult(false);
    }

    /*
     * Auto Login kaydeden method
     * */
    private void saveLogin() {
        SharedPreferences.Editor editor = preferences.edit();
        // giriş bilgileri doğru ise login değerini true yapıyoruz.
        editor.putBoolean(context.getString(R.string.loginControlkey), true);
        // değişiklikleri commit ediyoruz.
        editor.apply();
    }
}
