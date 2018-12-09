package com.dundies.pao.mobiledatausbtetheringmonitor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.dundies.pao.mobiledatausbtetheringmonitor.service.NetworkStatusService;
import com.dundies.pao.mobiledatausbtetheringmonitor.service.impl.NetworkStatusServiceImpl;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private NetworkStatusService networkStatusService;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText("is connected to mobile? " + networkStatusService.isConnectedToMobileData(getApplicationContext()));
                    mTextMessage.setText("is connected to mobile? " + networkStatusService.just(getApplicationContext()));
                    return true;
                case R.id.navigation_dashboard:
                    final ConnectionInfoBean info = networkStatusService.getInfo(getApplicationContext());
                    mTextMessage.setText("is info to mobile? " + info.getProvider() + " - " + info.getDataDownloaded() );
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        networkStatusService = new NetworkStatusServiceImpl();
    }

}
