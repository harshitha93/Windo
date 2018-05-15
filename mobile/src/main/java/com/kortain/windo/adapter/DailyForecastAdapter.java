package com.kortain.windo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kortain.windo.R;
import com.kortain.windo.model.ForecastModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastAdapter.ViewHolder> {

    private Context mContext;
    private List<ForecastModel> mData;

    public DailyForecastAdapter(Context mContext, List<ForecastModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public DailyForecastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_daily_forecast, parent, false));
    }

    @Override
    public void onBindViewHolder(DailyForecastAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ldf_time)
        TextView time;
        @BindView(R.id.ldf_temp)
        TextView temperature;
        @BindView(R.id.ldf_icon)
        ImageView icon;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int position) {

            ForecastModel forecastModel = mData.get(position);

            if (forecastModel != null) {

                time.setText(forecastModel.getTime());
                temperature.setText(forecastModel.getCurrentTemperature());
                icon.setImageDrawable(forecastModel.getIcon());
            }
        }
    }
}
