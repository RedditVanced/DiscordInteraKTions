package net.perfectdreams.discordinteraktions.platforms.kord.entities

import dev.kord.common.entity.Snowflake
import dev.kord.rest.builder.message.modify.EphemeralInteractionResponseModifyBuilder
import dev.kord.rest.builder.message.modify.PublicInteractionResponseModifyBuilder
import dev.kord.rest.builder.message.modify.embed
import dev.kord.rest.service.RestClient
import net.perfectdreams.discordinteraktions.common.entities.EphemeralMessage
import net.perfectdreams.discordinteraktions.common.entities.Message
import net.perfectdreams.discordinteraktions.common.utils.EphemeralMessageBuilder
import net.perfectdreams.discordinteraktions.common.utils.InteractionMessage
import net.perfectdreams.discordinteraktions.common.utils.buildEphemeralMessage
import net.perfectdreams.discordinteraktions.platforms.kord.utils.toKordAllowedMentions
import net.perfectdreams.discordinteraktions.platforms.kord.utils.toKordEmbedBuilder

class KordOriginalInteractionEphemeralMessage(
    private val rest: RestClient,
    private val applicationId: Snowflake,
    private val interactionToken: String,
    override val content: String
) : EphemeralMessage {
    override val id: net.perfectdreams.discordinteraktions.api.entities.Snowflake
        get() = error("Original Interaction Messages do not have an ID!")

    override suspend fun editMessage(block: EphemeralMessageBuilder.() -> Unit): EphemeralMessage {
        val message = buildEphemeralMessage(block)
        val newMessage = rest.interaction.modifyInteractionResponse(
            applicationId,
            interactionToken,
            EphemeralInteractionResponseModifyBuilder().apply {
                this.content = message.content
                this.allowedMentions = message.allowedMentions?.toKordAllowedMentions()
                this.embeds = message.embeds?.let { it.map { it.toKordEmbedBuilder() } }?.toMutableList()
            }.toRequest()
        )

        return KordEditedOriginalInteractionEphemeralMessage(
            rest,
            applicationId,
            interactionToken,
            newMessage
        )
    }
}