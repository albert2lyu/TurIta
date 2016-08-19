package it.sonicdebris.turita;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GlosbeApiGenerator {

    public static final String API_BASE_URL = "https://glosbe.com";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static GlosbeClient Create()
    {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(GlosbeClient.class);
    }

}
