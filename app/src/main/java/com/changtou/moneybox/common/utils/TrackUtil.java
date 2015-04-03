
package com.changtou.moneybox.common.utils;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

public class TrackUtil {

    /*
     * 友盟和Flurry事件统计
     */
    public static void trackEvent(Context context, String eventId, Map<String, String> map) {

//        FlurryAgent.logEvent(eventId, map);
//
//        MobclickAgent.onEvent(context, eventId, map);

    }

    /*
     * GA 友盟和Flurry事件统计
     */
    public static void trackEvent(Context context, String eventId, int eventIntegerId,
            String eventLabel, String category, Map<String, String> map) {
//        EasyTracker easyTracker = EasyTracker.getInstance(context);
//        easyTracker.send(MapBuilder.createEvent(category, eventId, eventLabel, null).build());
//        // AppsFlyerLib.sendTrackingWithEvent(context, eventId, eventLabel);
//
//        FlurryAgent.logEvent(eventId, map);
//
//        MobclickAgent.onEvent(context, eventId, map);

        // Tracker.appEvent(Integer.valueOf(eventIntegerId), 1);
    }

    /*
     * GA 友盟和Flurry事件统计
     */
    public static void trackEvent(Context context, String eventId, int eventIntegerId,
            String eventLabel, String category) {
//        EasyTracker easyTracker = EasyTracker.getInstance(context);
//        easyTracker.send(MapBuilder.createEvent(category, eventId, eventLabel, null).build());
//        // AppsFlyerLib.sendTrackingWithEvent(context, eventId, eventLabel);
//
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("value", eventLabel);
//        FlurryAgent.logEvent(eventId, map);
//
//        MobclickAgent.onEvent(context, eventId, map);

        // Tracker.appEvent(Integer.valueOf(eventIntegerId), 1);
    }

    /*
     * GA 友盟和Flurry事件统计
     */
    public static void trackEvent(Context context, String eventId, int eventIntegerId,
            String eventLabel, long value, String category) {
//        EasyTracker easyTracker = EasyTracker.getInstance(context);
//        easyTracker.send(MapBuilder.createEvent(category, eventId, eventLabel, value).build());
//
//        // AppsFlyerLib.sendTrackingWithEvent(context, eventId, "" + value);
//
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("value", "" + value);
//        FlurryAgent.logEvent(eventId, map);
//
//        MobclickAgent.onEvent(context, eventId, map);

        // Tracker.appEvent(Integer.valueOf(eventIntegerId), 1);
    }
}
