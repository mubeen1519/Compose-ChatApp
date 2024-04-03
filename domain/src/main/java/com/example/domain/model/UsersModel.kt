package com.example.domain.model

import javax.inject.Inject


class UsersModel{
    var name: String? = null
    var email: String? = null
    var userId: String? = null
    var type: String? = null
    var lastSeen: Any? = null
    var imageProfile:String? = null
    var fcmToken : String? = null

    @Inject
    constructor(name: String?, email: String?, userId: String?, type: String?,lastSeen: Any?,imageProfile:String?,fcmToken : String?) {
        this.name = name
        this.email = email
        this.userId = userId
        this.type = type
        this.lastSeen = lastSeen
        this.imageProfile = imageProfile
        this.fcmToken = fcmToken
    }


    @Inject
    constructor() {}

}