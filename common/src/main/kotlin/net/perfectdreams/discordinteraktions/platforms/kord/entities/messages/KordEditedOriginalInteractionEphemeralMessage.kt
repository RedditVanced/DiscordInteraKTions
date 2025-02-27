package net.perfectdreams.discordinteraktions.platforms.kord.entities.messages

import dev.kord.common.entity.DiscordMessage
import dev.kord.common.entity.Snowflake
import dev.kord.rest.service.RestClient
import net.perfectdreams.discordinteraktions.common.builder.message.modify.InteractionOrFollowupMessageModifyBuilder
import net.perfectdreams.discordinteraktions.common.entities.messages.EditableMessage

class KordEditedOriginalInteractionEphemeralMessage(
    private val rest: RestClient,
    private val applicationId: Snowflake,
    private val interactionToken: String,
    data: DiscordMessage
) : KordEphemeralMessage(data), EditableMessage {
    override suspend fun editMessage(message: InteractionOrFollowupMessageModifyBuilder): EditableMessage {
        val newMessage = rest.interaction.modifyInteractionResponse(
            applicationId,
            interactionToken,
            message.toInteractionMessageResponseModifyBuilder().toRequest()
        )

        return KordEditedOriginalInteractionEphemeralMessage(
            rest,
            applicationId,
            interactionToken,
            newMessage
        )
    }
}