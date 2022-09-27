package es.uned.lsi.eped.pract2021_2022;

import es.uned.lsi.eped.DataStructures.Collection;
import es.uned.lsi.eped.DataStructures.IteratorIF;
import es.uned.lsi.eped.DataStructures.List;

public class SparseArraySequence<E> extends Collection<E> implements SparseArrayIF<E> {

	protected List<IndexedPair<E>> sequence;
	
	public SparseArraySequence() {
		super();
		sequence = new List<IndexedPair<E>>();
	}
	
	/**
	 * Devuelve la posición en orden ascendente de índice
	 * @param index un índice de valor entero
	 * @return un entero indicando la posición en una secuencia ascendente
	 */
	private int sequencePos(int index) {
		IteratorIF<Integer> it = indexIterator();
		int counter = 1;
		while(it.hasNext()) {
			int pos = it.getNext();
			counter++;
			if(index <= pos) {
				return counter - 1;
			}
		}
		return sequence.size() + 1;
	}
	
	/**
	 * Comprueba la existencia de un índice en la secuencia de pares
	 * @param index el índice a comprobar
	 * @return true si existe index, falso en caso contrario
	 */
	private boolean existIndex(int index) {
		int sequencePos = sequencePos(index);
		if(sequencePos > sequence.size()) {
			return false;
		}
		return (index == sequence.get(sequencePos).getIndex());
	}
	
	/**
	 * Clase privada que genera un iterador de índices de una secuencia de 
	 * pares índice-valor ordenados de forma ascendente de índice
	 * @author José Mendoza
	 *
	 */
	private class IndexIterator implements IteratorIF<Integer> {
		
		private IteratorIF<IndexedPair<E>> iterator;
		
		IndexIterator(IteratorIF<IndexedPair<E>> iterator){
			this.iterator = iterator;
		}
		@Override
		public Integer getNext() {
			return iterator.getNext().getIndex();
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
	 * Clase privada que genera un iterador de elementos de una secuencia de 
	 * pares índice-valor ordenados de forma ascendente de índice
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
			return iterator.getNext().getValue();
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
		return new Iterator(sequence.iterator());
	}
	
	@Override
	public IteratorIF<Integer> indexIterator() {
		return new IndexIterator(sequence.iterator());
	}

	@Override
	public void set(int index, E elem) {
		IndexedPair<E> pair = new IndexedPair<E>(index,elem);
		int sequencePos = sequencePos(index);
		if(existIndex(index)) {
			sequence.set(sequencePos, pair);
		}else {
			sequence.insert(sequencePos, pair);
			size++;
		}
	}	

	@Override
	public E get(int index) {
		if(!existIndex(index)) {
			return null;
		}
		int sequencePos = sequencePos(index);
		return sequence.get(sequencePos).getValue();
	}

	@Override
	public void delete(int index) {
		if(!existIndex(index)) {
			return;
		}
		int sequencePos = sequencePos(index);
		sequence.remove(sequencePos);
		size--;
	}
	
	@Override
	public void clear() {
		super.clear();
		sequence.clear();
	}

	@Override
	public boolean contains(E e) {
		IteratorIF<IndexedPair<E>> it = sequence.iterator();
		while(it.hasNext()) {
			IndexedPair<E> pair = it.getNext();
			if(pair.getValue().equals(e)) {
				return true;
			}
		}
		return false;
	}
}
