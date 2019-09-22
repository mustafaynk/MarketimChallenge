package com.ynk.marketim.View;

public interface ILoginView {
    /*
    * Login işlemi sonucunda elde edilen sonucu veren method
    * @param
    *   result: Login sorgusu sonucu giriş işleminin doğru yada yanlış olmasını içerir.
    * */
    void loginResult(boolean result);
}
