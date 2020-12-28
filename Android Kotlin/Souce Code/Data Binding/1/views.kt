
//==== Text
android:text="@{viewmodel.totalOrderCountLD.toString()}"
android:text="@{`Order Count: (`+viewmodel.totalOrderCountLD.toString()+`)`}"

//==== Click
android:onClick="@={viewmodel.onFabClick}"

//==== visibility
android:visibility="@{viewmodel.rvVisibilityLD? View.VISIBLE:View.GONE}"