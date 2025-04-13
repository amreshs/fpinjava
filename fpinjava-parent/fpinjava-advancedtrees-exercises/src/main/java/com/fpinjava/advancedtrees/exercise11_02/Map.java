package com.fpinjava.advancedtrees.exercise11_02;
import com.fpinjava.common.Result;

public class Map<K extends Comparable<K>, V>{
  protected final Tree<MapEntry<K,V>> delegate;

  private Map(){
    this.delegate = Tree.empty();
  }

  private Map(Tree<MapEntry<K,V>> delegate){
    this.delegate = delegate;
  }

  public Map<K,V> add(K key, V value){
    return new Map<>(delegate.insert(MapEntry.mapEntry(key,value)));
  }

  public Map<K,V> remove(K key){
    return new Map<>(delegate.delete(MapEntry.mapEntry(key)));
  }

  public boolean contains(K key){
    return delegate.member(MapEntry.mapEntry(key));
  }

  public MapEntry<K,V> max(){
    return delegate.max();
  }

  public MapEntry<K,V> min(){
    return delegate.min();
  }

  public Result<MapEntry<K, V>> get(K key){
    return delegate.get(MapEntry.mapEntry(key));
  }

  public boolean isEmpty(){
    return delegate.isEmpty();
  }

  public static <K extends Comparable<K>, V> Map<K,V> empty(){
    return new Map<>();
  }

  public static void main(String[] args) {
    Map<String, Integer> mapFromTree = empty();
    mapFromTree.add("A", 1);
    mapFromTree.add("B", 2);
    mapFromTree.add("C", 3);
    mapFromTree.add("Z", 26);

    System.out.println(mapFromTree.max());
    System.out.println(mapFromTree.min());
    System.out.println(mapFromTree.get("Z"));
  }
}
/*
public class Map<K extends Comparable<K>, V> {

  protected final Tree<MapEntry<K, V>> delegate;

  private Map() {
    this.delegate = Tree.empty();
  }

  private Map(Tree<MapEntry<K, V>> delegate) {
    this.delegate = delegate;
  }

  public Map<K, V> add(K key, V value) {
    throw new IllegalStateException("To be implemented");
  }

  public boolean contains(K key) {
    throw new IllegalStateException("To be implemented");
  }

  public Map<K, V> remove(K key) {
    throw new IllegalStateException("To be implemented");
  }

  public boolean isEmpty() {
    throw new IllegalStateException("To be implemented");
  }

  public static <K extends Comparable<K>, V> Map<K, V> empty() {
    return new Map<>();
  }
}
*/