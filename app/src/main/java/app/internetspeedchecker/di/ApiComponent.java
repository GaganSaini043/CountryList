package app.internetspeedchecker.di;

import app.internetspeedchecker.model.CountryModel;
import app.internetspeedchecker.model.CountryService;
import app.internetspeedchecker.viewmodel.ListViewModel;
import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent {

    void inject(CountryService countryService);

    void inject(ListViewModel listViewModel);
}
