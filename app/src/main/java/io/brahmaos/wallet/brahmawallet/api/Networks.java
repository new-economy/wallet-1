package io.brahmaos.wallet.brahmawallet.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import io.brahmaos.wallet.brahmawallet.BuildConfig;
import io.brahmaos.wallet.brahmawallet.common.BrahmaConst;
import io.brahmaos.wallet.util.BLog;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * new api access
 */
public class Networks {

    // Default timeout period, unit: second
    private static final int DEFAULT_TIMEOUT = 15;
    ObjectMapper mapper = new ObjectMapper();

    private Context context;

    static String tag() {
        return Networks.class.getName();
    }

    private static Networks instance = new Networks();
    public static Networks getInstance() {
        return instance;
    }

    /**
     * 服务初始化
     *
     * @return true 表示初始化成功，false 为初始化失败
     */
    public boolean init(Context ctx) {
        context = ctx;
        // Get the content of the top 100 crypto currencies
        return true;
    }

    // coinmarketcap API
    private MarketApi marketApi;
    public MarketApi getMarketApi() {
        if (marketApi == null) {
            marketApi = configRetrofit(MarketApi.class, false);
        }
        return marketApi;
    }

    // ipfs
    private IpfsApi ipfsApi;
    public IpfsApi getIpfsApi() {
        if (ipfsApi == null) {
            ipfsApi = ipfsConfigRetrofit(IpfsApi.class, false);
        }
        return ipfsApi;
    }

    private <T> T configRetrofit(Class<T> service, boolean isAddCommonParam) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.MARKET_API_URL)
                .client(configClient(isAddCommonParam))
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(service);
    }

    private <T> T ipfsConfigRetrofit(Class<T> service, boolean isAddCommonParam) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BrahmaConst.IPFS_BASE_URL)
                .client(configClient(isAddCommonParam))
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(service);
    }

    private OkHttpClient configClient(final boolean isAddCommonParam) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

        // Add header configuration interceptors for all requests
        Interceptor headerIntercept = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("X-Client-Platform", "Android");
                builder.addHeader("Content-Type", "application/json; charset=utf-8");
                builder.addHeader("X-Client-Version", BuildConfig.VERSION_NAME);
                builder.addHeader("X-Client-Build", String.valueOf(BuildConfig.VERSION_CODE));
                Request request = builder.build();
                return chain.proceed(request);
            }
        };
        okHttpClient.addNetworkInterceptor(headerIntercept);

        // Response Interceptor
        Interceptor responseInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                ResponseBody responseBody = response.body();
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE);  // Buffer the entire body.
                return response;
            }
        };
        okHttpClient.addNetworkInterceptor(responseInterceptor);

        if (BuildConfig.LOG_DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(@NonNull String message) {
                    BLog.i(tag(), message);
                }
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient.addInterceptor(httpLoggingInterceptor);
        }

        okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        return okHttpClient.build();
    }
}
