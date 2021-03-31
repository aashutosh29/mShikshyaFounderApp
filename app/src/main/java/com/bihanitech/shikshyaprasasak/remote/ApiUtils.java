package com.bihanitech.shikshyaprasasak.remote;


/**
 * Created by dilip on 1/11/18.
 */

public class ApiUtils {


    public static final String AUTH_BASE_URL = "https://shikshyasoftware.com.np/ParentAuthAPI-235245892/api/";

    //    public static final String NEW_IP_URL = "https://139.59.81.31/CoreApplicationandAPIService-4617993073/api/";
    public static final String NEW_IP_URL = "https://shikshyasoftware.com.np/CoreApplicationandAPIService-4617993073/api/";
    public static final String V2_AUTH_BASE_URL = "http://shikshyasoftware.com.np/CoreApplicationandAPIService-4617993073/api/v2/";
    public static final String DUMMY_URL = "https://staging.shikshyasoftware.com/api/";


    public static CDSService getCDSService() {
        // return RetrofitClient.getClient(BASE_URL).create(CDSService.class);
        return RetrofitClient.getClient(NEW_IP_URL).create(CDSService.class);
    }

    public static AUTHService getAuthCDSService() {
        return RetrofitClient.getClient(AUTH_BASE_URL).create(AUTHService.class);
    }

    public static CDSService getV2AuthCDSService() {
        return RetrofitClient.getClient(V2_AUTH_BASE_URL).create(CDSService.class);
    }

    public static CDSService getDummyCDSService() {
        return RetrofitClient.getClient(DUMMY_URL).create(CDSService.class);

    }


}
