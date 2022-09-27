package es.uned.lsi.eped.pract2021_2022;

import es.uned.lsi.eped.DataStructures.BTree;
import es.uned.lsi.eped.DataStructures.BTreeIF;
import es.uned.lsi.eped.DataStructures.Collection;
import es.uned.lsi.eped.DataStructures.IteratorIF;
import es.uned.lsi.eped.DataStructures.Stack;
import es.uned.lsi.eped.DataStructures.StackIF;
import es.uned.lsi.eped.DataStructures.BTreeIF.IteratorModes;

public class SparseArrayBTree<E> extends Collection<E> implements SparseArrayIF<E> {

	protected final BTreeIF<IndexedPair<E>> btree;

	public SparseArrayBTree() {
		super();
		btree = new BTree<IndexedPair<E>>();
	}

	/**
	 * Convierte un valor entero en su representación binaria Devuelve una pila de
	 * valores booleanos en base a dicha representación true para el 1 y false para
	 * el 0 El valor de la cima de la pila corresponde al bit más significativo
	 * 
	 * @param n un númmero entero
	 * @return una pila con valores booleanos
	 */
	private StackIF<Boolean> num2bin(int n) {
		Stack<Boolean> salida = new Stack<Boolean>();
		if (n == 0) {
			salida.push(false);
		} else {
			while (n != 0) {
				salida.push((n % 2) == 1);
				n = n / 2;
			}
		}
		return salida;
	}

	/**
	 * Comprueba si existe un nodo creado asociado a un índice
	 * 
	 * @param index un valor entero de índice
	 * @return true si existe un nodo con dicho valor de índice
	 */
	private boolean existIndex(int index) {
		return get(index) != null;
	}

	/**
	 * Comprueba si un nodo contiene algú elemento.
	 * Comrueba la raíz, el hijo izquierdo y el hijo derecho
	 * 
	 * @param tree el árbol a comprobar
	 * @return true si la raíz o alguno de sus hijos es distinto de null
	 */
	private boolean containsElements(BTreeIF<IndexedPair<E>> tree) {
		if (tree == null) {
			return false;
		}
		if (tree.getNumChildren() == 0) {
			return tree.getRoot() != null;
		}
		return tree.getRoot() != null || containsElements(tree.getRightChild()) || containsElements(tree.getLeftChild());
	}
	
	/**
	 * Devuelve un nodo asociado a un valor de índice
	 * 
	 * @param index un valor entero de índice
	 * @return el nodo/árbol asociado a dicho valor de índice
	 */
	private BTreeIF<IndexedPair<E>> getNode(int index) {
		BTreeIF<IndexedPair<E>> currentNode = btree;
		StackIF<Boolean> stackBin = num2bin(index);
		while (!stackBin.isEmpty()) {
			if (stackBin.getTop() == true) {
				if (currentNode.getRightChild() == null) {
					return null;
				}
				currentNode = currentNode.getRightChild();
			} else {
				if (currentNode.getLeftChild() == null) {
					return null;
				}
				currentNode = currentNode.getLeftChild();
			}
			stackBin.pop();
		}
		return currentNode;
	}

	/**
	 * Clase privada que genera un iterador de índices del árbol binario.
	 * Para la operación getNext() se comprueba si dicho nodo no apunta a null de forma recursiva, en cuyo caso
	 * devuelve su índice.
	 * @author José Mendoza
	 *
	 */
	private class IndexIterator implements IteratorIF<Integer> {

		private IteratorIF<IndexedPair<E>> iterator;

		IndexIterator(IteratorIF<IndexedPair<E>> iterator) {
			this.iterator = iterator;
		}

		@Override
		public Integer getNext() {
			IndexedPair<E> pair = null;
			while (pair == null) {
				pair = iterator.getNext();
			}
			return pair.getIndex();
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public void reset() {
			iterator.reset();
		}

	}

	/**
	 * Clase privada que genera un iterador de nodos del árbol binario.
	 * Para la operación getNext() se comprueba si dicho nodo no apunta a null de forma recursiva, en cuyo caso
	 * devuelve su valor.
	 * @author José Mendoza
	 *
	 */
	private class Iterator implements IteratorIF<E> {

		private IteratorIF<IndexedPair<E>> iterator;

		public Iterator(IteratorIF<IndexedPair<E>> iterator) {
			this.iterator = iterator;
		}

		@Override
		public E getNext() {
			IndexedPair<E> pair = null;
			while (pair == null) {
				pair = iterator.getNext();
			}
			return pair.getValue();
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public void reset() {
			iterator.reset();
		}

	}

	@Override
	public IteratorIF<E> iterator() {
		return new Iterator(btree.iterator(IteratorModes.BREADTH));
	}

	@Override
	public IteratorIF<Integer> indexIterator() {
		return new IndexIterator(btree.iterator(IteratorModes.BREADTH));
	}
	
	@Override
	public void set(int index, E elem) {
		IndexedPair<E> pair = new IndexedPair<E>(index, elem);
		BTreeIF<IndexedPair<E>> currentNode = btree;
		StackIF<Boolean> stackBin = num2bin(index);
		while (!stackBin.isEmpty()) {
			if (stackBin.getTop() == true) {
				if (currentNode.getRightChild() == null) {
					currentNode.setRightChild(new BTree<IndexedPair<E>>());
				}
				currentNode = currentNode.getRightChild();
			} else {
				if (currentNode.getLeftChild() == null) {
					currentNode.setLeftChild(new BTree<IndexedPair<E>>());
				}
				currentNode = currentNode.getLeftChild();
			}
			stackBin.pop();
		}

		if (currentNode.getRoot() == null) {
			size++;
		}
		currentNode.setRoot(pair);
	}

	@Override
	public E get(int index) {
		BTreeIF<IndexedPair<E>> currentNode = btree;
		StackIF<Boolean> stackBin = num2bin(index);
		while (!stackBin.isEmpty()) {
			boolean bit = stackBin.getTop(); 
			stackBin.pop();
			if (bit) {
				if (currentNode.getRightChild() == null) {
					return null;
				}
				currentNode = currentNode.getRightChild();
			} else {
				if (currentNode.getLeftChild() == null) {
					return null;
				}
				currentNode = currentNode.getLeftChild();
			}
		}
		E element = null;
		if (currentNode.getRoot() != null) {
			element =  currentNode.getRoot().getValue();
		}
		return element;
	}

	@Override
	public void delete(int index) {
		BTreeIF<IndexedPair<E>> nodeToDelete = getNode(index);
		BTreeIF<IndexedPair<E>> currentNode = btree;
		StackIF<Boolean> stackBin = num2bin(index);
		if (!existIndex(index)) {
			return;
		}
		nodeToDelete.setRoot(null);
		this.size--;
		if (nodeToDelete.getNumChildren() != 0) {
			return;
		}
		while (stackBin.size() != 0) {
			boolean bit = stackBin.getTop();
			stackBin.pop();
			if (bit) {
				if (!containsElements(currentNode.getRightChild())) {
					currentNode.setRightChild(null);
					return;
				}
				currentNode = currentNode.getRightChild();
			} else {
				if (!containsElements(currentNode.getLeftChild())) {
					currentNode.setLeftChild(null);
					return;
				}
				currentNode = currentNode.getLeftChild();
			}
		}
	}

	@Override
	public void clear() {
		super.clear();
		btree.clear();
	}

	@Override
	public boolean contains(E e) {
		IteratorIF<E> it = iterator();
		while (it.hasNext()) {
			E value = it.getNext();
			if (value.equals(e)) {
				return true;
			}
		}
		return false;
	}
}
