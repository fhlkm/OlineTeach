package com.china.online.teach.com.china.online.teach.wechat;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hanlu.feng on 9/14/2017.
 */

public class WebSdkErrorException extends Exception implements Parcelable {


    public WebSdkErrorException(String msg) {
        super(msg);
    }

    protected WebSdkErrorException(Parcel in) {
    }

    public static final Creator<WebSdkErrorException> CREATOR = new Creator<WebSdkErrorException>() {
        @Override
        public WebSdkErrorException createFromParcel(Parcel in) {
            return new WebSdkErrorException(in);
        }

        @Override
        public WebSdkErrorException[] newArray(int size) {
            return new WebSdkErrorException[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
