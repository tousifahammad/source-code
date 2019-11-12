    private int getMinutesFrom12hrTime(String time) {
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

    ===============================================================================================

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

    ===============================================================================================

    public boolean isAnyTimeInInterval(String time_1, String time_2) {
        String start = "";
        String end = "";
        try {
            for (BookedService bookedService : booked_service_list) {
                start = bookedService.getStart_time();
                end = bookedService.getEnd_time();

                if ((time_1.compareTo(start) > 0) && (time_1.compareTo(end) < 0)
                        || (time_2.compareTo(start) > 0) && (time_2.compareTo(end) < 0)) {
                    //if any one true
                    return true;
                }
            }

        } catch (Exception e) {
            Alert.showError(this, e.getMessage());
        }

        return false;
    }

    ===============================================================================================

    public String convertTo12hrFormat(String time) {
        try {
            Date date = hh_mm_formater.parse(time);
            return hh_mm_aa_formater.format(date);
        } catch (Exception e) {
            Alert.showError(this, e.getMessage());
        }

        return null;
    }

    
    ===============================================================================================

    public String convertTo24hrFormat(String time) {
        try {
            Date date = hh_mm_aa_formater.parse(time);
            return hh_mm_formater.format(date);
        } catch (ParseException e) {
            Alert.showError(this, e.getMessage());
        }

        return null;
    }


    ===============================================================================================

    public boolean isHourInInterval(String target, String start, String end) {
        return ((target.compareTo(start) >= 0) && (target.compareTo(end) <= 0));
    }

    
    ===============================================================================================

    public boolean isBothTimeInInterval(String time_1, String time_2, String start, String end) {
        return (
                (time_1.compareTo(start) >= 0) && (time_1.compareTo(end) <= 0)
                        && (time_2.compareTo(start) >= 0) && (time_2.compareTo(end) <= 0)
        );
    }


    ===============================================================================================

    public boolean isAnyTimeInInterval(String time_1, String time_2, String start, String end) {
        return (
                (time_1.compareTo(start) >= 0) && (time_1.compareTo(end) <= 0)
                        || (time_2.compareTo(start) >= 0) && (time_2.compareTo(end) <= 0)
        );
    }

    ===============================================================================================
    public boolean isAnyTimeInInterval(String time_1, String time_2) {
        String start = "";
        String end = "";
        try {
            for (BookedService bookedService : booked_service_list) {
                start = bookedService.getStart_time();
                end = bookedService.getEnd_time();

                if ((time_1.compareTo(start) > 0) && (time_1.compareTo(end) < 0)
                        || (time_2.compareTo(start) > 0) && (time_2.compareTo(end) < 0)) {
                    //if any one true
                    return true;
                }
            }

        } catch (Exception e) {
            Alert.showError(this, e.getMessage());
        }

        return false;
    }
