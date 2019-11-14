     public void ShowTimePicker() {
        RangeTimePickerDialog tpd = new RangeTimePickerDialog(this, new RangeTimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String time = selectedHour + ":" + selectedMinute;

                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                Date date = null;
                try {
                    date = fmt.parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                tv_booking_time.setText(fmtOut.format(date));
            }
        }, 10, 0, false);
        tpd.setMin(10, 0);
        tpd.setMax(16, 20);
        tpd.show();
    }