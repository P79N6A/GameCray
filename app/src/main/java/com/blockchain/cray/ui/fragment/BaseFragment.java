package com.blockchain.cray.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blockchain.cray.R;
import com.blockchain.cray.net.HttpCommand;
import com.blockchain.cray.ui.view.LoadingView;
import com.blockchain.cray.utils.SharedPreferencesUtil;

import java.util.Objects;

public abstract class BaseFragment extends Fragment{
    
    protected Activity mActivity;
    protected HttpCommand httpCommand;
    protected LoadingView mLoadView;
    protected SharedPreferencesUtil sp;

    public BaseFragment(){
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        httpCommand = HttpCommand.getInstance();
        httpCommand.setContext(mActivity);
        sp = SharedPreferencesUtil.getInstance(getActivity());
        mLoadView = new LoadingView(mActivity);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = initView(inflater);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null !=mLoadView && mLoadView.isShowing()) {
            mLoadView.onDismiss();
            mLoadView = null;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private Bundle encryptData(Bundle bundle){
        return bundle;
    }


    protected void showToast(final String msg){
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
            }
        });

    }
    
    public abstract View initView(LayoutInflater inflater);

    public void initData() {

    }
}
