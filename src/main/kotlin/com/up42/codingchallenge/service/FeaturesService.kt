package com.up42.codingchallenge.service

import com.up42.codingchallenge.constant.FeatureConstants
import com.up42.codingchallenge.domain.FeatureCollection
import com.up42.codingchallenge.exception.FeatureNotFoundException
import com.up42.codingchallenge.service.datareader.FeatureDataReader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.io.FileNotFoundException

/**
 * Created by santoshsharma on 28 May, 2022
 */


/*
* This service class handles Feature requests.
* */
@Service
class FeaturesService (@Autowired @Qualifier("fileFeatureDataReader") var featureDataReader: FeatureDataReader) {

    /*
    * Loads all available features from [featureDataReader].
    *
    * @return Collection of Feature domain */
    fun getAllFeatures(): List<FeatureCollection.Feature> {
        val features: List<FeatureCollection.Feature>

        try {
            features = featureDataReader.readFeatureData()

        } catch (ex: FileNotFoundException) {
            throw FeatureNotFoundException(FeatureConstants.FEATURE_DATA_FILE_NOT_FOUND)
        } catch (ex: Exception) {
            throw ex
        }

        return features;
    }

}