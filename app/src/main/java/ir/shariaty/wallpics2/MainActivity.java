package ir.shariaty.wallpics2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ImageModel> modelClassList;
    private RecyclerView recyclerview;
    Adapter adapter;
    CardView mcat , mfood , mbook , mtrend;
    EditText editText;
    ImageButton search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerview = findViewById(R.id.recyclerview);
        mcat = findViewById(R.id.cat);
        mbook = findViewById(R.id.book);
        mfood = findViewById(R.id.food);
        mtrend = findViewById(R.id.trend);

        editText = findViewById(R.id.edittext);
        search = findViewById(R.id.search);

        modelClassList = new ArrayList<>();
        recyclerview.setLayoutManager(new GridLayoutManager(this , 2));
        recyclerview.setHasFixedSize(true);
        adapter = new Adapter(getApplicationContext() , modelClassList);
        recyclerview.setAdapter(adapter);
        findphotos();

        mcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "cat";
                getSearchimage(query);
            }
        });

        mfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "food";
                getSearchimage(query);
            }
        });

        mbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "book";
                getSearchimage(query);
            }
        });

        mtrend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findphotos();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = editText.toString().trim().toLowerCase();
                if (query.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Enter a Word", Toast.LENGTH_SHORT).show();
                }
                else {
                    getSearchimage(query);
                }
            }
        });

    }

    private void getSearchimage(String query) {
        ApiUtilities.getApiInterface().getSearchImage(query , 1 , 80).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                modelClassList.clear();
                if (response.isSuccessful()) {
                    modelClassList.addAll(response.body().getPhotos());
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Not Working!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {

            }
        });
    }

    private void findphotos() {
        modelClassList.clear();
        ApiUtilities.getApiInterface().getImage(1 , 80).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                modelClassList.clear();
                if (response.isSuccessful()) {
                    modelClassList.addAll(response.body().getPhotos());
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Not Working!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {

            }
        });
    }
}