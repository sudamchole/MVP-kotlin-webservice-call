package com.versionsolution.kotlindemo.model

class Category {
    var name: String? = null
    var image: Int? = null

    constructor(name: String, image: Int) {
        this.name = name
        this.image = image
    }
}