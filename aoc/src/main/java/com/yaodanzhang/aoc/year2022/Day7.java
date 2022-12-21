package com.yaodanzhang.aoc.year2022;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Integer.parseInt;

import com.google.common.base.Splitter;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.yaodanzhang.aoc.Puzzle;
import com.yaodanzhang.aoc.input.AllLines;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutionException;
import lombok.Getter;

public class Day7 implements Puzzle<Integer> {

  public static final Splitter COMMAND_SPLITTER = Splitter.on(" ").trimResults().omitEmptyStrings();
  public static final int MAX_TO_SUM = 100000;

  private final Dir root;

  Day7(Path inputFile) {
    List<String> input = new AllLines(inputFile).read();
    root = Dir.root();
    List<Command> commands = input.stream()
        .map(COMMAND_SPLITTER::splitToList)
        .map(this::commandFactory)
        .toList();

    FileItem current = root;
    for (Command command : commands) {
      current = command.executeOn(current);
    }
  }

  @Override
  public Integer part1Result() {
    return dirSizeSum(root, 0);
  }

  private int dirSizeSum(Dir dir, int sum) {
    if (dir.getSize() <= MAX_TO_SUM) {
      sum += dir.getSize();
    }
    List<Dir> childDirs = getChildDirs(dir);

    for (Dir child : childDirs) {
      sum = dirSizeSum(child, sum);
    }

    return sum;
  }

  private static List<Dir> getChildDirs(Dir dir) {
    return dir.children.stream()
        .filter(FileItem::isDir)
        .map(Dir.class::cast)
        .toList();
  }

  @Override
  public Integer part2Result() {
    int neededSpace = 30000000 - (70000000 - root.getSize());
    return smallestDeletableDirSize(root, neededSpace, Integer.MAX_VALUE);
  }

  private int smallestDeletableDirSize(Dir current, int neededSpace, int currentSmallest) {
    if (current.isDir()
        && current.getSize() >= neededSpace
        && current.getSize() < currentSmallest) {
      currentSmallest = current.getSize();
    }

    for (Dir child : getChildDirs(current)) {
      currentSmallest = smallestDeletableDirSize(child, neededSpace, currentSmallest);
    }

    return currentSmallest;
  }

  private Command commandFactory(List<String> commandInput) {
    if (commandInput.get(0).equals("$")) {
      if (commandInput.get(1).equals("cd")) {
        if (commandInput.get(2).equals("/")) {
          return new JumpDir(root);
        }
        return new ChangeDir(commandInput.get(2));
      }

      if (commandInput.get(1).equals("ls")) {
        return new ListDir();
      }
    }

    if (commandInput.get(0).equals("dir")) {
      return new ListChildDir(commandInput.get(1));
    }

    return new ListChildFile(parseInt(commandInput.get(0)), commandInput.get(1));
  }

  private interface Command {

    FileItem executeOn(FileItem current);
  }

  private record ChangeDir(String dir) implements Command {
    @Override
      public FileItem executeOn(FileItem current) {
        if (dir.equals("..")) {
          return current.parent;
        }
        return current.children.stream()
            .filter(it -> it.name.equals(dir))
            .findFirst()
            .orElseThrow();
      }
    }

  private record JumpDir(FileItem dir) implements Command {
    @Override
      public FileItem executeOn(FileItem current) {
        return dir;
      }
    }

  private static class ListDir implements Command {

    @Override
    public FileItem executeOn(FileItem current) {
      return current;
    }
  }

  private record ListChildDir(String childDir) implements Command {
    @Override
      public FileItem executeOn(FileItem current) {
        return current.addChild(new Dir(current, childDir));
      }
    }

  private record ListChildFile(int size, String childName) implements Command {
    @Override
    public FileItem executeOn(FileItem current) {
      return current.addChild(new File(current, childName, size));
    }
  }

  private abstract static class FileItem {

    @Getter
    protected final FileItem parent;
    @Getter
    protected final String name;
    protected final List<FileItem> children;

    protected FileItem(FileItem parent, String name) {
      this.parent = parent;
      this.name = name;
      children = newArrayList();
    }

    public FileItem addChild(FileItem child) {
      children.add(child);
      return this;
    }

    public abstract boolean isDir();

    public abstract int getSize();
  }

  private static class File extends FileItem {

    @Getter
    private final int size;

    protected File(FileItem parent, String name, int size) {
      super(parent, name);
      this.size = size;
    }

    @Override
    public boolean isDir() {
      return false;
    }
  }

  private static class Dir extends FileItem {

    private final Cache<Dir, Integer> cachedSize;

    protected Dir(FileItem parent, String name) {
      super(parent, name);
      cachedSize = CacheBuilder.newBuilder()
          .maximumSize(1)
          .build(new CacheLoader<>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public Integer load(Dir key) {
              return key.calculateSize();
            }
          });
    }

    @Override
    public boolean isDir() {
      return true;
    }

    @Override
    public int getSize() {
      try {
        return cachedSize.get(this, this::calculateSize);
      } catch (ExecutionException e) {
        throw new RuntimeException(e);
      }
    }

    public static Dir root() {
      return new Dir(null, "/");
    }

    private Integer calculateSize() {
      return children.stream()
          .map(FileItem::getSize)
          .reduce(Integer::sum)
          .orElse(0);
    }

  }

}
