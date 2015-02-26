package com.thoughtworks.kata.word;

import com.google.common.base.Joiner;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Splitter.fixedLength;
import static com.google.common.base.Splitter.on;
import static com.google.common.collect.Lists.newArrayList;

public class Wrap {

    private static final Joiner JOINER = Joiner.on("|");

    public String wrap(String word, int size) {
        checkArgument(size > 0);
        if (word == null) {
            return "";
        }
        if (word.length() <= size) {
            return word;
        }
        if (!word.contains(" ")) {
            return JOINER.join(fixedLength(size).split(word));
        }

        List<String> splitOnSpace = newArrayList(on(" ").omitEmptyStrings().split(word));
        for (int lineEnd = 1, lineBegin = 0; lineEnd < splitOnSpace.size(); lineEnd++) {

        }
        return JOINER.join(splitOnSpace);
    }
}
