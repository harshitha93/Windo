package com.kortain.windo.domain.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kortain.windo.domain.utility.Constants;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.kortain.windo.domain.utility.OWMEndpoints.BASE_URL;

/**
 * Created by satiswardash on 24/04/18.
 */

public class NetworkServiceBuilder {

    /**
     * Implemented a request interceptor to send the API_KEY to the OWM server in each network request
     */
    private static OkHttpClient interceptor =
            new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();

                    HttpUrl url = originalHttpUrl.newBuilder()
                            .addQueryParameter("APPID", Constants.OWM_API_KEY)
                            .build();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .url(url);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            }).build();

    /**
     * Get interceptor instance
     *
     * @return
     */
    private static OkHttpClient getInterceptor() {
        return interceptor;
    }

    /**
     * Gives you the retrofit API Service endpoints reference {@link NetworkService}
     * Here we are adding {@link GsonConverterFactory} before making the request to convert json data to object
     * and we are adding the interceptor client that we have created above.
     *
     * @return NetworkService reference
     */
    public static NetworkService build() {

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit =
                new Retrofit.Builder().
                        baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(getInterceptor())
                        .build();
        return retrofit.create(NetworkService.class);
    }
}
