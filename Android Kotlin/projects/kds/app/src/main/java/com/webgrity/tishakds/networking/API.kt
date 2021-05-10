package com.webgrity.tishakds.networking

object API {
    var serverIp = "0.0.0.0"
    var serverPort = 8000

    //connection names
    const val connection = "connection"
    const val get_table = "gettable"
    const val login = "login"
    const val menu_categories = "menucategories"
    const val sales_categories = "salescategories"
    const val menu_items = "menuitems"
    const val menu_item_attribute_category = "menuitemattributecategory"
    const val attribute_categories = "attributecategories"
    const val attribute_categories_values = "attributecategoriesvalues"
    const val taxes = "taxes"
    const val menu_category_taxes = "menucategorytaxes"
    const val place_order = "placeorder"
    const val accept_order = "acceptorder"
    const val request_for_bill = "requestforbill"
    const val post_request_for_bill = "postrequestforbill"
    const val order_closed = "orderclosed"
    const val feedback = "feedback"
    const val waiter_login = "waiterlogin"
    const val get_order = "getorder"
    const val print_order = "printorder"
    const val menu_item_image = "menuitemimage"
    const val booking_details ="bookingdetails"
    const val submit_booking ="submitbooking"
    const val delete_booking = "deletebooking"
    const val customer_mobile_data ="api/customer-data-by-mobile"
    const val table_booking_sms ="api/table-booking-sms"
}