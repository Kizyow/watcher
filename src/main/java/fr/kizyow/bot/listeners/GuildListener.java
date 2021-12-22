package fr.kizyow.bot.listeners;

import fr.kizyow.bot.database.tables.GuildTable;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class GuildListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        Guild guild = event.getGuild();
        GuildTable guildTable = new GuildTable();
        guildTable.createGuild(guild);
    }

}
