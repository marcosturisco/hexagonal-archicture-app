package com.turisco.learning.adapter.inbound.cli.zoo;

import com.turisco.learning.adapter.inbound.cli.exception.InvalidCommandException;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class AnimalShellCommands {

    private final AnimalCommandLine animalCommandLine;

    @ShellMethod(key = "create-animal",
            value = "Creates an Animal executing the AnimalCommandLine.")
    public String createAnimalCommand(
            @ShellOption(help = "Name of the animal") String name,
            @ShellOption(help = "Species of the animal") String species,
            @ShellOption(help = "Age of the animal") String age) {
        try {
            animalCommandLine.run(name, species, age);
            return "✅ CLI executed successfully!";
        } catch (Exception e) {
            throw new InvalidCommandException(e.getMessage());
        }
    }

    @ShellMethod(key = "find-all-animals",
            value = "Search for all Animals executing the AnimalCommandLine.")
    public String findAllAnimalCommand() {
        try {
            animalCommandLine.run();
            return "✅ CLI executed successfully!";
        } catch (Exception e) {
            throw new InvalidCommandException(e.getMessage());
        }
    }
}
