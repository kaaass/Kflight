package net.kaaass.kflight.data.sort;

import java.util.Comparator;

/**
 * 稳定化快速排序，加入了三者取中优化，并返回两个 pivot
 */
public class StableTriQuickSort {

    public final static class Pivot {
        int left;
        int right;
    }

    /**
     * 尾递归优化的稳定快速排序
     * 处理范围：[left, right)
     */
    public static <S> void sort(S[] arr, int left, int right, Comparator<S> cmp) {
        if (right - left < 2) // 已经有序
            return;
        sort(arr, left, right, new Object[right - left], cmp);
    }

    /**
     * 尾递归优化的稳定快速排序，需要传入临时数组 buf
     * 处理范围：[left, right)
     */
    static <S> void sort(S[] arr, int left, int right, Object[] buf, Comparator<S> cmpr) {
        if (right - left < 2) // 已经有序
            return;

        int lfPivot, rtPivot;
        while (right - left > 1) {
            // 处理分段
            lfPivot = left;
            rtPivot = right;
            if (right - left >= 2) {
                // 选择 key
                int mid = (left >> 1) + (right >> 1);
                S aLf = arr[left], key = arr[mid], aRt = arr[right - 1];
                boolean cmp1 = cmpr.compare(aLf, key) < 0,
                        cmp2 = cmpr.compare(key, aRt) < 0;
                if (cmp1 ^ cmp2) {
                    boolean cmp3 = cmpr.compare(aLf, aRt) < 0;
                    if (cmp2 ^ cmp3)
                        key = aRt;
                    else
                        key = aLf;
                }
                // 排序
                int lfCur = left, midCur, rtCur, cmp;
                midCur = rtCur = right - 1;
                for (var i = left; i < right; i++) {
                    cmp = cmpr.compare(arr[i], key);
                    if (cmp < 0) {
                        buf[lfCur] = arr[i];
                        lfCur++;
                        arr[i] = null; // 作标记
                    } else if (cmp == 0) {
                        buf[midCur] = arr[i];
                        midCur--;
                        arr[i] = null; // 作标记
                    }  // else: pass
                }
                for (var i = right - 1; i >= left; i--) {
                    if (arr[i] != null) {
                        if (rtCur != i)
                            arr[rtCur] = arr[i];
                        rtCur--;
                    }
                }
                // 复制
                if (lfCur - left >= 0)
                    System.arraycopy(buf, left, arr, left, lfCur - left);
                for (var i = 0; i < right - 1 - midCur; i++)
                    arr[lfCur + i] = (S) buf[right - 1 - i];
                // 分段
                lfPivot = lfCur;
                rtPivot = rtCur + 1;
            }
            // 继续排序
            sort(arr, rtPivot, right, buf, cmpr);
            right = lfPivot;
        }
    }
}