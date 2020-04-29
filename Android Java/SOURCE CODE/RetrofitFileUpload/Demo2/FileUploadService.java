package com.app.weerpbiometric.employee;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface FileUploadService {

    @Multipart
    @POST("EmpFingerPrint")
    Call<ResponseBody> uploadFile(
            @Part("user_id") RequestBody description,
            @Part MultipartBody.Part file);

    @Multipart
    @POST("upload_order")
    Call<ResponseBody> uploadFileWithPartMap(
            @PartMap() Map<String, RequestBody> partMap,
            @Part MultipartBody.Part file);


}
