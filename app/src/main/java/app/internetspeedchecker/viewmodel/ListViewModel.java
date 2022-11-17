package app.internetspeedchecker.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.internetspeedchecker.di.ApiComponent;
import app.internetspeedchecker.di.DaggerApiComponent;
import app.internetspeedchecker.model.CountryModel;
import app.internetspeedchecker.model.CountryService;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {

    public MutableLiveData<List<CountryModel>> countries = new MutableLiveData<List<CountryModel>> ();
    public MutableLiveData<Boolean> countryLoadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    @Inject
    public CountryService countryService ;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ListViewModel(){
        super();

        DaggerApiComponent.create().inject(this);
    }

    public void refresh()
    {
        fetchcountries();
    }

    private void fetchcountries(){

        loading.setValue(true);

        compositeDisposable.add(
                countryService.getCountries()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<CountryModel>>(){
                                           @Override
                                           public void onSuccess(List<CountryModel> countryModels) {
                                                countries.setValue(countryModels);
                                                countryLoadError.setValue(false);
                                                loading.setValue(false);
                                           }

                                           @Override
                                           public void onError(Throwable e) {
                                               countryLoadError.setValue(true);
                                               loading.setValue(false);
                                               e.printStackTrace();
                                           }
                                       })
        );

    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
    }
}
