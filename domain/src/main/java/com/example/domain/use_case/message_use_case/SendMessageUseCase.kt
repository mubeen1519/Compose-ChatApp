package com.example.domain.use_case.message_use_case


import com.example.domain.model.UsersModel
import com.example.domain.repository.MessageRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val messageRepository: MessageRepository) {


    operator fun invoke(
        chatId: String,
        messageSenderId: String?,
        messageReceiverId: String?,
        messageSenderName: String?,
        messageReceiverName: String?,
        messageText: String?,
        messageType : String? = null
    ) =
        messageRepository.sendMessage(
            chatId,
            messageSenderId,
            messageReceiverId,
            messageSenderName,
            messageReceiverName,
            messageText,
            messageType
        )


}