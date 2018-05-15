package com.kortain.windo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kortain.windo.R;
import com.kortain.windo.model.LocationModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {

    public interface LocationAdapterCallbacks {
        void onAddNewItemClicked();
    }

    private Context mContext;
    private List<LocationModel> mDataList;
    private LocationAdapterCallbacks mCallbacks;

    public LocationsAdapter(LocationAdapterCallbacks callbacks, Context mContext, List<LocationModel> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
        this.mCallbacks = callbacks;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataList.get(position).isLabel())
            return 1;
        else
            return 2;
    }

    @Override
    public LocationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        switch (viewType) {
            case 1: {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_add_location, parent, false);
                break;
            }

            case 2: {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_location, parent, false);
                break;
            }
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_location, parent, false);
        }

        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(LocationsAdapter.ViewHolder holder, int position) {
        if (position > 0)
            holder.bind(position);
        else
            holder.setOnClickListener();
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_current_temperature)
        TextView mCurrentTemperature;
        @BindView(R.id.ll_current_time)
        TextView mCurrentTime;
        @BindView(R.id.ll_location)
        TextView mLocation;
        @BindView(R.id.ll_location_icon)
        ImageView mCurrentLocationIcon;

        View view;

        ViewHolder(View itemView, int viewType) {
            super(itemView);
            view = itemView;
            if (viewType == 2)
                ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        void bind(int position) {

            LocationModel data = mDataList.get(position);

            mCurrentTemperature.setText(data.getTemperature());
            mCurrentTime.setText(data.getTime());
            mLocation.setText(String.valueOf(data.getPlace().charAt(0)).toUpperCase()+data.getPlace().substring(1, data.getPlace().length()));

            if (data.isCurrentLocation())
                mCurrentLocationIcon.setVisibility(View.VISIBLE);
            else
                mCurrentLocationIcon.setVisibility(View.GONE);

        }

        void setOnClickListener() {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCallbacks != null)
                        mCallbacks.onAddNewItemClicked();
                }
            });
        }
    }
}
