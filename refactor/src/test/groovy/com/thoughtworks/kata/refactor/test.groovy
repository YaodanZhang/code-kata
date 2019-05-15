package com.thoughtworks.kata.refactor

def old = new File('/Users/twer/Library/Preferences/WebStorm2018.2/scratches/starter.sorted.txt').readLines() as Set<String>
def newFile = new File('/Users/twer/Library/Preferences/WebStorm2018.2/scratches/new.sorted.txt').readLines() as Set<String>

def diff = new HashSet<String>()

old.removeAll(newFile)

old.forEach({it ->
    println(it)
})