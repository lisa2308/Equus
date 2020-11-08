package com.lisap.equus.data.retrofit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitEndpoints {

    @Headers({
            "Content-Type: application/json",
            "Authorization: key=AAAA_MSGMlk:APA91bFFmQ5uD0RjTsYD6D6rD9k7Ycn-qma8LoyCyXPZPuDC9ZTiYGpcvH34q_jwErwT67K2wJgDKFp6vGz7LqUuQzxQmEme5W3yWVgijguIvwPn8MrczetZ3nBSHuf4HJzPtOKIy4eN",
            "project_id: 1085628887641",
    })
    @POST("fcm/send")
    Call<Void> postSendTopicNotificationNewNote(
            @Body JsonObject jsonObject
    );
}
