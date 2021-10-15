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
    public boolean isValidBST(TreeNode root) {
        ArrayList<Integer> sortedVal = new ArrayList<Integer>();
    
        boolean result = checkChildValue( root, null, null );

        return result;
    }
    
    /**
     * Traverse BST in-order
     * 1. Recursively move to left most node and set MAX value using parents value 
     * and ensure that the left child node is smaller than the MAX value
     * 
     * 2. Recursively move to right most node set the MIN value using parent value
     * and ensure that the right child node is larger the the MIN value
     * 
     * As we traverse through the node the range will get smaller use the range 
     * to indirectly check that each of the in-order node value are in ascending order
    */
    public boolean checkChildValue(TreeNode rootNode, Integer min, Integer max) {
      
        if( rootNode == null ){
            return true;
        }

        boolean result = true;
        
        // Recursively move to left node
        result = checkChildValue( rootNode.left, min , rootNode.val);
        if( (max != null && rootNode.val >= max) || ( min != null && rootNode.val <= min) ) {
            
            return false;
        }
        
        // We stop  moving downwards if one of the node is out of range / asc order
        if(!result) {
            return false;
        }
        
        // Recursively move to right node
        result = checkChildValue( rootNode.right, rootNode.val , max);
 
        return result;   
    }
}