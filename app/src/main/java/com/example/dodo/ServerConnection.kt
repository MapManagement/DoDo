package com.example.dodo

import androidx.preference.PreferenceManager
import com.example.proto.DoDoGrpc
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import java.net.URL

public class ServerConnector(context: android.content.Context)  {
    private val app_context = context

    private fun createChannel(): ManagedChannel? {
        val serverAdress = getServerAddress()
        if(serverAdress == null) {
            val serverURL = URL(serverAdress) //ToDo: check server port
            val port = if (serverURL.port == -1) serverURL.defaultPort else serverURL.port

            val builder = ManagedChannelBuilder.forAddress(serverURL.host, port)
            if (serverURL.protocol == "https") {
                builder.useTransportSecurity()
            } else {
                builder.usePlaintext()
            }

            return builder.executor(Dispatchers.Default.asExecutor()).build()
        }
        return null
    }

    fun connectToServer(): DoDoGrpc.DoDoStub {
        return DoDoGrpc.newStub(createChannel())
    }

    private fun getServerAddress(): String? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(app_context)
        return prefs.getString("pref_server_addr", null)
    }

}