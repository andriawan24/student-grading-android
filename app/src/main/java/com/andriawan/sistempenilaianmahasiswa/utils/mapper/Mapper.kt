package com.andriawan.sistempenilaianmahasiswa.utils.mapper

interface Mapper<Network, Domain> {
    fun mapIncoming(network: Network): Domain
    fun mapOutgoing(domain: Domain): Network
}