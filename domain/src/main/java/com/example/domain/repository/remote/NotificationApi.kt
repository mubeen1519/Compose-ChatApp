package com.example.domain.repository.remote

import com.example.domain.model.PushNotification
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationApi {
    @Headers("Authorization:key=AAAAPSbUJlc:APA91bFI6luHYaxGglOzrHkQuvt-8AhYyPX5uPa7reWs2XEh7sUYL-2vDe2pRTCzhLjwnQvDnpE7_xCBj65prWFKgTE6YwojVbo-uMwk7eHtUgI5kORhACui69H3q3OCv171IvA9P7d5", "Content-Type:application/json")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification
    ): Response<ResponseBody>
}