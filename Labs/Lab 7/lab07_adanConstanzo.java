public class lab07_adanConstanzo{
  public static void main(String[] args) {

    System.out.println("Before BuildMaxHeap");
    int[] A = {7,6,5,1,8,9,2,4,3,10};
    HeapTree heap = new HeapTree(A);
    heap.print();
    System.out.println("After BuildMaxHeap");
    BuildMaxHeap(heap);
    heap.print();
    System.out.println("After HeapSort");
    HeapSort(heap);
    heap.print();

  }

  public static int parent(int i){ return i/2; }
  public static int left(int i){ return 2*i +1; }
  public static int right(int i){ return 2*i + 2 ; }
  public static void MaxHeapify(HeapTree A,int i){
    int l = left(i);
    int r = right(i);
    int largest;
    if(l<= A.heapSize-1 && (A.data[l] > A.data[i]) )
      largest = l;
    else
      largest = i;
    if( r <= A.heapSize-1 && (A.data[r] > A.data[largest]))
      largest = r;
    if(largest != i){
      int temp = A.data[largest];
      A.data[largest] = A.data[i];
      A.data[i] = temp;
      MaxHeapify(A,largest);
    }
  }
  public static void BuildMaxHeap(HeapTree A){
    int n = A.heapSize;
    for( int i = (n/2) -1; i >= 0; i--){
      MaxHeapify(A,i);
    }
  }
  public static void HeapSort(HeapTree A){
    BuildMaxHeap(A);
    for(int i = A.data.length-1; i >=1; i--){
      int temp = A.data[i];
      A.data[i] = A.data[0];
      A.data[0] = temp;
      A.heapSize = A.heapSize-1;
      MaxHeapify(A,0);
    }
  }
}

class HeapTree{
  public int[] data;
  public int heapSize;
  int heapPrint;
  public HeapTree(int[] A){
    data = new int[A.length];
    heapSize = A.length;
    heapPrint = heapSize;
    for(int i = 0; i < A.length; i++)
      data[i] = A[i];
  }
  public void print(){
    for(int i = 0; i < heapPrint; i++){
      if(i == 0)
        System.out.print("[ "+data[i]);
      else if (i == heapPrint -1)
        System.out.println(" | "+data[i]+" ]");
      else
        System.out.print(" | "+data[i]);
    }
  }
};
