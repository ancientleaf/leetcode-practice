// Problem https://leetcode.com/problems/n-th-tribonacci-number

class Tribonacci { 
    
  int temp[] = new int[3];
  
  public int tribonacci(int n) {
      temp[0] = 0;
      temp[1] = 1;
      temp[2] = 1;
      
      if(n == 0) {
          return 0;
      }
      
      if(n == 1 || n == 2) {
          return 1;
      }
      
      for(int i = 3; i <= n ; i++) {
          int result  = temp[0] + temp[1] + temp[2];
          temp[0] = temp[1];
          temp[1] = temp[2];
          temp[2] = result;
      }
      return temp[2];
  }
}