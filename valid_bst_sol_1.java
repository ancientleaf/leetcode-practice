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
  public String LEFT = "LEFT";
  public String RIGHT = "RIGHT";
  
  public boolean isValidBST(TreeNode root) {
      ArrayList<Integer> sortedVal = new ArrayList<Integer>();
  
      boolean result = checkChildValue(root, sortedVal, root.val);

      return result;
  }

  /**
     * Traverse BST in-order
     * Recursively traverse BST in-orderly push the child node value to the list 
     * 
     * In a valid BST, in-order traverse will return values in ascending order
     * 
     * As we traverse through the node will populate the value in to the list 
     * and expects it will be in ascending order. While traversing we check if the current node
     * is larger then the last value in the least. If it is smaller then it means the not in ASC order.
     * 
     * *Note: We had to Cast Integer value to compare value with node's value which is in primitive type.
     * Hence, will have another attempt to avoid using ArrayList<Integer> to store the values for checking
     * if we are getting ASC order values.
    */
  public boolean checkChildValue(TreeNode rootNode, ArrayList sortedVal, int parentVal) {
    
      if( rootNode == null ){
          return true;
      }
      
      boolean result = true;
      
      // Recursively move to left node
      result = checkChildValue( rootNode.left, sortedVal , rootNode.val);
      
      if( !sortedVal.isEmpty() && ( rootNode.val <= Integer.parseInt(sortedVal.get(sortedVal.size() - 1).toString()) ) ) {
          return false;
      }
      sortedVal.add(Integer.valueOf(rootNode.val));
      
      if( !result ) {
          return false;
      }
      // Recursively move to right node
      result = checkChildValue( rootNode.right, sortedVal , rootNode.val);


    return result;
  }
  
}