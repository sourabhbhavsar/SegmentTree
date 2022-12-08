
public class RangeSum {

    static class SegmentTree {
        int[] array;
        int tl;
        int tr;

        public SegmentTree(int[] arr) {
            array = new int[2 * arr.length];
            tl = 0;
            tr = arr.length - 1;
            buildTree(arr, 0, tl, tr);
        }

        public void buildTree(int[] arr, int vertex, int tl, int tr) {
            if (tl == tr) {
                array[vertex] = arr[tl];
            } else {
                int mid = (tl + tr) / 2;
                buildTree(arr, 2 * vertex + 1, tl, mid);
                buildTree(arr, 2 * vertex + 2, mid + 1, tr);

                array[vertex] = array[2 * vertex + 1] + array[2 * vertex + 2];
            }
        }

        public int getRangeSum(int l, int r) {
            return getRangeSumHelper(0, tl, tr, l, r);
        }

        public int getRangeSumHelper(int vertex, int tl, int tr, int l, int r) {
            if (l > r) {
                return 0;
            }

            if (tl == l && tr == r) {
                return array[vertex];
            }

            int tm = (tl + tr) / 2;

            return getRangeSumHelper(2 * vertex + 1, tl, tm, l, Math.min(tm, r)) // left
                    + getRangeSumHelper(2 * vertex + 2, tm + 1, tr, Math.max(tm + 1, l), r);
        }

        public void update(int pos, int newValue) {
            updateHelper(0, tl, tr, pos, newValue);
        }

        private void updateHelper(int vertex, int tl, int tr, int pos, int newValue) {
            if (tl == tr) {
                array[vertex] = newValue;
            } else {
                int tm = (tl + tr) / 2;
                if (pos <= tm) {
                    updateHelper(2 * vertex + 1, tl, tm, pos, newValue);
                } else {
                    updateHelper(2 * vertex + 2, tm + 1, tr, pos, newValue);
                }

                // update the sum
                array[vertex] = array[2 * vertex + 1] + array[2 * vertex + 2];
            }
        }
    }


    public static void main(String[] args) {
        int arr[] = {1, 2, 3, 4, 5};
        int n = arr.length;

        SegmentTree st = new SegmentTree(arr);
        System.out.println(st.getRangeSum(1, 3)); // 9
        System.out.println(st.getRangeSum(2, 4)); // 12

        st.update(3, 5);
        //  int arr[] = { 1, 2, 3, 5, 5 };
        System.out.println(st.getRangeSum(1, 3)); // 10
        System.out.println(st.getRangeSum(2, 4)); // 13

    }
}