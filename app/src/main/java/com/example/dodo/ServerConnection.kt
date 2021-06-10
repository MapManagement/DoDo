package com.example.dodo

import com.example.proto.DoDoGrpc
import com.example.proto.DoDoProto
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import java.io.Closeable
import java.net.URL

public class ServerConnector()  {

    private fun createChannel(): ManagedChannel {
        val serverURL = URL("") //ToDo: get URL from Settings
        val port = if (serverURL.port == -1) serverURL.defaultPort else serverURL.port


        val builder = ManagedChannelBuilder.forAddress(serverURL.host, port)
        if (serverURL.protocol == "https") {
            builder.useTransportSecurity()
        } else {
            builder.usePlaintext()
        }

        return builder.executor(Dispatchers.Default.asExecutor()).build()
    }

    fun connectToServer(): DoDoGrpc.DoDoStub {
        return DoDoGrpc.newStub(createChannel())
    }

}