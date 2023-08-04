import java.util.*;

@SuppressWarnings("unchecked")
public class DoubleLinkedList<E> implements List<E> {
    //List has to use the same type parameter as DoubleLinkedList in this case
    //Data fields
    private Node<E> mHead;
    private Node<E> mTail;
    private int mSize;

    public DoubleLinkedList() {
        mHead = null;
        mTail = null;
        mSize = 0;
    }

    public void add(int index, E element) {
        if(index < 0 || index > mSize)
            throw new IndexOutOfBoundsException();
        //Use list iterator to go to the correct index
        ListIter iter = new ListIter(index);
        iter.add(element);
    }

    public boolean add(E element) {
        if(element != null) {
            ListIter iter = new ListIter(mSize);
            iter.add(element);
            return true;
        }
        return false;
    }

    public E get(int index) {
        if(index < 0 || index >= mSize)
            throw new IndexOutOfBoundsException();
        ListIter iter = new ListIter(index);
        return iter.next();
    }

    public E set(int index, E element) {
        if(index < 0 || index >= mSize)
            throw new IndexOutOfBoundsException();
        ListIter iter = new ListIter(index);
        E temp = iter.next();
        iter.set(element);
        return temp;
    }

    public int size() {
        return mSize;
    }

    public boolean isEmpty() {
        return mSize == 0;
    }
    public int indexOf(Object o) {
        if(mSize == 0)
            return -1;
        int index = 0;
        Node<E> temp = mHead;
        for(int i = 0; i < mSize; i++) {
            if(temp.mData.equals((E)o))
                return index;
            temp = temp.mNext;
            index++;
        }
        return -1;
    }

    public boolean contains(Object o) {
        if(isEmpty())
            return false;
        return indexOf(o) >= 0;
    }

    public int lastIndexOf(Object o) {
        if(mSize == 0)
            return -1;
        int index = mSize - 1;
        Node<E> temp = mTail;
        for(int i = mSize - 1; i >= 0; i--) {
            if(temp.mData.equals((E)o))
                return index;
            temp = temp.mPrev;
            index--;
        }
        return -1;
    }

    public String toString() {
        String str = "[";
        if(mSize == 0)
            return "[]";

        for(int i = 0; i < mSize - 1; i++) {
            str += get(i).toString() + ", ";
        }
        str += get(mSize-1).toString() + "]";
        return str;
    }

    public Iterator<E> iterator() {
        return new ListIter(0);
    }

    public ListIterator<E> listIterator() {
        return new ListIter(0);
    }

    public ListIterator<E> listIterator(int index) {
        return new ListIter(index);
    }

    public ListIterator listIterator(ListIterator iter) {
        return new ListIter((ListIter) iter); //downcasting
    }

    public E getFirst() {
        return mHead.mData;
    }

    public E getLast() {
        return mTail.mData;
    }

    public void clear() {
        mHead = null;
        mTail = null;
        mSize = 0;
    }

    public E remove(int index) {
        if(index < 0 || index >= mSize)
            throw new IndexOutOfBoundsException();
        ListIter iter = new ListIter(index);
        E temp = iter.next();
        iter.remove();
        return temp;
    }

    public boolean remove(Object obj) {
        if(obj == null)
            return false;
        Node<E> temp = mHead;
        for(int i = 0; i < mSize; i++) {
            if(temp.mData.equals((E) obj)) {
                remove(i);
                return true;
            }
            temp = temp.mNext;
        }
        return false;
    }

    private void deleteElement(Node<E> element) {
        Node<E> nextElement = element.mNext;
        Node<E> prevElement = element.mPrev;

        if(nextElement == null)
            mTail = prevElement;
        else {
            nextElement.mPrev = prevElement;
            element.mPrev = null;
        }
        if(prevElement == null)
            mHead = nextElement;
        else {
            prevElement.mNext = nextElement;
            element.mPrev = null;
        }

        mSize--;
        element.mData = null;
    }



    //Methods below are not implemented for now

    public boolean retainAll(Collection c) {
        return false;
    }

    public boolean removeAll(Collection c) {
        return false;
    }

    public boolean containsAll(Collection c) {
        return false;
    }

    public Object[] toArray(Object[] a) {
        return new Object[0];
    }
    public Object[] toArray() {
        return new Object[0];
    }

    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    public boolean addAll(Collection c) {
        return false;
    }

    public boolean addAll(int index, Collection c) {
        return false;
    }




    //Node inner class
    private static class Node<E> {
        private E mData;
        private Node<E> mNext = null;
        private Node<E> mPrev = null;

        private Node(E dataItem) {
            mData = dataItem;
        }
        private void setData(E data) {
            mData = data;
        }

        private void setNext(Node<E> next) {
            mNext = next;
        }

        private void setPrev(Node<E> prev) {
            mPrev = prev;
        }

        private E getData() {
            return mData;
        }

        private Node<E> getNext() {
            return mNext;
        }

        private Node<E> getPrev() {
            return mPrev;
        }

        public String toString() {
            return mData.toString();
        }

    }




    //ListIterator inner class
    private class ListIter implements ListIterator<E> {
        private Node<E> nextItem;
        private Node<E> lastItemReturned;
        private int index = 0;

        //Constructors
        public ListIter() {
            nextItem = null;
            lastItemReturned = null;
        }
        public ListIter(int i) {
            if(i < 0 || i > mSize)
                throw new IndexOutOfBoundsException("Invalid index " + i);
            lastItemReturned = null;
            if(i == mSize) {
                nextItem = null;
                index = mSize;
            }
            else {
                nextItem = mHead;
                for(index = 0; index < i; index++) {
                    lastItemReturned = nextItem;
                    nextItem = nextItem.mNext;
                }
            }
        }

        public ListIter(ListIter other) {
            nextItem = other.nextItem;
            lastItemReturned = other.lastItemReturned;
            index = other.index;
        }

        public boolean hasNext() {
            if(mSize == 0)
                return false;
            else if(nextItem == null)
                return false;
            return true;
        }

        public boolean hasPrevious() {
            if(mSize == 0)
                return false;
            return (nextItem == null && mSize != 0) || nextItem.mPrev != null;
        }

        public int nextIndex() {
            return index;
        }

        public int previousIndex() {
            return index-1;
        }

        public void set(E data) {
            if(lastItemReturned == null)
                throw new IllegalStateException();
            lastItemReturned.mData = data;
        }

        public E next() {
            if(!hasNext())
                throw new NoSuchElementException();
            Node<E> temp;
            temp = nextItem;
            lastItemReturned = nextItem;
            nextItem = nextItem.mNext;
            index++;
            return temp.mData;
        }

        public E previous() {
            if(!hasPrevious())
                throw new NoSuchElementException();
            else if (nextItem == null)
                nextItem = mTail;
            else
                nextItem = nextItem.mPrev;
            lastItemReturned = nextItem;
            index--;
            return lastItemReturned.mData;
        }

        public void add(E data) {
            //adds the node where the iterator is located
            Node<E> newNode = new Node(data);

            if(mHead == null) { //empty list case
                mHead = newNode;
                mTail = newNode;
                mSize++;
            }
            else if(nextItem == mHead) { //at the head case and add at beginning
                newNode.mNext = nextItem;
                nextItem.mPrev = newNode; //was null before
                mHead = newNode;
                lastItemReturned = newNode;
                mSize++;
                index++;
            }
            else if(nextItem == null) { //at the tail case add at end
                mTail.mNext = newNode;
                newNode.mPrev = mTail;
                mTail = newNode;
                mSize++;
                index++;
            }
            else  { //add in the middle
                newNode.mPrev = nextItem.mPrev;
                nextItem.mPrev.mNext = newNode;
                newNode.mNext = nextItem;
                nextItem.mPrev = newNode;
                mSize++;
                index++;
            }

        }
        public void remove() {
            if(lastItemReturned == null)
                throw new IllegalStateException();
            Node<E> temp = lastItemReturned.mNext;
            deleteElement(lastItemReturned);
            if(nextItem == lastItemReturned)
                nextItem = temp;
            else
                index--;
            lastItemReturned = null;
            mSize--;
        }
    }
}