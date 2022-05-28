package com.up42.codingchallenge

import com.up42.codingchallenge.domain.FeatureCollection
import com.up42.codingchallenge.service.FeaturesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/features")
class FeaturesController(@Autowired var featureService: FeaturesService) {

    @GetMapping
    fun getFeatures(): ResponseEntity<List<FeatureCollection.Feature>> {
        return ResponseEntity(featureService.getAllFeatures(), HttpStatus.OK)
    }

}
