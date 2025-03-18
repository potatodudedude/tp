package seedu.address.logic.commands;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = HelpCommand.showHelpMessage();

    private static String[] listJavaFiles(File directory) {
        if (!directory.isDirectory() || !directory.exists()) {
            return null;
        } else {
            String[] directoryList = directory.list((dir, name) -> name.endsWith(".java"));

            if (directoryList == null) {
                return null;
            }

            return Arrays.stream(directoryList).map((name) -> {
                String[] nameArray = name.split("/");
                int length = nameArray.length;
                return nameArray[length - 1];
            }).toArray(String[]::new);
        }
    }

    private static String showHelpMessage() {
        StringBuilder message = new StringBuilder();

        File directory = new File("./src/main/java/seedu/address/logic/commands/");
        String[] fileStrings = listJavaFiles(directory);

        if (fileStrings == null) {
            return "";
        }

        for (String string : fileStrings) {
            String fileString = "seedu.address.logic.commands." + string;

            if (!fileString.endsWith("Command.java")
                    || string.equals("Command.java")) {
                continue;
            }

            int index = fileString.indexOf(".java");
            fileString = fileString.substring(0, index);

            try {
                Class<?> commandClass = Class.forName(fileString);
                String helpMessage = (String) commandClass.getDeclaredField("MESSAGE_USAGE").get(null);

                if (helpMessage == null) {
                    helpMessage = "MESSAGE_USAGE not implemented...";
                }

                message.append(helpMessage).append("\n\n");
            } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
                Logger logger = LogsCenter.getLogger(HelpCommand.class);
                logger.warning(e.getMessage());
            }
        }

        return message.toString().strip();
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, false, false);
    }
}
