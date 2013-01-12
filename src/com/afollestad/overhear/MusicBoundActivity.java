package com.afollestad.overhear;

import com.afollestad.overhear.MusicService.MusicActivityCallback;
import com.afollestad.overhear.MusicService.MusicBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

public abstract class MusicBoundActivity extends Activity implements MusicActivityCallback {

	MusicService mService;
	boolean mBound;
	
	public MusicService getMusicService() {
		return mService;
	}
	
	public boolean isServiceBound() {
		return mBound;
	}
	
	public abstract void onBound();
	
	public void onServiceUpdate() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = new Intent(this, MusicService.class);
		startService(intent);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		unbindService(mConnection);
	}
	
	private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
        	MusicBinder binder = (MusicBinder)service;
            mService = binder.getService();
            mBound = true;
            onBound();
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}