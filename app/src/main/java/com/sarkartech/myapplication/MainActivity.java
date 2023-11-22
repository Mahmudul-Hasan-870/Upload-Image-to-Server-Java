package com.sarkartech.myapplication;

import android.os.Bundle;
        import android.view.View;
        import android.widget.ProgressBar;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonArrayRequest;
        import com.android.volley.toolbox.Volley;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
        import java.util.ArrayList;
        import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "yuFX9gaiQohRAfr9n1BrRZJo7f9nXV";
    private static final String API_URL = "?api_key=" + API_KEY;


    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<ImageModel> imageList;
    private ProgressBar progressBar;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        imageList = new ArrayList<>();
        imageAdapter = new ImageAdapter(imageList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(imageAdapter);

        fetchDataFromServer();
    }

    private void fetchDataFromServer() {
        progressBar.setVisibility(View.VISIBLE);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                API_URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String imageUrl = jsonObject.getString("url");
                                imageList.add(new ImageModel(imageUrl));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        imageAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        // Handle error
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }
}
