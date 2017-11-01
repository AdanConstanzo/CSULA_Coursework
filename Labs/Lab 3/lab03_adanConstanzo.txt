public class lab03_adanConstanzo{

  public static void main(String[] args) {

    int[] A = {13,-3,-25,20,-3,-16,-23,18,20,-7,12,-5,-22,15,-4,7};
    printArray(A);
    SubArray a = FindMaximumSubarray(A,0,A.length-1);
    System.out.println("Divide and Conquer O( nlog(n) )");
    a.print();
    System.out.println("Brute Force O(n^2)");
    SubArray b = bruteForce(A);
    b.print();


  } /* End of main method */

  public static void printArray(int[] A){
    System.out.println("");
    System.out.println(" ");
    System.out.println("This is the array");
    System.out.print("A = {");
    for(int i = 0; i < A.length; i++){
      if( !(i == A.length -1))
        System.out.print(A[i]+", ");
      else
        System.out.println(A[i]+"}");
    }
  }

  public static SubArray bruteForce(int[] A){
    int low = 0, high = A.length-1;
    double max = Double.NEGATIVE_INFINITY,maxSum = 0;
    for(int i = 0; i < A.length; i++){
      maxSum = 0;
      for(int j = i; j < A.length; j++){
        maxSum += A[j];
        if(maxSum >= max){
          low = i;
          high = j;
          max = maxSum;
        }
      }
    }
    return new SubArray(low,high,max);
  } /* End of bruteForce method */

  public static SubArray FindMaximumSubarray(int[] A,int low,int high){
    if(high == low){
      return new SubArray(low,high,A[low]);
    }else{
      int mid = ((low+high)/2);
      SubArray l_sub =  FindMaximumSubarray(A,low,mid);
      SubArray r_sub =  FindMaximumSubarray(A,mid+1,high);
      SubArray m_sub =  FindMaxCrossingSubarray(A,low,mid,high);

      if( l_sub.getSum() >= r_sub.getSum() && l_sub.getSum() >= m_sub.getSum() ){
        return l_sub;
      }else if ( r_sub.getSum() >= l_sub.getSum() && r_sub.getSum() >= m_sub.getSum() ){
        return r_sub;
      }else{
        return m_sub;
      }
    }
  }; /* End of FindMaximumSubarray method */

  public static SubArray FindMaxCrossingSubarray(int[] A,int low, int mid, int high){
    double left_sum = Double.NEGATIVE_INFINITY;
    int max_left = 0;
    int sum = 0;
    for(int i = mid; i >= low; i--){
      sum = sum + A[i];
      if(sum > left_sum){
        left_sum = sum;
        max_left = i;
      }
    }
    double right_sum = Double.NEGATIVE_INFINITY;
    int max_right = 0;
    sum = 0;
    for(int j = mid + 1; j<= high; j++){
      sum = sum + A[j];
      if(sum>right_sum){
        right_sum = sum;
        max_right = j;
      }
    }
    double totalSum = right_sum + left_sum;
    return new SubArray(max_left,max_right,totalSum);
  } /* End of FindMaxCrossingSubarray method */

}; /* End of Class lab03_adanConstanzo */

class SubArray{
  int leftIndex, rightIndex;
  double sum;
  SubArray(int x, int y, double z){
    this.leftIndex = x;
    this.rightIndex = y;
    this.sum = z;
  }
  public double getSum(){return this.sum;}
  public void print(){
    System.out.println("This is max_left: "+this.leftIndex+" This is max_right: "+this.rightIndex+" This is total: "+this.sum);
  }
}; /* End of Class SubArray */
