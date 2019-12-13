package com.monsanity.sofuserapp.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.monsanity.sofuserapp.IBookmarkUserListener;
import com.monsanity.sofuserapp.R;
import com.monsanity.sofuserapp.retrofit.response.UserDetailItem;
import com.monsanity.sofuserapp.utils.Constant;
import java.util.Calendar;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private Activity mActivity;
    private List<UserDetailItem> mUserList;
    private IBookmarkUserListener mBookmarkListener;

    public UserListAdapter(Activity mActivity, List<UserDetailItem> userList,
                           IBookmarkUserListener bookmarkListener) {
        this.mActivity = mActivity;
        this.mUserList = userList;
        this.mBookmarkListener = bookmarkListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.user_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        UserDetailItem item = mUserList.get(i);
        viewHolder.tvName.setText(item.getDisplayName());
        viewHolder.tvReputation.setText(String.format(
                mActivity.getResources().getString(R.string.reputation),
                item.getReputation()));
        viewHolder.tvLocation.setText(item.getLocation());
        viewHolder.tvLastAccess.setText(String.format(
                mActivity.getResources().getString(R.string.access_date),
                getFormattedDate(item.getLastAccessDate())));
        Glide.with(mActivity)
                .load(item.getProfileImage())
                .into(viewHolder.ivAvatar);
        Glide.with(mActivity)
                .load(item.isBookmarked() ?
                        mActivity.getResources().getDrawable(R.drawable.yellow_star) :
                        mActivity.getResources().getDrawable(R.drawable.empty_star))
                .into(viewHolder.ivBookMark);
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public List<UserDetailItem> getUserList() {
        return mUserList;
    }

    public void setUserList(List<UserDetailItem> userList) {
        mUserList.addAll(userList);
        notifyDataSetChanged();
    }

    public void addUserItems(List<UserDetailItem> userList) {
        mUserList.addAll(userList);
    }

    public void clearData() {
        mUserList.clear();
    }

    public void removeItem(int position) {
        mUserList.remove(position);
    }

    private void bookmarkUser(int position) {
        if (mUserList.get(position).isBookmarked()) {
            mBookmarkListener.onRemoveUser(position);
        } else {
            mBookmarkListener.onAddUser(position);
        }
    }

    private String getFormattedDate(Long timeStamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStamp * 1000L);
        return DateFormat.format(Constant.DATE_FORMAT, cal).toString();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivAvatar, ivBookMark;
        private TextView tvName, tvReputation, tvLocation, tvLastAccess;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            ivBookMark = itemView.findViewById(R.id.iv_bookmark);
            tvName = itemView.findViewById(R.id.tv_name);
            tvReputation = itemView.findViewById(R.id.tv_reputation);
            tvLocation = itemView.findViewById(R.id.tv_location);
            tvLastAccess = itemView.findViewById(R.id.tv_last_access);
            itemView.setOnClickListener(this);
            ivBookMark.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_bookmark:
                    bookmarkUser(getAdapterPosition());
                    break;
                default:

                    break;
            }
        }
    }

}
