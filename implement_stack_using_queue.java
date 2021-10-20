class MyStack {
    
  private Queue<Integer> queue;
  private Queue<Integer> temp;
  
  public MyStack() {
      this.queue = new LinkedList<Integer>();
      this.temp = new LinkedList<Integer>();
  }
  
  public void push(int x) {

      while( this.queue.size() > 0) {
          this.temp.add( this.queue.remove() );
      }
      
      this.queue.add( x );

      while( this.temp.size() > 0 ) {
          this.queue.add( this.temp.remove() );
      }
      
  }
  
  public void print(Queue q) {
      Iterator<Integer> iterator = q.iterator();
      while(iterator.hasNext()){
          System.out.println(iterator.next().toString());
      }
  }
  
  
  public int pop() { 
      return this.queue.remove();
  }
  
  public int top() {
      return this.queue.peek();
  }
  
  public boolean empty() {
      return this.queue.size() == 0;
  }
}

/**
* Your MyStack object will be instantiated and called as such:
* MyStack obj = new MyStack();
* obj.push(x);
* int param_2 = obj.pop();
* int param_3 = obj.top();
* boolean param_4 = obj.empty();
*/