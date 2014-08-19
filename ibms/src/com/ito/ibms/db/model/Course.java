package com.ito.ibms.db.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Course {
    protected static final SimpleDateFormat sDateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    public int mId = -1;
    public CourseType mType = null;
    public int mVersion = 0;
    public double mSubscribeValue = 0;
    public double mBookValue = 0;
    public int mTotalClasses = 0;
    public Date mStart = new Date();
    public Date mEnd = new Date();
    public boolean[] mWeekDays = new boolean[7];
    public List<Date> mClassList = new ArrayList<Date>();
    public Date mDeactivate = null;

    public int getWeekDays() {
        int result = 0;
        // Sunday
        if (mWeekDays[0]) {
            result = result | 0x0001;
        }
        // Monday
        if (mWeekDays[1]) {
            result = result | 0x0002;
        }
        // Tuesday
        if (mWeekDays[2]) {
            result = result | 0x0004;
        }
        // Wednesday
        if (mWeekDays[3]) {
            result = result | 0x0008;
        }
        // Thursday
        if (mWeekDays[4]) {
            result = result | 0x0010;
        }
        // Friday
        if (mWeekDays[5]) {
            result = result | 0x0020;
        }
        // Saturday
        if (mWeekDays[6]) {
            result = result | 0x0040;
        }
        return result;
    }

    public void setWeekDays(int value) {
        // Sunday
        if ((value & 0x0001) > 0) {
            mWeekDays[0] = true;
        }
        // Monday
        if ((value & 0x0002) > 0) {
            mWeekDays[1] = true;
        }
        // Tuesday
        if ((value & 0x0004) > 0) {
            mWeekDays[2] = true;
        }
        // Wednesday
        if ((value & 0x0008) > 0) {
            mWeekDays[3] = true;
        }
        // Thursday
        if ((value & 0x0010) > 0) {
            mWeekDays[4] = true;
        }
        // Friday
        if ((value & 0x0020) > 0) {
            mWeekDays[5] = true;
        }
        // Saturday
        if ((value & 0x0040) > 0) {
            mWeekDays[6] = true;
        }
    }

    public String getClassDays() {
        StringBuilder result = new StringBuilder();
        for (int i=0; i<mTotalClasses; i++) {
            if (mClassList.size() < i) {
                break;
            }
            result.append(sDateFormatter.format(mClassList.get(i)) + ";");
        }
        result.deleteCharAt(result.length()-1);
        return result.toString();
    }

    public void setClassDays(String value) {
        mClassList.clear();
        String[] list = value.split(";");
        for (String item : list) {
            try {
                mClassList.add(sDateFormatter.parse(item));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public String toString() {
        if (mType == null) {
            return "-";
        }

        return mType.mName + " - " + sDateFormatter.format(mStart) + " - " + sDateFormatter.format(mEnd);
    }
}
