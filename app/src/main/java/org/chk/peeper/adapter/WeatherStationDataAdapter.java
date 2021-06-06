package org.chk.peeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import org.chk.peeper.R;
import org.chk.peeper.dto.MediaItem;
import org.chk.peeper.model.WeatherStationData;

import java.util.List;

public class WeatherStationDataAdapter extends RecyclerView.Adapter<WeatherStationDataAdapter.ViewHolder>{

    private List<WeatherStationData>dataList;
    private LayoutInflater mInflater;

    public WeatherStationDataAdapter(List<WeatherStationData>data, Context context){
        this.dataList = data;
        this.mInflater = LayoutInflater.from(context);
    }


    // inflates the row layout from xml when needed
    @Override
    public WeatherStationDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.weatherstationdata_row, parent, false);
        return new WeatherStationDataAdapter.ViewHolder(view);
    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WeatherStationData data = dataList.get(position);
        holder.name.setText(data.getProperties().getName());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public ViewHolder(View v){
            super(v);

            name = v.findViewById(R.id.stationname);
        }
    }

}
