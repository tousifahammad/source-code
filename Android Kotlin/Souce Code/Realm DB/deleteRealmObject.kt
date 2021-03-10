 
    realm.executeTransaction { r: Realm ->
        r.delete(OnlineInvoice::class.java)
        r.delete(OnlineOrderDetails::class.java)
        r.delete(OnlineOrderDetailsMenuItemAttribute::class.java)
        r.delete(OnlineOrderTaxesTotal::class.java)
    }