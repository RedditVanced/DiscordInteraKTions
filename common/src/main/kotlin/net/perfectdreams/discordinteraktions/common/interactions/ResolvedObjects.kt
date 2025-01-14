package net.perfectdreams.discordinteraktions.common.interactions

import dev.kord.common.entity.Snowflake
import net.perfectdreams.discordinteraktions.common.entities.InteractionMember
import net.perfectdreams.discordinteraktions.common.entities.User
import net.perfectdreams.discordinteraktions.common.entities.messages.Message

// TODO: channels
// TODO: roles
class ResolvedObjects(
    val users: Map<Snowflake, User>?,
    val members: Map<Snowflake, InteractionMember>?,
    val messages: Map<Snowflake, Message>?
)