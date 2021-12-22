package fr.kizyow.bot.commands.commons;

import fr.kizyow.bot.commands.GuildCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class SetupCommand extends GuildCommand {

    public SetupCommand() {
        super("setup", "Permet de configurer le bot pour un serveur de base", new Permission[]{Permission.ADMINISTRATOR});
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

        // config prefix cmd
        // config autorole
        // config ticket
        // config level
        // config bienvenue

    }
}
