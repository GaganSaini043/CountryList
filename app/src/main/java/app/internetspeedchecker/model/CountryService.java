package app.internetspeedchecker.model;

import java.util.List;

import javax.inject.Inject;

import app.internetspeedchecker.di.ApiComponent;
import app.internetspeedchecker.di.DaggerApiComponent;
import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryService {

    private static CountryService instance;

    @Inject
    public CountriesApis api;

    private CountryService(){

        DaggerApiComponent.create().inject(this);
    }

    public static CountryService getInstance() {
        if(instance == null){
            instance = new CountryService();
        }
        return instance;
    }

    public Single<List<CountryModel>> getCountries(){
        return api.getCountries();
    }
}
