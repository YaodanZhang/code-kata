package com.yaodanzhang.aoc.algo;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import com.google.common.base.Joiner;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import lombok.Getter;

public class ListCompiler<T> {

  private final Function<String, T> itemTransformer;

  public ListCompiler(Function<String, T> itemTransformer) {
    this.itemTransformer = itemTransformer;
  }

  public Item compile(String input) {
    MultiChildrenItem parent = new MultiChildrenItem();
    Deque<MultiChildrenItem> stack = new ArrayDeque<>();
    stack.push(parent);
    recursiveCompile(input.substring(1), stack, newArrayList());
    return parent;
  }

  @SuppressWarnings("DataFlowIssue")
  private void recursiveCompile(String input, Deque<MultiChildrenItem> stack, List<Character> queue) {
    String sanitizedInput = trimToEmpty(input);
    if (sanitizedInput.isBlank()) {
      return;
    }
    char firstChar = sanitizedInput.charAt(0);
    if (firstChar == '[') {
      MultiChildrenItem newParent = new MultiChildrenItem();
      stack.peek().addChild(newParent);
      stack.push(newParent);
    } else if (firstChar == ']') {
      addItem(queue, stack.pop());
    } else if (firstChar == ',') {
      addItem(queue, stack.peek());
    } else {
      queue.add(firstChar);
    }
    recursiveCompile(sanitizedInput.substring(1), stack, queue);
  }

  private void addItem(List<Character> queue, MultiChildrenItem parent) {
    if (!queue.isEmpty()) {
      String itemInput = queue.stream()
          .mapToInt(Character::charValue)
          .collect(StringBuilder::new,
              StringBuilder::appendCodePoint,
              StringBuilder::append)
          .toString();
      SingleValueItem<T> item = new SingleValueItem<>(
          itemTransformer.apply(trimToEmpty(itemInput)));
      parent.addChild(item);
      queue.clear();
    }
  }

  public interface Item {

  }

  public static class SingleValueItem<E> implements Item {

    @Getter
    private final E value;

    public SingleValueItem(E value) {
      this.value = value;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      SingleValueItem that = (SingleValueItem) o;
      return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
      return Objects.hash(value);
    }

    @Override
    public String toString() {
      return value.toString();
    }
  }

  public static class MultiChildrenItem implements Item {

    @Getter
    private final List<Item> children;

    public MultiChildrenItem() {
      this.children = newArrayList();
    }

    public MultiChildrenItem(List<Item> children) {
      this.children = children;
    }

    public void addChild(Item child) {
      this.children.add(child);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      MultiChildrenItem that = (MultiChildrenItem) o;
      return children.equals(that.children);
    }

    @Override
    public int hashCode() {
      return Objects.hash(children);
    }

    @Override
    public String toString() {
      return format("[%s]", Joiner.on(',').join(children));
    }
  }
}
