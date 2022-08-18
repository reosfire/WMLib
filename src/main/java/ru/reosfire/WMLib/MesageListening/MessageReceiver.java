package ru.reosfire.WMLib.MesageListening;

public interface MessageReceiver
{
    void OnMessage(String message, MessageListener listener);
}