package com.ito.ibms.db.model;

import java.util.Date;

public class CourseType {
    public int mId = -1;
    public String mName = null;
    public Date mStart = null;
    public Date mEnd = null;
    public CourseType mRequired = null;

    public String toString() {
        return mName;
    }
}
