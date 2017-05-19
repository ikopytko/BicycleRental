package pl.rzeszow.wsiz.bicyclerental.utils;

import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

/**
 * Created by Roman Savka on
 * 04-Apr-15.
 */
public class ErrorReader {

    public static String getDetailsOnError(RetrofitError error){
       return new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
    }
}
