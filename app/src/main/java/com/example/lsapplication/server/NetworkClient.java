package com.example.lsapplication.server;

import com.example.lsapplication.App;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient
{
    public static final String BASE_URL =  "http://localhost:3020/";


        public static final String User = "users/";
        public static final String employees = "employees";

        private static NetworkClient instance;

        private Retrofit retrofit;
        UserApiService userApiService;




        private NetworkClient() {
            if (retrofit == null) {
                OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                clientBuilder.addInterceptor(loggingInterceptor);


        /*    Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();*/
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(clientBuilder.build())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        //         .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();

           /* OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
*/
                userApiService = retrofit.create(UserApiService.class);



            }
        }


//    private Converter.Factory createGsonConverter() {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(RedirectionInfo.class, new RedirectionInfoDeserializer());
//        Gson gson = gsonBuilder.create();
//        return GsonConverterFactory.create(gson);
//    }

        public static NetworkClient getInstance() {
            if (instance == null) {
                instance = new NetworkClient();
            }
            return instance;
        }


        public UserApiService getUserApiService() {
            return userApiService;
        }

}
