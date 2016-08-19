package it.sonicdebris.turita;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GlosbeClient {

    @GET("/gapi/translate?from=ita&dest=tur&format=json&pretty=true")
    Call<ResponseBody> Search(@Query("phrase") String phrase);

    // example: https://glosbe.com/gapi/translate?from=ita&dest=tur&format=json&phrase=bambino&pretty=true
}
