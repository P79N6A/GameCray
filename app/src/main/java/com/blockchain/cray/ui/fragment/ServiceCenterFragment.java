package com.blockchain.cray.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.blockchain.cray.R;

public class ServiceCenterFragment extends BaseFragment {

    public ServiceCenterFragment(){}
    
    @Override
    public View initView(LayoutInflater inflater) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_service_center, null);
        return view;
    }
}
