<data>
    <import type="android.view.View" />
	<import type="com.webgrity.tisha.data.models.Shape" />

    <variable
        name="viewmodel"
        type="com.webgrity.tisha.ui.fragment.order_fab.OrderFabViewModel" />
</data>


//==== Text
android:text="@{viewmodel.totalOrderCountLD.toString()}"
android:text="@{`Order Count: (`+viewmodel.totalOrderCountLD.toString()+`)`}"
android:textColor="@{viewmodel.selected ? @color/White : @color/textColorPrimary}"


//==== Click
android:onClick="@{viewmodel.onFabClick}"

//==== visibility
android:visibility="@{viewmodel.rvVisibilityLD? View.VISIBLE:View.GONE}"
android:visibility="@{tableData.shape!=Shape.obstacle? View.VISIBLE:View.GONE}"