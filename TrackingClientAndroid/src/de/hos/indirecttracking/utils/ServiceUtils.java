package de.hos.indirecttracking.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class ServiceUtils {
	
	public static boolean isServicRunning(Context context, Class<?> serviceClazz){
		ActivityManager aManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for(RunningServiceInfo service : aManager.getRunningServices(Integer.MAX_VALUE)){
			if(serviceClazz.getName().equals(service.service.getClassName())){
				return true;
			}
		}
		return false;
	}

}
