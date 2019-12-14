package com.monsanity.sofuserapp.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.monsanity.sofuserapp.IBookmarkUserListener;
import com.monsanity.sofuserapp.IOnClickUserListener;
import com.monsanity.sofuserapp.R;
import com.monsanity.sofuserapp.adapter.ReputationListAdapter;
import com.monsanity.sofuserapp.adapter.UserListAdapter;
import com.monsanity.sofuserapp.retrofit.ApiUtils;
import com.monsanity.sofuserapp.retrofit.response.ReputationDetailItem;
import com.monsanity.sofuserapp.retrofit.response.ReputationItem;
import com.monsanity.sofuserapp.retrofit.response.UserDetailItem;
import com.monsanity.sofuserapp.retrofit.response.UserListItem;
import com.monsanity.sofuserapp.utils.Constant;
import com.monsanity.sofuserapp.utils.TinyDB;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

public class MainActivity extends AppCompatActivity implements IBookmarkUserListener, IOnClickUserListener {

    private RecyclerView mRvUser;
    private UserListAdapter mUserListAdapter;
    private LinearLayoutManager mLayoutManager;
    private Spinner mSpFilter;
    private ProgressBar mPbLoading;
    private TextView mTvEmpty;
    private int mUserPageIndex, mReputationPageIndex, mPageSize;
    boolean mIsLoadingUser = false, mIsLoadingReputation = false;
    private TinyDB mTinyDB;
    private ArrayList<Integer> mBookmarkedIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mPbLoading = findViewById(R.id.pb_loading);
        mSpFilter = findViewById(R.id.sp_filter);
        mTvEmpty = findViewById(R.id.tv_empty);
        mRvUser = findViewById(R.id.rv_user);
    }

    private void initData() {
        mTinyDB = new TinyDB(this);
        mBookmarkedIdList = new ArrayList<>();
        mBookmarkedIdList.addAll(mTinyDB.getListInt(Constant.KEY_BOOKMARKED_LIST));
        mUserPageIndex = 1;
        mPageSize = 30;
        mLayoutManager = new LinearLayoutManager(this);
        mRvUser.setLayoutManager(mLayoutManager);
        List<UserDetailItem> userList = new ArrayList<>();
        mUserListAdapter = new UserListAdapter(
                this,
                userList,
                this,
                this);
        mRvUser.setAdapter(mUserListAdapter);
        mRvUser.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL));

        mRvUser.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = mLayoutManager.findLastVisibleItemPosition();
                if (newState == SCROLL_STATE_IDLE && lastPosition >= mUserListAdapter.getItemCount() - 1) {
                    if (!mIsLoadingUser) {
                        mUserPageIndex++;
                        getUserList(false, false);
                        mIsLoadingUser = true;
                    }
                }
            }
        });

        mSpFilter.setSelection(0);
        mSpFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getUserList(true, true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getUserList(final boolean isClear, boolean isShowLoading) {
        if (isShowLoading) mPbLoading.setVisibility(View.VISIBLE);
        if (isClear) {
            mUserPageIndex = 1;
            mUserListAdapter.clearData();
        }
        ApiUtils.getRetrofitService()
                .doGetUserList(mUserPageIndex, mPageSize, Constant.SITE)
                .enqueue(new Callback<UserListItem>() {
            @Override
            public void onResponse(Call<UserListItem> call, Response<UserListItem> response) {
                if (response.body() != null) {
                    List<UserDetailItem> list = response.body().getUserDetailItems();
                    // Check if bookmark list needed
                    if (mSpFilter.getSelectedItemPosition() == 1) {
                        List<UserDetailItem> filteredList = getBookmarkedList(list);
                        mUserListAdapter.setUserList(filteredList);
                        // Check if bookmark list is loaded completely
                        if (mUserListAdapter.getItemCount() < mBookmarkedIdList.size()) {
                            mUserPageIndex++;
                            getUserList(false, true);
                        }
                    } else {
                        mUserListAdapter.setUserList(list);
                        checkBookmark();
                    }
                }

                if (mUserListAdapter.getItemCount() == 0) {
                    mTvEmpty.setVisibility(View.VISIBLE);
                }

                mPbLoading.setVisibility(View.GONE);
                mIsLoadingUser = false;
            }

            @Override
            public void onFailure(Call<UserListItem> call, Throwable t) {
                mPbLoading.setVisibility(View.GONE);
                mIsLoadingUser = false;
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUserReputation(final int userId, final String name, boolean isShowLoading) {
        if (isShowLoading) mPbLoading.setVisibility(View.VISIBLE);
        ApiUtils.getRetrofitService()
                .doGetUserReputation(userId, mReputationPageIndex, mPageSize, Constant.SITE)
                .enqueue(new Callback<ReputationItem>() {
                    @Override
                    public void onResponse(Call<ReputationItem> call, Response<ReputationItem> response) {
                        if (response.body() != null) {
                            showReputationListDialog(userId, name, response.body().getReputationDetailItems());
                        }
                        mPbLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ReputationItem> call, Throwable t) {
                        mPbLoading.setVisibility(View.GONE);
                        mIsLoadingUser = false;
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onAddUser(int position) {
        bookmarkUser(position, true);
    }

    @Override
    public void onRemoveUser(int position) {
        confirmRemoveBookmark(position);
    }

    @Override
    public void onClickUser(int userId, String name) {
        mReputationPageIndex = 1;
        getUserReputation(userId, name, true);
    }

    private void bookmarkUser(int position, boolean isAdd) {
        if (isAdd) {
            mUserListAdapter.getUserList().get(position).setBookmarked(true);
            mBookmarkedIdList.add(mUserListAdapter.getUserList().get(position).getUserId());
            mTinyDB.putListInt(Constant.KEY_BOOKMARKED_LIST, mBookmarkedIdList);
        } else {
            mUserListAdapter.getUserList().get(position).setBookmarked(false);
            if (mSpFilter.getSelectedItemPosition() == 1)
                mUserListAdapter.removeItem(position);
            mTinyDB.putListInt(Constant.KEY_BOOKMARKED_LIST, mBookmarkedIdList);
        }
        mUserListAdapter.notifyDataSetChanged();
    }

    private void checkBookmark() {
        for (UserDetailItem item : mUserListAdapter.getUserList()) {
            for (int id : mBookmarkedIdList) {
                if (id == item.getUserId()) {
                    item.setBookmarked(true);
                }
            }
        }
    }

    private List<UserDetailItem> getBookmarkedList(List<UserDetailItem> list) {
        List<UserDetailItem> filteredList = new ArrayList<>();
        for (UserDetailItem item : list) {
            for (int id : mBookmarkedIdList) {
                if (id == item.getUserId()) {
                    item.setBookmarked(true);
                    filteredList.add(item);
                }
            }
            if (filteredList.size() != mBookmarkedIdList.size()) {

            }
        }
        return filteredList;
    }

    private void confirmRemoveBookmark(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.confirm_remove));
        builder.setCancelable(true);

        builder.setPositiveButton(
                getResources().getString(R.string.confirm_yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        removeBookmark(position);
                    }
                });

        builder.setNegativeButton(
                getResources().getString(R.string.confirm_cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void removeBookmark(int position) {
        UserDetailItem item = mUserListAdapter.getUserList().get(position);
        for (Integer id : mBookmarkedIdList) {
            if (id.equals(item.getUserId())) {
                mBookmarkedIdList.remove(id);
                bookmarkUser(position, false);
                break;
            }
        }
    }

    private void showReputationListDialog(final int userId, final String name,
                                          List<ReputationDetailItem> reputationList) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.reputation_layout);
        dialog.setCancelable(true);

        TextView tvName = dialog.findViewById(R.id.tv_reputation_name);
        tvName.setText(name);
        RecyclerView rvReputation = dialog.findViewById(R.id.rv_reputation);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        final ReputationListAdapter adapter = new ReputationListAdapter(this, reputationList);
        rvReputation.setAdapter(adapter);
        rvReputation.setLayoutManager(layoutManager);
        rvReputation.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL));

        rvReputation.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = layoutManager.findLastVisibleItemPosition();
                if (newState == SCROLL_STATE_IDLE && lastPosition >= adapter.getItemCount() - 1) {
                    if (!mIsLoadingReputation) {
                        mUserPageIndex++;
                        getUserReputation(userId, name, false);
                        mIsLoadingReputation = true;
                    }
                }
            }
        });

        dialog.show();
    }

}
