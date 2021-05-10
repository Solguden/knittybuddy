package dk.au.mad21spring.group20.knittybuddy.inspiration.ViewModels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;

import dk.au.mad21spring.group20.knittybuddy.inspiration.Models.Pattern;
import dk.au.mad21spring.group20.knittybuddy.repository.Repository;

public class InspirationListViewModel extends AndroidViewModel {

    private static final String TAG = "InspirationListViewModel";
    Repository repository;
    Context context;
    LiveData<List<Pattern>> patterns;

    public InspirationListViewModel(@NonNull Application app)
    {
        super(app);
        repository = Repository.getRepositoryInstance();
        context = app.getApplicationContext();
//        patterns

    }

    public LiveData<List<Pattern>> getPatterns() { return repository.getPatterns(); } // GetPatternSearch (REPO?))

    public void getRepoPatterns(String input) { repository.getSearchPatterns(input, context); }
//LiveData<List<Pattern>>
}
