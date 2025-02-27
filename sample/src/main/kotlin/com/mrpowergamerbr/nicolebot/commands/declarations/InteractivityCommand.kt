package com.mrpowergamerbr.nicolebot.commands.declarations

import com.mrpowergamerbr.nicolebot.commands.ButtonsExecutor
import com.mrpowergamerbr.nicolebot.commands.SendModalExecutor
import net.perfectdreams.discordinteraktions.common.commands.SlashCommandDeclarationWrapper
import net.perfectdreams.discordinteraktions.common.commands.slashCommand

object InteractivityCommand : SlashCommandDeclarationWrapper {
    override fun declaration() = slashCommand("interactivity", "Showing off Discord InteraKTions' Interactivity") {
        subcommand("buttons", "Showing off Discord InteraKTions' Button Interactivity") {
            executor = ButtonsExecutor
        }

        subcommand("modal", "Showing off Discord InteraKTions' Modals Interactivity") {
            executor = SendModalExecutor
        }
    }
}