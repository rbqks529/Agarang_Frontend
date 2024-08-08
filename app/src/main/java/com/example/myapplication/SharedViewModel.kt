package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// UI 관련 데이터를 저장하고 관리해주는 아키텍처

class SharedViewModel:ViewModel() {

    private val _instrument=MutableLiveData<String>()
    val instrument: LiveData<String> get() =_instrument //only read

    private val _genre=MutableLiveData<String>()
    val genre: LiveData<String> get() =_genre

    private val _mood=MutableLiveData<String>()
    val mood: LiveData<String> get() =_mood

    private val _tempo=MutableLiveData<String>()
    val tempo: LiveData<String> get() =_tempo

    fun setInstrument(instrument:String){
        _instrument.value=instrument.uppercase()
    }

    fun setGenre(genre:String){
        _genre.value=genre.uppercase()
    }

    fun setMood(mood:String){
        _mood.value=mood.uppercase()
    }

    fun setTempo(tempo:String){
        _tempo.value=tempo.uppercase()
    }

}