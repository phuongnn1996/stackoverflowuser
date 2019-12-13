package com.monsanity.sofuserapp.retrofit;

public class ApiUtils {

    private static APIService APISERVICE = null;

    public static final String URL = "https://api.stackexchange.com/";

    public static final String USER_LIST = "2.2/users";

    public static final String USER_REPUTATION = "2.2/users/{id}/reputation-history";

    public ApiUtils() {

    }

    public static APIService getRetrofitService() {
        if (APISERVICE == null) {
            APISERVICE = RetrofitClient.getClient(URL).create(APIService.class);
        }
        return APISERVICE;
    }
}
