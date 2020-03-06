package com.app.haircutuser.mybooking;

public interface BookingClickListener {
    public void onCancelClicked(String booking_id);

    public void onModifyClicked(String booking_id);

    public void onRemainderChanged(boolean isChecked, String booking_id);
}
