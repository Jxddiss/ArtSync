package com.artcorp.artsync.dto;

import java.util.Objects;

public class Choice {
    private int index;
    private String text;

    public Choice(){
    }

    public Choice(int index, String text) {
        this.index = index;
        this.text = text;

    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Choice choice = (Choice) o;
        return index == choice.index && Objects.equals(text, choice.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, text);
    }
}
