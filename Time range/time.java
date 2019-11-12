    private String getTimeFromMinute(int mint) {
        String time = "00:00";
        try {
            int hours = mint / 60; //since both are ints, you get an int
            int minutes = mint % 60;
            time = hours + ":" + minutes;
        } catch (Exception e) {
            Alert.showError(this, e.getMessage());
        }

        return time;
    }

======================================================================================

    private int getMinutesFromTime(String time) {
        int total_mint = 0;
        try {
            Date date = hh_mm_aa_formater.parse(time);
            time = hh_mm_formater.format(date);

            int hour = Integer.parseInt(time.split(":")[0]);
            int minute = Integer.parseInt(time.split(":")[1]);

            total_mint = hour * 60 + minute;

        } catch (ParseException e) {
            Alert.showError(this, e.getMessage());
        }

        return total_mint;
    }

==========================================================================================

    private String getTimeFromMinute(int mint) {
        String time = "00:00";

        SimpleDateFormat sdf = new SimpleDateFormat("mm");

        try {
            Date dt = sdf.parse(String.valueOf(mint));
            sdf = new SimpleDateFormat("HH:mm");
            time = sdf.format(dt);
        } catch (ParseException e) {
            Alert.showError(this, e.getMessage());
        }

        return time;
    }


==========================================================================================

    void setTimeSlot() {
        for (Service service : AppData.service.selected_service_list) {
            service_time = service_time + Integer.parseInt(service.getService_time());
        }

        int opening_minute = getMinutesFromTime(AppData.service.opening_time);
        int closing_minute = getMinutesFromTime(AppData.service.closing_time);

        int total_opening_time = (closing_minute - opening_minute);
        int slot_count = total_opening_time / service_time;

        mainLayout.removeAllViews();


        int from_time = opening_minute, to_time;
        for (int i = 1; i <= slot_count; i++) {
            from_time = from_time + service_interval;
            to_time = opening_minute + service_time * i;

            addTimeSlotView(getTimeFromMinute(from_time), getTimeFromMinute(to_time));

            from_time = to_time;
        }

    }
