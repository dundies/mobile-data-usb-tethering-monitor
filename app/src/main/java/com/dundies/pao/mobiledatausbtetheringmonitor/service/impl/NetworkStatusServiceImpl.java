package com.dundies.pao.mobiledatausbtetheringmonitor.service.impl;

import android.app.ActivityManager;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.RemoteException;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.dundies.pao.mobiledatausbtetheringmonitor.ConnectionInfoBean;
import com.dundies.pao.mobiledatausbtetheringmonitor.service.NetworkStatusService;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class NetworkStatusServiceImpl implements NetworkStatusService {

    @Override
    public boolean isConnectedToMobileData(Context context) {

        final ConnectivityManager connectivityManager = getConnectivityManager(context);

        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;

    }

    @Override
    public ConnectionInfoBean getInfo(Context context) {

        final ConnectivityManager connectivityManager = getConnectivityManager(context);

        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo == null) {
            return ConnectionInfoBean.createNotConnectedInfo();
        }

        return new ConnectionInfoBean(activeNetworkInfo.getExtraInfo(), activeNetworkInfo.getReason(), "d");

    }

    @Override
    public String just(Context context) {

        final ConnectivityManager connectivityManager = getConnectivityManager(context);

        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        final NetworkStatsManager networkStatsManager = (NetworkStatsManager) context.getSystemService(Context.NETWORK_STATS_SERVICE);

        NetworkStats networkStats =null;
        try {
            networkStats = networkStatsManager.queryDetailsForUid(ConnectivityManager.TYPE_MOBILE,
                   getSubscriberId(context, ConnectivityManager.TYPE_MOBILE), 0, System.currentTimeMillis(),
                    NetworkStats.Bucket.UID_TETHERING);
        } catch (RemoteException e) {
           return "error";
        }

        long txBytes = 0L;
        NetworkStats.Bucket bucket = new NetworkStats.Bucket();
        while (networkStats.hasNextBucket()) {
            networkStats.getNextBucket(bucket);
            txBytes += bucket.getRxBytes();
        }
        networkStats.close();
        return "data " + txBytes;

    }

    private ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private String getSubscriberId(Context context, int networkType) {
        if (ConnectivityManager.TYPE_MOBILE == networkType) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getSubscriberId();
        }
        return "";
    }



}
