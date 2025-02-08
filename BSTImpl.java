
package a3;

public class BSTImpl implements BST {

    private Node root;
    private int size;

    public BSTImpl() {
        root = null;
        size = 0;
    }

    public BSTImpl(int val) {
        this.root = new NodeImpl(val);
        size = 1;
    }

    // The implementations given to you are intended to help you 
    // see how the linked cells work, and how to program with them.
    //
    // These methods are patterns to illustrate for you how to set up
    // the method implementations as recursion.
    //
    // You should follow this pattern for implementing the other recursive methods
    // by adding your own private recursive helper methods.

    @Override
    // interface method ==================================================
    public int height() {
        // It is not recursive itself, but it calls a recursive
        // private "helper" method and passes it access to the tree cells.
        return height_recursive(this.root);
    }

    // private recursive helper
    private int height_recursive(Node c) {
        // private inner "helper" method with different signature
        // this helper method uses recursion to traverse
        // and process the recursive structure of the tree of cells
        if (c == null) return -1;
        int lht = height_recursive(c.getLeft());
        int rht = height_recursive(c.getRight());
        return Math.max(lht, rht) + 1;
    }

    @Override
    // interface method ==================================================
    public boolean contains(int val) {
        if (this.root == null) return false;
        return contains_r(val, root);
    }

    // private recursive helper
    private boolean contains_r(int val, Node c) {
        if (c == null) return false;
        if (c.getValue() == val) return true;
        return contains_r(val, c.getLeft()) || contains_r(val, c.getRight());
    }

    @Override
    // interface method, used by autograder, do not change
    public Node getRoot() {
        return this.root;
    }

    @Override
    // interface method ==================================================
    public int size() {
        return this.size;
    }


    @Override
    // interface method ===================================================
    public void remove(int value) {
        remove_r(value, this.root);
    }

    // private recursive helper
    private Node remove_r(int k, Node c) {
        if (c == null) return null; // item not found, nothing to do
        // now we know we have a real node to examine
        //int cflag = k.compareTo(c.getValue());
        int vc = c.getValue();
        if (k < vc) { // k is smaller than node
            c.setLeft(remove_r(k, c.getLeft()));
            return c;
        } else if (k > vc) { // k is larger than node
            c.setRight(remove_r(k, c.getRight()));
            return c;
        } else { // k==vc   // found it... now figure out how to rearrange
            // cases
            if (c.getLeft() == null && c.getRight() == null) {
                c = null;
                this.size--;
            } // leaf
            else if (c.getLeft() == null && c.getRight() != null) {
                c = c.getRight();
                this.size--;
            } // RC only
            else if (c.getLeft() != null && c.getRight() == null) {
                c = c.getLeft();
                this.size--;
            } // LC only
            else { // 2 children
                Node mc = findMaxCell(c.getLeft());
                c.setValue(mc.getValue());
                c.setLeft(remove_r(c.getValue(), c.getLeft()));   // recurse
            }
            return c;
        }
    }

    // private recursive helper
    private Node findMaxCell(Node c) {
        if (c.getRight() == null) return c;
        return findMaxCell(c.getRight());
    }


    //====================================================================================
    //
    // The methods below here are part of the public BST interface we defined,
    // but you will write the implementation code.
    //
    // At the moment, they have return statements that return dummy values; this
    // is so they will compile, but those return values are just dummy behavior
    // you will replace the dummy returns with your own code and proper return values.
    //
    //====================================================================================


    @Override
    // interface method ==================================================
    public int insert(int val) {
        size++;
        insert_r(val, this.root);
        return val;
    }

    private int insert_r(int val, Node bst) {
        if (root == null) {
            root = new NodeImpl(val);
            return val;
        }
        if (bst == null) {
            bst = new NodeImpl(val);
            return val;
        }
        int vc = bst.getValue();
        if (vc > val) {
            if (bst.getLeft() == null) {
                bst.setLeft(new NodeImpl(val));
            } else insert_r(val, bst.getLeft());

        }
        if (vc < val) {
            if (bst.getRight() == null) {
                bst.setRight(new NodeImpl(val));
            } else {
                insert_r(val, bst.getRight());
            }
        }
        return val;
    }


    /* Hint: Don't forget to update size */
    /* Hint: You can find examples of how to create a new Node object elsewhere in the code */


    @Override
    // interface method ==================================================
    public int findMin() {
        Node current = this.root;
        return findMin_r(current);
    }

    private int findMin_r(Node current) {
        if (current == null) {
            return -1;

        }
        if (current.getLeft() != null) {
            current = current.getLeft();
            return findMin_r(current);
        } else {
            return current.getValue();
        }
    }

    @Override
    // interface method ==================================================
    public int findMax() {
        /*See BST.java for method specification */
        Node actu = this.root;
        return findMax_r(actu);
    }

    private int findMax_r(Node actu) {
        if (actu == null) {
            return -1;
        }
        if (actu.getRight() != null) {
            actu = actu.getRight();
            return findMax_r(actu);

        } else {
            return actu.getValue();
        }
    }


    @Override
    // interface method ==================================================
    public Node get(int val) {
        /*See BST.java for method specification */
        return get_r(this.root, null, val);
    }

    private Node get_r(Node c, Node vg, int val) {
        if (c == null) {
            return vg;
        }
        if (c.getValue() == val) {
            vg = c;
        }
        if (get_r(c.getLeft(), vg, val) != null) {
            vg = get_r(c.getLeft(), vg, val);
        }
        if (get_r(c.getRight(), vg, val) != null) {
            vg = get_r(c.getRight(), vg, val);
        }
        return vg;
    }

    @Override
    // interface method ==================================================
    public boolean isFullBT() {
        if (this.root == null) {
            return true;
        }
        return isFullTree_r(this.root);
    }

    /*See BST.java for method specification */
    /* Hint: How can you "break-up" the problem into smaller pieces? */
    /* Your code here */
    private boolean isFullTree_r(Node c) {
        if (c.getLeft() != null && c.getRight() == null) {
            return false;
        }
        if (c.getLeft() == null && c.getRight() != null) {
            return false;
        }
        if (c.getLeft() == null && c.getRight() == null) {
            return true;
        }
        if (isFullTree_r(c.getLeft()) && isFullTree_r(c.getRight())) {
            return true;
        }
        return false;


    }

    @Override
    // interface method ==================================================
    public int merge(BST nbt) {
        /*See BST.java for method specification */
        // Hint: traverse bst using pre-order
        // as each node is visited, take the value there
        // and do this.insert(value)
        // have to somehow count when an add is successful
        // so we can return the number of nodes added
        final int z = this.size();
        return mergev(nbt.getRoot(), z);


        // Dummy return statement.  Remove when you implement!
    }

    private int mergev(Node t, int temp) {
        if (t == null) {
            return this.size() - temp;
        }
        this.insert(t.getValue());
        if ((t.getLeft()) != null) {
            mergev(t.getLeft(), temp);
        }
        if (t.getRight() != null) {
            mergev(t.getRight(), temp);
        }
        return this.size - temp;


    }


    public int getMaxLeafHeightDiff() {
        /*See BST.java for method specification */
        /* Hint: Which of the methods you're given are most similar to this? */
        /* Your code here */

        return getmaxv(this.root);


    }

    private int getmaxv(Node c) {
        if (c == null) return -1;
        int lht = height_recursive(c.getLeft());
        int rht = height_recursive(c.getRight());
        return Math.max(lht, rht) - Math.min(lht, rht);
    }


}
