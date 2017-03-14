package com.github.onsdigital.cli.commands;

import java.io.IOException;

/**
 * Created by fawks on 09/01/2017.
 */
public interface Command {
    void execute() throws IOException;
    boolean isHelp();
}
