package app.internetspeedchecker;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import app.internetspeedchecker.model.CountryModel;
import app.internetspeedchecker.model.CountryService;
import app.internetspeedchecker.viewmodel.ListViewModel;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class ListViewModelTest {

    @Rule
    public InstantTaskExecutorRule rule=new InstantTaskExecutorRule();

    @Mock
    CountryService countryService;

    @InjectMocks
    ListViewModel viewModel = new ListViewModel();

    private Single<List<CountryModel>> testsingle;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCountriesSuccess(){
        CountryModel country= new CountryModel("countryname", "capital","flag");

        ArrayList<CountryModel> countryList = new ArrayList<>();

        countryList.add(country);

        testsingle = Single.just(countryList);

        Mockito.when(countryService.getCountries()).thenReturn(testsingle);

        viewModel.refresh();

        Assert.assertEquals(1,viewModel.countries.getValue().size());
        Assert.assertEquals(false,viewModel.countryLoadError.getValue());
        Assert.assertEquals(false,viewModel.loading.getValue());
    }

    @Test
    public void getCountriesFailure(){

        testsingle = Single.error(new Throwable());

        Mockito.when(countryService.getCountries()).thenReturn(testsingle);

        viewModel.refresh();

        Assert.assertEquals(true,viewModel.countryLoadError.getValue());
        Assert.assertEquals(false,viewModel.loading.getValue());
    }

    @Before
    public void setupRxSchedulers(){
        Scheduler immediate= new Scheduler() {
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(new Executor() {
                    @Override
                    public void execute(Runnable runnable) {
                        runnable.run();
                    }
                },
                        true);
            }
        };
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);

    }
}
