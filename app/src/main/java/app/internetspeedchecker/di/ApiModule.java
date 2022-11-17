package app.internetspeedchecker.di;

import app.internetspeedchecker.model.CountriesApis;
import app.internetspeedchecker.model.CountryService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private static final String BASE_URL="https://raw.githubusercontent.com/";

    @Provides
    public CountriesApis provideCountriesApis(){

        return new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CountriesApis.class);
    }

    @Provides
    public CountryService provideCountryService(){

        return CountryService.getInstance();
    }
}
