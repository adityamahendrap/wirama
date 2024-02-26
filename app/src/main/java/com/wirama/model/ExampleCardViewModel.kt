package com.wirama.model

class ExampleCardViewModel(
    private var name: String,
    private var thumbnail: Int,
) {

    fun getName(): String {
        return this.name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getThumbnail(): Int {
        return  this.thumbnail
    }

    fun setThumbnail(thumbnail: Int) {
        this.thumbnail = thumbnail
    }

}