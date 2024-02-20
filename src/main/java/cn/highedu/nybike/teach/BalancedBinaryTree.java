package cn.highedu.nybike.teach;
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

public class BalancedBinaryTree {

    public boolean isBalanced(TreeNode root) {
        return checkHeight(root) != -1;
    }

    private int checkHeight(TreeNode node) {
        if (node == null) {
            return 0; // 空树的高度为0
        }

        int leftHeight = checkHeight(node.left);
        if (leftHeight == -1) {
            return -1; // 不平衡状态
        }

        int rightHeight = checkHeight(node.right);
        if (rightHeight == -1) {
            return -1; // 不平衡状态
        }

        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1; // 高度差大于1，不平衡
        }

        return Math.max(leftHeight, rightHeight) + 1; // 返回节点的高度
    }

    public static void main(String[] args) {
        // 示例用法
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);

        BalancedBinaryTree bbt = new BalancedBinaryTree();
        System.out.println("Is tree balanced? " + bbt.isBalanced(root));
    }
}

