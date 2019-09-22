package com.ynk.marketim.Presenter;

public interface ILoginPresenter {
    /*
    * Login Method
    * @param
    *   userName: kullanıcı adı bilgisi
    *   password: şifre bilgisi
    *   autoLogin: Otomatik giriş(Auto Login) yapılıp yapılmadığı bilgisini içerir.
    */
    void login(String userName, String password, boolean autoLogin);
}
