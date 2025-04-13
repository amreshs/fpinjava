package com.fpinjava.advancedtrees.exercise11_04;


import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import com.fpinjava.common.Tuple;
import com.sun.org.apache.xpath.internal.objects.XString;

import java.util.HashMap;


public class Map<K, V> {

  protected final Tree<MapEntry<Integer, List<Tuple<K, V>>>> delegate;

  private Map() {
    this.delegate = Tree.empty();
  }

  private Result<List<Tuple<K,V>>> getAll(K key){
    return delegate.get(MapEntry.mapEntry(key.hashCode()))
                   .flatMap(result -> result.value.map(lt -> lt.map(t->t)));
  }

  public Map(Tree<MapEntry<Integer, List<Tuple<K, V>>>> delegate) {
    this.delegate = delegate;
  }

  public Map<K, V> add(K key, V value) {
    Tuple<K,V> tpl = new Tuple<K, V>(key, value);
    List<Tuple<K, V>> ltkv = getAll(key).map(lt -> lt.foldLeft(List.list(tpl), l -> t -> t._1.equals(key)?l: l.cons(t))).getOrElse(List.list(tpl));

    return new Map<>(delegate.insert(MapEntry.mapEntry(key.hashCode(), ltkv)));
  }

  public boolean contains(K key) {

    return getAll(key).map(lt -> lt.exists(l -> l.equals(key))).getOrElse(false);
  }

  public Map<K, V> remove(K key) {
    List<Tuple<K,V>> ltkv = getAll(key).map(lt->lt.foldLeft(List.<Tuple<K,V>>list(), l->t-> t._1.equals(key)
            ?l
            :l.cons(t))).getOrElse(List.<Tuple<K,V>>list());

    return ltkv.isEmpty()?new Map<>(delegate.delete(MapEntry.mapEntry(key.hashCode()))): new Map<>(delegate.insert(MapEntry.mapEntry(key.hashCode(), ltkv)));
  }

  public Result<Tuple<K, V>> get(K key) {
    return getAll(key).flatMap(lt -> lt.first(t -> t._1.equals(key)));
  }

  public boolean isEmpty() {
    return delegate.isEmpty();
  }

  public static <K, V> Map<K, V> empty() {
    return new Map<>();
  }

  @Override
  public String toString() {
    return String.format("Map[%s]", this.delegate);
  }

  public static void main(String[] args) {
    class Temp{
      public final int first;
      public final String second;

      public Temp(int first, String second) {
        this.first = first;
        this.second = second;
      }

      public Temp(){
        this.first = 0;
        this.second = "";
      }
    }

    Map<Temp, Integer> mapFromTree = empty();;
    //Map<String, Integer> mapFromTree = empty();
    mapFromTree = mapFromTree.add(new Temp(11, "A"), 1);
    mapFromTree = mapFromTree.add(new Temp(22, "B"), 2);
    mapFromTree = mapFromTree.add(new Temp(33, "C"), 3);
    mapFromTree = mapFromTree.add(new Temp(11, "A"), 26);

    System.out.println(mapFromTree.getAll(new Temp(11, "A")));
    System.out.println(mapFromTree.get(new Temp(11, "A")));
  }
}
