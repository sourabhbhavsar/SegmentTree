import java.util.Arrays;

public class FindMaximumAndItsFreq {

    static class SegmentTree {
        int[][] array;
        int tl;
        int tr;

        public SegmentTree(int[] arr) {
            array = new int[2 * arr.length][2];
            tl = 0;
            tr = arr.length - 1;
            buildTree(arr, 0, tl, tr);
        }

        public void buildTree(int[] arr, int vertex, int tl, int tr) {
            if (tl == tr) {
                array[vertex][0] = arr[tl];
                array[vertex][1] = 1;
            }
            else {
                int mid = (tl + tr) / 2;
                buildTree(arr, 2 * vertex + 1, tl, mid);
                buildTree(arr, 2 * vertex + 2, mid + 1, tr);

                if (array[2 * vertex + 1][0] > array[2 * vertex + 2][0]) {
                    array[vertex][0] = array[2 * vertex + 1][0];
                    array[vertex][1] = array[2 * vertex + 1][1];
                }
                else if (array[2 * vertex + 1][0] < array[2 * vertex + 2][0]) {
                    array[vertex][0] = array[2 * vertex + 2][0];
                    array[vertex][1] = array[2 * vertex + 2][1];
                }
                else {
                    array[vertex][0] = array[2 * vertex + 2][0];
                    array[vertex][1] = array[2 * vertex + 1][1] + array[2 * vertex + 2][1]; // add the count if they are same
                }
            }
        }

        public int[] getMax(int l, int r) {
            return getMaxHelper(0, tl, tr, l, r);
        }

        public int[] getMaxHelper(int vertex, int tl, int tr, int l, int r) {
            if (l > r) {
                return new int[] {Integer.MIN_VALUE, 0};
            }

            if (tl == l && tr == r) {
                return new int[] { array[vertex][0], array[vertex][1]};
            }

            int tm = (tl + tr) / 2;

            int[] leftMax =  getMaxHelper(2 * vertex + 1, tl, tm, l, Math.min(tm, r)); // left
            int[] rightMax =  getMaxHelper(2 * vertex + 2, tm + 1, tr, Math.max(tm + 1, l), r);

            if (leftMax[0] > rightMax[0]) {
                return leftMax;
            }
            else if (leftMax[0] < rightMax[0]) {
                return rightMax;
            }

            return new int[] {leftMax[0], leftMax[1] + rightMax[1]};
        }

        public void update(int pos, int newValue) {
            updateHelper(0, tl, tr, pos, newValue);
        }

        private void updateHelper(int vertex, int tl, int tr, int pos, int newValue) {
            if (tl == tr) {
                array[vertex][0] = newValue;
            }
            else {
                int tm = (tl + tr) / 2;
                if (pos <= tm) {
                    updateHelper(2 * vertex + 1, tl, tm, pos, newValue);
                }
                else {
                    updateHelper(2 * vertex + 2, tm + 1, tr, pos, newValue);
                }

                // update the Max
                if (array[2 * vertex + 1][0] > array[2 * vertex + 2][0]) {
                    array[vertex][0] = array[2 * vertex + 1][0];
                    array[vertex][1] = array[2 * vertex + 1][1];
                }
                else if (array[2 * vertex + 1][0] < array[2 * vertex + 2][0]) {
                    array[vertex][0] = array[2 * vertex + 2][0];
                    array[vertex][1] = array[2 * vertex + 2][1];
                }
                else {
                    array[vertex][0] = array[2 * vertex + 2][0];
                    array[vertex][1] = array[2 * vertex + 1][1] + array[2 * vertex + 2][1]; // add the count if they are same
                }
            }
        }
    }

    public static void main(String[] args) {
        int arr[] = { 17, 2, 17, 4, 9, 1, 1, 9, 9};
        int n = arr.length;

        SegmentTree st = new SegmentTree(arr);
        System.out.println(Arrays.toString(st.getMax(0, 2))); // 17, 2
        System.out.println(Arrays.toString(st.getMax(3, 8))); // 9, 3

        st.update(6, 21);
        //   int arr[] = { 17, 2, 17, 4, 9, 1, 21, 9, 9};
        System.out.println(Arrays.toString(st.getMax(0, 2))); // 17, 2
        System.out.println(Arrays.toString(st.getMax(3, 8))); // 21, 1

    }
}