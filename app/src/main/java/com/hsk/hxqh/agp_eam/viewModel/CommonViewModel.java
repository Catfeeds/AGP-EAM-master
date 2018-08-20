package com.hsk.hxqh.agp_eam.viewModel;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.hsk.hxqh.agp_eam.application.BaseApplication;
import com.hsk.hxqh.agp_eam.config.Constants;
import com.hsk.hxqh.agp_eam.model.DeleteResult;
import com.hsk.hxqh.agp_eam.model.Result3;
import com.hsk.hxqh.agp_eam.model.Result4;
import com.hsk.hxqh.agp_eam.unit.AccountUtils;
import com.hsk.hxqh.agp_eam.unit.TestUtil;
import com.rx2androidnetworking.Rx2AndroidNetworking;


import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.hsk.hxqh.agp_eam.config.Constants.BASE_URL;
import static com.hsk.hxqh.agp_eam.config.Constants.HTTP_API_IP;

/**
 * Created by wxs on 2018/1/4.
 */
public class CommonViewModel extends ViewModel {
    String ip_adress = AccountUtils.getIpAddress(BaseApplication.getContext()) + BASE_URL;

    public <T> MediatorLiveData<ArrayList<T>> getSelectLiveData(Context context, String data, final Class<T> tClass) {
        final MediatorLiveData<ArrayList<T>> resultData = new MediatorLiveData();

        //请求参数
        HashMap requestMap = new HashMap();
        requestMap.put("data", data);

        Rx2AndroidNetworking.get(ip_adress).addQueryParameter(requestMap)
                .build().getStringObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<String, ArrayList<T>>() {
                    @Override
                    public ArrayList<T> apply(String s) throws Exception {
                        Result3<T> result3 = null;
                        Result4<T> result4 = null;
                        ArrayList<T> arrayList = null;
                        try {
                            result3 = JSON.parseObject(s, new TypeReference<Result3<T>>() {});
                        }catch (Exception e) {
                            result4 = JSON.parseObject(s, new TypeReference<Result4<T>>() {});
                        }
                        if(TestUtil.isNotNull(result3)) {
                            arrayList = (ArrayList<T>) JSONArray.parseArray(result3.getResult().getResultlist().toString(), tClass);
                        }else if(TestUtil.isNotNull(result4)){
                            arrayList = (ArrayList<T>) JSONArray.parseArray(result4.getResult().toString(), tClass);
                        }
                        return arrayList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<T>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<T> arrayLists) {
                        resultData.postValue(arrayLists);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return resultData;
    }

    public <T> MediatorLiveData<DeleteResult<T>> getDeleteLiveData(Context context, String data, final Class<T> tClass) {
        final MediatorLiveData<DeleteResult<T>> resultData = new MediatorLiveData();

        //请求参数
        HashMap requestMap = new HashMap();
        requestMap.put("data", data);

        Rx2AndroidNetworking.post(ip_adress).addQueryParameter(requestMap)
                .build().getStringObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<String, DeleteResult<T>>() {
                    @Override
                    public DeleteResult<T> apply(String s) throws Exception {
                        DeleteResult<T> deleteResult = JSON.parseObject(s, new TypeReference<DeleteResult<T>>() {});;
                        return deleteResult;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DeleteResult<T>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DeleteResult<T> str) {
                        resultData.postValue(str);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return resultData;
    }

    public <V> MediatorLiveData<V> getSaveLiveData(Context context, String data, final Class<V> tClass) {
        final MediatorLiveData<V> resultData = new MediatorLiveData();

        //请求参数
        HashMap requestMap = new HashMap();
        requestMap.put("data", data);

        Rx2AndroidNetworking.post(ip_adress).addQueryParameter(requestMap)
                .build().getStringObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<String, V>() {
                    @Override
                    public V apply(String s) throws Exception {
                        V result = JSON.parseObject(s, tClass);
//                        Result3<T> result3 = JSON.parseObject(s, new TypeReference<Result3<T>>() {});
//                        ArrayList<T> arrayList = (ArrayList<T>) JSONArray.parseArray(result3.getResult().getResultlist().toString(), tClass);
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<V>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(V arrayLists) {
                        resultData.postValue(arrayLists);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return resultData;
    }


}
