package com.china.online.teach.com.china.online.teach.wechat;

import android.content.Context;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by hanlu.feng on 9/14/2017.
 */

public class WeChatApi {
    private static final String APP_ID="";
    private IWXAPI iwxApi;
    private static WeChatApi mWeCahtApi = null;

    public static WeChatApi getInstance(){
        if(null == mWeCahtApi){
            mWeCahtApi = new WeChatApi();
        }
        return mWeCahtApi;
    }

    public void initWechatInterface(Context mContext){
        iwxApi = WXAPIFactory.createWXAPI(mContext,APP_ID,true);
        iwxApi.registerApp(APP_ID);
    }

    public void loginByWechat(){
        // send oauth request
         SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "online_teach";
        iwxApi.sendReq(req);
    }
}
