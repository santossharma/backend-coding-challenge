package com.up42.codingchallenge.service.datareader

import com.up42.codingchallenge.domain.FeatureCollection
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

/**
 * Created by santoshsharma on 28 May, 2022
 */

@Service
@Qualifier("apiFeatureDataReader")
class APIFeatureDataReader : FeatureDataReader {
    override fun readFeatureData(): List<FeatureCollection.Feature> {
        TODO("feature data reads from API call")
    }
}