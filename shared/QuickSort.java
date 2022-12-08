package shared;

import java.util.ArrayList;
import java.util.function.BiFunction;

public class QuickSort<T> {
  ArrayList<T> items = new ArrayList<>();

  BiFunction<T, T, Boolean> compare;

  public static final <T> BiFunction<T, T, Boolean> strings() {
    return (a, b) -> {
      int index   = 0;
      String aStr = a.toString();
      String bStr = b.toString();
      while (aStr.substring(index).equals(bStr.substring(index)) &&
             index < aStr.length() && index < bStr.length()) {
        index++;
      }
      return aStr.compareTo(bStr) < 0;
    };
  };

  public QuickSort(Iterable<T> items, BiFunction<T, T, Boolean> compare) {
    for (T item : items) {
      this.items.add(item);
    }

    this.compare = compare;
  }

  public QuickSort(T[] items, BiFunction<T, T, Boolean> compare) {
    for (T item : items) {
      this.items.add(item);
    }

    this.compare = compare;
  }

  public QuickSort(Iterable<T> items) {
    for (T item : items) {
      this.items.add(item);
    }

    this.compare = strings();
  }

  public QuickSort(T[] items) {
    for (T item : items) {
      this.items.add(item);
    }

    this.compare = strings();
  }

  /**
   * Swaps two items in the list.
   */
  public void swap(int i, int j) {
    T temp = items.get(i);
    items.set(i, items.get(j));
    items.set(j, temp);
  }

  public Iterable<T> getItems() { return items; }

  /**
   * Sorts the list.
   */
  public Iterable<T> sort() { return sort(0, items.size() - 1); }

  /**
   * Sorts the list between the given indices.
   */
  public Iterable<T> sort(int start, int end) {
    if (start >= end) {
      return items;
    }

    int pivot = partition(start, end);
    sort(start, pivot - 1);
    sort(pivot + 1, end);

    return items;
  }

  /**
   * Partitions the list between the given indices.
   */
  public int partition(int start, int end) {
    int pivot = start;
    int left  = start + 1;
    int right = end;

    while (left <= right) {
      while (left <= end && compare.apply(items.get(left), items.get(pivot))) {
        left++;
      }

      while (right > start && compare.apply(items.get(pivot), items.get(right))) {
        right--;
      }

      if (left <= right) {
        swap(left, right);
        left++;
        right--;
      }
    }

    swap(pivot, right);
    return right;
  }
}
