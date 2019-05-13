package com.plugow.squanchyapp.ui.adapter

interface BindableAdapter<in T> {
    fun setData(items:List<T>)
}