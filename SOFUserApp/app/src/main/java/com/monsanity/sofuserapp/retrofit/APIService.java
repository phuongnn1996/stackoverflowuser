package com.monsanity.sofuserapp.retrofit;

import com.monsanity.sofuserapp.retrofit.response.ReputationItem;
import com.monsanity.sofuserapp.retrofit.response.UserListItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @GET(ApiUtils.USER_LIST)
    Call<UserListItem> doGetUserList(@Query("page") Integer page,
                                     @Query("pagesize") Integer pagesize,
                                     @Query("site") String site);

    @GET(ApiUtils.USER_REPUTATION)
    Call<ReputationItem> doGetUserReputation(@Path("id") Integer id,
                                             @Query("page") Integer page,
                                             @Query("pagesize") Integer pagesize,
                                             @Query("site") String site);

}
