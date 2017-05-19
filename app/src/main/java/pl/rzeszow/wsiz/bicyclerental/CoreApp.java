package pl.rzeszow.wsiz.bicyclerental;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import pl.rzeszow.wsiz.bicyclerental.api.BicycleRentalService;
import pl.rzeszow.wsiz.bicyclerental.api.model.User;
import pl.rzeszow.wsiz.bicyclerental.utils.ApiDateTypeAdapter;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by Roman Savka on
 * 29-Mar-15.
 */
public class CoreApp extends Application {

    private User user;
    private BicycleRentalService apiService;

    @Override
    public void onCreate() {
        super.onCreate();

        initServiceApi();
    }

    private void initServiceApi() {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(Config.API_TIMEOUT_IN_SEC, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(Config.API_TIMEOUT_IN_SEC, TimeUnit.SECONDS);

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Content-Type", "application/json");
            }
        };

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new ApiDateTypeAdapter())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setConverter(new GsonConverter(gson))
                .setEndpoint(Config.SERVICE_URL)
                .setClient(new OkClient(okHttpClient))
                .setRequestInterceptor(requestInterceptor)
                .build();

        apiService = restAdapter.create(BicycleRentalService.class);

    }

    public BicycleRentalService getApiService() {
        return apiService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
