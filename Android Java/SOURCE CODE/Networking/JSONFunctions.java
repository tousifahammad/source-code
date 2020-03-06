package com.app.cubeapparels.loaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.widget.ImageView;

import com.app.cubeapparels.baseclasses.WebServices;
import com.app.cubeapparels.utils.AppData;
import com.app.cubeapparels.utils.SP;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


public class JSONFunctions implements Callback<String> {

    public OnJSONResponseListener ojrl;

    private String method;
    private String url, token;
    private HashMap<String, String> hm = null;
    private HashMap<String, RequestBody> fileBodyMap = null;
    private HashMap<String, RequestBody> stringBodyMap = null;
    private OkHttpClient httpClient = new OkHttpClient();

    private JSONObject paramObject = null;
    int url_no;
    boolean isMultipart = false;
    private Retrofit retrofit = null;

    public JSONFunctions(OnJSONResponseListener ojrl) {
        this.ojrl = ojrl;
    }


    private void setupClientParams() {
        System.out.println("Inside setupClientParams method");
        /*HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);*/

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                //.addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request.Builder ongoing = chain.request().newBuilder();
                        return chain.proceed(ongoing.build());
                    }
                })
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(WebServices.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

             /*.client(httpClient.newBuilder().addInterceptor(new Interceptor() {
                      @Override
                      public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                          .addHeader("X-Header", "GiIyxW7KCBdpYByJXpC4V4YAow7Q48")
                          .addHeader("Content-Type", "application/json").build();
                        return chain.proceed(request);
                      }
                    }).build())*/
    }

    private synchronized void makeRequest(int url_no) {
        //System.out.println("Inside makeRequest method");
        this.url_no = url_no;
        RequestInterface apiService = retrofit.create(RequestInterface.class);
        Call<String> call = null;

        if (method.equalsIgnoreCase("POST")) {
            // request method is POST
            if (stringBodyMap != null && fileBodyMap != null) {
                call = apiService.makeMultipartFilePostRequest(url, fileBodyMap, stringBodyMap);
                System.out.println("makeMultipartFilePostRequest(url,fileBodyMap,stringBodyMap) method called");
            }
            if (stringBodyMap != null && fileBodyMap == null) {
                call = apiService.makeMultipartStringPostRequest(url, stringBodyMap);
                System.out.println("makeMultipartStringPostRequest(url,stringBodyMap) method called");
            }

            if (stringBodyMap == null && fileBodyMap == null) {
                if (hm != null) {
                    call = apiService.makePostParamRequest(url, hm);
                    System.out.println("makePostParamRequest(url,hm) method called");
                } else {
                    call = apiService.makePostRequest(url);
                    Log.e("url", url);
                    System.out.println("makePostRequest(url) method called");
                }
            }

        } else if (method.equalsIgnoreCase("GET")) {
            // request method is GET
            if (hm != null) {
                call = apiService.makeGetParamRequest(url, hm);
                System.out.println("makeGetParamRequest(url,hm) method called");
            } else {
                call = apiService.makeGetRequest(url);
                System.out.println("makeGetRequest(url) method called");
            }

        }
        if (call != null)
            call.enqueue(this);
        else {
            System.out.println("Something wrong, call is null");
        }
    }

    private synchronized void makeRawRequest(int url_no) {
        this.url_no = url_no;
        RequestInterface apiService = retrofit.create(RequestInterface.class);
        Call<String> call = null;

        if (method.equalsIgnoreCase("POST")) {
            //Raw data input
            if (paramObject != null) {
                Log.e("url", url);
                Log.e("paramObject", paramObject.toString());
                call = apiService.makeRawStringPostRequest(url, paramObject.toString());
                System.out.println("makeRawStringPostRequest(url) method called");
            }
        } else if (method.equalsIgnoreCase("GET")) {
            if (paramObject != null) {
                call = apiService.makeRawStringPostRequest(url, paramObject.toString());
                System.out.println("makeRawStringPostRequest(url) method called");
            }
        }
        if (call != null)
            call.enqueue(this);
        else {
            System.out.println("Something wrong, call is null");
        }
    }

    private void callRequest(int url_no) {
        //System.out.println("Inside callRequest method");
        setupClientParams();
        makeRequest(url_no);
    }

    private void callRawRequest(int url_no) {
        setupClientParams();
        makeRawRequest(url_no);
    }

    public void makeHttpRequest(String url, String method, int url_no) {
        Log.d(AppData.TAG, "URL ====> : " + url);
        Log.d(AppData.TAG, "PARAMS ====> : " + hm);

        this.method = method;
        this.url = WebServices.BASE_URL + url;

        callRequest(url_no);
    }

    public void makeHttpRequest(String url, String method, HashMap<String, String> hmString, HashMap<String, File> hmFile, int url_no) {
        Log.d(AppData.TAG, "URL ====> : " + url);
        Log.d(AppData.TAG, "PARAMS ====> : " + hm);

        this.hm = hmString;
        this.method = method;
        this.url = WebServices.BASE_URL + url;
        stringBodyMap = createStringBodyMap(hm);
        fileBodyMap = createFileBodyMap(hmFile);

        callRequest(url_no);
    }

    public void makeHttpRequest(String url, String method, HashMap<String, String> hm, boolean isMultipart, int url_no) {
        Log.d(AppData.TAG, "URL ====> : " + url);
        Log.d(AppData.TAG, "PARAMS ====> : " + hm);

        this.hm = hm;
        this.method = method;
        this.isMultipart = isMultipart;
        this.url = WebServices.BASE_URL + url;
        if (this.isMultipart)
            stringBodyMap = createStringBodyMap(hm);

        callRequest(url_no);
    }


    public void makeRawHttpRequest(String url, String method, HashMap<String, String> hm, int url_no) {
        Log.d(AppData.TAG, "URL ====> : " + url);
        Log.d(AppData.TAG, "PARAMS ====> : " + hm);

        this.paramObject = createRawStringBody(hm);
        this.method = method;
        this.url = WebServices.BASE_URL + url;

        callRawRequest(url_no);
    }


    public void makeHeaderPOSTRequest(String url, int url_no, Context context) {
        Log.d(AppData.TAG, "URL ====> : "+ WebServices.BASE_URL  + url);
        Log.d(AppData.TAG, "PARAMS ====> : " + hm);

        this.url_no = url_no;

        HashMap<String, String> headers = new HashMap<>();
        //headers.put("KEY_AUTHORIZATION","paste AUTHORIZATION value here");
        headers.put("Authorization", "Bearer " + SP.getStringPreference(context, SP.auth_token));

        setupClientParams();


        RequestInterface apiService = retrofit.create(RequestInterface.class);
        Call<String> call = apiService.makeHeaderPostRequest(WebServices.BASE_URL + url, headers);
        if (call != null)
            call.enqueue(this);
        else {
            System.out.println("Something wrong, call is null");
        }
    }

    public void makeHeaderBodyPOSTRequest(String url, HashMap<String, String> hashMap, int url_no, Context context) {
        Log.d(AppData.TAG, "URL ====> : " + WebServices.BASE_URL + url);
        Log.d(AppData.TAG, "PARAMS ====> : " + hashMap);

        this.url_no = url_no;

        HashMap<String, String> headers = new HashMap<>();
        //headers.put("KEY_AUTHORIZATION","paste AUTHORIZATION value here");
        headers.put("Authorization", "Bearer " + SP.getStringPreference(context, SP.auth_token));

        setupClientParams();

        RequestInterface apiService = retrofit.create(RequestInterface.class);
        Call<String> call = apiService.makeHeaderBodyPostRequest(WebServices.BASE_URL + url, headers, hashMap);
        if (call != null)
            call.enqueue(this);
        else {
            System.out.println("Something wrong, call is null");
        }
    }


    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String strJson = response.body();
        System.out.println("RESPONSE ====> : " + strJson);

        ojrl.getJSONResponseResult(strJson, url_no);
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        System.out.println("Inside onFailure, null will return");
        ojrl.getJSONResponseResult(null, url_no);
    }

/*   @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        //System.out.println("Inside onSuccess with JSONObject:"+response.toString()+statusCode);
        ojrl.getJSONResponseResult(response.toString(),url_no);
    }

   @Override
   public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        //System.out.println("Inside onSuccess with JSONArray:"+response.toString()+statusCode);
        ojrl.getJSONResponseResult(response.toString(),url_no);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        super.onSuccess(statusCode, headers, responseString);
        //System.out.println("Inside onSuccess with String:"+responseString+"StatusCode: "+statusCode);
        ojrl.getJSONResponseResult(responseString,url_no);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
        //System.out.println("Inside onFailure with String, Statuscode is:"+statusCode+",String is: "+res );
        ojrl.getJSONResponseResult(null,url_no);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
      //  super.onFailure(statusCode, headers, throwable, errorResponse);
        //System.out.println("Inside onFailure with JSONObject, Statuscode is:"+statusCode);
        ojrl.getJSONResponseResult(null,url_no);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
     //   super.onFailure(statusCode, headers, throwable, errorResponse);
        //System.out.println("Inside onFailure with JSONArray, Statuscode is:"+statusCode);
        ojrl.getJSONResponseResult(null,url_no);
    }*/

    private interface RequestInterface {
        @GET
        Call<String> makeGetParamRequest(@Url String url, @QueryMap Map<String, String> map);

        @GET
        Call<String> makeGetRequest(@Url String url);

        @FormUrlEncoded
        @POST
        Call<String> makePostParamRequest(@Url String url, @FieldMap Map<String, String> map);

        //@Headers("Content-Type: application/json")
        @POST
        Call<String> makePostRequest(@Url String url);

        @Multipart
        @POST
        Call<String> makeMultipartFilePostRequest(
                @Url String url, @PartMap Map<String, RequestBody> files, @PartMap Map<String, RequestBody> params);

        @Multipart
        @POST
        Call<String> makeMultipartStringPostRequest(
                @Url String url, @PartMap Map<String, RequestBody> str);

        @Headers("Content-Type: application/json")
        @POST
        Call<String> makeRawStringPostRequest(@Url String url, @Body String body);

        //============ With header auth token===============
        @POST
        Call<String> makeHeaderPostRequest(@Url String url, @HeaderMap Map<String, String> headers);

        @FormUrlEncoded
        @POST
        Call<String> makeHeaderBodyPostRequest(@Url String url, @HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);
    }

    private final String MULTIPART_FORM_DATA = "multipart/form-data";

    private RequestBody createRequestBody(@NonNull File file) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), file);
    }

    private RequestBody createRequestBody(@NonNull String s) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), s);
    }


    private JSONObject createRawStringBody(HashMap<String, String> hm) {
        JSONObject paramObject = new JSONObject();
        for (Map.Entry<String, String> entry : hm.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (value == null) {
                value = "";
            }
            try {
                System.out.println("Key:" + key + ",Value:" + value);
                if (value.startsWith("{")) {
                    JSONObject jObject = new JSONObject(value);
                    paramObject.put(key, jObject);
                } else if (value.startsWith("[")) {
                    JSONArray jArray = new JSONArray(value);
                    paramObject.put(key, jArray);
                } else {
                    paramObject.put(key, value);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return paramObject;
    }

    private HashMap<String, RequestBody> createStringBodyMap(HashMap<String, String> hm) {
        HashMap<String, RequestBody> hmString = new HashMap<String, RequestBody>();
        for (Map.Entry<String, String> entry : hm.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            RequestBody requestValue = createRequestBody(value);
            hmString.put(key, requestValue);
        }
        return hmString;
    }


    private HashMap<String, RequestBody> createFileBodyMap(HashMap<String, File> hm) {
        HashMap<String, RequestBody> hmFile = new HashMap<String, RequestBody>();
        for (Map.Entry<String, File> entry : hm.entrySet()) {
            String key = entry.getKey();
            File value = entry.getValue();
            RequestBody requestValue = createRequestBody(value);
            hmFile.put(key, requestValue);
        }
        return hmFile;

    }


    public interface OnJSONResponseListener {
        void getJSONResponseResult(String result, int url_no);
    }


    public static boolean isInternetOn(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public void setImageFromUrl(Context context, String url, Integer errorImage, ImageView imgvw, boolean isCircular) {
        CustomCircularImageViewTarget target = new CustomCircularImageViewTarget(context, imgvw, isCircular);
        if (errorImage != null) {
            Glide.with(context).load(url).asBitmap().centerCrop().error(errorImage).into(target);
        } else {
            Glide.with(context).load(url).asBitmap().into(target);
        }
    }

   /* public void setImageFromUrl(Fragment fragment, String url, Integer errorImage, ImageView imgvw, boolean isCircular) {
        CustomCircularImageViewTarget target = new CustomCircularImageViewTarget(fragment, imgvw, isCircular);
        if (errorImage != null) {
            Glide.with(fragment).load(url).asBitmap().centerCrop().error(errorImage).into(target);
        } else {
            Glide.with(fragment).load(url).asBitmap().into(target);
        }
    }*/


    private class CustomCircularImageViewTarget extends BitmapImageViewTarget {
        ImageView imgvw;
        boolean isCircular = false;
        Context context = null;
        Fragment fragment = null;

        public CustomCircularImageViewTarget(Context context, ImageView view, boolean isCircular) {
            super(view);
            CustomCircularImageViewTarget.this.imgvw = view;
            CustomCircularImageViewTarget.this.isCircular = isCircular;
            CustomCircularImageViewTarget.this.context = context;
        }

        public CustomCircularImageViewTarget(Fragment fragment, ImageView view, boolean isCircular) {
            super(view);
            CustomCircularImageViewTarget.this.imgvw = view;
            CustomCircularImageViewTarget.this.isCircular = isCircular;
            CustomCircularImageViewTarget.this.fragment = fragment;
        }


        @Override
        protected void setResource(Bitmap resource) {
            RoundedBitmapDrawable circularBitmapDrawable = null;
            if (context != null) {
                circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
            } else if (fragment != null) {
                circularBitmapDrawable = RoundedBitmapDrawableFactory.create(fragment.getResources(), resource);
            }
            if (isCircular)
                circularBitmapDrawable.setCircular(true);
            if (circularBitmapDrawable != null)
                imgvw.setImageDrawable(circularBitmapDrawable);
        }

    }

}


