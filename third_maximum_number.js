/**
 * @param {number[]} nums
 * @return {number}
 */
 var thirdMax = function(nums) {
  const MAX_LIST_SIZE = 3;
  const headNode = new ListNode(nums[0]);
  const maxLinkedList = new LinkedList(headNode);
  
  nums.shift();
  nums.forEach( num => {
    if( num > maxLinkedList.head.data) {
      maxLinkedList.insertInDescOrder( new ListNode(num) );
      if(maxLinkedList.length > MAX_LIST_SIZE) {
        maxLinkedList.removeLastNode();
      } 
    } else if( maxLinkedList.length < MAX_LIST_SIZE || maxLinkedList.lastNode.data < num ) {
      maxLinkedList.insertInDescOrder( new ListNode(num) );
      if(maxLinkedList.length > MAX_LIST_SIZE) {
        maxLinkedList.removeLastNode();
      } 
    }
  });

  if(maxLinkedList.length === MAX_LIST_SIZE) {
    return maxLinkedList.lastNode.data
  } else {
    return maxLinkedList.head.data
  }
};


class LinkedList {
  constructor(head = null) {
    this.length = head ? 1 : 0;
    this.head = head;
    this.lastNode = head;
    
  }

  insertInDescOrder(newNode) {
    let currNode = this.head;
    let prevNode = null;

    while( currNode ) {
      if(currNode.data == newNode.data) {
        return null;
      }

      if( newNode.data > currNode.data ) {
        if(prevNode) {
          prevNode.next = newNode;
        } else {
          this.head = newNode;
        }

        newNode.next = currNode;
        newNode.prev = prevNode;
        
        currNode.prev = newNode;

        this.length += 1;

        return newNode;
      } 

      prevNode = currNode;
      currNode = currNode.next;
    }
    
    newNode.setPrev(this.lastNode);
    this.lastNode.setNext(newNode);
    this.lastNode = newNode
    this.length += 1;

    return newNode;
  }

  removeLastNode() {
    if(!this.head) {
      return null;
    }

    let removeNode = this.lastNode;
    let newLastNode = this.lastNode.prev;

    this.lastNode = newLastNode;
    this.lastNode.next = null;

    this.length -= 1;
    
    if(this.length == 0 ) {
      this.head = null;
    }
    return removeNode;
  }

  length() {
    return this.length;
  }

  print() {
    let currNode = this.head;
    let array = [];
    while( currNode ) {
      array.push(currNode.data);
      currNode = currNode.next;
    }
    console.log(JSON.stringify(array));
  }
}

class ListNode {
  constructor(data) {
    this.data = data;
    this.next = null;
    this.prev = null;
  }

  setNext(node) {
    this.next = node;
  }
  
  setPrev(node) {
    this.prev = node;
  }
}



console.log(thirdMax([0,-4,0, 5, 3,7,1,4,7.8]))
