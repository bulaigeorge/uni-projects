
public class BSTree implements BSTOper {
    private Node rot;
    private int size;
    private int[] list;
    private int counter;

    private class Node {
        Node left, right, parent;
        // verdier i venstre subtre er < verdien i noden selv
        // verdier i høyre subtre er > verdien i noden selv
        int value;
        // konstruktør
        public Node(int v) {
            this.value = v;
        }

        private Node find(int value) {
            Node retur = null;
            if (this.value == value) {
                retur = this;
            }
            else if (this.value > value) {
                //check if the left child is empty
                if (left == null) {retur = new Node(-1);}
                else {
                    retur = left.find(value);
                }
            }
            else if (this.value < value) {
                //check if the right child is empty
                if (right == null) {retur = new Node(-1);}
                else {
                    retur = right.find(value);
                }
            }
            return retur;
        }

        private void add (int value) {
            if (this.value == value) {
                //System.out.println("This value exist already");
            }
            else if (this.value > value) {
                if (left == null) {
                    left = new Node(value);
                    size++;
                    left.parent = this;
                }
                else {
                    left.add(value);
                }
            }
            else if (this.value < value) {
                if (right == null) {
                    right = new Node(value);
                    size++;
                    right.parent = this;
                }
                else {
                    right.add(value);
                }
            }
        }

        public void addToList(Node node) {
            if (node == null) {
                return;
            }
            if (node.left != null) {
                addToList(node.left);
            }
            list[counter] = node.value;
            counter++;
            if (node.right != null) {
                addToList(node.right);
            }
        }

    }



    // konstruktør til BSTree
    public BSTree() {}

    public BSTree(int[] numbers) {
        this.rot = new Node(numbers[0]);
        for (int i = 1; i < numbers.length; i++) {
            this.add(numbers[i]);
        }
    }


    private Node findParent( Node n ){
         return n.parent;
     }
    private Node findGrandparent( Node n ){
         return n.parent.parent;
     }


    private Node find(int value) {
        Node retur = null;
        if (this.rot == null) {
            retur = rot;
        }
        else if (rot.value > value) {
            retur = rot.left.find(value);
        }
        else if (rot.value < value) {
            retur = rot.right.find(value);
        }
        else if (rot.value == value) {
            retur = rot;
        }
        return retur;
    }


    // metoder fra BSTOper

    public void add(int value) {
        if (rot == null) {
            rot = new Node(value);
            size++;
            //counter = 0;
        }
        else {
            rot.add(value);
            //counter = 0;
        }
    }

    public int size() {
        return size;
    }

    public boolean existsInTree(int value) {
        return find(value).value > -1;
    }

    public int[] findInRange(int low, int high) {
        int[] rangeList = new int[size];
        int numberElements = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] > low && list[i] < high) {
                rangeList[numberElements] = list[i];
                numberElements++;
            }
        }
        int[] returList = new int[numberElements];
        for (int i = 0; i < numberElements; i++) {
            returList[i] = rangeList[i];
        }
        return returList;
    }

    public int[] sortedArray() {
        counter = 0;
        list = new int[size];
        rot.addToList(rot);
        return list;
    }

    public void addAll(int[] integers) {
        for (int i = 0; i < integers.length; i++) {
            add(integers[i]);
        }
    }

    public int findNearestSmallerThan(int value) {
        int[] tempList = findInRange(-1,value);
        return tempList[tempList.length-1];
    }

    public boolean remove(int value) {
        Node node = find(value);
        if (value == rot.value) {
            int[] tList = findInRange(value, list[list.length-1]);
            int newRot = tList[0];
            if (find(tList[0]).right == null) {
                Node moved = find(tList[0]);
                moved.parent.left = null;
                moved = null;
                node.value = newRot;
                size--;
                return true;
            }
            else {
                Node moved = find(tList[0]);
                moved.parent.left = moved.right;
                moved.right.parent = moved.parent;
                moved = null;
                node.value = newRot;
                size--;
                return true;
            }
        }
        else if (value < rot.value) {
            if (node.left == null && node.right == null) {
                node.parent.left = null;
                node.parent = null;
                node = null;
                size--;
                return true;
            }
            else if (node.left != null && node.right == null){
                node.parent.left = node.left;
                node.left.parent = node.parent;
                node = null;
                size--;
                return true;
            }
            else {
                if (node == node.parent.right) {
                    if (node.left != null) {
                        node.parent.right = node.left;
                        node.right.parent = node.left;
                        node = null;
                        size--;
                        return true;
                    }
                    node.parent.right = node.right;
                    node.right.parent = node.parent;
                    node = null;
                    size--;
                    return true;
                }
                node.parent.left = node.right;
                node.right.parent = node.parent;
                node.right.left = node.left;
                node = null;
                size--;
                return true;
            }
        }
        else if (value > rot.value){
            if (node.left == null && node.right == null) {
                if (node == node.parent.left) {
                    node.parent.left = null;
                    node.parent = null;
                    node = null;
                    size--;
                    return true;
                }
                node.parent.right = null;
                node.parent = null;
                node = null;
                size--;
                return true;
            }
            else if (node.left != null && node.right == null){
                node.parent.right = node.left;
                node.left.parent = node.parent;
                node = null;
                size--;
                return true;
            }
            else {
                if (node == node.parent.left) {
                    if (node.left != null) {
                        node.parent.left = node.left;
                        node.right.parent = node.left;
                        node = null;
                        size--;
                        return true;
                    }
                    node.parent.left = node.right;
                    node.right.parent = node.parent;
                    node = null;
                    size--;
                    return true;
                }
                node.parent.right = node.right;
                node.right.parent = node.parent;
                //node.right.left = node.left;
                node = null;
                size--;
                return true;
            }
        } return false;
    }


    // // brukes til rød-svarte trær (tilleggsoppgave)
    // private static byte BLACK = 1;
    // private static byte RED = 2;
    //
    // private class RBNode extends Node {
    //     private byte colour = 0;
    //     boolean isRed( ) { return colour == RED; }
    //     boolean isBlack( ) { return colour == BLACK; }
    //     void setToRed( ) { colour = RED; }
    //     void setToBlack( ) { colour = BLACK; }
    // }
}
