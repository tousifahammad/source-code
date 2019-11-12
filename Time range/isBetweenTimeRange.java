    private void setTimeSlot() {
        booked_time_list.add("10:01-11:00");
        booked_time_list.add("11:01-12:00");
        booked_time_list.add("12:01-13:00");
        booked_time_list.add("13:01-14:00");
        booked_time_list.add("14:01-15:00");
        booked_time_list.add("15:01-16:00");

        try {
            for (String time : booked_time_list) {
                View myLayout = inflater.inflate(R.layout.row_time_slot, mainLayout, false);
                TextView tv_time = myLayout.findViewById(R.id.tv_time);

                tv_time.setText(time);
                tv_time.setOnClickListener(view -> {
                    //tv_time.setBackgroundResource(R.drawable.round_border_green);

                    //String now = DateUtil.getCurrentHour();
                    String now = time;
                    String start = "12:01";
                    String end = "13:26";

                    //Log.d("1111", now + " between " + start + "-" + end + "?");
                    //Log.d("1111", "" + isHourInInterval(now, start, end));


                    String time_1;
                    String time_2;

                    time_1 = time.split("-")[0];
                    time_2 = time.split("-")[1];
                    Log.d("1111", "isBothTimeInInterval? " + isBothTimeInInterval(time_1, time_2, start, end));

                    Log.d("1111", time_1 + " between " + start + "-" + end + "?");
                    Log.d("1111", "" + isHourInInterval(time_1,  start, end));

                    Log.d("1111", time_2 + " between " + start + "-" + end + "?");
                    Log.d("1111", "" + isHourInInterval(time_2,  start, end));
                });

                mainLayout.addView(myLayout);
            }

        } catch (Exception e) {
            Alert.showError(this, e.getMessage());
        }


    }



    public static boolean isHourInInterval(String target, String start, String end) {
        return ((target.compareTo(start) >= 0) && (target.compareTo(end) <= 0));
    }

    public static boolean isBothTimeInInterval(String time_1, String time_2, String start, String end) {
        return (
                (time_1.compareTo(start) >= 0) && (time_1.compareTo(end) <= 0)
                        && (time_2.compareTo(start) >= 0) && (time_2.compareTo(end) <= 0)
        );
    }
