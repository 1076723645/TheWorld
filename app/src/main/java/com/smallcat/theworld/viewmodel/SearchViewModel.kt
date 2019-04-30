package com.smallcat.theworld.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.MutableLiveData
import com.smallcat.theworld.model.db.Equip


class SearchViewModel : ViewModel() {

    private var mValue: MutableLiveData<MutableList<Equip>> = MutableLiveData()


    fun getValue(): MutableLiveData<MutableList<Equip>>? {
        return mValue
    }

    fun setValue(data: MutableList<Equip>){
        mValue.value = data
    }

    fun getEquipList(): MutableList<Equip> {
        if (mValue.value == null){
            return ArrayList()
        }
        return mValue.value!!
    }

    fun postValue(data: MutableList<Equip>){
        mValue.postValue(data)
    }

}