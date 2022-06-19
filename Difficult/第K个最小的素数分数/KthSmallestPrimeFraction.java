package Difficult.第K个最小的素数分数;import java.util.ArrayList;import java.util.Collections;import java.util.List;import java.util.PriorityQueue;public class KthSmallestPrimeFraction {    // a/b < c/d === a*d<b*c    // 避免了大量的浮点运算    public int[] kthSmallestPrimeFraction(int[] arr, int k) {        int len = arr.length;        List<int[]> frac = new ArrayList<>();        for (int i=0; i<len; i++){            for (int j=i+1; j<len; j++){                frac.add(new int[]{arr[i], arr[j]});            }        }        frac.sort((x, y) -> x[0] * y[1] - x[1] * y[0]);        return frac.get(k-1);    }    // 优先队列    public int[] kthSmallestPrimeFraction2(int[] arr, int k){        int len = arr.length;        // 这个队列存的是索引，我们返回的是索引对应的值        PriorityQueue<int[]> queue = new PriorityQueue<>                ((x,y)->arr[x[0]]*arr[y[1]]-arr[x[1]]*arr[y[0]]);        // 0..i-1作为分子，都是小于 i 作为分子除以 j 的        for (int i=1; i<len; i++){            queue.add(new int[]{0, i});        }        // 弹出的第k次，就是我们要的第k个最小素数分数        // 所以前面我们弹出k-1次，第k次弹出用于返回        for (int i=1; i<k; i++){            int[] frac = queue.poll();            assert frac != null;            int x = frac[0];            int y = frac[1];            if (x+1<y){                queue.add(new int[]{x+1, y});            }        }        assert queue.peek() != null;        return new int[]{arr[queue.peek()[0]],arr[queue.peek()[1]]};    }    // 二分查找+双指针    /*        给定一个分数值A，然后计算 arr[i]/arr[j] < A        这里需要恰好等于K才行，在进行寻找的过程中如果有个超过K的，取第K个值的时候        并不能保证我们取的第K个值对于全局来说也是合理的     */    public int[] kthSmallestPrimeFraction3(int[] arr, int k) {        int len = arr.length;        double left = 0.0;        double right = 1.0;        while (true){            int x = 0, y = 1;            int count = 0;            int i = 0;  // 取-1可以用于下面循环的 i<j-1，但是与此同时也需要加很多限制条件，防止越界等情况            double mid = (right+left)/2;            for (int j=1; j<len; j++){                while (arr[i]<mid*arr[j]){                    if (arr[i]*y>arr[j]*x){                        x = arr[i];                        y = arr[j];                    }                    i++;                }                count+=i;            }            if (count==k){                return new int[]{x,y};            }            if (count>k){                right = mid;            }else {                left = mid;            }        }    }}