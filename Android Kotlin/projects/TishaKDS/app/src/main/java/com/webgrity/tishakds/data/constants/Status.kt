package com.webgrity.tishakds.data.constants

class Status {
    companion object {
        const val none = 0

        const val table = 1
        const val waiter = 2

        const val active = 1
        const val deleted = 2
        const val hidden = 3

        const val seated = 1
        const val takeout = 2
        const val closed = 3

        const val activeRestaurant = 1
        const val trialRestaurant = 2
        const val sampleRestaurant = 3

        const val placed = 1000
        const val accepted = 1
        const val rejected = 2
        const val pending = 3
        const val failed = 4

        const val billDefault = 1
        const val billRequested = 2
        const val billPrinted = 3
    }
}