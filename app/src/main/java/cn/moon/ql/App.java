package cn.moon.ql;

import android.app.Application;
import android.content.SharedPreferences;

import cn.moon.ql.data.model.QLLoginData;
import cn.moon.ql.data.model.QLSettingsData;
import cn.moon.ql.data.model.QLStoreData;

public class App extends Application {
    private static App instance;
    private static SharedPreferences sharedPreferences;
    private static final String QL_DATA_FILE_NAME = "qinglong";
    private static QLStoreData qlStoreData;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initApp();
    }

    public static App getInstance() {
        return instance;
    }

    public static QLStoreData getQLStoreData() {
        return qlStoreData;
    }


    private void initApp() {
        sharedPreferences = getSharedPreferences(QL_DATA_FILE_NAME, MODE_PRIVATE);
        String url = sharedPreferences.getString("url", "http://ql.free.idcfengye.com");
        String cid = sharedPreferences.getString("cid", "f7kMA-5m_vmW");
        String csk = sharedPreferences.getString("csk", "sPO3x8Vg3i0Y240TmMzrAu-H");

        String tokenType = sharedPreferences.getString("token_type", "Bearer");
        String token = sharedPreferences.getString("token", "");

        QLLoginData loginData = new QLLoginData(tokenType, token);
        QLSettingsData settingsData = new QLSettingsData(url, cid, csk);
        qlStoreData = new QLStoreData(loginData, settingsData);
    }



    public static void storeQLData(QLSettingsData settingsData, QLLoginData loginData) {
        qlStoreData = new QLStoreData(loginData, settingsData);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("url", settingsData.getUrl());
        editor.putString("cid", settingsData.getCid());
        editor.putString("csk", settingsData.getCsk());
        editor.putString("token_type", loginData.getTokenType());
        editor.putString("token", loginData.getToken());
        editor.apply();
    }
}
