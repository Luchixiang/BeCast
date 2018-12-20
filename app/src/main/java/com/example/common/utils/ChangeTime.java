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
}
