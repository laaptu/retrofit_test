package com.alchemisthq.retrofit.test;

import java.util.Map;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Path;
import retrofit.mime.TypedFile;

/**
 * Created by laaptu on 3/23/15.
 */
public interface GeoApi {

    @GET("/geoip")
    public void fetchGeoData(Callback<GeoData> callback);

    @GET("/{endpoint}")
    public void fetchGeoData(@Path("endpoint") String endPoint, Callback<GeoData> callback);

    @FormUrlEncoded
    @POST("/post.php")
    public void postData(@Field("someString") String someString, Callback<String> callback);

    @POST("/1iex3yb1")
    public void postString(@Body String someString, Callback<Response> callback);


    //Field is used only with FormUrlEncoded

    @FormUrlEncoded
    @POST("/1iex3yb1")
    public void postFormData(@Field("username") String username, @Field("password") String password,
                             Callback<Response> callback);

    @POST("/post")
    public void postPOJO(@Body GeoData geoData, Callback<Response> callback);

    @FormUrlEncoded
    @POST("/1iex3yb1")
    public void postHashMap(@FieldMap Map<String, Object> params, Callback<Response> callback);

    @Multipart
    @POST("/path")
    public void postMultipartData(@Header("auth_token") String authToken,
                                  @Part("title") String title,
                                  @Part("request") TypedFile photo,
                                  Callback<Response> callback);


    @Multipart
    @POST("/post")
    public void postMultipartDatum(@Header("auth_token") String authToken,
                                   @Part("title") String title,
                                   @PartMap Map<String, TypedFile> fileMap,
                                   Callback<Response> callback);

    @GET("/delay/{delaytime}")
    public void cancelRequestTest(@Path("delaytime") int delayTime,
                                  CustomCallbackInterface<Response> callbackInterface);

    @GET("/delay/{delaytime}")
    public void cancelRequestTest(@Path("delaytime") int delayTime,
                                  Callback<Response> callback);

//    //You can use rx.java for sophisticated composition of requests
//    @GET("/users/{user}")
//    public Observable<SomeUserModel> fetchUser(@Path("user") String user);
//
//    //or you can just get your model if you use json api
//    @GET("/users/{user}")
//    public SomeUserModel fetchUser(@Path("user") String user);
//
//    //or if there are some special cases you can process your response manually
//    @GET("/users/{user}")
//    public Response fetchUser(@Path("user") String user);

}
