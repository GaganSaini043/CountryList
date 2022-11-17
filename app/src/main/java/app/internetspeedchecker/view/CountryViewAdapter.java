package app.internetspeedchecker.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.internetspeedchecker.R;
import app.internetspeedchecker.model.CountryModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CountryViewAdapter extends RecyclerView.Adapter<CountryViewAdapter.CountryViewHolder>{

    private List<CountryModel> countries;

    public CountryViewAdapter(List<CountryModel> countries){
        this.countries=countries;
    }

    public void updateCountries(List<CountryModel> newCountries){
        countries.clear();
        countries.addAll(newCountries);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country,parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {

        holder.bind(countries.get(position));
    }

    @Override
    public int getItemCount() {
        Log.d( "sizelist","..."+countries.size());
        return countries.size();
    }

    class CountryViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.flag_img)
        ImageView flag_Img;

        @BindView(R.id.txt_name)
        TextView countryName;

        @BindView(R.id.txt_capital)
        TextView capital;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bind(CountryModel country){
            Log.d( "fetchcountries","..."+country.getCountryName());
            countryName.setText(country.getCountryName());
            capital.setText(country.getCapital());
            Util.loadImage(flag_Img,country.getFlag(),Util.getCircularProgressDrawable(flag_Img.getContext()));
        }
    }
}
