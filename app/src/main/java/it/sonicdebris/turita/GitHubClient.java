package it.sonicdebris.turita;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by athos on 17/08/16.
 */

public interface GitHubClient {

    @GET("/users/{user}")
    Call<User> GetUser(@Path("user") String user);

    class User {
        String login;
        long id;
    }

}