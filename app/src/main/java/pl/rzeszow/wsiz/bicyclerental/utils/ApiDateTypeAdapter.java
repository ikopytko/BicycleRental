package pl.rzeszow.wsiz.bicyclerental.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Roman Savka on
 * 04-Apr-15.
 */
public class ApiDateTypeAdapter extends TypeAdapter<Date> {

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        String v = "/Date(" + value.getTime() + "+0100)/";
        out.value(v);
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        String time = in.nextString();
        long timeMills = Long.parseLong(time.substring(time.indexOf('(') + 1, time.indexOf('+')));
        return new Date(timeMills);
    }
}
