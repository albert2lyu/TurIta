package it.sonicdebris.turita;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DictionaryActivity extends AppCompatActivity
{
    GlosbeClient glosbe;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ((TextView)findViewById(R.id.result_textview)).setMovementMethod(new ScrollingMovementMethod());

        glosbe = GlosbeApiGenerator.Create();
    }

    SearchView searchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.options_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null)
        {
            searchView = (SearchView) searchItem.getActionView();
        }

        if (searchView != null)
        {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnQueryTextListener(queryTextListener);
        }
        //super.onCreateOptionsMenu(menu, inflater);



        return true;
    }

    OnQueryTextListener queryTextListener = new OnQueryTextListener()
    {

        @Override
        public boolean onQueryTextChange(String newText)
        {
            Log.i("onQueryTextChange", newText);
            return true;
        }

        @Override
        public boolean onQueryTextSubmit(String query)
        {
            Log.i("onQueryTextSubmit", query);
            Call<ResponseBody> call = glosbe.Search(query);

            call.enqueue( new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                {
                    String txt;
                    if (response.isSuccessful())
                    {
                        try {
                            txt = response.body().string();
                        } catch (IOException e) {
                            txt = "Can't parse response";
                        }
                    }
                    else
                        txt = "Response unsuccessful";

                    ((TextView)findViewById(R.id.result_textview)).setText(txt);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t)
                {
                    assert (findViewById(R.id.result_textview)) != null;
                    ((TextView)findViewById(R.id.result_textview)).setText("Request error");
                }
            });
            return true;
        }
    };

    void TestRetrofit()
    {
        GitHubClient client = ServiceGenerator.createService(GitHubClient.class);
        Log.d("Retrofit", "Retro call");
        // Fetch and print a list of the contributors to this library.
        Call<GitHubClient.User> call = client.GetUser("sonicdebris");

        call.enqueue(new Callback<GitHubClient.User> () {
            @Override
            public void onResponse(Call<GitHubClient.User> call, Response<GitHubClient.User> response)
            {
                if (response.isSuccessful())
                {
                    GitHubClient.User u = response.body();
                    Log.d("Retrofit","Retro user "+u.login+". id: "+u.id);
                }
                else
                {
                    Log.e("Retrofit","Retro response not successful. "+response.raw().toString());
                    // error response, no access to resource?
                }
            }

            @Override
            public void onFailure(Call<GitHubClient.User> call, Throwable t)
            {
                // something went completely south (like no internet connection)
                Log.e("Error", "Retro "+t.getMessage());
            }
        });

        Log.d("Retrofit","Retro called");
    }
    // suggestions while typing:
    // https://ramannanda.blogspot.it/2014/10/android-searchview-integration-with.html
}
