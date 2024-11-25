package com.example.volley_comments;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String url = "https://jsonplaceholder.typicode.com/comments";
    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private List<Comment> comments = new ArrayList<>();

    private static final String TAG = "taggggg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        commentAdapter = new CommentAdapter(comments, new CommentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Comment comment) {
                // Display details in Toast or another activity
                Toast.makeText(MainActivity.this, "Comment by: " + comment.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(commentAdapter);


        getData();
    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // success
                        List<Comment> fetchedComments = new ArrayList<>();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject commJson = response.getJSONObject(i);
                                Comment comment = new Comment(
                                        commJson.getInt("id"),
                                        commJson.getString("name"),
                                        commJson.getString("email"),
                                        commJson.getString("body")
                                );
                                fetchedComments.add(comment);
                            }

                            comments.clear();
                            comments.addAll(fetchedComments);
                            commentAdapter.notifyDataSetChanged();

                        } catch (Exception ex) {
                            Log.e(TAG, "Server error: " + ex.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error: " + error.getMessage());
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }
}
