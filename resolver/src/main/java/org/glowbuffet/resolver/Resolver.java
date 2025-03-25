package org.glowbuffet.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glowbuffet.common.dto.Command;
import org.glowbuffet.common.dto.Resolution;

public class Resolver {

    public static Resolution resolveMessage(String message) throws JsonProcessingException {
        Command command = new ObjectMapper().readValue(message, Command.class);
        Resolution resolution = new Resolution(command);
        if (command.getText().startsWith("go")) {
            resolution.setText(navigate(command.getText()));
        } else if (command.getText().startsWith("attack")) {
            resolution.setText(attack(command.getText()));
        } else {
            resolution.setText("No command found, please start message with 'attack' or 'go'");
        }

        return resolution;
    }

    private static String attack(String commandText) {
        String resolutionText;
        if (commandText.contains("wolf")) {
            resolutionText = "You attack the wolf";
        } else {
            resolutionText = "No target found, please be more specific with your attack target";
        }
        return resolutionText;
    }

    private static String navigate(String commandText) {
        String resolutionText;
        if (commandText.contains("north")) {
            resolutionText = "You travel North";
        } else {
            resolutionText = "No direction found, please include 'north', 'south', 'east', or 'west' in your message";
        }
        return resolutionText;
    }
}
