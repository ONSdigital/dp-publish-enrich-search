package com.github.onsdigital.cli;


import com.beust.jcommander.JCommander;
import com.github.onsdigital.cli.commands.Command;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Set;


/**
 * Created by James Fawke on 09/01/2017.
 */

public class CommandLineTool {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineTool.class);

    public static void main(String... args) throws IOException {
        JCommander jc = buildCommander();
        jc.parse(args);
        String parsedCommand = jc.getParsedCommand();

        if (StringUtils.isBlank(parsedCommand)) {
            jc.usage();
            return;
        }
        // Get the 'Parsed' Command object
        JCommander jCommander = jc.getCommands()
                                  .get(parsedCommand);

        Command requestedCommand = (Command) jCommander
                .getObjects()
                .get(0);
        if (requestedCommand.isHelp()) {
            jCommander.usage();
        }
        else {
            requestedCommand.execute();
        }


    }


    /**
     * Build the JCommander parameter parser based on the children of the Command interface
     */
    private static JCommander buildCommander() {
        final JCommander c = new JCommander();
        Reflections reflections = new Reflections("com.github.onsdigital.cli.commands");
        Set<Class<? extends Command>> subTypes = reflections.getSubTypesOf(Command.class);
        subTypes.stream()
                .filter(CommandLineTool::isNotInterface)
                .filter(CommandLineTool::isNotAbstract)
                .forEach(cmd -> CommandLineTool.addNewCommand(c, cmd));
        return c;
    }


    private static void addNewCommand(JCommander c, Class commandClass) {
        try {
            c.addCommand(commandClass.newInstance());
        }
        catch (InstantiationException | IllegalAccessException e) {
            LOGGER.info("addNewCommand([c, commandClass]) : Exception ",
                        e);
        }

    }

    private static boolean isNotInterface(Class<? extends Command> c) {
        return !c.isInterface();
    }

    private static boolean isNotAbstract(Class<? extends Command> c) {
        return !Modifier.isAbstract(c.getModifiers());
    }

}
