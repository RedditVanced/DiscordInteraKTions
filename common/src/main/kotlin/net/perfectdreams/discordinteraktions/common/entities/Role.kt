package net.perfectdreams.discordinteraktions.common.entities

import dev.kord.common.entity.DiscordRoleTags
import dev.kord.common.entity.Permissions
import dev.kord.common.entity.Snowflake
import dev.kord.common.entity.optional.Optional

interface Role {
	// id=Snowflake(value=297051132793061378), name=new role, color=15277667, hoist=false, position=60, permissions=Permissions(values=DiscordBitSet(100100100111101111101100011000011)), managed=false, mentionable=true, tags=Optional.Missing)}
	val id: Snowflake
	val name: String
	val color: Int
	val hoist: Boolean
	val position: Int
	val permissions: Permissions
	val managed: Boolean
	val mentionable: Boolean
	val tags: Optional<DiscordRoleTags>
}