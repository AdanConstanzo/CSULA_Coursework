import java.lang.Math;
import java.util.Random;

class lab06_adanConstanzo{
  public static void main(String[] args) {
    System.out.println("Random Hire with Permute-By-Sorting");
    Randomized_Hire_Assistant_With_Sort(10);
    System.out.println("=======");
    System.out.println("=======");
    System.out.println("=======");
    System.out.println("Random Hire with Random-In-Place");
    Randomized_Hire_Assistant_With_Place(10);
  }
  public static void hire(int i){ System.out.println("Hiring "+i+" cadidate");}
  public static void interview(int i){ System.out.println("Interviewing "+i);}
  public static void Randomized_Hire_Assistant_With_Sort(int n){
    int[] A = new int[n];
    for(int i = 0; i <n; i++ )
      A[i] = i+1;
    System.out.println("Original Array");
    PrintArray(A);
    PermutateBySorting(A);
    int best = 0;
    for(int i = 0; i <n; i++){
      interview(A[i]);
      if(A[i]>best){
        best = A[i];
        hire(A[i]);
      }
    }
    System.out.println("After Sort");
    PrintArray(A);
  }
  public static void Randomized_Hire_Assistant_With_Place(int n){
    int[] A = new int[n];
    for(int i = 0; i <n; i++ )
      A[i] = i+1;
    PrintArray(A);
    RandomInPlace(A);
    int best = 0;
    for(int i = 0; i <n; i++){
      interview(A[i]);
      if(A[i]>best){
        best = A[i];
        hire(A[i]);
      }
    }
    System.out.println("After Sort");
    PrintArray(A);
  }
  public static void exchange(int ai,int bi,int[] ar){
    int temp = ar[bi];
    ar[bi] = ar[ai];
    ar[ai] = temp;
  }
  public static void PermutateBySorting(int[] A){
    int n = A.length;
    int[] p = new int[n];

    for(int i = 0; i < n; i++)
      p[i] = 1 + (int)(Math.random() * ((Math.pow(n,3) - 1) + 1));

    for(int i = 0; i < n; i++)
      for(int j =i; j<n; j++)
        if(p[i]>p[j])
          exchange(i,j,A);
  }
  public static void RandomInPlace(int[] A){
    int n = A.length;
    for(int i = 0;i <n;i++){
      exchange(i, i + (int)(Math.random() * (( (n-1) - i) + 1)) ,A);
    }
  }
  public static void PrintArray(int[] data){
    for(int i = 0; i < data.length; i++){
      if(i == 0)
        System.out.print("[ "+data[i]);
      else if (i == data.length -1)
        System.out.println(" | "+data[i]+" ]");
      else
        System.out.print(" | "+data[i]);
    }
  }
}
