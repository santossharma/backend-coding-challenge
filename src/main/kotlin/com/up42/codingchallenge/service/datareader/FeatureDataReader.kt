package com.up42.codingchallenge.service.datareader

import com.up42.codingchallenge.domain.FeatureCollection

/**
 * Created by santoshsharma on 28 May, 2022
 */

interface FeatureDataReader {
    fun readFeatureData(): List<FeatureCollection.Feature>
}