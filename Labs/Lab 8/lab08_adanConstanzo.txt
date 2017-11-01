class lab08_adanConstanzo{
  public static void main(String[] args) {
    System.out.println("Quick Sort with Partition: ");
    int[] A = {1,5,3,2,6,77,2};
    System.out.print("The array:> ");
    print(A);
    QuickSort(A,0,A.length-1);
    System.out.println("");
    System.out.print(" Sorted :> ");
    print(A);

    System.out.println("");
    System.out.println("Quick Sort with random Partition: ");
    int B[] = {1,5,3,2,6,77,2};
    System.out.print("The original array:> ");
    print(B);
    System.out.println("The Random Partition Arrays :> ");
    RandomQuickSort(B,0,B.length-1);
    System.out.println("");
    System.out.print(" Sorted :> ");
    print(B);
  }
  public static void print(int[] data){
    for(int i = 0; i < data.length; i++){
      if(i == 0)
        System.out.print("[ "+data[i]);
      else if (i == data.length -1)
        System.out.println(" | "+data[i]+" ]");
      else
        System.out.print(" | "+data[i]);
    }
  }

  public static int RandomPartition(int[] A,int p,int r){
    int i = (int) (Math.random() *(r-p)) +p;
    int temp = A[r];
    A[r] = A[i];
    A[i] = temp;
    System.out.print("  ");
    print(A);
    return Partition(A,p,r);
  }

  public static void RandomQuickSort(int[] A,int p,int r){
    if(p<r){
      int q = RandomPartition(A,p,r);
      RandomQuickSort(A,p,q-1);
      RandomQuickSort(A,q+1,r);
    }
  }

  public static void QuickSort(int[] A,int p, int r){
    if(p<r){
      int q = Partition(A,p,r);
      QuickSort(A,p,q-1);
      QuickSort(A,q+1,r);
    }
  }

  public static int Partition(int[] A,int p, int r){
    int x = A[r];
    int i = p-1;
    for(int j = p; j<= r-1; j++){
      if(A[j]<= x){
        i ++;
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
      }
    }
    int temp = A[i+1];
    A[i+1] = A[r];
    A[r] = temp;
    return i+1;
  }
}
