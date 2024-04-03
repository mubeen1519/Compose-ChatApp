package com.example.domain.use_case.message_use_case

import com.example.domain.model.PushNotification
import com.example.domain.repository.MessageRepository
import javax.inject.Inject

class MessageNotificationUseCase @Inject constructor(private val messageRepository: MessageRepository) {

    suspend operator fun invoke (notification: PushNotification){
        messageRepository.sendPushNotification(notification)
    }
}