/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsrealestate.network.Channel_data
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsProperty
import com.example.android.marsrealestate.network.Movement
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    private val _movement = MutableLiveData<String>()
    val movement: LiveData<String>
        get() = _movement
    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getMarsRealEstateProperties() {
        //_response.value = ""

        viewModelScope.launch {
            try {
                val listResult = MarsApi.retrofitService.getProperties()
                _response.value =
                    "Success: ${listResult} Mars properties retrieved"

                _movement.value = "Last Movement was detected at: ${listResult.channel.updated_at}"
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }

//            MarsApi.retrofitService.getProperties().enqueue(
//            object: Callback<MarsProperty> {
//                override fun onResponse(call: Call<MarsProperty> , response: Response<MarsProperty> ) {
//                    _response.value = "Success: ${response.body()} Mars properties retrieved"
//                }
//
//                override fun onFailure(call: Call<MarsProperty> , t: Throwable) {
//                    _response.value = "Failure: " + t.message
//                }
//
//            })
    }
}
