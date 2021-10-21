class MyQueue {
  Stack<Integer> stack;
  Stack<Integer> temp;
      
  public MyQueue() {
      stack = new Stack<Integer>();
      temp = new Stack<Integer>();
  }
  
  public void push(int x) {

      while(! this.stack.isEmpty()) {
          this.temp.push(this.stack.pop());
      }
      
      this.stack.push(x);
      
      while(! this.temp.isEmpty()) {
          this.stack.push(this.temp.pop());
      }
  }
  
  public int pop() {
      return this.stack.pop();
  }
  
  public int peek() {
      return stack.peek();
  }
  
  public boolean empty() {
      return this.stack.empty();
  }
}