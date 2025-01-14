package net.perfectdreams.discordinteraktions.webserver

import dev.kord.common.annotation.KordPreview
import dev.kord.common.entity.DiscordInteraction
import dev.kord.common.entity.InteractionResponseType
import dev.kord.common.entity.Snowflake
import dev.kord.rest.service.RestClient
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import mu.KotlinLogging
import net.perfectdreams.discordinteraktions.common.commands.CommandManager
import net.perfectdreams.discordinteraktions.common.requests.InteractionRequestState
import net.perfectdreams.discordinteraktions.common.requests.RequestBridge
import net.perfectdreams.discordinteraktions.common.utils.Observable
import net.perfectdreams.discordinteraktions.platforms.kord.utils.KordAutocompleteChecker
import net.perfectdreams.discordinteraktions.platforms.kord.utils.KordCommandChecker
import net.perfectdreams.discordinteraktions.platforms.kord.utils.KordComponentChecker
import net.perfectdreams.discordinteraktions.platforms.kord.utils.KordModalSubmitChecker
import net.perfectdreams.discordinteraktions.webserver.requests.managers.WebServerRequestManager

/**
 * This is the default implementation of [InteractionRequestHandler],
 * and the server's built-in request handler too (you can change that on the [InteractionsServer] class)
 *
 * @param m The server that we'll handle the requests for.
 */
@OptIn(KordPreview::class, ExperimentalCoroutinesApi::class)
class DefaultInteractionRequestHandler(
    val applicationId: Snowflake,
    val commandManager: CommandManager,
    val rest: RestClient,
) : InteractionRequestHandler() {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private val kordCommandChecker = KordCommandChecker(commandManager)
    private val kordComponentChecker = KordComponentChecker(commandManager)
    private val kordAutocompleteChecker = KordAutocompleteChecker(commandManager)
    private val kordModalChecker = KordModalSubmitChecker(commandManager)

    override suspend fun onPing(call: ApplicationCall) {
        logger.info { "Ping Request Received!" }
        call.respondText(
            buildJsonObject {
                put("type", InteractionResponseType.Pong.type)
            }.toString(),
            ContentType.Application.Json
        )
    }

    override suspend fun onCommand(call: ApplicationCall, request: DiscordInteraction) {
        val observableState = Observable(InteractionRequestState.NOT_REPLIED_YET)
        val bridge = RequestBridge(observableState)

        val requestManager = WebServerRequestManager(
            bridge,
            rest,
            applicationId,
            request.token,
            call
        )

        bridge.manager = requestManager

        kordCommandChecker.checkAndExecute(
            request,
            requestManager
        )

        observableState.awaitChange()
        logger.info { "State was changed to ${observableState.value}, so this means we already replied via the Web Server! Leaving request scope..." }
    }

    override suspend fun onComponent(call: ApplicationCall, request: DiscordInteraction) {
        val observableState = Observable(InteractionRequestState.NOT_REPLIED_YET)
        val bridge = RequestBridge(observableState)

        val requestManager = WebServerRequestManager(
            bridge,
            rest,
            applicationId,
            request.token,
            call
        )

        bridge.manager = requestManager

        kordComponentChecker.checkAndExecute(
            request,
            requestManager
        )

        observableState.awaitChange()
        logger.info { "State was changed to ${observableState.value}, so this means we already replied via the Web Server! Leaving request scope..." }
    }

    override suspend fun onAutocomplete(call: ApplicationCall, request: DiscordInteraction) {
        val observableState = Observable(InteractionRequestState.NOT_REPLIED_YET)
        val bridge = RequestBridge(observableState)

        val requestManager = WebServerRequestManager(
            bridge,
            rest,
            applicationId,
            request.token,
            call
        )

        bridge.manager = requestManager

        kordAutocompleteChecker.checkAndExecute(
            request,
            requestManager
        )

        observableState.awaitChange()
        logger.info { "State was changed to ${observableState.value}, so this means we already replied via the Web Server! Leaving request scope..." }
    }

    override suspend fun onModalSubmit(call: ApplicationCall, request: DiscordInteraction) {
        val observableState = Observable(InteractionRequestState.NOT_REPLIED_YET)
        val bridge = RequestBridge(observableState)

        val requestManager = WebServerRequestManager(
            bridge,
            rest,
            applicationId,
            request.token,
            call
        )

        bridge.manager = requestManager

        kordModalChecker.checkAndExecute(
            request,
            requestManager
        )

        observableState.awaitChange()
        logger.info { "State was changed to ${observableState.value}, so this means we already replied via the Web Server! Leaving request scope..." }
    }
}
