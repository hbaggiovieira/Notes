package com.henriquevieira.notes.features.review

import android.app.Activity
import android.util.Log
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory

class InAppReview(
    private val activity: Activity,
) {
    fun requestInAppReview() {
        val manager: ReviewManager = ReviewManagerFactory.create(activity)

        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { request ->
            if (request.isSuccessful) {
                val reviewInfo = request.result
                val flow = manager.launchReviewFlow(activity, reviewInfo)
                flow.addOnCompleteListener { _ ->
                    Log.d("Review Status:", "Success")
                }
            } else {
                Log.d("Review Status:", "Error")
            }
        }
    }
}