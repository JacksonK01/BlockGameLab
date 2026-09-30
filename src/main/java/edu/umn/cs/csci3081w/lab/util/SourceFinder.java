package edu.umn.cs.csci3081w.lab.util;

import edu.umn.cs.csci3081w.lab.intr.Detectable;

import javax.annotation.Nullable;

public class SourceFinder {
    //Source: https://stackoverflow.com/questions/36585185/instance-of-t-generic-type-in-java
    @Nullable
    public static <T> T findSource(Object a, Object b, Class<T> type) {
        if(type.isInstance(a)) {
            return type.cast(a);
        } else if(type.isInstance(b)) {
            return type.cast(b);
        } else {
            return null;
        }
    }
}
