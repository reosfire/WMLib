package ru.reosfire.wmlib.extensions

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.wrappers.BlockPosition
import com.comphenix.protocol.wrappers.WrappedBlockData
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.craftbukkit.v1_12_R1.CraftChunk
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld
import org.bukkit.entity.Player
import org.bukkit.material.MaterialData

fun Block.crackFor(player: Player, level: Int, entityId: Int) {
    val protocolManager = ProtocolLibrary.getProtocolManager()
    val packet = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION)
    packet.blockPositionModifier.write(0, BlockPosition(x, y, z))
    packet.integers
        .write(0, entityId)
        .write(1, level)
    ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet)
}

fun Block.crackFor(players: Iterable<Player>, level: Int, entityId: Int) {
    for (player in players) {
        crackFor(player, level, entityId)
    }
}

fun Block.setFor(player: Player, materialData: MaterialData) {
    val protocolManager = ProtocolLibrary.getProtocolManager()
    val packet = protocolManager.createPacket(PacketType.Play.Server.BLOCK_CHANGE)
    packet.modifier.writeDefaults()
    packet.blockPositionModifier.write(0, BlockPosition(x, y, z))
    packet.blockData.write(0, WrappedBlockData.createData(materialData.itemType, materialData.data.toInt()))
    ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet)
}

fun Block.NMSSet(materialData: MaterialData) {
    val material = materialData.itemType
    val data = materialData.data
    val chunk = (location.chunk as CraftChunk).handle
    val blockPosition = net.minecraft.server.v1_12_R1.BlockPosition(x, y, z)
    val blockData = net.minecraft.server.v1_12_R1.Block.getByCombinedId(material.id + (data.toInt() shl 12))
    val chunkSection = chunk.sections[blockPosition.y shr 4]
    chunkSection.blocks.setBlock(
        blockPosition.x and 15, blockPosition.y and 15,
        blockPosition.z and 15, blockData
    )
    (location.world as CraftWorld).handle.notify(blockPosition, blockData, blockData, 0)
}