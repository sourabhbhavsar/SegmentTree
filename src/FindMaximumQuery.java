
public class FindMaximumQuery {

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
            }
            else {
                int mid = (tl + tr) / 2;
                buildTree(arr, 2 * vertex + 1, tl, mid);
                buildTree(arr, 2 * vertex + 2, mid + 1, tr);

                array[vertex] = Math.max(array[2 * vertex + 1], array[2 * vertex + 2]);
            }
        }

        public int getMax(int l, int r) {
            return getMaxHelper(0, tl, tr, l, r);
        }

        public int getMaxHelper(int vertex, int tl, int tr, int l, int r) {
            if (l > r) {
                return Integer.MIN_VALUE;
            }

            if (tl == l && tr == r) {
                return array[vertex];
            }

            int tm = (tl + tr) / 2;

            int leftMax =  getMaxHelper(2 * vertex + 1, tl, tm, l, Math.min(tm, r)); // left
            int rightMax =  getMaxHelper(2 * vertex + 2, tm + 1, tr, Math.max(tm + 1, l), r);

            return Math.max(leftMax, rightMax);
        }

        public void update(int pos, int newValue) {
            updateHelper(0, tl, tr, pos, newValue);
        }

        private void updateHelper(int vertex, int tl, int tr, int pos, int newValue) {
            if (tl == tr) {
                array[vertex] = newValue;
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
                array[vertex] = Math.max(array[2 * vertex + 1], array[2 * vertex + 2]);
            }
        }
    }

    public static void main(String[] args) {
        int arr[] = { 1, 2, 3, 4, 5 };
        int n = arr.length;

        SegmentTree st = new SegmentTree(arr);
        System.out.println(st.getMax(0, 2)); // 9
        System.out.println(st.getMax(2, 4)); // 12

        st.update(3, 9);
        //  int arr[] = { 1, 2, 3, 9, 5 };
        System.out.println(st.getMax(0, 2)); // 10
        System.out.println(st.getMax(2, 4)); // 13

    }
}