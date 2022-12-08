public class FindKthZeroQuery {

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
                array[vertex] = (arr[tl] == 0 ? 1 : 0);
            }
            else {
                int mid = (tl + tr) / 2;
                buildTree(arr, 2 * vertex + 1, tl, mid);
                buildTree(arr, 2 * vertex + 2, mid + 1, tr);

                array[vertex] = array[2 * vertex + 1] + array[2 * vertex + 2]; // add number of zero in left and right
            }
        }

        public int findKthZero(int k) {
            return findKthZero(0, tl, tr, k);
        }

        public int findKthZero(int vertex, int tl, int tr, int k) {
           if (k > array[vertex]) {
               return -1;
           }

           if (tl == tr) {
               return tl;
           }

           int tm = (tl + tr) / 2;
           if (array[2 * vertex + 1] >= k) {
               return findKthZero(2 * vertex + 1, tl, tm, k);
           }
           else {
               return findKthZero(2 * vertex + 2, tm + 1, tr, k - array[2 * vertex + 1]);
           }
        }

        public void update(int pos, int newValue) {
            updateHelper(0, tl, tr, pos, newValue);
        }

        private void updateHelper(int vertex, int tl, int tr, int pos, int newValue) {
            if (tl == tr) {
                array[vertex] = (newValue == 0 ? 1 : 0);
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
                array[vertex] = array[2 * vertex + 1] + array[2 * vertex + 2]; // add number of zero in left and right
            }
        }
    }

    public static void main(String[] args) {
        int arr[] = { 0, 2, 0, 0, 5, 7, 8 , 0, 0};
        int n = arr.length;

        FindKthZeroQuery.SegmentTree st = new FindKthZeroQuery.SegmentTree(arr);
        System.out.println(st.findKthZero(3)); // 3

        st.update(3, 9);
        // int arr[] = { 0, 2, 0, 9, 5, 7, 8 , 0, 0};
        System.out.println(st.findKthZero(3)); // 7

    }
}
