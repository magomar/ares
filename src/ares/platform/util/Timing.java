package ares.platform.util;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class Timing {

    /**
     * Get CPU time in nanoseconds.
     */
    public static long getCpuTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ?
                bean.getCurrentThreadCpuTime() : 0L;
    }

    /**
     * Get user time in nanoseconds.
     */
    public static long getUserTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ?
                bean.getCurrentThreadUserTime() : 0L;
    }

    /**
     * Get system time in nanoseconds.
     */
    public static long getSystemTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ?
                (bean.getCurrentThreadCpuTime() - bean.getCurrentThreadUserTime()) : 0L;
    }

    /**
     * Get CPU time in nanoseconds.
     */
    public static long getCpuTime(long[] ids) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        if (!bean.isThreadCpuTimeSupported())
            return 0L;
        long time = 0L;
        for (int i = 0; i < ids.length; i++) {
            long t = bean.getThreadCpuTime(ids[i]);
            if (t != -1)
                time += t;
        }
        return time;
    }

    /**
     * Get user time in nanoseconds.
     */
    public static long getUserTime(long[] ids) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        if (!bean.isThreadCpuTimeSupported())
            return 0L;
        long time = 0L;
        for (int i = 0; i < ids.length; i++) {
            long t = bean.getThreadUserTime(ids[i]);
            if (t != -1)
                time += t;
        }
        return time;
    }

    /**
     * Get system time in nanoseconds.
     */
    public static long getSystemTime(long[] ids) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        if (!bean.isThreadCpuTimeSupported())
            return 0L;
        long time = 0L;
        for (int i = 0; i < ids.length; i++) {
            long tc = bean.getThreadCpuTime(ids[i]);
            long tu = bean.getThreadUserTime(ids[i]);
            if (tc != -1 && tu != -1)
                time += (tc - tu);
        }
        return time;
    }
}
