package dk.au.mad21spring.group20.knittybuddy.inspiration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.R;
import dk.au.mad21spring.group20.knittybuddy.inspiration.Models.Pattern;
import dk.au.mad21spring.group20.knittybuddy.inspiration.ViewModels.InspirationListViewModel;
import dk.au.mad21spring.group20.knittybuddy.repository.Repository;

public class InspirationListActivity extends AppCompatActivity implements InspirationAdaptor.IPatternItemClickedListener{

    public static final int REQ_DETAIL = 200;
    private static final String TAG = "InspirationList";


    private Repository repository;
    private InspirationListViewModel inspirationListViewModel;

    private InspirationAdaptor adaptor;
    private RecyclerView recyclerView;
    private Button searchBtn;
    private Button backBtn;
    private EditText searchText;

    private List<Pattern> patternList;

    private int currentIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspiration_list);

//        repository = new Repository(getApplication());

        adaptor = new InspirationAdaptor(this);
        recyclerView = findViewById(R.id.inspirationRcView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptor);

        inspirationListViewModel = new ViewModelProvider(this).get(InspirationListViewModel.class);
//        inspirationListViewModel.getPatterns().observe(this, new Observer<List<Pattern>>() {
//            @Override
//            public void onChanged(List<Pattern> patterns) {
//                patternList = patterns;
////                updateUI();
//            }
//        });

        searchText = findViewById(R.id.inspirationSearchText);

        searchBtn = findViewById(R.id.btn_inspirationList_search);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = searchText.getText().toString();
                searchPatterns(search);
            }
        });

        backBtn = findViewById(R.id.btn_inspirationList_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//        queue = Volley.newRequestQueue(this);

    }

    private void searchPatterns(String input)
    {
        Log.d(TAG, "Search for input:" + input);
        if (!input.equals(""))
        {
            inspirationListViewModel.getRepoPatterns(input);
        }
        else
        {
            Toast.makeText(this, "Please input a search term", Toast.LENGTH_SHORT).show();
        }
        inspirationListViewModel.getPatterns();
    }


    @Override
    public void onPatternClicked(int index) {
        currentIndex = index;
        Pattern thisPattern = patternList.get(index);
        Intent i = new Intent(this, InspirationDetailActivity.class);
        i.putExtra("thisID", thisPattern.id);
        startActivityForResult(i, REQ_DETAIL);
    }
}