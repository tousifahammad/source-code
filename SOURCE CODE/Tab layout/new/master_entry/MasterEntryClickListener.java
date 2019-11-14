package com.app.cubeapparels.master_entry;

public interface MasterEntryClickListener {
    public void onCancelClicked(String booking_id);

    public void onModifyClicked(String booking_id);

    public void onRemainderChanged(boolean isChecked, String booking_id);
}
