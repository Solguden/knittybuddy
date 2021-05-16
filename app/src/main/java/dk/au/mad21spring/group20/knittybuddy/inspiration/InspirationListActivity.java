package dk.au.mad21spring.group20.knittybuddy.inspiration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.R;
import dk.au.mad21spring.group20.knittybuddy.inspiration.Models.ComPattern;
import dk.au.mad21spring.group20.knittybuddy.inspiration.Models.Pattern;
import dk.au.mad21spring.group20.knittybuddy.inspiration.ViewModels.InspirationListViewModel;
import dk.au.mad21spring.group20.knittybuddy.repository.Repository;

public class InspirationListActivity extends AppCompatActivity implements InspirationAdaptor.IPatternItemClickedListener{

    public static final int REQ_DETAIL = 200;
    private static final String TAG = "InspirationList";

    private InspirationListViewModel inspirationListViewModel;

    private InspirationAdaptor adaptor;
    private RecyclerView recyclerView;
    private Button searchBtn;
    private Button backBtn;
    private EditText searchText;

    private List<ComPattern> comPatternList;
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspiration_list);

        adaptor = new InspirationAdaptor(this);
        recyclerView = findViewById(R.id.inspirationRcView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptor);

        comPatternList = new ArrayList<>();

        inspirationListViewModel = new ViewModelProvider(this).get(InspirationListViewModel.class);
        inspirationListViewModel.getPatterns().observe(this, new Observer<List<ComPattern>>() {
            @Override
            public void onChanged(List<ComPattern> comPatterns) {
                    // Clear list for new search and add new items from search.
                    if (comPatternList.size() != 0)
                    {
                        comPatternList.clear();
                    }
                    comPatternList.addAll(comPatterns);
                    adaptor.updatePatternList(comPatternList);
            }
        });

        searchText = findViewById(R.id.inspirationSearchText);

        searchBtn = findViewById(R.id.btn_inspirationList_search);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = searchText.getText().toString();
                searchPatterns(search);
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

        backBtn = findViewById(R.id.btn_inspirationList_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void searchPatterns(String input)
    {
        Log.d(TAG, "Search for input:" + input);
        if (!input.equals(""))
        {
            inspirationListViewModel.getRepoPatterns(input);
            LiveData<List<ComPattern>> data = inspirationListViewModel.getPatterns();
            Log.d(TAG, "DATA: " + data.getValue());
        }
        else
        {
            Toast.makeText(this, getString(R.string.searchTerm), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPatternClicked(int index) {
        currentIndex = index;
        ComPattern thisPattern = comPatternList.get(index);
        Intent i = new Intent(this, InspirationDetailActivity.class);
        i.putExtra("thisID", thisPattern.id);
        i.putExtra("thisPatternName", thisPattern.name);
        i.putExtra("thisPatternPhoto", thisPattern.first_photo.medium_url);
        i.putExtra("thisAuthorName", thisPattern.pattern_author.users[0].username);
        i.putExtra("thisAuthorPhoto", thisPattern.pattern_author.users[0].photo_url);

        startActivityForResult(i, REQ_DETAIL);
    }
}