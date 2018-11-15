package cn.wangjiannan.util;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Locale;

/**
 * OkHttpLogInterceptor.
 *
 * @author wangjiannan
 */
public class OkHttpLogInterceptor implements Interceptor {
    private static final Logger log = LoggerFactory.getLogger(OkHttpLogInterceptor.class);

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        log.info("okhttp", String.format("Sending request %s on %s%n%s", request.url(),
                chain.connection(), request.headers()));

        long t1 = System.nanoTime();
        Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
        log.info("okhttp", String.format(Locale.getDefault(), "Received response for %s in %.1f ms %n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        log.info("okhttp", content);
        return response.newBuilder()
                .body((okhttp3.ResponseBody.create(mediaType, content)))
                .build();
    }
}
