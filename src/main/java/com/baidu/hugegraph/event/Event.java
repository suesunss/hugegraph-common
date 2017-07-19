package com.baidu.hugegraph.event;

import java.util.Arrays;
import java.util.Collections;

import com.baidu.hugegraph.util.E;

public class Event extends java.util.EventObject {

    private static final long serialVersionUID = 1625973849208342813L;

    private String name;
    private Object[] args;

    public Event(Object source, String event) {
        this(source, event, Collections.emptyList().toArray());
    }

    public Event(Object source, String event, Object... args) {
        super(source);
        this.name = event;
        this.args = args;
    }

    public String name() {
        return this.name;
    }

    public Object[] args() {
        return this.args;
    }

    public void checkArgs(Class<?>... classes) throws IllegalArgumentException {
        E.checkArgument(this.args.length == classes.length,
                        "The args count of event '%s' should be %s(actual %s)",
                        this.name, classes.length, this.args.length);
        int i = 0;
        for (Class<?> c : classes) {
            Object arg = this.args[i++];
            E.checkArgument(c.isAssignableFrom(arg.getClass()),
                            "The arg '%s'(%s) can't match %s",
                            arg, arg.getClass(), c);
        }
    }

    @Override
    public String toString() {
        return String.format("Event{name='%s', args=%s}",
                             this.name, Arrays.asList(this.args));
    }
}
