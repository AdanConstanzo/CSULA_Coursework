class lab04_adanConstanzo{
  public static void main(String[] args) {
    int[][] m = {{3,6,4,4},{7,8,4,4},{4,4,4,4},{3,3,3,3}};
    int[][] n = {{4,4,2,2},{2,1,2,2},{2,2,2,2},{3,3,3,3}};
    /*
    int[][] m =  {{3,6},{7,8}};
    int[][] n =  {{4,4},{2,1}};
    */
    
    Matrix a = new Matrix(m);
    Matrix b = new Matrix(n);
    System.out.println("Matrix A: ");
    a.print();
    System.out.println("");
    System.out.println("Matrix B:");
    b.print();
    System.out.println("");
    System.out.println("Brute Force (Three Loops) Theta(n^3) ");
    Matrix c = BruteForce(a,b);
    c.print();
    System.out.println("");
    System.out.println("Recusive Brute Force (8 recursive calls) Theta(n^log8)");
    Matrix d = BruteForceRecursion(a,b);
    d.print();
    System.out.println("");
    System.out.println("StrassenMethod Algorithm (7 recursive calls) Theta(n^log7)");
    Matrix e = StrassenMethod(a,b);
    e.print();
  }

  public static void split(Matrix P, Matrix C, int iB, int jB){
      for(int i1 = 0, i2 = iB; i1 < C.size; i1++, i2++)
          for(int j1 = 0, j2 = jB; j1 < C.size; j1++, j2++)
              C.data[i1][j1] = P.data[i2][j2];
  }

  public static Matrix add(Matrix left,Matrix right){
    int n = left.size;
    Matrix temp = new Matrix(n);
    for(int i = 0; i < n; i++){
      for(int j = 0; j < n; j++){
        temp.data[i][j] = left.data[i][j] + right.data[i][j];
      }
    }
    return temp;
  }

  public static Matrix subtract(Matrix left, Matrix right){
    int n = left.size;
    Matrix temp = new Matrix(n);
    for(int i = 0; i < n; i++){
      for(int j = 0; j < n; j++){
        temp.data[i][j] = left.data[i][j] - right.data[i][j];
      }
    }
    return temp;
  }

  public static void join(Matrix C, Matrix P, int iB, int jB) {
      for(int i1 = 0, i2 = iB; i1 < C.size; i1++, i2++)
          for(int j1 = 0, j2 = jB; j1 < C.size; j1++, j2++)
              P.data[i2][j2] = C.data[i1][j1];
  }

  public static Matrix BruteForce(Matrix left, Matrix right){
    int n = left.size;
    Matrix c = new Matrix(n);
    for(int i = 0; i <n; i++){
      for(int j = 0; j <n; j++){
        c.data[i][j] = 0;
        for(int k = 0; k < n; k++){
          c.data[i][j] = c.data[i][j] + left.data[i][k]*right.data[k][j];
        }
      }
    }
    return c;
  }

  public static Matrix BruteForceRecursion(Matrix A, Matrix B){
    int n = A.size;
    Matrix C = new Matrix(n);
    if(n == 1){
      C.data[0][0] = A.data[0][0] * B.data[0][0];
    }else{
      Matrix A11 = new Matrix(n/2);
      Matrix A12 = new Matrix(n/2);
      Matrix A21 = new Matrix(n/2);
      Matrix A22 = new Matrix(n/2);
      Matrix B11 = new Matrix(n/2);
      Matrix B12 = new Matrix(n/2);
      Matrix B21 = new Matrix(n/2);
      Matrix B22 = new Matrix(n/2);
      split(A, A11, 0 , 0);
      split(A, A12, 0 , n/2);
      split(A, A21, n/2, 0);
      split(A, A22, n/2, n/2);
      split(B, B11, 0 , 0);
      split(B, B12, 0 , n/2);
      split(B, B21, n/2, 0);
      split(B, B22, n/2, n/2);
      Matrix D11 = add( BruteForceRecursion(A11,B11) , BruteForceRecursion(A12,B21) );
      Matrix D12 = add( BruteForceRecursion(A11,B12) , BruteForceRecursion(A12,B22) );
      Matrix D21 = add( BruteForceRecursion(A21,B11) , BruteForceRecursion(A22,B21) );
      Matrix D22 = add( BruteForceRecursion(A21,B12) , BruteForceRecursion(A22,B22) );

      join(D11, C, 0 , 0);
      join(D12, C, 0 , n/2);
      join(D21, C, n/2, 0);
      join(D22, C, n/2, n/2);

    }
    return C;
  }

  public static Matrix StrassenMethod(Matrix A, Matrix B){
    int n = A.size;
    Matrix C = new Matrix(n);
    if(n == 1){
      C.data[0][0] = A.data[0][0] * B.data[0][0];
    }else{
      Matrix A11 = new Matrix(n/2);
      Matrix A12 = new Matrix(n/2);
      Matrix A21 = new Matrix(n/2);
      Matrix A22 = new Matrix(n/2);
      Matrix B11 = new Matrix(n/2);
      Matrix B12 = new Matrix(n/2);
      Matrix B21 = new Matrix(n/2);
      Matrix B22 = new Matrix(n/2);
      split(A, A11, 0 , 0);
      split(A, A12, 0 , n/2);
      split(A, A21, n/2, 0);
      split(A, A22, n/2, n/2);
      split(B, B11, 0 , 0);
      split(B, B12, 0 , n/2);
      split(B, B21, n/2, 0);
      split(B, B22, n/2, n/2);

      Matrix S1 = subtract(B12,B22);
      Matrix S2 = add(A11,A12);
      Matrix S3 = add(A21,A22);
      Matrix S4 = subtract(B21,B11);
      Matrix S5 = add(A11,A22);
      Matrix S6 = add(B11,B22);
      Matrix S7 = subtract(A12,A22);
      Matrix S8 = add(B21,B22);
      Matrix S9 = subtract(A11,A21);
      Matrix S10 = add(B11,B12);

      Matrix P1 = StrassenMethod(A11,S1);
      Matrix P2 = StrassenMethod(S2,B22);
      Matrix P3 = StrassenMethod(S3,B11);
      Matrix P4 = StrassenMethod(A22,S4);
      Matrix P5 = StrassenMethod(S5,S6);
      Matrix P6 = StrassenMethod(S7,S8);
      Matrix P7 = StrassenMethod(S9,S10);

      Matrix D11 = add(subtract(add(P5,P4),P2),P6);
      Matrix D12 = add(P1,P2);
      Matrix D21 = add(P3,P4);
      Matrix D22 = subtract(subtract(add(P5,P1),P3),P7);

      join(D11, C, 0 , 0);
      join(D12, C, 0 , n/2);
      join(D21, C, n/2, 0);
      join(D22, C, n/2, n/2);

    }
    return C;
  }

}
class Matrix {

  public int size;
  public int[][] data;

  Matrix(int[][] a){
    this.size = a.length;
    this.data =new int[this.size][this.size];
    for(int i = 0; i < this.size; i++){
      for(int j = 0; j < this.size; j++ ){
        data[i][j] = a[i][j];
      }
    }
  } // End of Matrix Constructor

  Matrix(int size){
    this.size = size;
    this.data = new int[this.size][this.size];
  }

  public void print(){
    for(int i = 0; i < this.size; i++){
      for(int j = 0; j < this.size; j++){
        if(j == 0){
          System.out.print("{ [ "+this.data[i][j] +" ] ,");
        }else if(j == this.size-1){
          System.out.println("[ "+this.data[i][j]+" ]}");
        }else{
          System.out.print(" ["+this.data[i][j]+" ] ,");
        }
      }
    }
  } // End of print() method

};
