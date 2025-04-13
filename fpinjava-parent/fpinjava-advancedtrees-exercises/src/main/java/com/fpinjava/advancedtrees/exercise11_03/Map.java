package com.fpinjava.advancedtrees.exercise11_03;


import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Result;

import static com.fpinjava.advancedtrees.exercise11_03.MapEntry.mapEntry;


public class Map<K extends Comparable<K>, V> {

  protected final Tree<MapEntry<K, V>> delegate;

  private Map() {
    this.delegate = Tree.empty();
  }

  private Map(Tree<MapEntry<K, V>> delegate) {
    this.delegate = delegate;
  }

  public Map<K, V> add(K key, V value) {
    return new Map<>(delegate.insert(mapEntry(key, value)));
  }

  public boolean contains(K key) {
    return delegate.member(mapEntry(key));
  }

  public Map<K, V> remove(K key) {
    return new Map<>(delegate.delete(mapEntry(key)));
  }

  public MapEntry<K, V> max() {
    return delegate.max();
  }

  public MapEntry<K, V> min() {
    return delegate.min();
  }

  public Result<MapEntry<K, V>> get(K key) {
    return delegate.get(mapEntry(key));
  }

  public boolean isEmpty() {
    return delegate.isEmpty();
  }

  public List<V> values() {
    return List.sequence(delegate.foldInReverseOrder(List.<Result<V>>list(), lst1->vl->lst2-> List.concat(lst2, lst1.cons(vl.value)))).getOrElse(List.list());
  }

  public static <K extends Comparable<K>, V> Map<K, V> empty() {
    return new Map<>();
  }

  public static void main(String[] args) {
    Map<String, Integer> mapFromTree = empty();
    mapFromTree = mapFromTree.add("A", 1);
    mapFromTree = mapFromTree.add("B", 2);
    mapFromTree = mapFromTree.add("C", 3);
    mapFromTree = mapFromTree.add("Z", 26);

    System.out.println(mapFromTree.values());
  }

}
