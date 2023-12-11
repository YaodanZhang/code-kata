package com.yaodanzhang.aoc.algo;

import java.util.List;
import lombok.Getter;

public record Coordinate(@Getter int x, @Getter int y) {

  public int arrayIndex(List<List<Character>> input) {
    return y * input.get(0).size() + x;
  }

  public boolean inBetween(Coordinate start, Coordinate end) {
    return this.x >= start.x() && this.x <= end.x()
        && this.y >= start.y() && this.y <= end.y();
  }
}
