package com.dundies.pao.mobiledatausbtetheringmonitor.service;

import android.content.Context;

import com.dundies.pao.mobiledatausbtetheringmonitor.ConnectionInfoBean;

/**
 * Created by Pao on 12/8/2018.
 */

public interface NetworkStatusService {
    boolean isConnectedToMobileData(Context context);

    ConnectionInfoBean getInfo(Context context);

    String just(Context context);
}
