package com.wirama.model

class ExampleRecyclerViewModel(private var title: String, private var date: String) {
    public fun getTitle(): String {
        return this.title
    }

    public fun setTitle(title: String) {
        this.title = title
    }

    public fun getDate(): String {
        return this.date
    }

    public fun setDate(date: String) {
        this.date = date
    }
}