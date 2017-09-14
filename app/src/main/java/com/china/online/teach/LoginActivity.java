package com.china.online.teach;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.china.online.teach.com.china.online.teach.wechat.WeChatApi;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class LoginActivity extends Activity  implements IWXAPIEventHandler{

    Button btn_wechat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btn_wechat = findViewById(R.id.wechat_login);
        btn_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mIntent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(mIntent);
            }
        });


    }

    private void loginWechat(){
        WeChatApi mApi = WeChatApi.getInstance();
        mApi.initWechatInterface(this);
        mApi.loginByWechat();
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        SendAuth.Resp resp = (SendAuth.Resp) baseResp;

        String code = ((SendAuth.Resp) baseResp).code;

        }
}

