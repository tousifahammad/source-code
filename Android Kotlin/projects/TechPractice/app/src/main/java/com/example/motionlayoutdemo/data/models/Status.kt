package com.example.motionlayoutdemo.data.models

class Status {
    companion object {
        const val active = 1
        const val deleted = 2
        const val hidden = 3

        const val seated = 1 //means order currently running
        const val takeout = 2
        const val onlineSeated = 3
        const val onlineWebSeated = 4
        const val onlineWebTakeout = 5
        const val closed = 3
        const val onlineOrder = 4

        const val floorPlanScreen = "Floorplan"
        const val registerScreen = "Register"

        const val activeRestaurant = 1
        const val trialRestaurant = 2
        const val sampleRestaurant = 3

        const val createTable = 0

        const val noRefund = 1
        const val refunded = 2
        const val postRefund = 3

        const val accepted = 1
        const val rejected = 2
        const val pending = 3

        const val billDefault = 1
        const val billRequested = 2
        const val billPrinted = 3

        const val table = 1
        const val waiter = 2

        const val merge = 1
        const val move = 3

        const val payIn = 1
        const val payOut = 2


        const val checkoutInvoice = 1
        const val registerInvoice = 2
        const val billHistoryInvoice = 3

    }
}