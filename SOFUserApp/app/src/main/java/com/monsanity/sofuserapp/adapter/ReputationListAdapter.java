package com.monsanity.sofuserapp.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monsanity.sofuserapp.R;
import com.monsanity.sofuserapp.retrofit.response.ReputationDetailItem;
import com.monsanity.sofuserapp.utils.Utils;

import java.util.List;

public class ReputationListAdapter extends RecyclerView.Adapter<ReputationListAdapter.ViewHolder> {

    private Activity mActivity;
    private List<ReputationDetailItem> mReputationList;

    public ReputationListAdapter(Activity mActivity, List<ReputationDetailItem> reputationList) {
        this.mActivity = mActivity;
        this.mReputationList = reputationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.reputation_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ReputationDetailItem item = mReputationList.get(i);
        viewHolder.tvPostId.setText(String.format(
                mActivity.getResources().getString(R.string.reputation_post_id),
                item.getPostId()));
        viewHolder.tvChange.setText(String.format(
                mActivity.getResources().getString(R.string.reputation_change),
                item.getReputationChange()));
        viewHolder.tvCreatedAt.setText(String.format(
                mActivity.getResources().getString(R.string.reputation_created_at),
                Utils.getFormattedDate(item.getCreationDate())));

        String reputationType = item.getReputationHistoryType().replace("_", " ");
        viewHolder.tvReputationType.setText(reputationType);
    }

    @Override
    public int getItemCount() {
        return mReputationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPostId, tvReputationType, tvChange, tvCreatedAt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPostId = itemView.findViewById(R.id.tv_post_id);
            tvReputationType = itemView.findViewById(R.id.tv_reputation_type);
            tvChange = itemView.findViewById(R.id.tv_reputation_change);
            tvCreatedAt = itemView.findViewById(R.id.tv_reputation_created_at);
        }
    }

}
