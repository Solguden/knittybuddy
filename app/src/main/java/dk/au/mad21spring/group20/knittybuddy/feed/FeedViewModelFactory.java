package dk.au.mad21spring.group20.knittybuddy.feed;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FeedViewModelFactory implements ViewModelProvider.Factory {

    Application application;

    public FeedViewModelFactory(Application app) { this.application = app; }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FeedViewModel(application);
    }
}
