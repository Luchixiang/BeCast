package com.example.common.utils;

public class ChangeTime {
        public static String calculateTime(String timee) {
            int minute;
            int second;
            if (timee.contains(":")) {
                return timee;
            } else {
                int time = Integer.parseInt(timee);
                if (time >= 60) {
                    minute = time / 60;
                    second = time % 60;

                    if (second < 10) {
                        return minute + ":" + "0" + second;
                    } else {
                        return minute + ":" + second;
                    }

                } else {
                    second = time;
                    if (second < 10) {
                        return "0" + ":" + "0" + second;
                    } else {
                        return "0" + ":" + second;
                    }
                }
            }
        }
        public static long changTimeintoLong(String time){
            if (time.contains(":"))
            {
                String[] tempt = time.split(":");
                long timeLong = 0;
                int multipe = 1;
                for (int i = tempt.length-1;i>=0;i--){
                    timeLong = Integer.parseInt(tempt[i])*multipe + timeLong;
                    multipe = multipe*60;
                }
                return timeLong;
            }
            return Long.parseLong(time);
        }
}
