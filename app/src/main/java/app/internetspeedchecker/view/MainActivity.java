package app.internetspeedchecker.view;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.internetspeedchecker.R;
import app.internetspeedchecker.model.CountryModel;
import app.internetspeedchecker.viewmodel.ListViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.text_err)
    TextView txt_err;

    @BindView(R.id.countriesList)
    RecyclerView country_list;

    @BindView(R.id.loading_view)
    ProgressBar loading_view;

    private ListViewModel listViewModel;
    private CountryViewAdapter adapter =new CountryViewAdapter(new ArrayList<>());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);

        listViewModel.refresh();

        country_list.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        country_list.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listViewModel.refresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        observerViewModel();
    }

    private void observerViewModel(){
        listViewModel.countries.observe(this, countryModels -> {
            if(countryModels != null){
                Log.d( "observerViewModel: ","..." + countryModels);
                country_list.setVisibility(View.VISIBLE);
                adapter.updateCountries(countryModels);

            }
        });
        listViewModel.countryLoadError.observe(this, isError -> {
            if (isError != null){
                txt_err.setVisibility(isError ? View.VISIBLE : View.GONE);
            }

        });
        listViewModel.loading.observe(this, isLoading -> {

            if(isLoading != null){
                loading_view.setVisibility((isLoading ? View.VISIBLE : View.GONE));
               if(isLoading){
                   txt_err.setVisibility(View.GONE);
                   country_list.setVisibility(View.GONE);
               }
            }

        });

    }
}