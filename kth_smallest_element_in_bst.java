/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
  static int currentSteps = 0;
    
  public int kthSmallest(TreeNode root, int k) {
    currentSteps = 0;
    return traverseInOrderByKTimes(root, k);
  }
  
  /**
   * Traverse tree and increment currentSteps class static value as we 
   * visit the node. Since a valid BST will return values in ASC order 
   * when performing in-order traversing, we just need to stop at kth
   * time visiting a node.   
   */
  public Integer traverseInOrderByKTimes(TreeNode root, int k) {
      
      if(root == null) {
        return null;
      }

      Integer result = null;

      result = traverseInOrder(root.left, k);
      if( result != null ) {
          return result;
      }
          
      currentSteps += 1;
      
      if(currentSteps == k) {
        result = root.val;
        return result;
      }
      
      result = traverseInOrder(root.right, k);
      
      return result;
  }
}