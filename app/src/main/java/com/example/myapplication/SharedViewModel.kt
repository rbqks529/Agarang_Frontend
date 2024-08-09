package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// UI 관련 데이터를 저장하고 관리해주는 아키텍처

class SharedViewModel:ViewModel() {

    // 아이 정보를 위한 데이터 입니다.
    // 1. 태명 2. 예정일 3. 체중 4. 가족 코드 5. 가족 역할
    private val _babyName=MutableLiveData<String>()
    val babyName: LiveData<String> get() =_babyName

    private val _dueDate=MutableLiveData<String>()
    val dueDate: LiveData<String> get() =_dueDate

    private val _babyWeight=MutableLiveData<String>()
    val babyWeight: LiveData<String> get() =_babyWeight

    private val _familyCode=MutableLiveData<String>()
    val familyCode: LiveData<String> get() =_familyCode

    private val _familyRole=MutableLiveData<String>()
    val familyRole: LiveData<String> get() =_familyRole

    private val _babyDday=MutableLiveData<Int>()
    val babyDday: LiveData<Int> get() =_babyDday

    fun setBabyName(babyName:String){
        _babyName.value=babyName
    }

    fun setDueDate(dueDate:String){
        _dueDate.value=dueDate
    }

    fun setBabyWeight(babyWeight:String){
        _babyWeight.value=babyWeight
    }

    fun setFamilyCode(familyCode:String){
        _familyCode.value=familyCode
    }

    fun setFamilyRole(familyRole:String){
        _familyRole.value=familyRole
    }

    fun setBabyDDay(babyDday:Int){
        _babyDday.value=babyDday
    }


    // 음악 선정을 위한 데이터입니다.
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