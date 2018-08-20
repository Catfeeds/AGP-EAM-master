package com.hsk.hxqh.agp_eam.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnEditClickL;
import com.flyco.dialog.widget.NormalEditTextDialog;
import com.hsk.hxqh.agp_eam.R;
import com.hsk.hxqh.agp_eam.api.HttpManager;
import com.hsk.hxqh.agp_eam.api.HttpRequestHandler;
import com.hsk.hxqh.agp_eam.config.Constants;
import com.hsk.hxqh.agp_eam.dialog.FlippingLoadingDialog;
import com.hsk.hxqh.agp_eam.manager.AppManager;
import com.hsk.hxqh.agp_eam.unit.AccountUtils;
import com.hsk.hxqh.agp_eam.unit.MessageUtils;
import com.hsk.hxqh.agp_eam.unit.Store;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;


/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {


    private static final String TAG = "LoginActivity";
    private EditText mUsername;
    private EditText mPassword;
    private Button mLogin;
    private CheckBox checkBox; //记住密码
    private TextView versionName;//版本号
    private LinearLayout langlange_setting;

    private boolean isRemember; //是否记住密码


    String userName; //用户名

    String userPassWorld; //密码

    String imei; //imei

    protected FlippingLoadingDialog mLoadingDialog;

    String[] cities;

    String[] locals;
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
                .getDeviceId();

        findViewById();
        initView();
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    @Override
    protected void findViewById() {
        mUsername = (EditText) findViewById(R.id.user_login_id);
        mPassword = (EditText) findViewById(R.id.user_login_password);
        checkBox = (CheckBox) findViewById(R.id.isremenber_password);
        mLogin = (Button) findViewById(R.id.user_login);
        versionName = (TextView) findViewById(R.id.versionName);
        langlange_setting = (LinearLayout)findViewById(R.id.langlange_setting);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        changeAppLanguage();
        boolean isChecked = AccountUtils.getIsChecked(LoginActivity.this);
        if (isChecked) {
            mUsername.setText(AccountUtils.getUserName(LoginActivity.this));
            mPassword.setText(AccountUtils.getUserPassword(LoginActivity.this));
        }
        if (AccountUtils.getIpAddress(LoginActivity.this) == null || AccountUtils.getIpAddress(LoginActivity.this).equals("")) {
            AccountUtils.setIpAddress(LoginActivity.this, Constants.HTTP_API_IP);
        }
        checkBox.setOnCheckedChangeListener(cheBoxOnCheckedChangListener);
        langlange_setting.setOnClickListener(langlange_settingOnClickListener);
        mLogin.setOnClickListener(this);

        versionName.setText(getVersion());
        versionName.setOnClickListener(ipOnClickListener);

//        if (!mUsername.getText().toString().equals("") && !mPassword.getText().toString().equals("")) {
//            mLogin.performClick();
//        }
    }
    private View.OnClickListener ipOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setIP();
        }
    };
    private void setIP() {
        final NormalEditTextDialog editTextDialog = new NormalEditTextDialog(LoginActivity.this);
        editTextDialog.title("Configure IP")
                .content(AccountUtils.getIpAddress(LoginActivity.this))
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();
        editTextDialog.setOnBtnClickL(
                new OnBtnEditClickL() {
                    @Override
                    public void onBtnClick(String text) {
                        editTextDialog.dismiss();
                    }
                }, new OnBtnEditClickL() {
                    @Override
                    public void onBtnClick(String text) {
                        AccountUtils.setIpAddress(LoginActivity.this, text);
                        editTextDialog.dismiss();
                    }
                }
        );
    }

    private View.OnClickListener langlange_settingOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String[] cities = {getString(R.string.lan_chinese), getString(R.string.lan_en), getString(R.string.lan_ru)};
            final String[] locals = {"zh_CN", "en", "ru"};
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(LoginActivity.this);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle(R.string.select_language);
            builder.setItems(cities, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    Store.setLanguageLocal(LoginActivity.this, locals[which]);
                    EventBus.getDefault().post("EVENT_REFRESH_LANGUAGE");
                }
            });
            builder.show();
        }
    };

    private CompoundButton.OnCheckedChangeListener cheBoxOnCheckedChangListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            isRemember = isChecked;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_login:
                if (mUsername.getText().length() == 0) {
                    mUsername.setError(getString(R.string.login_error_empty_user));
                    mUsername.requestFocus();
                } else if (mPassword.getText().length() == 0) {
                    mPassword.setError(getString(R.string.login_error_empty_passwd));
                    mPassword.requestFocus();
                } else {
                    login();
                }
                break;
        }
    }


    /**
     * 登陆*
     */
    private void login() {

        getLoadingDialog(getString(R.string.login_loging)).show();

        HttpManager.loginWithUsername(LoginActivity.this,
                mUsername.getText().toString(),
                mPassword.getText().toString(), imei,
                new HttpRequestHandler<String>() {
                    @Override
                    public void onSuccess(String data) {

                        MessageUtils.showMiddleToast(LoginActivity.this, getString(R.string.login_successful_hint));
                        mLoadingDialog.dismiss();
//                        AccountUtils.setIpAddress(LoginActivity.this,adress);
                        if (isRemember) {
                            AccountUtils.setChecked(LoginActivity.this, true);
                            //记住密码
                            AccountUtils.setUserNameAndPassWord(LoginActivity.this, mUsername.getText().toString(), mPassword.getText().toString());
                        }
                        try {//保存登录返回信息
                            JSONObject object = new JSONObject(data);
                            JSONObject LoginDetails = object.getJSONObject("userLoginDetails");
                            AccountUtils.setLoginDetails(LoginActivity.this, LoginDetails.getString("insertOrg"), LoginDetails.getString("insertSite"),
                                    LoginDetails.getString("personId"), object.getString("userName"), LoginDetails.getString("displayName"));
//                            findByDepartment(LoginDetails.getString("personId"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AccountUtils.setIsOffLine(LoginActivity.this, false);
                        startIntent();

                    }

                    @Override
                    public void onSuccess(String data, int totalPages, int currentPage) {
                        MessageUtils.showMiddleToast(LoginActivity.this, getString(R.string.login_successful_hint));
                        AccountUtils.setIsOffLine(LoginActivity.this, false);
                        startIntent();
                    }

                    @Override
                    public void onFailure(String error) {
                        MessageUtils.showErrorMessage(LoginActivity.this, error);
                        mLoadingDialog.dismiss();
                    }
                });
    }


    private void startIntent() {
        Intent inetnt = new Intent();
        inetnt.setClass(this, MainActivity.class);
        startActivityForResult(inetnt, 0);
    }

    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return this.getString(R.string.can_not_find_version_name);
        }
    }

    private FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }


    private long exitTime = 0;

    @Override
    public void onBackPressed() {


        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, getResources().getString(R.string.exit_text), Toast.LENGTH_LONG).show();
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.AppExit(LoginActivity.this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String str) {
        switch (str) {
            case "EVENT_REFRESH_LANGUAGE":
                changeAppLanguage();
                recreate();//刷新界面
                break;
        }
    }


    public void changeAppLanguage() {
        String sta = Store.getLanguageLocal(this);
        if(sta != null && !"".equals(sta)){
            // 本地语言设置
            Locale myLocale = new Locale(sta);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
