package com.sjsu.taas.rest;

import java.io.Console;

import com.android.prefs.AndroidLocation.AndroidLocationException;
import com.android.sdklib.SdkManager;
import com.android.sdklib.internal.avd.AvdInfo;
import com.android.sdklib.internal.avd.AvdManager;
import com.android.utils.StdLogger;

public class Test {

	private static SdkManager sdkManager;
	private static AvdManager avdManager;

	public static void main(String... args) throws AndroidLocationException{
		sdkManager = SdkManager.createManager(System.getenv("ANDROID_HOME"), new StdLogger(StdLogger.Level.VERBOSE));
        avdManager = AvdManager.getInstance(sdkManager.getLocalSdk(), new StdLogger(StdLogger.Level.VERBOSE));
        AvdInfo[] infoList = avdManager.getAllAvds();
        for(AvdInfo f : infoList){
        	System.out.println(f.getTag());
        }
	}
}
